package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = {5, 10, 20, 50, 100};

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = { "/manage/debtor/loan/wo/list"})
	public String listCreditTerms(@RequestParam("pageSize") Optional<Integer> pageSize,
								  @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<WriteOff> writeOffs = woService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = woService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("writeOffs", writeOffs);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());

		return "/manage/debtor/loan/wo/list";

	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/wo/{woId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("woId")Long woId)
	{
		
		if(woId == 0)
		{
			model.addAttribute("wo", new WriteOff());
		}
			
		if(woId > 0)
		{
			model.addAttribute("wo", woService.getById(woId));
		}
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("loanId", loanId);
			
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
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_1";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/wo/delete", method=RequestMethod.POST)
    public String deleteWriteOff(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {
		if(id > 0)
			woService.remove(woService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#tab_1";
    }
	
}
