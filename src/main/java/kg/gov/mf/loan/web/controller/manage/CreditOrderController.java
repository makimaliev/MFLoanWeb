package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.repository.order.CreditOrderRepository;
import kg.gov.mf.loan.web.fetchModels.AppliedEntityListModel;
import kg.gov.mf.loan.web.util.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityList;
import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.model.order.CreditOrderState;
import kg.gov.mf.loan.manage.model.order.CreditOrderType;
import kg.gov.mf.loan.manage.service.entitylist.AppliedEntityListStateService;
import kg.gov.mf.loan.manage.service.entitylist.AppliedEntityListTypeService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.order.CreditOrderStateService;
import kg.gov.mf.loan.manage.service.order.CreditOrderTypeService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermAccrMethodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermDaysMethodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFloatingRateTypeService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFrequencyTypeService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFundService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermRatePeriodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermTransactionOrderService;
import kg.gov.mf.loan.web.util.Utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Controller
@SessionAttributes("roles")
public class CreditOrderController {
	
	@Autowired
	CreditOrderStateService creditOrderStateService;
	
	@Autowired
	CreditOrderTypeService creditOrderTypeService;
	
	@Autowired
	CreditOrderService creditOrderService;
	
	static final Logger loggerOrder = LoggerFactory.getLogger(CreditOrder.class);
	
	@Autowired
	AppliedEntityListStateService elStateService;
	
	@Autowired
	AppliedEntityListTypeService elTypeService;
	
	@Autowired
	OrderTermFundService fundService;
	
	@Autowired
	OrderTermCurrencyService currService;
	
	@Autowired
	OrderTermFrequencyTypeService freqTypeService;
	
	@Autowired
	OrderTermRatePeriodService ratePeriodService;
	
	@Autowired
	OrderTermFloatingRateTypeService rateTypeService;
	
	@Autowired
	OrderTermTransactionOrderService txOrderService;
	
	@Autowired
	OrderTermDaysMethodService daysMethodService;
	
	@Autowired
	OrderTermAccrMethodService accrMethodService;

	@Autowired
	CreditOrderRepository creditOrderRepository;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/view"})
    public String viewOrder(ModelMap model, @PathVariable("orderId")Long orderId) {

		CreditOrder order = creditOrderService.getById(orderId);
        model.addAttribute("order", order);

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonLists = gson.toJson(getListsByOrderId(orderId));
		model.addAttribute("entityLists", jsonLists);
        
        model.addAttribute("orderDocumentPackages", order.getOrderDocumentPackages());
        model.addAttribute("orderTerms", order.getOrderTerms());
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/view";
    }
	
	@RequestMapping(value = {"/manage/order/list" }, method = RequestMethod.GET)
    public String listOrders(ModelMap model) {

        List<CreditOrderState> states = creditOrderStateService.list();
        List<CreditOrderType> types = creditOrderTypeService.list();
        model.addAttribute("states", states);
        model.addAttribute("types", types);

        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/list";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/save", method=RequestMethod.GET)
	public String formCreditOrder(ModelMap model, @PathVariable("orderId")Long orderId)
	{
		if(orderId == 0)
		{
			model.addAttribute("creditOrder", new CreditOrder());
		}
		
		if(orderId > 0)
		{
			model.addAttribute("creditOrder", creditOrderService.getById(orderId));
		}
		List<CreditOrderType> types = creditOrderTypeService.list();
		model.addAttribute("types", types);
		return "/manage/order/save";
	}
	
	@RequestMapping(value="/manage/order/save", method=RequestMethod.POST)
	public String saveCreditOrder(CreditOrder creditOrder)
	{
		loggerOrder.info("Order : {}", creditOrder);
		if(creditOrder.getId() == 0){
			creditOrder.setCreditOrderState(creditOrderStateService.getById(2L));
			creditOrderService.add(creditOrder);
		}
		else
		{
			CreditOrder order = creditOrderService.getById(creditOrder.getId());
			CreditOrderState state = creditOrderStateService.getById(order.getCreditOrderState().getId());
			creditOrder.setCreditOrderState(state);
			creditOrderService.update(creditOrder);
		}


		return "redirect:" + "/manage/order/" + creditOrder.getId() + "/view";
	}

