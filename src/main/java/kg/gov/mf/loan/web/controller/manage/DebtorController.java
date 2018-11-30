package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Position;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.CollectionProcedure;
import kg.gov.mf.loan.manage.model.collection.PhaseDetails;
import kg.gov.mf.loan.manage.model.debtor.*;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.collateral.CollateralItemReposiory;
import kg.gov.mf.loan.manage.repository.debtor.DebtorRepository;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.process.service.JobItemService;
import kg.gov.mf.loan.web.fetchModels.CollateralAgreementModel;
import kg.gov.mf.loan.web.fetchModels.CollectionProcedureModel;
import kg.gov.mf.loan.web.fetchModels.LoanModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.collection.CollectionProcedureService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.DebtorTypeService;
import kg.gov.mf.loan.manage.service.debtor.OrganizationFormService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.manage.service.debtor.WorkSectorService;
import kg.gov.mf.loan.manage.service.loan.LoanStateService;
import kg.gov.mf.loan.manage.service.loan.LoanTypeService;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.web.util.Utils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Controller
public class DebtorController {

	@Autowired
	DebtorTypeService debtorTypeService;

	@Autowired
	OrganizationFormService formService;

	@Autowired
	WorkSectorService sectorService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	OrderTermCurrencyService currService;

	@Autowired
	LoanTypeService loanTypeService;

	@Autowired
	LoanStateService loanStateService;

	@Autowired
	CreditOrderService orderService;

	@Autowired
	CollateralAgreementService agreementService;

	@Autowired
	CollectionProcedureService procService;

	@Autowired
	PersonService personService;

	@Autowired
	OrganizationService orgService;

	@Autowired
	OwnerService ownerService;

	@Autowired
	DebtorRepository debtorRepository;

	@Autowired
	OwnerRepository ownerRepository;

    @Autowired
    JobItemService jobItemService;

	@Autowired
	DistrictService districtService;

	@Autowired
	WorkSectorService workSectorService;

	@Autowired
	LoanRepository loanRepository;

	@Autowired
	CollateralItemReposiory collateralItemReposiory;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	PositionService positionService;

	@Autowired
	InformationService informationService;

