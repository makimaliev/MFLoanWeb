package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.loan.CreditTerm;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermDaysMethod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFloatingRateType;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermRatePeriod;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermTransactionOrder;
import kg.gov.mf.loan.manage.repository.loan.CreditTermRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.CreditTermService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermDaysMethodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFloatingRateTypeService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermRatePeriodService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermTransactionOrderService;
import kg.gov.mf.loan.web.fetchModels.CreditTermAuditModel;
import kg.gov.mf.loan.web.fetchModels.PaymentScheduleAuditModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CreditTermController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	CreditTermService termService;
	
	@Autowired
	OrderTermRatePeriodService ratePeriodService;
	
	@Autowired
	OrderTermFloatingRateTypeService rateTypeService;
	
	@Autowired
	OrderTermTransactionOrderService txOrderService;
	
	@Autowired
	OrderTermDaysMethodService daysMethodService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	CreditTermRepository termRepository;

	@Autowired
	OrderTermRatePeriodService orderTermRatePeriodService;

	@Autowired
	OrderTermFloatingRateTypeService orderTermFloatingRateTypeService;

	@Autowired
	UserService userService;

	@Autowired
	StaffService staffService;

	@PersistenceContext
	EntityManager entityManager;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/term/{termId}/view", method=RequestMethod.GET)
	public String view(ModelMap model,
								 @PathVariable("debtorId")Long debtorId,
								 @PathVariable("loanId")Long loanId,
								 @PathVariable("termId")Long termId){
		CreditTerm creditTerm=termService.getById(termId);
		model.addAttribute("term",creditTerm);

		String createdByStr=null;
		String modifiedByStr=null;

		if(creditTerm.getAuCreatedBy()!=null){
			if(creditTerm.getAuCreatedBy().equals("admin")){
				createdByStr="Система";
			}
			else{
				User createdByUser=userService.findByUsername(creditTerm.getAuCreatedBy());
				Staff createdByStaff=createdByUser.getStaff();
				createdByStr=createdByStaff.getName();
			}
		}
		if(creditTerm.getAuLastModifiedBy()!=null){
			if(creditTerm.getAuLastModifiedBy().equals("admin")){
				modifiedByStr="Система";
			}
			else{
				User lastModifiedByUser=userService.findByUsername(creditTerm.getAuLastModifiedBy());
				Staff lastModifiedByStaff=lastModifiedByUser.getStaff();
				modifiedByStr=lastModifiedByStaff.getName();
			}
		}

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonHistory= gson.toJson(historyOfTerms(termId));
		model.addAttribute("jsonHistory", jsonHistory);

		model.addAttribute("createdBy",createdByStr);
		model.addAttribute("modifiedBy",modifiedByStr);


		return "/manage/debtor/loan/term/view";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/term/{termId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("termId")Long termId)
	{
		
		if(termId == 0)
		{
			CreditTerm term=new CreditTerm();
			term.setStartDate(new Date());
			term.setInterestRateValue(Double.valueOf(0));
			term.setPenaltyOnPrincipleOverdueRateValue(Double.valueOf(0));
			term.setPenaltyOnInterestOverdueRateValue(Double.valueOf(0));
			term.setPenaltyLimitPercent(Double.valueOf(20));
			term.setFloatingRateType(orderTermFloatingRateTypeService.getById(Long.valueOf(2)));
			term.setPenaltyOnInterestOverdueRateType(orderTermFloatingRateTypeService.getById(Long.valueOf(2)));
			term.setPenaltyOnPrincipleOverdueRateType(orderTermFloatingRateTypeService.getById(Long.valueOf(2)));
			term.setRatePeriod(orderTermRatePeriodService.getById(Long.valueOf(1)));
			term.setTransactionOrder(txOrderService.getById(Long.valueOf(1)));
			term.setDaysInMonthMethod(daysMethodService.getById(Long.valueOf(1)));
			term.setDaysInYearMethod(daysMethodService.getById(Long.valueOf(1)));
			term.setRecord_status(1);
			model.addAttribute("term", term);
		}
			
		if(termId > 0)
		{
			model.addAttribute("term", termService.getById(termId));
		}
		
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
        
        model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/term/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/term/save"}, method=RequestMethod.POST)
    public String saveCreditTerm(CreditTerm term,
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("loanId")Long loanId,
    		ModelMap model) {
		
		Loan loan = loanService.getById(loanId);
		term.setLoan(loan);
		
		if(term.getId() == 0)
			termRepository.save(term);
		else
			termRepository.save(term);
		
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/term/{loanId/delete", method=RequestMethod.GET)
    public String deleteCreditTerm(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("termId")Long termId) {
		if(termId > 0)
            termRepository.delete(termRepository.findOne(termId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

	//    get audited history of creditTerm
	private List<CreditTermAuditModel> historyOfTerms(Long termId){

		String getCreditTerms="select c.*,s.name as staffName,uR.timestamp as date,null as daysInMonthMethodName,null as daysInYearMethodName,\n" +
				"       null as floatingRateTypeName, null as penaltyOnInterestOverdueRateTypeName,\n" +
				"       null as penaltyOnPrincipleOverdueRateTypeName,null as ratePeriodName,null as transactionOrderName\n" +
				"from creditTerm_AUD c,user_revisions uR,users u,staff s\n" +
				"where\n" +
				"  u.username=uR.username and u.staff_id=s.id and uR.id=c.REV AND c.id="+termId;
		Query query=entityManager.createNativeQuery(getCreditTerms,CreditTermAuditModel.class);
		List<CreditTermAuditModel> result=query.getResultList();
		for (CreditTermAuditModel model:result){
			model.setDaysInMonthMethodName(daysMethodService.getById(model.getDaysInMonthMethodId()).getName());
			model.setDaysInYearMethodName(daysMethodService.getById(model.getDaysInYearMethodId()).getName());
			model.setFloatingRateTypeName(orderTermFloatingRateTypeService.getById(model.getFloatingRateTypeId()).getName());
			model.setPenaltyOnInterestOverdueRateTypeName(orderTermFloatingRateTypeService.getById(model.getPenaltyOnInterestOverdueRateTypeId()).getName());
			model.setPenaltyOnPrincipleOverdueRateTypeName(orderTermFloatingRateTypeService.getById(model.getPenaltyOnPrincipleOverdueRateTypeId()).getName());
			model.setRatePeriodName(ratePeriodService.getById(model.getRatePeriodId()).getName());
			model.setTransactionOrderName(txOrderService.getById(model.getTransactionOrderId()).getName());
		}

		return result;
	}
}
