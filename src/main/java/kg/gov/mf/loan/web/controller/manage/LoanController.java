package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.CollectionProcedure;
import kg.gov.mf.loan.manage.model.loan.*;
import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.model.orderterm.*;
import kg.gov.mf.loan.manage.model.process.Accrue;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummaryType;
import kg.gov.mf.loan.manage.repository.collateral.CollateralItemReposiory;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.repository.order.CreditOrderRepository;
import kg.gov.mf.loan.manage.repository.org.StaffRepository;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.manage.service.collateral.QuantityTypeService;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.manage.service.loan.*;
import kg.gov.mf.loan.manage.service.orderterm.*;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.web.fetchModels.*;
import org.bouncycastle.ocsp.Req;
import org.hibernate.boot.model.relational.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.web.util.Utils;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Controller
public class LoanController {

    @Autowired
    EntityManager entityManager;
	
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
    PaymentService paymentService;
	
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

    @Autowired
    QuantityTypeService quantityTypeService;

    @Autowired
    GoodTypeService goodTypeService;

    @Autowired
    CurrencyRateService currencyRateService;

    @Autowired
    BankruptService bankruptService;

    @Autowired
    PaymentScheduleService paymentScheduleService;

    @Autowired
    CreditTermService creditTermService;

    @Autowired
    OrderTermCurrencyService orderTermCurrencyService;

    @Autowired
    LoanGoodsService loanGoodsService;

    @Autowired
    DebtTransferService debtTransferService;

    @Autowired
    OrderTermFundService fundService;

    @Autowired
    LoanFinGroupService loanFinGroupService;

    @Autowired
    CollateralAgreementService collateralAgreementService;

    @Autowired
    CollateralItemService collateralItemService;

    @Autowired
    CollateralItemReposiory collateralItemReposiory;

    @Autowired
    CollectionPhaseService collectionPhaseService;

    @Autowired
    OrderTermFundService orderTermFundService;

    @Autowired
    OwnerService ownerService;

    @Autowired
    LoanSummaryService loanSummaryService;

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
        model.addAttribute("creditOrder",loan.getCreditOrder());
        Staff staff=userService.findById(loan.getSupervisorId()).getStaff();
        model.addAttribute("supervisorName",staff.getName());
        model.addAttribute("supervisorId",loan.getSupervisorId());

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
//        String jsonTerms = gson.toJson(getTermsByLoanId(loanId));
//        model.addAttribute("terms", jsonTerms);
//
//        String jsonChildL = gson.toJson(getChildrenByLoanId(loanId));
//        model.addAttribute("children", jsonChildList);
        model.addAttribute("isEmptyChild",false);
        if(loan.getChildren().isEmpty()){
            model.addAttribute("isEmptyChild",true);
        }
        model.addAttribute("hasParent",false);
        if(loan.getParent()!=null){
            model.addAttribute("hasParent",true);
        }
//        String jsonPaymentSchedules = gson.toJson(getPaymentSchedulesByLoanId(loanId));
//        model.addAttribute("paymentSchedules", jsonPaymentSchedules);

//        String jsonPayments = gson.toJson(getPaymentsByLoanId(loanId));
//        model.addAttribute("payments", jsonPayments);

//        String jsonWriteOffs = gson.toJson(getWOsByLoanId(loanId));
//        model.addAttribute("WOs", jsonWriteOffs);

//        String jsonSupervisorPlans = gson.toJson(getSPsByLoanId(loanId));
//        model.addAttribute("SPs", jsonSupervisorPlans);

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

        String jsonTargetedUses = gson.toJson(getTUsByLoanId(loanId));
        model.addAttribute("TUs", jsonTargetedUses);

        String jsonReconstructuredLists = gson.toJson(getRLsByLoanId(loanId));
        model.addAttribute("RLs", jsonReconstructuredLists);