	@Autowired
    StaffService staffService;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = { "/manage/debtor/{debtorId}/view"})
	public String viewOrder(ModelMap model, @PathVariable("debtorId")Long debtorId) {

		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtor", debtor);
		String isPerson="true";
		if (debtor.getOwner().getOwnerType().name().equals("ORGANIZATION")){

			isPerson="false";
			Organization organization = this.organizationService.findById(debtor.getOwner().getEntityId());
            String staff="";
			for (Department department:organization.getDepartment())
			{
				staff= staffService.findAllByDepartment(department).get(0).getName();
				break;
			}

			model.addAttribute("organization", organization);
			model.addAttribute("staff", staff);
//			model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, organization.getId()));
		}
		else{
			Person person = this.personService.findById(debtor.getOwner().getId());

			model.addAttribute("person", person);
//			model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, person.getId()));
		}

		model.addAttribute("isPerson",isPerson);
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonLoans = gson.toJson(getLoansByDebtorId(debtorId));
		model.addAttribute("loans", jsonLoans);

		String jsonAgreements = gson.toJson(getCollAgreementsByDebtorId(debtorId));
		model.addAttribute("agreements", jsonAgreements);

        String jsonProcs = gson.toJson(getProcsByDebtorId(debtorId));
        model.addAttribute("procs", jsonProcs);

		List<CreditOrder> orders = orderService.list();
		model.addAttribute("orders", orders);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/view";
	}

	@RequestMapping(value = {"/manage/debtor/list1" }, method = RequestMethod.GET)
	public String listDebtors(ModelMap model) {


		model.addAttribute("districts", districtService.findAll());
		model.addAttribute("sectors", workSectorService.list());

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/list";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/save", method=RequestMethod.GET)
	public String formDebtor(Model model, @PathVariable("debtorId")Long debtorId)
	{
		List<DebtorType> types = debtorTypeService.list();
		model.addAttribute("types", types);

		List<WorkSector> sectors = sectorService.list();
		model.addAttribute("sectors", sectors);

		if(debtorId == 0)
		{
			Debtor debtor = new Debtor();
			model.addAttribute("debtor", debtor);
			model.addAttribute("ownerText", "");
		}

		if(debtorId > 0)
		{
			Debtor debtor = debtorService.getById(debtorId);
			Owner owner = debtor.getOwner();
			model.addAttribute("debtor", debtor);
			String ownerText = "[" + owner.getId() + "] "
					+ owner.getName()
					+ " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
			model.addAttribute("ownerText", ownerText);
		}

		return "/manage/debtor/save";
	}

	@RequestMapping(value="/manage/debtor/save", method=RequestMethod.POST)
	public String saveDebtor(Debtor debtor)
	{
		if(debtor.getId() == 0){
			Owner owner = ownerRepository.findOne(debtor.getOwner().getId());
			debtor.setOwner(owner);
			debtor.setName(owner.getName());
			if(owner.getOwnerType().equals(OwnerType.ORGANIZATION))
				debtor.setOrgForm(formService.getById(1L));
			else
				debtor.setOrgForm(formService.getById(2L));
			debtorService.add(debtor);
		}

		else
		{
			Owner owner = ownerRepository.findOne(debtor.getOwner().getId());
			debtor.setOwner(owner);
			debtor.setName(owner.getName());
			if(owner.getOwnerType().equals(OwnerType.ORGANIZATION))
				debtor.setOrgForm(formService.getById(1L));
			else
				debtor.setOrgForm(formService.getById(2L));
			debtorService.update(debtor);
		}

		return "redirect:" + "/manage/debtor/" + debtor.getId() + "/view";
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/delete", method=RequestMethod.GET)
	public String deleteDebtor(@PathVariable("debtorId")Long debtorId) {
		if(debtorId > 0)
			debtorRepository.delete(debtorRepository.findOne(debtorId));
		return "redirect:" + "/manage/debtor/list";
	}

    @RequestMapping(value="/manage/debtor/{debtorId}/generatesummary", method=RequestMethod.GET)
    public String formGenerateSummary(ModelMap model,
                                          @PathVariable("debtorId")Long debtorId)
    {
        Debtor debtor = debtorService.getById(debtorId);
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("tLoans", debtor.getLoans());
        GenerateSummaryForm gsForm = new GenerateSummaryForm();
        model.addAttribute("gsForm", gsForm);

        return "/manage/debtor/generatesummary";
    }

    @RequestMapping(value="/manage/debtor/{debtorId}/generatesummary", method=RequestMethod.POST)
    public String executeGenerateSummary(GenerateSummaryForm gsForm)
    {
        List<Loan> selectedLoans = gsForm.getLoans();
        Date date = gsForm.getDate();

        for (Loan loan: selectedLoans
             ) {
            this.jobItemService.runManualCalculateProcedure(loan.getId(), date);
        }

        return "redirect:" + "/manage/debtor/{debtorId}/view";
    }

	//BEGIN - TYPE
	@RequestMapping(value = { "/manage/debtor/type/list" }, method = RequestMethod.GET)
	public String listDebtorTypes(ModelMap model) {

		List<DebtorType> types = debtorTypeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/type/list";
	}

	@RequestMapping(value="/manage/debtor/type/{typeId}/save", method=RequestMethod.GET)
	public String formDebtorType(ModelMap model, @PathVariable("typeId")Long typeId)
	{
		if(typeId == 0)
		{
			model.addAttribute("debtorType", new DebtorType());
		}

		if(typeId > 0)
		{
			model.addAttribute("debtorType", debtorTypeService.getById(typeId));
		}
		return "/manage/debtor/type/save";
	}

	@RequestMapping(value="/manage/debtor/type/save", method=RequestMethod.POST)
	public String saveDebtorType(DebtorType type,  ModelMap model) {
		if(type.getId() == 0)
			debtorTypeService.add(type);
		else
			debtorTypeService.update(type);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/type/list";
	}

	@RequestMapping(value="/manage/debtor/type/delete", method=RequestMethod.POST)
	public String deleteDebtorType(long id) {
		if(id > 0)
			debtorTypeService.remove(debtorTypeService.getById(id));
		return "redirect:" + "/manage/debtor/type/list";
	}
	//END - TYPE

	//BEGIN - ORGFORM
	@RequestMapping(value = { "/manage/debtor/orgform/list" }, method = RequestMethod.GET)
	public String listOrgForm(ModelMap model) {

		List<OrganizationForm> orgForms = formService.list();
		model.addAttribute("orgForms", orgForms);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/orgform/list";
	}

	@RequestMapping(value="/manage/debtor/orgform/{formId}/save", method=RequestMethod.GET)
	public String formOrgForm(ModelMap model, @PathVariable("formId")Long formId)
	{
		if(formId == 0)
		{
			model.addAttribute("orgForm", new OrganizationForm());
		}

		if(formId > 0)
		{
			model.addAttribute("orgForm", formService.getById(formId));
		}
		return "/manage/debtor/orgform/save";
	}

	@RequestMapping(value="/manage/debtor/orgform/save", method=RequestMethod.POST)
	public String saveOrgForm(OrganizationForm form,  ModelMap model) {
		if(form.getId() == 0)
			formService.add(form);
		else
			formService.update(form);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/orgform/list";
	}

	@RequestMapping(value="/manage/debtor/orgform/delete", method=RequestMethod.POST)
	public String deleteOgrForm(long id) {
		if(id > 0)
			formService.remove(formService.getById(id));
		return "redirect:" + "/manage/debtor/orgform/list";
	}
	//END - ORGFORM

	//BEGIN - WORK SECTOR
	@RequestMapping(value = { "/manage/debtor/worksector/list" }, method = RequestMethod.GET)
	public String listWorkSector(ModelMap model) {

		List<WorkSector> workSectors = sectorService.list();
		model.addAttribute("workSectors", workSectors);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/worksector/list";
	}

	@RequestMapping(value="/manage/debtor/worksector/{sectorId}/save", method=RequestMethod.GET)
	public String formWorkSector(ModelMap model, @PathVariable("sectorId")Long sectorId)
	{
		if(sectorId == 0)
		{
			model.addAttribute("workSector", new WorkSector());
		}

		if(sectorId > 0)
		{
			model.addAttribute("workSector", sectorService.getById(sectorId));
		}
		return "/manage/debtor/worksector/save";
	}

	@RequestMapping(value="/manage/debtor/worksector/save", method=RequestMethod.POST)
	public String saveWorkSector(WorkSector sector,  ModelMap model) {
		if(sector.getId() == 0)
			sectorService.add(sector);
		else
			sectorService.update(sector);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/worksector/list";
	}

	@RequestMapping(value="/manage/debtor/worksector/delete", method=RequestMethod.POST)
	public String deleteWorkSector(long id) {
		if(id > 0)
			sectorService.remove(sectorService.getById(id));
		return "redirect:" + "/manage/debtor/worksector/list";
	}
	//END - WORK SECTOR

	private List<LoanModel> getLoansByDebtorId(long debtorId)
	{
		String baseQuery = "SELECT loan.id, loan.regNumber, loan.regDate, loan.amount, loan.currencyId, currency.name as currencyName,\n" +
				"  loan.loanTypeId, type.name as loanTypeName, loan.loanStateId, state.name as loanStateName,\n" +
				"  loan.supervisorId, IFNULL(loan.parent_id, 0) as parentLoanId, loan.creditOrderId\n" +
				"FROM loan loan, orderTermCurrency currency, loanType type, loanState state\n" +
				"WHERE loan.currencyId = currency.id\n" +
				"  AND loan.loanTypeId = type.id\n" +
				"  AND loan.loanStateId = state.id\n" +
				"  AND loan.debtorId =" + debtorId + "\n" +
				"  AND  ISNULL(loan.parent_id) \n" +
                "ORDER BY  loan.regDate DESC";

		Query query = entityManager.createNativeQuery(baseQuery, LoanModel.class);

		List<LoanModel> loans = query.getResultList();
		return loans;
	}

	private List<CollateralAgreementModel> getCollAgreementsByDebtorId(long debtorId)
	{
	    Map<Long, CollateralAgreementModel> models = new HashMap<>();
	    Debtor debtor = debtorService.getById(debtorId);
	    for (Loan loan: debtor.getLoans())
        {
            Set<CollateralAgreement> agreements = loan.getCollateralAgreements();
            for(CollateralAgreement agreement: agreements)
            {
                List<CollateralItem> items = collateralItemReposiory.findByCollateralAgreementId(agreement.getId());
                for(CollateralItem item: items)
                {
                    CollateralAgreementModel model = new CollateralAgreementModel();
                    model.setId(agreement.getId());
                    model.setAgreementNumber(agreement.getAgreementNumber());
                    model.setAgreementDate(agreement.getAgreementDate());
                    model.setCollateralOfficeRegNumber(agreement.getCollateralOfficeRegNumber());
                    model.setCollateralOfficeRegDate(agreement.getCollateralOfficeRegDate());
                    model.setNotaryOfficeRegNumber(agreement.getNotaryOfficeRegNumber());
                    model.setNotaryOfficeRegDate(agreement.getNotaryOfficeRegDate());
                    model.setArrestRegNumber(agreement.getArrestRegNumber());
                    model.setArrestRegDate(agreement.getArrestRegDate());
                    model.setOwnerId(agreement.getOwner().getId());
                    model.setItemId(item.getId());
                    model.setItemName(item.getName());
                    model.setItemDescription(item.getDescription());
                    model.setItemTypeId(item.getItemType().getId());
                    model.setItemTypeName(item.getItemType().getName());
                    model.setQuantity(item.getQuantity());
                    model.setQuantityTypeId(item.getQuantityType().getId());
                    model.setQuantityTypeName(item.getQuantityType().getName());
                    model.setCollateralValue(item.getCollateralValue());

                    if(!models.containsKey(model.getItemId()))
                        models.put(model.getItemId(), model);
                }

            }
        }

        List<CollateralAgreementModel> result = new ArrayList<>();
        for(CollateralAgreementModel model: models.values())
            result.add(model);

        Collections.sort(result);
        return result;

	}

	private List<CollectionProcedureModel> getProcsByDebtorId(long debtorId)
	{

		Map<Long, CollectionProcedureModel> models = new HashMap<>();
		Debtor debtor = debtorService.getById(debtorId);
		Set<CollectionProcedure> procs = new HashSet<>();
		for (Loan loan: debtor.getLoans()
				) {

			Set<CollectionPhase> phases = loan.getCollectionPhases();
			for (CollectionPhase phase: phases
					) {
				CollectionProcedure proc = phase.getCollectionProcedure();

				CollectionProcedureModel model = new CollectionProcedureModel();
				model.setId(proc.getId());
				model.setProcedureStatusId(proc.getProcedureStatus().getId());
				model.setProcedureStatusName(proc.getProcedureStatus().getName());
				model.setPhaseId(phase.getId());
				model.setPhaseTypeId(phase.getPhaseType().getId());
				model.setPhaseTypeName(phase.getPhaseType().getName());
				model.setStartDate(phase.getStartDate());
				double totalStartAmount = 0.0;
				for(PhaseDetails details: phase.getPhaseDetails())
				{
                    if(details.getStartTotalAmount()!=null)
					totalStartAmount += details.getStartTotalAmount();
				}
				model.setStartTotalAmount(totalStartAmount);

				model.setPhaseStatusId(phase.getPhaseStatus().getId());
				model.setPhaseStatusName(phase.getPhaseStatus().getName());

				model.setCloseDate(phase.getCloseDate());
				double totalClosetAmount = 0.0;
				for(PhaseDetails details: phase.getPhaseDetails())
				{
				    if(details.getCloseTotalAmount()!=null)
					    totalClosetAmount += details.getCloseTotalAmount();
				}
				model.setCloseTotalAmount(totalClosetAmount);

				if(!models.containsKey(model.getPhaseId()))
					models.put(model.getPhaseId(), model);
			}
		}

        List<CollectionProcedureModel> result = new ArrayList<>();
        for(CollectionProcedureModel model: models.values())
            result.add(model);

        Collections.sort(result);
        return result;
	}

}
