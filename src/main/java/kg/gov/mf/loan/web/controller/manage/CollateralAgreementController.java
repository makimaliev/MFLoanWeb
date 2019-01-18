package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.collateral.AdditionalAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.service.collateral.AdditionalAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.web.fetchModels.AdditionalAgreementModel;
import kg.gov.mf.loan.web.fetchModels.CollateralItemModel;
import kg.gov.mf.loan.web.fetchModels.LoanModel;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;

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

	@Autowired
	AdditionalAgreementService additionalAgreementService;

	@Autowired
	CollateralItemService collateralItemService;

	@Autowired
	EntityManager entityManager;

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
		model.addAttribute("agreementId", agreement.getId());

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonItems = gson.toJson(getItemsByAgreementId(agreementId));
		model.addAttribute("items", jsonItems);

		String jsonAdditionalAgreements=gson.toJson(getAdditionalsByAgreementId(agreementId));
		model.addAttribute("additionalAgreements",jsonAdditionalAgreements);

		String jsonLoans=gson.toJson(getLoansByLoanId(agreementId));
		model.addAttribute("jsonLoans",jsonLoans);

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
			agreement.setAgreementDate(new Date());
			agreement.setArrestRegDate(new Date());
			agreement.setCollateralOfficeRegDate(new Date());
			agreement.setNotaryOfficeRegDate(new Date());
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
		for(CollateralItem item1: agreement.getCollateralItems())
		{
			CollateralItem item=collateralItemService.getById(item1.getId());
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

	private List<AdditionalAgreementModel> getAdditionalsByAgreementId(long agreementId)
	{
		List<AdditionalAgreementModel> result = new ArrayList<>();
		CollateralAgreement agreement = agreementService.getById(agreementId);
		for(AdditionalAgreement item: agreement.getAdditionalAgreements())
		{
			AdditionalAgreement additionalAgreement=additionalAgreementService.getById(item.getId());
			AdditionalAgreementModel model = new AdditionalAgreementModel();
			model.setId(additionalAgreement.getId());
			model.setDescription(additionalAgreement.getDescription());
			model.setDate(additionalAgreement.getDate());
			model.setNumber(additionalAgreement.getNumber());
			result.add(model);
		}

		return result;
	}

	private List<LoanModel> getLoansByLoanId(long agreementId)
	{
		String baseQuery = "SELECT loan.id, loan.loan_class_id, loan.regNumber, loan.regDate, loan.amount, loan.currencyId, currency.name as currencyName,\n" +
				"  loan.loanTypeId, type.name as loanTypeName, loan.loanStateId, state.name as loanStateName,\n" +
				"  loan.supervisorId, IFNULL(loan.parent_id, 0) as parentLoanId, loan.creditOrderId\n" +
				"FROM loan loan, orderTermCurrency currency,mfloan.loanCollateralAgreement lCA, loanType type, loanState state\n" +
				"WHERE loan.currencyId = currency.id\n" +
				"  AND loan.loanTypeId = type.id\n" +
				"  AND loan.loanStateId = state.id\n" +
				"  AND loan.id= lCA.loanId\n" +
				"  AND lCA.collateralAgreementId =" + agreementId+ "\n" +
				"  AND  ISNULL(loan.parent_id) \n" +
				"ORDER BY  loan.regDate DESC";

		Query query = entityManager.createNativeQuery(baseQuery, LoanModel.class);

		List<LoanModel> loans = query.getResultList();
		return loans;
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
