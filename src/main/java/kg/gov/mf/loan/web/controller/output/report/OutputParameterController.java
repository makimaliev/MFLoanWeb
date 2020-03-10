package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.output.report.model.OutputParameter;
import kg.gov.mf.loan.output.report.service.OutputParameterService;
import kg.gov.mf.loan.output.report.service.ReportTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OutputParameterController {
	
	@Autowired
    private OutputParameterService outputParameterService;
	
	
	@Autowired
    private ReportTemplateService reportTemplateService;
	
     
    public void setOutputParameterService(OutputParameterService rs)
    {
        this.outputParameterService = rs;
    }

	@RequestMapping(value = "/outputParameter/list", method = RequestMethod.GET)
	public String listOutputParameters(Model model) {
		model.addAttribute("outputParameter", new OutputParameter());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());

		return "output/report/outputParameterList";
	}
	
	@RequestMapping("outputParameter/{id}/view")
	public String viewOutputParameterById(@PathVariable("id") long id, Model model) {

		OutputParameter outputParameter = this.outputParameterService.findById(id);

		model.addAttribute("outputParameter", outputParameter);

		return "output/report/outputParameterView";
	}
	
	@RequestMapping("outputParameter/{id}/details")
	public String viewOutputParameterDetailsById(@PathVariable("id") long id, Model model) {

		OutputParameter outputParameter = this.outputParameterService.findById(id);

		model.addAttribute("outputParameter", outputParameter);

		return "output/report/outputParameterDetails";
	}	
    
	
	@RequestMapping(value = "/outputParameter/add", method = RequestMethod.GET)
	public String getOutputParameterAddForm(Model model) {

		model.addAttribute("outputParameter", new OutputParameter());

		return "output/report/outputParameterForm";
	}
	
	@RequestMapping("/outputParameter/{id}/edit")
	public String getOutputParameterEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("outputParameter", this.outputParameterService.findById(id));
		return "output/report/outputParameterForm";

	}

	@RequestMapping(value = "/outputParameter/save", method = RequestMethod.POST)
	public String saveOutputParameterAndRedirectToOutputParameterList(@Validated @ModelAttribute("outputParameter") OutputParameter outputParameter, BindingResult result) {

		if (result.hasErrors()) {

		} else if (outputParameter.getId() == 0) {
			this.outputParameterService.create(outputParameter);
		} else {
			this.outputParameterService.edit(outputParameter);
		}

		return "redirect:/outputParameter/list";

	}

	@RequestMapping("/outputParameter/{id}/remove")
	public String removeOutputParameterAndRedirectToOutputParameterList(@PathVariable("id") long id) {

		this.outputParameterService.deleteById(id);

		return "redirect:/outputParameter/list";
	}

     

     

}
