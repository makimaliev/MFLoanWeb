package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.repository.loan.LoanGoodsRepository;
import kg.gov.mf.loan.manage.service.collateral.QuantityTypeService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.GoodTypeService;
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

	@Autowired
	DebtorService debtorService;

	@Autowired
	LoanGoodsRepository loanGoodsRepository;

	@Autowired
	QuantityTypeService quantityTypeService;

	@Autowired
	GoodTypeService goodTypeService;
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/loangoods/{lgId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("lgId")Long lgId)
	{
		
		if(lgId == 0)
		{
			model.addAttribute("lg", new LoanGoods());
			model.addAttribute("thisUnit","");
			model.addAttribute("thisGoodType","");
		}
			
		if(lgId > 0)
		{
			model.addAttribute("lg", lgService.getById(lgId));
			model.addAttribute("thisUnit",quantityTypeService.getById(lgService.getById(lgId).getUnitTypeId()).getName());
			model.addAttribute("thisGoodType",goodTypeService.getById(lgService.getById(lgId).getGoodsTypeId()).getName());
		}



		model.addAttribute("unitTypes",quantityTypeService.list());
		model.addAttribute("goodTypes",goodTypeService.list());
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/loangoods/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/loangoods/save"}, method=RequestMethod.POST)
    public String saveLoanGoods(LoanGoods lg, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		lg.setLoan(loan);

		if(lg.getId() == 0)
			lgService.add(lg);
		else		
			lgService.update(lg);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/loangoods/{goodsId}/delete", method=RequestMethod.GET)
    public String deleteLoanGoods(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("goodsId")Long goodsId) {
		if(goodsId > 0)
		    loanGoodsRepository.delete(loanGoodsRepository.findOne(goodsId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

}
