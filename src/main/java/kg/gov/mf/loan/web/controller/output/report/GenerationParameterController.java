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

import java.util.Date;

@Controller
public class GenerationParameterController {
	
	@Autowired
    private GenerationParameterService generationParameterService;
	
	
	@Autowired
    private ReportTemplateService reportTemplateService;

	@Autowired
	private GenerationParameterTypeService generationParameterTypeService;

    public void setGenerationParameterService(GenerationParameterService rs)
    {
        this.generationParameterService = rs;
    }

	@RequestMapping(value = "/generationParameter/list", method = RequestMethod.GET)
	public String listGenerationParameters(Model model) {
		model.addAttribute("generationParameter", new GenerationParameter());
		model.addAttribute("generationParameterList", this.generationParameterService.findAll());
		model.addAttribute("generationParameterTypeList", this.generationParameterTypeService.findAll());

		return "output/report/generationParameterList";
	}
	
	@RequestMapping("generationParameter/{id}/view")
	public String viewGenerationParameterById(@PathVariable("id") long id, Model model) {

		GenerationParameter generationParameter = this.generationParameterService.findById(id);

		model.addAttribute("generationParameter", generationParameter);
		model.addAttribute("generationParameterTypeList", this.generationParameterTypeService.findAll());

		return "output/report/generationParameterView";
	}
	
	@RequestMapping("generationParameter/{id}/details")
	public String viewGenerationParameterDetailsById(@PathVariable("id") long id, Model model) {

		GenerationParameter generationParameter = this.generationParameterService.findById(id);

		model.addAttribute("generationParameter", generationParameter);
		model.addAttribute("generationParameterTypeList", this.generationParameterTypeService.findAll());

		return "output/report/generationParameterDetails";
	}	
    
	
	@RequestMapping(value = "/generationParameter/add", method = RequestMethod.GET)
	public String getGenerationParameterAddForm(Model model) {

    	GenerationParameter generationParameter=new GenerationParameter();
    	generationParameter.setDate(new Date());
		model.addAttribute("generationParameter",generationParameter);
		model.addAttribute("generationParameterTypeList", this.generationParameterTypeService.findAll());

		return "output/report/generationParameterForm";
	}
	
	@RequestMapping("/generationParameter/{id}/edit")
	public String getGenerationParameterEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("generationParameter", this.generationParameterService.findById(id));
		model.addAttribute("generationParameterTypeList", this.generationParameterTypeService.findAll());

		return "output/report/generationParameterForm";

	}

	@RequestMapping(value = "/generationParameter/save", method = RequestMethod.POST)
	public String saveGenerationParameterAndRedirectToGenerationParameterList(@Validated @ModelAttribute("generationParameter") GenerationParameter generationParameter, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (generationParameter.getId() == 0) {
			this.generationParameterService.create(generationParameter);
		} else {
			this.generationParameterService.edit(generationParameter);
		}

		return "redirect:/generationParameter/list";

	}

	@RequestMapping("/generationParameter/{id}/remove")
	public String removeGenerationParameterAndRedirectToGenerationParameterList(@PathVariable("id") long id) {

		this.generationParameterService.deleteById(id);

		return "redirect:/generationParameter/list";
	}

     

     

}
