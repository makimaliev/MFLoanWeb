package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import kg.gov.mf.loan.web.util.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.LoanState;
import kg.gov.mf.loan.manage.model.loan.LoanType;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermDaysMethod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFloatingRateType;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermRatePeriod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermTransactionOrder;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.InstallmentStateService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.LoanStateService;
import kg.gov.mf.loan.manage.service.loan.LoanTypeService;
import kg.gov.mf.loan.manage.service.loan.PaymentTypeService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermDaysMethodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFloatingRateTypeService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermRatePeriodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermTransactionOrderService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class LoanController {
	
	@Autowired
	DebtorService debtorService;
	
	@Autowired
	OrderTermCurrencyService currService;
	
	@Autowired
	LoanTypeService loanTypeService;
	
	@Autowired
	LoanStateService loanStateService;
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	CreditOrderService orderService;
	
	@Autowired
	OrderTermRatePeriodService ratePeriodService;
	
	@Autowired
	OrderTermFloatingRateTypeService rateTypeService;
	
	@Autowired
	OrderTermTransactionOrderService txOrderService;
	
	@Autowired
	OrderTermDaysMethodService daysMethodService;
	
	@Autowired
	InstallmentStateService iStateService;
	
	@Autowired
	PaymentTypeService pTypeService;
	
	@Autowired
	CollateralAgreementService agreementService;

	static final Logger loggerLoan = LoggerFactory.getLogger(Loan.class);

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
	
	@RequestMapping(value = { "/manage/debtor/loan/list"})
    public String listLoans(@RequestParam("pageSize") Optional<Integer> pageSize,
							@RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<Loan> loans = loanService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = loanService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("loans", loans);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		
		return "/manage/debtor/loan/list";
		
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/view"})
    public String viewLoan(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {

		Loan loan = loanService.getById(loanId);
        model.addAttribute("loan", loan);
        
        model.addAttribute("terms", loan.getCreditTerms());
        model.addAttribute("WOs", loan.getWriteOffs());
        model.addAttribute("SPs", loan.getSupervisorPlans());
        model.addAttribute("PaymentSchedules", loan.getPaymentSchedules());
        model.addAttribute("Payments", loan.getPayments());
        
        List<OrderTermRatePeriod> ratePeriods = ratePeriodService.list();
        model.addAttribute("ratePeriods", ratePeriods);
        
        List<OrderTermFloatingRateType> rateTypes = rateTypeService.list();
        model.addAttribute("rateTypes", rateTypes);
        model.addAttribute("popots", rateTypes);
        model.addAttribute("poiots", rateTypes);
        
        List<OrderTermTransactionOrder> txOrders = txOrderService.list();
        model.addAttribute("tXs", txOrders);
        
        List<OrderTermDaysMethod> daysMethods = daysMethodService.list();
        model.addAttribute("dimms", daysMethods);
        model.addAttribute("diyms", daysMethods);
        
        model.addAttribute("LGs", loan.getLoanGoods());
        model.addAttribute("DTs", loan.getDebtTransfers());
        model.addAttribute("TUs", loan.getTargetedUses());
        model.addAttribute("RLs", loan.getReconstructedLists());
        model.addAttribute("Bankrupts", loan.getBankrupts());
        model.addAttribute("Collaterals", loan.getCollaterals());
        model.addAttribute("debtorId", debtorId);

		model.addAttribute("detailedSummaries", loan.getLoanDetailedSummaries());
		model.addAttribute("summaries", loan.getLoanSummaries());
		model.addAttribute("accrues", loan.getAccrues());
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/loan/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/save", method=RequestMethod.GET)
	public String formLoan(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId)
	{
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		
		//Get all loans except this loan
		Set<Loan> tLoans = null;
		tLoans.remove(loanService.getById(loanId));
        model.addAttribute("tLoans", tLoans);
        
        model.addAttribute("agreements", agreementService.list());
        model.addAttribute("orders", orderService.list());
		
		if(loanId == 0)
		{
			model.addAttribute("loan", new Loan());
		}
			
		if(loanId > 0)
		{
			model.addAttribute("loan", loanService.getById(loanId));
		}
		
		List<OrderTermCurrency> currs = currService.list();
        model.addAttribute("currencies", currs);
		
		List<LoanType> types = loanTypeService.list();
        model.addAttribute("types", types);
        
        List<LoanState> states = loanStateService.list();
        model.addAttribute("states", states);
			
		return "/manage/debtor/loan/save";
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/save", method=RequestMethod.POST)
	public String saveLoan(Loan loan,
			String[] agreementIdList,
			@PathVariable("debtorId")Long debtorId, 
			ModelMap model)
	{
		
		Debtor debtor = debtorService.getById(debtorId);
		loan.setDebtor(debtor);
		
		if(loan.getId() == 0)
		{
			loggerLoan.info("createLoan : {}", loan);
			if(loan.getParentLoan().getId() == 0)
				loan.setParentLoan(null);
			else
				loan.setParentLoan(loanService.getById(loan.getParentLoan().getId()));
			loanService.add(loan);
		}
		else
		{
			loggerLoan.info("updateLoan : {}", loan);
			loanService.update(loan);
		}
			
		return "redirect:" + "/manage/debtor/{debtorId}/view";
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/delete", method=RequestMethod.POST)
    public String deleteLoan(long id, @PathVariable("debtorId")Long debtorId) {
		if(id > 0)
			loanService.remove(loanService.getById(id));
		return "redirect:" + "/manage/debtor/{debtorId}/view";
    }

	@RequestMapping(value = { "/manage/debtor/loan/state/list" }, method = RequestMethod.GET)
	public String listLoanStates(ModelMap model) {

		List<LoanState> states = loanStateService.list();
		model.addAttribute("states", states);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/loan/state/list";
	}

	@RequestMapping(value="/manage/debtor/loan/state/{stateId}/save", method=RequestMethod.GET)
	public String formLoanState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("loanState", new LoanState());
		}

		if(stateId > 0)
		{
			model.addAttribute("loanState", loanStateService.getById(stateId));
		}
		return "/manage/debtor/loan/state/save";
	}

	@RequestMapping(value="/manage/debtor/loan/state/save", method=RequestMethod.POST)
    public String saveLoanState(LoanState state, ModelMap model) {
		if(state.getId() == 0)
			loanStateService.add(state);
		else
			loanStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/loan/state/list";
    }

	@RequestMapping(value="/manage/debtor/loan/state/delete", method=RequestMethod.POST)
	public String deleteLoanState(long id) {
		if(id > 0)
			loanStateService.remove(loanStateService.getById(id));
		return "redirect:" + "/manage/debtor/loan/state/list";
	}
	
	@RequestMapping(value = { "/manage/debtor/loan/type/list" }, method = RequestMethod.GET)
	public String listLoanTypes(ModelMap model) {

		List<LoanType> types = loanTypeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/loan/type/list";
	}

	@RequestMapping(value="/manage/debtor/loan/type/{typeId}/save", method=RequestMethod.GET)
	public String formLoanType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("loanType", new LoanState());
		}

		if(typeId > 0)
		{
			model.addAttribute("loanType", loanTypeService.getById(typeId));
		}
		return "/manage/debtor/loan/type/save";
	}

	@RequestMapping(value="/manage/debtor/loan/type/save", method=RequestMethod.POST)
    public String saveLoanType(LoanType type, ModelMap model) {
		if(type.getId() == 0)
			loanTypeService.add(type);
		else
			loanTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/loan/type/list";
	}
	
	@RequestMapping(value="/manage/debtor/loan/type/delete", method=RequestMethod.POST)
    public String deleteLoanType(long id) {
		if(id > 0)
			loanTypeService.remove(loanTypeService.getById(id));
		return "redirect:" + "/manage/debtor/loan/type/list";
    }

}
