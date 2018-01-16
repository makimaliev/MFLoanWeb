package kg.gov.mf.loan.web.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.LoanGoods;
import kg.gov.mf.loan.manage.service.loan.LoanGoodsService;
import kg.gov.mf.loan.manage.service.loan.LoanService;

@Controller
public class LoanGoodsController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	LoanGoodsService lgService;
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/loangoods/save"})
    public String saveLoanGoods(LoanGoods lg, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		lg.setLoan(loan);
		
		if(lg.getId() == null || lg.getId() == 0)
			lgService.add(lg);
		else		
			lgService.update(lg);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_5";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/loangoods/delete", method=RequestMethod.POST)
    public String deleteLoanGoods(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			lgService.remove(lgService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_5";
    }

}
