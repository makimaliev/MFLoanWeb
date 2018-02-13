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

import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collateral.CollateralItemArrestFree;
import kg.gov.mf.loan.manage.model.collateral.CollateralItemDetails;
import kg.gov.mf.loan.manage.model.collateral.CollateralItemInspectionResult;
import kg.gov.mf.loan.manage.model.collateral.ConditionType;
import kg.gov.mf.loan.manage.model.collateral.InspectionResultType;
import kg.gov.mf.loan.manage.model.collateral.ItemType;
import kg.gov.mf.loan.manage.model.collateral.QuantityType;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemArrestFreeService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemDetailsService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemInspectionResultService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.manage.service.collateral.ConditionTypeService;
import kg.gov.mf.loan.manage.service.collateral.InspectionResultTypeService;
import kg.gov.mf.loan.manage.service.collateral.ItemTypeService;
import kg.gov.mf.loan.manage.service.collateral.QuantityTypeService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.web.util.Utils;

@Controller
public class CollateralItemController {
	
	@Autowired
	CollateralAgreementService agreementService;
	
	@Autowired
	CollateralItemService itemService;
	
	@Autowired
	DebtorService debtorService;
	
	@Autowired
	ItemTypeService iTypeService;
	
	@Autowired
	QuantityTypeService qTypeService;
	
	@Autowired
	ConditionTypeService cTypeService;
	
	@Autowired
	CollateralItemDetailsService itemDetailsService;
	
	@Autowired
	CollateralItemArrestFreeService afService;

	@Autowired
	CollateralItemInspectionResultService insService;
	
