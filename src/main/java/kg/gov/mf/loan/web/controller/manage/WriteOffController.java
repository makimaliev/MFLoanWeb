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
import kg.gov.mf.loan.manage.model.loan.WriteOff;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.WriteOffService;

@Controller
public class WriteOffController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	WriteOffService woService;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/wo/{woId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("woId")Long woId)
	{
		
		if(woId == 0)
		{
			model.addAttribute("wo", new WriteOff());
		}
			
		if(woId > 0)
		{
			model.addAttribute("wo", woService.getById(woId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("loanId", loanId);
			
		return "/manage/debtor/loan/wo/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/wo/save"}, method=RequestMethod.POST)
    public String saveWriteOff(WriteOff wo, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		wo.setLoan(loan);
		
		if(wo.getId() == 0)
			woService.add(wo);
		else
			woService.update(wo);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_1";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/wo/delete", method=RequestMethod.POST)
    public String deleteWriteOff(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			woService.remove(woService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_1";
    }
	
}
