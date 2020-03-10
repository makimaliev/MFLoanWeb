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
public class IdentityDocTypeController {
	
	@Autowired
    private IdentityDocTypeService identityDocTypeService;
     
    public void setIdentityDocTypeService(IdentityDocTypeService rs)
    {
        this.identityDocTypeService = rs;
    }

	@RequestMapping(value = "/identityDocType/list", method = RequestMethod.GET)
	public String listIdentityDocTypes(Model model) {
		model.addAttribute("identityDocType", new IdentityDocType());
		model.addAttribute("identityDocTypeList", this.identityDocTypeService.findAll());

		return "admin/org/identityDocTypeList";
	}
	
	@RequestMapping("identityDocType/{id}/view")
	public String viewIdentityDocTypeById(@PathVariable("id") long id, Model model) {

		IdentityDocType identityDocType = this.identityDocTypeService.findById(id);

		model.addAttribute("identityDocType", identityDocType);

		return "admin/org/identityDocTypeView";
	}
	
	@RequestMapping("identityDocType/{id}/details")
	public String viewIdentityDocTypeDetailsById(@PathVariable("id") long id, Model model) {

		IdentityDocType identityDocType = this.identityDocTypeService.findById(id);

		model.addAttribute("identityDocType", identityDocType);

		return "admin/org/identityDocTypeDetails";
	}	
    
	
	@RequestMapping(value = "/identityDocType/add", method = RequestMethod.GET)
	public String getIdentityDocTypeAddForm(Model model) {

		model.addAttribute("identityDocType", new IdentityDocType());

		return "admin/org/identityDocTypeForm";
	}

	@RequestMapping("/identityDocType/{id}/edit")
	public String getIdentityDocTypeEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("identityDocType", this.identityDocTypeService.findById(id));
		return "admin/org/identityDocTypeForm";

	}

	@RequestMapping(value = "/identityDocType/save", method = RequestMethod.POST)
	public String saveIdentityDocTypeAndRedirectToIdentityDocTypeList(@Validated @ModelAttribute("identityDocType") IdentityDocType identityDocType, BindingResult result) {

		if (result.hasErrors()) {

		} else if (identityDocType.getId() == 0) {
			this.identityDocTypeService.create(identityDocType);
		} else {
			this.identityDocTypeService.edit(identityDocType);
		}

		return "redirect:/identityDocType/list";

	}

	@RequestMapping("/identityDocType/{id}/remove")
	public String removeIdentityDocTypeAndRedirectToIdentityDocTypeList(@PathVariable("id") long id) {

		this.identityDocTypeService.deleteById(id);

		return "redirect:/identityDocType/list";
	}

     

     

}
