package kg.gov.mf.loan.web.controller.admin.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;

@Controller
public class EmploymentHistoryEventTypeController {
	
	@Autowired
    private EmploymentHistoryEventTypeService employmentHistoryEventTypeService;
     
    public void setEmploymentHistoryEventTypeService(EmploymentHistoryEventTypeService rs)
    {
        this.employmentHistoryEventTypeService = rs;
    }

	@Autowired
    private OrganizationService organizationService;
     
    public void setOrganizationService(OrganizationService rs)
    {
        this.organizationService = rs;
    }
    
    
	@RequestMapping(value = "/employmentHistoryEventType/list", method = RequestMethod.GET)
	public String listEmploymentHistoryEventTypes(Model model) {
		model.addAttribute("employmentHistoryEventType", new EmploymentHistoryEventType());
		model.addAttribute("employmentHistoryEventTypeList", this.employmentHistoryEventTypeService.findAll());

		return "admin/org/employmentHistoryEventTypeList";
	}
	
	
	@RequestMapping(value = "/employmentHistoryEventType/table", method = RequestMethod.GET)
	public String showEmploymentHistoryEventTypeTable(Model model) {
		model.addAttribute("employmentHistoryEventType", new EmploymentHistoryEventType());
		model.addAttribute("employmentHistoryEventTypeList", this.employmentHistoryEventTypeService.findAll());

		return "admin/org/employmentHistoryEventTypeTable";
	}	
	
	@RequestMapping("/employmentHistoryEventType/{id}/view")
	public String viewEmploymentHistoryEventTypeById(@PathVariable("id") long id, Model model) {

		EmploymentHistoryEventType employmentHistoryEventType = this.employmentHistoryEventTypeService.findById(id);

		model.addAttribute("employmentHistoryEventType", employmentHistoryEventType);

		return "admin/org/employmentHistoryEventTypeView";
	}
    
	
	@RequestMapping(value = "/employmentHistoryEventType/add", method = RequestMethod.GET)
	public String getEmploymentHistoryEventTypeAddForm(Model model) {

		model.addAttribute("employmentHistoryEventType", new EmploymentHistoryEventType());

		return "admin/org/employmentHistoryEventTypeForm";
	}
	
	
	@RequestMapping("/employmentHistoryEventType/{id}/edit")
	public String getEmploymentHistoryEventTypeEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("employmentHistoryEventType", this.employmentHistoryEventTypeService.findById(id));
		return "admin/org/employmentHistoryEventTypeForm";

	}

	@RequestMapping(value = "/employmentHistoryEventType/save", method = RequestMethod.POST)
	public String saveEmploymentHistoryEventTypeAndRedirectToEmploymentHistoryEventTypeList(@Validated @ModelAttribute("employmentHistoryEventType") EmploymentHistoryEventType employmentHistoryEventType, BindingResult result) {

		if (result.hasErrors()) {

		} else if (employmentHistoryEventType.getId() == 0) {
			this.employmentHistoryEventTypeService.create(employmentHistoryEventType);
		} else {
			this.employmentHistoryEventTypeService.edit(employmentHistoryEventType);
		}

		return "redirect:/employmentHistoryEventType/list";

	}

	@RequestMapping("/employmentHistoryEventType/{id}/remove")
	public String removeEmploymentHistoryEventTypeAndRedirectToEmploymentHistoryEventTypeList(@PathVariable("id") long id) {

		this.employmentHistoryEventTypeService.deleteById(id);

		return "redirect:/employmentHistoryEventType/list";
	}

     

     

}
