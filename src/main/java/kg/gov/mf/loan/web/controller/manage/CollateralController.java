package kg.gov.mf.loan.web.controller.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.collateral.Collateral;
import kg.gov.mf.loan.manage.model.collateral.CollateralSummary;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.collateral.CollateralService;
import kg.gov.mf.loan.manage.service.loan.LoanService;

@Controller
public class CollateralController {

	@Autowired
	LoanService loanService;
	
	@Autowired
	CollateralService collService;
	
	@RequestMapping(value = { "/manage/collateral/list"})
    public String listCollaterals(ModelMap model) {
		
		List<Collateral> colls = collService.list(); 
		model.addAttribute("colls", colls);
		
		return "/manage/collateral/list";
		
	}
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/view"})
    public String viewLoan(ModelMap model, @PathVariable("collateralId")Long collateralId) {
		
		Collateral collateral = collService.getById(collateralId);
		
		model.addAttribute("collateral", collateral);
		
		model.addAttribute("summaries", collateral.getCollateralSummaries());
		model.addAttribute("emptySummary", new CollateralSummary());
		
		return "/manage/collateral/view";
		
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/collateral/{collId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("collId")Long collId)
	{
		
		if(collId == 0)
		{
			model.addAttribute("coll", new Collateral());
		}
			
		if(collId > 0)
		{
			model.addAttribute("coll", collService.getById(collId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("loanId", loanId);
			
		return "/manage/debtor/loan/collateral/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/collateral/save"}, method=RequestMethod.POST)
    public String saveCollateral(Collateral coll, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		coll.setLoan(loan);
		
		if(coll.getId() == 0)
			collService.add(coll);
		else			
			collService.update(coll);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_10";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/collateral/delete", method=RequestMethod.POST)
    public String deleteCollateral(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			collService.remove(collService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_10";
    }
	
}