	@RequestMapping(value="/manage/order/{orderId}/approve", method=RequestMethod.GET)
	public String approveCreditOrder(@PathVariable("orderId")Long orderId)
	{
		CreditOrder order = creditOrderService.getById(orderId);
		order.setCreditOrderState(creditOrderStateService.getById(1L));
		creditOrderService.update(order);
		return "redirect:" + "/manage/order/list";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/delete", method=RequestMethod.GET)
    public String deleteCreditOrder(@PathVariable("orderId")Long orderId) {
		if(orderId > 0)
		    creditOrderRepository.delete(creditOrderRepository.findOne(orderId));
        return "redirect:" + "/manage/order/list";
    }
	
	@RequestMapping(value = { "/manage/order/state/list" }, method = RequestMethod.GET)
    public String listOrderStates(ModelMap model) {
 
		model.addAttribute("states", creditOrderStateService.list());
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/state/list";
    }
	
	@RequestMapping(value="/manage/order/state/{stateId}/save", method=RequestMethod.GET)
	public String formCreditOrderState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("orderState", new CreditOrderState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("orderState", creditOrderStateService.getById(stateId));
		}
		return "/manage/order/state/save";
	}
	
	@RequestMapping(value="/manage/order/state/save", method=RequestMethod.POST)
    public String saveCreditOrderState(CreditOrderState state, ModelMap model) {
		if(state.getId() == 0)
			creditOrderStateService.add(state);
		else
			creditOrderStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/state/list";
    }
	
	@RequestMapping(value="/manage/order/state/delete", method=RequestMethod.POST)
    public String deleteCreditOrderState(long id) {
		if(id > 0)
			creditOrderStateService.remove(creditOrderStateService.getById(id));
        return "redirect:" + "/manage/order/state/list";
    }
	
	@RequestMapping(value = { "/manage/order/type/list" }, method = RequestMethod.GET)
    public String listOrderTypes(ModelMap model) {
 
		List<CreditOrderType> types = creditOrderTypeService.list();
		model.addAttribute("types", types);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/type/list";
    }
	
	@RequestMapping(value="/manage/order/type/{typeId}/save", method=RequestMethod.GET)
	public String formCreditOrderType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("orderType", new CreditOrderType());
		}
		
		if(typeId > 0)
		{
			model.addAttribute("orderType", creditOrderTypeService.getById(typeId));
		}
		return "/manage/order/type/save";
	}
	
	@RequestMapping(value="/manage/order/type/save", method=RequestMethod.POST)
    public String saveCreditOrderType(CreditOrderType type,  ModelMap model) {
		
		if(type.getId() == 0)
			creditOrderTypeService.add(type);
		else
			creditOrderTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/type/list";
    }
	
	@RequestMapping(value="/manage/order/type/delete", method=RequestMethod.POST)
    public String deleteCreditOrderType(long id) {
		if(id > 0)
			creditOrderTypeService.remove(creditOrderTypeService.getById(id));
        return "redirect:" + "/manage/order/type/list";
    }

	private List<AppliedEntityListModel> getListsByOrderId(long orderId)
	{
		String baseQuery = "SELECT list.id,\n" +
				"  list.listDate,\n" +
				"  list.listNumber,\n" +
				"  list.appliedEntityListStateId as state,\n" +
				"  list.appliedEntityListTypeId as type\n" +
				"FROM appliedEntityList list\n" +
				"WHERE list.creditOrderId = " + orderId;

		Query query = entityManager.createNativeQuery(baseQuery, AppliedEntityListModel.class);

		List<AppliedEntityListModel> lists = query.getResultList();
		return lists;
	}
}
