package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	
	static final Logger loggerEntityList = LoggerFactory.getLogger(AppliedEntityList.class);

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = {5, 10, 20, 50, 100};
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = {"/manage/order/entitylist/list" }, method = RequestMethod.GET)
	public String listEntityLists(@RequestParam("pageSize") Optional<Integer> pageSize,
							 @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<AppliedEntityList> lists = listService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = listService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("lists", lists);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/order/entitylist/list";
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/view"})
    public String viewEntityList(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId) {

		AppliedEntityList list = listService.getById(listId);
        model.addAttribute("entityList", list);
        model.addAttribute("entities", list.getAppliedEntities());
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
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/delete", method=RequestMethod.POST)
    public String deleteAppliedEntityList(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			listService.remove(listService.getById(id));
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
	
}
