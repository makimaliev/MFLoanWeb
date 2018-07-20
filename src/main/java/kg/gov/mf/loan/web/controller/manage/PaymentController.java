package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kg.gov.mf.loan.manage.repository.loan.PaymentRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.web.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.loan.InstallmentState;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.model.loan.PaymentType;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.PaymentService;
import kg.gov.mf.loan.manage.service.loan.PaymentTypeService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class PaymentController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	PaymentTypeService pTypeService;
	
	@Autowired
	PaymentService paymentService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	PaymentRepository paymentRepository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/payment/{paymentId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("paymentId")Long paymentId)
	{
		
		if(paymentId == 0)
		{
			model.addAttribute("payment", new Payment());
		}
			
		if(paymentId > 0)
		{
			model.addAttribute("payment", paymentService.getById(paymentId));
		}
		
		List<PaymentType> pTypes = pTypeService.list();
        model.addAttribute("pTypes", pTypes);
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/payment/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/payment/save"}, method=RequestMethod.POST)
    public String savePayment(Payment payment, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		payment.setLoan(loan);
		
		if(payment.getId() == 0)
			paymentService.add(payment);
		else
			paymentService.update(payment);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/payment/{paymentId}/delete", method=RequestMethod.GET)
    public String deletePayment(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("paymentId")Long paymentId) {
		if(paymentId > 0)
            paymentRepository.delete(paymentRepository.findOne(paymentId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value = { "/manage/debtor/loan/payment/type/list" }, method = RequestMethod.GET)
    public String listAppliedEntityStates(ModelMap model) {
 
		List<PaymentType> types = pTypeService.list();
		model.addAttribute("types", types);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/loan/payment/type/list";
    }
	
	@RequestMapping(value="/manage/debtor/loan/payment/type/{typeId}/save", method=RequestMethod.GET)
	public String formEState(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new PaymentType());
		}
		
		if(typeId > 0)
		{
			model.addAttribute("type", pTypeService.getById(typeId));
		}
		return "/manage/debtor/loan/payment/type/save";
	}

	@RequestMapping(value="/manage/debtor/loan/payment/type/save", method=RequestMethod.POST)
    public String savePaymentType(PaymentType type, ModelMap model) {
		if(type.getId() == 0)
			pTypeService.add(type);
		else
			pTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/loan/payment/type/list";
    }
	
	@RequestMapping(value="/manage/debtor/loan/payment/type/delete", method=RequestMethod.POST)
    public String deletePaymentType(long id) {
		if(id > 0)
			pTypeService.remove(pTypeService.getById(id));
		return "redirect:" + "/manage/debtor/loan/payment/type/list";
    }

}
