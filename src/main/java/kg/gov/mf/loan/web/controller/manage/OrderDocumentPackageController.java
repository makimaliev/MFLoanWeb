package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.documentpackage.DocumentPackageType;
import kg.gov.mf.loan.manage.model.entity.AppliedEntity;
import kg.gov.mf.loan.manage.repository.orderdocumentpackage.OrderDocumentPackageRepository;
import kg.gov.mf.loan.web.fetchModels.OrderDocumentModel;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	@Autowired
	OrderDocumentPackageRepository orderDocumentPackageRepository;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = { "/manage/order/{orderId}/orderdocumentpackage/{oDPId}/view"})
    public String viewOrderDocumentPackage(ModelMap model, @PathVariable("orderId")Long orderId, @PathVariable("oDPId")Long oDPId) {

		OrderDocumentPackage oDP = oDPService.getById(oDPId);
        model.addAttribute("oDP", oDP);

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonDocuments = gson.toJson(getDocumentsByPackageId(oDPId));
		model.addAttribute("documents", jsonDocuments);
        
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
	
	@RequestMapping(value="/manage/order/{orderId}/orderdocumentpackage/{packageId}/delete", method=RequestMethod.GET)
    public String deleteOrderDocumentPackage(@PathVariable("orderId")Long orderId, @PathVariable("packageId")Long packageId) {
		if(packageId > 0)
			orderDocumentPackageRepository.delete(orderDocumentPackageRepository.findOne(packageId));

		return "redirect:" + "/manage/order/{orderId}/view";
    }

	private List<OrderDocumentModel> getDocumentsByPackageId(long packageId)
	{
		String baseQuery = "SELECT doc.id,\n" +
				"  doc.name,\n" +
				"  doc.orderDocumentTypeId as typeId,\n" +
				"  type.name as typeName\n" +
				"FROM orderDocument doc, orderDocumentType type\n" +
				"WHERE doc.orderDocumentTypeId = type.id\n" +
				"  AND doc.orderDocumentPackageId = " + packageId;

		Query query = entityManager.createNativeQuery(baseQuery, OrderDocumentModel.class);

		List<OrderDocumentModel> documents = query.getResultList();
		return documents;
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
