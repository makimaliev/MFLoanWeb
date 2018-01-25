package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackage;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentRegisteredBy;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentState;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentRegisteredByService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentStateService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class EntityDocumentController {
	
	@Autowired
	EntityDocumentStateService edStateService;
	
	@Autowired
	EntityDocumentRegisteredByService edRBService;
	
	@Autowired
	DocumentPackageService dpService;
	
	@Autowired
	EntityDocumentService edService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/{edId}/view"})
    public String viewEntity(
    		ModelMap model, 
    		@PathVariable("orderId")Long orderId, 
    		@PathVariable("listId")Long listId, 
    		@PathVariable("entityId")Long entityId,
    		@PathVariable("dpId")Long dpId,
    		@PathVariable("edId")Long edId) {

		EntityDocument ed = edService.findById(edId);
        model.addAttribute("ed", ed);
        
        model.addAttribute("orderId", orderId);
        model.addAttribute("listId", listId);
        model.addAttribute("entityId", entityId);
        model.addAttribute("dpId", dpId);
        model.addAttribute("edId", edId);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/entitydocument/view";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/{edId}/save", method=RequestMethod.GET)
	public String formEntityDocument(
			ModelMap model, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId, 
			@PathVariable("entityId")Long entityId,
			@PathVariable("dpId")Long dpId,
			@PathVariable("edId")Long edId)
	{
		if(edId == 0)
		{
			model.addAttribute("ed", new EntityDocument());
		}
			
		
		if(edId > 0)
		{
			model.addAttribute("ed", edService.findById(edId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("listId", listId);
		model.addAttribute("entityId", entityId);
		model.addAttribute("dpId", dpId);
		
		List<EntityDocumentState> states = edStateService.findAll();
        model.addAttribute("states", states);
		
		List<EntityDocumentRegisteredBy> rBs = edRBService.findAll();
		model.addAttribute("rBs", rBs);
		
		return "/manage/order/entitylist/entity/documentpackage/entitydocument/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/save", method=RequestMethod.POST)
	public String saveEntityDocument(
			EntityDocument doc, 
			long rbId,
			long stateId,
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId,
			@PathVariable("entityId")Long entityId,
			@PathVariable("dpId")Long dpId,
			ModelMap model)
	{
		DocumentPackage dPackage = dpService.findById(dpId);
		
		if(doc != null && doc.getId() == 0)
		{
			EntityDocument newDoc = new EntityDocument(
					doc.getName(),
					doc.getCompletedBy(),
					doc.getCompletedDate(),
					doc.getCompletedDescription(),
					doc.getApprovedBy(),
					doc.getApprovedDate(),
					doc.getApprovedDescription(),
					doc.getRegisteredNumber(),
					doc.getRegisteredDate(),
					doc.getRegisteredDescription(),
					edRBService.findById(rbId),
					edStateService.findById(stateId));
			newDoc.setDocumentPackage(dPackage);
			edService.save(newDoc);
		}
			
		if(doc != null && doc.getId() > 0)
		{
			doc.setRegisteredBy(edRBService.findById(rbId));
			doc.setEntityDocumentState(edStateService.findById(stateId));
			edService.update(doc);
		}
			
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/delete", method=RequestMethod.POST)
    public String deleteDocumentPackage(long id, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId, @PathVariable("dpId")Long dpId) {
		if(id > 0)
			edService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/view";
    }

	@RequestMapping(value = { "/manage/order/entitylist/entity/documentpackage/entitydocument/state/list" }, method = RequestMethod.GET)
    public String listEDStates(ModelMap model) {
 
		List<EntityDocumentState> states = edStateService.findAll();
		model.addAttribute("states", states);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/entitydocument/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/state/{stateId}/save", method=RequestMethod.GET)
	public String formEDState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("edState", new EntityDocumentState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("edState", edStateService.findById(stateId));
		}
		return "/manage/order/entitylist/entity/documentpackage/entitydocument/state/save";
	}	
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/state/save", method=RequestMethod.POST)
    public String saveEntityDocumentState(EntityDocumentState state, ModelMap model) {
		if(state != null && state.getId() == 0)
			edStateService.save(new EntityDocumentState(state.getName()));
		
		if(state != null && state.getId() > 0)
			edStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/documentpackage/entitydocument/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/state/delete", method=RequestMethod.POST)
    public String deleteEntityDocumentState(long id) {
		if(id > 0)
			edStateService.deleteById(id);
		return "redirect:" + "/manage/order/entitylist/entity/documentpackage/entitydocument/state/list";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/list" }, method = RequestMethod.GET)
    public String listEDRBs(ModelMap model) {
 
		List<EntityDocumentRegisteredBy> rBs = edRBService.findAll();
		model.addAttribute("rBs", rBs);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/{rbId}/save", method=RequestMethod.GET)
	public String formEDRB(ModelMap model, @PathVariable("rbId")Long rbId)
	{
		if(rbId == 0)
		{
			model.addAttribute("edRB", new EntityDocumentRegisteredBy());
		}
		
		if(rbId > 0)
		{
			model.addAttribute("edRB", edRBService.findById(rbId));
		}
		return "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/save", method=RequestMethod.POST)
    public String saveEntityDocumentRegisteredBy(EntityDocumentRegisteredBy rb,	ModelMap model) {
		if(rb != null && rb.getId() == 0)
			edRBService.save(new EntityDocumentRegisteredBy(rb.getName()));
		
		if(rb != null && rb.getId() > 0)
			edRBService.update(rb);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/delete", method=RequestMethod.POST)
    public String deleteEntityDocumentRegisteredBy(long id) {
		if(id > 0)
			edRBService.deleteById(id);
		return "redirect:" + "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/list";
    }
	
}
