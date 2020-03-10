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
public class ContactController {
	
	@Autowired
    private ContactService contactService;
     
    public void setContactService(ContactService rs)
    {
        this.contactService = rs;
    }

	@RequestMapping(value = "/contact/list", method = RequestMethod.GET)
	public String listContacts(Model model) {
		model.addAttribute("contact", new Contact());
		model.addAttribute("contactList", this.contactService.findAll());

		return "admin/org/contactList";
	}
	
	@RequestMapping("contact/{id}/view")
	public String viewContactById(@PathVariable("id") long id, Model model) {

		Contact contact = this.contactService.findById(id);

		model.addAttribute("contact", contact);

		return "admin/org/contactView";
	}
	
	@RequestMapping("contact/{id}/details")
	public String viewContactDetailsById(@PathVariable("id") long id, Model model) {

		Contact contact = this.contactService.findById(id);

		model.addAttribute("contact", contact);

		return "admin/org/contactDetails";
	}	
    
	
	@RequestMapping(value = "/contact/add", method = RequestMethod.GET)
	public String getContactAddForm(Model model) {

		model.addAttribute("contact", new Contact());

		return "admin/org/contactForm";
	}

	@RequestMapping("/contact/{id}/edit")
	public String getContactEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("contact", this.contactService.findById(id));
		return "admin/org/contactForm";

	}

	@RequestMapping(value = "/contact/save", method = RequestMethod.POST)
	public String saveContactAndRedirectToContactList(@Validated @ModelAttribute("contact") Contact contact, BindingResult result) {

		if (result.hasErrors()) {

		} else if (contact.getId() == 0) {
			this.contactService.create(contact);
		} else {
			this.contactService.edit(contact);
		}

		return "redirect:/contact/list";

	}

	@RequestMapping("/contact/{id}/remove")
	public String removeContactAndRedirectToContactList(@PathVariable("id") long id) {

		this.contactService.deleteById(id);

		return "redirect:/contact/list";
	}

     

     

}
