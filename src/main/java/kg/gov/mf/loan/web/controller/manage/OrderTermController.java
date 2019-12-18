package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.model.orderterm.*;
import kg.gov.mf.loan.manage.repository.orderterm.OrderTermRepository;
import kg.gov.mf.loan.manage.service.loan.InstallmentStateService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.orderterm.*;
import kg.gov.mf.loan.web.fetchModels.AgreementTemplateModel;
import kg.gov.mf.loan.web.fetchModels.OrderTermMetaModel;
import kg.gov.mf.loan.web.fetchModels.OrderTermModel;
import kg.gov.mf.loan.web.fetchModels.PaymentScheduleModel;
import kg.gov.mf.loan.web.util.Meta;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

	@Autowired
	OrderTermRepository orderTermRepository;

	@Autowired
	InstallmentStateService installmentStateService;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/orderterm/{termId}/view"})
    public String viewOrderTerm(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("termId")Long termId) {

		OrderTerm term = orderTermService.getById(termId);
		model.addAttribute("term", term);

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonTemplates = gson.toJson(getTemplatesByTermId(termId));
		model.addAttribute("templates", jsonTemplates);
		
        model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
        
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
			model.addAttribute("term", orderTermService.getById(termId));
		}

		model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));

		List<OrderTermFund> funds = fundService.list();
        model.addAttribute("funds", funds);
        
        List<OrderTermCurrency> currs = currService.list();
        model.addAttribute("currencies", currs);
        
        List<OrderTermFrequencyType> freqTypes = freqTypeService.list();
        model.addAttribute("freqTypes", freqTypes);
        
        List<OrderTermRatePeriod> ratePeriods = ratePeriodService.list();
        model.addAttribute("ratePeriods", ratePeriods);
        
        List<OrderTermFloatingRateType> rateTypes = rateTypeService.list();
        model.addAttribute("rateTypes", rateTypes);
        
        List<OrderTermTransactionOrder> txOrders = txOrderService.list();
        model.addAttribute("txOrders", txOrders);
        
        List<OrderTermDaysMethod> daysMethods = daysMethodService.list();
        model.addAttribute("daysMethods", daysMethods);
        
        List<OrderTermAccrMethod> accrMethods = accrMethodService.list();
        model.addAttribute("accrMethods", accrMethods);
		
		return "/manage/order/orderterm/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/save", method=RequestMethod.POST)
	public String saveOrderTerm(
			OrderTerm term, 
			@PathVariable("orderId")Long orderId, ModelMap model)
	{
		CreditOrder creditOrder = orderService.getById(orderId);
		term.setCreditOrder(creditOrder);
		
		if(term.getId() == 0)
		{
			term.setFund(fundService.getById(term.getFund().getId()));
			term.setCurrency(currService.getById(term.getCurrency().getId()));
			term.setFrequencyType(freqTypeService.getById(term.getFrequencyType().getId()));
			term.setInterestRateValuePerPeriod(ratePeriodService.getById(term.getInterestRateValuePerPeriod().getId()));
			term.setInterestType(rateTypeService.getById(term.getInterestType().getId()));
			term.setPenaltyOnPrincipleOverdueType(rateTypeService.getById(term.getPenaltyOnPrincipleOverdueType().getId()));
			term.setPenaltyOnInterestOverdueType(rateTypeService.getById(term.getPenaltyOnInterestOverdueType().getId()));
			term.setDaysInYearMethod(daysMethodService.getById(term.getDaysInYearMethod().getId()));
			term.setDaysInMonthMethod(daysMethodService.getById(term.getDaysInMonthMethod().getId()));
			term.setTransactionOrder(txOrderService.getById(term.getTransactionOrder().getId()));
			term.setInterestAccrMethod(accrMethodService.getById(term.getInterestAccrMethod().getId()));
			orderTermService.add(term);
		}

		else
		{
			term.setFund(fundService.getById(term.getFund().getId()));
			term.setCurrency(currService.getById(term.getCurrency().getId()));
			term.setFrequencyType(freqTypeService.getById(term.getFrequencyType().getId()));
			term.setInterestRateValuePerPeriod(ratePeriodService.getById(term.getInterestRateValuePerPeriod().getId()));
			term.setInterestType(rateTypeService.getById(term.getInterestType().getId()));
			term.setPenaltyOnPrincipleOverdueType(rateTypeService.getById(term.getPenaltyOnPrincipleOverdueType().getId()));
			term.setPenaltyOnInterestOverdueType(rateTypeService.getById(term.getPenaltyOnInterestOverdueType().getId()));
			term.setDaysInYearMethod(daysMethodService.getById(term.getDaysInYearMethod().getId()));
			term.setDaysInMonthMethod(daysMethodService.getById(term.getDaysInMonthMethod().getId()));
			term.setTransactionOrder(txOrderService.getById(term.getTransactionOrder().getId()));
			term.setInterestAccrMethod(accrMethodService.getById(term.getInterestAccrMethod().getId()));
			orderTermService.update(term);
		}
			
		return "redirect:" + "/manage/order/{orderId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/{termId}/delete", method=RequestMethod.GET)
    public String deleteOrderTerm(@PathVariable("orderId")Long orderId, @PathVariable("termId")Long termId) {
		if(termId > 0)
			orderTermRepository.delete(orderTermRepository.findOne(termId));
		return "redirect:" + "/manage/order/{orderId}/view";
    }
	
	//BEGIN - ORDER TERM FUND
	@RequestMapping(value = { "/manage/order/orderterm/fund/list" }, method = RequestMethod.GET)
    public String listOrderTermFunds(ModelMap model) {
 
		List<OrderTermFund> funds = fundService.list();
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
			model.addAttribute("fund", fundService.getById(fundId));
		}
		return "/manage/order/orderterm/fund/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/fund/save", method=RequestMethod.POST)
    public String saveOrderTermFund(OrderTermFund fund, ModelMap model) {
		if(fund.getId() == 0)
			fundService.add(fund);
		else
			fundService.update(fund);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/fund/list";
    }

	@RequestMapping(value="/manage/order/orderterm/fund/delete", method=RequestMethod.POST)
    public String deleteOrderTermFund(long id) {
		if(id > 0)
			fundService.remove(fundService.getById(id));
		return "redirect:" + "/manage/order/orderterm/fund/list";
    }
	//END - ORDER TERM FUND
	
	//BEGIN - ORDER TERM CURRENCY
	@RequestMapping(value = { "/manage/order/orderterm/currency/list" }, method = RequestMethod.GET)
    public String listOrderTermCurrencies(ModelMap model) {
 
		List<OrderTermCurrency> currencies = currService.list();
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
			model.addAttribute("currency", currService.getById(currId));
		}
		return "/manage/order/orderterm/currency/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/currency/save", method=RequestMethod.POST)
    public String saveOrderTermCurrency(OrderTermCurrency curr, ModelMap model) {
		if(curr.getId() == 0)
			currService.add(curr);
		else
			currService.update(curr);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/currency/list";
    }

	@RequestMapping(value="/manage/order/orderterm/currency/delete", method=RequestMethod.POST)
    public String deleteOrderTermCurrency(long id) {
		if(id > 0)
			currService.remove(currService.getById(id));
		return "redirect:" + "/manage/order/orderterm/currency/list";
    }
	//END - ORDER TERM CURRENCY
	
	//BEGIN - ORDER TERM FREQ TYPE
	@RequestMapping(value = { "/manage/order/orderterm/freqtype/list" }, method = RequestMethod.GET)
    public String listOrderTermFreqTypes(ModelMap model) {
 
		List<OrderTermFrequencyType> freqtypes = freqTypeService.list();
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
			model.addAttribute("freqtype", freqTypeService.getById(freqTypeId));
		}
		return "/manage/order/orderterm/freqtype/save";
	}	
	
	@RequestMapping(value="/manage/order/orderterm/freqtype/save", method=RequestMethod.POST)
    public String saveOrderTermFreqType(OrderTermFrequencyType freqType, ModelMap model) {
		if(freqType.getId() == 0)
			freqTypeService.add(freqType);
		else
			freqTypeService.update(freqType);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/freqtype/list";
    }

	@RequestMapping(value="/manage/order/orderterm/freqtype/delete", method=RequestMethod.POST)
    public String deleteOrderTermFreqType(long id) {
		if(id > 0)
			freqTypeService.remove(freqTypeService.getById(id));
		return "redirect:" + "/manage/order/orderterm/freqtype/list";
    }
	//END - ORDER TERM CURRENCY
	
	//BEGIN - ORDER TERM RATE PERIOD
	@RequestMapping(value = { "/manage/order/orderterm/rateperiod/list" }, method = RequestMethod.GET)
    public String listOrderTermRatePeriods(ModelMap model) {
 
		List<OrderTermRatePeriod> rateperiods = ratePeriodService.list();
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
			model.addAttribute("rateperiod", ratePeriodService.getById(ratePeriodId));
		}
		return "/manage/order/orderterm/rateperiod/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/rateperiod/save", method=RequestMethod.POST)
    public String saveOrderTermRatePeriod(OrderTermRatePeriod ratePeriod, ModelMap model) {
		if(ratePeriod.getId() == 0)
			ratePeriodService.add(ratePeriod);
		else
			ratePeriodService.update(ratePeriod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/rateperiod/list";
    }

	@RequestMapping(value="/manage/order/orderterm/rateperiod/delete", method=RequestMethod.POST)
    public String deleteOrderTermRatePeriod(long id) {
		if(id > 0)
			ratePeriodService.remove(ratePeriodService.getById(id));
		return "redirect:" + "/manage/order/orderterm/rateperiod/list";
    }
	//END - ORDER TERM RATE PERIOD
	
	//BEGIN - ORDER TERM FLOATING RATE TYPE
	@RequestMapping(value = { "/manage/order/orderterm/ratetype/list" }, method = RequestMethod.GET)
    public String listOrderTermRateTypes(ModelMap model) {
 
		List<OrderTermFloatingRateType> ratetypes = rateTypeService.list();
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
			model.addAttribute("ratetype", rateTypeService.getById(rateTypeId));
		}
		return "/manage/order/orderterm/ratetype/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/ratetype/save", method=RequestMethod.POST)
    public String saveOrderTermFloatingRateType(OrderTermFloatingRateType rateType, ModelMap model) {
		if(rateType.getId() == 0)
			rateTypeService.add(rateType);
		else
			rateTypeService.update(rateType);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/ratetype/list";
    }

	@RequestMapping(value="/manage/order/orderterm/ratetype/delete", method=RequestMethod.POST)
    public String deleteOrderTermFloatingRateType(long id) {
		if(id > 0)
			rateTypeService.remove(rateTypeService.getById(id));
		return "redirect:" + "/manage/order/orderterm/ratetype/list";
    }
	//END - ORDER TERM FLOATING RATE TYPE
	
	//BEGIN - ORDER TERM FLOATING TX ORDER
	@RequestMapping(value = { "/manage/order/orderterm/transactionorder/list" }, method = RequestMethod.GET)
    public String listOrderTermTXOrders(ModelMap model) {
 
		List<OrderTermTransactionOrder> txorders = txOrderService.list();
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
			model.addAttribute("txorder", txOrderService.getById(txOrderId));
		}
		return "/manage/order/orderterm/transactionorder/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/transactionorder/save", method=RequestMethod.POST)
    public String saveOrderTermTransactionOrder(OrderTermTransactionOrder txOrder, ModelMap model) {
		if(txOrder.getId() == 0)
			txOrderService.add(txOrder);
		else
			txOrderService.update(txOrder);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/transactionorder/list";
    }

	@RequestMapping(value="/manage/order/orderterm/transactionorder/delete", method=RequestMethod.POST)
    public String deleteOrderTermTransactionOrder(long id) {
		if(id > 0)
			txOrderService.remove(txOrderService.getById(id));
		return "redirect:" + "/manage/order/orderterm/transactionorder/list";
    }
	//END - ORDER TERM FLOATING TX ORDER
	
	//BEGIN - ORDER TERM FLOATING DAYS METHOD
	@RequestMapping(value = { "/manage/order/orderterm/daysmethod/list" }, method = RequestMethod.GET)
    public String listOrderTermDaysMethods(ModelMap model) {
 
		List<OrderTermDaysMethod> dMethods = daysMethodService.list();
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
			model.addAttribute("dmethod", daysMethodService.getById(dMethodId));
		}
		return "/manage/order/orderterm/daysmethod/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/daysmethod/save", method=RequestMethod.POST)
    public String saveOrderTermDaysMethod(OrderTermDaysMethod dMethod, ModelMap model) {
		if(dMethod.getId() == 0)
			daysMethodService.add(dMethod);
		else
			daysMethodService.update(dMethod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/daysmethod/list";
    }

	@RequestMapping(value="/manage/order/orderterm/daysmethod/delete", method=RequestMethod.POST)
    public String deleteOrderTermDaysMethod(long id) {
		if(id > 0)
			daysMethodService.remove(daysMethodService.getById(id));
		return "redirect:" + "/manage/order/orderterm/daysmethod/list";
    }
	//END - ORDER TERM FLOATING DAYS METHOD
	
	//BEGIN - ORDER TERM FLOATING ACCR METHOD
	@RequestMapping(value = { "/manage/order/orderterm/accrmethod/list" }, method = RequestMethod.GET)
    public String listOrderTermAccrMethods(ModelMap model) {
 
		List<OrderTermAccrMethod> aMethods = accrMethodService.list();
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
			model.addAttribute("amethod", accrMethodService.getById(aMethodId));
		}
		return "/manage/order/orderterm/accrmethod/save";
	}
	
	@RequestMapping(value="/manage/order/orderterm/accrmethod/save", method=RequestMethod.POST)
    public String saveOrderTermAccrMethod(OrderTermAccrMethod accrMethod, ModelMap model) {
		if(accrMethod.getId() == 0)
			accrMethodService.add(accrMethod);
		else
			accrMethodService.update(accrMethod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderterm/accrmethod/list";
    }

	@RequestMapping(value="/manage/order/orderterm/accrmethod/delete", method=RequestMethod.POST)
    public String deleteOrderTermAccrMethod(long id) {
		if(id > 0)
			accrMethodService.remove(accrMethodService.getById(id));
		return "redirect:" + "/manage/order/orderterm/accrmethod/list";
    }
	//END - ORDER TERM FLOATING ACCR METHOD


	//region Calculator

	@GetMapping("/orderTerm/{orderTermId}/calculateForm")
	public String getOrderTermCalculateForm(@PathVariable("orderTermId") Long orderTermId, Model model){

		OrderTerm orderTerm = orderTermService.getById(orderTermId);
		model.addAttribute("term",orderTerm);

		return "/manage/order/orderterm/orderTermCalculateForm";
	}

	@GetMapping("/manage/orderTerm/{orderTermId}/calculate")
	public String getListOfPaymentSchedules(@PathVariable("orderTermId") Long orderTermId, Model model,Double amount){


		String getSchedulesQuery = "select id,expectedDate,disbursement,principalPayment,interestPayment,\n" +
				"       collectedInterestPayment,collectedPenaltyPayment,installmentStateId,\n" +
				"       record_status, null as installmentStateName, 0 as counter\n" +
				"from paymentSchedule limit 10";
		Query query = entityManager.createNativeQuery(getSchedulesQuery,PaymentScheduleModel.class);
		List<PaymentScheduleModel> paymentScheduleList = query.getResultList();

		for(PaymentScheduleModel paymentScheduleModel: paymentScheduleList)
		{
			paymentScheduleModel.setInstallmentStateName(installmentStateService.getById(paymentScheduleModel.getInstallmentStateId()).getName());
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();
		String result = gson.toJson(paymentScheduleList);

		model.addAttribute("jsonPaymentSchedules",result);
		model.addAttribute("amount",amount);
		model.addAttribute("orderTermId",orderTermId);

		return "/manage/order/orderterm/orderTermPaymentScheduleView";
	}

	//visualize payment schedules
	@GetMapping("/manage/orderTerm/{orderTermId}/visualize")
	public String visualizePaymentSchedulesOfOrderTerm(ModelMap model, @PathVariable("orderTermId")Long orderTermId){


		OrderTerm orderTerm = orderTermService.getById(orderTermId);

		model.addAttribute("term", orderTerm);

		List<OrderTermFund> funds = fundService.list();
		model.addAttribute("funds", funds);

		List<OrderTermCurrency> currs = currService.list();
		model.addAttribute("currencies", currs);

		List<OrderTermFrequencyType> freqTypes = freqTypeService.list();
		model.addAttribute("freqTypes", freqTypes);

		List<OrderTermRatePeriod> ratePeriods = ratePeriodService.list();
		model.addAttribute("ratePeriods", ratePeriods);

		List<OrderTermFloatingRateType> rateTypes = rateTypeService.list();
		model.addAttribute("rateTypes", rateTypes);

		List<OrderTermTransactionOrder> txOrders = txOrderService.list();
		model.addAttribute("txOrders", txOrders);

		List<OrderTermDaysMethod> daysMethods = daysMethodService.list();
		model.addAttribute("daysMethods", daysMethods);

		List<OrderTermAccrMethod> accrMethods = accrMethodService.list();
		model.addAttribute("accrMethods", accrMethods);

		return "/manage/order/orderterm/visualizePaymentSchedules";
	}

	@GetMapping("/manage/orderTerm/getListDiv")
	public String getListToDiv(Model model, OrderTerm orderTerm){

			String getSchedulesQuery = "select id,expectedDate,disbursement,principalPayment,interestPayment,\n" +
				"       collectedInterestPayment,collectedPenaltyPayment,installmentStateId,\n" +
				"       record_status, null as installmentStateName, 0 as counter\n" +
				"from paymentSchedule limit 10";
		Query query = entityManager.createNativeQuery(getSchedulesQuery,PaymentScheduleModel.class);
		List<PaymentScheduleModel> paymentScheduleList = query.getResultList();

		for(PaymentScheduleModel paymentScheduleModel: paymentScheduleList)
		{
			paymentScheduleModel.setInstallmentStateName(installmentStateService.getById(paymentScheduleModel.getInstallmentStateId()).getName());
		}
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String result = gson.toJson(paymentScheduleList);

		model.addAttribute("jsonPaymentSchedules",result);
		model.addAttribute("orderTermId",orderTerm);

		return "/manage/order/orderterm/showPaymentSchedules";
	}

	//endregion

	private List<AgreementTemplateModel> getTemplatesByTermId(long termId)
	{
		String baseQuery = "SELECT tmp.id,\n" +
				"  tmp.name,\n" +
				"  tmp.createdBy,\n" +
				"  tmp.createdDate,\n" +
				"  tmp.createdDescription,\n" +
				"  tmp.approvedBy,\n" +
				"  tmp.approvedDate,\n" +
				"  tmp.approvedDescription\n" +
				"FROM agreementTemplate tmp\n" +
				"WHERE tmp.orderTermId = " + termId;

		Query query = entityManager.createNativeQuery(baseQuery, AgreementTemplateModel.class);

		List<AgreementTemplateModel> templates = query.getResultList();
		return templates;
	}

	@GetMapping("/orderterm/list")
	public String getAllTerms(Model model){


		return "/output/report/orderTermViewList";
	}


//	**********************************************************************
//	REST
//	**********************************************************************

	@PostMapping("/api/orderterms")
	@ResponseBody
	public OrderTermMetaModel getListOfOrderTerms(@RequestParam Map<String,String> datatable){

		String pageStr = datatable.get("datatable[pagination][page]");
		String perPageStr = datatable.get("datatable[pagination][perpage]");
		String sortStr = datatable.get("datatable[sort][sort]");
		String sortField = datatable.get("datatable[sort][field]");

		boolean isSearch= datatable.containsKey("datatable[query][generalSearch]");
		if(isSearch){

		}

		Integer page = Integer.parseInt(pageStr);
		Integer perPage =Integer.parseInt(perPageStr);
		Integer offset = (page-1)*perPage;


		String baseQuery = "select *, null as fundName, null as currencyName, null as frequencyTypeName, null as interestRateValuePerPeriodName, null as interestTypeName, null as penaltyOnPrincipleOverdueTypeName,\n" +
				"    null as penaltyOnInterestOverdueTypeName, null as daysInYearMethodName, null as daysInMonthMethodName, null as transactionOrderName, null as interestAccrMethodName\n" +
				"    from orderTerm order by id desc limit 100 ";

		Query query = entityManager.createNativeQuery(baseQuery,OrderTermModel.class);
		List<OrderTermModel> all = query.getResultList();

		BigInteger count= BigInteger.valueOf(0);
		if (all.size()==100){
			count=BigInteger.valueOf(100);
		}
		else{
			count=BigInteger.valueOf(all.size());

		}

		Meta meta=new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

		OrderTermMetaModel metaModel=new OrderTermMetaModel();
		metaModel.setMeta(meta);

		if(all.size()>perPage){
			metaModel.setData(all.subList(offset,perPage+offset));
		}
		else{
			metaModel.setData(all);
		}

//		metaModel.setData(all);


		return metaModel;
	}
}
