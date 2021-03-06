package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collateral.AdditionalAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collateral.CollateralItemArrestFree;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.service.collateral.AdditionalAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemArrestFreeService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.web.fetchModels.AdditionalAgreementModel;
import kg.gov.mf.loan.web.fetchModels.CollateralItemModel;
import kg.gov.mf.loan.web.fetchModels.LoanModel;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CollateralAgreementController {

	//region services
	
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

	@Autowired
	LoanService loanService;

	@Autowired
	UserService userService;

	@Autowired
	InformationService informationService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	SystemFileService systemFileService;

	@Autowired
	CollateralItemArrestFreeService collateralItemArrestFreeService;

	//endregion

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


		String jsonFiles= gson.toJson(getSystemFilesByPaymentId(agreementId));
		model.addAttribute("files", jsonFiles);

		String jsonAdditionalAgreements=gson.toJson(getAdditionalsByAgreementId(agreementId));
		model.addAttribute("additionalAgreements",jsonAdditionalAgreements);

		String jsonLoans=gson.toJson(getLoansByLoanId(agreementId));
		model.addAttribute("jsonLoans",jsonLoans);

		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtor);

		String createdByStr=null;
		String modifiedByStr=null;

		if(agreement.getAuCreatedBy()!=null){
			if(agreement.getAuCreatedBy().equals("admin")){
				createdByStr="Система";
			}
			else{
				User createdByUser=userService.findByUsername(agreement.getAuCreatedBy());
				Staff createdByStaff=createdByUser.getStaff();
				createdByStr=createdByStaff.getName();
			}
		}
		if(agreement.getAuLastModifiedBy()!=null){
			if(agreement.getAuLastModifiedBy().equals("admin")){
				modifiedByStr="Система";
			}
			else{
				User lastModifiedByUser=userService.findByUsername(agreement.getAuLastModifiedBy());
				Staff lastModifiedByStaff=lastModifiedByUser.getStaff();
				modifiedByStr=lastModifiedByStaff.getName();
			}
		}
		model.addAttribute("createdBy",createdByStr);
		model.addAttribute("modifiedBy",modifiedByStr);
		
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
			ArrayList<Long> loanIds=new ArrayList<>();
			CollateralAgreement agreement = new CollateralAgreement();
