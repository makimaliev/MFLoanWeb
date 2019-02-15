package kg.gov.mf.loan.web.controller.manage;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.repository.entitylist.AppliedEntityListRepository;
import kg.gov.mf.loan.web.fetchModels.AppliedEntityListMetaModel;
import kg.gov.mf.loan.web.fetchModels.AppliedEntityModel;
import kg.gov.mf.loan.web.fetchModels.DebtorMetaModel;
import kg.gov.mf.loan.web.util.Meta;
import kg.gov.mf.loan.web.util.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityList;
import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityListState;
import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityListType;
import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.service.entity.AppliedEntityStateService;
import kg.gov.mf.loan.manage.service.entitylist.AppliedEntityListService;
import kg.gov.mf.loan.manage.service.entitylist.AppliedEntityListStateService;
import kg.gov.mf.loan.manage.service.entitylist.AppliedEntityListTypeService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.web.util.Utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Controller
public class AppliedEntityListController {
	
	@Autowired
	AppliedEntityListService listService;

	@Autowired
	AppliedEntityListStateService elStateService;
	
	@Autowired
	AppliedEntityListTypeService elTypeService;
	
	@Autowired
	CreditOrderService orderService;
	
	@Autowired
	AppliedEntityStateService entityStateService;

	@Autowired
	AppliedEntityListRepository appliedEntityListRepository;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;
	
