package kg.gov.mf.loan.web.controller.manage;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackage;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackageState;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackageType;
import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.manage.model.entity.AppliedEntityState;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentRegisteredBy;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentState;
import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityList;
import kg.gov.mf.loan.manage.model.orderdocument.OrderDocument;
import kg.gov.mf.loan.manage.model.orderdocumentpackage.OrderDocumentPackage;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageStateService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageTypeService;
import kg.gov.mf.loan.manage.service.entity.AppliedEntityService;
import kg.gov.mf.loan.manage.service.entity.AppliedEntityStateService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentRegisteredByService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentStateService;
import kg.gov.mf.loan.manage.service.entitylist.AppliedEntityListService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class AppliedEntityController {
	
	@Autowired
	AppliedEntityStateService entityStateService;
	
	@Autowired
	AppliedEntityService entityService;
	
	@Autowired
	AppliedEntityListService listService;
	
	@Autowired
	DocumentPackageStateService dpStateService;
	
	@Autowired
	DocumentPackageTypeService dpTypeService;
	
	@Autowired
	DocumentPackageService dpService;
	
	@Autowired
	CreditOrderService orderService;
	
	@Autowired
	EntityDocumentStateService edStateService;
	
	@Autowired
	EntityDocumentRegisteredByService edRBService;
	
	@Autowired
	EntityDocumentService edService;
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/view"})
    public String viewEntity(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId) {

		AppliedEntity entity = entityService.findById(entityId);
        model.addAttribute("entity", entity);
        
        model.addAttribute("dPackages", entity.getDocumentPackage());
        
        model.addAttribute("orderId", orderId);
        model.addAttribute("listId", listId);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/view";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/save", method=RequestMethod.GET)
	public String formeAppliedEntityList(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId)
	{
		if(entityId == 0)
		{
			model.addAttribute("entity", new AppliedEntity());
		}
			
		
		if(entityId > 0)
		{
			model.addAttribute("entity", entityService.findById(entityId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("listId", listId);
		List<AppliedEntityState> states = entityStateService.findAll();
        model.addAttribute("states", states);
			
		return "/manage/order/entitylist/entity/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/save", method=RequestMethod.POST)
	public String saveAppliedEntity(
			AppliedEntity entity, 
			long stateId, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId, 
			ModelMap model)
	{
		AppliedEntityList list = listService.findById(listId);
		
		if(entity != null && entity.getId() == 0)
		{
			AppliedEntity newEntity = new AppliedEntity(entity.getName(), entityStateService.findById(stateId));
			newEntity.setAppliedEntityList(list);
			entityService.save(newEntity);
			
			addPackagesAndDocuments(orderId, newEntity);
			
		}
			
		if(entity != null && entity.getId() > 0)
		{
			entity.setAppliedEntityState(entityStateService.findById(stateId));
			entityService.update(entity);
		}
			
			return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/delete", method=RequestMethod.POST)
    public String deleteAppliedEntity(long id, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId) {
		if(id > 0)
			entityService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/view";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/entity/state/list" }, method = RequestMethod.GET)
    public String listAppliedEntityStates(ModelMap model) {
 
		List<AppliedEntityState> states = entityStateService.findAll();
		model.addAttribute("states", states);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/state/{stateId}/save", method=RequestMethod.GET)
	public String formEState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("eState", new AppliedEntityState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("eState", entityStateService.findById(stateId));
		}
		return "/manage/order/entitylist/entity/state/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/entity/state/save", method=RequestMethod.POST)
    public String saveAppliedEntityState(AppliedEntityState state, ModelMap model) {
		if(state != null && state.getId() == 0)
			entityStateService.save(new AppliedEntityState(state.getName()));
		
		if(state != null && state.getId() > 0)
			entityStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/state/delete", method=RequestMethod.POST)
    public String deleteAppliedEntityState(long id) {
		if(id > 0)
			entityStateService.deleteById(id);
		return "redirect:" + "/manage/order/entitylist/entity/state/list";
    }
	
	private void addPackagesAndDocuments(Long orderId, AppliedEntity newEntity) {
		Set<OrderDocumentPackage> oDPs = orderService.findById(orderId).getOrderDocumentPackage();
		for (OrderDocumentPackage orderDocumentPackage : oDPs) {
			DocumentPackage dp = new DocumentPackage(orderDocumentPackage.getName(), 
					new Date(0), 
					new Date(0), 
					0.0, 
					0.0, 
					0.0, 
					getDummyPackageState(), 
					getDummyPackageType());
			dp.setOrderDocumentPackageId(orderDocumentPackage.getId());
			dp.setAppliedEntity(newEntity);
			dpService.save(dp);
			
			Set<OrderDocument> docs = orderDocumentPackage.getOrderDocument();
			for (OrderDocument orderDocument : docs) {
				EntityDocument newDoc = new EntityDocument(
						orderDocument.getName(), 
						0L, 
						new Date(0), 
						"", 
						0L, 
						new Date(0), 
						"", 
						"123", 
						new Date(0), 
						"", 
						getDummyDocumentRB(), 
						getDummyDocumentState());
				newDoc.setDocumentPackage(dp);
				edService.save(newDoc);
			}
		}
	}
	
	private DocumentPackageState getDummyPackageState() {
		DocumentPackageState result = dpStateService.findByName("Dummy State");
		if(result == null) {
			result = new DocumentPackageState("Dummy State");
			dpStateService.save(result);
		}
		
		return result;
	}
	
	private DocumentPackageType getDummyPackageType() {
		DocumentPackageType result = dpTypeService.findByName("Dummy Type");
		if(result == null) {
			result = new DocumentPackageType("Dummy Type");
			dpTypeService.save(result);
		}
		
		return result;
	}
	
	private EntityDocumentState getDummyDocumentState() {
		EntityDocumentState result = edStateService.findByName("Dummy State");
		if(result == null) {
			result = new EntityDocumentState("Dummy State");
			edStateService.save(result);
		}
		
		return result;
	}
	
	private EntityDocumentRegisteredBy getDummyDocumentRB() {
		EntityDocumentRegisteredBy result = edRBService.findByName("Dummy RB");
		if(result == null) {
			result = new EntityDocumentRegisteredBy("Dummy RB");
			edRBService.save(result);
		}
		
		return result;
	}

}
