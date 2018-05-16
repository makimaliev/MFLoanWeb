package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/{psId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("psId")Long psId)
	{
		
		if(psId == 0)
		{
			model.addAttribute("paymentSchedule", new PaymentSchedule());
		}
			
		if(psId > 0)
		{
			model.addAttribute("paymentSchedule", paymentScheduleService.getById(psId));
		}
		
		List<InstallmentState> iStates = installmentStateService.list();
        model.addAttribute("iStates", iStates);
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("loanId", loanId);
			
		return "/manage/debtor/loan/paymentschedule/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/save"}, method=RequestMethod.POST)
    public String savePaymentSchedule(PaymentSchedule paymentSchedule, 
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("loanId")Long loanId, 
    		ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		paymentSchedule.setLoan(loan);
		
		if(paymentSchedule.getId() == 0)
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
	
	@RequestMapping(value = { "/manage/debtor/loan/paymentschedule/installmentstate/list" }, method = RequestMethod.GET)
    public String listAppliedEntityStates(ModelMap model) {
 
		List<InstallmentState> states = installmentStateService.list();
		model.addAttribute("states", states);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/loan/paymentschedule/installmentstate/list";
    }
	
	@RequestMapping(value="/manage/debtor/loan/paymentschedule/installmentstate/{stateId}/save", method=RequestMethod.GET)
	public String formEState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("state", new InstallmentState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("state", installmentStateService.getById(stateId));
		}
		return "/manage/debtor/loan/paymentschedule/installmentstate/save";
	}
	
	@RequestMapping(value="/manage/debtor/loan/paymentschedule/installmentstate/save", method=RequestMethod.POST)
    public String saveInstallmentState(InstallmentState state, ModelMap model) {
		if(state.getId() == 0)
			installmentStateService.add(state);
		else
			installmentStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/loan/paymentschedule/installmentstate/list";
    }
	
	@RequestMapping(value="/manage/debtor/loan/paymentschedule/installmentstate/delete", method=RequestMethod.POST)
    public String deleteInstallmentState(long id) {
		if(id > 0)
			installmentStateService.remove(installmentStateService.getById(id));
		return "redirect:" + "/manage/debtor/loan/paymentschedule/installmentstate/list";
    }
	
}
