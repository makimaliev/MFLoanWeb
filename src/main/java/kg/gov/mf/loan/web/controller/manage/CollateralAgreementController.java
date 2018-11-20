package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.web.fetchModels.CollateralItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;

@Controller
public class CollateralAgreementController {
	
	@Autowired
	CollateralAgreementService agreementService;
	
	@Autowired
	DebtorService debtorService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	OrganizationService orgService;
	
	@Autowired
	OwnerService ownerService;

	@Autowired
	OwnerRepository ownerRepository;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/view"})
    public String viewAgreement(ModelMap model, @PathVariable("debtorId")Long debtorId, @PathVariable("agreementId")Long agreementId) {
		
		CollateralAgreement agreement = agreementService.getById(agreementId);
		model.addAttribute("agreement", agreement);

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonItems = gson.toJson(getItemsByAgreementId(agreementId));
		model.addAttribute("items", jsonItems);

		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtor);
		
		return "/manage/debtor/collateralagreement/view";
		
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/collateralagreement/{agreementId}/save", method=RequestMethod.GET)
	public String formCollateralAgreement(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("agreementId")Long agreementId)
	{
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtor);
		List<Loan> loans=new ArrayList<>();
		for(Loan l:debtor.getLoans()){
			if(l.getParent()==null){
				loans.add(l);
			}
		}
		model.addAttribute("tLoans", loans);
		
		if(agreementId == 0)
		{
			CollateralAgreement agreement = new CollateralAgreement();
			model.addAttribute("agreement", agreement);
			model.addAttribute("ownerText", "");
		}

		if(agreementId > 0)
		{
			CollateralAgreement agreement = agreementService.getById(agreementId);
			model.addAttribute("agreement", agreement);
			Owner owner = agreement.getOwner();
			String ownerText = "[" + owner.getId() + "] "
					+ owner.getName()
					+ " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
			model.addAttribute("ownerText", ownerText);
		}

		return "/manage/debtor/collateralagreement/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/save"}, method=RequestMethod.POST)
    public String saveCollateralAgreement(CollateralAgreement agreement, 
    		@PathVariable("debtorId")Long debtorId,
    		ModelMap model)
    {
		if(agreement.getId() == 0){
			Owner owner=ownerRepository.findOne(debtorService.getById(debtorId).getOwner().getId());
			agreement.setOwner(owner);
			agreementService.add(agreement);
		}

		else
		{
			Owner owner = ownerRepository.findOne(agreement.getOwner().getId());
			agreement.setOwner(owner);
			agreementService.update(agreement);
		}

		return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/"+agreement.getId()+"/view";
    }

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/delete"})
    public String deleteCollateralAgreement(long id, @PathVariable("debtorId")Long debtorId)
    {
		if(id > 0)
			agreementService.remove(agreementService.getById(id));
		
		return "redirect:" + "/manage/debtor/{debtorId}/view";
    }

	private List<CollateralItemModel> getItemsByAgreementId(long agreementId)
	{
		List<CollateralItemModel> result = new ArrayList<>();
		CollateralAgreement agreement = agreementService.getById(agreementId);
		for(CollateralItem item: agreement.getCollateralItems())
		{
			CollateralItemModel model = new CollateralItemModel();
			model.setId(item.getId());
			model.setName(item.getName());
			model.setDescription(item.getDescription());
			model.setItemTypeId(item.getItemType().getId());
			model.setItemTypeName(item.getItemType().getName());
			model.setQuantity(item.getQuantity());
			try{
				model.setRisk_rate(item.getRisk_rate());
			}
			catch (Exception e){
				model.setRisk_rate((0));
			}
			try{
				model.setDemand_rate(item.getDemand_rate());
			}
			catch (Exception e){
				model.setDemand_rate(Double.valueOf(0));
			}
			model.setQuantityTypeId(item.getQuantityType().getId());
			model.setQuantityTypeName(item.getQuantityType().getName());
			model.setCollateralValue(item.getCollateralValue());
			model.setEstimatedValue(item.getEstimatedValue());
			if(item.getConditionType()!=null)
            {
                model.setConditionTypeId(item.getConditionType().getId());
                model.setConditionTypeName(item.getConditionType().getName());
            }

			result.add(model);
		}

		return result;
	}
	
	/*
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/inspection/save"})
    public String saveCollateralInspection(CollateralInspection inspection, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		CollateralAgreement agreement = agreementService.getById(agreementId);
		inspection.setCollateralAgreement(agreement);
		
		if(inspection.getId() == 0)
			inspectionService.add(inspection);
		else		
			inspectionService.update(inspection);
		
		return "redirect:" + "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view#tab_0";
    }
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/inspection/delete"})
    public String deleteCollateralInspection(long id, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		if(id > 0)
			inspectionService.remove(inspectionService.getById(id));
		
		return "redirect:" + "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view#tab_0";
    }
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/af/save"})
    public String saveArrestFree(CollateralArrestFree af, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		CollateralAgreement agreement = agreementService.getById(agreementId);
		af.setCollateralAgreement(agreement);
		
		if(af.getId() == 0)
			afService.add(af);
		else
			afService.update(af);
		
		return "redirect:" + "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view#tab_1";
    }
	
	@RequestMapping(value = { "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/af/delete"})
    public String deleteCollateralArrestFree(long id, @PathVariable("collateralId")Long collateralId, @PathVariable("agreementId")Long agreementId)
    {
		if(id > 0)
			afService.remove(afService.getById(id));
		
		return "redirect:" + "/manage/collateral/{collateralId}/collateralagreement/{agreementId}/view#tab_1";
    }
	*/
}
