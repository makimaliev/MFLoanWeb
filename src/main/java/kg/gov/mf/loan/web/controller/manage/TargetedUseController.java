package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

import kg.gov.mf.loan.manage.repository.loan.TargetedUseRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
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
import kg.gov.mf.loan.manage.model.loan.TargetedUse;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.TargetedUseService;

@Controller
public class TargetedUseController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	TargetedUseService tuService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	TargetedUseRepository targetedUseRepository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/targeteduse/{tuId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("tuId")Long tuId)
	{
		
		if(tuId == 0)
		{
			model.addAttribute("tu", new TargetedUse());
		}
			
		if(tuId > 0)
		{
			model.addAttribute("tu", tuService.getById(tuId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("loanId", loanId);

        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/targeteduse/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/targeteduse/save"}, method=RequestMethod.POST)
    public String saveTargetedUse(TargetedUse tu, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		tu.setLoan(loan);
		
		if(tu.getId() == 0)
			tuService.add(tu);
		else
			tuService.update(tu);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/targeteduse/{tuId}/delete", method=RequestMethod.GET)
    public String deleteTargetedUse(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("tuId")Long tuId) {
		if(tuId > 0)
            targetedUseRepository.delete(targetedUseRepository.findOne(tuId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

}
