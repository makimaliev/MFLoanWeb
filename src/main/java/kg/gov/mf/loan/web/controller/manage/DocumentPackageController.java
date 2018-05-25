package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kg.gov.mf.loan.web.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackage;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackageState;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackageType;
import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageStateService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageTypeService;
import kg.gov.mf.loan.manage.service.entity.AppliedEntityService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentRegisteredByService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentStateService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class DocumentPackageController {
	
	@Autowired
	DocumentPackageStateService dpStateService;
	
	@Autowired
	DocumentPackageTypeService dpTypeService;
	
	@Autowired
	AppliedEntityService entityService;
	
	@Autowired
	DocumentPackageService dpService;
	
	@Autowired
	EntityDocumentStateService edStateService;
	
	@Autowired
	EntityDocumentRegisteredByService rbService;

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = {5, 10, 20, 50, 100};
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = {"/manage/order/entitylist/entity/documentpackage/list" }, method = RequestMethod.GET)
	public String listDocumentPackages(@RequestParam("pageSize") Optional<Integer> pageSize,
							   @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<DocumentPackage> dps = dpService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = dpService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("PackageType", dps);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/order/entitylist/entity/documentpackage/list";
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/view"})
    public String viewEntity(
    		ModelMap model, 
    		@PathVariable("orderId")Long orderId, 
    		@PathVariable("listId")Long listId, 
    		@PathVariable("entityId")Long entityId,
    		@PathVariable("dpId")Long dpId) {

		DocumentPackage dp = dpService.getById(dpId);
        model.addAttribute("dp", dp);
        
		model.addAttribute("emptyED", new EntityDocument());
		model.addAttribute("entityDocuments", dp.getEntityDocuments());
        
        model.addAttribute("orderId", orderId);
        model.addAttribute("listId", listId);
        model.addAttribute("entityId", entityId);
        model.addAttribute("dpId", dpId);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/view";
    }
	
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/{dpId}/save", method=RequestMethod.GET)
	public String formDocumentPackage(
			ModelMap model, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId, 
			@PathVariable("entityId")Long entityId,
			@PathVariable("dpId")Long dpId)
	{
		if(dpId == 0)
		{
			model.addAttribute("dp", new DocumentPackage());
		}
			
		
		if(dpId > 0)
		{
			model.addAttribute("dp", dpService.getById(dpId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("listId", listId);
		model.addAttribute("entityId", entityId);
		
		List<DocumentPackageState> states = dpStateService.list();
        model.addAttribute("states", states);
		
		List<DocumentPackageType> types = dpTypeService.list();
        model.addAttribute("types", types);
		
		return "/manage/order/entitylist/entity/documentpackage/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/save", method=RequestMethod.POST)
	public String saveDocumentPackage(
			DocumentPackage dp, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("listId")Long listId,
			@PathVariable("entityId")Long entityId,
			ModelMap model)
	{
		AppliedEntity entity = entityService.getById(entityId);
		dp.setAppliedEntity(entity);
		
		if(dp.getId() == 0)
			dpService.add(dp);
		else
			dpService.update(dp);
			
		
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/documentpackage/delete", method=RequestMethod.POST)
    public String deleteDocumentPackage(long id, @PathVariable("orderId")Long orderId, @PathVariable("listId")Long listId, @PathVariable("entityId")Long entityId) {
		if(id > 0)
			dpService.remove(dpService.getById(id));
		return "redirect:" + "/manage/order/{orderId}/entitylist/{listId}/entity/{entityId}/view";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/entity/documentpackage/state/list" }, method = RequestMethod.GET)
    public String listDPStates(ModelMap model) {
 
		List<DocumentPackageState> states = dpStateService.list();
		model.addAttribute("states", states);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/state/{stateId}/save", method=RequestMethod.GET)
	public String formDPState(ModelMap model, @PathVariable("stateId")Long stateId)
	{
		if(stateId == 0)
		{
			model.addAttribute("dpState", new DocumentPackageState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("dpState", dpStateService.getById(stateId));
		}
		return "/manage/order/entitylist/entity/documentpackage/state/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/state/save", method=RequestMethod.POST)
    public String saveDocumentPackageState(DocumentPackageState state, ModelMap model) {
		if(state.getId() == 0)
			dpStateService.add(state);
		else
			dpStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/documentpackage/state/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/state/delete", method=RequestMethod.POST)
    public String deleteDocumentPackageState(long id) {
		if(id > 0)
			dpStateService.remove(dpStateService.getById(id));
		return "redirect:" + "/manage/order/entitylist/entity/documentpackage/state/list";
    }
	
	@RequestMapping(value = { "/manage/order/entitylist/entity/documentpackage/type/list" }, method = RequestMethod.GET)
    public String listDPTypes(ModelMap model) {
 
		List<DocumentPackageType> types = dpTypeService.list();
		model.addAttribute("types", types);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/entitylist/entity/documentpackage/type/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/type/{typeId}/save", method=RequestMethod.GET)
	public String formDPType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("dpType", new DocumentPackageType());
		}
		
		if(typeId > 0)
		{
			model.addAttribute("dpType", dpTypeService.getById(typeId));
		}
		return "/manage/order/entitylist/entity/documentpackage/type/save";
	}
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/type/save", method=RequestMethod.POST)
    public String saveDocumentPackageType(DocumentPackageType type, ModelMap model) {
		if(type.getId() == 0)
			dpTypeService.add(type);
		else
			dpTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/entitylist/entity/documentpackage/type/list";
    }
	
	@RequestMapping(value="/manage/order/entitylist/entity/documentpackage/type/delete", method=RequestMethod.POST)
    public String deleteDocumentPackageType(long id) {
		if(id > 0)
			dpTypeService.remove(dpTypeService.getById(id));
		return "redirect:" + "/manage/order/entitylist/entity/documentpackage/type/list";
    }

}
