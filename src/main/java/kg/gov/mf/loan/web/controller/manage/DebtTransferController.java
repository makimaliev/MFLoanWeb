package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

import kg.gov.mf.loan.manage.repository.loan.DebtTransferRepository;
import kg.gov.mf.loan.manage.service.collateral.QuantityTypeService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.GoodTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.loan.DebtTransfer;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.loan.DebtTransferService;
import kg.gov.mf.loan.manage.service.loan.LoanService;

@Controller
public class DebtTransferController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	DebtTransferService dtService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	DebtTransferRepository debtTransferRepository;

	@Autowired
	QuantityTypeService quantityTypeService;

	@Autowired
	GoodTypeService goodTypeService;
	
	static final Logger loggerDT = LoggerFactory.getLogger(DebtTransfer.class);

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/{dtId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("dtId")Long dtId)
	{
		
		if(dtId == 0)
		{
			DebtTransfer debtTransfer=new DebtTransfer();
			debtTransfer.setDate(new Date());
			debtTransfer.setQuantityType(quantityTypeService.getById(Long.valueOf(1)));
			model.addAttribute("dt", debtTransfer);
			model.addAttribute("thisUnit","");
			model.addAttribute("thisGoodType","");
		}
			
		if(dtId > 0)
		{
			model.addAttribute("dt", dtService.getById(dtId));
//			model.addAttribute("thisUnit",quantityTypeService.getById(dtService.getById(dtId).getUnitTypeId()).getName());
//			model.addAttribute("thisGoodType",goodTypeService.getById(dtService.getById(dtId).getGoodsTypeId()).getName());
		}

		model.addAttribute("unitTypes",quantityTypeService.list());
		model.addAttribute("goodTypes",goodTypeService.list());
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/debttransfer/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/save"}, method=RequestMethod.POST)
    public String saveDebtTransfer(DebtTransfer dt, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		dt.setLoan(loan);
//		dt.setTransferCreditId(Long.valueOf(0));
		
		if(dt.getId() == 0)
			dtService.add(dt);
		else
			dtService.update(dt);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/{transferId}/delete", method=RequestMethod.GET)
    public String deleteDebtTransfer(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("transferId")Long transferId) {
		if(transferId > 0)
		    debtTransferRepository.delete(debtTransferRepository.findOne(transferId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
}
