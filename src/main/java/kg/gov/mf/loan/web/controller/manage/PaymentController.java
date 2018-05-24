package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	@RequestMapping(value = { "/manage/debtor/loan/payment/list"})
	public String listPayments(@RequestParam("pageSize") Optional<Integer> pageSize,
							@RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<Payment> payments = paymentService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = paymentService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("payments", payments);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());

		return "/manage/debtor/loan/payment/list";

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
        model.addAttribute("loanId", loanId);
			
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
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_3";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/payment/delete", method=RequestMethod.POST)
    public String deletePayment(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			paymentService.remove(paymentService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_3";
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
