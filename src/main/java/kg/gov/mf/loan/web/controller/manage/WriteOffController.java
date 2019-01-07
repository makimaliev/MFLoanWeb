package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kg.gov.mf.loan.manage.repository.loan.WriteOffRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.web.util.Pager;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	DebtorService debtorService;

	@Autowired
	WriteOffRepository writeOffRepository;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
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
			WriteOff writeOff=new WriteOff();
			writeOff.setDate(new Date());
			writeOff.setFee(0.0);
			writeOff.setInterest(0.0);
			writeOff.setPenalty(0.0);
			writeOff.setTotalAmount(0.0);
			writeOff.setPrincipal(0.0);
			writeOff.setDescription("-");
			model.addAttribute("wo",writeOff );
		}
			
		if(woId > 0)
		{
			model.addAttribute("wo", woService.getById(woId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
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
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/wo/{woId}/delete", method=RequestMethod.GET)
    public String deleteWriteOff(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("woId")Long woId) {
		if(woId > 0)
            writeOffRepository.delete(writeOffRepository.findOne(woId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
}
