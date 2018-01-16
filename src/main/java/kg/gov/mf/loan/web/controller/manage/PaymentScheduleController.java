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

import kg.gov.mf.loan.manage.model.loan.InstallmentState;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.PaymentSchedule;
import kg.gov.mf.loan.manage.service.loan.InstallmentStateService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.PaymentScheduleService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class PaymentScheduleController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	PaymentScheduleService paymentScheduleService;
	
	@Autowired
	InstallmentStateService installmentStateService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/save"})
    public String savePaymentSchedule(PaymentSchedule paymentSchedule, 
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("loanId")Long loanId, 
    		ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		paymentSchedule.setLoan(loan);
		
		if(paymentSchedule.getId() == null || paymentSchedule.getId() == 0)
			paymentScheduleService.add(paymentSchedule);
		else
			paymentScheduleService.update(paymentSchedule);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/delete", method=RequestMethod.POST)
    public String deletePaymentSchedule(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			paymentScheduleService.remove(paymentScheduleService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_2";
    }

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/installmentstate/save", method=RequestMethod.POST)
    public String saveInstallmentState(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, InstallmentState state, ModelMap model) {
		if(state.getId() == null || state.getId() == 0)
			installmentStateService.add(state);
		else
			installmentStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_2";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/installmentstate/delete", method=RequestMethod.POST)
    public String deleteInstallmentState(long id) {
		if(id > 0)
			installmentStateService.remove(installmentStateService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_2";
    }
	
}
