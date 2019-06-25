package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.model.loan.PaymentType;
import kg.gov.mf.loan.manage.model.orderterm.CurrencyRate;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.repository.loan.PaymentRepository;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.collection.CollectionProcedureService;
import kg.gov.mf.loan.manage.service.collection.PhaseDetailsService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.PaymentService;
import kg.gov.mf.loan.manage.service.loan.PaymentTypeService;
import kg.gov.mf.loan.process.service.JobItemService;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
import kg.gov.mf.loan.web.util.Utils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    InformationService informationService;

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    SystemFileService systemFileService;

	@PersistenceContext
	private EntityManager entityManager;


    @Autowired
    CollectionProcedureService collectionProcedureService;

    @Autowired
    CollectionPhaseService collectionPhaseService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    JobItemService jobItemService;

    @Autowired
    UserService userService;

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }


    @Autowired
    public PaymentController(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }


    @RequestMapping(value = "/manage/debtor/{debtorId}/loan/{loanId}/payment/{paymentId}/view")
    public String view(ModelMap model,
                       @PathVariable("debtorId") Long debtorId,
                       @PathVariable("loanId") Long loanId,
                       @PathVariable("paymentId") Long paymentId){

        User user=userService.findByUsername(Utils.getPrincipal());

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonSysFiles= gson.toJson(getSystemFilesByPaymentId(paymentId));
        model.addAttribute("files", jsonSysFiles);

        Payment payment=paymentService.getById(paymentId);
        model.addAttribute("payment",payment);
        model.addAttribute("loanId",loanId);
        model.addAttribute("debtorId",debtorId);
        model.addAttribute("userId",user.getId());

        Attachment attachment=new Attachment();
        model.addAttribute("attachment",attachment);

        return "/manage/debtor/loan/payment/view";
    }

    @RequestMapping(value = "/manage/debtor/{debtorId}/loan/{loanId}/payment/{paymentId}/save", method = RequestMethod.GET)
    public String formCreditTerm(ModelMap model,
                                 @PathVariable("debtorId") Long debtorId,
                                 @PathVariable("loanId") Long loanId,
                                 @PathVariable("paymentId") Long paymentId) throws ParseException {


        Loan loan = loanService.getById(loanId);

        if (paymentId == 0) {
            Payment payment = new Payment();
            payment.setPaymentDate(new Date());
            payment.setInterest(0.0);
            payment.setTotalAmount(0.0);
            payment.setPrincipal(0.0);
            payment.setPenalty(0.0);
            payment.setNumber("б/н");
            payment.setDetails(" ");
            payment.setFee(Double.valueOf(0));
            payment.setExchange_rate(Double.valueOf(1));
            payment.setPaymentType(pTypeService.getById(Long.valueOf(1)));
            payment.setRecord_status(1);
            model.addAttribute("payment", payment);
        }

        if (paymentId > 0) {
            model.addAttribute("payment", paymentService.getById(paymentId));
        }

        List<PaymentType> pTypes = pTypeService.list();
        model.addAttribute("pTypes", pTypes);

        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loan);
        model.addAttribute("loan_currency", loan.getCurrency().getId());

        return "/manage/debtor/loan/payment/save";
    }

    @RequestMapping(value = {"/manage/debtor/{debtorId}/loan/{loanId}/payment/save"}, method = RequestMethod.POST)
    public String savePayment(Payment payment, @PathVariable("debtorId") Long debtorId, @PathVariable("loanId") Long loanId) {
        Loan loan = loanService.getById(loanId);
        Date onDate = new Date();

        if (payment.getPaymentDate().after(onDate)) {
            onDate = payment.getPaymentDate();
        }

        if (payment.getPaymentDate() != null) {

            if (loan.getCurrency().getId() > 1) {
                OrderTermCurrency orderTermCurrency = loan.getCurrency();
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

                String currencyQuery = "select c.id,c.rate,c.date,c.status,c.type_id,c.currency_type_id from currency_rate c where currency_type_id=" + orderTermCurrency.getId() + " and date<='" + dt.format(payment.getPaymentDate()) + "' order by date desc";
                Query query = entityManager.createNativeQuery(currencyQuery, CurrencyRate.class);
                query.setMaxResults(1);
                CurrencyRate currencyRate = (CurrencyRate) query.getSingleResult();

                payment.setExchange_rate(currencyRate.getRate());

            }

            if (payment.getPaymentDate() == null) {
                payment.setPaymentDate(new Date());
            }

            if (payment.getInterest() == null) {
                payment.setInterest(0.0);
            }

            if (payment.getPenalty() == null) {
                payment.setPenalty(0.0);
            }

            if (payment.getPrincipal() == null) {
                payment.setPrincipal(0.0);
            }

            if (loan.getCurrency().getId() == Long.valueOf(1)) {
                payment.setIn_loan_currency(true);
            }

            payment.setTotalAmount(payment.getInterest() + payment.getPenalty() + payment.getPrincipal());
//		else{
//			payment.setIn_loan_currency(false);
//		}
            payment.setLoan(loan);

            paymentRepository.save(payment);


//			this.jobItemService.runDailyCalculateProcedureForOneLoan(loanId,new Date());

            if (loan.getParent() != null) {
                this.jobItemService.runDailyCalculateProcedureForOneLoan(loan.getParent().getId(), onDate);
            } else {
                this.jobItemService.runDailyCalculateProcedureForOneLoan(loan.getId(), onDate);
            }
        }
        int x = 1; // However many threads you want
        System.out.println("=========================================================");
        System.out.println(new Date());
        System.out.println("=========================================================");
        ScheduledExecutorService someScheduler = Executors.newScheduledThreadPool(x);
        Runnable runnable = new Runnable() {
            public void run() {
                phaseAndPhaseDetailsUpdate(loan);
            }
        };
        long timeDelay = 30; // You can specify 1 what
        someScheduler.schedule(runnable, timeDelay, TimeUnit.SECONDS);
//		new Thread(new Runnable() {
//			public void run() {
//				phaseUpdater(debtorId);
//			}
//		}).start();

        return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";

    }


    public void phaseUpdater(Long debtorId) {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            session = sessionFactory.openSession();
        }

        session.getTransaction().begin();
        try {
            String phaseUpdateQuery = "update phaseDetails phd, phase_amount_view v,loan l\n" +
                    "set phd.paidTotalAmount = v.paid_amount,\n" +
                    "    phd.paidPrincipal = v.paid_principal,\n" +
                    "    phd.paidInterest = v.paid_interest,\n" +
                    "    phd.paidPenalty = v.paid_penalty\n" +
                    "where phd.id = v.det_id and phd.loan_id=l.id and l.debtorId=" + debtorId;
            session.createSQLQuery(phaseUpdateQuery).executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        Debtor debtor = debtorService.getById(debtorId);
        for (Loan loan1 : debtor.getLoans()) {
            Loan loan = loanService.getById(loan1.getId());
            for (CollectionPhase phase1 : loan.getCollectionPhases()) {
                try {
                    String phaseSetPaid = "update collectionPhase cph,phaseDetails det\n" +
                            "set cph.paid = (select sum(paidTotalAmount) from phaseDetails d where d.collectionPhaseId=" + phase1.getId() + ")\n" +
                            "where cph.id=det.collectionPhaseId and det.collectionPhaseId=" + phase1.getId();
                    session.createSQLQuery(phaseSetPaid).executeUpdate();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        session.getTransaction().commit();

    }

    @RequestMapping(value = "/manage/debtor/{debtorId}/loan/{loanId}/payment/{paymentId}/delete", method = RequestMethod.GET)
    public String deletePayment(@PathVariable("debtorId") Long debtorId, @PathVariable("loanId") Long loanId, @PathVariable("paymentId") Long paymentId) {
        if (paymentId > 0)
            paymentRepository.delete(paymentRepository.findOne(paymentId));
        return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

    @RequestMapping(value = {"/manage/debtor/loan/payment/type/list"}, method = RequestMethod.GET)
    public String listAppliedEntityStates(ModelMap model) {

        List<PaymentType> types = pTypeService.list();
        model.addAttribute("types", types);

        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/loan/payment/type/list";
    }

    @RequestMapping(value = "/manage/debtor/loan/payment/type/{typeId}/save", method = RequestMethod.GET)
    public String formEState(ModelMap model, @PathVariable("typeId") Long typeId) {
        if (typeId == 0) {
            model.addAttribute("type", new PaymentType());
        }

        if (typeId > 0) {
            model.addAttribute("type", pTypeService.getById(typeId));
        }
        return "/manage/debtor/loan/payment/type/save";
    }

    @RequestMapping(value = "/manage/debtor/loan/payment/type/save", method = RequestMethod.POST)
    public String savePaymentType(PaymentType type, ModelMap model) {
        if (type.getId() == 0)
            pTypeService.add(type);
        else
            pTypeService.update(type);

        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/loan/payment/type/list";
    }

    @RequestMapping(value = "/manage/debtor/loan/payment/type/delete", method = RequestMethod.POST)
    public String deletePaymentType(long id) {
        if (id > 0)
            pTypeService.remove(pTypeService.getById(id));
        return "redirect:" + "/manage/debtor/loan/payment/type/list";
    }

//    add information form
    @RequestMapping("/manage/debtor/{debtorId}/loan/{loanId}/payment/{paymentId}/addInformation")
    public String getAddInformationForm(Model model, @PathVariable("debtorId") Long debtorId, @PathVariable("loanId") Long loanId, @PathVariable("paymentId") Long paymentId){

        String ids="";
        ids=ids+"debtorId:"+debtorId+",";
        ids=ids+"loanId:"+loanId;
        model.addAttribute("ids",ids);

        Payment payment=paymentService.getById(paymentId);
        model.addAttribute("object",payment);
        model.addAttribute("systemObjectTypeId",5);

        model.addAttribute("attachment",new Attachment());

        return "/manage/debtor/loan/payment/addInformationForm";

    }

    public void phaseAndPhaseDetailsUpdate(Loan loan) {
        Set<Payment> payments = loan.getPayments();
        for (CollectionPhase phase1 : loan.getCollectionPhases()) {
            CollectionPhase phase = collectionPhaseService.getById(phase1.getId());
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
            runUpdateOfPhases(phase.getId(),session);
            session.getTransaction().commit();
        }
        System.out.println("finished");
    }

    public HashMap<String, Double> getPaids(Set<Payment> payments, Date fromDate, Date toDate) {
        HashMap<String, Double> result = new HashMap<>();
        Double totalAmount = 0.0;
        Double principal = 0.0;
        Double penalty = 0.0;
        Double interest = 0.0;
        Double fee = 0.0;
        for (Payment payment : payments) {
            Date paymentDate = payment.getPaymentDate();
            if (payment.getRecord_status() == 1 && paymentDate.after(fromDate) && paymentDate.before(toDate) && (payment.equals(fromDate) || payment.equals(toDate))) {
                totalAmount = totalAmount + payment.getTotalAmount();
                principal = principal + payment.getPrincipal();
                penalty = penalty + payment.getPenalty();
                interest = interest + payment.getInterest();
                fee = fee + payment.getFee();
            }
        }
        result.put("totalAmount", totalAmount);
        result.put("principal", principal);
        result.put("penalty", penalty);
        result.put("interest", interest);
        result.put("fee", fee);

        return result;

    }

//    update the phases
    public void runUpdateOfPhases(Long phaseId,Session session){



        try {
            String phaseUpdateQuery = "update phaseDetails phd, phase_amount_view v\n" +
                    "set phd.paidTotalAmount = v.paid_amount,\n" +
                    "  phd.paidPrincipal = v.paid_principal,\n" +
                    "  phd.paidInterest = v.paid_interest,\n" +
                    "  phd.paidPenalty = v.paid_penalty\n" +
                    "where phd.id = v.det_id and phd.collectionPhaseId =" + phaseId;
            session.createSQLQuery(phaseUpdateQuery).executeUpdate();

            String phaseSetPaid = "update collectionPhase cph,phaseDetails det\n" +
                    "set cph.paid = (select sum(distinct det.paidTotalAmount) from phaseDetails det where det.collectionPhaseId = "+phaseId+" group by det.collectionPhaseId)\n" +
                    "where cph.id=det.collectionPhaseId and det.collectionPhaseId="+phaseId;

            session.createSQLQuery(phaseSetPaid).executeUpdate();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    private List<SystemFileModel> getSystemFilesByPaymentId(Long paymentId){

        List<SystemFileModel> list=new ArrayList<>();
        List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(5,paymentId);
        for (Information information1:informations){
            Information information=informationService.findById(information1.getId());
            Set<Attachment> attachments= information.getAttachment();
            for (Attachment attachment1:attachments){
                Attachment attachment=attachmentService.findById(attachment1.getId());
                for (SystemFile systemFile1:attachment.getSystemFile()){
                    SystemFile systemFile=systemFileService.findById(systemFile1.getId());
                    SystemFileModel systemFileModel=new SystemFileModel();
                    systemFileModel.setAttachment_id(attachment.getId());
                    systemFileModel.setSys_name(systemFile.getName());
                    systemFileModel.setSystem_file_id(systemFile.getId());
                    systemFileModel.setAttachment_name(attachment.getName());
                    systemFileModel.setPath(systemFile.getPath());
                    list.add(systemFileModel);
                }
            }
        }

        return list;
    }
}
