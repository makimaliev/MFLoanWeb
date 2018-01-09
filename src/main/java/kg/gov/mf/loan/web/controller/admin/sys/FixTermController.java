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
public class FixTermController {
	
	@Autowired
    private FixTermService fixTermService;
     
    public void setFixTermService(FixTermService rs)
    {
        this.fixTermService = rs;
    }

	@Autowired
    private ObjectTypeService objectTypeService;
     
    public void setObjectTypeService(ObjectTypeService rs)
    {
        this.objectTypeService = rs;
    }
    
    
	@RequestMapping(value = "/fixTerm/list", method = RequestMethod.GET)
	public String listFixTerms(Model model) {
		model.addAttribute("fixTerm", new FixTerm());
		model.addAttribute("fixTermList", this.fixTermService.findAll());

		return "admin/sys/fixTermList";
	}
	
	
	@RequestMapping(value = "/fixTerm/table", method = RequestMethod.GET)
	public String showFixTermTable(Model model) {
		model.addAttribute("fixTerm", new FixTerm());
		model.addAttribute("fixTermList", this.fixTermService.findAll());

		return "admin/sys/fixTermTable";
	}	
	
	@RequestMapping("/fixTerm/{id}/view")
	public String viewFixTermById(@PathVariable("id") long id, Model model) {

		FixTerm fixTerm = this.fixTermService.findById(id);

		model.addAttribute("fixTerm", fixTerm);

		return "admin/sys/fixTermView";
	}
    
	
	@RequestMapping("/fixTerm/{id}/details")
	public String viewFixTermDetailsById(@PathVariable("id") long id, Model model) {

		FixTerm fixTerm = this.fixTermService.findById(id);

		model.addAttribute("fixTerm", fixTerm);

		return "admin/sys/fixTermDetails";
	}	
	
	@RequestMapping(value = "/fixTerm/add", method = RequestMethod.GET)
	public String getFixTermAddForm(Model model) {

		model.addAttribute("fixTerm", new FixTerm());

		return "admin/sys/fixTermForm";
	}
	
	
	
	@RequestMapping(value = "/objectType/{objectTypeId}/fixTerm/add", method = RequestMethod.GET)
	public String getFixTermAddByObjectTypeIdForm(@PathVariable("objectTypeId") long objectTypeId,Model model) {

		FixTerm modelFixTerm = new FixTerm();
		modelFixTerm.setObjectType(this.objectTypeService.findById(objectTypeId));
		model.addAttribute("fixTerm",modelFixTerm);

		return "admin/sys/fixTermForm";
	}
	
	
	@RequestMapping("/fixTerm/{id}/edit")
	public String getFixTermEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("fixTerm", this.fixTermService.findById(id));
		return "admin/sys/fixTermForm";

	}

	@RequestMapping(value = "/fixTerm/save", method = RequestMethod.POST)
	public String saveFixTermAndRedirectToFixTermList(@Validated @ModelAttribute("fixTerm") FixTerm fixTerm, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (fixTerm.getId() == 0) {
			this.fixTermService.create(fixTerm);
		} else {
			this.fixTermService.edit(fixTerm);
		}

		return "redirect:/objectType/list";

	}

	@RequestMapping("/fixTerm/{id}/remove")
	public String removeFixTermAndRedirectToFixTermList(@PathVariable("id") long id) {

		this.fixTermService.deleteById(id);

		return "redirect:/objectType/list";
	}

     

     

}