	@Autowired
	InspectionResultTypeService insTypeService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/collateralitem/list"})
    public String listCollateralItems(ModelMap model) {
		
		List<CollateralItem> items = itemService.list(); 
		model.addAttribute("items", items);
		
		return "/manage/collateralitem/list";
		
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/view"})
    public String viewCollateralItem(ModelMap model, 
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("agreementId")Long agreementId,
    		@PathVariable("itemId")Long itemId) {
		
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("agreementId", agreementId);
		
		CollateralItem tItem = itemService.getById(itemId);
		CollateralItemDetails tDetails = tItem.getCollateralItemDetails();
		model.addAttribute("item", tItem);
		model.addAttribute("itemDetails", tDetails);
		
		model.addAttribute("aFs", tItem.getCollateralItemArrestFree());
		model.addAttribute("inpections", tItem.getCollateralItemInspectionResults());
		
		return "/manage/debtor/collateralagreement/collateralitem/view";
		
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/save", method=RequestMethod.GET)
	public String formCollateralItem(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("agreementId")Long agreementId,
			@PathVariable("itemId")Long itemId)
	{
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("agreementId", agreementId);
		
		if(itemId == 0)
		{
			model.addAttribute("item", new CollateralItem());
			model.addAttribute("itemDetails", new CollateralItemDetails());
		}
			
		if(itemId > 0)
		{
			CollateralItem tItem = itemService.getById(itemId);
			CollateralItemDetails tDetails = tItem.getCollateralItemDetails();
			model.addAttribute("item", tItem);
			model.addAttribute("itemDetails", tDetails);
		}
		
		model.addAttribute("iTypes", iTypeService.list());
		model.addAttribute("qTypes", qTypeService.list());
		model.addAttribute("cTypes", cTypeService.list());
		
		return "/manage/debtor/collateralagreement/collateralitem/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/save"}, method=RequestMethod.POST)
    public String saveCollateralItem(CollateralItem item, 
    		CollateralItemDetails itemDetails,
    		@PathVariable("debtorId")Long debtorId,
    		@PathVariable("agreementId")Long agreementId,
    		ModelMap model)
    {
		CollateralAgreement agreement = agreementService.getById(agreementId);
		item.setCollateralAgreement(agreement);
		
		if(item.getId() == 0)
		{
			item.setCollateralItemDetails(itemDetails);
			itemDetails.setCollateralItem(item);
			itemService.add(item);
		}
		else
		{
			item.setCollateralItemDetails(itemDetails);
			itemDetails.setCollateralItem(item);
			itemService.update(item);
		}
			
		
		return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/view";
    }

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/delete"}, method=RequestMethod.POST)
    public String deleteCollateralItem(long id, @PathVariable("debtorId")Long debtorId, @PathVariable("agreementId")Long agreementId)
    {
		if(id > 0)
			itemService.remove(itemService.getById(id));
		
		return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/arrestfree/{afId}/save", method=RequestMethod.GET)
	public String formItemArrestFree(ModelMap model, 
			@PathVariable("debtorId")Long debtorId,
			@PathVariable("agreementId")Long agreementId,
			@PathVariable("itemId")Long itemId,
			@PathVariable("afId")Long afId)
	{
		if(afId == 0)
		{
			model.addAttribute("af", new CollateralItemArrestFree());
		}

		if(afId > 0)
		{
			model.addAttribute("af", afService.getById(afId));
		}
		return "/manage/debtor/collateralagreement/collateralitem/arrestfree/save";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/arrestfree/save", method=RequestMethod.POST)
    public String saveItemArrestFree(CollateralItemArrestFree af,  
    		ModelMap model,
    		@PathVariable("debtorId")Long debtorId,
			@PathVariable("agreementId")Long agreementId,
			@PathVariable("itemId")Long itemId) {
		
		CollateralItem item = itemService.getById(itemId);
		item.setCollateralItemArrestFree(null);
		item.setCollateralItemArrestFree(af);
		af.setCollateralItem(item);
		
		if(af.getId() == 0)
			afService.add(af);
		else
			afService.update(af);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/arrestfree/delete", method=RequestMethod.POST)
    public String deleteItemArrestFree(long id, @PathVariable("itemId")Long itemId) {
		if(id > 0) {
			afService.remove(afService.getById(id));
			CollateralItem item = itemService.getById(itemId);
			item.setCollateralItemArrestFree(null);
		}
			
        return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/insresult/{insId}/save", method=RequestMethod.GET)
	public String formItemInspectionResult(ModelMap model, 
			@PathVariable("debtorId")Long debtorId,
			@PathVariable("agreementId")Long agreementId,
			@PathVariable("itemId")Long itemId,
			@PathVariable("insId")Long insId)
	{
		if(insId == 0)
		{
			model.addAttribute("ins", new CollateralItemInspectionResult());
		}

		if(insId > 0)
		{
			model.addAttribute("ins", insService.getById(insId));
		}
		
		model.addAttribute("types", insTypeService.list());
		
		return "/manage/debtor/collateralagreement/collateralitem/insresult/save";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/insresult/save", method=RequestMethod.POST)
    public String saveItemInspectionResult(CollateralItemInspectionResult ins,  
    		ModelMap model,
    		@PathVariable("debtorId")Long debtorId,
			@PathVariable("agreementId")Long agreementId,
			@PathVariable("itemId")Long itemId) {
		
		CollateralItem item = itemService.getById(itemId);
		ins.setCollateralItem(item);
		
		if(ins.getId() == 0)
			insService.add(ins);
		else
			insService.update(ins);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/insresult/delete", method=RequestMethod.POST)
    public String deleteItemInspectionResult(long id) {
		if(id > 0)
			insService.remove(insService.getById(id));
        return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/view";
    }
	
	@RequestMapping(value = { "/manage/debtor/collateralagreement/collateralitem/insresult/resulttype/list" }, method = RequestMethod.GET)
	public String listInspectionResultTypes(ModelMap model) {

		List<InspectionResultType> types = insTypeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collateralagreement/collateralitem/insresult/resulttype/list";
	}

	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/insresult/resulttype/{typeId}/save", method=RequestMethod.GET)
	public String formInspectionResultType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new InspectionResultType());
		}

		if(typeId > 0)
		{
			model.addAttribute("type", insTypeService.getById(typeId));
		}
		return "/manage/debtor/collateralagreement/collateralitem/insresult/resulttype/save";
	}

	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/insresult/resulttype/save", method=RequestMethod.POST)
    public String saveInspectionResultType(InspectionResultType type,  ModelMap model) {
		if(type.getId() == 0)
			insTypeService.add(type);
		else
			insTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collateralagreement/collateralitem/insresult/resulttype/list";
    }
	
	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/insresult/resulttype/delete", method=RequestMethod.POST)
    public String deleteInspectionResultType(long id) {
		if(id > 0)
			insTypeService.remove(insTypeService.getById(id));
        return "redirect:" + "/manage/debtor/collateralagreement/collateralitem/insresult/resulttype/list";
    }
	
	//BEGIN - ITEM TYPE
	@RequestMapping(value = { "/manage/debtor/collateralagreement/collateralitem/itemtype/list" }, method = RequestMethod.GET)
	public String listItemTypes(ModelMap model) {

		List<ItemType> types = iTypeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collateralagreement/collateralitem/itemtype/list";
	}

	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/itemtype/{typeId}/save", method=RequestMethod.GET)
	public String formItemType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new ItemType());
		}

		if(typeId > 0)
		{
			model.addAttribute("type", iTypeService.getById(typeId));
		}
		return "/manage/debtor/collateralagreement/collateralitem/itemtype/save";
	}

	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/itemtype/save", method=RequestMethod.POST)
    public String saveItemType(ItemType type,  ModelMap model) {
		if(type.getId() == 0)
			iTypeService.add(type);
		else
			iTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collateralagreement/collateralitem/itemtype/list";
    }
	
	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/itemtype/delete", method=RequestMethod.POST)
    public String deleteItemType(long id) {
		if(id > 0)
			iTypeService.remove(iTypeService.getById(id));
        return "redirect:" + "/manage/debtor/collateralagreement/collateralitem/itemtype/list";
    }
    //END - ITEM TYPE
	
	//BEGIN - Q TYPE
	@RequestMapping(value = { "/manage/debtor/collateralagreement/collateralitem/quantitytype/list" }, method = RequestMethod.GET)
	public String listQTypes(ModelMap model) {

		List<QuantityType> types = qTypeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collateralagreement/collateralitem/quantitytype/list";
	}

	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/quantitytype/{typeId}/save", method=RequestMethod.GET)
	public String formQType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new QuantityType());
		}

		if(typeId > 0)
		{
			model.addAttribute("type", qTypeService.getById(typeId));
		}
		return "/manage/debtor/collateralagreement/collateralitem/quantitytype/save";
	}

	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/quantitytype/save", method=RequestMethod.POST)
    public String saveQType(QuantityType type,  ModelMap model) {
		if(type.getId() == 0)
			qTypeService.add(type);
		else
			qTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collateralagreement/collateralitem/quantitytype/list";
    }
	
	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/quantitytype/delete", method=RequestMethod.POST)
    public String deleteQType(long id) {
		if(id > 0)
			qTypeService.remove(qTypeService.getById(id));
        return "redirect:" + "/manage/debtor/collateralagreement/collateralitem/quantitytype/list";
    }
    //END - Q TYPE
	
	//BEGIN - C TYPE
	@RequestMapping(value = { "/manage/debtor/collateralagreement/collateralitem/conditiontype/list" }, method = RequestMethod.GET)
	public String listCTypes(ModelMap model) {

		List<ConditionType> types = cTypeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/collateralagreement/collateralitem/conditiontype/list";
	}

	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/conditiontype/{typeId}/save", method=RequestMethod.GET)
	public String formCType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("type", new ConditionType());
		}

		if(typeId > 0)
		{
			model.addAttribute("type", cTypeService.getById(typeId));
		}
		return "/manage/debtor/collateralagreement/collateralitem/conditiontype/save";
	}

	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/conditiontype/save", method=RequestMethod.POST)
    public String saveCType(ConditionType type,  ModelMap model) {
		if(type.getId() == 0)
			cTypeService.add(type);
		else
			cTypeService.update(type);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/collateralagreement/collateralitem/conditiontype/list";
    }
	
	@RequestMapping(value="/manage/debtor/collateralagreement/collateralitem/conditiontype/delete", method=RequestMethod.POST)
    public String deleteCType(long id) {
		if(id > 0)
			cTypeService.remove(cTypeService.getById(id));
        return "redirect:" + "/manage/debtor/collateralagreement/collateralitem/conditiontype/list";
    }
    //END - C TYPE
	
}