package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackage;
import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.manage.model.entity.AppliedEntityState;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityList;
import kg.gov.mf.loan.manage.model.orderdocument.OrderDocument;
import kg.gov.mf.loan.manage.model.orderdocumentpackage.OrderDocumentPackage;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.repository.entity.AppliedEntityRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
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
import kg.gov.mf.loan.manage.service.orderdocumentpackage.OrderDocumentPackageService;
import kg.gov.mf.loan.web.fetchModels.EntityDocumentPackageModel;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

	@Autowired
	AppliedEntityRepository appliedEntityRepository;

	@Autowired
	PersonService personService;

	@Autowired
	OwnerService ownerService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	UserService userService;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/view"})
    public String viewEntity(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId) {

		AppliedEntity entity = entityService.getById(entityId);
        model.addAttribute("entity", entity);
		Person person=personService.findById(ownerService.getById(entity.getOwner().getId()).getEntityId());
		model.addAttribute("person",person);

		try{
			Owner owner=this.ownerService.getByEntityId(person.getId(),"PERSON");
			Debtor debtor=debtorService.getByOwnerId(owner.getId());
			model.addAttribute("hasDebtor",true);
		}
		catch (Exception e){
			model.addAttribute("hasDebtor",false);
		}

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonPackages = gson.toJson(getPackagesByEntityId(entityId));
		model.addAttribute("dPackages", jsonPackages);

        model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
        model.addAttribute("listId", listId);
		model.addAttribute("list", listService.getById(listId));
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/view";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/save", method=RequestMethod.GET)
	public String formeAppliedEntityList(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId)
	{
		if(entityId == 0)
		{
			model.addAttribute("entity", new AppliedEntity());
			model.addAttribute("ownerText", "");
		}
			
		
		if(entityId > 0)
		{
			AppliedEntity entity = entityService.getById(entityId);
			Owner owner = entity.getOwner();
			model.addAttribute("entity", entity);
			String ownerText = "[" + owner.getId() + "] "
					+ owner.getName()
					+ " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
			model.addAttribute("ownerText", ownerText);
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
		model.addAttribute("listId", listId);
		model.addAttribute("list", listService.getById(listId));
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
		else{
			Owner owner = ownerRepository.findOne(entity.getOwner().getId());
			entity.setOwner(owner);
			entityService.update(entity);
		}

		return "redirect:" + "/manage/order/" + orderId+"/entitylist/"+listId+"/entity/"+ entity.getId()+"/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/delete", method=RequestMethod.GET)
    public String deleteAppliedEntity(@PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId) {
		if(entityId > 0)
			appliedEntityRepository.delete(appliedEntityRepository.findOne(entityId));
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

    @RequestMapping("/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/completeAll")
	public String completeAlls(@PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId){

		User user=userService.findByUsername(Utils.getPrincipal());

		AppliedEntity entity=entityService.getById(entityId);
		if(entity.getAppliedEntityState().getId()!=2) {
			entity.setAppliedEntityState(entityStateService.getById(2L));
			entityService.update(entity);
			for (DocumentPackage dp : entity.getDocumentPackages()) {
				DocumentPackage documentPackage = dpService.getById(dp.getId());
				if(documentPackage.getDocumentPackageState().getId()!=2) {
					documentPackage.setDocumentPackageState(dpStateService.getById(2L));
					documentPackage.setApprovedRatio(1.0);
					documentPackage.setApprovedDate(new Date());
					documentPackage.setCompletedRatio(1.0);
					documentPackage.setCompletedDate(new Date());
					dpService.update(documentPackage);
					for (EntityDocument ed : documentPackage.getEntityDocuments()) {
						EntityDocument entityDocument = edService.getById(ed.getId());
						if(entityDocument.getEntityDocumentState().getId()!=4) {
							entityDocument.setEntityDocumentState(edStateService.getById(4L));
							entityDocument.setApprovedBy(user.getId());
							entityDocument.setCompletedBy(user.getId());
							entityDocument.setApprovedDate(new Date());
							entityDocument.setCompletedDate(new Date());
							edService.update(entityDocument);
						}

					}
				}
			}
		}
 		return "redirect:/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/view";
	}

	@GetMapping("/entity/{id}/changeEntityList")
	public String getChangeEntityEntityList(@PathVariable("id") Long id, Model model){

		AppliedEntity entity=entityService.getById(id);
		List<AppliedEntityList> appliedEntityLists=listService.list();

		model.addAttribute("entity",entity);
		model.addAttribute("entityLists",appliedEntityLists);

		return "/manage/order/entitylist/entity/entityListChangeForm";
	}

	@PostMapping("/entity/changeEntityEntityList")
	public String postChangeEntityEntityList(AppliedEntity entity){

		AppliedEntity oldEntity=entityService.getById(entity.getId());
		oldEntity.setAppliedEntityList(entity.getAppliedEntityList());
		entityService.update(oldEntity);
		AppliedEntityList appliedEntityList=listService.getById(oldEntity.getAppliedEntityList().getId());
		Long orderId=appliedEntityList.getCreditOrder().getId();

		return "redirect:/manage/order/"+orderId+"/entitylist/"+appliedEntityList.getId()+"/entity/"+oldEntity.getId()+"/view";
	}
	
	private void addPackagesAndDocuments(Long orderId, AppliedEntity entity) {
		Set<OrderDocumentPackage> oDPs = orderService.getById(orderId).getOrderDocumentPackages();
		for (OrderDocumentPackage odp : oDPs) {
			OrderDocumentPackage tOdp = odpService.getById(odp.getId());
			DocumentPackage dp = new DocumentPackage();
			dp.setName(odp.getName());
			dp.setOrderDocumentPackageId(odp.getId());
			dp.setAppliedEntity(entity);
			dp.setCompletedRatio(0.0);
			dp.setApprovedRatio(0.0);
			dp.setRegisteredRatio(0.0);
			dp.setDocumentPackageType(odp.getDocumentPackageType());
			dp.setDocumentPackageState(dpStateService.getById(1L)!=null? dpStateService.getById(1L): null);
			dpService.add(dp);

			Set<OrderDocument> docs = tOdp.getOrderDocuments();
			for (OrderDocument od : docs) {
				EntityDocument newDoc = new EntityDocument();
				newDoc.setName(od.getName());
				newDoc.setDocumentTypeId(od.getOrderDocumentType().getId());
				newDoc.setEntityDocumentState(edStateService.getById(1L)!=null?edStateService.getById(1L):null);
				newDoc.setDocumentPackage(dp);
				edService.add(newDoc);
			}
		}
	}

	private List<EntityDocumentPackageModel> getPackagesByEntityId(long entityId)
	{
		String baseQuery = "SELECT pk.id,\n" +
				"  pk.name,\n" +
				"  pk.completedDate,\n" +
				"  pk.approvedDate,\n" +
				"  pk.completedRatio,\n" +
				"  pk.approvedRatio,\n" +
				"  pk.registeredRatio,\n" +
				"  pk.orderDocumentPackageId,\n" +
				"  pk.documentPackageStateId as stateId,\n" +
				"  state.name as stateName,\n" +
				"  pk.documentPackageTypeId as typeId,\n" +
				"  type.name as typeName\n" +
				"FROM documentPackage pk, documentPackageState state,\n" +
				"  documentPackageType type\n" +
				"WHERE pk.documentPackageStateId = state.id\n" +
				"      AND pk.documentPackageTypeId = type.id\n" +
				"      AND pk.appliedEntityId =" + entityId;

		Query query = entityManager.createNativeQuery(baseQuery, EntityDocumentPackageModel.class);

		List<EntityDocumentPackageModel> packages = query.getResultList();
		AppliedEntity entity = entityService.getById(entityId);
		if(entity.getAppliedEntityState().getId()!=2) {
			int counter = 0;
			for (EntityDocumentPackageModel p : packages) {
				if (p.getApprovedRatio() == 1) {
					counter++;
				}
			}
			if (counter == packages.size()) {
				entity.setAppliedEntityState(entityStateService.getById(2L));
				entityService.update(entity);
			}
		}
		return packages;
	}
}
