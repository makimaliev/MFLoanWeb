package kg.gov.mf.loan.web.controller.admin.sys;

import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AttachmentController {
	
	@Autowired
    private AttachmentService attachmentService;
	
    public void setAttachmentService(AttachmentService rs)
    {
        this.attachmentService = rs;
    }

    
	@Autowired
    private InformationService informationService;
	
    public void setInformationService(InformationService rs)
    {
        this.informationService = rs;
    }
    
    
	@RequestMapping(value = "/attachment/list", method = RequestMethod.GET)
	public String listAttachments(Model model) {
		
		model.addAttribute("attachment", new Attachment());
		model.addAttribute("attachmentList", this.attachmentService.findAll());
		return "admin/sys/attachmentList";
	}
	
	@RequestMapping("/attachment/{id}/view")
	public String viewAttachmentById(@PathVariable("id") long id, Model model) {

		Attachment attachment = this.attachmentService.findById(id);

		model.addAttribute("attachment", attachment);


		return "admin/sys/attachmentView";
	}
	
	@RequestMapping("/attachment/{id}/details")
	public String viewAttachmentDetailsById(@PathVariable("id") long id, Model model) {

		Attachment attachment = this.attachmentService.findById(id);
		model.addAttribute("documents",attachment.getDocumentIds());
		model.addAttribute("attachment", attachment);


		return "admin/sys/attachmentDetails";
	}	
    
	
	@RequestMapping(value = "/attachment/add", method = RequestMethod.GET)
	public String getAttachmentAddForm(Model model) {

		model.addAttribute("attachment", new Attachment());
		model.addAttribute("attachmentList", this.attachmentService.findAll());		

		return "admin/sys/attachmentForm";
	}
	

	@RequestMapping(value = "/information/{informationId}/attachment/add", method = RequestMethod.GET)
	public String getAttachmentAddByInformationIdForm(@PathVariable("informationId") long informationId,Model model) {

		Attachment modelAttachment = new Attachment();
		modelAttachment.setInformation(this.informationService.findById(informationId));
		model.addAttribute("attachment",modelAttachment);

		return "admin/sys/attachmentForm";
	}
	
	
	
	@RequestMapping(value = "/attachment/add/{informationId}", method = RequestMethod.GET)
	public String getAttachmentAddFormWithParentAttachment(@PathVariable("informationId") long informationId,Model model) {

		Attachment modelAttachment = new Attachment();
		modelAttachment.setInformation(this.informationService.findById(informationId));
		model.addAttribute("attachment",modelAttachment);

		return "admin/sys/attachmentForm";
	}
	
	

	@RequestMapping("/attachment/{id}/edit")
	public String getAttachmentEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("attachment", this.attachmentService.findById(id));
		model.addAttribute("attachmentList", this.attachmentService.findAll());		
	
		
		return "admin/sys/attachmentForm";

	}


	
	
	@RequestMapping(value = "/attachment/save", method = RequestMethod.POST)
	public ModelAndView saveAttachmentAndRedirectToInformationViewPage(@Validated @ModelAttribute("attachment") Attachment attachment, BindingResult result,ModelMap model) {

		
		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (attachment.getId() == 0) {
			this.attachmentService.create(attachment);
		} else {
			this.attachmentService.edit(attachment);
		}
		
		String url = "/attachment/"+attachment.getId()+"/details";

        return new ModelAndView("redirect:"+url, model);

	}	
	

	@RequestMapping("/attachment/{id}/remove")
	public String removeAttachmentAndRedirectToAttachmentList(@PathVariable("id") long id) {

		this.attachmentService.deleteById(id);

		return "redirect:/attachment/list";
	}

     

     

}
