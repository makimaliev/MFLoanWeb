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
import org.springframework.web.bind.annotation.SessionAttributes;

import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityList;
import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityListState;
import kg.gov.mf.loan.manage.model.entitylist.AppliedEntityListType;
import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.model.order.CreditOrderState;
import kg.gov.mf.loan.manage.model.order.CreditOrderType;
import kg.gov.mf.loan.manage.model.orderdocumentpackage.OrderDocumentPackage;
import kg.gov.mf.loan.manage.model.orderterm.OrderTerm;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermAccrMethod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermDaysMethod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFloatingRateType;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFrequencyType;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFund;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermRatePeriod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermTransactionOrder;
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
	
	static final Logger loggerEntityList = LoggerFactory.getLogger(AppliedEntityList.class);
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/view"})
    public String viewOrder(ModelMap model, @PathVariable("orderId")Long orderId) {

		CreditOrder order = creditOrderService.findById(orderId);
        model.addAttribute("order", order);
        
        model.addAttribute("entityLists", order.getAppliedEntityList());
        model.addAttribute("orderDocumentPackages", order.getOrderDocumentPackage());
        model.addAttribute("orderTerms", order.getOrderTerm());
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/view";
    }
	
	@RequestMapping(value = { "/manage/order/", "/manage/order/list" }, method = RequestMethod.GET)
    public String listOrders(ModelMap model) {
 
        List<CreditOrder> orders = creditOrderService.findAll();
        model.addAttribute("orders", orders);
        
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
			model.addAttribute("creditOrder", creditOrderService.findById(orderId));
		}
		List<CreditOrderState> states = creditOrderStateService.findAll();
		List<CreditOrderType> types = creditOrderTypeService.findAll();
		model.addAttribute("states", states);
		model.addAttribute("types", types);
		return "/manage/order/save";
	}
	
	@RequestMapping(value="/manage/order/save", method=RequestMethod.POST)
	public String saveCreditOrder(CreditOrder creditOrder, long stateId, long typeId)
	{
		loggerOrder.info("Order : {}", creditOrder);
		if(creditOrder != null && creditOrder.getId() == 0)
		{
			CreditOrder newOrder = new CreditOrder(creditOrder.getRegNumber(), creditOrder.getRegDate(), 
					creditOrder.getDescription(), creditOrderStateService.findById(stateId), creditOrderTypeService.findById(typeId));
			creditOrderService.save(newOrder);
		}
			
		
		if(creditOrder != null && creditOrder.getId() > 0)
		{
			creditOrder.setCreditOrderState(creditOrderStateService.findById(stateId));
			creditOrder.setCreditOrderType(creditOrderTypeService.findById(typeId));
			creditOrderService.update(creditOrder);
		}
			
		return "redirect:" + "/manage/order/list";
	}
	
	@RequestMapping(value="/manage/order/delete", method=RequestMethod.POST)
    public String deleteCreditOrder(long id) {
		if(id > 0)
			creditOrderService.deleteById(id);
        return "redirect:" + "/manage/order/list";
    }
	
	@RequestMapping(value = { "/manage/order/state/list" }, method = RequestMethod.GET)
    public String listOrderStates(ModelMap model) {
 
		List<CreditOrderState> states = creditOrderStateService.findAll();
		model.addAttribute("states", states);
        
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
			model.addAttribute("orderState", creditOrderStateService.findById(stateId));
		}
		return "/manage/order/state/save";
	}
	
	@RequestMapping(value="/manage/order/state/save", method=RequestMethod.POST)
    public String saveCreditOrderState(CreditOrderState state, ModelMap model) {
		if(state != null && state.getId() == 0)
			creditOrderStateService.save(new CreditOrderState(state.getName()));
		
		if(state != null && state.getId() > 0)
			creditOrderStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/state/list";
    }
	
	@RequestMapping(value="/manage/order/state/delete", method=RequestMethod.POST)
    public String deleteCreditOrderState(long id) {
		if(id > 0)
			creditOrderStateService.deleteById(id);
        return "redirect:" + "/manage/order/state/list";
    }
	
	@RequestMapping(value = { "/manage/order/type/list" }, method = RequestMethod.GET)
    public String listOrderTypes(ModelMap model) {
 
		List<CreditOrderType> types = creditOrderTypeService.findAll();
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
			model.addAttribute("orderType", creditOrderTypeService.findById(typeId));
		}
		return "/manage/order/type/save";
	}
	
	@RequestMapping(value="/manage/order/type/save", method=RequestMethod.POST)
    public String saveCreditOrderType(CreditOrderType type,  ModelMap model) {
		if(type != null && type.getId() == 0)
			creditOrderTypeService.save(new CreditOrderType(type.getName()));
		
		if(type != null && type.getId() > 0)
			creditOrderTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/type/list";
    }
	
	@RequestMapping(value="/manage/order/type/delete", method=RequestMethod.POST)
    public String deleteCreditOrderType(long id) {
		if(id > 0)
			creditOrderTypeService.deleteById(id);
        return "redirect:" + "/manage/order/type/list";
    }
}
