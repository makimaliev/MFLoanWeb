package kg.gov.mf.loan.web.controller.manage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.CollectionProcedure;
import kg.gov.mf.loan.manage.model.collection.PhaseDetails;
import kg.gov.mf.loan.manage.model.orderterm.CurrencyRate;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.repository.loan.PaymentRepository;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.collection.CollectionProcedureService;
import kg.gov.mf.loan.manage.service.collection.PhaseDetailsService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.process.service.JobItemService;
import kg.gov.mf.loan.web.util.Pager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.loan.InstallmentState;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.model.loan.PaymentType;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.PaymentService;
import kg.gov.mf.loan.manage.service.loan.PaymentTypeService;
import kg.gov.mf.loan.web.util.Utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Controller
public class PaymentController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	PaymentTypeService pTypeService;
	
	@Autowired
	PaymentService paymentService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	PhaseDetailsService phaseDetailsService;

//	@PersistenceContext
//	private EntityManager entityManager;

	@Autowired
	EntityManager entityManager;

	@Autowired
	CollectionProcedureService collectionProcedureService;

	@Autowired
	CollectionPhaseService collectionPhaseService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	JobItemService jobItemService;

	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}


	@Autowired
	public PaymentController(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/payment/{paymentId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("paymentId")Long paymentId) throws ParseException {
		
		if(paymentId == 0)
		{
			Loan loan=loanService.getById(loanId);
			OrderTermCurrency orderTermCurrency=loan.getCurrency();
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			Date today=new Date();
			String currencyQuery="select c.id,c.rate,c.date,c.status,c.type_id,c.currency_type_id from currency_rate c where currency_type_id="+orderTermCurrency.getId()+" and date<='"+dt.format(today)+"' order by date desc";
			Query query=entityManager.createNativeQuery(currencyQuery,CurrencyRate.class);
			query.setMaxResults(1);
			CurrencyRate currencyRate=(CurrencyRate) query.getSingleResult();
			Payment payment=new Payment();
			payment.setPaymentDate(new Date());
			payment.setInterest(0.0);
			payment.setTotalAmount(0.0);
			payment.setPrincipal(0.0);
			payment.setPenalty(0.0);
			payment.setNumber("б/н");
			payment.setDetails(" ");
			payment.setFee(Double.valueOf(0));
			payment.setExchange_rate(currencyRate.getRate());
			payment.setPaymentType(pTypeService.getById(Long.valueOf(1)));
			payment.setRecord_status(1);
			model.addAttribute("payment", payment);
		}
			
		if(paymentId > 0)
		{
			model.addAttribute("payment", paymentService.getById(paymentId));
		}
		
		List<PaymentType> pTypes = pTypeService.list();
        model.addAttribute("pTypes", pTypes);
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/payment/save";
	}

	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/payment/save"}, method=RequestMethod.POST)
    public String savePayment(Payment payment, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId)
    {
    	if(payment.getPaymentDate()!=null){
		Loan loan = loanService.getById(loanId);
		if(payment.getPaymentDate()==null){
			payment.setPaymentDate(new Date());
		}
		if(payment.getTotalAmount()==null){
			payment.setTotalAmount(0.0);
		}
		if(payment.getInterest()==null){
			payment.setInterest(0.0);
		}
		if(payment.getPenalty()==null){
			payment.setPenalty(0.0);
		}
		if(payment.getPrincipal()==null){
			payment.setPrincipal(0.0);
		}
		if (loan.getCurrency().getId()==Long.valueOf(1)){
			payment.setIn_loan_currency(true);
		}
		else{
			payment.setIn_loan_currency(false);
		}
		payment.setLoan(loan);
		
		if(payment.getId() == 0){
			paymentService.add(payment);
		}
		else{
			paymentService.update(payment);
			}

		Session session;
		try
		{
			session = sessionFactory.getCurrentSession();
		}
		catch (HibernateException e)
		{
			session = sessionFactory.openSession();
		}
		session.getTransaction().begin();
			runUpdateOfPhases(loan);
		session.getTransaction().commit();

		this.jobItemService.runDailyCalculateProcedureForOneLoan(loanId,new Date());



			if(loan.getParent()!=null){

				this.jobItemService.runDailyCalculateProcedureForOneLoan(loan.getParent().getId(), new Date());
			}
			else{
				this.jobItemService.runDailyCalculateProcedureForOneLoan(loan.getId(), new Date());
			}

    	}



		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

	public void runUpdateOfPhases(Loan loan){


		for(CollectionPhase phase1:loan.getCollectionPhases()){
			CollectionPhase phase=collectionPhaseService.getById(phase1.getId());
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
			Date startDate=phase.getStartDate();
			Date closeDate=new Date();
			CollectionProcedure procedure=collectionProcedureService.getById(phase.getCollectionProcedure().getId());
			if(phase.getCloseDate()!=null){
				closeDate=phase.getCloseDate();
			}
			else if(procedure.getCloseDate()!=null){
				closeDate=phase.getCollectionProcedure().getCloseDate();
			}

			Double sumFeeP=0.0;
			Double sumPrincipalP=0.0;
			Double sumInterestP=0.0;
			Double sumPenaltyP=0.0;
			Double sumTotalP=0.0;
			for(PhaseDetails phaseDetails1: phase.getPhaseDetails()){

				PhaseDetails phaseDetails=phaseDetailsService.getById(phaseDetails1.getId());

				List<Payment> payments=paymentService.getFromToDate(loan.getId(),startDate,closeDate);
				Double sumFeeD=0.0;
				Double sumPrincipalD=0.0;
				Double sumInterestD=0.0;
				Double sumPenaltyD=0.0;
				Double sumTotalD=0.0;
				for(Payment payment:payments){
					sumFeeD=sumFeeD+payment.getFee();
					sumPenaltyD=sumPenaltyD+payment.getPenalty();
					sumPrincipalD=sumPrincipalD+payment.getPrincipal();
					sumInterestD=sumInterestD+payment.getInterest();
					sumTotalD=sumTotalD+payment.getTotalAmount();
				}
				phaseDetails.setPaidFee(sumFeeD);
				phaseDetails.setPaidPrincipal(sumPrincipalD);
				phaseDetails.setPaidPenalty(sumPenaltyD);
				phaseDetails.setPaidInterest(sumInterestD);
				phaseDetails.setPaidTotalAmount(sumTotalD);

				phaseDetailsService.update(phaseDetails);

				sumFeeP=sumFeeP+phaseDetails.getPaidFee();
				sumPenaltyP=sumPenaltyP+phaseDetails.getPaidPenalty();
				sumPrincipalP=sumPrincipalP+phaseDetails.getPaidPrincipal();
				sumInterestP=sumInterestP+phaseDetails.getPaidInterest();
				sumTotalP=sumTotalP+phaseDetails.getPaidTotalAmount();
			}
			phase.setPaid(sumFeeP+sumPenaltyP+sumInterestP+sumPrincipalP+sumTotalP);

			collectionPhaseService.update(phase);
		}
	}

    /*public void runUpdateQueries(Loan loan,Session session){


		for(CollectionPhase phase1:loan.getCollectionPhases()){
			CollectionPhase phase=collectionPhaseService.getById(phase1.getId());
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
			Date startDate=phase.getStartDate();
			Date closeDate=new Date();
			CollectionProcedure procedure=collectionProcedureService.getById(phase.getCollectionProcedure().getId());
			if(phase.getCloseDate()!=null){
				closeDate=phase.getCloseDate();
			}
			else if(procedure.getCloseDate()!=null){
				closeDate=phase.getCollectionProcedure().getCloseDate();
			}
			String closingDate=dateFormat.format(closeDate);
			try {
				String updatePhaseDetailsQuery="update phaseDetails\n" +
						"set phaseDetails.paidFee=(select sum(fee) from payment where loanId="+loan.getId()+" and paymentDate between '"+startDate+"' and '"+closingDate+"'),\n" +
						"    phaseDetails.paidInterest=(select sum(interest) from payment where loanId="+loan.getId()+" and paymentDate between '"+startDate+"' and '"+closingDate+"'),\n" +
						"    phaseDetails.paidPenalty=(select sum(penalty) from payment where loanId="+loan.getId()+" and paymentDate between '"+startDate+"' and '"+closingDate+"'),\n" +
						"    phaseDetails.paidPrincipal=(select sum(principal) from payment where loanId="+loan.getId()+" and paymentDate between '"+startDate+"' and '"+closingDate+"'),\n" +
						"    phaseDetails.paidTotalAmount=(select sum(totalAmount) from payment where loanId="+loan.getId()+" and paymentDate between '"+startDate+"' and '"+closingDate+"') " +
						"where collectionPhaseId="+phase.getId()+" and loan_id="+loan.getId();

				session.createSQLQuery(updatePhaseDetailsQuery).executeUpdate();
				String updatePhaseQuery="update collectionPhase\n" +
						"set collectionPhase.paid=(select paidTotalAmount from phaseDetails where loan_id="+loan.getId()+" and collectionPhaseId="+phase.getId()+") where id="+phase.getId();
				session.createSQLQuery(updatePhaseQuery).executeUpdate();
			}
			catch (Exception e){
					System.out.println(e);
			}

        }
	}*/
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/payment/{paymentId}/delete", method=RequestMethod.GET)
    public String deletePayment(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("paymentId")Long paymentId) {
		if(paymentId > 0)
            paymentRepository.delete(paymentRepository.findOne(paymentId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value = { "/manage/debtor/loan/payment/type/list" }, method = RequestMethod.GET)
    public String listAppliedEntityStates(ModelMap model) {
 
		List<PaymentType> types = pTypeService.list();
		model.addAttribute("types", types);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/loan/payment/type/list";
    }
	
	@RequestMapping(value="/manage/debtor/loan/payment/type/{typeId}/save", method=RequestMethod.GET)
	public String formEState(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new PaymentType());
		}
		
		if(typeId > 0)
		{
			model.addAttribute("type", pTypeService.getById(typeId));
		}
		return "/manage/debtor/loan/payment/type/save";
	}

	@RequestMapping(value="/manage/debtor/loan/payment/type/save", method=RequestMethod.POST)
    public String savePaymentType(PaymentType type, ModelMap model) {
		if(type.getId() == 0)
			pTypeService.add(type);
		else
			pTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/loan/payment/type/list";
    }
	
	@RequestMapping(value="/manage/debtor/loan/payment/type/delete", method=RequestMethod.POST)
    public String deletePaymentType(long id) {
		if(id > 0)
			pTypeService.remove(pTypeService.getById(id));
		return "redirect:" + "/manage/debtor/loan/payment/type/list";
    }

}
