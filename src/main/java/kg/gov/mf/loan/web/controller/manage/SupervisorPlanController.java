package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kg.gov.mf.loan.manage.repository.loan.SupervisorPlanRepository;
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
import kg.gov.mf.loan.manage.model.loan.SupervisorPlan;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.SupervisorPlanService;

@Controller
public class SupervisorPlanController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	SupervisorPlanService spService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	SupervisorPlanRepository supervisorPlanRepository;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	private Date globalRegDate=new Date();
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/sp/{spId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("spId")Long spId)
	{
		
		if(spId == 0)
		{
			SupervisorPlan sp=new SupervisorPlan();
			sp.setDate(new Date());
			sp.setAmount(0.0);
			sp.setInterest(0.0);
			sp.setPenalty(0.0);
			sp.setPrincipal(0.0);
			sp.setDescription("-");
			model.addAttribute("sp", sp);
		}
			
		if(spId > 0)
		{
			SupervisorPlan sp=spService.getById(spId);
			System.out.println(sp.getReg_date());
			globalRegDate=sp.getReg_date();
			model.addAttribute("sp", sp);
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/sp/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/sp/save"}, method=RequestMethod.POST)
    public String saveSupervisorPlan(SupervisorPlan sp, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		sp.setLoan(loan);
		
		if(sp.getId() == 0){
			sp.setReg_by_id(1);
			sp.setReg_date(sp.getDate());
			spService.add(sp);
		}
		else
			sp.setReg_date(globalRegDate);
			spService.update(sp);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/sp/{spId}/delete", method=RequestMethod.GET)
    public String deleteSupervisorPlan(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("spId")Long spId) {
		if(spId > 0)
		    supervisorPlanRepository.delete(supervisorPlanRepository.findOne(spId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
}
