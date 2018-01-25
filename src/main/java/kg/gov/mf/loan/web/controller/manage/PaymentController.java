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
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/payment/save"})
    public String savePayment(Payment payment, long paymentTypeId, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.findById(loanId);
		if(payment != null && payment.getId() == 0)
		{
			Payment newPayment = new Payment(
					payment.getPaymentDate(),
					payment.getTotalAmount(),
					payment.getPrincipal(),
					payment.getInterest(),
					payment.getPenalty(),
					payment.getFee(),
					payment.getNumber(),
					pTypeService.findById(paymentTypeId));
					
			newPayment.setLoan(loan);
			paymentService.save(newPayment);
		}
		
		if(payment != null && payment.getId() > 0)
		{
			payment.setPaymentType(pTypeService.findById(paymentTypeId));
			paymentService.update(payment);
		}
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_3";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/payment/delete", method=RequestMethod.POST)
    public String deletePayment(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			paymentService.deleteById(id);
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_3";
    }

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/payment/type/save", method=RequestMethod.POST)
    public String savePaymentType(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, PaymentType type, ModelMap model) {
		if(type != null && type.getId() == 0)
			pTypeService.save(new PaymentType(type.getName()));
		
		if(type != null && type.getId() > 0)
			pTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_3";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/payment/type/delete", method=RequestMethod.POST)
    public String deletePaymentType(long id) {
		if(id > 0)
			pTypeService.deleteById(id);
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_3";
    }

}
