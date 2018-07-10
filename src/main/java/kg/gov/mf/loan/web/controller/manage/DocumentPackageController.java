package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentRegisteredBy;
import kg.gov.mf.loan.manage.service.entitylist.AppliedEntityListService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.web.fetchModels.EntityDocumentModel;
import kg.gov.mf.loan.web.util.EntityDocumentProgress;
import kg.gov.mf.loan.web.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackage;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackageState;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackageType;
import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageStateService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageTypeService;
import kg.gov.mf.loan.manage.service.entity.AppliedEntityService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentRegisteredByService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentStateService;
import kg.gov.mf.loan.web.util.Utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Controller
public class DocumentPackageController {
	
	@Autowired
	DocumentPackageStateService dpStateService;
	
	@Autowired
	DocumentPackageTypeService dpTypeService;
	
	@Autowired
	AppliedEntityService entityService;
	
	@Autowired
	DocumentPackageService dpService;
	
	@Autowired
	EntityDocumentStateService edStateService;
	
	@Autowired
	EntityDocumentRegisteredByService rbService;

    @Autowired
    EntityDocumentRegisteredByService edRBService;

    @Autowired
    CreditOrderService orderService;

    @Autowired
    AppliedEntityListService listService;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/view"})
    public String viewEntity(
    		ModelMap model, 
    		@PathVariable("orderId")Long orderId, 
    		@PathVariable("listId")Long listId, 
    		@PathVariable("entityId")Long entityId,
    		@PathVariable("dpId")Long dpId) {

		DocumentPackage dp = dpService.getById(dpId);
        model.addAttribute("dp", dp);

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonDocuments = gson.toJson(getDocumentsByPackageId(dpId));
        model.addAttribute("entityDocuments", jsonDocuments);
        
        model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
        model.addAttribute("listId", listId);
		model.addAttribute("list", listService.getById(listId));
        model.addAttribute("entityId", entityId);
		model.addAttribute("entity", entityService.getById(entityId));
        model.addAttribute("dpId", dpId);

        List<EntityDocumentRegisteredBy> rBs = edRBService.list();
        model.addAttribute("rBs", rBs);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/view";
    }
	
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/save", method=RequestMethod.GET)
	public String formDocumentPackage(
			ModelMap model, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId, 
			@PathVariable("entityId")Long entityId,
			@PathVariable("dpId")Long dpId)
	{
		if(dpId == 0)
		{
			model.addAttribute("dp", new DocumentPackage());
		}
			
		
		if(dpId > 0)
		{
			model.addAttribute("dp", dpService.getById(dpId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
		model.addAttribute("listId", listId);
		model.addAttribute("list", listService.getById(listId));
		model.addAttribute("entityId", entityId);
		model.addAttribute("entity", entityService.getById(entityId));
		
		List<DocumentPackageType> types = dpTypeService.list();
        model.addAttribute("types", types);
		
		return "/manage/order/entitylist/entity/documentpackage/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/save", method=RequestMethod.POST)
	public String saveDocumentPackage(
			DocumentPackage dp, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId,
			@PathVariable("entityId")Long entityId,
			ModelMap model)
	{
		AppliedEntity entity = entityService.getById(entityId);
		dp.setAppliedEntity(entity);
		
		if(dp.getId() == 0)
		{
			dp.setCompletedRatio(0.0);
			dp.setApprovedRatio(0.0);
			dp.setRegisteredRatio(0.0);
			dp.setDocumentPackageState(dpStateService.getById(1L)!=null? dpStateService.getById(1L): null);
			dpService.add(dp);
		}

		else
		{
			DocumentPackage tDp = dpService.getById(dp.getId());
			dp.setDocumentPackageState(tDp.getDocumentPackageState());
			dp.setCompletedDate(tDp.getCompletedDate());
			dp.setCompletedRatio(tDp.getCompletedRatio());
			dp.setApprovedDate(tDp.getApprovedDate());
			dp.setApprovedRatio(tDp.getApprovedRatio());
			dp.setRegisteredRatio(tDp.getRegisteredRatio());
			dpService.update(dp);
		}

		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/delete", method=RequestMethod.POST)
    public String deleteDocumentPackage(long id, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId) {
		if(id > 0)
			dpService.remove(dpService.getById(id));
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/view";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/entity/documentpackage/state/list" }, method = RequestMethod.GET)
    public String listDPStates(ModelMap model) {
 
		List<DocumentPackageState> states = dpStateService.list();
		model.addAttribute("states", states);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/state/{stateId}/save", method=RequestMethod.GET)
	public String formDPState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("dpState", new DocumentPackageState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("dpState", dpStateService.getById(stateId));
		}
		return "/manage/order/entitylist/entity/documentpackage/state/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/state/save", method=RequestMethod.POST)
    public String saveDocumentPackageState(DocumentPackageState state, ModelMap model) {
		if(state.getId() == 0)
			dpStateService.add(state);
		else
			dpStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/documentpackage/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/state/delete", method=RequestMethod.POST)
    public String deleteDocumentPackageState(long id) {
		if(id > 0)
			dpStateService.remove(dpStateService.getById(id));
		return "redirect:" + "/manage/order/entitylist/entity/documentpackage/state/list";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/entity/documentpackage/type/list" }, method = RequestMethod.GET)
    public String listDPTypes(ModelMap model) {
 
		List<DocumentPackageType> types = dpTypeService.list();
		model.addAttribute("types", types);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/type/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/type/{typeId}/save", method=RequestMethod.GET)
	public String formDPType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("dpType", new DocumentPackageType());
		}
		
		if(typeId > 0)
		{
			model.addAttribute("dpType", dpTypeService.getById(typeId));
		}
		return "/manage/order/entitylist/entity/documentpackage/type/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/type/save", method=RequestMethod.POST)
    public String saveDocumentPackageType(DocumentPackageType type, ModelMap model) {
		if(type.getId() == 0)
			dpTypeService.add(type);
		else
			dpTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/documentpackage/type/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/type/delete", method=RequestMethod.POST)
    public String deleteDocumentPackageType(long id) {
		if(id > 0)
			dpTypeService.remove(dpTypeService.getById(id));
		return "redirect:" + "/manage/order/entitylist/entity/documentpackage/type/list";
    }

	private List<EntityDocumentModel> getDocumentsByPackageId(long packageId)
	{
		String baseQuery = "SELECT doc.id,\n" +
				"  doc.name as docName,\n" +
				"  pck.name as packageName,\n" +
				"  tp.name as typeName,\n" +
				"  owner.name as ownerName,\n" +
				"  owner.id as ownerId,\n" +
				"  address.district_id as districtId,\n" +
				"  district.name as districtName,\n" +
				"  st.id as statusId,\n" +
				"  st.name as statusName,\n" +
				"  CASE\n" +
				"  WHEN st.id = 1 THEN 0\n" +
				"  WHEN st.id = 3 THEN 50\n" +
				"  WHEN st.id = 4 THEN  100\n" +
				"  END as progress,\n" +
				"  doc.documentPackageId as packageId,\n" +
				"  pck.appliedEntityId as entityId,\n" +
				"  ent.appliedEntityListId as entityListId,\n" +
				"  list.creditOrderId as orderId,\n" +
				"  cOrder.regNumber as orderNumber\n" +
				"FROM entityDocument doc,\n" +
				"  documentPackage pck,\n" +
				"  orderDocumentType tp,\n" +
				"  entityDocumentState st,\n" +
				"  appliedEntity ent,\n" +
				"  appliedEntityList list,\n" +
				"  owner owner,\n" +
				"  address address,\n" +
				"  district district,\n" +
				"  creditOrder cOrder\n" +
				"WHERE pck.id = doc.documentPackageId\n" +
				"AND tp.id = doc.documentTypeId\n" +
				"AND st.id = doc.entityDocumentStateId\n" +
				"AND ent.id = pck.appliedEntityId\n" +
				"AND list.id = ent.appliedEntityListId\n" +
				"AND owner.id = ent.ownerId\n" +
				"AND owner.addressId = address.id\n" +
				"AND district.id = address.district_id\n" +
				"AND cOrder.id = list.creditOrderId\n" +
				"AND doc.documentPackageId = " + packageId;

		Query query = entityManager.createNativeQuery(baseQuery, EntityDocumentModel.class);

		List<EntityDocumentModel> documents = query.getResultList();
		return documents;
	}

}
