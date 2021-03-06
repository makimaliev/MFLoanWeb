package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.PositionService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.ManageCounter;
import kg.gov.mf.loan.manage.model.collection.*;
import kg.gov.mf.loan.manage.model.debtor.*;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.LoanFinGroup;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.model.loan.PaymentSchedule;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.repository.ManageCounterRepository;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.service.collection.*;
import kg.gov.mf.loan.manage.service.debtor.DebtorGroupService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.DebtorSubGroupService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.manage.service.loan.LoanFinGroupService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.PaymentService;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.process.LoanDetailedSummaryService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import kg.gov.mf.loan.output.report.service.CollectionPhaseViewService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.output.report.utils.CalculationTool;
import kg.gov.mf.loan.process.service.JobItemService;
import kg.gov.mf.loan.web.fetchModels.*;
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
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CollectionPhaseController {
	
	//region services
	@Autowired
	CollectionPhaseService phaseService;

	@Autowired
	CollectionProcedureService procService;

	@Autowired
	ProcedureStatusService procedureStatusService;/**/

	@Autowired
	ProcedureTypeService procedureTypeService;

	@Autowired
	PhaseStatusService statusService;

	@Autowired
	PhaseTypeService typeService;

	@Autowired
	LoanService loanService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	LoanRepository loanRepository;

	@Autowired
	JobItemService jobItemService;

	@Autowired
	LoanDetailedSummaryService loanDetailedSummaryService;

	@Autowired
	PhaseDetailsService phaseDetailsService;

	@Autowired
	PhaseStatusService phaseStatusService;

	@Autowired
	LoanSummaryService loanSummaryService;

	@Autowired
	CollectionPhaseService collectionPhaseService;

	@Autowired
	CollectionPhaseGroupService collectionPhaseGroupService;

	@Autowired
	CollectionPhaseIndexService collectionPhaseIndexService;

	@Autowired
	CollectionPhaseSubIndexService collectionPhaseSubIndexService;

	@Autowired
	UserService userService;

	@Autowired
	ReferenceViewService referenceViewService;

	@Autowired
	SessionFactory sessionFactory;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	CollectionEventService collectionEventService;

	@Autowired
	StaffService staffService;

	@Autowired
	OwnerService ownerService;

	@Autowired
	PositionService positionService;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	PersonService personService;

	@Autowired
	EventStatusService eventStatusService;

	@Autowired
	EventTypeService eventTypeService;

	@Autowired
	InformationService informationService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	SystemFileService systemFileService;

	@Autowired
	CurrencyRateService currencyRateService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	CollectionPhaseViewService collectionPhaseViewService;

	@Autowired
	DebtorGroupService debtorGroupService;

	@Autowired
	DebtorSubGroupService debtorSubGroupService;

	@Autowired
	LoanFinGroupService loanFinGroupService;

	@Autowired
	ManageCounterRepository manageCounterRepository;

	//endregion


	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

//	private final ThreadLocal<List<PhaseDetails>> threadLocal=new ThreadLocal<>();
//	private List<PhaseDetails> phaseDetailsList=new ArrayList<>();
	private static HashMap<String,List<PhaseDetails>> phaseDetailsLists=new HashMap<>();

	private List<CollectionPhaseSubIndexModel> collectionPhaseSubIndexModelList=new ArrayList<CollectionPhaseSubIndexModel>();
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/view"})
    public String viewCollateralItem(ModelMap model, 
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("procId")Long procId,
    		@PathVariable("phaseId")Long phaseId) {

		CollectionProcedure procedure = procService.getById(procId);

		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtorService.getById(debtorId));
		model.addAttribute("procId", procId);
		model.addAttribute("proc", procedure);

		CollectionPhase phase = phaseService.getById(phaseId);
		model.addAttribute("phase", phase);
		Set<CollectionEvent> collectionEvents=new HashSet<CollectionEvent>();
		for(CollectionEvent event1:phase.getCollectionEvents()){
		    CollectionEvent event=collectionEventService.getById(event1.getId());
		    collectionEvents.add(event);
        }
        model.addAttribute("events",collectionEvents);



        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonPhaseDetails = gson.toJson(getPhaseDetailsByPhaseId(phaseId));
        model.addAttribute("phaseDetails", jsonPhaseDetails);

		String jsonLoans = gson.toJson(getLoansByPhaseId(phaseId));
        model.addAttribute("jsonLoans", jsonLoans);

		String jsonFiles = gson.toJson(getSystemFilesByItemId(phaseId));
		model.addAttribute("files", jsonFiles);

        String createdByStr=null;
        String modifiedByStr=null;
        if(phase.getAuCreatedBy()!=null){
            if(phase.getAuCreatedBy().equals("admin")){
                createdByStr="Система";
            }
            else{
                User createdByUser=userService.findByUsername(phase.getAuCreatedBy());
                Staff createdByStaff=createdByUser.getStaff();
                createdByStr=createdByStaff.getName();
            }
        }
        if(phase.getAuLastModifiedBy()!=null){
            if(phase.getAuLastModifiedBy().equals("admin")){
               modifiedByStr="Система";
            }
            else{
                User lastModifiedByUser=userService.findByUsername(phase.getAuLastModifiedBy());
                Staff lastModifiedByStaff=lastModifiedByUser.getStaff();
                modifiedByStr=lastModifiedByStaff.getName();
            }
        }
        model.addAttribute("createdBy",createdByStr);
        model.addAttribute("modifiedBy",modifiedByStr);

        User user = userService.findByUsername(Utils.getPrincipal());
        Staff staff = staffService.findById(user.getStaff().getId());

        Long departmentId = staff.getDepartment().getId();

        if(procedure.getStatusDepartmentId()!=null) {
			if (departmentId == 9 && procedure.getStatusDepartmentId() == 4) {
				model.addAttribute("accept", 8);
			}
			else if (departmentId == 10 && procedure.getStatusDepartmentId() == 3) {
				model.addAttribute("accept", 7);
			}
			else if ((departmentId == 4 || departmentId == 5 || departmentId == 6 || departmentId == 16) && procedure.getStatusDepartmentId() == 5) {
				model.addAttribute("accept", 9);
			}
			else if (departmentId == 7 && procedure.getStatusDepartmentId() == 6) {
				model.addAttribute("accept", 10);
			}
			else {
				model.addAttribute("accept", 0);
			}
		}
		else
			{
				model.addAttribute("accept", 0);
			}

			if(phase.getDepartment_id()==departmentId || (phase.getPhaseType().getId()==13 && departmentId == 10 ) || (phase.getDepartment_id()==null))
			{
				if((departmentId==9 && procedure.getProcedureStatus().getId() == 8)
						||(departmentId==10 && procedure.getProcedureStatus().getId() == 7)
						||(departmentId==7 && procedure.getProcedureStatus().getId() == 10))
				{
					model.addAttribute("modifyPermission", 1);
				}
				else if((departmentId == 4 || departmentId == 5 || departmentId == 6 || departmentId == 16) && (procedure.getProcedureStatus().getId() == 9 ||procedure.getProcedureStatus().getId() == 1))
				{
					model.addAttribute("modifyPermission", 1);
				}
				else
				{
					if(departmentId==3 || user.getId()==1)
					{
						model.addAttribute("modifyPermission", 1);
					}
					else
					{
						model.addAttribute("modifyPermission", 0);
					}

				}





			}
			else
			{
				if(departmentId==3 || user.getId()==1)
				{
					model.addAttribute("modifyPermission", 1);
				}
				else
				{
					if((departmentId == 4 || departmentId == 5 || departmentId == 6 || departmentId == 16) && (procedure.getProcedureStatus().getId() == 9 ||procedure.getProcedureStatus().getId() == 1) && (phase.getId()==1))
					{
						model.addAttribute("modifyPermission", 1);
					}
					else
					{
						model.addAttribute("modifyPermission", 0);
					}

				}
			}




		return "/manage/debtor/collectionprocedure/collectionphase/view";

	}

	@RequestMapping(value = { "debtor/{debtorId}/procedure/{procId}/phase/{phaseId}/details"})
	public String viewPhaseInformation(ModelMap model,
									 @PathVariable("debtorId")Long debtorId,
									 @PathVariable("procId")Long procId,
									 @PathVariable("phaseId")Long phaseId) {

		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtorService.getById(debtorId));
		model.addAttribute("procId", procId);
		model.addAttribute("proc", procService.getById(procId));

		CollectionPhase phase = phaseService.getById(phaseId);
		model.addAttribute("phase", phase);
		model.addAttribute("phaseRecordStatus",phase.getRecord_status());