//			agreement.setAgreementDate();
//			agreement.setArrestRegDate(new Date());
//			agreement.setCollateralOfficeRegDate(new Date());
//			agreement.setNotaryOfficeRegDate(new Date());
			model.addAttribute("agreement", agreement);
			model.addAttribute("loanIds", loanIds);
			model.addAttribute("ownerText", "");
		}

		if(agreementId > 0)
		{
			CollateralAgreement agreement = agreementService.getById(agreementId);
			ArrayList<Long> loanIds=new ArrayList<>();
			for(Loan loan:agreement.getLoans()){
				loanIds.add(loan.getId());
			}
			model.addAttribute("loanIds",loanIds);
			model.addAttribute("agreement", agreement);
			Owner owner = agreement.getNotary();

			String ownerText = "";

			if(owner!=null)
			{
				ownerText = "[" + owner.getId() + "] "
						+ owner.getName()
						+ " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
			}


			model.addAttribute("ownerText", ownerText);
		}

		return "/manage/debtor/collateralagreement/save";
	}

    @RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/collateralagreement/{agreementId}/save", method=RequestMethod.GET)
    public String formCollateralAgreement(ModelMap model,
                                          @PathVariable("debtorId")Long debtorId,
                                          @PathVariable("loanId")Long loanId,
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
            Loan loan=loanService.getById(loanId);
            ArrayList<Long> loanIds=new ArrayList<>();
            CollateralAgreement agreement = new CollateralAgreement();
            Set<Loan> loanSet=new HashSet<>();
            loanSet.add(loan);
            loanIds.add(loanId);
            agreement.setLoans(loanSet);
//			agreement.setAgreementDate();
//			agreement.setArrestRegDate(new Date());
//			agreement.setCollateralOfficeRegDate(new Date());
//			agreement.setNotaryOfficeRegDate(new Date());
            model.addAttribute("agreement", agreement);
            model.addAttribute("loanIds", loanIds);
            model.addAttribute("ownerText", "");
        }

        if(agreementId > 0)
        {
            CollateralAgreement agreement = agreementService.getById(agreementId);
            ArrayList<Long> loanIds=new ArrayList<>();
            for(Loan loan:agreement.getLoans()){
                loanIds.add(loan.getId());
            }
            model.addAttribute("loanIds",loanIds);
            model.addAttribute("agreement", agreement);
            Owner owner = agreement.getNotary();

            String ownerText = "";

            if(owner!=null)
            {
                ownerText = "[" + owner.getId() + "] "
                        + owner.getName()
                        + " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
            }


            model.addAttribute("ownerText", ownerText);
        }

        return "/manage/debtor/collateralagreement/save";
    }
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/save"}, method=RequestMethod.POST)
    public String saveCollateralAgreement(CollateralAgreement agreement,
										  @PathVariable("debtorId")Long debtorId, String[] loanses)
    {
    	if(agreement.getNotaryOfficeRegNumber()==null||agreement.getOwner()==null|| agreement.getArrestRegNumber()==null||
				loanses.length==0){
			return "redirect:" + "/manage/debtor/{debtorId}/view";
		}
    	else {
			Set<Loan> loanSet = new HashSet<>();
			for (String l : loanses) {
				loanSet.add(loanService.getById(Long.valueOf(l)));
			}
			if (agreement.getId() == 0) {
				Owner owner = ownerRepository.findOne(debtorService.getById(debtorId).getOwner().getId());
				agreement.setOwner(owner);
				agreement.setAgreementDate(agreement.getNotaryOfficeRegDate());
				agreement.setAgreementNumber(agreement.getNotaryOfficeRegNumber());
				agreement.setLoans(loanSet);
				if(agreement.getCollateralOfficeRegNumber()==null)
					agreement.setCollateralOfficeRegNumber("");
				agreementService.add(agreement);
				return "redirect:/manage/debtor/{debtorId}/collateralagreement/"+agreement.getId()+"/collateralitem/0/save";
			} else {
				Owner owner = ownerRepository.findOne(agreement.getOwner().getId());
				agreement.setOwner(owner);
				agreement.setLoans(loanSet);
				agreement.setAgreementNumber(agreement.getNotaryOfficeRegNumber());
				agreement.setAgreementDate(agreement.getNotaryOfficeRegDate());
				agreementService.update(agreement);
			}
			return "redirect:" + "/manage/debtor/{debtorId}/collateralagreement/" + agreement.getId() + "/view";
		}

    }

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/delete"})
    public String deleteCollateralAgreement(long id, @PathVariable("debtorId")Long debtorId)
    {
		if(id > 0)
			agreementService.remove(agreementService.getById(id));
		
		return "redirect:" + "/manage/debtor/{debtorId}/view";
    }

    @RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/arrestFree"})
	public String arrestFree(ModelMap model,@PathVariable("debtorId") Long debtorId,@PathVariable("agreementId") Long agreementId){

		Debtor debtor=debtorService.getById(debtorId);
		CollateralAgreement collateralAgreement=agreementService.getById(agreementId);

		model.addAttribute("debtorId",debtorId);
		model.addAttribute("debtor",debtor);

		model.addAttribute("agreementId",agreementId);
		model.addAttribute("agreement",collateralAgreement);

		CollateralItemArrestFree collateralItemArrestFree=new CollateralItemArrestFree();
		collateralItemArrestFree.setOnDate(new Date());
		model.addAttribute("af", collateralItemArrestFree);


		return "/manage/debtor/collateralagreement/arrestfree";
	}

	@RequestMapping(value = { "/manage/debtor/{debtorId}/collateralagreement/{agreementId}/arrestFree/save"},method = RequestMethod.POST)
	public String arrestFreeSave(@PathVariable("debtorId") Long debtorId,@PathVariable("agreementId") Long agreementId,CollateralItemArrestFree collateralItemArrestFree){

		CollateralAgreement collateralAgreement=agreementService.getById(agreementId);
//		List<CollateralItem> collateralItems=new
		User user = userService.findByUsername(Utils.getPrincipal());
		collateralItemArrestFree.setArrestFreeBy(user.getId());
		for(CollateralItem item1:collateralAgreement.getCollateralItems()){
			CollateralItem item=collateralItemService.getById(item1.getId());
			if(item.getStatus()==1){
				item.setCollateralItemArrestFree(collateralItemArrestFree);
				item.setStatus(2);
				collateralItemArrestFreeService.add(collateralItemArrestFree);
				collateralItemService.update(item);
			}
		}

		return "redirect: /manage/debtor/{debtorId}/collateralagreement/{agreementId}/view";
	}

