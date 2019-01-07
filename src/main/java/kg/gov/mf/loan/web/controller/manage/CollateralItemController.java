package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collateral.*;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.repository.collateral.CollateralItemArrestFreeRepository;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.web.fetchModels.CollateralItemOwnerModel;
import kg.gov.mf.loan.web.fetchModels.ItemArrestFreeModel;
import kg.gov.mf.loan.web.fetchModels.ItemInspectionResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@Autowired
	UserService userService;

	@Autowired
	OwnerService ownerService;


	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
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
		model.addAttribute("debtor", debtorService.getById(debtorId));
		model.addAttribute("agreementId", agreementId);
		model.addAttribute("agreement", agreementService.getById(agreementId));

		CollateralItem tItem = itemService.getById(itemId);
		CollateralItemDetails tDetails = tItem.getCollateralItemDetails();
		model.addAttribute("item", tItem);
		model.addAttribute("itemDetails", tDetails);
		
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonInspections = gson.toJson(getInspectionsByItemId(itemId));
        model.addAttribute("inspections", jsonInspections);

        String jsonArrestFrees = gson.toJson(getArrestFreesByItemId(itemId));
        model.addAttribute("arrestFrees", jsonArrestFrees);

		//model.addAttribute("inspections", tItem.getCollateralItemInspectionResults());
		
		return "/manage/debtor/collateralagreement/collateralitem/view";
		
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/save", method=RequestMethod.GET)
	public String formCollateralItem(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("agreementId")Long agreementId,
			@PathVariable("itemId")Long itemId)
	{
//		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtorService.getById(debtorId));
		model.addAttribute("agreementId", agreementId);
		model.addAttribute("agreement", agreementService.getById(agreementId));
//		String jsonOwners = gson.toJson(getCollateralItemOwners());
//		model.addAttribute("owners", jsonOwners);
		
		if(itemId == 0)
		{
			CollateralItem collateralItem=new CollateralItem();
			collateralItem.setQuantityType(qTypeService.getById(Long.valueOf(1)));
			collateralItem.setQuantity(1.0);
			collateralItem.setCollateralValue(0.0);
			CollateralItemDetails collateralItemDetails=new CollateralItemDetails();
//			collateralItem.setCollateralItemDetails(collateralItemDetails);
//			collateralItemDetails.setCollateralItem(collateralItem);
			model.addAttribute("item", collateralItem);
			model.addAttribute("itemDetails", collateralItemDetails);
		}
			
		if(itemId > 0)
		{
			CollateralItem tItem = itemService.getById(itemId);
			CollateralItemDetails tDetails = itemDetailsService.getById(tItem.getCollateralItemDetails().getId());
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
			itemDetailsService.add(itemDetails);
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

        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("agreementId", agreementId);
        model.addAttribute("agreement", agreementService.getById(agreementId));
        model.addAttribute("item", itemService.getById(itemId));

		if(afId == 0)
		{
			CollateralItemArrestFree collateralItemArrestFree=new CollateralItemArrestFree();
			collateralItemArrestFree.setOnDate(new Date());
			model.addAttribute("af", collateralItemArrestFree);
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
		item.setStatus(2);
		if(af.getId() == 0)
		{
			User user = userService.findByUsername(Utils.getPrincipal());
			af.setArrestFreeBy(user.getId());
			item.setCollateralItemArrestFree(af);
			afService.add(af);
			itemService.update(item);
		}

		else{
			User user = userService.findByUsername(Utils.getPrincipal());
			af.setArrestFreeBy(user.getId());
			item.setCollateralItemArrestFree(af);
			afService.update(af);
			itemService.update(item);
		}

		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/collateralitem/{itemId}/arrestfree/{afId}/delete", method=RequestMethod.GET)
    public String deleteItemArrestFree(@PathVariable("afId")Long afId) {
		if(afId > 0) {
			afService.remove(afService.getById(afId));
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

        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("agreementId", agreementId);
        model.addAttribute("agreement", agreementService.getById(agreementId));
        model.addAttribute("item", itemService.getById(itemId));

		if(insId == 0)
		{
			CollateralItemInspectionResult collateralItemInspectionResult=new CollateralItemInspectionResult();
			collateralItemInspectionResult.setOnDate(new Date());
			collateralItemInspectionResult.setInspectionResultType(insTypeService.getById(Long.valueOf(1)));
			model.addAttribute("ins", collateralItemInspectionResult);
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

    private List<ItemInspectionResultModel> getInspectionsByItemId(long itemId)
    {
        List<ItemInspectionResultModel> result = new ArrayList<>();
        CollateralItem item = itemService.getById(itemId);
        for(CollateralItemInspectionResult temp: item.getCollateralItemInspectionResults())
        {
            ItemInspectionResultModel model = new ItemInspectionResultModel();
            model.setId(temp.getId());
            model.setOnDate(temp.getOnDate());
            model.setInspectionResultTypeId(temp.getInspectionResultType().getId());
            model.setInspectionResultTypeName(temp.getInspectionResultType().getName());
            model.setDetails(temp.getDetails());
            result.add(model);
        }
        return result;
    }

    private List<ItemArrestFreeModel> getArrestFreesByItemId(long itemId)
    {
        List<ItemArrestFreeModel> result = new ArrayList<>();
        CollateralItem item = itemService.getById(itemId);

        CollateralItemArrestFree temp = item.getCollateralItemArrestFree();
        if(temp!=null)
		{
			ItemArrestFreeModel model = new ItemArrestFreeModel();
			model.setId(temp.getId());
			model.setOnDate(temp.getOnDate());
			model.setArrestFreeBy(temp.getArrestFreeBy());
			model.setDetails(temp.getDetails());
			result.add(model);
		}

        return result;
    }

//    private List<CollateralItemOwnerModel> getCollateralItemOwners(){
//		List<CollateralItemOwnerModel> result=new ArrayList<>();
//
//		List<Owner> owners = ownerService.list();
//
//		for(Owner i :owners){
//			CollateralItemOwnerModel model=new CollateralItemOwnerModel();
//			model.setId(i.getId());
//			model.setName(i.getName());
//			result.add(model);
//		}
//
//
//
//		return result;
//	}
}
