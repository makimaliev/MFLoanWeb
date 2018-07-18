package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.loan.*;
import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.model.process.Accrue;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.repository.order.CreditOrderRepository;
import kg.gov.mf.loan.manage.repository.org.StaffRepository;
import kg.gov.mf.loan.web.fetchModels.*;
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

	@Autowired
	LoanRepository loanRepository;

	@Autowired
	CreditOrderRepository creditOrderRepository;

	@Autowired
	UserService userService;

	@Autowired
	StaffRepository staffRepository;

	static final Logger loggerLoan = LoggerFactory.getLogger(Loan.class);
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/view"})
    public String viewLoan(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId) {

		Loan loan = loanService.getById(loanId);
        model.addAttribute("loan", loan);

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonTerms = gson.toJson(getTermsByLoanId(loanId));
        model.addAttribute("terms", jsonTerms);

        String jsonPaymentSchedules = gson.toJson(getPaymentSchedulesByLoanId(loanId));
        model.addAttribute("paymentSchedules", jsonPaymentSchedules);

        String jsonPayments = gson.toJson(getPaymentsByLoanId(loanId));
        model.addAttribute("payments", jsonPayments);

        String jsonWriteOffs = gson.toJson(getWOsByLoanId(loanId));
        model.addAttribute("WOs", jsonWriteOffs);

        String jsonSupervisorPlans = gson.toJson(getSPsByLoanId(loanId));
        model.addAttribute("SPs", jsonSupervisorPlans);

        //model.addAttribute("terms", loan.getCreditTerms());
        //model.addAttribute("PaymentSchedules", loan.getPaymentSchedules());
        //model.addAttribute("Payments", loan.getPayments());
        //model.addAttribute("WOs", loan.getWriteOffs());
        //model.addAttribute("SPs", loan.getSupervisorPlans());
        
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

        String jsonGoods = gson.toJson(getGoodsByLoanId(loanId));
        model.addAttribute("goods", jsonGoods);

        String jsonDebtTransfers = gson.toJson(getDTsByLoanId(loanId));
        model.addAttribute("DTs", jsonDebtTransfers);

        //model.addAttribute("LGs", loan.getLoanGoods());
        //model.addAttribute("DTs", loan.getDebtTransfers());
        model.addAttribute("TUs", loan.getTargetedUses());
        model.addAttribute("RLs", loan.getReconstructedLists());
        model.addAttribute("Bankrupts", loan.getBankrupts());
        model.addAttribute("Collaterals", loan.getCollaterals());
        model.addAttribute("debtorId", debtorId);
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtor", debtor);

        String jsonDetailedSummaries = gson.toJson(getDetailedSummariesByLoanId(loanId));
        model.addAttribute("detailedSummaries", jsonDetailedSummaries);

        String jsonSummaries = gson.toJson(getSummariesByLoanId(loanId));
        model.addAttribute("summaries", jsonSummaries);

        String jsonAccrues = gson.toJson(getAccruesByLoanId(loanId));
        model.addAttribute("accrues", jsonAccrues);

		//model.addAttribute("detailedSummaries", loan.getLoanDetailedSummaries());
		//model.addAttribute("summaries", loan.getLoanSummaries());
		//model.addAttribute("accrues", loan.getAccrues());
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/loan/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/save", method=RequestMethod.GET)
	public String formLoan(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId)
	{
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtor);

		if(loanId == 0)
		{
			model.addAttribute("loan", new Loan());
			model.addAttribute("ownerText", "");
			model.addAttribute("orderText", "");
			model.addAttribute("pLoanText", "");
		}
			
		if(loanId > 0)
		{
			Loan loan = loanService.getById(loanId);
			model.addAttribute("loan", loan);
			User user = userService.findById(loan.getSupervisorId());
			Staff staff = staffRepository.findById(user.getStaff().getId());

			String supervisorText = "[" + staff.getId() + "] " + staff.getName();
			model.addAttribute("supervisorText", supervisorText);

			CreditOrder order = loan.getCreditOrder();
			String orderText = "[" + order.getId() + "] "+ order.getRegNumber();
			model.addAttribute("orderText", orderText);

			Loan pLoan = loan.getParentLoan();
			if(pLoan == null)
				model.addAttribute("pLoanText", "");
			else
			{
				String pLoanText = "[" + pLoan.getId() + "] " + pLoan.getRegNumber();
				model.addAttribute("pLoanText", pLoanText);
			}

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
		    if(loan.getParentLoan().getId() > 0)
            {
                Loan tLoan = loanRepository.findOne(loan.getParentLoan().getId());
                loan.setParentLoan(tLoan);
            }
            else
                loan.setParentLoan(null);

            CreditOrder creditOrder = creditOrderRepository.findOne(loan.getCreditOrder().getId());
            loan.setCreditOrder(creditOrder);

            Staff staff = staffRepository.findById(loan.getSupervisorId());
            User user = userService.findByStaff(staff);

            loan.setSupervisorId(user.getId());

			loggerLoan.info("createLoan : {}", loan);
			loanService.add(loan);
		}
		else
		{
			if(loan.getParentLoan().getId() > 0)
			{
				Loan tLoan = loanRepository.findOne(loan.getParentLoan().getId());
				loan.setParentLoan(tLoan);
			}
			else
				loan.setParentLoan(null);

			CreditOrder creditOrder = creditOrderRepository.findOne(loan.getCreditOrder().getId());
			loan.setCreditOrder(creditOrder);

			Staff staff = staffRepository.findById(loan.getSupervisorId());
			User user = userService.findByStaff(staff);

			loan.setSupervisorId(user.getId());
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

	private List<CreditTermModel> getTermsByLoanId(long loanId)
	{
		List<CreditTermModel> result = new ArrayList<>();
		Loan loan = loanService.getById(loanId);
		for(CreditTerm term: loan.getCreditTerms())
		{
            CreditTermModel model = new CreditTermModel();
			model.setId(term.getId());
			model.setStartDate(term.getStartDate());
			model.setInterestRateValue(term.getInterestRateValue());
			model.setRatePeriodId(term.getRatePeriod().getId());
			model.setRatePeriodName(term.getRatePeriod().getName());
			model.setFloatingRateTypeId(term.getFloatingRateType().getId());
			model.setFloatingRateTypeName(term.getFloatingRateType().getName());
			model.setPenaltyOnPrincipleOverdueRateValue(term.getPenaltyOnPrincipleOverdueRateValue());
			model.setPenaltyOnPrincipleOverdueRateTypeId(term.getPenaltyOnPrincipleOverdueRateType().getId());
			model.setPenaltyOnPrincipleOverdueRateTypeName(term.getPenaltyOnPrincipleOverdueRateType().getName());
			model.setPenaltyOnInterestOverdueRateValue(term.getPenaltyOnInterestOverdueRateValue());
			model.setPenaltyOnInterestOverdueRateTypeId(term.getPenaltyOnInterestOverdueRateType().getId());
			model.setPenaltyOnInterestOverdueRateTypeName(term.getPenaltyOnInterestOverdueRateType().getName());
			model.setPenaltyLimitPercent(term.getPenaltyLimitPercent());
			model.setPenaltyLimitEndDate(term.getPenaltyLimitEndDate());
			model.setTransactionOrderId(term.getTransactionOrder().getId());
			model.setTransactionOrderName(term.getTransactionOrder().getName());
			model.setDaysInMonthMethodId(term.getDaysInMonthMethod().getId());
			model.setDaysInMonthMethodName(term.getDaysInMonthMethod().getName());
			model.setDaysInYearMethodId(term.getDaysInYearMethod().getId());
			model.setDaysInYearMethodName(term.getDaysInYearMethod().getName());
			result.add(model);
		}

		Collections.sort(result);

		return result;
	}

    private List<PaymentScheduleModel> getPaymentSchedulesByLoanId(long loanId)
    {
        List<PaymentScheduleModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(PaymentSchedule ps: loan.getPaymentSchedules())
        {
            PaymentScheduleModel model = new PaymentScheduleModel();
            model.setId(ps.getId());
            model.setExpectedDate(ps.getExpectedDate());
            model.setDisbursement(ps.getDisbursement());
            model.setPrincipalPayment(ps.getPrincipalPayment());
            model.setInterestPayment(ps.getInterestPayment());
            model.setCollectedInterestPayment(ps.getCollectedInterestPayment());
            model.setCollectedPenaltyPayment(ps.getCollectedPenaltyPayment());
            model.setInstallmentStateId(ps.getInstallmentState().getId());
            model.setInstallmentStateName(ps.getInstallmentState().getName());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }

    private List<PaymentModel> getPaymentsByLoanId(long loanId)
    {
        List<PaymentModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(Payment p: loan.getPayments())
        {
            PaymentModel model = new PaymentModel();
            model.setId(p.getId());
            model.setPaymentDate(p.getPaymentDate());
            model.setTotalAmount(p.getTotalAmount());
            model.setPrincipal(p.getPrincipal());
            model.setInterest(p.getInterest());
            model.setPenalty(p.getPenalty());
            model.setFee(p.getFee());
            model.setExchange_rate(p.getExchange_rate());
            model.setNumber(p.getNumber());
            model.setIn_loan_currency(p.isIn_loan_currency());
            model.setDetails(p.getDetails());
            model.setPaymentTypeId(p.getPaymentType().getId());
            model.setPaymentTypeName(p.getPaymentType().getName());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }

    private List<WriteOffModel> getWOsByLoanId(long loanId)
    {
        List<WriteOffModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(WriteOff w: loan.getWriteOffs())
        {
            WriteOffModel model = new WriteOffModel();
            model.setId(w.getId());
            model.setDate(w.getDate());
            model.setTotalAmount(w.getTotalAmount());
            model.setPrincipal(w.getPrincipal());
            model.setInterest(w.getInterest());
            model.setPenalty(w.getPenalty());
            model.setFee(w.getFee());
            model.setDescription(w.getDescription());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }

    private List<SupervisorPlanModel> getSPsByLoanId(long loanId)
    {
        List<SupervisorPlanModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(SupervisorPlan sp: loan.getSupervisorPlans())
        {
            SupervisorPlanModel model = new SupervisorPlanModel();
            model.setId(sp.getId());
            model.setDate(sp.getDate());
            model.setAmount(sp.getAmount());
            model.setPrincipal(sp.getPrincipal());
            model.setInterest(sp.getInterest());
            model.setPenalty(sp.getPenalty());
            model.setFee(sp.getFee());
            model.setDescription(sp.getDescription());
            model.setReg_by_id(sp.getReg_by_id());
            model.setReg_date(sp.getReg_date());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }

    private List<LoanDetailedSummaryModel> getDetailedSummariesByLoanId(long loanId)
    {
        List<LoanDetailedSummaryModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(LoanDetailedSummary d: loan.getLoanDetailedSummaries())
        {
            LoanDetailedSummaryModel model = new LoanDetailedSummaryModel();
            model.setId(d.getId());
            model.setOnDate(d.getOnDate());
            model.setDisbursement(d.getDisbursement());
            model.setTotalDisbursement(d.getTotalDisbursement());
            model.setPrincipalPayment(d.getPrincipalPayment());
            model.setTotalPrincipalPayment(d.getTotalPrincipalPayment());
            model.setPrincipalPaid(d.getPrincipalPaid());
            model.setTotalPrincipalPaid(d.getTotalPrincipalPaid());
            model.setPrincipalWriteOff(d.getPrincipalWriteOff());
            model.setTotalPrincipalWriteOff(d.getTotalPrincipalWriteOff());
            model.setPrincipalOutstanding(d.getPrincipalOutstanding());
            model.setPrincipalOverdue(d.getPrincipalOverdue());
            model.setDaysInPeriod(d.getDaysInPeriod());
            model.setInterestAccrued(d.getInterestAccrued());
            model.setTotalInterestAccrued(d.getTotalInterestAccrued());
            model.setTotalInterestAccruedOnInterestPayment(d.getTotalInterestAccruedOnInterestPayment());
            model.setInterestPayment(d.getInterestPayment());
            model.setTotalInterestPayment(d.getTotalInterestPayment());
            model.setCollectedInterestPayment(d.getCollectedInterestPayment());
            model.setTotalCollectedInterestPayment(d.getTotalCollectedInterestPayment());
            model.setCollectedInterestDisbursed(d.getCollectedInterestDisbursed());
            model.setInterestPaid(d.getInterestPaid());
            model.setTotalInterestPaid(d.getTotalInterestPaid());
            model.setInterestOutstanding(d.getInterestOutstanding());
            model.setInterestOverdue(d.getInterestOverdue());
            model.setPenaltyAccrued(d.getPenaltyAccrued());
            model.setTotalPenaltyAccrued(d.getTotalPenaltyAccrued());
            model.setCollectedPenaltyPayment(d.getCollectedPenaltyPayment());
            model.setTotalCollectedPenaltyPayment(d.getTotalCollectedPenaltyPayment());
            model.setCollectedPenaltyDisbursed(d.getCollectedPenaltyDisbursed());
            model.setPenaltyPaid(d.getPenaltyPaid());
            model.setTotalPenaltyPaid(d.getTotalPenaltyPaid());
            model.setPenaltyOutstanding(d.getPenaltyOutstanding());
            model.setPenaltyOverdue(d.getPenaltyOverdue());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }

    private List<LoanSummaryModel> getSummariesByLoanId(long loanId)
    {
        List<LoanSummaryModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(LoanSummary d: loan.getLoanSummaries())
        {
            LoanSummaryModel model = new LoanSummaryModel();
            model.setId(d.getId());
            model.setOnDate(d.getOnDate());
            model.setLoanAmount(d.getLoanAmount());
            model.setTotalDisbursed(d.getTotalDisbursed());
            model.setTotalPaid(d.getTotalPaid());
            model.setPaidPrincipal(d.getPaidPrincipal());
            model.setPaidInterest(d.getPaidInterest());
            model.setPaidPenalty(d.getPaidPenalty());
            model.setPaidFee(d.getPaidFee());
            model.setTotalOutstanding(d.getTotalOutstanding());
            model.setOutstadingPrincipal(d.getOutstadingPrincipal());
            model.setOutstadingInterest(d.getOutstadingInterest());
            model.setOutstadingPenalty(d.getOutstadingPenalty());
            model.setOutstadingFee(d.getOutstadingFee());
            model.setTotalOverdue(d.getTotalOverdue());
            model.setOverduePrincipal(d.getOverduePrincipal());
            model.setOverdueInterest(d.getOverdueInterest());
            model.setOverduePenalty(d.getOverduePenalty());
            model.setOverdueFee(d.getOverdueFee());
            model.setTotalPrincipalPaid(d.getTotalPrincipalPaid());
            model.setTotalInterestPaid(d.getTotalInterestPaid());
            model.setTotalPenaltyPaid(d.getTotalPenaltyPaid());
            model.setTotalFeePaid(d.getTotalFeePaid());
            model.setLoanSummaryType(d.getLoanSummaryType());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }

    private List<AccrueModel> getAccruesByLoanId(long loanId)
    {
        List<AccrueModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(Accrue d: loan.getAccrues())
        {
            AccrueModel model = new AccrueModel();
            model.setId(d.getId());
            model.setFromDate(d.getFromDate());
            model.setToDate(d.getToDate());
            model.setDaysInPeriod(d.getDaysInPeriod());
            model.setInterestAccrued(d.getInterestAccrued());
            model.setPenaltyAccrued(d.getPenaltyAccrued());
            model.setPenaltyOnPrincipalOverdue(d.getPenaltyOnPrincipalOverdue());
            model.setPenaltyOnInterestOverdue(d.getPenaltyOnInterestOverdue());
            model.setLastInstPassed(d.isLastInstPassed());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }

    private List<LoanGoodsModel> getGoodsByLoanId(long loanId)
    {
        List<LoanGoodsModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(LoanGoods d: loan.getLoanGoods())
        {
            LoanGoodsModel model = new LoanGoodsModel();
            model.setId(d.getId());
            model.setQuantity(d.getQuantity());
            model.setUnitTypeId(d.getUnitTypeId());
            model.setGoodsTypeId(d.getGoodsTypeId());

            result.add(model);
        }

        return result;
    }

    private List<DebtTransferModel> getDTsByLoanId(long loanId)
    {
        List<DebtTransferModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(DebtTransfer d: loan.getDebtTransfers())
        {
            DebtTransferModel model = new DebtTransferModel();
            model.setId(d.getId());
            model.setNumber(d.getNumber());
            model.setDate(d.getDate());
            model.setQuantity(d.getQuantity());
            model.setPricePerUnit(d.getPricePerUnit());
            model.setUnitTypeId(d.getUnitTypeId());
            model.setTotalCost(d.getTotalCost());
            model.setTransferPaymentId(d.getTransferPaymentId());
            model.setTransferCreditId(d.getTransferCreditId());
            model.setTransferPersonId(d.getTransferPersonId());
            model.setGoodsTypeId(d.getGoodsTypeId());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }

}
