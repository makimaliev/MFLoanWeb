package kg.gov.mf.loan.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import kg.gov.mf.loan.admin.sys.model.*;
import kg.gov.mf.loan.admin.sys.service.*;

@Controller
public class ValidationTermController {
	
	@Autowired
    private ValidationTermService validationTermService;
     
    public void setValidationTermService(ValidationTermService rs)
    {
        this.validationTermService = rs;
    }

	@Autowired
    private ObjectFieldService objectFieldService;
     
    public void setObjectFieldService(ObjectFieldService rs)
    {
        this.objectFieldService = rs;
    }
    
    
	@RequestMapping(value = "/validationTerm/list", method = RequestMethod.GET)
	public String listValidationTerms(Model model) {
		model.addAttribute("validationTerm", new ValidationTerm());
		model.addAttribute("validationTermList", this.validationTermService.findAll());

		return "admin/sys/validationTermList";
	}
	
	
	@RequestMapping(value = "/validationTerm/table", method = RequestMethod.GET)
	public String showValidationTermTable(Model model) {
		model.addAttribute("validationTerm", new ValidationTerm());
		model.addAttribute("validationTermList", this.validationTermService.findAll());

		return "admin/sys/validationTermTable";
	}	
	
	@RequestMapping("/validationTerm/{id}/view")
	public String viewValidationTermById(@PathVariable("id") long id, Model model) {

		ValidationTerm validationTerm = this.validationTermService.findById(id);

		model.addAttribute("validationTerm", validationTerm);

		return "admin/sys/validationTermView";
	}
    
	
	@RequestMapping("/validationTerm/{id}/details")
	public String viewValidationTermDetailsById(@PathVariable("id") long id, Model model) {

		ValidationTerm validationTerm = this.validationTermService.findById(id);

		model.addAttribute("validationTerm", validationTerm);

		return "admin/sys/validationTermDetails";
	}	
	
	@RequestMapping(value = "/validationTerm/add", method = RequestMethod.GET)
	public String getValidationTermAddForm(Model model) {

		model.addAttribute("validationTerm", new ValidationTerm());

		return "admin/sys/validationTermForm";
	}
	
	
	
	@RequestMapping(value = "/objectField/{objectFieldId}/validationTerm/add", method = RequestMethod.GET)
	public String getValidationTermAddByObjectFieldIdForm(@PathVariable("objectFieldId") long objectFieldId,Model model) {

		ValidationTerm modelValidationTerm = new ValidationTerm();
		modelValidationTerm.setObjectField(this.objectFieldService.findById(objectFieldId));
		model.addAttribute("validationTerm",modelValidationTerm);

		return "admin/sys/validationTermForm";
	}
	
	
	@RequestMapping("/validationTerm/{id}/edit")
	public String getValidationTermEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("validationTerm", this.validationTermService.findById(id));
		return "admin/sys/validationTermForm";

	}

	@RequestMapping(value = "/validationTerm/save", method = RequestMethod.POST)
	public String saveValidationTermAndRedirectToValidationTermList(@Validated @ModelAttribute("validationTerm") ValidationTerm validationTerm, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (validationTerm.getId() == 0) {
			this.validationTermService.create(validationTerm);
		} else {
			this.validationTermService.edit(validationTerm);
		}

		return "redirect:/objectType/list";

	}

	@RequestMapping("/validationTerm/{id}/remove")
	public String removeValidationTermAndRedirectToValidationTermList(@PathVariable("id") long id) {

		this.validationTermService.deleteById(id);

		return "redirect:/objectType/list";
	}

     

     

}
