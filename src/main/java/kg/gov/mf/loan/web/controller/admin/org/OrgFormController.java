package kg.gov.mf.loan.web.controller.admin.org;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.admin.org.converter.OrgFormFormatter;
import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;

@Controller
public class OrgFormController {
	
	@Autowired
    private OrgFormService orgFormService;
     
    public void setOrgFormService(OrgFormService rs)
    {
        this.orgFormService = rs;
    }

	@Autowired
    private OrganizationService organizationService;
     
    public void setOrganizationService(OrganizationService rs)
    {
        this.organizationService = rs;
    }
    
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		
	    binder.addCustomFormatter(new OrgFormFormatter(), OrgForm.class);
	}    
    
    
	@RequestMapping(value = "/orgForm/list", method = RequestMethod.GET)
	public String listOrgForms(Model model) {

    	OrgForm newOrgForm = new OrgForm();
    	newOrgForm.setEnabled(true);

		model.addAttribute("orgForm", newOrgForm);
		model.addAttribute("orgFormList", this.orgFormService.findAll());

		return "admin/org/orgFormList";
	}
	
	
	@RequestMapping(value = "/orgForm/table", method = RequestMethod.GET)
	public String showOrgFormTable(Model model) {
		model.addAttribute("orgForm", new OrgForm());
		model.addAttribute("orgFormList", this.orgFormService.findAll());

		return "admin/org/orgFormTable";
	}	
	
	@RequestMapping("/orgForm/{id}/view")
	public String viewOrgFormById(@PathVariable("id") long id, Model model) {

		OrgForm orgForm = this.orgFormService.findById(id);

		model.addAttribute("orgForm", orgForm);

		return "admin/org/orgFormView";
	}
    
	
	@RequestMapping(value = "/orgForm/add", method = RequestMethod.GET)
	public String getOrgFormAddForm(Model model) {


		OrgForm newOrgForm = new OrgForm();
		newOrgForm.setEnabled(true);

		model.addAttribute("orgForm", newOrgForm);

		return "admin/org/orgFormForm";
	}
	
	
	@RequestMapping("/orgForm/{id}/edit")
	public String getOrgFormEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("orgForm", this.orgFormService.findById(id));
		return "admin/org/orgFormForm";

	}

	@RequestMapping(value = "/orgForm/save", method = RequestMethod.POST)
	public String saveOrgFormAndRedirectToOrgFormList(@Validated @ModelAttribute("orgForm") OrgForm orgForm, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (orgForm.getId() == 0) {
			this.orgFormService.create(orgForm);
		} else {
			this.orgFormService.edit(orgForm);
		}

		return "redirect:/orgForm/list";

	}

	@RequestMapping("/orgForm/{id}/remove")
	public String removeOrgFormAndRedirectToOrgFormList(@PathVariable("id") long id) {

		this.orgFormService.deleteById(id);

		return "redirect:/orgForm/list";
	}

     

     

}
