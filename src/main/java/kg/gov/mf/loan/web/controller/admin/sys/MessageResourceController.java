package kg.gov.mf.loan.web.controller.admin.sys;

import kg.gov.mf.loan.admin.sys.model.MessageResource;
import kg.gov.mf.loan.admin.sys.service.MessageResourceService;
import kg.gov.mf.loan.admin.sys.service.ObjectTypeService;
import kg.gov.mf.loan.web.config.DatabaseDrivenMessageSource;
import kg.gov.mf.loan.web.fetchModels.MessageResourceMetaModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DelegatingMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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

     

//	********************************************************************************************
     //rest
//	********************************************************************************************

	@PostMapping("/api/messageResources")
	@ResponseBody
	public MessageResourceMetaModel getMessageResourceList(@RequestParam Map<String,String> datatable){

		String pageStr = datatable.get("datatable[pagination][page]");
		String perPageStr = datatable.get("datatable[pagination][perpage]");
		String sortStr = datatable.get("datatable[sort][sort]");
		String sortField = datatable.get("datatable[sort][field]");

		boolean isSearch= datatable.containsKey("datatable[query][generalSearch]");
		if(isSearch){

		}

		Integer page = Integer.parseInt(pageStr);
		Integer perPage =Integer.parseInt(perPageStr);
		Integer offset = (page-1)*perPage;


		List<MessageResource> all = null;
		if(isSearch){
			String searchStr = datatable.get("datatable[query][generalSearch]");
			all = messageResourceService.findAll(100,searchStr);
		}
		else{
			all = messageResourceService.findAll(100);
		}

		BigInteger count= BigInteger.valueOf(0);
		if (all.size()==100){
			count=BigInteger.valueOf(100);
		}
		else{
			count=BigInteger.valueOf(all.size());

		}

		Meta meta=new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

		MessageResourceMetaModel metaModel=new MessageResourceMetaModel();
		metaModel.setMeta(meta);

		if(all.size()>perPage){
			metaModel.setData(all.subList(offset,perPage+offset));
		}
		else{
			metaModel.setData(all);
		}

//		metaModel.setData(all);


		return metaModel;
	}

}