//    add information form
	@RequestMapping("/manage/debtor/{debtorId}/agreement/{agreementId}/addInformation")
	public String getAddInformationForm(Model model, @PathVariable("debtorId") Long debtorId,@PathVariable("agreementId") Long agreementId){

		String ids="";
		ids=ids+"debtorId:"+debtorId;

		CollateralAgreement agreement=agreementService.getById(agreementId);
		model.addAttribute("object",agreement);
		model.addAttribute("systemObjectTypeId",6);

		model.addAttribute("attachment",new Attachment());
		model.addAttribute("ids",ids);

		return "/manage/debtor/loan/payment/addInformationForm";

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
			model.setStatus(item.getStatus());
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
				"  loan.supervisorId, IFNULL(loan.parent_id, 0) as parentLoanId, loan.creditOrderId, 0.0 as remainder\n" +
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

		for (LoanModel loanModel : loans){
			loanModel.setRemainder(getRemainderOfLoan(loanModel.getId()));
        }
		return loans;
	}

	private List<SystemFileModel> getSystemFilesByPaymentId(Long agreementId){

		List<SystemFileModel> list=new ArrayList<>();
		List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(6,agreementId);
		for (Information information1:informations){
			Information information=informationService.findById(information1.getId());
			Set<Attachment> attachments= information.getAttachment();
			for (Attachment attachment1:attachments){
				Attachment attachment=attachmentService.findById(attachment1.getId());
				for (SystemFile systemFile1:attachment.getSystemFile()){
					SystemFile systemFile=systemFileService.findById(systemFile1.getId());
					SystemFileModel systemFileModel=new SystemFileModel();
					systemFileModel.setAttachment_id(attachment.getId());
					systemFileModel.setSys_name(systemFile.getName());
					systemFileModel.setSystem_file_id(systemFile.getId());
					systemFileModel.setAttachment_name(attachment.getName());
					systemFileModel.setPath(systemFile.getPath());
					list.add(systemFileModel);
				}
			}
		}

		return list;
	}

    private Double getRemainderOfLoan(long loanId){


        String baseQuery = "select ls.loanAmount-ls.totalDisbursed\n" +
				"from loanSummary ls where ls.loanId="+loanId+" order by ls.onDate desc limit 1";
        Query query = entityManager.createNativeQuery(baseQuery);

		Double value = 0.0;
		try{
			value = (Double) query.getResultList().get(0);
		}
		catch (Exception e){

		}

        return value;
    }



//  ******************************************************************************************************************
//	REST
//  ******************************************************************************************************************

//	search agreements by agreementNumber
	@GetMapping("/api/agreements/search")
	@ResponseBody
	public String[] searchByAgreementNum(@RequestParam(value = "q") String q){
		String searchStr = "select *\n" +
				"from collateralAgreement where agreementNumber like '%"+q+"%'";

		Query query = entityManager.createNativeQuery(searchStr,CollateralAgreement.class);
		List<CollateralAgreement> agreementList = query.getResultList();

		String[] result = new String[agreementList.size()];
		int i=0;
		for(CollateralAgreement agreement : agreementList){
			result[i++] = "[" + agreement.getId() + "] "
					+ agreement.getAgreementNumber();
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
