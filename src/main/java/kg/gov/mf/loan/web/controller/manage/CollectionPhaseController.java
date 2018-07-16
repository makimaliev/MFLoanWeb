package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.collection.*;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.service.collection.*;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.process.LoanDetailedSummaryService;
import kg.gov.mf.loan.process.service.JobItemService;
import kg.gov.mf.loan.web.fetchModels.CollectionPhaseDetailsModel;
import kg.gov.mf.loan.web.fetchModels.PhaseDetailsModel;
import kg.gov.mf.loan.web.fetchModels.PhaseDetailsModelList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
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

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonPhaseDetails = gson.toJson(getPhaseDetailsByPhaseId(phaseId));
        model.addAttribute("phaseDetails", jsonPhaseDetails);

		return "/manage/debtor/collectionprocedure/collectionphase/view";

	}

	@RequestMapping(value="/manage/debtor/{debtorId}/initializephase", method=RequestMethod.POST)
	@ResponseBody
	public String initializePhaseForm(@PathVariable("debtorId")Long debtorId, @RequestParam Map<String, String> selectedLoans, @RequestParam Date initDate)
	{
		List<PhaseDetailsModel> result = new ArrayList<>();
		int count = 0;
		for (String value:selectedLoans.values()
			 ) {
			if(count == selectedLoans.size()-1) continue;
			PhaseDetailsModel dTemp = new PhaseDetailsModel();
			Loan loan = loanRepository.findOne(Long.parseLong(value));
			dTemp.setLoanId(loan.getId());
			dTemp.setLoanRegNumber(loan.getRegNumber());
			this.jobItemService.runManualCalculateProcedure(loan.getId(), initDate);
			LoanDetailedSummary summary = loanDetailedSummaryService.getByOnDateAndLoanId(initDate, loan.getId());
			dTemp.setStartPrincipal(summary.getPrincipalOverdue());
			dTemp.setStartInterest(summary.getInterestOverdue());
			dTemp.setStartPenalty(summary.getPenaltyOverdue());
			dTemp.setStartTotalAmount(summary.getPrincipalOverdue() + summary.getInterestOverdue() + summary.getPenaltyOverdue());
			result.add(dTemp);
			count++;
		}
		System.out.println(initDate);

		Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String jsonResult = gson2.toJson(result);
		return jsonResult;
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/initializephasesave", method=RequestMethod.POST)
	@ResponseBody
	public String initializePhaseSave(@PathVariable("debtorId")Long debtorId, @RequestBody PhaseDetailsModelList phaseDetailsModels)
	{
		if(phaseDetailsModels.getPhaseDetailsModels().size() > 0){

		    Date startDate = null;

            CollectionPhase phase = new CollectionPhase();

		    List<PhaseDetailsModel> list = phaseDetailsModels.getPhaseDetailsModels();
            Set<Loan> loans = new HashSet<>();

            for (PhaseDetailsModel model: list
                 ) {
                Loan loan = loanRepository.findOne(model.getLoanId());
                loans.add(loan);

                startDate = model.getInitDate();
            }

            phase.setLoans(loans);
            phase.setPhaseStatus(statusService.getById(1L));
            phase.setPhaseType(typeService.getById(1L));
            phase.setStartDate(startDate);

            CollectionProcedure procedure = new CollectionProcedure();
            procedure.setStartDate(startDate);
            procedure.setProcedureStatus(procedureStatusService.getById(1L));
            procedure.setProcedureType(procedureTypeService.getById(1L));
            procService.add(procedure);
            phase.setCollectionProcedure(procedure);
            phaseService.add(phase);

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

            procedure.setLastPhase(phase.getId());
            //procedure.setLastStatusId(phase.getLastStatusId());
            procService.update(procedure);

        }

		return "OK";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/save", method=RequestMethod.GET)
	public String formCollateralItem(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("procId")Long procId,
			@PathVariable("phaseId")Long phaseId)
	{
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtor);
		model.addAttribute("procId", procId);
		model.addAttribute("proc", procService.getById(procId));

		if(phaseId == 0)
		{
			model.addAttribute("phase", new CollectionPhase());
			model.addAttribute("tLoans", debtor.getLoans());
		}

		if(phaseId > 0)
		{
			CollectionPhase phase = phaseService.getById(phaseId);
			model.addAttribute("phase", phase);
			model.addAttribute("tLoans", phase.getLoans());
		}

		model.addAttribute("statuses", statusService.list());
		model.addAttribute("types", typeService.list());

		return "/manage/debtor/collectionprocedure/collectionphase/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/save"}, method=RequestMethod.POST)
    public String saveCollateralItem(CollectionPhase phase,
    		@PathVariable("debtorId")Long debtorId,
    		@PathVariable("procId")Long procId,
    		ModelMap model)
    {
		CollectionProcedure procedure = procService.getById(procId);
		phase.setCollectionProcedure(procedure);

		if(phase.getId() == 0){
			phaseService.add(phase);
			Set<Loan> loans = phase.getLoans();
			for (Loan loan: loans)
			{
				PhaseDetails details = new PhaseDetails();
				details.setLoan_id(loan.getId());
				PhaseDetails latestDetails = phaseDetailsService.getById(getLatestDetailsByDate(loan.getId()));
				if(latestDetails != null)
				{
					details.setStartPrincipal(latestDetails.getStartPrincipal());
					details.setStartInterest(latestDetails.getStartInterest());
					details.setStartPenalty(latestDetails.getStartPenalty());
					details.setStartTotalAmount(latestDetails.getStartTotalAmount());
				}
				else
				{
					details.setStartPrincipal(0.0);
					details.setStartInterest(0.0);
					details.setStartPenalty(0.0);
					details.setStartTotalAmount(0.0);
				}

				details.setCollectionPhase(phase);
				phaseDetailsService.add(details);
			}
		}

		else
			phaseService.update(phase);
		
		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view";
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
            model.setStartTotalAmount(temp.getStartTotalAmount());
            model.setStartPrincipal(temp.getStartPrincipal());
            model.setStartInterest(temp.getStartInterest());
            model.setStartPenalty(temp.getStartPenalty());
            model.setStartFee(temp.getStartFee());
            model.setCloseTotalAmount(temp.getCloseTotalAmount());
            model.setClosePrincipal(temp.getClosePrincipal());
            model.setCloseInterest(temp.getCloseInterest());
            model.setClosePenalty(temp.getClosePenalty());
            model.setCloseFee(temp.getCloseFee());
            model.setPaidTotalAmount(temp.getPaidTotalAmount());
            model.setPaidPrincipal(temp.getPaidPrincipal());
            model.setPaidInterest(temp.getPaidInterest());
            model.setPaidPenalty(temp.getPaidPenalty());
            model.setPaidFee(temp.getPaidFee());
            model.setLoan_id(temp.getLoan_id());
            result.add(model);
        }

        return result;
    }
	
}
