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
	
	@RequestMapping(value = { "/manage/order/{orderId}/view"})
    public String viewOrder(ModelMap model, @PathVariable("orderId")Long orderId) {

		CreditOrder order = creditOrderService.getById(orderId);
        model.addAttribute("order", order);
        
        model.addAttribute("entityLists", order.getAppliedEntityLists());
        model.addAttribute("orderDocumentPackages", order.getOrderDocumentPackages());
        model.addAttribute("orderTerms", order.getOrderTerms());
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/view";
    }
	
	@RequestMapping(value = {"/manage/order/list" }, method = RequestMethod.GET)
    public String listOrders(@RequestParam("pageSize") Optional<Integer> pageSize,
							 @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<CreditOrder> orders = creditOrderService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = creditOrderService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
        model.addAttribute("orders", orders);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

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
		List<CreditOrderState> states = creditOrderStateService.list();
		List<CreditOrderType> types = creditOrderTypeService.list();
		model.addAttribute("states", states);
		model.addAttribute("types", types);
		return "/manage/order/save";
	}
	
	@RequestMapping(value="/manage/order/save", method=RequestMethod.POST)
	public String saveCreditOrder(CreditOrder creditOrder)
	{
		loggerOrder.info("Order : {}", creditOrder);
		if(creditOrder.getId() == 0)
			creditOrderService.add(creditOrder);
		else
			creditOrderService.update(creditOrder);
			
		return "redirect:" + "/manage/order/list";
	}
	
	@RequestMapping(value="/manage/order/delete", method=RequestMethod.POST)
    public String deleteCreditOrder(long id) {
		if(id > 0)
			creditOrderService.remove(creditOrderService.getById(id));
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
}
