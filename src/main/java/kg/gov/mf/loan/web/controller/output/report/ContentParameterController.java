package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.output.report.model.ContentParameter;
import kg.gov.mf.loan.output.report.service.ContentParameterService;
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
public class ContentParameterController {
	
	@Autowired
    private ContentParameterService contentParameterService;
	
	
	@Autowired
    private ReportTemplateService reportTemplateService;
	
     
    public void setContentParameterService(ContentParameterService rs)
    {
        this.contentParameterService = rs;
    }

	@RequestMapping(value = "/contentParameter/list", method = RequestMethod.GET)
	public String listContentParameters(Model model) {
		model.addAttribute("contentParameter", new ContentParameter());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		return "output/report/contentParameterList";
	}
	
	@RequestMapping("contentParameter/{id}/view")
	public String viewContentParameterById(@PathVariable("id") long id, Model model) {

		ContentParameter contentParameter = this.contentParameterService.findById(id);

		model.addAttribute("contentParameter", contentParameter);

		return "output/report/contentParameterView";
	}
	
	@RequestMapping("contentParameter/{id}/details")
	public String viewContentParameterDetailsById(@PathVariable("id") long id, Model model) {

		ContentParameter contentParameter = this.contentParameterService.findById(id);

		model.addAttribute("contentParameter", contentParameter);

		return "output/report/contentParameterDetails";
	}	
    
	
	@RequestMapping(value = "/contentParameter/add", method = RequestMethod.GET)
	public String getContentParameterAddForm(Model model) {

		model.addAttribute("contentParameter", new ContentParameter());

		return "output/report/contentParameterForm";
	}
	
	@RequestMapping("/contentParameter/{id}/edit")
	public String getContentParameterEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("contentParameter", this.contentParameterService.findById(id));
		return "output/report/contentParameterForm";

	}

	@RequestMapping(value = "/contentParameter/save", method = RequestMethod.POST)
	public String saveContentParameterAndRedirectToContentParameterList(@Validated @ModelAttribute("contentParameter") ContentParameter contentParameter, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (contentParameter.getId() == 0) {
			this.contentParameterService.create(contentParameter);
		} else {
			this.contentParameterService.edit(contentParameter);
		}

		return "redirect:/contentParameter/list";

	}

	@RequestMapping("/contentParameter/{id}/remove")
	public String removeContentParameterAndRedirectToContentParameterList(@PathVariable("id") long id) {

		this.contentParameterService.deleteById(id);

		return "redirect:/contentParameter/list";
	}

     

     

}
