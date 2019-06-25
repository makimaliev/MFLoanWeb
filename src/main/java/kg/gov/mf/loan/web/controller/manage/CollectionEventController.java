package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.manage.model.collection.CollectionEvent;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.EventStatus;
import kg.gov.mf.loan.manage.model.collection.EventType;
import kg.gov.mf.loan.manage.service.collection.CollectionEventService;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.collection.EventStatusService;
import kg.gov.mf.loan.manage.service.collection.EventTypeService;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
import kg.gov.mf.loan.web.util.Utils;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class CollectionEventController {
	
	@Autowired
	CollectionEventService eventService;
	
	@Autowired
	EventStatusService statusService;
	
	@Autowired
	EventTypeService typeService;
	
	@Autowired
	CollectionPhaseService phaseService;

	@Autowired
	InformationService informationService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	SystemFileService systemFileService;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/collectionevent/{eventId}/view", method=RequestMethod.GET)
	public String view(ModelMap model,
									  @PathVariable("debtorId")Long debtorId,
									  @PathVariable("procId")Long procId,
									  @PathVariable("phaseId")Long phaseId,
									  @PathVariable("eventId")Long eventId){
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonSysFiles= gson.toJson(getSystemFilesByPaymentId(eventId));
		model.addAttribute("files", jsonSysFiles);

		CollectionEvent event=eventService.getById(eventId);

		model.addAttribute("event",event);
		model.addAttribute("debtorId",debtorId);
		model.addAttribute("procId",procId);
		model.addAttribute("phaseId",phaseId);

		Attachment attachment=new Attachment();
		model.addAttribute("attachment",attachment);

		return "/manage/debtor/collectionprocedure/collectionphase/collectionevent/view";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/collectionevent/{eventId}/save", method=RequestMethod.GET)
	public String formCollectionEvent(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("procId")Long procId,
			@PathVariable("phaseId")Long phaseId,
			@PathVariable("eventId")Long eventId)
	{
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("procId", procId);
		model.addAttribute("phaseId", phaseId);
		
		if(eventId == 0)
		{
			CollectionEvent collectionEvent=new CollectionEvent();
			collectionEvent.setStartDate(new Date());
			model.addAttribute("event",collectionEvent );
		}
			
		if(eventId > 0)
		{
			
			CollectionEvent event = eventService.getById(eventId);
			model.addAttribute("event", event);
		}
		
		model.addAttribute("statuses", statusService.list());
		model.addAttribute("types", typeService.list());
		
		return "/manage/debtor/collectionprocedure/collectionphase/collectionevent/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/collectionevent/save"}, method=RequestMethod.POST)
    public String saveCollectionEvent(CollectionEvent event,
    		@PathVariable("debtorId")Long debtorId,
    		@PathVariable("procId")Long procId,
    		@PathVariable("phaseId")Long phaseId,
    		ModelMap model)
    {
		CollectionPhase phase = phaseService.getById(phaseId);
		event.setCollectionPhase(phase);
		
		if(event.getId() == 0)
		{
			eventService.add(event);
		}
		else
		{
			CollectionEvent event1=eventService.getById(event.getId());
			event1.setEventStatus(event.getEventStatus());
			event1.setEventType(event.getEventType());
			event1.setStartDate(event.getStartDate());
			eventService.update(event1);
		}
			
		
		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/view";
    }

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/collectionevent/delete"}, method=RequestMethod.POST)
    public String deleteCollectionEvent(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("procId")Long procId, @PathVariable("phaseId")Long phaseId)
    {
		if(id > 0)
			eventService.remove(eventService.getById(id));
		
		return "redirect:" + "/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/view";
    }
	
	@RequestMapping(value = { "/manage/debtor/collectionprocedure/collectionphase/collectionevent/status/list" }, method = RequestMethod.GET)
	public String listStatuses(ModelMap model) {

		List<EventStatus> statuses = statusService.list();
		model.addAttribute("statuses", statuses);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collectionprocedure/collectionphase/collectionevent/status/list";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/collectionevent/status/{statusId}/save", method=RequestMethod.GET)
	public String formLoanState(ModelMap model, @PathVariable("statusId")Long statusId)
	{
		if(statusId == 0)
		{
			model.addAttribute("status", new EventStatus());
		}

		if(statusId > 0)
		{
			model.addAttribute("status", statusService.getById(statusId));
		}
		return "/manage/debtor/collectionprocedure/collectionphase/collectionevent/status/save";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/collectionevent/status/save", method=RequestMethod.POST)
    public String saveLoanState(EventStatus status, ModelMap model) {
		if(status.getId() == 0)
			statusService.add(status);
		else
			statusService.update(status);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/collectionevent/status/list";
    }

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/collectionevent/status/delete", method=RequestMethod.POST)
	public String deleteLoanState(long id) {
		if(id > 0)
			statusService.remove(statusService.getById(id));
		return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/collectionevent/status/list";
	}
	
	@RequestMapping(value = { "/manage/debtor/collectionprocedure/collectionphase/collectionevent/type/list" }, method = RequestMethod.GET)
	public String listTypes(ModelMap model) {

		List<EventType> types = typeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collectionprocedure/collectionphase/collectionevent/type/list";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/collectionevent/type/{typeId}/save", method=RequestMethod.GET)
	public String formType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new EventType());
		}

		if(typeId > 0)
		{
			model.addAttribute("type", typeService.getById(typeId));
		}
		return "/manage/debtor/collectionprocedure/collectionphase/collectionevent/type/save";
	}

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/collectionevent/type/save", method=RequestMethod.POST)
    public String saveType(EventType type, ModelMap model) {
		if(type.getId() == 0)
			typeService.add(type);
		else
			typeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/collectionevent/type/list";
    }

	@RequestMapping(value="/manage/debtor/collectionprocedure/collectionphase/collectionevent/type/delete", method=RequestMethod.POST)
	public String deleteType(long id) {
		if(id > 0)
			typeService.remove(typeService.getById(id));
		return "redirect:" + "/manage/debtor/collectionprocedure/collectionphase/collectionevent/type/list";
	}

	//	add information form
	@RequestMapping("/manage/debtor/{debtorId}/collectionprocedure/{procId}/collectionphase/{phaseId}/collectionevent/{eventId}/addInformation")
	public String getAddInformationForm(Model model, @PathVariable("debtorId")Long debtorId,@PathVariable("procId")Long procId,
										@PathVariable("phaseId")Long phaseId,
										@PathVariable("eventId")Long eventId){


		String ids="";
		ids=ids+"debtorId:"+debtorId+",";
		ids=ids+"procedureId:"+procId+",";
		ids=ids+"phaseId:"+phaseId;
		model.addAttribute("ids",ids);

		CollectionEvent event=eventService.getById(eventId);


		model.addAttribute("object",event);
		model.addAttribute("systemObjectTypeId",12);

		model.addAttribute("attachment",new Attachment());

		return "/manage/debtor/loan/payment/addInformationForm";

	}


	private List<SystemFileModel> getSystemFilesByPaymentId(Long id){

		List<SystemFileModel> list=new ArrayList<>();
		List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(12,id);
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