        String jsonBankrupts = gson.toJson(getBankruptsByLoanId(loanId));
        model.addAttribute("bankrupts", jsonBankrupts);

//        String jsonCollectionPhases = gson.toJson(getPhasesByLoanId(loanId));
//        model.addAttribute("phases", jsonCollectionPhases);

        model.addAttribute("debtorId", debtorId);
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtor", debtor);

//        String jsonDetailedSummaries = gson.toJson(getDetailedSummariesByLoanId(loanId));
//        model.addAttribute("detailedSummaries", jsonDetailedSummaries);

//        String jsonSummaries = gson.toJson(getSummariesByLoanId(loanId));
//        model.addAttribute("summaries", jsonSummaries);

//        String jsonAccrues = gson.toJson(getAccruesByLoanId(loanId));
//        model.addAttribute("accrues", jsonAccrues);

        User user = userService.findByUsername(Utils.getPrincipal());
//        System.out.println(user.getId());
//        System.out.println(loan.getSupervisorId());
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        model.addAttribute("loggedinuserId",user.getId());


        return "/manage/debtor/loan/view";
    }

    @GetMapping("/loanFinGroup/{loanId}/save")
    public String updateLoanFinGroup(Model model,@PathVariable(value = "loanId") Long loanId){
	    Loan loan=loanService.getById(loanId);
	    if (loan.getLoanFinGroup()!=null){
            model.addAttribute("loanFinGroup",loan.getLoanFinGroup());
        }
	    else{
            model.addAttribute("loanFinGroup",loanFinGroupService.getById(Long.valueOf(2)));
        }
        model.addAttribute("loanId",loanId);
        model.addAttribute("finGroupList",loanFinGroupService.list());
        return "/manage/debtor/loan/loanFinGroupForm";
    }

    @PostMapping("/loanFinGroup/{loanId}/save")
    public String updateFinGroup(@PathVariable(value = "loanId") Long loanId ,@Validated @ModelAttribute(value = "finGroup") LoanFinGroup finGroup, BindingResult result){

        Long debtorId=null;
	    if(result.hasErrors()){
            System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
        }
        else {
            LoanFinGroup loanFinGroup=loanFinGroupService.getById(finGroup.getId());
            Loan loan=loanService.getById(loanId);
            debtorId=loan.getDebtor().getId();
            loan.setLoanFinGroup(loanFinGroup);
            loanService.update(loan);
        }
	    return "redirect:" + "/manage/debtor/"+debtorId+"/loan/{loanId}/view";
    }

    @GetMapping("/loanFund/{loanId}/save")
    public String updateLoanFund(Model model,@PathVariable(value = "loanId") Long loanId){
        Loan loan=loanService.getById(loanId);
        if (loan.getFund()!=null){
            model.addAttribute("fund",loan.getFund());
        }
        else{
            model.addAttribute("fund",fundService.getById(Long.valueOf(30)));
        }
        model.addAttribute("loanId",loanId);
        model.addAttribute("fundList",fundService.list());
        return "/manage/debtor/loan/loanFundForm";
    }

    @PostMapping("/loanFund/{loanId}/save")
    public String updateFund(@PathVariable(value = "loanId") Long loanId ,@Validated @ModelAttribute(value = "fund") OrderTermFund fund, BindingResult result){

        Long debtorId=null;
        if(result.hasErrors()){
            System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
        }
        else {
            OrderTermFund loanFund=fundService.getById(fund.getId());
            Loan loan=loanService.getById(loanId);
            debtorId=loan.getDebtor().getId();
            loan.setFund(loanFund);
            loanService.update(loan);
        }
        return "redirect:" + "/manage/debtor/"+debtorId+"/loan/{loanId}/view";
    }

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{classId}/{loanId}/save", method=RequestMethod.GET)
	public String formLoan(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("classId")Integer classId, @PathVariable("loanId")Long loanId)
	{
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtor);
        model.addAttribute("classId", classId);

		if(loanId == 0)
		{
		    if(classId == 1) {
                NormalLoan normalLoan = new NormalLoan();
                normalLoan.setAmount(0.0);
                normalLoan.setRegDate(new Date());
                normalLoan.setLoanState(loanStateService.getById(Long.valueOf(1)));
                normalLoan.setLoanType(loanTypeService.getById(Long.valueOf(1)));
                normalLoan.setCurrency(orderTermCurrencyService.getById(Long.valueOf(1)));
                normalLoan.setLoanFinGroup(loanFinGroupService.getById(Long.valueOf(2)));
                normalLoan.setFund(orderTermFundService.getById(Long.valueOf(30)));
                model.addAttribute("loan", normalLoan);
            }
		    else if(classId == 2){
                TrancheeLoan trancheeLoan= new TrancheeLoan();
                trancheeLoan.setAmount(0.0);
                trancheeLoan.setRegDate(new Date());
                trancheeLoan.setLoanState(loanStateService.getById(Long.valueOf(1)));
                trancheeLoan.setLoanType(loanTypeService.getById(Long.valueOf(1)));
                trancheeLoan.setCurrency(orderTermCurrencyService.getById(Long.valueOf(1)));
                trancheeLoan.setLoanFinGroup(loanFinGroupService.getById(Long.valueOf(2)));
                trancheeLoan.setFund(orderTermFundService.getById(Long.valueOf(30)));
                model.addAttribute("loan", trancheeLoan);
            }
		    else{
                RestructuredLoan restructuredLoan=new RestructuredLoan();
                restructuredLoan.setAmount(0.0);
                restructuredLoan.setRegDate(new Date());
                restructuredLoan.setLoanState(loanStateService.getById(Long.valueOf(1)));
                restructuredLoan.setLoanType(loanTypeService.getById(Long.valueOf(1)));
                restructuredLoan.setCurrency(orderTermCurrencyService.getById(Long.valueOf(1)));
                restructuredLoan.setLoanFinGroup(loanFinGroupService.getById(Long.valueOf(2)));
                restructuredLoan.setFund(orderTermFundService.getById(Long.valueOf(30)));
                model.addAttribute("loan", restructuredLoan);
            }
            model.addAttribute("ownerText", "");
			model.addAttribute("orderText", "");
			model.addAttribute("pLoanText", "");
		}

		if(loanId > 0)
		{
			Loan loan = loanService.getById(loanId);
			if(loan.getFund()==null){
			    loan.setFund(orderTermFundService.getById(Long.valueOf(30)));
            }
			model.addAttribute("loan", loan);
			User user = userService.findById(loan.getSupervisorId());
			Staff staff = staffRepository.findById(user.getStaff().getId());

			String supervisorText = "[" + staff.getId() + "] " + staff.getName();
			model.addAttribute("supervisorText", supervisorText);

			CreditOrder order = loan.getCreditOrder();
			String orderText = "[" + order.getId() + "] "+ order.getRegNumber();
			model.addAttribute("orderText", orderText);

			Loan pLoan = loan.getParent();
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
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/1/save", method=RequestMethod.POST)
	public String saveLoan(NormalLoan loan,
			@PathVariable("debtorId")Long debtorId)
	{
		
		Debtor debtor = debtorService.getById(debtorId);
		loan.setDebtor(debtor);
		setBankruptData(loan);
		if(loan.getLoanType().getId()==10){
		    loan.setCloseDate(null);
        }

		saveLoanData(loan);

		return "redirect:" + "/manage/debtor/{debtorId}/loan/"+loan.getId()+"/view";
	}

    @RequestMapping(value="/manage/debtor/{debtorId}/loan/2/save", method=RequestMethod.POST)
    public String saveTrancheLoan(TrancheeLoan loan,
                           @PathVariable("debtorId")Long debtorId)
    {

        Debtor debtor = debtorService.getById(debtorId);
        loan.setDebtor(debtor);
        setBankruptData(loan);

        if(loan.getLoanType().getId()==10){
            loan.setCloseDate(null);
        }

        saveLoanData(loan);

        return "redirect:" + "/manage/debtor/{debtorId}/loan/"+loan.getId()+"/view";
    }

    @RequestMapping(value="/manage/debtor/{debtorId}/loan/3/save", method=RequestMethod.POST)
    public String saveRestructuredLoan(RestructuredLoan loan,
                                  @PathVariable("debtorId")Long debtorId)
    {

        Debtor debtor = debtorService.getById(debtorId);
        loan.setDebtor(debtor);
        setBankruptData(loan);

        if(loan.getLoanType().getId()==10){
            loan.setCloseDate(null);
        }

        saveLoanData(loan);

        return "redirect:" + "/manage/debtor/{debtorId}/loan/"+loan.getId()+"/view";
    }

    private void setBankruptData(Loan loan)
    {
        Set<Bankrupt> bankrupts = loan.getBankrupts();
        CurrencyRate rate = null;
        for (Bankrupt b:bankrupts){
            if(b.getStartedOnDate() != null)
            {
                rate = currencyRateService.findByDateAndType(b.getStartedOnDate(), loan.getCurrency());
                break;
            }
        }

//        if (rate == null)
//            rate = currencyRateService.findByDateAndType(loan.getCloseDate(), loan.getCurrency());

        if (rate != null)
            loan.setCloseRate(rate.getRate());
        else
            loan.setCloseRate(1.0);
    }

    private void saveLoanData(Loan loan)
    {
        if(loan.getId() == null)
        {
            if(loan.getParent().getId() != null)
            {
                Loan tLoan = loanRepository.findOne(loan.getParent().getId());
                loan.setParent(tLoan);
            }
            else
                loan.setParent(null);

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
            if(loan.getParent().getId() != null)
            {
                Loan tLoan = loanRepository.findOne(loan.getParent().getId());
                loan.setParent(tLoan);
            }
            else
                loan.setParent(null);

            CreditOrder creditOrder = creditOrderRepository.findOne(loan.getCreditOrder().getId());
            loan.setCreditOrder(creditOrder);

            Staff staff = staffRepository.findById(loan.getSupervisorId());
            User user = userService.findByStaff(staff);

            loan.setSupervisorId(user.getId());
            loggerLoan.info("updateLoan : {}", loan);
            loanService.update(loan);
        }
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

    @RequestMapping(value = "/manage/debtor/{debtorId}/loan/{loanId}/summary/{id}/view",method = RequestMethod.GET)
    public String viewSummary(ModelMap model,@PathVariable("debtorId") Long debtorId,@PathVariable("loanId") Long loanId,@PathVariable("id") Long id){

	    Loan loan=loanService.getById(loanId);

        LoanSummary loanSummary=loanSummaryService.getById(id);
        String name="";
        if(loan.getCurrency().getId()!=17){
            name=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+loanSummary.getOnDate()+". в тыс. "+loan.getCurrency().getName();
        }
        else{
            name=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+loanSummary.getOnDate()+". в тоннах "+loan.getCurrency().getName();
        }

        Double rate=currencyRateService.findByDateAndType(loanSummary.getOnDate(),loan.getCurrency()).getRate();

        Boolean notKGS=false;
        LoanSummary newLoanSummary=new LoanSummary();
        if(loan.getCurrency().getId()!=1){
            notKGS=true;
            newLoanSummary.setLoanAmount(loanSummary.getLoanAmount()*rate);
            newLoanSummary.setTotalDisbursed(loanSummary.getTotalDisbursed()*rate);
            newLoanSummary.setPaidPrincipal(loanSummary.getPaidPrincipal()*rate);
            newLoanSummary.setPaidInterest(loanSummary.getPaidInterest()*rate);
            newLoanSummary.setPaidPenalty(loanSummary.getPaidPenalty()*rate);
            newLoanSummary.setPaidFee(loanSummary.getPaidFee()*rate);
            newLoanSummary.setTotalOutstanding(loanSummary.getTotalOutstanding()*rate);
            newLoanSummary.setOutstadingPrincipal(loanSummary.getOutstadingPrincipal()*rate);
            newLoanSummary.setOutstadingInterest(loanSummary.getOutstadingInterest()*rate);
            newLoanSummary.setOutstadingPenalty(loanSummary.getOutstadingPenalty()*rate);
            newLoanSummary.setTotalOverdue(loanSummary.getTotalOverdue()*rate);
            newLoanSummary.setOverduePrincipal(loanSummary.getOverduePrincipal()*rate);
            newLoanSummary.setOverdueInterest(loanSummary.getOverdueInterest()*rate);
            newLoanSummary.setOverduePenalty(loanSummary.getOverduePenalty()*rate);
            newLoanSummary.setOverdueFee(loanSummary.getOverdueFee()*rate);
            newLoanSummary.setTotalPrincipalPaid(loanSummary.getTotalPrincipalPaid()*rate);
            newLoanSummary.setTotalInterestPaid(loanSummary.getTotalInterestPaid()*rate);
            newLoanSummary.setTotalPenaltyPaid(loanSummary.getTotalPenaltyPaid()*rate);
            newLoanSummary.setTotalFeePaid(loanSummary.getTotalFeePaid()*rate);
            newLoanSummary.setOnDate(loanSummary.getOnDate());
            newLoanSummary.setTotalPaidKGS(loanSummary.getTotalPaidKGS());
            model.addAttribute("newSummary",newLoanSummary);
        }
	    else{
            model.addAttribute("newSummary",loanSummary);
        }
	    model.addAttribute("debtorId",debtorId);
        model.addAttribute("debtor",debtorService.getById(debtorId));
        model.addAttribute("loanId",loanId);
        model.addAttribute("loan",loan);
        model.addAttribute("summary",loanSummary);
        model.addAttribute("name",name);
        model.addAttribute("rate","в тыс. сомах по курсу "+rate);
        model.addAttribute("notKGS",notKGS);

	    return "/manage/debtor/loan/loanSummary";
    }

    @PostMapping("/paymentScheduleRequest/{loanId}")
    @ResponseBody
    public String getListOfPaymentSchedules(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getPaymentSchedulesByLoanId(loanId));
        return result;
    }

    @PostMapping("/paymentRequest/{loanId}")
    @ResponseBody
    public String getListOfPayments(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getPaymentsByLoanId(loanId));
        return result;
    }
    @PostMapping("/writeOffsRequest/{loanId}")
    @ResponseBody
    public String getListOfWriteOffs(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonWriteOffs = gson.toJson(getWOsByLoanId(loanId));
        return jsonWriteOffs;
    }

    @PostMapping("/loanSummaryRequest/{loanId}")
    @ResponseBody
    public String getListOfLoanSummaries(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getSummariesByLoanId(loanId));
        return result;
    }

    @PostMapping("/detailedLoanSummaryRequest/{loanId}")
    @ResponseBody
    public String getListOfDetailedLoanSummaries(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
//        ,@RequestParam Map<String,String> datatable

//        String pageStr = datatable.get("datatable[pagination][page]");
//        String perPageStr = datatable.get("datatable[pagination][perpage]");

        List<LoanDetailedSummaryModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
//        LIMIT "+pageStr+","+perPageStr
        String baseQuery="select * from loanDetailedSummary where loanId="+loanId+" ORDER BY onDate asc ";

        Query query=entityManager.createNativeQuery(baseQuery,LoanDetailedSummaryModel.class);
        result=query.getResultList();

        String result1 = gson.toJson(result);

        return result1;
    }

    @PostMapping("/accrueRequest/{loanId}")
    @ResponseBody
    public String getListOfAccrues(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getAccruesByLoanId(loanId));
        return result;
    }


    @PostMapping("/supervisorPlanRequest/{loanId}")
    @ResponseBody
    public String getListOfSupervisorPlans(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getSPsByLoanId(loanId));
        return result;
    }

    @PostMapping("/childrenRequest/{loanId}")
    @ResponseBody
    public String getListOfChildren(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getChildrenByLoanId(loanId));
        return result;
    }

    @PostMapping("/creditTermRequest/{loanId}")
    @ResponseBody
    public String getListOfCreditTerms(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getTermsByLoanId(loanId));
        return result;
    }

    @PostMapping("/agreements/{loanId}")
    @ResponseBody
    public String getListOfAgreements(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getCollAgreementsByLoanId(loanId));
        return result;
    }

    @PostMapping("/phases/{loanId}")
    @ResponseBody
    public String getListOfPhases(@PathVariable("loanId") Long loanId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getPhasesByLoanId(loanId));
        return result;
    }

    private List<CreditTermModel> getTermsByLoanId(long loanId)
    {
        List<CreditTermModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(CreditTerm termm: loan.getCreditTerms())
        {
            CreditTerm term=creditTermService.getById(termm.getId());


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
            model.setRecord_status(term.getRecord_status());
            result.add(model);
        }

		Collections.sort(result);
        return result;
    }

    private List<LoanModel> getChildrenByLoanId(long loanId)
    {
        List<LoanModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(Loan childd: loan.getChildren())
        {
            Loan child=loanService.getById(childd.getId());
            LoanModel model = new LoanModel();
            model.setId(child.getId());
            model.setRegNumber(child.getRegNumber());
            model.setRegDate(child.getRegDate());
            model.setAmount(child.getAmount());
            model.setCurrencyId(child.getCurrency().getId());
            model.setCurrencyName(child.getCurrency().getName());
            model.setLoanTypeId(child.getLoanType().getId());
            model.setLoanTypeName(child.getLoanType().getName());
            model.setLoanStateId(child.getLoanState().getId());
            model.setLoanStateName(child.getLoanState().getName());
            model.setSupervisorId(child.getSupervisorId());
            model.setParentLoanId(child.getParent().getId());
            model.setCreditOrderId(child.getCreditOrder().getId());

            result.add(model);
        }

        Collections.sort(result);
        return result;
    }


    private List<PaymentScheduleModel> getPaymentSchedulesByLoanId(long loanId)
    {
        List<PaymentScheduleModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(PaymentSchedule pss: loan.getPaymentSchedules())
        {
            PaymentSchedule ps=paymentScheduleService.getById(pss.getId());
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
            model.setRecord_status(ps.getRecord_status());

            result.add(model);
        }

        Collections.sort(result);

        return result;
    }


    private List<PaymentModel> getPaymentsByLoanId(long loanId)
    {
        List<PaymentModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(Payment pp: loan.getPayments())
        {
            PaymentModel model = new PaymentModel();

            Payment p = this.paymentService.getById(pp.getId());

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
            model.setRecord_status(p.getRecord_status());

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
            model.setRecord_status(w.getRecord_status());

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
            model.setRecord_status(sp.getRecord_status());

            result.add(model);
        }


        Collections.sort(result);
        return result;
    }

    private List<LoanDetailedSummaryModel> getDetailedSummariesByLoanId(long loanId,Integer offset, Integer perpage)
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
            if(d.getLoanSummaryType()!=LoanSummaryType.DAILY)
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
                model.setRecord_status(d.getRecord_status());

                result.add(model);
            }
        }
        LoanSummary d=loanSummaryService.getLastByLoanSummaryType("DAILY");
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
        model.setRecord_status(d.getRecord_status());

        result.add(model);


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
        for(LoanGoods dd: loan.getLoanGoods())
        {
            LoanGoods d=loanGoodsService.getById(dd.getId());
            LoanGoodsModel model = new LoanGoodsModel();
            model.setId(d.getId());
            model.setQuantity(d.getQuantity());
            try{

                model.setUnitType(d.getQuantityType().getName());
            }
            catch(Exception e){
                model.setUnitType(" ");
            }
            try{

                model.setGoodsType(d.getGoodType().getName());
            }
            catch(Exception e){
                model.setGoodsType(" ");
            }
//            model.setGoodsType(d.getGoodsTypeId());

            result.add(model);
        }

        return result;
    }

    private List<DebtTransferModel> getDTsByLoanId(long loanId)
    {
        List<DebtTransferModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(DebtTransfer dd: loan.getDebtTransfers())
        {
            DebtTransfer d=debtTransferService.getById(dd.getId());
            DebtTransferModel model = new DebtTransferModel();
            model.setId(d.getId());
            model.setNumber(d.getNumber());
            model.setDate(d.getDate());
            model.setQuantity(d.getQuantity());
            model.setPricePerUnit(d.getPricePerUnit());
//            model.setUnitTypeId(d.getUnitTypeId());
            try{

                model.setUnitType(d.getQuantityType().getName());
            }
            catch(Exception e){
                model.setUnitType(" ");
            }
            model.setTotalCost(d.getTotalCost());
            model.setTransferPaymentId(d.getTransferPaymentId());
            model.setTransferCreditId(d.getTransferCreditId());
            model.setTransferPersonId(d.getTransferPersonId());
            try{
                model.setTransferPersonName(debtorService.getByOwnerId(d.getTransferPersonId()).getName());
            }
            catch (Exception e){
                System.out.println(e);
            }
//            model.setGoodsTypeId(d.getGoodsTypeId());
            try{

                model.setGoodsType(d.getGoodType().getName());
            }
            catch(Exception e){
                model.setGoodsType(" ");
            }
            result.add(model);
        }

        Collections.sort(result);
        return result;
    }

    private List<TargetedUseModel> getTUsByLoanId(long loanId)
    {
        List<TargetedUseModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(TargetedUse d: loan.getTargetedUses())
        {
            TargetedUseModel model = new TargetedUseModel();
            model.setId(d.getId());
            model.setTargetedUseResultId(d.getTargetedUseResultId());
            model.setCreatedById(d.getCreatedById());
            model.setCreatedDate(d.getCreatedDate());
            model.setApprovedById(d.getApprovedById());
            model.setApprovedDate(d.getApprovedDate());
            model.setCheckedById(d.getCheckedById());
            model.setCheckedDate(d.getCheckedDate());
            model.setAttachmentId(d.getAttachmentId());

            result.add(model);
        }

        Collections.sort(result);
        return result;
    }

    private List<ReconstructedListModel> getRLsByLoanId(long loanId)
    {
        List<ReconstructedListModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(ReconstructedList d: loan.getReconstructedLists())
        {
            ReconstructedListModel model = new ReconstructedListModel();
            model.setId(d.getId());
            model.setOnDate(d.getOnDate());
            model.setOldLoanId(d.getOldLoanId());

            result.add(model);
        }

        Collections.sort(result);
        return result;
    }

    private List<BankruptModel> getBankruptsByLoanId(long loanId)
    {
        List<BankruptModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(Bankrupt d: loan.getBankrupts())
        {
            BankruptModel model = new BankruptModel();
            model.setId(d.getId());
            model.setStartedOnDate(d.getStartedOnDate());
            model.setFinishedOnDate(d.getFinishedOnDate());

            result.add(model);
        }

        Collections.sort(result);
        return result;
    }

    private List<CollectionPhaseModel> getPhasesByLoanId(long loanId)
    {
        List<CollectionPhaseModel> result = new ArrayList<>();
        Loan loan = loanService.getById(loanId);
        for(CollectionPhase dd: loan.getCollectionPhases())
        {
            CollectionPhaseModel model = new CollectionPhaseModel();
            CollectionPhase d=collectionPhaseService.getById(dd.getId());
            model.setId(d.getId());
            model.setStartDate(d.getStartDate());
            model.setCloseDate(d.getCloseDate());
            model.setLastEvent(d.getLastEvent());
            model.setLastStatusId(d.getLastStatusId());
            model.setPhaseTypeId(d.getPhaseType().getId());
            model.setPhaseTypeName(d.getPhaseType().getName());
            model.setPhaseStatusId(d.getPhaseStatus().getId());
            model.setPhaseStatusName(d.getPhaseStatus().getName());
            model.setProcedureId(d.getCollectionProcedure().getId());

            result.add(model);
        }

        Collections.sort(result);
        return result;
    }

    private List<CollateralAgreementModel> getCollAgreementsByLoanId(long loanId)
    {
        Map<Long, CollateralAgreementModel> models = new HashMap<>();
        Loan loan=loanService.getById(loanId);
        Set<CollateralAgreement> agreements = loan.getCollateralAgreements();
        for(CollateralAgreement agreement1: agreements)
        {
            CollateralAgreement agreement=collateralAgreementService.getById(agreement1.getId());
            List<CollateralItem> items = collateralItemReposiory.findByCollateralAgreementId(agreement.getId());
            for(CollateralItem item1: items)
            {
                CollateralItem item=collateralItemService.getById(item1.getId());
                CollateralAgreementModel model = new CollateralAgreementModel();
                model.setId(agreement.getId());
                model.setAgreementNumber(agreement.getAgreementNumber());
                model.setAgreementDate(agreement.getAgreementDate());
                model.setCollateralOfficeRegNumber(agreement.getCollateralOfficeRegNumber());
                model.setCollateralOfficeRegDate(agreement.getCollateralOfficeRegDate());
                model.setNotaryOfficeRegNumber(agreement.getNotaryOfficeRegNumber());
                model.setNotaryOfficeRegDate(agreement.getNotaryOfficeRegDate());
                model.setArrestRegNumber(agreement.getArrestRegNumber());
                model.setArrestRegDate(agreement.getArrestRegDate());
                model.setOwnerId(agreement.getOwner().getId());
                model.setItemId(item.getId());
                model.setItemName(item.getName());
                model.setItemDescription(item.getDescription());
                model.setItemTypeId(item.getItemType().getId());
                model.setItemTypeName(item.getItemType().getName());
                model.setQuantity(item.getQuantity());
                model.setQuantityTypeId(item.getQuantityType().getId());
                model.setQuantityTypeName(item.getQuantityType().getName());
                model.setCollateralValue(item.getCollateralValue());

                if(!models.containsKey(model.getItemId()))
                    models.put(model.getItemId(), model);
            }

        }

        List<CollateralAgreementModel> result = new ArrayList<>();
        for(CollateralAgreementModel model: models.values())
            result.add(model);

        Collections.sort(result);
        return result;

    }

//    private List<CollectionPhaseModel> getPhasesByLoanId(long loanId)
//    {
//        List<CollectionPhaseModel> result = new ArrayList<>();
//        Loan loan=loanService.getById(loanId);
//        for(CollectionPhase tempp: loan.getCollectionPhases())
//        {
//            CollectionPhase temp=collectionPhaseService.getById(tempp.getId());
//
//            CollectionPhaseModel model = new CollectionPhaseModel();
//            model.setId(temp.getId());
//            model.setStartDate(temp.getStartDate());
//            model.setCloseDate(temp.getCloseDate());
//            model.setLastEvent(temp.getLastEvent());
//            model.setLastStatusId(temp.getLastStatusId());
//            model.setPhaseStatusId(temp.getPhaseStatus().getId());
//            model.setPhaseStatusName(temp.getPhaseStatus().getName());
//            model.setPhaseTypeId(temp.getPhaseType().getId());
//            model.setPhaseTypeName(temp.getPhaseType().getName());
//            model.setProcedureId(temp.getCollectionProcedure().getId());
//            result.add(model);
//        }
//
//        Collections.sort(result);
//
//        return result;
//    }

}
