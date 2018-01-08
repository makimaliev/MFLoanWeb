package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.loan.CreditTerm;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.loan.CreditTermService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermDaysMethodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFloatingRateTypeService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermRatePeriodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermTransactionOrderService;

@Controller
public class CreditTermController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	CreditTermService termService;	
	
	@Autowired
	OrderTermRatePeriodService ratePeriodService;
	
	@Autowired
	OrderTermFloatingRateTypeService rateTypeService;
	
	@Autowired
	OrderTermTransactionOrderService txOrderService;
	
	@Autowired
	OrderTermDaysMethodService daysMethodService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/term/save"})
    public String saveCreditTerm(CreditTerm term,
    		long ratePeriodId,
    		long rateTypeId,
    		long popotId,
    		long poiotId,
    		long txOrderId,
    		long dimmId,
    		long diymId,
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("loanId")Long loanId,
    		ModelMap model) {
		
		Loan loan = loanService.findById(loanId);
		
		if(term != null && term.getId() == 0)
		{
			CreditTerm newTerm  = new CreditTerm(
					term.getStartDate(), 
					term.getInterestRateValue(),
					ratePeriodService.findById(ratePeriodId),
					rateTypeService.findById(rateTypeId),
					term.getPenaltyOnPrincipleOverdueRateValue(),
					rateTypeService.findById(popotId),
					term.getPenaltyOnInterestOverdueRateValue(),
					rateTypeService.findById(poiotId),
					term.getPenaltyLimitPercent(),
					term.getPenaltyLimitEndDate(),
					txOrderService.findById(txOrderId),
					daysMethodService.findById(dimmId),
					daysMethodService.findById(diymId));
			
			newTerm.setLoan(loan);
			termService.save(newTerm);
		}
		
		if(term != null && term.getId() > 0)
		{
			term.setRatePeriod(ratePeriodService.findById(ratePeriodId));
			term.setFloatingRateType(rateTypeService.findById(rateTypeId));
			term.setPenaltyOnPrincipleOverdueRateType(rateTypeService.findById(popotId));
			term.setPenaltyOnInterestOverdueRateType(rateTypeService.findById(poiotId));
			term.setTransactionOrder(txOrderService.findById(txOrderId));
			term.setDaysInMonthMethod(daysMethodService.findById(dimmId));
			term.setDaysInYearMethod(daysMethodService.findById(diymId));
			termService.update(term);
		}
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/term/delete", method=RequestMethod.POST)
    public String deleteCreditTerm(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			termService.deleteById(id);
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
}
