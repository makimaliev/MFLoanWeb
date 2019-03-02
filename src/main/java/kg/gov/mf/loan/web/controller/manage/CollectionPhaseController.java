package kg.gov.mf.loan.web.controller.manage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collection.*;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.service.collection.*;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.process.LoanDetailedSummaryService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.process.service.JobItemService;
import kg.gov.mf.loan.web.fetchModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.web.util.Utils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Controller
public class CollectionPhaseController {
	
	@Autowired
	CollectionPhaseService phaseService;
	
	@Autowired
	CollectionProcedureService procService;

	@Autowired
	ProcedureStatusService procedureStatusService;

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

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
    CollectionEventService collectionEventService;

	@Autowired
	StaffService staffService;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

//	private final ThreadLocal<List<PhaseDetails>> threadLocal=new ThreadLocal<>();
//	private List<PhaseDetails> phaseDetailsList=new ArrayList<>();
	private HashMap<String,List<PhaseDetails>> phaseDetailsLists=new HashMap<>();

	private List<CollectionPhaseSubIndexModel> collectionPhaseSubIndexModelList=new ArrayList<CollectionPhaseSubIndexModel>();
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/view"})
    public String viewCollateralItem(ModelMap model, 
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("procId")Long procId,
    		@PathVariable("phaseId")Long phaseId) {

		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtorService.getById(debtorId));
		model.addAttribute("procId", procId);
		model.addAttribute("proc", procService.getById(procId));

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

		return "/manage/debtor/collectionprocedure/collectionphase/view";

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
						CollectionPhase phase=phaseService.getById(id);
						for (PhaseDetails phaseDetails1:phase.getPhaseDetails()){
							if(phaseDetails1.getLoan_id()==Long.valueOf(value)){
								PhaseDetails phaseDetails=phaseDetailsService.getById(phaseDetails1.getId());
								dTemp.setStartPrincipal(phaseDetails.getStartPrincipal());
								dTemp.setStartInterest(phaseDetails.getStartInterest());
								dTemp.setStartPenalty(phaseDetails.getStartPenalty());
								dTemp.setStartTotalAmount(phaseDetails.getStartTotalAmount());
							}
						}
					}
					catch(Exception e){
						dTemp.setStartPrincipal(Double.valueOf(0));
						dTemp.setStartInterest(Double.valueOf(0));
						dTemp.setStartPenalty(Double.valueOf(0));
						dTemp.setStartTotalAmount(Double.valueOf(0));
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
            details.setLoan_id(model.getLoanId());
            details.setStartPrincipal(model.getStartPrincipal());
            details.setStartInterest(model.getStartInterest());
            details.setStartPenalty(model.getStartPenalty());
            details.setStartTotalAmount(model.getStartTotalAmount());
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
            procedure.setProcedureStatus(procedureStatusService.getById(1L));
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

			CollectionProcedure procedure = procService.getById(procId);
			phase.setCollectionProcedure(procedure);

			Set<Loan> loanSet = new HashSet<>();
			if (loanses != null)
				for (String id : loanses) {
					loanSet.add(loanService.getById(Long.valueOf(id)));
				}
			if (phase.getId() == 0) {


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
				phaseDetailsLists.remove(Utils.getPrincipal());
			} else {
				CollectionPhase oldPhase = phaseService.getById(phase.getId());

				phase.setPhaseStatus(oldPhase.getPhaseStatus());
				phase.setLoans(loanSet);

				if (phaseDetailsLists.get(Utils.getPrincipal()) == null) {
					System.out.println("asdf");
				} else {
					Set<PhaseDetails> listOfDetails = oldPhase.getPhaseDetails();
					for (PhaseDetails phaseDetail : phaseDetailsLists.get(Utils.getPrincipal())) {
						phaseDetail.setCollectionPhase(phase);
						for (PhaseDetails phaseDetail1 : listOfDetails) {
							if (phaseDetail.getLoan_id() == phaseDetail1.getLoan_id()) {
								phaseDetail1.setStartInterest(phaseDetail.getStartInterest());
								phaseDetail1.setStartPenalty(phaseDetail.getStartPenalty());
								phaseDetail1.setStartPrincipal(phaseDetail.getStartPrincipal());
								phaseDetail1.setStartTotalAmount(phaseDetail.getStartTotalAmount());
								phaseDetail1.setCollectionPhase(phase);
								phaseDetailsService.update(phaseDetail1);
							}
						}
					}
					String updateStart = "(select sum(startTotalAmount) from phaseDetails where collectionPhaseId=" + phase.getId() + ")";
					Query query = entityManager.createNativeQuery(updateStart);
					Double sumOfStart = (Double) query.getSingleResult();
					phase.setStart_amount(sumOfStart);
				}

				phaseService.update(phase);
				phaseDetailsLists.remove(Utils.getPrincipal());
			}
		}

		
		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
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
				details.setClosePrincipal(model.getClosePrincipal());
				details.setCloseInterest(model.getCloseInterest());
				details.setClosePenalty(model.getClosePenalty());
				details.setCloseTotalAmount(model.getCloseTotalAmount());
				phaseDetailsService.update(details);

			}
			String updateStart="(select sum(closeTotalAmount) from phaseDetails where collectionPhaseId="+phase.getId()+")";
			Query query=entityManager.createNativeQuery(updateStart);
			Double sumOfClose= (Double) query.getSingleResult();
			phase.setClose_amount(sumOfClose);
			phaseService.update(phase);

		}
	}

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{collectionPhaseId}/changeStatus"}, method=RequestMethod.POST)
	public String saveChangedStatus(@PathVariable("debtorId")Long debtorId,
									@PathVariable("procId")Long procId,@PathVariable("collectionPhaseId")Long collectionPhaseId, CollectionPhase collectionPhase){
		CollectionPhase phase=phaseService.getById(collectionPhaseId);

		System.out.println("==========================================================================================");
		System.out.println();
		phase.setPhaseStatus(collectionPhase.getPhaseStatus());
		phase.setCloseDate(collectionPhase.getCloseDate());
		phase.setResultDocNumber(collectionPhase.getResultDocNumber());
		phaseService.update(phase);
		CollectionProcedure procedure=procService.getById(phase.getCollectionProcedure().getId());
		procedure.setCloseDate(collectionPhase.getCollectionProcedure().getCloseDate());
		procedure.setProcedureStatus(collectionPhase.getCollectionProcedure().getProcedureStatus());
		procService.update(procedure);

		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
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
				"  loan.supervisorId, IFNULL(loan.parent_id, 0) as parentLoanId, loan.creditOrderId\n" +
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
	
}
