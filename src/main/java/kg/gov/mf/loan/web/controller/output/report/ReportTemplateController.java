package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.output.report.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private OutputParameterService outputParameterService;

	@Autowired
    private ReportService reportService;

	@Autowired
	private GroupTypeService groupTypeService;
	
     
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
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());

		return "output/report/reportTemplateList";
	}
	
	@RequestMapping("reportTemplate/{id}/view")
	public String viewReportTemplateById(@PathVariable("id") long id, Model model) {

		ReportTemplate reportTemplate = this.reportTemplateService.findById(id);
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());

		model.addAttribute("reportTemplate", reportTemplate);

		return "output/report/reportTemplateView";
	}
	
	@RequestMapping("reportTemplate/{id}/details")
	public String viewReportTemplateDetailsById(@PathVariable("id") long id, Model model) {

		ReportTemplate reportTemplate = this.reportTemplateService.findById(id);
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());
		model.addAttribute("reportTemplate", reportTemplate);

		return "output/report/reportTemplateDetails";
	}	

	@RequestMapping(value = "/reportTemplate/add", method = RequestMethod.GET)
	public String getReportTemplateAddForm(Model model) {

		ReportTemplate modelReportTemplate = new ReportTemplate();
		
		modelReportTemplate.setReport(this.reportService.findById((long)1));
		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());
		model.addAttribute("reportTemplate",modelReportTemplate);
		

		return "output/report/reportTemplateForm";
	}
	
	@RequestMapping(value = "/report/{reportId}/reportTemplate/add", method = RequestMethod.GET)
	public String getReportTemplateAddFormWithReport(@PathVariable("reportId") long reportId,Model model) {




		ReportTemplate modelReportTemplate = new ReportTemplate();
		
		modelReportTemplate.setReport(this.reportService.findById(reportId));
		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());

		model.addAttribute("reportTemplateGenerationParameters", modelReportTemplate.getGenerationParameters());
		model.addAttribute("reportTemplateFilterParameters", modelReportTemplate.getFilterParameters());
		model.addAttribute("reportTemplateContentParameters", modelReportTemplate.getContentParameters());
		model.addAttribute("reportTemplateOutputParameters", modelReportTemplate.getOutputParameters());

		model.addAttribute("reportTemplate",modelReportTemplate);

		return "output/report/reportTemplateForm";
	}	

	@RequestMapping("/reportTemplate/{id}/edit")
	public String getReportTemplateEditForm(@PathVariable("id") long id, Model model) {

/*
    	ReportTemplate modelReportTemplate = this.reportTemplateService.findById(id);
    	modelReportTemplate.setGenerationParameters(this.reportTemplateService.findById(id).getGenerationParameters());
*/

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startDate = new Date();
		System.out.println(" START == "+dateFormat.format(startDate)); //2016/11/16 12:08:43


		ReportTemplate modelReportTemplate = this.reportTemplateService.findById(id);

		Date dataGetDate = new Date();
		System.out.println(" FINISH == "+dateFormat.format(dataGetDate)); //2016/11/16 12:08:43
		System.out.println(" DIFF == "+(dataGetDate.getTime()-startDate.getTime())); //2016/11/16 12:08:43


		model.addAttribute("reportTemplateGenerationParameters", modelReportTemplate.getGenerationParameters());
		model.addAttribute("reportTemplateFilterParameters", modelReportTemplate.getFilterParameters());
		model.addAttribute("reportTemplateContentParameters", modelReportTemplate.getContentParameters());
		model.addAttribute("reportTemplateOutputParameters", modelReportTemplate.getOutputParameters());


		model.addAttribute("reportTemplate", modelReportTemplate);

		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());


		return "output/report/reportTemplateForm";

	}

	@RequestMapping(value = "/reportTemplate/save", method = RequestMethod.POST)
	public String saveReportTemplateAndRedirectToReportTemplateList(@Validated @ModelAttribute("reportTemplate") ReportTemplate reportTemplate,
																	String[] reportTemplateGenerationParametersIds,
																	String[] reportTemplateFilterParametersIds,
																	String[] reportTemplateContentParametersIds,
																	String[] reportTemplateOutputParametersIds,
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


		if(reportTemplateOutputParametersIds!=null)
			for (String id: reportTemplateOutputParametersIds)
			{
				reportTemplate.getOutputParameters().add(this.outputParameterService.findById(Long.valueOf(id)));
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



		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition","attachment; filename=report.xls");
		OutputStream out = null;

		ReportTemplate reportTemplate = this.reportTemplateService.findById(id);



		switch(reportTemplate.getReport().getReportType().toString())
		{
			case "LOAN_SUMMARY":
				generateReport(out,response,new ReportGeneratorLoanSummary(), reportTemplate);

				break;

			case "LOAN_PAYMENT":
				generateReport(out,response,new ReportGeneratorPayment(), reportTemplate);
				break;

			case "COLLATERAL_ITEM":
				generateReport(out,response,new ReportGeneratorCollateralItem(), reportTemplate);
				break;

			case "LOAN_SCHEDULE":
				generateReport(out,response,new ReportGeneratorPaymentSchedule(), reportTemplate);
				break;

			case "COLLECTION_PHASE":
				generateReport(out,response,new ReportGeneratorCollectionPhase(), reportTemplate);
				break;

			case "ENTITY_DOCUMENT":
				generateReport(out,response,new ReportGeneratorEntityDocument(), reportTemplate);


				break;

		}

	}

	public void generateReport(OutputStream out, HttpServletResponse response, ReportGenerator reportGenerator, ReportTemplate reportTemplate  )
	{
		try {
			out = response.getOutputStream();
			reportGenerator.generateReportByTemplate(reportTemplate).write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
