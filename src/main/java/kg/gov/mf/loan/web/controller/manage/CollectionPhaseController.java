package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.collection.*;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.service.collection.*;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.web.util.Utils;

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
		model.addAttribute("procId", procId);
		
		CollectionPhase phase = phaseService.getById(phaseId);
		Set<PhaseDetails> phaseDetails = phase.getPhaseDetails();
		model.addAttribute("phase", phase);
		//model.addAttribute("phaseDetails", phaseDetails);
		
		//model.addAttribute("events", phase.getCollectionEvents());
		
		return "/manage/debtor/collectionprocedure/collectionphase/view";
		
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/initializephase", method=RequestMethod.POST)
	@ResponseBody
	public String initializePhaseForm(@PathVariable("debtorId")Long debtorId, @RequestParam Map<String, String> selectedLoans, @RequestParam Date initDate)
	{
		for (String value:selectedLoans.values()
			 ) {
			System.out.println(value);
		}
		System.out.println(initDate);

		Gson gson2 = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String result = gson2.toJson(getPhaseDetails());
		return result;
	}

	private List<PhaseDetails> getPhaseDetails(){
		List<PhaseDetails> result = new ArrayList<>();

		PhaseDetails d1 = new PhaseDetails();
		d1.setLoan_id(30897);
		d1.setStartPrincipal(100.0);
		d1.setStartInterest(20.0);
		d1.setStartPenalty(10.0);
		d1.setStartFee(0.0);
		d1.setStartTotalAmount(1000.0);

		result.add(d1);

		PhaseDetails d2 = new PhaseDetails();
		d2.setLoan_id(30898);
		d2.setStartPrincipal(200.0);
		d2.setStartInterest(10.0);
		d2.setStartPenalty(30.0);
		d2.setStartFee(0.0);
		d2.setStartTotalAmount(2000.0);

		result.add(d2);

		return result;
	}

	/*
	@RequestMapping(value="/manage/debtor/{debtorId}/initializephase", method=RequestMethod.POST)
	public String initializePhaseSave(ModelMap model,
									  @PathVariable("debtorId")Long debtorId,
									  CollectionPhaseForm phaseForm)
	{
		CollectionPhase phase = phaseForm.getCollectionPhase();
		Set<Loan> selectedLoans = phaseForm.getLoans();
		phase.setLoans(selectedLoans);

		CollectionProcedure procedure = new CollectionProcedure();
		procedure.setStartDate(phase.getStartDate());
		procedure.setProcedureStatus(procedureStatusService.getById(1L));
		procedure.setProcedureType(procedureTypeService.getById(1L));
		procService.add(procedure);

		phase.setCollectionProcedure(procedure);
		phaseService.add(phase);

		procedure.setLastPhase(phase.getId());
		procedure.setLastStatusId(phase.getLastStatusId());
		procService.update(procedure);

		return "redirect:" + "/manage/debtor/{debtorId}/view";
	}
	*/
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/save", method=RequestMethod.GET)
	public String formCollateralItem(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("procId")Long procId,
			@PathVariable("phaseId")Long phaseId)
	{
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("tLoans", debtor.getLoans());

		model.addAttribute("phaseForm", new CollectionPhaseForm());

		model.addAttribute("statuses", statusService.list());
		model.addAttribute("types", typeService.list());

		return "/manage/debtor/collectionprocedure/collectionphase/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/save"}, method=RequestMethod.POST)
    public String saveCollateralItem(CollectionPhaseForm phaseForm,
    		@PathVariable("debtorId")Long debtorId,
    		@PathVariable("procId")Long procId,
    		ModelMap model)
    {
    	CollectionProcedure procedure = procService.getById(procId);
		CollectionPhase phase = phaseForm.getCollectionPhase();
		Set<Loan> selectedLoans = phaseForm.getLoans();
		phase.setLoans(selectedLoans);
		phase.setCollectionProcedure(procedure);
		phaseService.add(phase);
		
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
	
}
