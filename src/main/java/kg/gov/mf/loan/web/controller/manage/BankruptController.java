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

import kg.gov.mf.loan.manage.model.loan.Bankrupt;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.loan.BankruptService;
import kg.gov.mf.loan.manage.service.loan.LoanService;

@Controller
public class BankruptController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	BankruptService bankruptService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/bankrupt/{bankruptId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("bankruptId")Long bankruptId)
	{
		
		if(bankruptId == 0)
		{
			model.addAttribute("bankrupt", new Bankrupt());
		}
			
		if(bankruptId > 0)
		{
			model.addAttribute("bankrupt", bankruptService.getById(bankruptId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("loanId", loanId);
			
		return "/manage/debtor/loan/bankrupt/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/bankrupt/save"})
    public String saveBankrupt(Bankrupt bankrupt, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		bankrupt.setLoan(loan);
		
		if(bankrupt.getId() == null || bankrupt.getId() == 0)
			bankruptService.add(bankrupt);
		else
			bankruptService.update(bankrupt);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_9";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/bankrupt/delete", method=RequestMethod.POST)
    public String deleteBankrupt(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			bankruptService.remove(bankruptService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_9";
    }

}
