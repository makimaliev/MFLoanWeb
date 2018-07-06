package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.web.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.orderdocument.OrderDocument;
import kg.gov.mf.loan.manage.model.orderdocument.OrderDocumentType;
import kg.gov.mf.loan.manage.model.orderdocumentpackage.OrderDocumentPackage;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentRegisteredByService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentService;
import kg.gov.mf.loan.manage.service.entitydocument.EntityDocumentStateService;
import kg.gov.mf.loan.manage.service.orderdocument.OrderDocumentService;
import kg.gov.mf.loan.manage.service.orderdocument.OrderDocumentTypeService;
import kg.gov.mf.loan.manage.service.orderdocumentpackage.OrderDocumentPackageService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class OrderDocumentController {
	
	@Autowired
	OrderDocumentPackageService oDPService;
	
	@Autowired
	OrderDocumentTypeService oDTypeService;
	
	@Autowired
	OrderDocumentService odService;
	
	@Autowired
	OrderDocumentPackageService orderDocumentPackageService;
	
	@Autowired
	EntityDocumentStateService edStateService;
	
	@Autowired
	EntityDocumentRegisteredByService edRBService;
	
	@Autowired
	EntityDocumentService edService;

	@Autowired
	CreditOrderService orderService;

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

	@RequestMapping(value = {"/manage/order/orderdocumentpackage/orderdocument/list" }, method = RequestMethod.GET)
	public String listEntities(@RequestParam("pageSize") Optional<Integer> pageSize,
							   @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<OrderDocument> oDs = odService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = odService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("oDs", oDs);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/order/orderdocumentpackage/orderdocument/list";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/{dpId}/orderdocument/{docId}/save", method=RequestMethod.GET)
	public String formeAppliedEntityList(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("dpId")Long dpId, @PathVariable("docId")Long docId)
	{
		if(docId == 0)
		{
			model.addAttribute("doc", new OrderDocument());
		}
			
		
		if(docId > 0)
		{
			model.addAttribute("doc", odService.getById(docId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
		model.addAttribute("dpId", dpId);
		model.addAttribute("dp", orderDocumentPackageService.getById(dpId));
		List<OrderDocumentType> types = oDTypeService.list();
        model.addAttribute("types", types);
			
		return "/manage/order/orderdocumentpackage/orderdocument/save";
	}

	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/{oDPId}/orderdocument/save", method=RequestMethod.POST)
	public String saveOrderDocument(
			OrderDocument doc, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("oDPId")Long oDPId, 
			ModelMap model)
	{
		
		OrderDocumentPackage oDP = oDPService.getById(oDPId);
		doc.setOrderDocumentPackage(oDP);
		
		if(doc.getId() == 0)
		{
			odService.add(doc);
			//add new document under entity packages
			//addToEntities(doc, oDPId);
		}
		else
		{
			odService.update(doc);
			//updateInEntities(doc, oDPId);
		}
			
			return "redirect:" + "/manage/order/{orderId}/orderdocumentpackage/{oDPId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/{oDPId}/orderdocument/delete", method=RequestMethod.GET)
    public String deleteOrderDocument(long id, @PathVariable("orderId")Long orderId, @PathVariable("oDPId")Long oDPId) {
		if(id > 0) {
			odService.remove(odService.getById(id));
			//TODO: delete from entities as well
		}
			
		return "redirect:" + "/manage/order/{orderId}/orderdocumentpackage/{oDPId}/view";
    }
	
	@RequestMapping(value = { "/manage/order/orderdocumentpackage/orderdocument/type/list" }, method = RequestMethod.GET)
    public String listODTypes(ModelMap model) {
 
		List<OrderDocumentType> types = oDTypeService.list();
		model.addAttribute("odTypes", types);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderdocumentpackage/orderdocument/type/list";
    }
	
	@RequestMapping(value="/manage/order/orderdocumentpackage/orderdocument/type/{typeId}/save", method=RequestMethod.GET)
	public String formODType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("odType", new OrderDocumentType());
		}
		
		if(typeId > 0)
		{
			model.addAttribute("odType", oDTypeService.getById(typeId));
		}
		return "/manage/order/orderdocumentpackage/orderdocument/type/save";
	}
	
	@RequestMapping(value="/manage/order/orderdocumentpackage/orderdocument/type/save", method=RequestMethod.POST)
    public String saveOrderDocumentType(OrderDocumentType type, ModelMap model) {
		if(type.getId() == 0)
			oDTypeService.add(type);
		else
			oDTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderdocumentpackage/orderdocument/type/list";
    }
	
	@RequestMapping(value="/manage/order/orderdocumentpackage/orderdocument/type/delete", method=RequestMethod.POST)
    public String deleteOrderDocumentType(long id) {
		if(id > 0)
			oDTypeService.remove(oDTypeService.getById(id));
		return "redirect:" + "/manage/order/orderdocumentpackage/orderdocument/type/list";
    }
	
	/*
	private void addToEntities(OrderDocument doc, Long oDPId) {
		
		List<DocumentPackage> dps = dpService.findByOrderDocumentPackageId(oDPId);
		for (DocumentPackage documentPackage : dps) {
			EntityDocument newDoc = new EntityDocument(
					doc.getName(), 
					0L, 
					new Date(0), 
					"", 
					0L, 
					new Date(0), 
					"", 
					"123", 
					new Date(0), 
					"", 
					getDummyRB(), 
					getDummyState());
			newDoc.setDocumentPackage(documentPackage);
			edService.save(newDoc);
		}
		
	}
	
	private void updateInEntities(OrderDocument doc, Long oDPId) {
		List<DocumentPackage> dps = dpService.findByOrderDocumentPackageId(oDPId);
		for (DocumentPackage documentPackage : dps) {
			Set<EntityDocument> eds = documentPackage.getEntityDocument();
			for (EntityDocument entityDocument : eds) {
				entityDocument.setName(doc.getName());
				edService.update(entityDocument);
			}
		}
	}
	
	private EntityDocumentState getDummyState() {
		EntityDocumentState result = edStateService.findByName("Dummy State");
		if(result == null) {
			result = new EntityDocumentState("Dummy State");
			edStateService.save(result);
		}
		
		return result;
	}
	
	private EntityDocumentRegisteredBy getDummyRB() {
		EntityDocumentRegisteredBy result = edRBService.findByName("Dummy RB");
		if(result == null) {
			result = new EntityDocumentRegisteredBy("Dummy RB");
			edRBService.save(result);
		}
		
		return result;
	}
	*/
}
