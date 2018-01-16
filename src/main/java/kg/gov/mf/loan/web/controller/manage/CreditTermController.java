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
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("loanId")Long loanId,
    		ModelMap model) {
		
		Loan loan = loanService.getById(loanId);
		term.setLoan(loan);
		
		if(term.getId() == null || term.getId() == 0)
			termService.add(term);
		else
			termService.update(term);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/term/delete", method=RequestMethod.POST)
    public String deleteCreditTerm(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			termService.remove(termService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
}
