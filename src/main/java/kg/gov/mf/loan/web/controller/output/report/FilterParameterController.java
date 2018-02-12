package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.output.report.model.FilterParameter;
import kg.gov.mf.loan.output.report.service.FilterParameterService;
import kg.gov.mf.loan.output.report.service.ContentParameterService;
import kg.gov.mf.loan.output.report.service.ObjectListService;
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
public class FilterParameterController {
	
	@Autowired
    private FilterParameterService filterParameterService;
	
	@Autowired
    private ObjectListService objectListService;

	@Autowired
	private ContentParameterService contentParameterService;



	@Autowired
    private ReportTemplateService reportTemplateService;
	
     
    public void setFilterParameterService(FilterParameterService rs)
    {
        this.filterParameterService = rs;
    }

	@RequestMapping(value = "/filterParameter/list", method = RequestMethod.GET)
	public String listFilterParameters(Model model) {
		model.addAttribute("filterParameter", new FilterParameter());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("objectListList", this.objectListService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		return "output/report/filterParameterList";
	}
	
	@RequestMapping("filterParameter/{id}/view")
	public String viewFilterParameterById(@PathVariable("id") long id, Model model) {

		FilterParameter filterParameter = this.filterParameterService.findById(id);

		model.addAttribute("filterParameter", filterParameter);
		model.addAttribute("objectListList", this.objectListService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		return "output/report/filterParameterView";
	}
	
	@RequestMapping("filterParameter/{id}/details")
	public String viewFilterParameterDetailsById(@PathVariable("id") long id, Model model) {

		FilterParameter filterParameter = this.filterParameterService.findById(id);

		model.addAttribute("filterParameter", filterParameter);
		model.addAttribute("objectListList", this.objectListService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		return "output/report/filterParameterDetails";
	}	
    
	
	@RequestMapping(value = "/filterParameter/add", method = RequestMethod.GET)
	public String getFilterParameterAddForm(Model model) {

		model.addAttribute("filterParameter", new FilterParameter());
		model.addAttribute("objectListList", this.objectListService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		return "output/report/filterParameterForm";
	}
	
	@RequestMapping("/filterParameter/{id}/edit")
	public String getFilterParameterEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("filterParameter", this.filterParameterService.findById(id));
		model.addAttribute("objectListList", this.objectListService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());

		return "output/report/filterParameterForm";

	}

	@RequestMapping(value = "/filterParameter/save", method = RequestMethod.POST)
	public String saveFilterParameterAndRedirectToFilterParameterList(@Validated @ModelAttribute("filterParameter") FilterParameter filterParameter, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (filterParameter.getId() == 0) {
			this.filterParameterService.create(filterParameter);
		} else {
			this.filterParameterService.edit(filterParameter);
		}

		return "redirect:/filterParameter/list";

	}

	@RequestMapping("/filterParameter/{id}/remove")
	public String removeFilterParameterAndRedirectToFilterParameterList(@PathVariable("id") long id) {

		this.filterParameterService.deleteById(id);

		return "redirect:/filterParameter/list";
	}

     

     

}
