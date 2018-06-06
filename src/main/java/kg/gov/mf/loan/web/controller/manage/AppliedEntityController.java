package kg.gov.mf.loan.web.controller.manage;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.service.orderdocumentpackage.OrderDocumentPackageService;
import kg.gov.mf.loan.web.util.Pager;
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
import org.springframework.web.bind.annotation.RequestParam;

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

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	OrderDocumentPackageService odpService;

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = {5, 10, 20, 50, 100};

	@RequestMapping(value = {"/manage/order/entitylist/entity/list" }, method = RequestMethod.GET)
	public String listEntities(@RequestParam("pageSize") Optional<Integer> pageSize,
								  @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<AppliedEntity> entities = entityService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = entityService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("entities", entities);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/order/entitylist/entity/list";
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/view"})
    public String viewEntity(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId) {

		AppliedEntity entity = entityService.getById(entityId);
        model.addAttribute("entity", entity);
        
        model.addAttribute("dPackages", entity.getDocumentPackages());
        
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
			model.addAttribute("entity", entityService.getById(entityId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("listId", listId);
		List<AppliedEntityState> states = entityStateService.list();
        model.addAttribute("states", states);
			
		return "/manage/order/entitylist/entity/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/save", method=RequestMethod.POST)
	public String saveAppliedEntity(
			AppliedEntity entity, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId, 
			ModelMap model)
	{
		AppliedEntityList list = listService.getById(listId);
		entity.setAppliedEntityList(list);
		
		if(entity.getId() == 0)
		{
			Owner owner = ownerRepository.findOne(entity.getOwner().getId());
			entity.setOwner(owner);
			entityService.add(entity);
			addPackagesAndDocuments(orderId, entity);
		}
		else
			entityService.update(entity);
			
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/delete", method=RequestMethod.POST)
    public String deleteAppliedEntity(long id, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId) {
		if(id > 0)
			entityService.remove(entityService.getById(id));
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/view";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/entity/state/list" }, method = RequestMethod.GET)
    public String listAppliedEntityStates(ModelMap model) {
 
		List<AppliedEntityState> states = entityStateService.list();
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
			model.addAttribute("eState", entityStateService.getById(stateId));
		}
		return "/manage/order/entitylist/entity/state/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/entity/state/save", method=RequestMethod.POST)
    public String saveAppliedEntityState(AppliedEntityState state, ModelMap model) {
		if(state.getId() == 0)
			entityStateService.add(state);
		else
			entityStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/state/delete", method=RequestMethod.POST)
    public String deleteAppliedEntityState(long id) {
		if(id > 0)
			entityStateService.remove(entityStateService.getById(id));
		return "redirect:" + "/manage/order/entitylist/entity/state/list";
    }
	
	private void addPackagesAndDocuments(Long orderId, AppliedEntity entity) {
		Set<OrderDocumentPackage> oDPs = orderService.getById(orderId).getOrderDocumentPackages();
		for (OrderDocumentPackage odp : oDPs) {
			OrderDocumentPackage tOdp = odpService.getById(odp.getId());
			DocumentPackage dp = new DocumentPackage();
			dp.setName(odp.getName());
			dp.setOrderDocumentPackageId(odp.getId());
			dp.setAppliedEntity(entity);
			dpService.add(dp);

			Set<OrderDocument> docs = tOdp.getOrderDocuments();
			for (OrderDocument od : docs) {
				EntityDocument newDoc = new EntityDocument();
				newDoc.setName(od.getName());
				newDoc.setDocumentPackage(dp);
				edService.add(newDoc);
			}
		}
	}
	
	private DocumentPackageState getPackageState() {
		DocumentPackageState result = dpStateService.getById(1L);
		if(result == null) {
			result = new DocumentPackageState();
			result.setVersion(1);
			result.setName("Dummy State");
			dpStateService.add(result);
		}
		
		return result;
	}
	
	private DocumentPackageType getPackageType() {
		DocumentPackageType result = dpTypeService.getById(1L);
		if(result == null) {
			result = new DocumentPackageType();
			result.setVersion(1);
			result.setName("Dummy Type");
			dpTypeService.add(result);
		}
		
		return result;
	}
	
	
	private EntityDocumentState getDocumentState() {
		EntityDocumentState result = edStateService.getById(1L);
		if(result == null) {
			result = new EntityDocumentState();
			result.setVersion(1);
			result.setName("Dummy State");
			edStateService.add(result);
		}
		
		return result;
	}
	
	private EntityDocumentRegisteredBy getDocumentRB() {
		EntityDocumentRegisteredBy result = edRBService.getById(1L);
		if(result == null) {
			result = new EntityDocumentRegisteredBy();
			result.setVersion(1);
			result.setName("Dummy RB");
			edRBService.add(result);
		}
		
		return result;
	}

}
