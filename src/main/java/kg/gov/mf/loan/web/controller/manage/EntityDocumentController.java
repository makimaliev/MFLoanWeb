package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackage;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentRegisteredBy;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentState;
import kg.gov.mf.loan.manage.model.orderdocument.OrderDocumentType;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageStateService;
import kg.gov.mf.loan.manage.service.entity.AppliedEntityService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentRegisteredByService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentStateService;
import kg.gov.mf.loan.manage.service.entitylist.AppliedEntityListService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.orderdocument.OrderDocumentTypeService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class EntityDocumentController {
	
	@Autowired
	EntityDocumentStateService edStateService;
	
	@Autowired
	EntityDocumentRegisteredByService edRBService;
	
	@Autowired
	DocumentPackageService dpService;
	
	@Autowired
	EntityDocumentService edService;

	@Autowired
	UserService userService;

	@Autowired
	OrderDocumentTypeService oDTypeService;

	@Autowired
	DocumentPackageStateService dpStateService;

	@Autowired
	CreditOrderService orderService;

	@Autowired
	AppliedEntityListService listService;

	@Autowired
	AppliedEntityService entityService;

	@Autowired
	DistrictService districtService;

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = {5, 10, 20, 50, 100};
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = {"/manage/order/entitydocument/list" }, method = RequestMethod.GET)
	public String listEntityDocuments(ModelMap model) {

		model.addAttribute("districts", districtService.findAll());
		model.addAttribute("orders", orderService.list());

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/order/entityDocumentList";
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/{edId}/view"})
    public String viewEntity(
    		ModelMap model, 
    		@PathVariable("orderId")Long orderId, 
    		@PathVariable("listId")Long listId, 
    		@PathVariable("entityId")Long entityId,
    		@PathVariable("dpId")Long dpId,
    		@PathVariable("edId")Long edId) {

		EntityDocument ed = edService.getById(edId);
        model.addAttribute("ed", ed);
        
        model.addAttribute("orderId", orderId);
        model.addAttribute("listId", listId);
        model.addAttribute("entityId", entityId);
        model.addAttribute("dpId", dpId);
        model.addAttribute("edId", edId);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/entitydocument/view";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/{edId}/save", method=RequestMethod.GET)
	public String formEntityDocument(
			ModelMap model, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId, 
			@PathVariable("entityId")Long entityId,
			@PathVariable("dpId")Long dpId,
			@PathVariable("edId")Long edId)
	{
		if(edId == 0)
		{
			model.addAttribute("ed", new EntityDocument());
		}
			
		
		if(edId > 0)
		{
			model.addAttribute("ed", edService.getById(edId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
		model.addAttribute("listId", listId);
		model.addAttribute("list", listService.getById(listId));
		model.addAttribute("entityId", entityId);
		model.addAttribute("entity", entityService.getById(entityId));
		model.addAttribute("dpId", dpId);
		model.addAttribute("dp", dpService.getById(dpId));

		List<OrderDocumentType> types = oDTypeService.list();
		model.addAttribute("types", types);
		
		return "/manage/order/entitylist/entity/documentpackage/entitydocument/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/save", method=RequestMethod.POST)
	public String saveEntityDocument(
			EntityDocument doc, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId,
			@PathVariable("entityId")Long entityId,
			@PathVariable("dpId")Long dpId,
			ModelMap model)
	{
		DocumentPackage dPackage = dpService.getById(dpId);
		doc.setDocumentPackage(dPackage);
		
		if(doc.getId() == 0)
		{
			doc.setEntityDocumentState(edStateService.getById(1L) != null? edStateService.getById(1L): null);
			edService.add(doc);
			dPackage.calculateRatios();
			dpService.update(dPackage);
		}

		else
			edService.update(doc);
			
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/view";
	}

	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/{edId}/progress", method=RequestMethod.GET)
	public String completeEntityDocument(
			@RequestParam("type") String type,
			@RequestParam("description") String description,
			@RequestParam("registeredNumber") Optional<String> registeredNumber,
			@RequestParam("registeredDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date registeredDate,
			@RequestParam("registeredById") Optional<Long> registeredById,
			@PathVariable("orderId")Long orderId,
			@PathVariable("listId")Long listId,
			@PathVariable("entityId")Long entityId,
			@PathVariable("dpId")Long dpId,
			@PathVariable("edId")Long edId)
	{

		EntityDocument document = edService.getById(edId);
		User user = userService.findByUsername(Utils.getPrincipal());
		Calendar now = Calendar.getInstance();
		Date today = now.getTime();

		if(type.equals("completed"))
		{
			document.setCompletedBy(user.getId());
			document.setCompletedDate(today);
			document.setCompletedDescription(description);
			document.setEntityDocumentState(edStateService.getById(3L));
		}
		else if(type.equals("approved"))
		{
			document.setApprovedBy(user.getId());
			document.setApprovedDate(today);
			document.setApprovedDescription(description);
			document.setEntityDocumentState(edStateService.getById(4L));
		}
		else
		{
			String regNumber = registeredNumber.orElse(null);
			long regId = registeredById.orElse(0L);
			//Date regDate = registeredDate.orElse(null);

			if(regNumber != null && regId > 0 && registeredDate != null)
			{
				document.setRegisteredNumber(regNumber);
				document.setRegisteredBy(edRBService.getById(regId));
				document.setRegisteredDate(registeredDate);
				document.setRegisteredDescription(description);
				document.setEntityDocumentState(edStateService.getById(2L));
			}

		}

		edService.update(document);
		updateDocumentPackageInfo(dpId, type, today);

		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/view";
	}

	private void updateDocumentPackageInfo(Long dpId, String type, Date date) {

		DocumentPackage dp = dpService.getById(dpId);
		dp.calculateRatios();

		if(type.equals("completed"))
			dp.setCompletedDate(date);
		else if(type.equals("approved"))
			dp.setApprovedDate(date);

		if(dp.getCompletedRatio() == 1.0)
			dp.setDocumentPackageState(dpStateService.getById(1L));

		if(dp.getApprovedRatio() == 1.0)
			dp.setDocumentPackageState(dpStateService.getById(2L));

		/*
		if(dp.getRegisteredRatio() == 1.0)
			dp.setDocumentPackageState(dpStateService.getById(6L));
			*/

		dpService.update(dp);

	}

	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/entitydocument/delete", method=RequestMethod.POST)
    public String deleteDocumentPackage(long id, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId, @PathVariable("dpId")Long dpId) {
		if(id > 0)
			edService.remove(edService.getById(id));
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/view";
    }

	@RequestMapping(value = { "/manage/order/entitylist/entity/documentpackage/entitydocument/state/list" }, method = RequestMethod.GET)
    public String listEDStates(ModelMap model) {
 
		List<EntityDocumentState> states = edStateService.list();
		model.addAttribute("states", states);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/entitydocument/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/state/{stateId}/save", method=RequestMethod.GET)
	public String formEDState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("edState", new EntityDocumentState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("edState", edStateService.getById(stateId));
		}
		return "/manage/order/entitylist/entity/documentpackage/entitydocument/state/save";
	}	
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/state/save", method=RequestMethod.POST)
    public String saveEntityDocumentState(EntityDocumentState state, ModelMap model) {
		if(state.getId() == 0)
			edStateService.add(state);
		else
			edStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/documentpackage/entitydocument/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/state/delete", method=RequestMethod.POST)
    public String deleteEntityDocumentState(long id) {
		if(id > 0)
			edStateService.remove(edStateService.getById(id));
		return "redirect:" + "/manage/order/entitylist/entity/documentpackage/entitydocument/state/list";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/list" }, method = RequestMethod.GET)
    public String listEDRBs(ModelMap model) {
 
		List<EntityDocumentRegisteredBy> rBs = edRBService.list();
		model.addAttribute("rBs", rBs);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/{rbId}/save", method=RequestMethod.GET)
	public String formEDRB(ModelMap model, @PathVariable("rbId")Long rbId)
	{
		if(rbId == 0)
		{
			model.addAttribute("edRB", new EntityDocumentRegisteredBy());
		}
		
		if(rbId > 0)
		{
			model.addAttribute("edRB", edRBService.getById(rbId));
		}
		return "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/save", method=RequestMethod.POST)
    public String saveEntityDocumentRegisteredBy(EntityDocumentRegisteredBy rb,	ModelMap model) {
		if(rb.getId() == 0)
			edRBService.add(rb);
		else
			edRBService.update(rb);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/delete", method=RequestMethod.POST)
    public String deleteEntityDocumentRegisteredBy(long id) {
		if(id > 0)
			edRBService.remove(edRBService.getById(id));
		return "redirect:" + "/manage/order/entitylist/entity/documentpackage/entitydocument/registeredby/list";
    }
	
}
