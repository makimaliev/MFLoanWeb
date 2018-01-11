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

import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.model.orderterm.AgreementTemplate;
import kg.gov.mf.loan.manage.model.orderterm.OrderTerm;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermAccrMethod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermDaysMethod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFloatingRateType;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFrequencyType;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFund;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermRatePeriod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermTransactionOrder;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermAccrMethodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermDaysMethodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFloatingRateTypeService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFrequencyTypeService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFundService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermRatePeriodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermTransactionOrderService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class OrderTermController {
	
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
	CreditOrderService orderService;
	
	@Autowired
	OrderTermService orderTermService;
	
	static final Logger loggerOrderTerm = LoggerFactory.getLogger(OrderTerm.class);
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/orderterm/{termId}/view"})
    public String viewOrderTerm(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("termId")Long termId) {

		OrderTerm term = orderTermService.findById(termId);
		model.addAttribute("term", term);
		
        model.addAttribute("templates", term.getAgreementTemplate());
		
        model.addAttribute("orderId", orderId);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/view";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/{termId}/save", method=RequestMethod.GET)
	public String formOrderTerm(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("termId")Long termId)
	{
		if(termId == 0)
		{
			model.addAttribute("term", new OrderTerm());
		}
			
		if(termId > 0)
		{
			model.addAttribute("term", orderTermService.findById(termId));
		}
		model.addAttribute("orderId", orderId);
		
		List<OrderTermFund> funds = fundService.findAll();
        model.addAttribute("funds", funds);
        
        List<OrderTermCurrency> currs = currService.findAll();
        model.addAttribute("currencies", currs);
        
        List<OrderTermFrequencyType> freqTypes = freqTypeService.findAll();
        model.addAttribute("freqTypes", freqTypes);
        
        List<OrderTermRatePeriod> ratePeriods = ratePeriodService.findAll();
        model.addAttribute("ratePeriods", ratePeriods);
        
        List<OrderTermFloatingRateType> rateTypes = rateTypeService.findAll();
        model.addAttribute("rateTypes", rateTypes);
        
        List<OrderTermTransactionOrder> txOrders = txOrderService.findAll();
        model.addAttribute("txOrders", txOrders);
        
        List<OrderTermDaysMethod> daysMethods = daysMethodService.findAll();
        model.addAttribute("daysMethods", daysMethods);
        
        List<OrderTermAccrMethod> accrMethods = accrMethodService.findAll();
        model.addAttribute("accrMethods", accrMethods);
		
		return "/manage/order/orderterm/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/save", method=RequestMethod.POST)
	public String saveOrderTerm(
			OrderTerm term, 
			long fundId, 
			long currId,
			long freqTypeId, 
			long ratePeriodId,
			long interestTypeId,
			long popotId,
			long poiotId,
			long diyMethodId,
			long dimMethodId,
			long txOrderId,
			long intAccrId,
			
			@PathVariable("orderId")Long orderId, ModelMap model)
	{
		CreditOrder creditOrder = orderService.findById(orderId);
		
		if(term != null && term.getId() == 0)
		{
			OrderTerm newTerm = new OrderTerm(
					term.getDescription(), 
					fundService.findById(fundId),
					term.getAmount(),
					currService.findById(currId),
					term.getFrequencyQuantity(),
					freqTypeService.findById(freqTypeId),
					term.getInstallmentQuantity(),
					term.getInstallmentFirstDay(),
					term.getFirstInstallmentDate(),
					term.getLastInstallmentDate(),
					term.getMinDaysDisbFirstInst(),
					term.getMaxDaysDisbFirstInst(),
					term.getGraceOnPrinciplePaymentInst(),
					term.getGraceOnPrinciplePaymentDays(),
					term.getGraceOnInterestPaymentInst(),
					term.getGraceOnInterestPaymentDays(),
					term.getGraceOnInterestAccrInst(),
					term.getGraceOnInterestAccrDays(),
					term.getInterestRateValue(),
					ratePeriodService.findById(ratePeriodId),
					rateTypeService.findById(interestTypeId),
					term.getPenaltyOnPrincipleOverdueRateValue(),
					rateTypeService.findById(popotId),
					term.getPenaltyOnInterestOverdueRateValue(),
					rateTypeService.findById(poiotId),
					daysMethodService.findById(diyMethodId),
					daysMethodService.findById(dimMethodId),
					txOrderService.findById(txOrderId),
					accrMethodService.findById(intAccrId),
					term.isEarlyRepaymentAllowed(),
					term.getPenaltyLimitPercent(),
					term.isCollateralFree());
			
			newTerm.setCreditOrder(creditOrder);
			loggerOrderTerm.info("O R D E R   T E R M : {}", newTerm);
			orderTermService.save(newTerm);
		}
			
		
		if(term != null && term.getId() > 0)
		{
			term.setFund(fundService.findById(fundId));
			term.setCurrency(currService.findById(currId));
			term.setFrequencyType(freqTypeService.findById(freqTypeId));
			term.setInterestRateValuePerPeriod(ratePeriodService.findById(ratePeriodId));
			term.setInterestType(rateTypeService.findById(interestTypeId));
			term.setPenaltyOnPrincipleOverdueType(rateTypeService.findById(popotId));
			term.setPenaltyOnInterestOverdueType(rateTypeService.findById(poiotId));
			term.setDaysInYearMethod(daysMethodService.findById(diyMethodId));
			term.setDaysInMonthMethod(daysMethodService.findById(dimMethodId));
			term.setTransactionOrder(txOrderService.findById(txOrderId));
			term.setInterestAccrMethod(accrMethodService.findById(intAccrId));
			orderTermService.update(term);
		}
			
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/delete", method=RequestMethod.POST)
    public String deleteOrderTerm(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			orderTermService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
	//BEGIN - ORDER TERM FUND
	@RequestMapping(value = { "/manage/order/orderterm/fund/list" }, method = RequestMethod.GET)
    public String listOrderTermFunds(ModelMap model) {
 
		List<OrderTermFund> funds = fundService.findAll();
		model.addAttribute("funds", funds);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/fund/list";
    }
	
	@RequestMapping(value="/manage/order/orderterm/fund/{fundId}/save", method=RequestMethod.GET)
	public String formOrderTermFund(ModelMap model, @PathVariable("fundId")Long fundId)
	{
		if(fundId == 0)
		{
			model.addAttribute("fund", new OrderTermFund());
		}
		
		if(fundId > 0)
		{
			model.addAttribute("fund", fundService.findById(fundId));
		}
		return "/manage/order/orderterm/fund/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/fund/save", method=RequestMethod.POST)
    public String saveOrderTermFund(OrderTermFund fund, ModelMap model) {
		if(fund != null && fund.getId() == 0)
			fundService.save(new OrderTermFund(fund.getName()));
		
		if(fund != null && fund.getId() > 0)
			fundService.update(fund);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/fund/list";
    }

	@RequestMapping(value="/manage/order/orderterm/fund/delete", method=RequestMethod.POST)
    public String deleteOrderTermFund(long id) {
		if(id > 0)
			fundService.deleteById(id);
		return "redirect:" + "/manage/order/orderterm/fund/list";
    }
	//END - ORDER TERM FUND
	
	//BEGIN - ORDER TERM CURRENCY
	@RequestMapping(value = { "/manage/order/orderterm/currency/list" }, method = RequestMethod.GET)
    public String listOrderTermCurrencies(ModelMap model) {
 
		List<OrderTermCurrency> currencies = currService.findAll();
		model.addAttribute("currencies", currencies);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/currency/list";
    }
	
	@RequestMapping(value="/manage/order/orderterm/currency/{currId}/save", method=RequestMethod.GET)
	public String formOrderTermCurrency(ModelMap model, @PathVariable("currId")Long currId)
	{
		if(currId == 0)
		{
			model.addAttribute("currency", new OrderTermCurrency());
		}
		
		if(currId > 0)
		{
			model.addAttribute("currency", currService.findById(currId));
		}
		return "/manage/order/orderterm/currency/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/currency/save", method=RequestMethod.POST)
    public String saveOrderTermCurrency(OrderTermCurrency curr, ModelMap model) {
		if(curr != null && curr.getId() == 0)
			currService.save(new OrderTermCurrency(curr.getName()));
		
		if(curr != null && curr.getId() > 0)
			currService.update(curr);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/currency/list";
    }

	@RequestMapping(value="/manage/order/orderterm/currency/delete", method=RequestMethod.POST)
    public String deleteOrderTermCurrency(long id) {
		if(id > 0)
			currService.deleteById(id);
		return "redirect:" + "/manage/order/orderterm/currency/list";
    }
	//END - ORDER TERM CURRENCY
	
	//BEGIN - ORDER TERM FREQ TYPE
	@RequestMapping(value = { "/manage/order/orderterm/freqtype/list" }, method = RequestMethod.GET)
    public String listOrderTermFreqTypes(ModelMap model) {
 
		List<OrderTermFrequencyType> freqtypes = freqTypeService.findAll();
		model.addAttribute("freqtypes", freqtypes);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/freqtype/list";
    }
	
	@RequestMapping(value="/manage/order/orderterm/freqtype/{freqTypeId}/save", method=RequestMethod.GET)
	public String formOrderTermFreqType(ModelMap model, @PathVariable("freqTypeId")Long freqTypeId)
	{
		if(freqTypeId == 0)
		{
			model.addAttribute("freqtype", new OrderTermFrequencyType());
		}
		
		if(freqTypeId > 0)
		{
			model.addAttribute("freqtype", freqTypeService.findById(freqTypeId));
		}
		return "/manage/order/orderterm/freqtype/save";
	}	
	
	@RequestMapping(value="/manage/order/orderterm/freqtype/save", method=RequestMethod.POST)
    public String saveOrderTermFreqType(OrderTermFrequencyType freqType, ModelMap model) {
		if(freqType != null && freqType.getId() == 0)
			freqTypeService.save(new OrderTermFrequencyType(freqType.getName()));
		
		if(freqType != null && freqType.getId() > 0)
			freqTypeService.update(freqType);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/freqtype/list";
    }

	@RequestMapping(value="/manage/order/orderterm/freqtype/delete", method=RequestMethod.POST)
    public String deleteOrderTermFreqType(long id) {
		if(id > 0)
			freqTypeService.deleteById(id);
		return "redirect:" + "/manage/order/orderterm/freqtype/list";
    }
	//END - ORDER TERM CURRENCY
	
	//BEGIN - ORDER TERM RATE PERIOD
	@RequestMapping(value = { "/manage/order/orderterm/rateperiod/list" }, method = RequestMethod.GET)
    public String listOrderTermRatePeriods(ModelMap model) {
 
		List<OrderTermRatePeriod> rateperiods = ratePeriodService.findAll();
		model.addAttribute("rateperiods", rateperiods);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/rateperiod/list";
    }
	
	@RequestMapping(value="/manage/order/orderterm/rateperiod/{ratePeriodId}/save", method=RequestMethod.GET)
	public String formOrderTermRatePeriod(ModelMap model, @PathVariable("ratePeriodId")Long ratePeriodId)
	{
		if(ratePeriodId == 0)
		{
			model.addAttribute("rateperiod", new OrderTermRatePeriod());
		}
		
		if(ratePeriodId > 0)
		{
			model.addAttribute("rateperiod", ratePeriodService.findById(ratePeriodId));
		}
		return "/manage/order/orderterm/rateperiod/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/rateperiod/save", method=RequestMethod.POST)
    public String saveOrderTermRatePeriod(OrderTermRatePeriod ratePeriod, ModelMap model) {
		if(ratePeriod != null && ratePeriod.getId() == 0)
			ratePeriodService.save(new OrderTermRatePeriod(ratePeriod.getName()));
		
		if(ratePeriod != null && ratePeriod.getId() > 0)
			ratePeriodService.update(ratePeriod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/rateperiod/list";
    }

	@RequestMapping(value="/manage/order/orderterm/rateperiod/delete", method=RequestMethod.POST)
    public String deleteOrderTermRatePeriod(long id) {
		if(id > 0)
			ratePeriodService.deleteById(id);
		return "redirect:" + "/manage/order/orderterm/rateperiod/list";
    }
	//END - ORDER TERM RATE PERIOD
	
	//BEGIN - ORDER TERM FLOATING RATE TYPE
	@RequestMapping(value = { "/manage/order/orderterm/ratetype/list" }, method = RequestMethod.GET)
    public String listOrderTermRateTypes(ModelMap model) {
 
		List<OrderTermFloatingRateType> ratetypes = rateTypeService.findAll();
		model.addAttribute("ratetypes", ratetypes);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/ratetype/list";
    }
	
	@RequestMapping(value="/manage/order/orderterm/ratetype/{rateTypeId}/save", method=RequestMethod.GET)
	public String formOrderTermRateType(ModelMap model, @PathVariable("rateTypeId")Long rateTypeId)
	{
		if(rateTypeId == 0)
		{
			model.addAttribute("ratetype", new OrderTermFloatingRateType());
		}
		
		if(rateTypeId > 0)
		{
			model.addAttribute("ratetype", rateTypeService.findById(rateTypeId));
		}
		return "/manage/order/orderterm/ratetype/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/ratetype/save", method=RequestMethod.POST)
    public String saveOrderTermFloatingRateType(OrderTermFloatingRateType rateType, ModelMap model) {
		if(rateType != null && rateType.getId() == 0)
			rateTypeService.save(new OrderTermFloatingRateType(rateType.getName()));
		
		if(rateType != null && rateType.getId() > 0)
			rateTypeService.update(rateType);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/ratetype/list";
    }

	@RequestMapping(value="/manage/order/orderterm/ratetype/delete", method=RequestMethod.POST)
    public String deleteOrderTermFloatingRateType(long id) {
		if(id > 0)
			rateTypeService.deleteById(id);
		return "redirect:" + "/manage/order/orderterm/ratetype/list";
    }
	//END - ORDER TERM FLOATING RATE TYPE
	
	//BEGIN - ORDER TERM FLOATING TX ORDER
	@RequestMapping(value = { "/manage/order/orderterm/transactionorder/list" }, method = RequestMethod.GET)
    public String listOrderTermTXOrders(ModelMap model) {
 
		List<OrderTermTransactionOrder> txorders = txOrderService.findAll();
		model.addAttribute("txorders", txorders);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/transactionorder/list";
    }
	
	@RequestMapping(value="/manage/order/orderterm/transactionorder/{txOrderId}/save", method=RequestMethod.GET)
	public String formOrderTermTXOrder(ModelMap model, @PathVariable("txOrderId")Long txOrderId)
	{
		if(txOrderId == 0)
		{
			model.addAttribute("txorder", new OrderTermTransactionOrder());
		}
		
		if(txOrderId > 0)
		{
			model.addAttribute("txorder", txOrderService.findById(txOrderId));
		}
		return "/manage/order/orderterm/transactionorder/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/transactionorder/save", method=RequestMethod.POST)
    public String saveOrderTermTransactionOrder(OrderTermTransactionOrder txOrder, ModelMap model) {
		if(txOrder != null && txOrder.getId() == 0)
			txOrderService.save(new OrderTermTransactionOrder(txOrder.getName()));
		
		if(txOrder != null && txOrder.getId() > 0)
			txOrderService.update(txOrder);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/transactionorder/list";
    }

	@RequestMapping(value="/manage/order/orderterm/transactionorder/delete", method=RequestMethod.POST)
    public String deleteOrderTermTransactionOrder(long id) {
		if(id > 0)
			txOrderService.deleteById(id);
		return "redirect:" + "/manage/order/orderterm/transactionorder/list";
    }
	//END - ORDER TERM FLOATING TX ORDER
	
	//BEGIN - ORDER TERM FLOATING DAYS METHOD
	@RequestMapping(value = { "/manage/order/orderterm/daysmethod/list" }, method = RequestMethod.GET)
    public String listOrderTermDaysMethods(ModelMap model) {
 
		List<OrderTermDaysMethod> dMethods = daysMethodService.findAll();
		model.addAttribute("dmethods", dMethods);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/daysmethod/list";
    }
	
	@RequestMapping(value="/manage/order/orderterm/daysmethod/{dMethodId}/save", method=RequestMethod.GET)
	public String formOrderTermDaysMethod(ModelMap model, @PathVariable("dMethodId")Long dMethodId)
	{
		if(dMethodId == 0)
		{
			model.addAttribute("dmethod", new OrderTermDaysMethod());
		}
		
		if(dMethodId > 0)
		{
			model.addAttribute("dmethod", daysMethodService.findById(dMethodId));
		}
		return "/manage/order/orderterm/daysmethod/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/daysmethod/save", method=RequestMethod.POST)
    public String saveOrderTermDaysMethod(OrderTermDaysMethod dMethod, ModelMap model) {
		if(dMethod != null && dMethod.getId() == 0)
			daysMethodService.save(new OrderTermDaysMethod(dMethod.getName()));
		
		if(dMethod != null && dMethod.getId() > 0)
			daysMethodService.update(dMethod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/daysmethod/list";
    }

	@RequestMapping(value="/manage/order/orderterm/daysmethod/delete", method=RequestMethod.POST)
    public String deleteOrderTermDaysMethod(long id) {
		if(id > 0)
			daysMethodService.deleteById(id);
		return "redirect:" + "/manage/order/orderterm/daysmethod/list";
    }
	//END - ORDER TERM FLOATING DAYS METHOD
	
	//BEGIN - ORDER TERM FLOATING ACCR METHOD
	@RequestMapping(value = { "/manage/order/orderterm/accrmethod/list" }, method = RequestMethod.GET)
    public String listOrderTermAccrMethods(ModelMap model) {
 
		List<OrderTermAccrMethod> aMethods = accrMethodService.findAll();
		model.addAttribute("amethods", aMethods);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/accrmethod/list";
    }
	
	@RequestMapping(value="/manage/order/orderterm/accrmethod/{aMethodId}/save", method=RequestMethod.GET)
	public String formOrderTermAccrMethod(ModelMap model, @PathVariable("aMethodId")Long aMethodId)
	{
		if(aMethodId == 0)
		{
			model.addAttribute("amethod", new OrderTermAccrMethod());
		}
		
		if(aMethodId > 0)
		{
			model.addAttribute("amethod", accrMethodService.findById(aMethodId));
		}
		return "/manage/order/orderterm/accrmethod/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/accrmethod/save", method=RequestMethod.POST)
    public String saveOrderTermAccrMethod(OrderTermAccrMethod accrMethod, ModelMap model) {
		if(accrMethod != null && accrMethod.getId() == 0)
			accrMethodService.save(new OrderTermAccrMethod(accrMethod.getName()));
		
		if(accrMethod != null && accrMethod.getId() > 0)
			accrMethodService.update(accrMethod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/accrmethod/list";
    }

	@RequestMapping(value="/manage/order/orderterm/accrmethod/delete", method=RequestMethod.POST)
    public String deleteOrderTermAccrMethod(long id) {
		if(id > 0)
			accrMethodService.deleteById(id);
		return "redirect:" + "/manage/order/orderterm/accrmethod/list";
    }
	//END - ORDER TERM FLOATING ACCR METHOD
}
