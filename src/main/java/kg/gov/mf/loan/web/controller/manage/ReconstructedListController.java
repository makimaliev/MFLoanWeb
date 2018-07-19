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
import kg.gov.mf.loan.manage.model.loan.ReconstructedList;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.ReconstructedListService;

@Controller
public class ReconstructedListController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	ReconstructedListService rlService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/reconstructedlist/{rlId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("rlId")Long rlId)
	{
		
		if(rlId == 0)
		{
			model.addAttribute("rl", new ReconstructedList());
		}
			
		if(rlId > 0)
		{
			model.addAttribute("rl", rlService.getById(rlId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("loanId", loanId);
			
		return "/manage/debtor/loan/reconstructedlist/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/reconstructedlist/save"}, method=RequestMethod.POST)
    public String saveReconstructedList(ReconstructedList rl, 
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("loanId")Long loanId, 
    		ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		rl.setLoan(loan);
		
		if(rl.getId() == 0)
			rlService.add(rl);
		else
			rlService.update(rl);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_8";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/reconstructedlist/delete", method=RequestMethod.POST)
    public String deleteReconstructedList(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			rlService.remove(rlService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_8";
    }

}
