package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.web.fetchModels.CollectionPhaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.collection.CollectionProcedure;
import kg.gov.mf.loan.manage.model.collection.ProcedureStatus;
import kg.gov.mf.loan.manage.model.collection.ProcedureType;
import kg.gov.mf.loan.manage.service.collection.CollectionProcedureService;
import kg.gov.mf.loan.manage.service.collection.ProcedureStatusService;
import kg.gov.mf.loan.manage.service.collection.ProcedureTypeService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class CollectionProcedureController {
	
	@Autowired
	CollectionProcedureService procService;
	
	@Autowired
	DebtorService debtorService;

	@Autowired
	ProcedureStatusService procStatService;
	
	@Autowired
	ProcedureTypeService procTypeService;

	@Autowired
	CollectionPhaseService collectionPhaseService;

	@Autowired
	UserService userService;

	@Autowired
	StaffService staffService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view"})
    public String viewProcedure(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("procId")Long procId) {
		
		CollectionProcedure proc = procService.getById(procId);
		model.addAttribute("proc", proc);

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonPhases = gson.toJson(getPhasesByProcId(procId));
        model.addAttribute("phases", jsonPhases);

		User user = userService.findByUsername(Utils.getPrincipal());
		Staff staff=staffService.findById(user.getStaff().getId());
		try{
			model.addAttribute("departmentId",staff.getDepartment().getId());
		}
		catch(Exception e){
			model.addAttribute("departmentId",0);
		}

		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtorService.getById(debtorId));
		model.addAttribute("procId", procId);
		
		return "/manage/debtor/collectionprocedure/view";
		
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/save", method=RequestMethod.GET)
	public String formProcedure(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("procId")Long procId)
	{
		model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
		
		if(procId == 0)
		{
			model.addAttribute("proc", new CollectionProcedure());
		}
			
		if(procId > 0)
		{
			model.addAttribute("proc", procService.getById(procId));
		}
		
		model.addAttribute("statuses", procStatService.list());
		model.addAttribute("types", procTypeService.list());
		
		return "/manage/debtor/collectionprocedure/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/save"})
    public String saveProcedure(CollectionProcedure proc, 
    		@PathVariable("debtorId")Long debtorId,
    		ModelMap model)
    {
		if(proc.getId() == 0)
			procService.add(proc);
		else
			procService.update(proc);
		
		return "redirect:" + "/manage/debtor/{debtorId}/view";
    }

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/delete"})
    public String deleteProcedure(long id, @PathVariable("debtorId")Long debtorId)
    {
		if(id > 0)
			procService.remove(procService.getById(id));
		
		return "redirect:" + "/manage/debtor/{debtorId}/view";
    }
	
	@RequestMapping(value = { "/manage/debtor/collectionprocedure/status/list" }, method = RequestMethod.GET)
	public String listStatuses(ModelMap model) {

		List<ProcedureStatus> statuses = procStatService.list();
		model.addAttribute("statuses", statuses);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collectionprocedure/status/list";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/status/{statusId}/save", method=RequestMethod.GET)
	public String formLoanState(ModelMap model, @PathVariable("statusId")Long statusId)
	{
		if(statusId == 0)
		{
			model.addAttribute("status", new ProcedureStatus());
		}

		if(statusId > 0)
		{
			model.addAttribute("status", procStatService.getById(statusId));
		}
		return "/manage/debtor/collectionprocedure/status/save";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/status/save", method=RequestMethod.POST)
    public String saveLoanState(ProcedureStatus status, ModelMap model) {
		if(status.getId() == 0)
			procStatService.add(status);
		else
			procStatService.update(status);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collectionprocedure/status/list";
    }

	@RequestMapping(value="/manage/debtor/collectionprocedure/status/delete", method=RequestMethod.POST)
	public String deleteLoanState(long id) {
		if(id > 0)
			procStatService.remove(procStatService.getById(id));
		return "redirect:" + "/manage/debtor/collectionprocedure/status/list";
	}
	
	@RequestMapping(value = { "/manage/debtor/collectionprocedure/type/list" }, method = RequestMethod.GET)
	public String listTypes(ModelMap model) {

		List<ProcedureType> types = procTypeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collectionprocedure/type/list";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/type/{typeId}/save", method=RequestMethod.GET)
	public String formType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new ProcedureType());
		}

		if(typeId > 0)
		{
			model.addAttribute("type", procTypeService.getById(typeId));
		}
		return "/manage/debtor/collectionprocedure/type/save";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/type/save", method=RequestMethod.POST)
    public String saveType(ProcedureType type, ModelMap model) {
		if(type.getId() == 0)
			procTypeService.add(type);
		else
			procTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collectionprocedure/type/list";
    }

	@RequestMapping(value="/manage/debtor/collectionprocedure/type/delete", method=RequestMethod.POST)
	public String deleteType(long id) {
		if(id > 0)
			procTypeService.remove(procTypeService.getById(id));
		return "redirect:" + "/manage/debtor/collectionprocedure/type/list";
	}

    private List<CollectionPhaseModel> getPhasesByProcId(long procId)
    {
        List<CollectionPhaseModel> result = new ArrayList<>();
        CollectionProcedure proc = procService.getById(procId);
        for(CollectionPhase tempp: proc.getCollectionPhases())
        {
        	CollectionPhase temp=collectionPhaseService.getById(tempp.getId());

            CollectionPhaseModel model = new CollectionPhaseModel();
            model.setId(temp.getId());
            model.setStartDate(temp.getStartDate());
            model.setCloseDate(temp.getCloseDate());
            model.setLastEvent(temp.getLastEvent());
            model.setLastStatusId(temp.getLastStatusId());
            model.setPhaseStatusId(temp.getPhaseStatus().getId());
            model.setPhaseStatusName(temp.getPhaseStatus().getName());
            model.setPhaseTypeId(temp.getPhaseType().getId());
            model.setPhaseTypeName(temp.getPhaseType().getName());
            model.setProcedureId(temp.getCollectionProcedure().getId());
            try{
				model.setDepartmentId(temp.getDepartment_id());
			}
            catch(Exception e){
            	model.setDepartmentId(Long.valueOf(0));
			}
            result.add(model);
        }

        Collections.sort(result);

        return result;
    }
	
}
