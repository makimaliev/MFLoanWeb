package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackage;
import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.manage.model.entity.AppliedEntityState;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentRegisteredBy;
import kg.gov.mf.loan.manage.model.entitydocument.EntityDocumentState;
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
	DocumentPackageService dpService;
	
	@Autowired
	EntityDocumentStateService edStateService;
	
	@Autowired
	EntityDocumentRegisteredByService edRBService;
	
	@Autowired
	EntityDocumentService edService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
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
			model.addAttribute("doc", odService.findById(docId));
		}
		model.addAttribute("orderId", orderId);
		model.addAttribute("dpId", dpId);
		List<OrderDocumentType> types = oDTypeService.findAll();
        model.addAttribute("types", types);
			
		return "/manage/order/orderdocumentpackage/orderdocument/save";
	}

	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/{oDPId}/orderdocument/save", method=RequestMethod.POST)
	public String saveOrderDocument(
			OrderDocument doc, 
			long typeId, 
			@PathVariable("orderId")Long orderId, 
			@PathVariable("oDPId")Long oDPId, 
			ModelMap model)
	{
		
		OrderDocumentPackage oDP = oDPService.findById(oDPId);
		
		if(doc != null && doc.getId() == 0)
		{
			OrderDocument newOD = new OrderDocument(doc.getName(), oDTypeService.findById(typeId));
			newOD.setOrderDocumentPackage(oDP);
			odService.save(newOD);
			
			//add new document under entity packages
			//addToEntities(doc, oDPId);
		}
			
		if(doc != null && doc.getId() > 0)
		{
			doc.setOrderDocumentType(oDTypeService.findById(typeId));
			odService.update(doc);
			//updateInEntities(doc, oDPId);
		}
			
			return "redirect:" + "/manage/order/{orderId}/orderdocumentpackage/{oDPId}/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/{oDPId}/orderdocument/delete", method=RequestMethod.POST)
    public String deleteOrderDocument(long id, @PathVariable("orderId")Long orderId, @PathVariable("oDPId")Long oDPId) {
		if(id > 0) {
			odService.deleteById(id);
			//TODO: delete from entities as well
		}
			
		return "redirect:" + "/manage/order/{orderId}/orderdocumentpackage/{oDPId}/view";
    }
	
	@RequestMapping(value = { "/manage/order/orderdocumentpackage/orderdocument/type/list" }, method = RequestMethod.GET)
    public String listODTypes(ModelMap model) {
 
		List<OrderDocumentType> types = oDTypeService.findAll();
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
			model.addAttribute("odType", oDTypeService.findById(typeId));
		}
		return "/manage/order/orderdocumentpackage/orderdocument/type/save";
	}
	
	@RequestMapping(value="/manage/order/orderdocumentpackage/orderdocument/type/save", method=RequestMethod.POST)
    public String saveOrderDocumentType(OrderDocumentType type, ModelMap model) {
		if(type != null && type.getId() == 0)
			oDTypeService.save(new OrderDocumentType(type.getName()));
		
		if(type != null && type.getId() > 0)
			oDTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/order/orderdocumentpackage/orderdocument/type/list";
    }
	
	@RequestMapping(value="/manage/order/orderdocumentpackage/orderdocument/type/delete", method=RequestMethod.POST)
    public String deleteOrderDocumentType(long id) {
		if(id > 0)
			oDTypeService.deleteById(id);
		return "redirect:" + "/manage/order/orderdocumentpackage/orderdocument/type/list";
    }
	
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
	
}
