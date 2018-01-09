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
public class IdentityDocGivenByController {
	
	@Autowired
    private IdentityDocGivenByService identityDocGivenByService;
     
    public void setIdentityDocGivenByService(IdentityDocGivenByService rs)
    {
        this.identityDocGivenByService = rs;
    }

	@RequestMapping(value = "/identityDocGivenBy/list", method = RequestMethod.GET)
	public String listIdentityDocGivenBys(Model model) {
		model.addAttribute("identityDocGivenBy", new IdentityDocGivenBy());
		model.addAttribute("identityDocGivenByList", this.identityDocGivenByService.findAll());

		return "admin/org/identityDocGivenByList";
	}
	
	@RequestMapping("identityDocGivenBy/{id}/view")
	public String viewIdentityDocGivenByById(@PathVariable("id") long id, Model model) {

		IdentityDocGivenBy identityDocGivenBy = this.identityDocGivenByService.findById(id);

		model.addAttribute("identityDocGivenBy", identityDocGivenBy);

		return "admin/org/identityDocGivenByView";
	}
	
	@RequestMapping("identityDocGivenBy/{id}/details")
	public String viewIdentityDocGivenByDetailsById(@PathVariable("id") long id, Model model) {

		IdentityDocGivenBy identityDocGivenBy = this.identityDocGivenByService.findById(id);

		model.addAttribute("identityDocGivenBy", identityDocGivenBy);

		return "admin/org/identityDocGivenByDetails";
	}	
    
	
	@RequestMapping(value = "/identityDocGivenBy/add", method = RequestMethod.GET)
	public String getIdentityDocGivenByAddForm(Model model) {

		model.addAttribute("identityDocGivenBy", new IdentityDocGivenBy());

		return "admin/org/identityDocGivenByForm";
	}

	@RequestMapping("/identityDocGivenBy/{id}/edit")
	public String getIdentityDocGivenByEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("identityDocGivenBy", this.identityDocGivenByService.findById(id));
		return "admin/org/identityDocGivenByForm";

	}

	@RequestMapping(value = "/identityDocGivenBy/save", method = RequestMethod.POST)
	public String saveIdentityDocGivenByAndRedirectToIdentityDocGivenByList(@Validated @ModelAttribute("identityDocGivenBy") IdentityDocGivenBy identityDocGivenBy, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (identityDocGivenBy.getId() == 0) {
			this.identityDocGivenByService.create(identityDocGivenBy);
		} else {
			this.identityDocGivenByService.edit(identityDocGivenBy);
		}

		return "redirect:/identityDocGivenBy/list";

	}

	@RequestMapping("/identityDocGivenBy/{id}/remove")
	public String removeIdentityDocGivenByAndRedirectToIdentityDocGivenByList(@PathVariable("id") long id) {

		this.identityDocGivenByService.deleteById(id);

		return "redirect:/identityDocGivenBy/list";
	}

     

     

}
