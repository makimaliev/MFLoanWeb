package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.output.report.model.GenerationParameterType;
import kg.gov.mf.loan.output.report.service.GenerationParameterTypeService;
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
public class GenerationParameterTypeController {
	
	@Autowired
    private GenerationParameterTypeService generationParameterTypeService;
	

   public void setGenerationParameterTypeService(GenerationParameterTypeService rs)
    {
        this.generationParameterTypeService = rs;
    }

	@RequestMapping(value = "/generationParameterType/list", method = RequestMethod.GET)
	public String listGenerationParameterTypes(Model model) {
		model.addAttribute("generationParameterType", new GenerationParameterType());
		model.addAttribute("generationParameterTypeList", this.generationParameterTypeService.findAll());

		return "output/report/generationParameterTypeList";
	}
	
	@RequestMapping("generationParameterType/{id}/view")
	public String viewGenerationParameterTypeById(@PathVariable("id") long id, Model model) {

		GenerationParameterType generationParameterType = this.generationParameterTypeService.findById(id);

		model.addAttribute("generationParameterType", generationParameterType);

		return "output/report/generationParameterTypeView";
	}
	
	@RequestMapping("generationParameterType/{id}/details")
	public String viewGenerationParameterTypeDetailsById(@PathVariable("id") long id, Model model) {

		GenerationParameterType generationParameterType = this.generationParameterTypeService.findById(id);

		model.addAttribute("generationParameterType", generationParameterType);

		return "output/report/generationParameterTypeDetails";
	}	
    
	
	@RequestMapping(value = "/generationParameterType/add", method = RequestMethod.GET)
	public String getGenerationParameterTypeAddForm(Model model) {

		model.addAttribute("generationParameterType", new GenerationParameterType());

		return "output/report/generationParameterTypeForm";
	}
	
	@RequestMapping("/generationParameterType/{id}/edit")
	public String getGenerationParameterTypeEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("generationParameterType", this.generationParameterTypeService.findById(id));
		return "output/report/generationParameterTypeForm";

	}

	@RequestMapping(value = "/generationParameterType/save", method = RequestMethod.POST)
	public String saveGenerationParameterTypeAndRedirectToGenerationParameterTypeList(@Validated @ModelAttribute("generationParameterType") GenerationParameterType generationParameterType, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (generationParameterType.getId() == 0) {
			this.generationParameterTypeService.create(generationParameterType);
		} else {
			this.generationParameterTypeService.edit(generationParameterType);
		}

		return "redirect:/generationParameterType/list";

	}

	@RequestMapping("/generationParameterType/{id}/remove")
	public String removeGenerationParameterTypeAndRedirectToGenerationParameterTypeList(@PathVariable("id") long id) {

		this.generationParameterTypeService.deleteById(id);

		return "redirect:/generationParameterType/list";
	}

     

     

}
