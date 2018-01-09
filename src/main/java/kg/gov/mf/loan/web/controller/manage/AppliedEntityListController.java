package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.manage.model.entity.AppliedEntityState;
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/view"})
    public String viewEntityList(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId) {

		AppliedEntityList list = listService.findById(listId);
        model.addAttribute("entityList", list);
        
        List<AppliedEntityState> states = entityStateService.findAll();
        model.addAttribute("states", states);
		model.addAttribute("emptyState", new AppliedEntityState());
		
		model.addAttribute("emptyEntity", new AppliedEntity());
        model.addAttribute("entities", list.getAppliedEntity());
        
        model.addAttribute("orderId", orderId);
        
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
			model.addAttribute("entityList", listService.findById(listId));
		}
		model.addAttribute("orderId", orderId);
		List<AppliedEntityListState> states = elStateService.findAll();
        model.addAttribute("states", states);
		List<AppliedEntityListType> types = elTypeService.findAll();
        model.addAttribute("types", types);
			
		return "/manage/order/entitylist/save";
	}
	
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/save", method=RequestMethod.POST)
	public String saveAppliedEntityList(AppliedEntityList list, long stateId, long typeId, 
			@PathVariable("orderId")Long orderId, ModelMap model)
	{
		CreditOrder creditOrder = orderService.findById(orderId);
		loggerEntityList.info("Entity List : {}", list);
		if(list != null && list.getId() == 0)
		{
			AppliedEntityList newList = new AppliedEntityList(list.getListNumber(), list.getListDate(), 
					elStateService.findById(stateId), elTypeService.findById(typeId));
			newList.setCreditOrder(creditOrder);
			listService.save(newList);
		}
			
		
		if(list != null && list.getId() > 0)
		{
			list.setAppliedEntityListState(elStateService.findById(stateId));
			list.setAppliedEntityListType(elTypeService.findById(typeId));
			listService.update(list);
		}
			
		return "redirect:" + "/manage/order/{orderId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/delete", method=RequestMethod.POST)
    public String deleteAppliedEntityList(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			listService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/state/list" }, method = RequestMethod.GET)
    public String listAppliedEntityListStates(ModelMap model) {
 
		List<AppliedEntityListState> states = elStateService.findAll();
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
			model.addAttribute("elState", elStateService.findById(stateId));
		}
		return "/manage/order/entitylist/state/save";
	}
	
	
	@RequestMapping(value="/manage/order/entitylist/state/save", method=RequestMethod.POST)
    public String saveAppliedEntityListState(AppliedEntityListState state, ModelMap model) {
		if(state != null && state.getId() == 0)
			elStateService.save(new AppliedEntityListState(state.getName()));
		
		if(state != null && state.getId() > 0)
			elStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/state/delete", method=RequestMethod.POST)
    public String deleteAppliedEntityListState(long id) {
		if(id > 0)
			elStateService.deleteById(id);
		return "redirect:" + "/manage/order/entitylist/state/list";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/type/list" }, method = RequestMethod.GET)
    public String listAppliedEntityListTypes(ModelMap model) {
 
		List<AppliedEntityListType> types = elTypeService.findAll();
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
			model.addAttribute("elType", elTypeService.findById(typeId));
		}
		return "/manage/order/entitylist/type/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/type/save", method=RequestMethod.POST)
    public String saveAppliedEntityListType(AppliedEntityListType type, ModelMap model) {
		if(type != null && type.getId() == 0)
			elTypeService.save(new AppliedEntityListType(type.getName()));
		
		if(type != null && type.getId() > 0)
			elTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/order/entitylist/type/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/type/delete", method=RequestMethod.POST)
    public String deleteAppliedEntityListType(long id) {
		if(id > 0)
			elTypeService.deleteById(id);
		return "redirect:" + "/manage/order/entitylist/type/list";
    }
	
}
