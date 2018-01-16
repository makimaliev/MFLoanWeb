package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.model.orderdocumentpackage.OrderDocumentPackage;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageStateService;
import kg.gov.mf.loan.manage.service.documentpackage.DocumentPackageTypeService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.orderdocument.OrderDocumentTypeService;
import kg.gov.mf.loan.manage.service.orderdocumentpackage.OrderDocumentPackageService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class OrderDocumentPackageController {
	
	@Autowired
	CreditOrderService orderService;
	
	@Autowired
	OrderDocumentPackageService oDPService;
	
	@Autowired
	OrderDocumentTypeService typeService;
	
	@Autowired
	DocumentPackageService dpService;
	
	@Autowired
	DocumentPackageStateService dpStateService;
	
	@Autowired
	DocumentPackageTypeService dpTypeService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/orderdocumentpackage/{oDPId}/view"})
    public String viewOrderDocumentPackage(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("oDPId")Long oDPId) {

		OrderDocumentPackage oDP = oDPService.getById(oDPId);
        model.addAttribute("oDP", oDP);
        
        model.addAttribute("documents", oDP.getOrderDocuments());
        
        model.addAttribute("orderId", orderId);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/order/orderdocumentpackage/view";
    }
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/{dpId}/save", method=RequestMethod.GET)
	public String formOrderDocumentPackage(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("dpId")Long dpId)
	{
		if(dpId == 0)
		{
			model.addAttribute("documentPackage", new OrderDocumentPackage());
		}
			
		
		if(dpId > 0)
		{
			model.addAttribute("documentPackage", oDPService.getById(dpId));
		}
		model.addAttribute("orderId", orderId);
		return "/manage/order/orderdocumentpackage/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/save", method=RequestMethod.POST)
	public String saveOrderDocumentPackage(OrderDocumentPackage oDP, 
			@PathVariable("orderId")Long orderId, ModelMap model)
	{
		CreditOrder creditOrder = orderService.getById(orderId);
		oDP.setCreditOrder(creditOrder);
		
		if(oDP.getId() == null || oDP.getId() == 0)
			oDPService.add(oDP);
			//add this document package to all entities under this credit
			//addToEntities(creditOrder, oDP, newODP);
		else
			oDPService.update(oDP);
			//updateInEntities(creditOrder, oDP);
			
		return "redirect:" + "/manage/order/{orderId}/view#tab_1";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/delete", method=RequestMethod.POST)
    public String deleteOrderDocumentPackage(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0) {
			oDPService.remove(oDPService.getById(id));
			//deleteInEntities(id);
		}
			
		return "redirect:" + "/manage/order/{orderId}/view#tab_1";
    }
	
	/*
	private void addToEntities(CreditOrder creditOrder, OrderDocumentPackage oDP, OrderDocumentPackage newODP) {
		Set<AppliedEntityList> lists = creditOrder.getAppliedEntityList();
		for (AppliedEntityList list : lists) {
			Set<AppliedEntity> entities = list.getAppliedEntity();
			for (AppliedEntity entity : entities) {
				DocumentPackage dp = new DocumentPackage(oDP.getName(), 
						new Date(0), 
						new Date(0), 
						0.0, 
						0.0, 
						0.0, 
						getDummyState(), 
						getDummyType());
				dp.setOrderDocumentPackageId(newODP.getId());
				dp.setAppliedEntity(entity);
				dpService.save(dp);
			}
		}
	}
	
	private void updateInEntities(CreditOrder creditOrder, OrderDocumentPackage oDP) {
		List<DocumentPackage> dps = dpService.findByOrderDocumentPackageId(oDP.getId());
		for (DocumentPackage documentPackage : dps) {
			documentPackage.setName(oDP.getName());
			dpService.update(documentPackage);
		}
	}
	
	private void deleteInEntities(long id) {
		List<DocumentPackage> dps = dpService.findByOrderDocumentPackageId(id);
		for (DocumentPackage documentPackage : dps) {
			dpService.deleteById(documentPackage.getId());
		}
	}
	
	private DocumentPackageState getDummyState() {
		DocumentPackageState result = dpStateService.findByName("Dummy State");
		if(result == null) {
			result = new DocumentPackageState("Dummy State");
			dpStateService.save(result);
		}
		
		return result;
	}
	
	private DocumentPackageType getDummyType() {
		DocumentPackageType result = dpTypeService.findByName("Dummy Type");
		if(result == null) {
			result = new DocumentPackageType("Dummy Type");
			dpTypeService.save(result);
		}
		
		return result;
	}
	*/
}
