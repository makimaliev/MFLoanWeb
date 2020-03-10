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
public class ObjectEventController {
	
	@Autowired
    private ObjectEventService objectEventService;
     
    public void setObjectEventService(ObjectEventService rs)
    {
        this.objectEventService = rs;
    }

	@Autowired
    private ObjectTypeService objectTypeService;
     
    public void setObjectTypeService(ObjectTypeService rs)
    {
        this.objectTypeService = rs;
    }
    
    
	@RequestMapping(value = "/objectEvent/list", method = RequestMethod.GET)
	public String listObjectEvents(Model model) {
		model.addAttribute("objectEvent", new ObjectEvent());
		model.addAttribute("objectEventList", this.objectEventService.findAll());

		return "admin/sys/objectEventList";
	}
	
	
	@RequestMapping(value = "/objectEvent/table", method = RequestMethod.GET)
	public String showObjectEventTable(Model model) {
		model.addAttribute("objectEvent", new ObjectEvent());
		model.addAttribute("objectEventList", this.objectEventService.findAll());

		return "admin/sys/objectEventTable";
	}	
	
	@RequestMapping("/objectEvent/{id}/view")
	public String viewObjectEventById(@PathVariable("id") long id, Model model) {

		ObjectEvent objectEvent = this.objectEventService.findById(id);

		model.addAttribute("objectEvent", objectEvent);

		return "admin/sys/objectEventView";
	}
    
	
	@RequestMapping("/objectEvent/{id}/details")
	public String viewObjectEventDetailsById(@PathVariable("id") long id, Model model) {

		ObjectEvent objectEvent = this.objectEventService.findById(id);

		model.addAttribute("objectEvent", objectEvent);

		return "admin/sys/objectEventDetails";
	}	
	
	@RequestMapping(value = "/objectEvent/add", method = RequestMethod.GET)
	public String getObjectEventAddForm(Model model) {

		model.addAttribute("objectEvent", new ObjectEvent());

		return "admin/sys/objectEventForm";
	}
	
	
	
	@RequestMapping(value = "/objectType/{objectTypeId}/objectEvent/add", method = RequestMethod.GET)
	public String getObjectEventAddByObjectTypeIdForm(@PathVariable("objectTypeId") long objectTypeId,Model model) {

		ObjectEvent modelObjectEvent = new ObjectEvent();
		modelObjectEvent.setObjectType(this.objectTypeService.findById(objectTypeId));
		model.addAttribute("objectEvent",modelObjectEvent);

		return "admin/sys/objectEventForm";
	}
	
	
	@RequestMapping("/objectEvent/{id}/edit")
	public String getObjectEventEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("objectEvent", this.objectEventService.findById(id));
		return "admin/sys/objectEventForm";

	}

	@RequestMapping(value = "/objectEvent/save", method = RequestMethod.POST)
	public String saveObjectEventAndRedirectToObjectEventList(@Validated @ModelAttribute("objectEvent") ObjectEvent objectEvent, BindingResult result) {

		if (result.hasErrors()) {

		} else if (objectEvent.getId() == 0) {
			this.objectEventService.create(objectEvent);
		} else {
			this.objectEventService.edit(objectEvent);
		}

		return "redirect:/objectType/list";

	}

	@RequestMapping("/objectEvent/{id}/remove")
	public String removeObjectEventAndRedirectToObjectEventList(@PathVariable("id") long id) {

		this.objectEventService.deleteById(id);

		return "redirect:/objectType/list";
	}

     

     

}
