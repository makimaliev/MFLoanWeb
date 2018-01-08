package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	static final Logger loggerDT = LoggerFactory.getLogger(DebtTransfer.class);

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/save"})
    public String saveDebtTransfer(DebtTransfer dt, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.findById(loanId);
		if(dt != null && dt.getId() == 0)
		{
			DebtTransfer newDT = new DebtTransfer(dt.getNumber(), dt.getDate(), dt.getQuantity(), 
					dt.getPricePerUnit(), dt.getUnitTypeId(), dt.getTotalCost(), 
					dt.getTransferPaymentId(), dt.getTransferCreditId(), dt.getTransferPersonId(), dt.getGoodsTypeId());
			newDT.setLoan(loan);
			loggerDT.info("createDT : {}", newDT);
			dtService.save(newDT);
		}
		
		if(dt != null && dt.getId() > 0)
		{
			dtService.update(dt);
		}
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_6";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/debttransfer/delete", method=RequestMethod.POST)
    public String deleteDebtTransfer(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			dtService.deleteById(id);
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_6";
    }
	
}