	static final Logger loggerEntityList = LoggerFactory.getLogger(AppliedEntityList.class);
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/view"})
    public String viewEntityList(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId) {

		AppliedEntityList list = listService.getById(listId);
        model.addAttribute("entityList", list);

//		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
//		String jsonEntities = gson.toJson(getEntitiesByListId(listId));
//		model.addAttribute("entities", jsonEntities);

        model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/view";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/save", method=RequestMethod.GET)
	public String formeAppliedEntityList(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId)
	{
		if(listId == 0)
		{
			model.addAttribute("entityList", new AppliedEntityList());
		}
			
		
		if(listId > 0)
		{
			model.addAttribute("entityList", listService.getById(listId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
		List<AppliedEntityListState> states = elStateService.list();
        model.addAttribute("states", states);
		List<AppliedEntityListType> types = elTypeService.list();
        model.addAttribute("types", types);
			
		return "/manage/order/entitylist/save";
	}
	
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/save", method=RequestMethod.POST)
	public String saveAppliedEntityList(AppliedEntityList list,  
			@PathVariable("orderId")Long orderId, ModelMap model)
	{
		loggerEntityList.info("Entity List : {}", list);
		CreditOrder creditOrder = orderService.getById(orderId);
		list.setCreditOrder(creditOrder);
		if(list.getId() == 0)
		{
			listService.add(list);
		}
			
		if(list != null && list.getId() > 0)
		{
			listService.update(list);
		}
		
		return "redirect:" + "/manage/order/" + orderId +"/entitylist/" + list.getId()+ "/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/delete", method=RequestMethod.GET)
    public String deleteAppliedEntityList(@PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId) {
		if(listId > 0)
			appliedEntityListRepository.delete(appliedEntityListRepository.findOne(listId));
		return "redirect:" + "/manage/order/{orderId}/view";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/state/list" }, method = RequestMethod.GET)
    public String listAppliedEntityListStates(ModelMap model) {
 
		List<AppliedEntityListState> states = elStateService.list();
		model.addAttribute("states", states);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/state/{stateId}/save", method=RequestMethod.GET)
	public String formELState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("elState", new AppliedEntityListState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("elState", elStateService.getById(stateId));
		}
		return "/manage/order/entitylist/state/save";
	}
	
	
	@RequestMapping(value="/manage/order/entitylist/state/save", method=RequestMethod.POST)
    public String saveAppliedEntityListState(AppliedEntityListState state, ModelMap model) {
		if(state.getId() == 0)
			elStateService.add(state);
		else
			elStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/state/delete", method=RequestMethod.POST)
    public String deleteAppliedEntityListState(long id) {
		if(id > 0)
			elStateService.remove(elStateService.getById(id));
		return "redirect:" + "/manage/order/entitylist/state/list";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/type/list" }, method = RequestMethod.GET)
    public String listAppliedEntityListTypes(ModelMap model) {
 
		List<AppliedEntityListType> types = elTypeService.list();
		model.addAttribute("types", types);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/type/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/type/{typeId}/save", method=RequestMethod.GET)
	public String formELType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("elType", new AppliedEntityListType());
		}
		
		if(typeId > 0)
		{
			model.addAttribute("elType", elTypeService.getById(typeId));
		}
		return "/manage/order/entitylist/type/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/type/save", method=RequestMethod.POST)
    public String saveAppliedEntityListType(AppliedEntityListType type, ModelMap model) {
		if(type.getId() == 0)
			elTypeService.add(type);
		else
			elTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/order/entitylist/type/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/type/delete", method=RequestMethod.POST)
    public String deleteAppliedEntityListType(long id) {
		if(id > 0)
			elTypeService.remove(elTypeService.getById(id));
		return "redirect:" + "/manage/order/entitylist/type/list";
    }

    @PostMapping("/api/entitylists/{listId}/")
	@ResponseBody
	public AppliedEntityListMetaModel getList(@RequestParam Map<String, String> datatable,@PathVariable("listId") Long listId)
	{
		String pageStr = datatable.get("datatable[pagination][page]");
		String perPageStr = datatable.get("datatable[pagination][perpage]");
		String sortStr = datatable.get("datatable[sort][sort]");
		String sortField = datatable.get("datatable[sort][field]");

		Integer page = Integer.parseInt(pageStr);
		Integer perPage = Integer.parseInt(perPageStr);
		Integer offset = (page-1)*perPage;

		boolean searchByName= datatable.containsKey("datatable[query][generalSearch]");

		String ownerStr = searchByName?datatable.get("datatable[query][generalSearch]"):"";

		String baseQuery = "SELECT ent.id, owner.name as ownerName, state.name as status, list.id as listId\n" +
				"FROM appliedEntity ent, appliedEntityList list,\n" +
				"  owner owner, appliedEntityState state\n" +
				"WHERE ent.appliedEntityListId = list.id\n" +
				"  AND owner.id = ent.ownerId\n" +
				"  AND ent.appliedEntityStateId = state.id\n" +
				"  AND list.id =" + listId+"\n"+
				"  AND owner.name like \'%" + ownerStr+"%'\n"+
				"ORDER BY " + sortField + " " + sortStr + " LIMIT " + offset +"," + perPage;

		Query query = entityManager.createNativeQuery(baseQuery, AppliedEntityModel.class);

		String countQuery= "select count(1)\n" +
				"FROM appliedEntity ent, appliedEntityList list,\n" +
				"  owner owner, appliedEntityState state\n" +
				"WHERE ent.appliedEntityListId = list.id\n" +
				"  AND owner.id = ent.ownerId\n" +
				"  AND ent.appliedEntityStateId = state.id\n" +
				"  AND list.id =" + listId;

		BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);

		AppliedEntityListMetaModel metaModel = new AppliedEntityListMetaModel();
		Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
		metaModel.setMeta(meta);

		List<AppliedEntityModel> entities = query.getResultList();
		metaModel.setData(entities);
		return metaModel;

	}

	private List<AppliedEntityModel> getEntitiesByListId(long listId)
	{
		String baseQuery = "SELECT ent.id, owner.name as ownerName, state.name as status, list.id as listId\n" +
				"FROM appliedEntity ent, appliedEntityList list,\n" +
				"  owner owner, appliedEntityState state\n" +
				"WHERE ent.appliedEntityListId = list.id\n" +
				"  AND owner.id = ent.ownerId\n" +
				"  AND ent.appliedEntityStateId = state.id\n" +
				"  AND list.id =" + listId;

		Query query = entityManager.createNativeQuery(baseQuery, AppliedEntityModel.class);

		List<AppliedEntityModel> entities = query.getResultList();
		return entities;
	}
	
}