//		remainder of start and paid of phase
		Double remainder=ifNullMakeZero(phase.getStart_amount())-ifNullMakeZero(phase.getPaid());
		if(remainder<=0){
			model.addAttribute("remainder", 0.00);
		}
		else{
			model.addAttribute("remainder", remainder);
		}

		if(phase.getCollectionPhaseIndex()==null){
			phase.setSub_index_id(1L);
			phase.setCollectionPhaseIndex(collectionPhaseIndexService.getById(1L));
			phaseService.update(phase);
			model.addAttribute("indexId", 1);
		}
		else{
			model.addAttribute("indexId", phase.getCollectionPhaseIndex().getId());
		}

		model.addAttribute("today",new Date());
		model.addAttribute("phaseTypes",typeService.list() );
		model.addAttribute("phaseStatuses", phaseStatusService.list());

		return "/manage/debtor/collectionprocedure/collectionphase/collectionPhaseInformation";

	}

    @RequestMapping(value="/collectionPhase/{id}/getPhaseDetails", method=RequestMethod.POST)
    @ResponseBody
    public String getPhaseDetailsTable(@RequestParam(value = "selectedLoans") String selectedLoans,@PathVariable(value = "id") Long id){

        List<PhaseDetailsModel> result = new ArrayList<>();
        List<String> listOfLoans= Arrays.asList(selectedLoans.split("[^0-9.]"));
        for (String value:listOfLoans) {
        	if (value.length()!=0){
				LoanModel1 loan=getLoanIdAndRegNumber(Long.parseLong(value));
				if(loan!=null){
					PhaseDetailsModel dTemp = new PhaseDetailsModel();
					dTemp.setLoanId(loan.getId());
					dTemp.setLoanRegNumber(loan.getRegNumber());
					dTemp.setLoanStateId(loan.getStateId());
					try{
					    PhaseDetails phaseDetails=phaseDetailsService.findByPhaseIdAndLoanId(id,loan.getId());
                        dTemp.setStartPrincipal(phaseDetails.getStartPrincipal());
                        dTemp.setStartInterest(phaseDetails.getStartInterest());
                        dTemp.setStartPenalty(phaseDetails.getStartPenalty());
                        dTemp.setStartTotalAmount(phaseDetails.getStartTotalAmount());
                        dTemp.setId(phaseDetails.getId());
						/*for (PhaseDetails phaseDetails1:phase.getPhaseDetails()){
							if(phaseDetails1.getLoan_id()==Long.valueOf(value)){
								PhaseDetails phaseDetails=phaseDetailsService.getById(phaseDetails1.getId());
								dTemp.setStartPrincipal(phaseDetails.getStartPrincipal());
								dTemp.setStartInterest(phaseDetails.getStartInterest());
								dTemp.setStartPenalty(phaseDetails.getStartPenalty());
								dTemp.setStartTotalAmount(phaseDetails.getStartTotalAmount());
							}
						}*/
					}
					catch(Exception e){
						dTemp.setStartPrincipal(Double.valueOf(0));
						dTemp.setStartInterest(Double.valueOf(0));
						dTemp.setStartPenalty(Double.valueOf(0));
						dTemp.setStartTotalAmount(Double.valueOf(0));
                        dTemp.setId(0L);
					}
					result.add(dTemp);
				}
        	}
        }

        Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String jsonResult = gson2.toJson(result);
        return jsonResult;
    }

	@RequestMapping(value="/manage/debtor/{debtorId}/initializephase", method=RequestMethod.POST)
	@ResponseBody
	public String initializePhaseForm(@PathVariable("debtorId")Long debtorId, @RequestParam Map<String, String> selectedLoans, @RequestParam String initDater)
    {
		Date date1=null;
		try {
			date1=new SimpleDateFormat("dd.MM.yyyy").parse(initDater);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		Date initDate=date1;
		List<PhaseDetailsModel> result = new ArrayList<>();
		int count = 0;
		for (String value:selectedLoans.values()
			 ) {
			if(count == selectedLoans.size()-1) continue;
			LoanModel1 loan=getLoanIdAndRegNumber(Long.parseLong(value));
			if(loan!=null){
			PhaseDetailsModel dTemp = new PhaseDetailsModel();
//			Loan loan = loanRepository.getOne(Long.parseLong(value));
			dTemp.setLoanId(loan.getId());
			dTemp.setLoanRegNumber(loan.getRegNumber());
			dTemp.setLoanStateId(loan.getStateId());
			this.jobItemService.runManualCalculateProcedure(loan.getId(), initDate);
			LoanSummary summary = loanSummaryService.getByOnDateAndLoanId(initDate, loan.getId());
			if(summary.getOverduePrincipal()==null){
				dTemp.setStartPrincipal(0.0);
			}
			else{
				dTemp.setStartPrincipal(summary.getOverduePrincipal());
			}
			if(summary.getOverdueInterest()==null){
				dTemp.setStartInterest(0.0);
			}
			else{
				dTemp.setStartInterest(summary.getOverdueInterest());
			}
			if(summary.getOverduePenalty()==null){
				dTemp.setStartPenalty(0.0);
			}
			else{
				dTemp.setStartPenalty(summary.getOverduePenalty());
			}
			Double totalAmount=0.0;
			if(summary.getOverduePrincipal()!=null){
				totalAmount=totalAmount+summary.getOverduePrincipal();
			}
			if (summary.getOverduePenalty()!=null){
				totalAmount=totalAmount+summary.getOverduePenalty();
			}
				if (summary.getOverdueInterest()!=null){
					totalAmount=totalAmount+summary.getOverdueInterest();
				}
			dTemp.setStartTotalAmount(totalAmount);
			result.add(dTemp);}
			count++;
		}

		Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String jsonResult = gson2.toJson(result);
		return jsonResult;
	}
	@PostMapping(value="/manage/debtor/{debtorId}/initializeManualCalculator")
	@ResponseBody
	public void initializeManualCalculator(@PathVariable("debtorId")Long debtorId, @RequestParam Map<String, String> selectedLoans, @RequestParam String initDater)
	{


		Date date1=null;
		try {
			date1=new SimpleDateFormat("dd.MM.yyyy").parse(initDater);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date initDate=date1;
		int count = 0;
		for (String value:selectedLoans.values()
		) {
			if(count == selectedLoans.size()-1) continue;
//			Loan loan = loanRepository.findOne(Long.parseLong(value));
			this.jobItemService.runManualCalculateProcedure(Long.parseLong(value), initDate);
			count++;
		}
	}

	@PostMapping(value="/manage/debtor/{debtorId}/initializeDailyCalculator")
	@ResponseBody
	public void initializeDailyCalculator(@PathVariable("debtorId")Long debtorId, @RequestParam Map<String, String> selectedLoans, @RequestParam String initDater)
	{
		Date date1=null;
		try {
			date1=new SimpleDateFormat("dd.MM.yyyy").parse(initDater);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date initDate=date1;
		int count = 0;
		for (String value:selectedLoans.values()
		) {
			if(count == selectedLoans.size()-1) continue;
			Loan loan = loanService.getById(Long.parseLong(value));
			if(loan.getParent()!=null){

				this.jobItemService.runDailyCalculateProcedureForOneLoan(loan.getParent().getId(), initDate);
			}
			else{
				this.jobItemService.runDailyCalculateProcedureForOneLoan(loan.getId(), initDate);
			}
			count++;
		}
	}

    @RequestMapping(value="/collectionPhase/savePhaseDetails", method=RequestMethod.POST)
    @ResponseBody
    public void savePhaseDetails( @RequestBody PhaseDetailsModelList phaseDetailsModels){

//	    this.phaseDetailsList.clear();
//		threadLocal.remove();
		List<PhaseDetails> phaseDetailsList=new ArrayList<>();
        List<PhaseDetailsModel> list = phaseDetailsModels.getPhaseDetailsModels();
        for (PhaseDetailsModel model: list
        ) {
            PhaseDetails details = new PhaseDetails();
            details.setId(model.getId());
            details.setLoan_id(model.getLoanId());
            details.setStartPrincipal(ifNullMakeZero(model.getStartPrincipal()));
            details.setStartInterest(ifNullMakeZero(model.getStartInterest()));
            details.setStartPenalty(ifNullMakeZero(model.getStartPenalty()));
            details.setStartTotalAmount(ifNullMakeZero(model.getStartPrincipal())+ifNullMakeZero(model.getStartInterest())+ifNullMakeZero(model.getStartPenalty()));
            phaseDetailsList.add(details);

        }
        String key= Utils.getPrincipal();
        if(phaseDetailsLists.containsKey(key)){
			phaseDetailsLists.replace(key,phaseDetailsList);
		}
		else{
			phaseDetailsLists.put(key,phaseDetailsList);
		}

    }

	@RequestMapping(value="/manage/debtor/{debtorId}/initializephasesave", method=RequestMethod.POST)
	@ResponseBody
	public String initializePhaseSave(@PathVariable("debtorId")Long debtorId, @RequestBody PhaseDetailsModelList phaseDetailsModels)
	{
	    String result="";
		if(phaseDetailsModels.getPhaseDetailsModels().size() > 0){

		    Date startDate = null;

            CollectionPhase phase = new CollectionPhase();

		    List<PhaseDetailsModel> list = phaseDetailsModels.getPhaseDetailsModels();
            Set<Loan> loans = new HashSet<>();

            for (PhaseDetailsModel model: list
                 ) {
                Loan loan = loanRepository.findOne(model.getLoanId());
                loans.add(loan);
				try {
					startDate=new SimpleDateFormat("dd.MM.yyyy").parse(this.initeDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
//                startDate=model.getInitDate();
            }

            Set<Loan> phaseLoans=new HashSet<>();
			for (Loan l:loans) {
				Loan l1=loanService.getById(l.getId());
				phaseLoans.add(l1);
			}
            phase.setLoans(phaseLoans);
            phase.setPhaseStatus(statusService.getById(1L));
            phase.setPhaseType(typeService.getById(1L));
            phase.setStartDate(startDate);

            CollectionProcedure procedure = new CollectionProcedure();
            procedure.setStartDate(startDate);
            procedure.setProcedureStatus(procedureStatusService.getById(9L));
            procedure.setProcedureType(procedureTypeService.getById(1L));
            procService.add(procedure);
            result=String.valueOf(procedure.getId());
            phase.setCollectionProcedure(procedure);

			User user = userService.findByUsername(Utils.getPrincipal());
			Staff staff=staffService.findById(user.getStaff().getId());
			phase.setDepartment_id(staff.getDepartment().getId());

            phaseService.add(phase);
            result=result+"-"+String.valueOf(phase.getId());

            for (PhaseDetailsModel model: list
                    ) {
                PhaseDetails details = new PhaseDetails();
                details.setLoan_id(model.getLoanId());
                details.setStartPrincipal(model.getStartPrincipal());
                details.setStartInterest(model.getStartInterest());
                details.setStartPenalty(model.getStartPenalty());
                details.setStartTotalAmount(model.getStartTotalAmount());
                details.setCollectionPhase(phase);
                phaseDetailsService.add(details);
            }

			String updateStart="(select sum(startTotalAmount) from phaseDetails where collectionPhaseId="+phase.getId()+")";
			Query query=entityManager.createNativeQuery(updateStart);
			Double sumOfStart= (Double) query.getSingleResult();
			phase.setStart_amount(sumOfStart);
			phaseService.update(phase);
            procedure.setLastPhase(phase.getId());
            //procedure.setLastStatusId(phase.getLastStatusId());
            procService.update(procedure);

        }

		return result;
	}

	@RequestMapping(value = "/subIndexByIndex/list", method = RequestMethod.GET)
	public @ResponseBody
	List<CollectionPhaseSubIndexModel> findSubIndexByIndex(@RequestParam(value = "indexId", required = true) Long indexId) {

		CollectionPhaseIndex collectionPhaseIndex= this.collectionPhaseIndexService.getById(indexId);
//		Set<CollectionPhaseSubIndex> collectionPhaseSubIndices= ;
		List<CollectionPhaseSubIndexModel> lists=new ArrayList<CollectionPhaseSubIndexModel>();
		for(CollectionPhaseSubIndex collectionPhaseSubIndex:collectionPhaseIndex.getCollectionPhaseSubIndices()){
			CollectionPhaseSubIndexModel model=new CollectionPhaseSubIndexModel();
			model.setId(collectionPhaseSubIndex.getId());
			model.setIndexId(collectionPhaseIndex.getId());
			model.setName(collectionPhaseSubIndex.getName());
			lists.add(model);
		}
//		this.collectionPhaseSubIndexModelList=lists;
		return lists;
	}

	private String initeDate=null;
	@PostMapping("/initializephasesave/initdate")
    @ResponseBody
	protected void setInitDate(@RequestParam(value = "initdate") String initdate){
		this.initeDate=initdate;
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/save", method=RequestMethod.GET)
	public String formCollateralItem(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("procId")Long procId,
			@PathVariable("phaseId")Long phaseId)
	{
		Debtor debtor = debtorService.getById(debtorId);

		CollectionPhaseIndex collectionPhaseIndexModel= (CollectionPhaseIndex) this.collectionPhaseIndexService.getById(Long.valueOf(1));

		Set<CollectionPhaseSubIndex> collectionPhaseSubIndexModels= collectionPhaseIndexModel.getCollectionPhaseSubIndices();

		model.addAttribute("debtorId", debtorId);
		model.addAttribute("subIndexId", Long.valueOf(1));
		model.addAttribute("debtor", debtor);
		model.addAttribute("phaseId", phaseId);
		model.addAttribute("procId", procId);
		model.addAttribute("proc", procService.getById(procId));

		if(phaseId == 0)
		{

			CollectionProcedure procedure = procService.getById(procId);

			if(procService.getById(procId).getLastPhase()==0)
			{


				Date lastDate = null;
				for (CollectionPhase phase: procedure.getCollectionPhases())
				{
					if(lastDate==null) {
						lastDate = phase.getStartDate();
						procedure.setLastPhase(phase.getId());
					}
					else
					{
						if(!lastDate.before(phase.getStartDate()))
						{
							lastDate = phase.getStartDate();
							procedure.setLastPhase(phase.getId());
						}
					}

				}

			}


			CollectionPhase collectionPhase=new CollectionPhase();

			User user = userService.findByUsername(Utils.getPrincipal());
			Staff staff=staffService.findById(user.getStaff().getId());
			try {
				collectionPhase.setDepartment_id(staff.getDepartment().getId());
			}
			catch(Exception e){
			}

			collectionPhase.setStartDate(new Date());
			collectionPhase.setLoans(phaseService.getById(procedure.getLastPhase()).getLoans());
			collectionPhase.setCollectionPhaseIndex(collectionPhaseIndexModel);
			model.addAttribute("phase", collectionPhase);
			List<Long> loanIds=new ArrayList<>();
			List<Loan> loans=new ArrayList<>();
            for (Loan l:phaseService.getById(procedure.getLastPhase()).getLoans()) {
                loanIds.add(l.getId());
            }
			for (Loan l:debtor.getLoans()) {
				loans.add(l);
			}
			model.addAttribute("tLoans", loans);
			model.addAttribute("loanIds", loanIds);
		}

		if(phaseId > 0)
		{
			List<Loan> loans=new ArrayList<>();
			for (Loan l:debtor.getLoans()) {
				loans.add(l);
			}
			CollectionPhase phase = phaseService.getById(phaseId);
			model.addAttribute("phase", phase);
			model.addAttribute("tLoans", loans);

			List<Long> loanIds=new ArrayList<>();

			for (Loan l:phase.getLoans()) {
				loanIds.add(l.getId());
			}
			model.addAttribute("loanIds", loanIds);
		}

		model.addAttribute("statuses", statusService.list());
		model.addAttribute("types", typeService.list());

		return "/manage/debtor/collectionprocedure/collectionphase/save";
	}

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/save"}, method=RequestMethod.POST)
    public String saveCollateralItem(CollectionPhase phase,
    		@PathVariable("debtorId")Long debtorId,
    		@PathVariable("procId")Long procId,
    		ModelMap model,String[] loanses)
    {
    	if(phase.getStartDate()!=null) {
    		if(phase.getPaymentFromDate()==null){
    			phase.setPaymentFromDate(phase.getStartDate());
			}
			CollectionProcedure procedure = procService.getById(procId);
			phase.setCollectionProcedure(procedure);

			Set<Loan> loanSet = new HashSet<>();
			if (loanses != null)
				for (String id : loanses) {
					loanSet.add(loanService.getById(Long.valueOf(id)));
				}
			if (phase.getId() == 0 && isPhaseWithDateValid(procId,phase.getStartDate())) {


				phase.setLoans(loanSet);
				phase.setPhaseStatus(phaseStatusService.getById(Long.valueOf(1)));
				phaseService.add(phase);
				if (phaseDetailsLists.get(Utils.getPrincipal()) == null) {
					Set<Loan> loans = phase.getLoans();
					for (Loan loan : loans) {
						PhaseDetails details = new PhaseDetails();
						details.setLoan_id(loan.getId());
						PhaseDetails latestDetails = phaseDetailsService.getById(getLatestDetailsByDate(loan.getId()));
						if (latestDetails != null) {
							details.setStartPrincipal(latestDetails.getStartPrincipal());
							details.setStartInterest(latestDetails.getStartInterest());
							details.setStartPenalty(latestDetails.getStartPenalty());
							details.setStartTotalAmount(latestDetails.getStartTotalAmount());
						} else {
							details.setStartPrincipal(0.0);
							details.setStartInterest(0.0);
							details.setStartPenalty(0.0);
							details.setStartTotalAmount(0.0);
						}

						details.setCollectionPhase(phase);
						phaseDetailsService.add(details);
					}
				} else {
					for (PhaseDetails phaseDetail : phaseDetailsLists.get(Utils.getPrincipal())) {
						phaseDetail.setCollectionPhase(phase);
						phaseDetailsService.add(phaseDetail);
					}
				}
				String updateStart = "(select sum(startTotalAmount) from phaseDetails where collectionPhaseId=" + phase.getId() + ")";
				Query query = entityManager.createNativeQuery(updateStart);
				Double sumOfStart = (Double) query.getSingleResult();
				phase.setStart_amount(sumOfStart);
				phaseService.update(phase);
				updatePaidOfPhase(phase);
				phaseDetailsLists.remove(Utils.getPrincipal());
			}
			else if(phase.getId() > 0){
				CollectionPhase oldPhase = phaseService.getById(phase.getId());

				phase.setPhaseStatus(oldPhase.getPhaseStatus());
				phase.setLoans(loanSet);

				if (phaseDetailsLists.get(Utils.getPrincipal()) == null) {
				    phase.setStart_amount(oldPhase.getStart_amount());

				}
				else {
				    List<Long> phaseDetailsIds=new ArrayList<>();
					for (PhaseDetails phaseDetail : phaseDetailsLists.get(Utils.getPrincipal())) {
					    if(phaseDetail.getId()==0){
                            phaseDetail.setStartInterest(phaseDetail.getStartInterest());
                            phaseDetail.setStartPenalty(phaseDetail.getStartPenalty());
                            phaseDetail.setStartPrincipal(phaseDetail.getStartPrincipal());
                            phaseDetail.setStartTotalAmount(phaseDetail.getStartTotalAmount());
                            phaseDetail.setCollectionPhase(phase);
                            phaseDetailsService.add(phaseDetail);
                            phaseDetailsIds.add(phaseDetail.getId());
                        }
                        else if(phaseDetail.getId()>0){
                            PhaseDetails oldPhaseDetail=phaseDetailsService.getById(phaseDetail.getId());
                            oldPhaseDetail.setStartInterest(phaseDetail.getStartInterest());
                            oldPhaseDetail.setStartPenalty(phaseDetail.getStartPenalty());
                            oldPhaseDetail.setStartPrincipal(phaseDetail.getStartPrincipal());
                            oldPhaseDetail.setStartTotalAmount(phaseDetail.getStartTotalAmount());
                            phaseDetailsService.update(oldPhaseDetail);
                            phaseDetailsIds.add(oldPhaseDetail.getId());
                        }
					}
					for(PhaseDetails details:phase.getPhaseDetails()){
					    if(!phaseDetailsIds.contains(details.getId())){
					        phaseDetailsService.remove(details);
							Session session;
							try{
								session=sessionFactory.getCurrentSession();
							}
							catch (Exception e){
								session=sessionFactory.openSession();
							}
							String deleteQuery="DELETE FROM loanCollectionPhase WHERE collectionPhaseId="+phase.getId()+" and loanId="+details.getLoan_id();
							session.createSQLQuery(deleteQuery).executeUpdate();
                        }
                    }
					String updateStart = "(select sum(startTotalAmount) from phaseDetails where collectionPhaseId=" + phase.getId() + ")";
					Query query = entityManager.createNativeQuery(updateStart);
					Double sumOfStart = (Double) query.getSingleResult();
					phase.setStart_amount(sumOfStart);
				}

				updatePaidOfPhase(phase);
				phaseService.update(phase);
				phaseDetailsLists.remove(Utils.getPrincipal());
			}
		}

		
		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
    }

    @RequestMapping(value = "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/setPaymentFromDate",method = RequestMethod.POST)
	public String setPaymentfromDate(@PathVariable("debtorId") Long debtorId,
									 @PathVariable("procId") Long procId,
									 @PathVariable("phaseId") Long phaseId,String date) throws ParseException {
		CollectionPhase collectionPhase = collectionPhaseService.getById(phaseId);
		SimpleDateFormat sd=new SimpleDateFormat("dd.MM.yyyy");
		Date paymentFromDate=sd.parse(date);
		collectionPhase.setPaymentFromDate(paymentFromDate);
		collectionPhaseService.update(collectionPhase);
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
		runUpdateOfPhases(phaseId,session);
		session.getTransaction().commit();

		return "redirect:/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/view";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/groupAndIndex/change", method=RequestMethod.GET)
	public String  changeGroupAndIndex(ModelMap model,@PathVariable("debtorId") Long debtorId,@PathVariable("procId") Long procId,@PathVariable("phaseId") Long phaseId){

		CollectionPhase collectionPhase=collectionPhaseService.getById(phaseId);
		model.addAttribute("phase",collectionPhase);
		model.addAttribute("debtorId",debtorId);
		model.addAttribute("procId",procId);
		model.addAttribute("groupList",collectionPhaseGroupService.list());
		model.addAttribute("indexList",collectionPhaseIndexService.list());
		CollectionPhaseIndex collectionPhaseIndex=collectionPhaseIndexService.getById(Long.valueOf(1));
		model.addAttribute("subIndex",collectionPhaseIndex.getCollectionPhaseSubIndices());

		return "/manage/debtor/collectionprocedure/collectionphase/changeGroupIndex";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/groupAndIndex/change", method=RequestMethod.POST)
	public String groupIndexChange(@PathVariable("phaseId") Long phaseId,@PathVariable("debtorId") Long debtorId,@PathVariable("procId") Long procId, CollectionPhase collectionPhase){

		CollectionPhase collectionPhase1=collectionPhaseService.getById(phaseId);
		collectionPhase1.setCollectionPhaseGroup(collectionPhaseGroupService.getById(collectionPhase.getCollectionPhaseGroup().getId()));
		CollectionPhaseIndex collectionPhaseIndex=collectionPhaseIndexService.getById(collectionPhase.getCollectionPhaseIndex().getId());
		collectionPhase1.setCollectionPhaseIndex(collectionPhaseIndex);

		Set<CollectionPhaseSubIndex> lost= new HashSet<CollectionPhaseSubIndex>();
		try {
			lost.add(collectionPhaseSubIndexService.getById(Long.valueOf(collectionPhase.getSub_index_id())));
			collectionPhase1.setSub_index_id(collectionPhase.getSub_index_id());
		}
		catch (Exception e){
			CollectionPhaseSubIndex collectionPhaseSubIndex=new CollectionPhaseSubIndex();
			for(CollectionPhaseSubIndex collectionPhaseSubIndex1:collectionPhaseIndex.getCollectionPhaseSubIndices()){
				collectionPhaseSubIndex=collectionPhaseSubIndexService.getById(collectionPhaseSubIndex1.getId());
				collectionPhase1.setSub_index_id(collectionPhaseSubIndex.getId());
				break;
			}
		}
		collectionPhaseIndexService.getById(collectionPhase.getCollectionPhaseIndex().getId()).setCollectionPhaseSubIndices(lost);
		collectionPhaseService.update(collectionPhase1);

		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/department/change", method=RequestMethod.GET)
	public String  changeDepartment(ModelMap model,@PathVariable("debtorId") Long debtorId,@PathVariable("procId") Long procId,@PathVariable("phaseId") Long phaseId){

		CollectionPhase collectionPhase=collectionPhaseService.getById(phaseId);
		model.addAttribute("phase",collectionPhase);
		model.addAttribute("debtorId",debtorId);
		model.addAttribute("procId",procId);

		List<ReferenceView> departments=referenceViewService.findByParameter("department");
		model.addAttribute("departments",departments);

		return "/manage/debtor/collectionprocedure/collectionphase/changeDepartment";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/department/change", method=RequestMethod.POST)
	public String departmentChange(@PathVariable("phaseId") Long phaseId,@PathVariable("debtorId") Long debtorId,@PathVariable("procId") Long procId, CollectionPhase collectionPhase){

		CollectionPhase collectionPhase1=collectionPhaseService.getById(phaseId);
		collectionPhase1.setDepartment_id(collectionPhase.getDepartment_id());
		collectionPhaseService.update(collectionPhase1);

		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
	}


	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{collectionPhaseId}/changeStatus"}, method=RequestMethod.GET)
	public String changeStatus(@PathVariable("debtorId")Long debtorId,
							   @PathVariable("procId")Long procId,@PathVariable("collectionPhaseId") Long collectionPhaseId,ModelMap model){

		CollectionPhase collectionPhase=phaseService.getById(collectionPhaseId);
		Set<PhaseDetails> phaseDetails=collectionPhase.getPhaseDetails();



		List<PhaseDetails> list=new ArrayList<>();
		for(PhaseDetails p1:phaseDetails){
			PhaseDetails p=phaseDetailsService.getById(p1.getId());
			list.add(p);
		}

		Debtor debtor=debtorService.getById(debtorId);

		model.addAttribute("debtorId",debtorId);
		model.addAttribute("procId",procId);
		model.addAttribute("collectionPhaseId",collectionPhaseId);
		model.addAttribute("debtor",debtor);
		model.addAttribute("statuses",statusService.list());
		model.addAttribute("proStatuses",procedureStatusService.list());
		model.addAttribute("collectionPhase",collectionPhase);

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonPhaseDetails= gson.toJson(getPhaseDetailsByCollectionPhaseId(collectionPhaseId));
		model.addAttribute("phaseDetails",jsonPhaseDetails);

		return "/manage/debtor/collectionprocedure/collectionphase/changeStatus";
	}

	@PostMapping("/collectionphase/{collectionPhaseId}/savePhaseDetailsChange")
	@ResponseBody
	public void savePhaseDetailsChanges(@PathVariable("collectionPhaseId")Long collectionPhaseId,@RequestBody CollectionPhaseDetailsModel1List list){
		if(list.getCollectionPhaseDetailsModel1().size() > 0){

			CollectionPhase phase = phaseService.getById(collectionPhaseId);

//			List<CollectionPhaseDetailsModel1> list = phaseDetailsModels.getCollectionPhaseDetailsModel1();
			Set<PhaseDetails> phaseDetails= new HashSet<>();

			for (CollectionPhaseDetailsModel1 model: list.getCollectionPhaseDetailsModel1())
			{
				PhaseDetails details = phaseDetailsService.getById(model.getId());
				details.setClosePrincipal(ifNullMakeZero(model.getClosePrincipal()));
				details.setCloseInterest(ifNullMakeZero(model.getCloseInterest()));
				details.setClosePenalty(ifNullMakeZero(model.getClosePenalty()));
				details.setCloseTotalAmount(ifNullMakeZero(model.getClosePrincipal())+ifNullMakeZero(model.getCloseInterest())+ifNullMakeZero(model.getClosePenalty()));
				phaseDetailsService.update(details);
				phaseDetails.add(details);

			}
			String updateStart="(select sum(closeTotalAmount) from phaseDetails where collectionPhaseId="+phase.getId()+")";
			Query query=entityManager.createNativeQuery(updateStart);
			Double sumOfClose= (Double) query.getSingleResult();
			phase.setPhaseDetails(phaseDetails);
			phase.setClose_amount(sumOfClose);
			phaseService.update(phase);

		}
	}

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{collectionPhaseId}/changeStatus"}, method=RequestMethod.POST)
	public String saveChangedStatus(@PathVariable("debtorId")Long debtorId,
									@PathVariable("procId")Long procId,@PathVariable("collectionPhaseId")Long collectionPhaseId, CollectionPhase collectionPhase){
		CollectionPhase phase=phaseService.getById(collectionPhaseId);


		phase.setPhaseStatus(collectionPhase.getPhaseStatus());
		phase.setCloseDate(collectionPhase.getCloseDate());
		phase.setResultDocNumber(collectionPhase.getResultDocNumber());
		phaseService.update(phase);
		CollectionProcedure procedure=procService.getById(phase.getCollectionProcedure().getId());
		procedure.setCloseDate(collectionPhase.getCollectionProcedure().getCloseDate());
		ProcedureStatus newProcStatus=collectionPhase.getCollectionProcedure().getProcedureStatus();
		if(procedure.getProcedureStatus().getId() != newProcStatus.getId()){
			if(newProcStatus.getId() ==3 ){
				procedure.setStatusDepartmentId(3L);
				procedure.setProcedureStatus(newProcStatus);
			}
			else if(newProcStatus.getId() == 4){
				procedure.setStatusDepartmentId(4L);
				procedure.setProcedureStatus(newProcStatus);
			}
			else if(newProcStatus.getId() == 5){
				procedure.setStatusDepartmentId(5L);
				procedure.setProcedureStatus(newProcStatus);
			}
			else if(newProcStatus.getId() == 6){
				procedure.setStatusDepartmentId(6L);
				procedure.setProcedureStatus(newProcStatus);
			}
			else if(newProcStatus.getId() == 2)
				{
					procedure.setProcedureStatus(newProcStatus);
				}
		}
		procService.update(procedure);

		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
	}

	@GetMapping("/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/accept/{procStatusId}/change")
	public String acceptChangeOfProcedureStatus(@PathVariable("debtorId") Long debtorId,@PathVariable("procId") Long procId,@PathVariable("phaseId") Long phaseId,@PathVariable("procStatusId") Long procStatusId){

		ProcedureStatus procedureStatus = procedureStatusService.getById(procStatusId);
		CollectionProcedure procedure = procService.getById(procId);
		procedure.setProcedureStatus(procedureStatus);
		procedure.setStatusDepartmentId(0L);
		procService.update(procedure);
		changeDebtorGroupAndSubGroup(debtorId,procStatusId,procedure);

		return "redirect:/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/view";
	}

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/changeRecordStatus"}, method=RequestMethod.POST)
	public String changeRecordStatus(@PathVariable("debtorId") Long debtorId,@PathVariable("procId") Long procId,@PathVariable("phaseId") Long phaseId){

		CollectionPhase phase=phaseService.getById(phaseId);
		phase.setRecord_status(2);
		phaseService.update(phase);

		return "redirect: /manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
	}

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/delete"}, method=RequestMethod.POST)
    public String deleteCollateralItem(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("procId")Long procId)
    {
		if(id > 0)
			phaseService.remove(phaseService.getById(id));
		
		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
    }
	
	@RequestMapping(value = { "/manage/debtor/collectionprocedure/collectionphase/status/list" }, method = RequestMethod.GET)
	public String listStatuses(ModelMap model) {

		List<PhaseStatus> statuses = statusService.list();
		model.addAttribute("statuses", statuses);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collectionprocedure/collectionphase/status/list";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/status/{statusId}/save", method=RequestMethod.GET)
	public String formLoanState(ModelMap model, @PathVariable("statusId")Long statusId)
	{
		if(statusId == 0)
		{
			model.addAttribute("status", new PhaseStatus());
		}

		if(statusId > 0)
		{
			model.addAttribute("status", statusService.getById(statusId));
		}
		return "/manage/debtor/collectionprocedure/collectionphase/status/save";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/status/save", method=RequestMethod.POST)
    public String saveLoanState(PhaseStatus status, ModelMap model) {
		if(status.getId() == 0)
			statusService.add(status);
		else
			statusService.update(status);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/status/list";
    }

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/status/delete", method=RequestMethod.POST)
	public String deleteLoanState(long id) {
		if(id > 0)
			statusService.remove(statusService.getById(id));
		return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/status/list";
	}
	
	@RequestMapping(value = { "/manage/debtor/collectionprocedure/collectionphase/type/list" }, method = RequestMethod.GET)
	public String listTypes(ModelMap model) {

		List<PhaseType> types = typeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collectionprocedure/collectionphase/type/list";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/type/{typeId}/save", method=RequestMethod.GET)
	public String formType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new PhaseType());
		}

		if(typeId > 0)
		{
			model.addAttribute("type", typeService.getById(typeId));
		}
		return "/manage/debtor/collectionprocedure/collectionphase/type/save";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/type/save", method=RequestMethod.POST)
    public String saveType(PhaseType type, ModelMap model) {
		if(type.getId() == 0)
			typeService.add(type);
		else
			typeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/type/list";
    }

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/type/delete", method=RequestMethod.POST)
	public String deleteType(long id) {
		if(id > 0)
			typeService.remove(typeService.getById(id));
		return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/type/list";
	}

	//	add information form
	@RequestMapping("/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionPhase/{phaseId}/addInformation")
	public String getAddInformationForm(Model model, @PathVariable("debtorId") Long debtorId,@PathVariable("procId") Long procId,@PathVariable("phaseId") Long phaseId){


		String ids="";
		ids=ids+"debtorId:"+debtorId+",";
		ids=ids+"procedureId:"+procId;
		model.addAttribute("ids",ids);

		CollectionPhase phase=phaseService.getById(phaseId);
		model.addAttribute("object",phase);
		model.addAttribute("systemObjectTypeId",11);

		model.addAttribute("attachment",new Attachment());

		return "/manage/debtor/loan/payment/addInformationForm";

	}

    @GetMapping("/collectionPhase/{id}/partialedit")
    public String regFieldEditForm(@PathVariable Long id, Model model){

	    CollectionPhase collectionPhase = phaseService.getById(id);

        model.addAttribute("object",collectionPhase);

        return "manage/debtor/collectionprocedure/collectionphase/editDocNumberField";
    }

	@PostMapping("/collectionPhase/saveregfields")
	public String regFieldEditForm(CollectionPhase collectionPhase){

		CollectionPhase oldCollectionPhase = phaseService.getById(collectionPhase.getId());
		oldCollectionPhase.setDocNumber(collectionPhase.getDocNumber());
		oldCollectionPhase.setReg_date(collectionPhase.getReg_date());

		phaseService.update(oldCollectionPhase);

		return "redirect:/collectionPhaseViews/claim";
	}


	private long getLatestDetailsByDate(long loanId)
	{
		String baseQuery = "SELECT det.id\n" +
				"FROM phaseDetails det, collectionPhase phase, loanCollectionPhase lph\n" +
				"WHERE det.loan_id = lph.loanId\n" +
				"AND phase.id = lph.collectionPhaseId\n" +
				"AND det.collectionPhaseId = lph.collectionPhaseId\n" +
				"AND det.loan_id = " +
				loanId + " " +
				"ORDER BY phase.startDate DESC LIMIT 1";

		long result = -1;

		try {
			Long id = ((Number) entityManager.createNativeQuery(baseQuery)
					.getSingleResult()).longValue();
			result = id;
		}
		catch (NoResultException ex){
			}

		return result;
	}

    private List<CollectionPhaseDetailsModel> getPhaseDetailsByPhaseId(long phaseId)
    {
        List<CollectionPhaseDetailsModel> result = new ArrayList<>();
        CollectionPhase phase = phaseService.getById(phaseId);
        for(PhaseDetails temp: phase.getPhaseDetails())
        {
            CollectionPhaseDetailsModel model = new CollectionPhaseDetailsModel();
            model.setId(temp.getId());
            model.setStartTotalAmount(ifNullMakeZero(temp.getStartTotalAmount()));
            model.setStartPrincipal(ifNullMakeZero(temp.getStartPrincipal()));
            model.setStartInterest(ifNullMakeZero(temp.getStartInterest()));
            model.setStartPenalty(ifNullMakeZero(temp.getStartPenalty()));
            model.setStartFee(ifNullMakeZero(temp.getStartFee()));
            model.setCloseTotalAmount(ifNullMakeZero(temp.getCloseTotalAmount()));
            model.setClosePrincipal(ifNullMakeZero(temp.getClosePrincipal()));
            model.setCloseInterest(ifNullMakeZero(temp.getCloseInterest()));
            model.setClosePenalty(ifNullMakeZero(temp.getClosePenalty()));
            model.setCloseFee(ifNullMakeZero(temp.getCloseFee()));
            model.setPaidTotalAmount(ifNullMakeZero(temp.getPaidTotalAmount()));
            model.setPaidPrincipal(ifNullMakeZero(temp.getPaidPrincipal()));
            model.setPaidInterest(ifNullMakeZero(temp.getPaidInterest()));
            model.setPaidPenalty(ifNullMakeZero(temp.getPaidPenalty()));
            model.setPaidFee(ifNullMakeZero(temp.getPaidFee()));
            model.setLoan_id(temp.getLoan_id());
            model.setRegNumber(loanService.getById(temp.getLoan_id()).getRegNumber());
            result.add(model);
        }

        return result;
    }
    public Double ifNullMakeZero(Double num){
		if(num==null){
			return Double.valueOf(0);
		}
		else
			return num;
	}

    private List<CollectionPhaseDetailsModel1> getPhaseDetailsByCollectionPhaseId(long collectionPhaseId){

		String baseQuery = "select p.id as id, IFNULL(p.closeInterest,p.startInterest) as closeInterest,IFNULL(p.closePenalty,p.startPenalty) as closePenalty,\n" +
				"IFNULL(p.closePrincipal,p.startPrincipal) as closePrincipal,IFNULL(p.closeTotalAmount,p.startTotalAmount) as closeTotalAmount, IFNULL(p.startInterest,0) as startInterest,IFNULL(p.startPenalty,0) as startPenalty,\n" +
                "IFNULL(p.startPrincipal,0) as startPrincipal,IFNULL(p.startTotalAmount,0) as startTotalAmount \n" +
				"from phaseDetails p where p.collectionPhaseId="+String.valueOf(collectionPhaseId);
		Query query = entityManager.createNativeQuery(baseQuery, CollectionPhaseDetailsModel1.class);
		List<CollectionPhaseDetailsModel1> result = query.getResultList();

		return result;
	}

	private LoanModel1 getLoanIdAndRegNumber(long loanId){
		String baseQuery = "select l.id as id,l.regNumber as regNumber, l.loanStateId as stateId \n"+
				"from loan l where l.loanStateId=2 and l.id=\""+String.valueOf(loanId)+"\"";
//		String baseQuery= "select loan.id as id, loan.regNumber as regNumber, loan.loanStateId as stateId from loan loan where loan.id="+ String.valueOf(loanId);
		Query query=entityManager.createNativeQuery(baseQuery,LoanModel1.class);
		try{

			LoanModel1 result= (LoanModel1) query.getSingleResult();
			return result;

		}
		catch (Exception e){
			return null;
		}
	}


	private List<LoanModel> getLoansByPhaseId(long phaseId)
	{
		String baseQuery = "SELECT loan.id, loan.loan_class_id, loan.regNumber, loan.regDate, loan.amount, loan.currencyId, currency.name as currencyName,\n" +
				"  loan.loanTypeId, type.name as loanTypeName, loan.loanStateId, state.name as loanStateName,\n" +
				"  loan.supervisorId, IFNULL(loan.parent_id, 0) as parentLoanId, 0.0 as remainder, loan.creditOrderId\n" +
				"FROM loan loan, orderTermCurrency currency,mfloan.loanCollectionPhase lCPH, loanType type, loanState state\n" +
				"WHERE loan.currencyId = currency.id\n" +
				"  AND loan.loanTypeId = type.id\n" +
				"  AND loan.loanStateId = state.id\n" +
				"  AND loan.id= lCPH.loanId\n" +
				"  AND lCPH.collectionPhaseId =" + phaseId+ "\n" +
				"  AND  ISNULL(loan.parent_id) \n" +
				"ORDER BY  loan.regDate DESC";

		Query query = entityManager.createNativeQuery(baseQuery, LoanModel.class);

		List<LoanModel> loans = query.getResultList();
		return loans;
	}


//	update collectionPhase instanly
	@PostMapping("/phase/instantUpdate")
    @ResponseBody
    public void dododo(int type,Long data,Long id){
	    CollectionPhase collectionPhase=collectionPhaseService.getById(id);
	    if(type==1){
            PhaseStatus phaseStatus=phaseStatusService.getById(data);
            collectionPhase.setPhaseStatus(phaseStatus);
        }
	    else if(type==2){
            PhaseType phaseType=typeService.getById(data);
            collectionPhase.setPhaseType(phaseType);
        }
//	    collectionPhaseService.update(collectionPhase);
        updatePaidOfPhase(collectionPhase);

    }

	@GetMapping("/phase/{id}/getType")
	public String getPhaseType(Model model,@PathVariable("id") Long id){

		CollectionPhase phase=collectionPhaseService.getById(id);
		PhaseType type=typeService.getById(phase.getPhaseType().getId());

		model.addAttribute("type",type);
		return "/manage/debtor/collectionprocedure/collectionphase/phaseType";
	}


    @GetMapping("/phase/{id}/getStatus")
    public String getPhaseStatus(Model model,@PathVariable("id") Long id){

	    CollectionPhase phase=collectionPhaseService.getById(id);
	    PhaseStatus status=phaseStatusService.getById(phase.getPhaseStatus().getId());

	    model.addAttribute("status",status);
	    model.addAttribute("statuses",phaseStatusService.list());
	    model.addAttribute("phase",phase);
	    return "/manage/debtor/collectionprocedure/collectionphase/phaseStatus";
    }

    @PostMapping("/phaseStatus/instantUpdate")
    @ResponseBody
    public String updateTheStatus(Long id,Long stat,String date,String res) throws ParseException {
        SimpleDateFormat sf=new SimpleDateFormat("dd.MM.yyyy");

        CollectionPhase collectionPhase=collectionPhaseService.getById(id);
        PhaseStatus status=phaseStatusService.getById(stat);
        collectionPhase.setPhaseStatus(status);
        try{
            collectionPhase.setCloseDate(sf.parse(date));
        }
        catch (Exception e){

        }
        collectionPhase.setResultDocNumber(res);

//        collectionPhaseService.update(collectionPhase);
        updatePaidOfPhase(collectionPhase);

        return "OK";
    }


    @GetMapping("/phase/{id}/getProcedureStatus")
    public String getPhaseProcStatus(Model model,@PathVariable("id") Long id){

        CollectionPhase phase=collectionPhaseService.getById(id);
        ProcedureStatus procedureStatus=procedureStatusService.getById(phase.getCollectionProcedure().getProcedureStatus().getId());
		CollectionProcedure procedure=procService.getById(phase.getCollectionProcedure().getId());

        model.addAttribute("closeDate",procedure.getCloseDate());
        model.addAttribute("procedureS",procedureStatus);
        model.addAttribute("statuses",procedureStatusService.list());
        return "manage/debtor/collectionprocedure/collectionphase/phaseProcedureStatus";
    }

    @PostMapping("/phaseProcStatus/instantUpdate")
    @ResponseBody
    public String updateTheProcedureStatus(Long id,Long stat,String date) throws ParseException {
		SimpleDateFormat sf=new SimpleDateFormat("dd.MM.yyyy");

        CollectionPhase collectionPhase=collectionPhaseService.getById(id);
        ProcedureStatus status=procedureStatusService.getById(stat);

        CollectionProcedure procedure=procService.getById(collectionPhase.getCollectionProcedure().getId());
        procedure.setProcedureStatus(status);
        try{
			procedure.setCloseDate(sf.parse(date));
		}
		catch (Exception e){

		}

        procService.update(procedure);
        updatePaidOfPhase(collectionPhase);

        return "OK";
    }


    @GetMapping("/phase/{id}/getGroup")
    public String getPhaseGroup(Model model,@PathVariable("id") Long id){

        CollectionPhase phase=collectionPhaseService.getById(id);
        CollectionPhaseGroup group=new CollectionPhaseGroup();
        if(phase.getCollectionPhaseGroup()==null){
            group=collectionPhaseGroupService.getById(1L);
        }
        else{
            group=collectionPhaseGroupService.getById(phase.getCollectionPhaseGroup().getId());
        }

        model.addAttribute("groups",collectionPhaseGroupService.list());
        model.addAttribute("group",group);
        return "manage/debtor/collectionprocedure/collectionphase/phaseGroup";
    }

    @PostMapping("/phaseGroup/instantUpdate")
    @ResponseBody
    public String updateTheGroup(Long id,Long data){

        CollectionPhase collectionPhase=collectionPhaseService.getById(id);
        CollectionPhaseGroup group=collectionPhaseGroupService.getById(data);

        collectionPhase.setCollectionPhaseGroup(group);
//        collectionPhaseService.update(collectionPhase);
        updatePaidOfPhase(collectionPhase);

        return "OK";
    }


    @GetMapping("/phase/{id}/getIndex")
    public String getPhaseIndex(Model model,@PathVariable("id") Long id){

        CollectionPhase phase=collectionPhaseService.getById(id);
        CollectionPhaseIndex index=new CollectionPhaseIndex();
        if(phase.getCollectionPhaseIndex()==null){
            index=collectionPhaseIndexService.getById(1L);
        }
        else{
            index=collectionPhaseIndexService.getById(phase.getCollectionPhaseIndex().getId());
        }

        model.addAttribute("index",index);
        model.addAttribute("indexes",collectionPhaseIndexService.list());
        return "manage/debtor/collectionprocedure/collectionphase/phaseIndex";
    }

    @PostMapping("/phaseIndex/instantUpdate")
    @ResponseBody
    public String updateTheIndex(Long id,Long data){

        CollectionPhase collectionPhase=collectionPhaseService.getById(id);
        CollectionPhaseIndex index=collectionPhaseIndexService.getById(data);
        if ((collectionPhase.getSub_index_id()!=null) && !index.getCollectionPhaseSubIndices().contains(collectionPhaseSubIndexService.getById(collectionPhase.getSub_index_id()))){
            for(CollectionPhaseSubIndex pSI:index.getCollectionPhaseSubIndices()){
                collectionPhase.setSub_index_id(pSI.getId());
                break;
            }
        }

        collectionPhase.setCollectionPhaseIndex(index);
//        collectionPhaseService.update(collectionPhase);

        updatePaidOfPhase(collectionPhase);

        return "OK";
    }


    @GetMapping("/phase/{id}/index/{indexId}/getIndex2")
    public String getPhaseIndex2(Model model,@PathVariable("id") Long id,@PathVariable("indexId") Long indexId){

        CollectionPhase phase=collectionPhaseService.getById(id);
        CollectionPhaseIndex index=collectionPhaseIndexService.getById(indexId);
        CollectionPhaseSubIndex index2=new CollectionPhaseSubIndex();
        if(phase.getSub_index_id()==null){
            for(CollectionPhaseSubIndex pSI:index.getCollectionPhaseSubIndices()){
                index2=collectionPhaseSubIndexService.getById(pSI.getId());
                break;
            }
        }
        else {
            index2=collectionPhaseSubIndexService.getById(phase.getSub_index_id());
        }

        model.addAttribute("index2",index2);
        model.addAttribute("index2s",index.getCollectionPhaseSubIndices());

        return "manage/debtor/collectionprocedure/collectionphase/phaseIndex2";
    }

    @PostMapping("/phaseIndex2/instantUpdate")
    @ResponseBody
    public String updateTheIndex2(Long id,Long data){

        CollectionPhase collectionPhase=collectionPhaseService.getById(id);
        CollectionPhaseIndex index=collectionPhaseIndexService.getById(collectionPhase.getCollectionPhaseIndex().getId());
        CollectionPhaseSubIndex index2=collectionPhaseSubIndexService.getById(data);

        Set<CollectionPhaseSubIndex> set=new HashSet<>();
        set.clear();
        set.add(index2);

        index.setCollectionPhaseSubIndices(set);
        collectionPhaseIndexService.update(index);

        collectionPhase.setSub_index_id(index2.getId());
//        collectionPhaseService.update(collectionPhase);
        updatePaidOfPhase(collectionPhase);
        return "OK";
    }


    @PostMapping("/phasePaymentFromDate/instantUpdate")
    @ResponseBody
    public String updateThePaymentFromDate(Long id,String data){
	    SimpleDateFormat sf=new SimpleDateFormat("dd.MM.yyyy");
	    SimpleDateFormat sf2=new SimpleDateFormat("yyyy-MM-dd");

	    Date closeDate=new Date();



	    String loanIds="";
        CollectionPhase collectionPhase=collectionPhaseService.getById(id);
        if(collectionPhase.getPaymentToDate()!=null){
        	closeDate=collectionPhase.getPaymentToDate();
		}
        String strCloseDate=sf2.format(closeDate);
        try {
            collectionPhase.setPaymentFromDate(sf.parse(data));
            for(Loan loan:collectionPhase.getLoans()){
                loanIds=loanIds+loan.getId()+",";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        String getTotalPaidQuery="select sum(p.totalAmount)\n" +
                "from payment p where loanId in ("+loanIds.substring(0,loanIds.length()-1)+") and p.paymentDate between '"+sf2.format(collectionPhase.getPaymentFromDate())+"' and '"+strCloseDate+"'";
        Query query=entityManager.createNativeQuery(getTotalPaidQuery);
        Double paidOfPhase= (Double) query.getSingleResult();

        collectionPhase.setPaid(paidOfPhase);
        collectionPhaseService.update(collectionPhase);

		String getSumOfPayments="select phd.id,sum(p.totalAmount) as amount,sum(p.principal) as principal,sum(p.interest) as interest,sum(p.penalty) as penalty,sum(p.fee) as fee\n" +
				"from phaseDetails phd,payment p where collectionPhaseId="+collectionPhase.getId()+" and p.loanId=phd.loan_id " +
				"and p.paymentDate between '"+sf2.format(collectionPhase.getPaymentFromDate())+"' and '"+strCloseDate+"' group by phd.id";
		Query query1=entityManager.createNativeQuery(getSumOfPayments,SimplePhaseDetailsModel.class);
		List<SimplePhaseDetailsModel> simplePhaseDetailsModelList=query1.getResultList();
		for(SimplePhaseDetailsModel model:simplePhaseDetailsModelList){
			PhaseDetails phaseDetails=phaseDetailsService.getById(model.getId());
			phaseDetails.setPaidTotalAmount(model.getAmount());
			phaseDetails.setPaidPrincipal(model.getPrincipal());
			phaseDetails.setPaidInterest(model.getInterest());
			phaseDetails.setPaidPenalty(model.getPenalty());
			phaseDetails.setPaidFee(model.getFee());
			phaseDetailsService.update(phaseDetails);
		}

//        Session session;
//        try
//        {
//            session = sessionFactory.getCurrentSession();
//        }
//        catch (HibernateException e)
//        {
//            session = sessionFactory.openSession();
//        }
//
//        session.getTransaction().begin();
//        runUpdateOfPhases(id,session);
//        session.getTransaction().commit();

        return "OK";
    }

	@PostMapping("/phasePaymentToDate/instantUpdate")
	@ResponseBody
	public String updateThePaymentToDate(Long id,String data){
		SimpleDateFormat sf=new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sf2=new SimpleDateFormat("yyyy-MM-dd");

		String loanIds="";
		CollectionPhase collectionPhase=collectionPhaseService.getById(id);
		try {
			collectionPhase.setPaymentToDate(sf.parse(data));
			for(Loan loan:collectionPhase.getLoans()){
				loanIds=loanIds+loan.getId()+",";
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return "OK";
		}
		Date fromDate=collectionPhase.getPaymentFromDate();
		if(collectionPhase.getPaymentFromDate()==null){
		    fromDate=collectionPhase.getStartDate();
        }
		String strCloseDate=sf2.format(collectionPhase.getPaymentToDate());
		String getTotalPaidQuery="select sum(p.totalAmount)\n" +
				"from payment p where loanId in ("+loanIds.substring(0,loanIds.length()-1)+") and p.paymentDate between '"+sf2.format(fromDate)+"' and '"+strCloseDate+"'";
		Query query=entityManager.createNativeQuery(getTotalPaidQuery);
		Double paidOfPhase= (Double) query.getSingleResult();

		collectionPhase.setPaid(paidOfPhase);
		collectionPhaseService.update(collectionPhase);


		return "OK";
	}


    @GetMapping("/owner/{id}/getInformation")
    public String getOwnerInformation(Model model,@PathVariable("id") Long id){

	    Owner owner=ownerService.getById(id);
	    if(owner.getOwnerType().equals(OwnerType.ORGANIZATION)){
            Organization organization = this.organizationService.findById(owner.getEntityId());

            String hasDebtor="true";
            try{
                Debtor debtor=debtorService.getByOwnerId(owner.getId());
                model.addAttribute("debtorId",debtor.getId());
            }
            catch (Exception e){
                hasDebtor="false";
                model.addAttribute("debtorId",0);
            }
            model.addAttribute("hasDebtor", hasDebtor);
            List<Position> organizationPositionList = new ArrayList<Position>();

            for (Department department:organization.getDepartment())
            {
                organizationPositionList.addAll(this.positionService.findByDepartment(department));
            }

            model.addAttribute("docs",getDocs(id));
            model.addAttribute("organization", organization);
            model.addAttribute("positionList", organizationPositionList);
            return "manage/debtor/collectionprocedure/collectionphase/organizationInformation";
        }
	    else{
            Person person = this.personService.findById(owner.getEntityId());
            String hasDebtor="true";
            try{
                Debtor debtor=debtorService.getByOwnerId(owner.getId());
                model.addAttribute("debtorId",debtor.getId());
            }
            catch (Exception e){
                hasDebtor="false";
                model.addAttribute("debtorId",0);
            }
            model.addAttribute("docs",getDocs(id));
            model.addAttribute("hasDebtor", hasDebtor);
            model.addAttribute("person", person);

            return "manage/debtor/collectionprocedure/collectionphase/personInformation";
        }
    }


    @GetMapping("/phase/{id}/getEvents")
    public String getPhaseEvents(Model model,@PathVariable("id") Long id){

        CollectionPhase phase=collectionPhaseService.getById(id);
        CollectionProcedure procedure=procService.getById(phase.getCollectionProcedure().getId());
        Set<CollectionEvent> events=phase.getCollectionEvents();
		CollectionPhaseView collectionPhaseView=collectionPhaseViewService.findById(procedure.getId());


		model.addAttribute("debtorId",collectionPhaseView.getV_debtor_id());
		model.addAttribute("procId",procedure.getId());
        model.addAttribute("id",phase.getId());
        model.addAttribute("events",events);

        return "/manage/debtor/collectionprocedure/collectionphase/collectionEvent";
    }

    @GetMapping("/phase/{phaseId}/event/{id}/save")
    public String savePhaseEventGet(Model model,@PathVariable("id") Long id,@PathVariable("phaseId") Long phaseId){

        if(id==0){
            CollectionPhase phase=collectionPhaseService.getById(phaseId);
            CollectionEvent event=new CollectionEvent();
            event.setCollectionPhase(phase);
            model.addAttribute("event",event);
        }
        else if(id>0){
            CollectionEvent event=collectionEventService.getById(id);
            model.addAttribute("event",event);
        }

        model.addAttribute("types",eventTypeService.list());
        model.addAttribute("statuses",eventStatusService.list());

        return "/manage/debtor/collectionprocedure/collectionphase/collectionEventForm";
    }

    @PostMapping("/event/save")
    @ResponseBody
    public String savePhaseEventPost(CollectionEvent event){

        if(event.getId()==0){
			event.setEventStatus(eventStatusService.getById(1L));
            collectionEventService.add(event);
        }
        else if(event.getId()>0){
            collectionEventService.update(event);
        }
        CollectionPhase phase=collectionPhaseService.getById(event.getCollectionPhase().getId());
        updatePaidOfPhase(phase);


        return "OK";
    }

	@PostMapping("/phaseDescription/save")
	@ResponseBody
	public String savePhaseDescription(Long id,String data){

		CollectionPhase phase=collectionPhaseService.getById(id);
		phase.setDescription(data);
//		collectionPhaseService.update(phase);
        updatePaidOfPhase(phase);
		return "OK";
	}


	@PostMapping("/delete/phase/phaseType1")
	@ResponseBody
	public String deleteThePhase(Long id){
		CollectionPhase phase=collectionPhaseService.getById(id);
		collectionPhaseService.remove(phase);
		return "OK";
	}


	@GetMapping("/phase/{id}/getEdit")
	public String getEditForm(@PathVariable("id") Long id,Model model){
		CollectionPhase phase=collectionPhaseService.getById(id);

		model.addAttribute("phase",phase);
		model.addAttribute("types",typeService.list());

        List<Long> loanIds=new ArrayList<>();
        List<Loan> loans=new ArrayList<>();
        int counter=0;
        Loan loan=null;
        for (Loan l:phase.getLoans()) {
            if(counter==0){
                loan=loanService.getById(l.getId());
            }
            loanIds.add(l.getId());
        }
        Debtor debtor=null;
        if(loan!=null){
            debtor=debtorService.getById(loan.getDebtor().getId());
        }
        if (debtor != null) {
            for (Loan l:debtor.getLoans()) {
                loans.add(l);
            }
        }

		model.addAttribute("tLoans", loans);
		model.addAttribute("loanIds", loanIds);

		return "/manage/debtor/collectionprocedure/collectionphase/editForm";
	}

	@PostMapping("/collectionphase/save")
	@ResponseBody
	public void postEditForm(CollectionPhase phase){
		if(phase.getStartDate()!=null){
			CollectionPhase oldPhase = phaseService.getById(phase.getId());
			oldPhase.setStartDate(phase.getStartDate());
			oldPhase.setPhaseType(phase.getPhaseType());
			oldPhase.setDocNumber(phase.getDocNumber());

			if (phaseDetailsLists.get(Utils.getPrincipal()) == null) {

				for(PhaseDetails details:phase.getPhaseDetails()){
					PhaseDetails phaseDetails=phaseDetailsService.getById(details.getId());
					phaseDetailsService.remove(phaseDetails);
				}
				phaseService.update(phase);
			}
			else {
				List<Long> phaseDetIds=new ArrayList<>();
				for (PhaseDetails phaseDetail : phaseDetailsLists.get(Utils.getPrincipal())) {
					phaseDetail.setCollectionPhase(oldPhase);
					if(phaseDetail.getId()>0){
						PhaseDetails oldPhaseDetails=phaseDetailsService.getById(phaseDetail.getId());
						oldPhaseDetails.setStartInterest(phaseDetail.getStartInterest());
						oldPhaseDetails.setStartPenalty(phaseDetail.getStartPenalty());
						oldPhaseDetails.setStartPrincipal(phaseDetail.getStartPrincipal());
						oldPhaseDetails.setStartTotalAmount(phaseDetail.getStartTotalAmount());
						phaseDetailsService.update(oldPhaseDetails);
						phaseDetIds.add(oldPhaseDetails.getId());
					}
					else if(phaseDetail.getId()==0){
						phaseDetail.setCollectionPhase(oldPhase);
						phaseDetailsService.add(phaseDetail);
						phaseDetIds.add(phaseDetail.getId());
						Session session;
						try{
							session=sessionFactory.getCurrentSession();
						}
						catch (Exception e){
							session=sessionFactory.openSession();
						}
						String insertLoanToCollectionPhaseQuery="insert into loanCollectionPhase VALUES ("+oldPhase.getId()+","+phaseDetail.getLoan_id()+")";
						session.createSQLQuery(insertLoanToCollectionPhaseQuery).executeUpdate();
					}
				}
				for(PhaseDetails details:oldPhase.getPhaseDetails()){
					if(!phaseDetIds.contains(details.getId())){
						phaseDetailsService.remove(details);
						Session session;
						try{
							session=sessionFactory.getCurrentSession();
						}
						catch (Exception e){
							session=sessionFactory.openSession();
						}
						String deleteQuery="DELETE FROM loanCollectionPhase WHERE collectionPhaseId="+oldPhase.getId()+" and loanId="+details.getLoan_id();
						session.createSQLQuery(deleteQuery).executeUpdate();
					}
				}

				String updateStart = "(select sum(startTotalAmount) from phaseDetails where collectionPhaseId=" + phase.getId() + ")";
				Query query = entityManager.createNativeQuery(updateStart);
				Double sumOfStart = (Double) query.getSingleResult();
				oldPhase.setStart_amount(sumOfStart);
			}
			phaseService.update(oldPhase);
			phaseDetailsLists.remove(Utils.getPrincipal());
            updatePaidOfPhase(oldPhase);
			}
//		return "OK";
	}

	@PostMapping("/phase/changeRecordStatus")
	@ResponseBody
	public String postChangeStatus(Long id){
		CollectionPhase phase=collectionPhaseService.getById(id);
		phase.setRecord_status(2);
//		collectionPhaseService.update(phase);
        updatePaidOfPhase(phase);
		return "OK";
	}

	// get payments of phase
	@GetMapping("/phase/{id}/getPayments")
	public String getPhasePayments(Model model,@PathVariable("id") Long id){

		CollectionPhase phase=collectionPhaseService.getById(id);

		Loan loan=loanService.getById(phase.getLoans().iterator().next().getId());

		model.addAttribute("id",phase.getId());
		model.addAttribute("debtorId",loan.getDebtor().getId());

		return "/manage/debtor/collectionprocedure/collectionphase/phasePayments";
	}

	// get loan summaries of phase
	@GetMapping("/phase/{id}/getComputation")
	public String getPhaseComputation(Model model,@PathVariable("id") Long id){

		CollectionPhase phase=collectionPhaseService.getById(id);
		String phaseLoanIds="";
		Loan loan=null;
		for (Loan l:phase.getLoans()){
            phaseLoanIds=phaseLoanIds+(l.getId())+"-";
        }

		model.addAttribute("id",phase.getId());
		model.addAttribute("phaseLoanIds",phaseLoanIds);
		model.addAttribute("fromLoan","true");
		model.addAttribute("today",new Date());

		return "/manage/debtor/collectionprocedure/collectionphase/phaseComputation";
	}

	@RequestMapping(value = "/manage/collectionphase/{phaseId}/summary/view",method = RequestMethod.GET)
	public String getSelectedLoanSummariesView(ModelMap model, @PathVariable("phaseId") Long phaseId,String date,String name) throws ParseException
	{


		CollectionPhase phase=phaseService.getById(phaseId);
		CalculationTool calculationTool=new CalculationTool();
		LoanSummary sumLoanSummary=new LoanSummary();
		LinkedHashSet<LoanView> loanViews = new LinkedHashSet<LoanView>(0);
		HashMap<LoanSummaryModel1,LoanSummaryModel1> summaries=new HashMap<>();
		Date newDate=new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).parse(date);
		LinkedHashMap<Long, LoanDetailedSummary> loanDetailedSummaryList = new LinkedHashMap<>();

		for (String id:name.split("-")){
			if(!id.equals("")) {
				String baseQuery="select * from loan_view where v_loan_id="+id;
				Query query=entityManager.createNativeQuery(baseQuery,LoanView.class);
				LoanView loanView= (LoanView) query.getSingleResult();
				loanViews.add(loanView);
			}
		}
		loanDetailedSummaryList.putAll(calculationTool.getAllLoanDetailedSummariesByLoanViewList(loanViews, newDate ));
		for (LoanView loanView:loanViews){
			Loan loan = loanService.getById(Long.valueOf(loanView.getV_loan_id()));

			Date srokDate = null;

			for (PaymentSchedule schedule: loan.getPaymentSchedules())
			{
				if(schedule.getPrincipalPayment()>0)
				{
					if(srokDate==null)
					{
						srokDate=schedule.getExpectedDate();
					}
					else
					{
						if(schedule.getExpectedDate()!=null)
							if(schedule.getExpectedDate().after(srokDate));
						{
							srokDate = schedule.getExpectedDate();
						}
					}

				}

			}

			if(srokDate==null) srokDate = loan.getRegDate();
			LoanSummaryModel1 loanSummary1=new LoanSummaryModel1();

			LoanSummary loanSummary=calculationTool.getLoanSummaryCaluculatedByLoanIdAndOnDate(loanView,loanView.getV_loan_id(),newDate,null);

			Double rate=currencyRateService.findByDateAndType(loanSummary.getOnDate(),loan.getCurrency()).getRate();


			if(loan.getCloseRate()!=null)
				if(loan.getCloseRate()>0) rate = loan.getCloseRate();

			loanSummary1.setLoanAmount(conditional(loanSummary.getLoanAmount()));
			loanSummary1.setTotalDisbursed(conditional(loanSummary.getTotalDisbursed()));
			loanSummary1.setTotalOutstanding(conditional(loanSummary.getTotalOutstanding()));
			loanSummary1.setOutstadingPrincipal(conditional(loanSummary.getOutstadingPrincipal()));
			loanSummary1.setOutstadingInterest(conditional(loanSummary.getOutstadingInterest()));
			loanSummary1.setOutstadingPenalty(conditional(loanSummary.getOutstadingPenalty()));
			loanSummary1.setTotalOverdue(conditional(loanSummary.getTotalOverdue()));
			loanSummary1.setOverduePrincipal(conditional(loanSummary.getOverduePrincipal()));
			loanSummary1.setOverdueInterest(conditional(loanSummary.getOverdueInterest()));
			loanSummary1.setOverduePenalty(conditional(loanSummary.getOverduePenalty()));
			loanSummary1.setOnDate(srokDate);
			loanSummary1.setTotalPaidKGS(conditional(loanSummary.getTotalPaidKGS()));
			loanSummary1.setTotalPaid(conditional(loanSummary.getTotalPaid()));

			String name1="";
			if(loan.getCurrency().getId()!=17){
				name1=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).format(loan.getRegDate())+"г. в тыс. "+loan.getCurrency().getName();
			}
			else{
				name1=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).format(loan.getRegDate())+"г. в тоннах "+loan.getCurrency().getName();
			}
			loanSummary1.setUuid(name1);
			loanSummary1.setVersion(loan.getId());
			loanSummary1.setId(loan.getDebtor().getId());



			LoanSummaryModel1 newLoanSummary=new LoanSummaryModel1();
			if(loan.getCurrency().getId()!=1){
				newLoanSummary.setVersion(Long.valueOf(1));
				newLoanSummary.setUuid("в тыс. сомах по курсу "+rate);
				newLoanSummary.setLoanAmount((loanSummary1.getLoanAmount()*rate));
				newLoanSummary.setTotalDisbursed((loanSummary1.getTotalDisbursed()*rate));
				newLoanSummary.setTotalOutstanding((loanSummary1.getTotalOutstanding()*rate));
				newLoanSummary.setOutstadingPrincipal((loanSummary1.getOutstadingPrincipal()*rate));
				newLoanSummary.setOutstadingInterest((loanSummary1.getOutstadingInterest()*rate));
				newLoanSummary.setOutstadingPenalty((loanSummary1.getOutstadingPenalty()*rate));
				newLoanSummary.setTotalOverdue((loanSummary1.getTotalOverdue()*rate));
				newLoanSummary.setOverduePrincipal((loanSummary1.getOverduePrincipal()*rate));
				newLoanSummary.setOverdueInterest((loanSummary1.getOverdueInterest()*rate));
				newLoanSummary.setOverduePenalty((loanSummary1.getOverduePenalty()*rate));
				newLoanSummary.setOnDate(loanSummary1.getOnDate());
				newLoanSummary.setTotalPaidKGS((loanSummary1.getTotalPaidKGS()));
				newLoanSummary.setTotalPaid((loanSummary1.getTotalPaid()));

				model.addAttribute("newSummary",newLoanSummary);

				summaries.put(loanSummary1,newLoanSummary);

				if(sumLoanSummary.getLoanAmount()!=null) {
					sumLoanSummary.setTotalPaidKGS(sumLoanSummary.getTotalPaidKGS() + newLoanSummary.getTotalPaidKGS());
					sumLoanSummary.setTotalPaid(sumLoanSummary.getTotalPaid() + newLoanSummary.getTotalPaid());
					sumLoanSummary.setOutstadingPenalty(sumLoanSummary.getOutstadingPenalty() + newLoanSummary.getOutstadingPenalty());
					sumLoanSummary.setOutstadingPrincipal(sumLoanSummary.getOutstadingPrincipal() + newLoanSummary.getOutstadingPrincipal());
					sumLoanSummary.setOutstadingInterest(sumLoanSummary.getOutstadingInterest() + newLoanSummary.getOutstadingInterest());
					sumLoanSummary.setTotalOverdue(sumLoanSummary.getTotalOverdue() + newLoanSummary.getTotalOverdue());
					sumLoanSummary.setOverduePrincipal(sumLoanSummary.getOverduePrincipal()+newLoanSummary.getOverduePrincipal());
					sumLoanSummary.setOverdueInterest(sumLoanSummary.getOverdueInterest()+newLoanSummary.getOverdueInterest());
					sumLoanSummary.setOverduePenalty(sumLoanSummary.getOverduePenalty()+newLoanSummary.getOverduePenalty());
					sumLoanSummary.setTotalOutstanding(sumLoanSummary.getTotalOutstanding() + newLoanSummary.getTotalOutstanding());
					sumLoanSummary.setTotalDisbursed(sumLoanSummary.getTotalDisbursed() + newLoanSummary.getTotalDisbursed());
					sumLoanSummary.setLoanAmount(sumLoanSummary.getLoanAmount() + newLoanSummary.getLoanAmount());
				}
				else{
					sumLoanSummary.setTotalPaidKGS(newLoanSummary.getTotalPaidKGS());
					sumLoanSummary.setTotalPaid(newLoanSummary.getTotalPaid());
					sumLoanSummary.setOutstadingPenalty(newLoanSummary.getOutstadingPenalty());
					sumLoanSummary.setOutstadingPrincipal(newLoanSummary.getOutstadingPrincipal());
					sumLoanSummary.setOutstadingInterest(newLoanSummary.getOutstadingInterest());
					sumLoanSummary.setOutstadingFee(newLoanSummary.getOutstadingFee());
					sumLoanSummary.setTotalOverdue(newLoanSummary.getTotalOverdue());
					sumLoanSummary.setOverduePrincipal(newLoanSummary.getOverduePrincipal());
					sumLoanSummary.setOverdueInterest(newLoanSummary.getOverdueInterest());
					sumLoanSummary.setOverduePenalty(newLoanSummary.getOverduePenalty());
					sumLoanSummary.setTotalOutstanding(newLoanSummary.getTotalOutstanding());
					sumLoanSummary.setTotalDisbursed(newLoanSummary.getTotalDisbursed());
					sumLoanSummary.setLoanAmount(newLoanSummary.getLoanAmount());
				}
			}
			else{
//					loanSummary.setVersion(Long.valueOf(0));
//					loanSummary.setUuid(loanSummary.getUuid()+" в тыс. сомах по курсу "+rate);
				summaries.put(loanSummary1,loanSummary1);
				if(sumLoanSummary.getLoanAmount()!=null) {
					sumLoanSummary.setTotalPaidKGS(sumLoanSummary.getTotalPaidKGS() + loanSummary1.getTotalPaidKGS());
					sumLoanSummary.setTotalPaid(sumLoanSummary.getTotalPaid()+loanSummary1.getTotalPaid());
					sumLoanSummary.setOutstadingPenalty(sumLoanSummary.getOutstadingPenalty() + loanSummary1.getOutstadingPenalty());
					sumLoanSummary.setOutstadingPrincipal(sumLoanSummary.getOutstadingPrincipal() + loanSummary1.getOutstadingPrincipal());
					sumLoanSummary.setOutstadingInterest(sumLoanSummary.getOutstadingInterest() + loanSummary1.getOutstadingInterest());
//						sumLoanSummary.setOutstadingFee(sumLoanSummary.getOutstadingFee() + loanSummary.getOutstadingFee());
					sumLoanSummary.setTotalOverdue(sumLoanSummary.getTotalOverdue() + loanSummary1.getTotalOverdue());
					sumLoanSummary.setOverduePrincipal(sumLoanSummary.getOverduePrincipal()+ loanSummary1.getOverduePrincipal());
					sumLoanSummary.setOverdueInterest(sumLoanSummary.getOverdueInterest()+ loanSummary1.getOverdueInterest());
					sumLoanSummary.setOverduePenalty(sumLoanSummary.getOverduePenalty()+ loanSummary1.getOverduePenalty());
					sumLoanSummary.setTotalOutstanding(sumLoanSummary.getTotalOutstanding() + loanSummary1.getTotalOutstanding());
					sumLoanSummary.setTotalDisbursed(sumLoanSummary.getTotalDisbursed() + loanSummary1.getTotalDisbursed());
					sumLoanSummary.setLoanAmount(sumLoanSummary.getLoanAmount() + loanSummary1.getLoanAmount());
				}
				else{
					sumLoanSummary.setTotalPaidKGS(loanSummary1.getTotalPaidKGS());
					sumLoanSummary.setTotalPaid(loanSummary1.getTotalPaid());
					sumLoanSummary.setOutstadingPenalty(loanSummary1.getOutstadingPenalty());
					sumLoanSummary.setOutstadingPrincipal(loanSummary1.getOutstadingPrincipal());
					sumLoanSummary.setOutstadingInterest(loanSummary1.getOutstadingInterest());
					sumLoanSummary.setOutstadingFee(loanSummary1.getOutstadingFee());
					sumLoanSummary.setTotalOverdue(loanSummary1.getTotalOverdue());
					sumLoanSummary.setOverduePrincipal(loanSummary1.getOverduePrincipal()*rate);
					sumLoanSummary.setOverdueInterest(loanSummary1.getOverdueInterest()*rate);
					sumLoanSummary.setOverduePenalty(loanSummary1.getOverduePenalty()*rate);
					sumLoanSummary.setTotalOutstanding(loanSummary1.getTotalOutstanding());
					sumLoanSummary.setTotalDisbursed(loanSummary1.getTotalDisbursed());
					sumLoanSummary.setLoanAmount(loanSummary1.getLoanAmount());
				}
			}
		}
//		model.addAttribute("debtorId",);
//		model.addAttribute("debtor",debtorService.getById(debtorId));
		model.addAttribute("fromLoanList","true");
		model.addAttribute("fromLoan","true");
		model.addAttribute("lists",summaries);
		model.addAttribute("isCalculationSaved",false);
		model.addAttribute("totals",sumLoanSummary);
		return "/manage/debtor/loanSummary2";
	}


//	get the payments of phase
	@PostMapping("/collectionphase/{phaseId}/payments")
	@ResponseBody
	private String getPaymentsByPhaseId(@PathVariable("phaseId") Long phaseId)
	{

		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		List<PaymentModel> result = new ArrayList<>();
		CollectionPhase phase=phaseService.getById(phaseId);
		String phaseLoanIds="";
		for(Loan l:phase.getLoans()) {
			phaseLoanIds = phaseLoanIds + (l.getId()) + ",";
		}
		phaseLoanIds= phaseLoanIds.substring(0, phaseLoanIds.length() - 1);
		Date closeDate = new Date();
		if (phase.getCloseDate() != null) {
			closeDate = phase.getCloseDate();
		}
		else if(phase.getCollectionProcedure()!=null){
			CollectionProcedure procedure=procService.getById(phase.getCollectionProcedure().getId());
			if(procedure.getCloseDate()!=null){
				closeDate=procedure.getCloseDate();
			}
		}
		String getListOfPaymentsQuery= String.format("select *\nfrom payment where loanId in (%s)" +
				" and paymentDate between '%s' and '%s'", phaseLoanIds, sf.format(phase.getPaymentFromDate()), sf.format(closeDate));
		Query query=entityManager.createNativeQuery(getListOfPaymentsQuery,Payment.class);
		List<Payment> payments=query.getResultList();
		for(Payment pp: payments)
		{
			PaymentModel model = new PaymentModel();
			Payment p=paymentService.getById(pp.getId());

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
			model.setLoanId(p.getLoan().getId());

			result.add(model);
		}

		Collections.sort(result);
		int counter=1;
		for(PaymentModel model:result){
			model.setCounter(counter++);
		}

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String returnResult= gson.toJson(result);

		return returnResult;
	}

	//	if less than zero make zero
	public Double conditional(Double num){
		if(num<0){
			return Double.valueOf(0);
		}
		else if(num==Double.valueOf(0)){
			return num;
		}
		else{
			return num/1000;
		}
	}



	public String  getDocs(Long id){

        String baseQuery="select d.title,d.id,dt.name as type\n" +
                "from df_document d,person p,cat_responsible_organization ro,cat_document_type dt\n" +
                "where (d.receiverResponsible=ro.Responsible_id or d.senderResponsible=ro.Responsible_id) " +
                "and dt.id=d.documentType and ro.organizations_id="+id+" group by d.id";
        Query query=entityManager.createNativeQuery(baseQuery, PersonOrganizationDocModel.class);

        List<PersonOrganizationDocModel> list=query.getResultList();

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(list);

        return result;
    }

	public void phaseUpdater(Long debtorId) {
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
		try{
			String phaseUpdateQuery = "update phaseDetails phd, phase_amount_view v,loan l\n" +
					"set phd.paidTotalAmount = v.paid_amount,\n" +
					"    phd.paidPrincipal = v.paid_principal,\n" +
					"    phd.paidInterest = v.paid_interest,\n" +
					"    phd.paidPenalty = v.paid_penalty\n" +
					"where phd.id = v.det_id and phd.loan_id=l.id and l.debtorId="+debtorId;
			session.createSQLQuery(phaseUpdateQuery).executeUpdate();
		}
		catch (Exception e){

		}
		Debtor debtor=debtorService.getById(debtorId);
		for(Loan loan1:debtor.getLoans()){
			Loan loan=loanService.getById(loan1.getId());
			for(CollectionPhase phase1:loan.getCollectionPhases()){
				try {
					String phaseSetPaid = "update collectionPhase cph,phaseDetails det\n" +
							"set cph.paid = (select sum(paidTotalAmount) from phaseDetails d where d.collectionPhaseId="+phase1.getId()+")\n" +
							"where cph.id=det.collectionPhaseId and det.collectionPhaseId="+phase1.getId();
					session.createSQLQuery(phaseSetPaid).executeUpdate();
				}
				catch (Exception e){

				}
			}
		}

		session.getTransaction().commit();

	}

	public void phaseAndPhaseDetailsUpdate(CollectionPhase phase) {
		Double totalPaid = 0.0;
		Date closeDate = new Date();
		if (phase.getCloseDate() != null) {
			closeDate = phase.getCloseDate();
		}
		for (PhaseDetails details : phase.getPhaseDetails()) {
			HashMap<String, Double> totals = getPaids( details.getLoan_id(),phase.getPaymentFromDate(), closeDate);
				details.setPaidTotalAmount(totals.get("totalAmount"));
				details.setPaidPrincipal(totals.get("principal"));
				details.setPaidPenalty(totals.get("penalty"));
				details.setPaidInterest(totals.get("interest"));
				details.setPaidFee(totals.get("fee"));
				phaseDetailsService.update(details);
				totalPaid = totalPaid + totals.get("totalAmount");
		}
		phase.setPaid(totalPaid);
		collectionPhaseService.update(phase);

	}

	public HashMap<String, Double> getPaids(Long loanId, Date fromDate, Date toDate) {
		HashMap<String, Double> result = new HashMap<>();
		Double totalAmount = 0.0;
		Double principal = 0.0;
		Double penalty = 0.0;
		Double interest = 0.0;
		Double fee = 0.0;
		Loan loan=loanService.getById(loanId);
		for (Payment payment : loan.getPayments()) {
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

        }
    }

	private List<SystemFileModel> getSystemFilesByItemId(Long phaseId){

		List<SystemFileModel> list=new ArrayList<>();
		List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(11,phaseId);
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

	private void updatePaidOfPhase(CollectionPhase phase){

        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");

        String loanIds="";
        Date fromDate=phase.getPaymentFromDate();
        if(fromDate==null){
            fromDate=phase.getStartDate();
        }
        Date toDate=phase.getPaymentToDate();
        if(toDate==null)
            toDate=new Date();

        for(Loan loan:phase.getLoans()){
            loanIds=loanIds+loan.getId()+",";
        }
        String getTotalPaidQuery="select IFNULL(sum(p.totalAmount),0.0)\n" +
                "from payment p where p.record_status=1 and loanId in ("+loanIds.substring(0,loanIds.length()-1)+") and p.paymentDate between '"+dateFormat.format(fromDate)+"' and '"+dateFormat.format(toDate)+"'";
        Query query=entityManager.createNativeQuery(getTotalPaidQuery);
        Double paidOfPhase= (Double) query.getSingleResult();

        phase.setPaid(paidOfPhase);
        collectionPhaseService.update(phase);

        String getSumOfPayments="select phd.id,sum(p.totalAmount) as amount,sum(p.principal) as principal,sum(p.interest) as interest,sum(p.penalty) as penalty,sum(p.fee) as fee\n" +
                "from phaseDetails phd,payment p where collectionPhaseId="+phase.getId()+" and p.loanId=phd.loan_id " +
                "and p.paymentDate between '"+dateFormat.format(fromDate)+"' and '"+dateFormat.format(toDate)+"' group by phd.id";
        Query query1=entityManager.createNativeQuery(getSumOfPayments,SimplePhaseDetailsModel.class);
        List<SimplePhaseDetailsModel> simplePhaseDetailsModelList=query1.getResultList();
        for(SimplePhaseDetailsModel model:simplePhaseDetailsModelList){
            PhaseDetails phaseDetails=phaseDetailsService.getById(model.getId());
            phaseDetails.setPaidTotalAmount(model.getAmount());
            phaseDetails.setPaidPrincipal(model.getPrincipal());
            phaseDetails.setPaidInterest(model.getInterest());
            phaseDetails.setPaidPenalty(model.getPenalty());
            phaseDetails.setPaidFee(model.getFee());
            phaseDetailsService.update(phaseDetails);
        }
    }

    //debtor group
    private void changeDebtorGroupAndSubGroup(Long debtorId, Long procedureStatusId, CollectionProcedure procedure){

		Debtor debtor = debtorService.getById(debtorId);

		if(procedureStatusId ==7)
		{
			DebtorGroup group = debtorGroupService.getById(2L);
			DebtorSubGroup subGroup = debtorSubGroupService.getById(4L);

			debtor.setDebtorGroup(group);
			debtor.setDebtorSubGroup(subGroup);

			debtorService.update(debtor);

			CollectionPhase phase = phaseService.getById(procedure.getLastPhase());

			for (PhaseDetails phaseDetails: phase.getPhaseDetails())
			{
				Loan loan = loanService.getById(phaseDetails.getLoan_id());

				LoanFinGroup loanGroup = loanFinGroupService.getById(18L);

				loan.setLoanFinGroup(loanGroup);

				loanService.update(loan);
			}


		}


		if(procedureStatusId ==8)
		{
			DebtorGroup group = debtorGroupService.getById(2L);
			DebtorSubGroup subGroup = debtorSubGroupService.getById(3L);

			debtor.setDebtorGroup(group);
			debtor.setDebtorSubGroup(subGroup);

			debtorService.update(debtor);

			CollectionPhase phase = phaseService.getById(procedure.getLastPhase());

			for (PhaseDetails phaseDetails: phase.getPhaseDetails())
			{
				Loan loan = loanService.getById(phaseDetails.getLoan_id());

				LoanFinGroup loanGroup = loanFinGroupService.getById(17L);

				loan.setLoanFinGroup(loanGroup);

				loanService.update(loan);
			}


		}


		if(procedureStatusId ==9)
		{
			DebtorGroup group = debtorGroupService.getById(1L);
			DebtorSubGroup subGroup = debtorSubGroupService.getById(2L);

			debtor.setDebtorGroup(group);
			debtor.setDebtorSubGroup(subGroup);

			debtorService.update(debtor);

			CollectionPhase phase = phaseService.getById(procedure.getLastPhase());

			for (PhaseDetails phaseDetails: phase.getPhaseDetails())
			{
				Loan loan = loanService.getById(phaseDetails.getLoan_id());

				LoanFinGroup loanGroup = loanFinGroupService.getById(3L);

				loan.setLoanFinGroup(loanGroup);

				loanService.update(loan);
			}
		}


		if(procedureStatusId ==10)
		{
			DebtorGroup group = debtorGroupService.getById(2L);
			DebtorSubGroup subGroup = debtorSubGroupService.getById(5L);

			debtor.setDebtorGroup(group);
			debtor.setDebtorSubGroup(subGroup);

			debtorService.update(debtor);

			CollectionPhase phase = phaseService.getById(procedure.getLastPhase());

			for (PhaseDetails phaseDetails: phase.getPhaseDetails())
			{
				Loan loan = loanService.getById(phaseDetails.getLoan_id());

				LoanFinGroup loanGroup = loanFinGroupService.getById(20L);

				loan.setLoanFinGroup(loanGroup);

				loanService.update(loan);
			}
		}






	}

	private boolean isPhaseWithDateValid(Long procedureId, Date phaseDate){


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String baseQuery = "select count(1)\n" +
				"from collectionPhase p where collectionProcedureId="+procedureId+ " and p.startDate = '"+format.format(phaseDate)+"' and p.record_status=1";
		Query query = entityManager.createNativeQuery(baseQuery);

        BigInteger count = (BigInteger) query.getResultList().get(0);

		if(count == BigInteger.valueOf(0)){
		    return true;
        }
        return false;
	}


	//register collectionphase api
	@PostMapping("/api/collectionphase/registerchange")
	@ResponseBody
	private String registerApi(Long id)
	{
		registerCollectionPhase(id);
		return "OK";
	}

	//register collectionPhase function
	public void registerCollectionPhase(Long id){
		CollectionPhase collectionPhase = collectionPhaseService.getById(id);

		ManageCounter counter = manageCounterRepository.getByEntityNameEquals(CollectionPhase.class.getSimpleName());


		if (counter == null){
			ManageCounter counter1 =new ManageCounter();
			counter1.setEntityName(CollectionPhase.class.getSimpleName());
			counter1.setNumber(1L);
			manageCounterRepository.save(counter1);

			collectionPhase.setDocNumber("№ "+counter1.getNumber());
			collectionPhase.setReg_date(new Date());

			collectionPhaseService.update(collectionPhase);

			counter1.setNumber(counter1.getNumber()+1);
			manageCounterRepository.save(counter1);
		}
		else{
			collectionPhase.setDocNumber("№ "+counter.getNumber());
			collectionPhase.setReg_date(new Date());

			collectionPhaseService.update(collectionPhase);

			counter.setNumber(counter.getNumber()+1);
			manageCounterRepository.save(counter);
		}

	}

    //search phases by date or phase type
    @GetMapping("/api/phases/search")
    @ResponseBody
    private String[] searchPhasesByDateType(@RequestParam(value = "q") String q)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String searchStr = "select *\n" +
                "from collectionPhase where docNumber like '%"+q+"%'";

        Query query = entityManager.createNativeQuery(searchStr,CollectionPhase.class);
        List<CollectionPhase> phaseList = query.getResultList();

        String[] result = new String[phaseList.size()];
        int i=0;
        for(CollectionPhase phase : phaseList){
            result[i++] = "[" + phase.getId() + "] "
                    + phase.getDocNumber() +" - " + simpleDateFormat.format(phase.getStartDate());
        }
        return result;
    }

}
