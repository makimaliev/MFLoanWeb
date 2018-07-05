package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackageType;
import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.web.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

	@RequestMapping(value = {"/manage/order/orderdocumentpackage/list" }, method = RequestMethod.GET)
	public String listODPs(@RequestParam("pageSize") Optional<Integer> pageSize,
							   @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<OrderDocumentPackage> oDPs = oDPService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = oDPService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("oDPs", oDPs);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/order/orderdocumentpackage/list";
	}
	
	@RequestMapping(value = { "/manage/order/{orderId}/orderdocumentpackage/{oDPId}/view"})
    public String viewOrderDocumentPackage(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("oDPId")Long oDPId) {

		OrderDocumentPackage oDP = oDPService.getById(oDPId);
        model.addAttribute("oDP", oDP);
        
        model.addAttribute("documents", oDP.getOrderDocuments());
        
        model.addAttribute("orderId", orderId);
		model.addAttribute("order", orderService.getById(orderId));
        
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
		model.addAttribute("order", orderService.getById(orderId));
		List<DocumentPackageType> types = dpTypeService.list();
		model.addAttribute("types", types);

		return "/manage/order/orderdocumentpackage/save";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/save", method=RequestMethod.POST)
	public String saveOrderDocumentPackage(OrderDocumentPackage oDP, 
			@PathVariable("orderId")Long orderId, ModelMap model)
	{
		CreditOrder creditOrder = orderService.getById(orderId);
		oDP.setCreditOrder(creditOrder);
		
		if(oDP.getId() == 0)
		{
			oDPService.add(oDP);
			//add this document package to all entities under this credit
			//addToEntities(creditOrder, oDP);
		}
			
		else
			oDPService.update(oDP);
			//updateInEntities(creditOrder, oDP);
			
		return "redirect:" + "/manage/order/"+ orderId + "/orderdocumentpackage/" + oDP.getId()+ "/view";
	}
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/delete", method=RequestMethod.POST)
    public String deleteOrderDocumentPackage(long id, @PathVariable("orderId")Long orderId) {
		if(id > 0) {
			oDPService.remove(oDPService.getById(id));
			//deleteInEntities(id);
		}
			
		return "redirect:" + "/manage/order/{orderId}/view#documentPackages";
    }
	
	/*
	private void addToEntities(CreditOrder creditOrder, OrderDocumentPackage oDP) {
		Set<AppliedEntityList> lists = creditOrder.getAppliedEntityLists();
		for (AppliedEntityList list : lists) {
			Set<AppliedEntity> entities = list.getAppliedEntities();
			for (AppliedEntity entity : entities) {
				DocumentPackage dp = new DocumentPackage();
				dp.setName(oDP.getName());
				dp.setCompletedDate(new Date(0));
				dp.setApprovedDate(new Date(0));
				dp.setCompletedRatio(0.0);
				dp.setApprovedRatio(0.0);
				dp.setRegisteredRatio(0.0);
				dp.setDocumentPackageState(getDummyState());
				dp.setDocumentPackageType(getDummyType());
				dp.setAppliedEntity(entity);
				dpService.add(dp);
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
		DocumentPackageState result = dpStateService.getByName("Dummy State");
		if(result == null) {
			result = new DocumentPackageState();
			result.setVersion(1);
			result.setName("Dummy State");
			dpStateService.add(result);
		}
		
		return result;
	}
	
	private DocumentPackageType getDummyType() {
		DocumentPackageType result = dpTypeService.getByName("Dummy Type");
		if(result == null) {
			result = new DocumentPackageType();
			result.setVersion(1);
			result.setName("Dummy Type");
			dpTypeService.add(result);
		}
		
		return result;
	}
	*/
}
