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
import kg.gov.mf.loan.manage.model.loan.TargetedUse;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.TargetedUseService;

@Controller
public class TargetedUseController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	TargetedUseService tuService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/targeteduse/save"})
    public String saveTargetedUse(TargetedUse tu, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.findById(loanId);
		if(tu != null && tu.getId() == 0)
		{
			TargetedUse newTU = new TargetedUse(tu.getTargetedUseResultId(), tu.getCreatedById(), tu.getCreatedDate(), tu.getApprovedById(), tu.getApprovedDate(), tu.getCheckedById(), tu.getCheckedDate(), tu.getAttachmentId());
			newTU.setLoan(loan);
			tuService.save(newTU);
		}
		
		if(tu != null && tu.getId() > 0)
		{
			tuService.update(tu);
		}
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_7";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/targeteduse/delete", method=RequestMethod.POST)
    public String deleteTargetedUse(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			tuService.deleteById(id);
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_7";
    }

}
