package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/view"})
    public String viewProcedure(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("procId")Long procId) {
		
		CollectionProcedure proc = procService.getById(procId);
		model.addAttribute("proc", proc);
		model.addAttribute("phases", proc.getCollectionPhases());
		
		return "/manage/debtor/collectionprocedure/view";
		
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/save", method=RequestMethod.GET)
	public String formProcedure(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("procId")Long procId)
	{
		model.addAttribute("debtorId", debtorId);
		
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
	
}
