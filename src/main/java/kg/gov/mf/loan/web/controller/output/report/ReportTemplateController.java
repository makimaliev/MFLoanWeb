package kg.gov.mf.loan.web.controller.output.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.admin.org.model.District;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.*;

import java.util.Set;

@Controller
public class ReportTemplateController {
	
	@Autowired
    private ReportTemplateService reportTemplateService;

	@Autowired
	private GenerationParameterService generationParameterService;

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

		return "output/report/reportTemplateList";
	}
	
	@RequestMapping("reportTemplate/{id}/view")
	public String viewReportTemplateById(@PathVariable("id") long id, Model model) {

		ReportTemplate reportTemplate = this.reportTemplateService.findById(id);
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());

		model.addAttribute("reportTemplate", reportTemplate);

		return "output/report/reportTemplateView";
	}
	
	@RequestMapping("reportTemplate/{id}/details")
	public String viewReportTemplateDetailsById(@PathVariable("id") long id, Model model) {

		ReportTemplate reportTemplate = this.reportTemplateService.findById(id);
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());

		model.addAttribute("reportTemplate", reportTemplate);

		return "output/report/reportTemplateDetails";
	}	
    
	
	@RequestMapping(value = "/reportTemplate/add", method = RequestMethod.GET)
	public String getReportTemplateAddForm(Model model) {

		ReportTemplate modelReportTemplate = new ReportTemplate();
		
		modelReportTemplate.setReport(this.reportService.findById((long)1));
		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());

		model.addAttribute("reportTemplate",modelReportTemplate);
		

		return "output/report/reportTemplateForm";
	}
	
	@RequestMapping(value = "/report/{reportId}/reportTemplate/add", method = RequestMethod.GET)
	public String getReportTemplateAddFormWithReport(@PathVariable("reportId") long reportId,Model model) {

		ReportTemplate modelReportTemplate = new ReportTemplate();
		
		modelReportTemplate.setReport(this.reportService.findById(reportId));
		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());

		model.addAttribute("reportTemplateGenerationParameters", modelReportTemplate.getGenerationParameters());

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

		model.addAttribute("reportTemplate", this.reportTemplateService.findById(id));

		model.addAttribute("reportList", this.reportService.findAll());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		
		return "output/report/reportTemplateForm";

	}

	@RequestMapping(value = "/reportTemplate/{reportTemplateId}/generationParameter/add", method = RequestMethod.GET)
	public String getAddGenerationParamtersToReportTemplateForm(@PathVariable("reportTemplateId") long reportTemplateId,Model model) {

		ReportTemplate modelReportTemplate = this.reportTemplateService.findById(reportTemplateId);



		model.addAttribute("generationParameterList", this.generationParameterService.findAll());

		model.addAttribute("reportTemplate",modelReportTemplate);
		model.addAttribute("selectedGenerationParameterId",(long)1);

		return "output/report/generationParameterAddToReportTemplateForm";
	}

	@RequestMapping(value = "/reportTemplate/generationParameter/add", method = RequestMethod.POST)
	public String addGenerationParameterToReportTemplate(@PathVariable("reportTemplateId")Long reportTemplateId)
	{

		ReportTemplate reportTemplate = this.reportTemplateService.findById(reportTemplateId);

		System.out.println(reportTemplateId);
		//GenerationParameter generationParameter = this.generationParameterService.findById(selectedGenerationParameterId);

		//Set<GenerationParameter> generationParameters = reportTemplate.getGenerationParameter();

		//reportTemplate.setGenerationParameter(generationParameters);


		this.reportTemplateService.edit(reportTemplate);

		return "redirect:/reportTemplate/list";

	}



	@RequestMapping(value = "/reportTemplate/save", method = RequestMethod.POST)
	public String saveReportTemplateAndRedirectToReportTemplateList(@Validated @ModelAttribute("reportTemplate") ReportTemplate reportTemplate,
																	String[] reportTemplateGenerationParametersIds,
																	BindingResult result) {

		Report report = this.reportService.findById(reportTemplate.getReport().getId());

		for (String id: reportTemplateGenerationParametersIds)
		{
			reportTemplate.getGenerationParameters().add(this.generationParameterService.findById(Long.valueOf(id)));
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

     

     

}
