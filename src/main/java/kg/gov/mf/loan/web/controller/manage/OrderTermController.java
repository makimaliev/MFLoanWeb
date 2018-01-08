package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

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
		
		model.addAttribute("emptyTemplate", new AgreementTemplate());
        model.addAttribute("templates", term.getAgreementTemplate());
		
        model.addAttribute("orderId", orderId);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderterm/view";
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
		loggerOrderTerm.info("Order Term : {}", term);
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
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/fund/save", method=RequestMethod.POST)
    public String saveOrderTermFund(OrderTermFund fund, @PathVariable("orderId")Long orderId, ModelMap model) {
		if(fund != null && fund.getId() == 0)
			fundService.save(new OrderTermFund(fund.getName()));
		
		if(fund != null && fund.getId() > 0)
			fundService.update(fund);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }

	@RequestMapping(value="/manage/order/{orderId}/orderterm/fund/delete", method=RequestMethod.POST)
    public String deleteOrderTermFund(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			fundService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/currency/save", method=RequestMethod.POST)
    public String saveOrderTermCurrency(OrderTermCurrency curr, @PathVariable("orderId")Long orderId, ModelMap model) {
		if(curr != null && curr.getId() == 0)
			currService.save(new OrderTermCurrency(curr.getName()));
		
		if(curr != null && curr.getId() > 0)
			currService.update(curr);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }

	@RequestMapping(value="/manage/order/{orderId}/orderterm/currency/delete", method=RequestMethod.POST)
    public String deleteOrderTermCurrency(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			currService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/freqtype/save", method=RequestMethod.POST)
    public String saveOrderTermFreqType(OrderTermFrequencyType freqType, @PathVariable("orderId")Long orderId, ModelMap model) {
		if(freqType != null && freqType.getId() == 0)
			freqTypeService.save(new OrderTermFrequencyType(freqType.getName()));
		
		if(freqType != null && freqType.getId() > 0)
			freqTypeService.update(freqType);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }

	@RequestMapping(value="/manage/order/{orderId}/orderterm/freqtype/delete", method=RequestMethod.POST)
    public String deleteOrderTermFreqType(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			freqTypeService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/rateperiod/save", method=RequestMethod.POST)
    public String saveOrderTermRatePeriod(OrderTermRatePeriod ratePeriod, @PathVariable("orderId")Long orderId, ModelMap model) {
		if(ratePeriod != null && ratePeriod.getId() == 0)
			ratePeriodService.save(new OrderTermRatePeriod(ratePeriod.getName()));
		
		if(ratePeriod != null && ratePeriod.getId() > 0)
			ratePeriodService.update(ratePeriod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }

	@RequestMapping(value="/manage/order/{orderId}/orderterm/rateperiod/delete", method=RequestMethod.POST)
    public String deleteOrderTermRatePeriod(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			ratePeriodService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/ratetype/save", method=RequestMethod.POST)
    public String saveOrderTermFloatingRateType(OrderTermFloatingRateType rateType, @PathVariable("orderId")Long orderId, ModelMap model) {
		if(rateType != null && rateType.getId() == 0)
			rateTypeService.save(new OrderTermFloatingRateType(rateType.getName()));
		
		if(rateType != null && rateType.getId() > 0)
			rateTypeService.update(rateType);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }

	@RequestMapping(value="/manage/order/{orderId}/orderterm/ratetype/delete", method=RequestMethod.POST)
    public String deleteOrderTermFloatingRateType(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			rateTypeService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/transactionorder/save", method=RequestMethod.POST)
    public String saveOrderTermTransactionOrder(OrderTermTransactionOrder txOrder, @PathVariable("orderId")Long orderId, ModelMap model) {
		if(txOrder != null && txOrder.getId() == 0)
			txOrderService.save(new OrderTermTransactionOrder(txOrder.getName()));
		
		if(txOrder != null && txOrder.getId() > 0)
			txOrderService.update(txOrder);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }

	@RequestMapping(value="/manage/order/{orderId}/orderterm/transactionorder/delete", method=RequestMethod.POST)
    public String deleteOrderTermTransactionOrder(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			txOrderService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/daysmethod/save", method=RequestMethod.POST)
    public String saveOrderTermDaysMethod(OrderTermDaysMethod dMethod, @PathVariable("orderId")Long orderId, ModelMap model) {
		if(dMethod != null && dMethod.getId() == 0)
			daysMethodService.save(new OrderTermDaysMethod(dMethod.getName()));
		
		if(dMethod != null && dMethod.getId() > 0)
			daysMethodService.update(dMethod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }

	@RequestMapping(value="/manage/order/{orderId}/orderterm/daysmethod/delete", method=RequestMethod.POST)
    public String deleteOrderTermDaysMethod(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			daysMethodService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderterm/accrmethod/save", method=RequestMethod.POST)
    public String saveOrderTermAccrMethod(OrderTermAccrMethod accrMethod, @PathVariable("orderId")Long orderId, ModelMap model) {
		if(accrMethod != null && accrMethod.getId() == 0)
			accrMethodService.save(new OrderTermAccrMethod(accrMethod.getName()));
		
		if(accrMethod != null && accrMethod.getId() > 0)
			accrMethodService.update(accrMethod);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }

	@RequestMapping(value="/manage/order/{orderId}/orderterm/accrmethod/delete", method=RequestMethod.POST)
    public String deleteOrderTermAccrMethod(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0)
			accrMethodService.deleteById(id);
		return "redirect:" + "/manage/order/{orderId}/view#tab_2";
    }
	
}
