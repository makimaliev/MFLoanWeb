package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.output.report.utils.ReportGenerator;
import kg.gov.mf.loan.output.report.utils.ReportGeneratorLoan;
import kg.gov.mf.loan.output.report.utils.ReportGeneratorPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.admin.org.model.District;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.Set;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class ReportTemplateController {
	
	@Autowired
    private ReportTemplateService reportTemplateService;

	@Autowired
	private GenerationParameterService generationParameterService;


	@Autowired
	private FilterParameterService filterParameterService;


	@Autowired
	private ContentParameterService contentParameterService;



	@Autowired
    private ReportService reportService;
	
     
    public void setReportTemplateService(ReportTemplateService rs)
    {
        this.reportTemplateService = rs;
    }

	@RequestMapping(value = "/reportTemplate/list", method = RequestMethod.GET)
	public String listReportTemplates(Model model) {
		model.addAttribute("reportTemplate", new ReportTemplate());
		model.addAttribute("reportTemplateList", this.reportTemplateService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		return "output/report/reportTemplateList";
	}
	
	@RequestMapping("reportTemplate/{id}/view")
	public String viewReportTemplateById(@PathVariable("id") long id, Model model) {

		ReportTemplate reportTemplate = this.reportTemplateService.findById(id);
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		model.addAttribute("reportTemplate", reportTemplate);

		return "output/report/reportTemplateView";
	}
	
	@RequestMapping("reportTemplate/{id}/details")
	public String viewReportTemplateDetailsById(@PathVariable("id") long id, Model model) {

		ReportTemplate reportTemplate = this.reportTemplateService.findById(id);
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("reportTemplate", reportTemplate);

		return "output/report/reportTemplateDetails";
	}	
    
	
	@RequestMapping(value = "/reportTemplate/add", method = RequestMethod.GET)
	public String getReportTemplateAddForm(Model model) {

		ReportTemplate modelReportTemplate = new ReportTemplate();
		
		modelReportTemplate.setReport(this.reportService.findById((long)1));
		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("reportTemplate",modelReportTemplate);
		

		return "output/report/reportTemplateForm";
	}
	
	@RequestMapping(value = "/report/{reportId}/reportTemplate/add", method = RequestMethod.GET)
	public String getReportTemplateAddFormWithReport(@PathVariable("reportId") long reportId,Model model) {

		ReportTemplate modelReportTemplate = new ReportTemplate();
		
		modelReportTemplate.setReport(this.reportService.findById(reportId));
		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		model.addAttribute("reportTemplateGenerationParameters", modelReportTemplate.getGenerationParameters());
		model.addAttribute("reportTemplateFilterParameters", modelReportTemplate.getFilterParameters());
		model.addAttribute("reportTemplateContentParameters", modelReportTemplate.getContentParameters());

		model.addAttribute("reportTemplate",modelReportTemplate);

		return "output/report/reportTemplateForm";
	}	

	@RequestMapping("/reportTemplate/{id}/edit")
	public String getReportTemplateEditForm(@PathVariable("id") long id, Model model) {

/*
    	ReportTemplate modelReportTemplate = this.reportTemplateService.findById(id);
    	modelReportTemplate.setGenerationParameters(this.reportTemplateService.findById(id).getGenerationParameters());
*/

		model.addAttribute("reportTemplateGenerationParameters", this.reportTemplateService.findById(id).getGenerationParameters());
		model.addAttribute("reportTemplateFilterParameters", this.reportTemplateService.findById(id).getFilterParameters());
		model.addAttribute("reportTemplateContentParameters", this.reportTemplateService.findById(id).getContentParameters());

		model.addAttribute("reportTemplate", this.reportTemplateService.findById(id));

		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		return "output/report/reportTemplateForm";

	}

	@RequestMapping(value = "/reportTemplate/save", method = RequestMethod.POST)
	public String saveReportTemplateAndRedirectToReportTemplateList(@Validated @ModelAttribute("reportTemplate") ReportTemplate reportTemplate,
																	String[] reportTemplateGenerationParametersIds,
																	String[] reportTemplateFilterParametersIds,
																	String[] reportTemplateContentParametersIds,
																	BindingResult result) {

		Report report = this.reportService.findById(reportTemplate.getReport().getId());

		if(reportTemplateGenerationParametersIds!=null)
		for (String id: reportTemplateGenerationParametersIds)
		{
			reportTemplate.getGenerationParameters().add(this.generationParameterService.findById(Long.valueOf(id)));
		}

		if(reportTemplateFilterParametersIds!=null)
		for (String id: reportTemplateFilterParametersIds)
		{
			reportTemplate.getFilterParameters().add(this.filterParameterService.findById(Long.valueOf(id)));
		}

		if(reportTemplateContentParametersIds!=null)
		for (String id: reportTemplateContentParametersIds)
		{
			reportTemplate.getContentParameters().add(this.contentParameterService.findById(Long.valueOf(id)));
		}


		reportTemplate.setReport(report);
		
		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (reportTemplate.getId() == 0) {
			this.reportTemplateService.create(reportTemplate);
		} else {
			this.reportTemplateService.edit(reportTemplate);
		}

		return "redirect:/reportTemplate/list";

	}

	@RequestMapping("/reportTemplate/{id}/remove")
	public String removeReportTemplateAndRedirectToReportTemplateList(@PathVariable("id") long id) {

		this.reportTemplateService.deleteById(id);

		return "redirect:/reportTemplate/list";
	}


	@RequestMapping("/reportTemplate/{id}/generate")
	public void generateReportByReportTemplate(@PathVariable("id") long id, HttpServletResponse response) {


		ReportGeneratorLoan loanReportGenerator = new ReportGeneratorLoan();
		ReportGeneratorPayment paymentReportGenerator = new ReportGeneratorPayment();

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment; filename=report.xls");
		OutputStream out = null;

		ReportTemplate reportTemplate = this.reportTemplateService.findById(id);


		System.out.println(reportTemplate.getReport().getReportType());

		switch(reportTemplate.getReport().getReportType().toString())
		{
			case "LOAN_SUMMARY":


				try {
					out = response.getOutputStream();
					loanReportGenerator.generateReportByTemplate(reportTemplate).write(out);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;

			case "LOAN_PAYMENT":

				try {
					out = response.getOutputStream();
					paymentReportGenerator.generateReportByTemplate(reportTemplate).write(out);
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;

		}










	}


}
