package kg.gov.mf.loan.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DelegatingMessageSource;
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
import kg.gov.mf.loan.web.config.DatabaseDrivenMessageSource;

@Controller
public class MessageResourceController {
	
	@Autowired
    private MessageResourceService messageResourceService;
     
    public void setMessageResourceService(MessageResourceService rs)
    {
        this.messageResourceService = rs;
    }

	@Autowired
    private ObjectTypeService objectTypeService;
     
    public void setObjectTypeService(ObjectTypeService rs)
    {
        this.objectTypeService = rs;
    }
    
    @Autowired
    @Qualifier("messageSource")
    private MessageSource messages;
     
    private void reloadDatabaseMessages() {
        if (messages instanceof DatabaseDrivenMessageSource) {
            ((DatabaseDrivenMessageSource)messages).reload();
        } else if (messages instanceof DelegatingMessageSource) {
            DelegatingMessageSource myMessage = ((DelegatingMessageSource)messages);
            if (myMessage.getParentMessageSource()!=null && myMessage.getParentMessageSource() instanceof DatabaseDrivenMessageSource) {
                ((DatabaseDrivenMessageSource) myMessage.getParentMessageSource()).reload();
            }
        }
    }
    
	@RequestMapping(value = "/messageResource/list", method = RequestMethod.GET)
	public String listMessageResources(Model model) {
		model.addAttribute("messageResource", new MessageResource());
		model.addAttribute("messageResourceList", this.messageResourceService.findAll());

		return "admin/sys/messageResourceList";
	}
	
	
	@RequestMapping(value = "/messageResource/table", method = RequestMethod.GET)
	public String showMessageResourceTable(Model model) {
		model.addAttribute("messageResource", new MessageResource());
		model.addAttribute("messageResourceList", this.messageResourceService.findAll());

		return "admin/sys/messageResourceTable";
	}	
	
	@RequestMapping("/messageResource/{id}/view")
	public String viewMessageResourceById(@PathVariable("id") long id, Model model) {

		MessageResource messageResource = this.messageResourceService.findById(id);

		model.addAttribute("messageResource", messageResource);

		return "admin/sys/messageResourceView";
	}
    
	
	@RequestMapping("/messageResource/{id}/details")
	public String viewMessageResourceDetailsById(@PathVariable("id") long id, Model model) {

		MessageResource messageResource = this.messageResourceService.findById(id);

		model.addAttribute("messageResource", messageResource);

		return "admin/sys/messageResourceDetails";
	}	
	
	@RequestMapping(value = "/messageResource/add", method = RequestMethod.GET)
	public String getMessageResourceAddForm(Model model) {

		model.addAttribute("messageResource", new MessageResource());

		return "admin/sys/messageResourceForm";
	}
	
	
	
	@RequestMapping(value = "/objectType/{objectTypeId}/messageResource/add", method = RequestMethod.GET)
	public String getMessageResourceAddByObjectTypeIdForm(@PathVariable("objectTypeId") long objectTypeId,Model model) {

		MessageResource modelMessageResource = new MessageResource();
		
		
		model.addAttribute("messageResource",modelMessageResource);

		return "admin/sys/messageResourceForm";
	}
	
	
	@RequestMapping("/messageResource/{id}/edit")
	public String getMessageResourceEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("messageResource", this.messageResourceService.findById(id));
		return "admin/sys/messageResourceForm";

	}

	@RequestMapping(value = "/messageResource/save", method = RequestMethod.POST)
	public String saveMessageResourceAndRedirectToMessageResourceList(@Validated @ModelAttribute("messageResource") MessageResource messageResource, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (messageResource.getId() == 0) {
			this.messageResourceService.create(messageResource);
		} else {
			this.messageResourceService.edit(messageResource);
		}
		
		this.reloadDatabaseMessages();

		return "redirect:/messageResource/list";

	}

	@RequestMapping("/messageResource/{id}/remove")
	public String removeMessageResourceAndRedirectToMessageResourceList(@PathVariable("id") long id) {

		this.messageResourceService.deleteById(id);

		return "redirect:/messageResource/list";
	}

     

     

}
