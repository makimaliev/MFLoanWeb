package kg.gov.mf.loan.web.controller.manage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collateral.CollateralItem;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.CollectionProcedure;
import kg.gov.mf.loan.manage.model.collection.PhaseDetails;
import kg.gov.mf.loan.manage.model.debtor.*;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.LoanSummaryAct;
import kg.gov.mf.loan.manage.model.loan.Payment;
import kg.gov.mf.loan.manage.model.loan.PaymentSchedule;
import kg.gov.mf.loan.manage.model.process.LoanDetailedSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummary;
import kg.gov.mf.loan.manage.model.process.LoanSummaryType;
import kg.gov.mf.loan.manage.repository.collateral.CollateralItemReposiory;
import kg.gov.mf.loan.manage.repository.debtor.DebtorRepository;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.service.collateral.CollateralAgreementService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.manage.service.collection.CollectionPhaseService;
import kg.gov.mf.loan.manage.service.collection.CollectionProcedureService;
import kg.gov.mf.loan.manage.service.collection.PhaseDetailsService;
import kg.gov.mf.loan.manage.service.debtor.*;
import kg.gov.mf.loan.manage.service.loan.*;
import kg.gov.mf.loan.manage.service.order.CreditOrderService;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.process.LoanSummaryService;
import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.utils.CalculationTool;
import kg.gov.mf.loan.process.service.JobItemService;
import kg.gov.mf.loan.web.fetchModels.*;
import kg.gov.mf.loan.web.util.Utils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

	@Autowired
	CollectionPhaseService collectionPhaseService;

	@Autowired
	CollectionProcedureService collectionProcedureService;

	@Autowired
	CollateralAgreementService collateralAgreementService;

	@Autowired
	CollateralItemService collateralItemService;

	@Autowired
	LoanService loanService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	LoanSummaryService loanSummaryService;

	@Autowired
	CurrencyRateService currencyRateService;

	@Autowired
	LoanViewService loanViewService;

	@Autowired
	PhaseDetailsService phaseDetailsService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	LoanSummaryActService loanSummaryActService;

	@Autowired
	LoanSummaryActStateService loanSummaryActStateService;

	@Autowired
	UserService userService;

	@Autowired
	DebtorGroupService debtorGroupService;

	@Autowired
	DebtorSubGroupService debtorSubGroupService;

	/**
	 * The entity manager.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = {"/manage/debtor/{debtorId}/view"})
	public String viewOrder(ModelMap model, @PathVariable("debtorId") Long debtorId) {

		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtor", debtor);
		String isPerson = "true";
		if (debtor.getOwner().getOwnerType().name().equals("ORGANIZATION")) {

			isPerson = "false";
			Organization organization = this.organizationService.findById(debtor.getOwner().getEntityId());
//            String staff="";
			List<String> staffWithPositions = new ArrayList<>();
			List<Staff> staffList=new ArrayList<>();
			for (Department department1 : organization.getDepartment()) {
				Department department = departmentService.findById(department1.getId());
				int counter = 0;
				for (Staff staff1 : staffService.findAllByDepartment(department)) {
					Staff staff = staffService.findById(staff1.getId());
					staffList.add(staff);
					String positionName = staff.getPosition().getName();
					String staffName = staff.getName();
					if (staffName.length() > 3) {
						staffWithPositions.add(positionName + " : " + staffName);
					}
					counter++;
					if (counter == 5) {
						break;
					}
				}
				break;

			}

			model.addAttribute("organization", organization);
			model.addAttribute("staffs", staffList);
//			model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, organization.getId()));
		} else {
			Person person = this.personService.findById(debtor.getOwner().getEntityId());

			model.addAttribute("person", person);
//			model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, person.getId()));
		}

		model.addAttribute("isPerson", isPerson);

//		model.addAttribute("loanSummaryActList", this.loanSummaryActService.getLoanSummaryActByDebtor(debtor));
//		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
//		String jsonLoans = gson.toJson(getLoansByDebtorId(debtorId));
//		model.addAttribute("loans", jsonLoans);

//		String jsonAgreements = gson.toJson(getCollAgreementsByDebtorId(debtorId));
//		model.addAttribute("agreements", jsonAgreements);
//
//        String jsonProcs = gson.toJson(getProcsByDebtorId(debtorId));
//        model.addAttribute("procs", jsonProcs);

//		List<CreditOrder> orders = orderService.list();
//		model.addAttribute("orders", orders);

		model.addAttribute("loggedinuser", Utils.getPrincipal());

		SimpleDateFormat sd=new SimpleDateFormat("dd.MM.yyyy");
		model.addAttribute("today",sd.format(new Date()));
		model.addAttribute("type",debtorTypeService.getById(debtor.getDebtorType().getId()));
		model.addAttribute("types",debtorTypeService.list());

		String createdByStr=null;
		String modifiedByStr=null;

		if(debtor.getAuCreatedBy()!=null){
			if(debtor.getAuCreatedBy().equals("admin")){
				createdByStr="Система";
			}
			else{
				User createdByUser=userService.findByUsername(debtor.getAuCreatedBy());
				Staff createdByStaff=createdByUser.getStaff();
				createdByStr=createdByStaff.getName();
			}
		}
		if(debtor.getAuLastModifiedBy()!=null){
			if(debtor.getAuLastModifiedBy().equals("admin")){
				modifiedByStr="Система";
			}
			else{
				User lastModifiedByUser=userService.findByUsername(debtor.getAuLastModifiedBy());
				Staff lastModifiedByStaff=lastModifiedByUser.getStaff();
				modifiedByStr=lastModifiedByStaff.getName();
			}
		}
		model.addAttribute("createdBy",createdByStr);
		model.addAttribute("modifiedBy",modifiedByStr);

//		checking if there is any unregistered loanSummaryAct
		String isThereUnregisteredLoanSummaryAct = "select if(COUNT(*)>0,'true','false')\n" +
				"from loanSummaryAct where debtorId="+debtorId+" and (reg_number is null or reg_number = '-') and YEAR(onDate) > 2019";
		Query query = entityManager.createNativeQuery(isThereUnregisteredLoanSummaryAct);
		model.addAttribute("isThereUnregisteredLoanSummaryAct", query.getSingleResult().equals("true"));

//        checking if there is any unregistered collectionPhases with type id 1
        String isThereUnregisteredCollectionPhaseWithTypeId1 = "select if(COUNT(*)>0,'true','false')\n" +
                "from collection_phase_view where v_debtor_id="+debtorId+" and v_cph_phaseTypeId =1 and (v_cph_doc_number is null or v_cph_doc_number='-')  and YEAR(v_cph_startDate) > 2019";
        Query query1 = entityManager.createNativeQuery(isThereUnregisteredCollectionPhaseWithTypeId1);
        model.addAttribute("isThereUnregisteredCollectionPhaseWithTypeId1", query1.getSingleResult().equals("true"));

		return "/manage/debtor/view";
	}

	@RequestMapping(value = {"/manage/debtor/list1"}, method = RequestMethod.GET)
	public String listDebtors(ModelMap model) {


		model.addAttribute("districts", districtService.findAll());
		model.addAttribute("sectors", workSectorService.list());

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/list";
	}

	@RequestMapping(value = "/manage/debtor/{debtorId}/save", method = RequestMethod.GET)
	public String formDebtor(Model model, @PathVariable("debtorId") Long debtorId) {
		List<DebtorType> types = debtorTypeService.list();
		model.addAttribute("types", types);

		List<WorkSector> sectors = sectorService.list();
		model.addAttribute("sectors", sectors);

		if (debtorId == 0) {
			Debtor debtor = new Debtor();
			model.addAttribute("debtor", debtor);
			model.addAttribute("ownerText", "");
		}

		if (debtorId > 0) {
			Debtor debtor = debtorService.getById(debtorId);
			Owner owner = debtor.getOwner();
			model.addAttribute("debtor", debtor);
			String ownerText = "[" + owner.getId() + "] "
					+ owner.getName()
					+ " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION) ? "Организация" : "Физ. лицо") + ")";
			model.addAttribute("ownerText", ownerText);
		}

		return "/manage/debtor/save";
	}

	@RequestMapping(value = "/manage/debtor/save", method = RequestMethod.POST)
	public String saveDebtor(Debtor debtor) {
		if (debtor.getId() == 0) {
			Owner owner = ownerRepository.findOne(debtor.getOwner().getId());
			debtor.setOwner(owner);
			debtor.setAddress_id(owner.getAddress().getId());
			debtor.setName(owner.getName());
			debtor.setDebtorType(debtorTypeService.getById(1L));
			if (owner.getOwnerType().equals(OwnerType.ORGANIZATION))
				debtor.setOrgForm(formService.getById(1L));
			else
				debtor.setOrgForm(formService.getById(2L));

			DebtorGroup debtorGroup= debtorGroupService.getById(1L);
			DebtorSubGroup debtorSubGroup= debtorSubGroupService.getById(1L);
			debtor.setDebtorGroup(debtorGroup);
			debtor.setDebtorSubGroup(debtorSubGroup);
			debtorService.add(debtor);
		} else {
			Owner owner = ownerRepository.findOne(debtor.getOwner().getId());
			Debtor oldDebtor=debtorService.getById(debtor.getId());
			oldDebtor.setOwner(owner);
			oldDebtor.setName(owner.getName());
			if (owner.getOwnerType().equals(OwnerType.ORGANIZATION))
				oldDebtor.setOrgForm(formService.getById(1L));
			else
				oldDebtor.setOrgForm(formService.getById(2L));

			WorkSector workSector = workSectorService.getById(debtor.getWorkSector().getId());
			oldDebtor.setWorkSector(workSector);
			debtorService.update(oldDebtor);
		}

		return "redirect:" + "/manage/debtor/" + debtor.getId() + "/view";
	}

	@RequestMapping(value = "/manage/debtor/{debtorId}/delete", method = RequestMethod.GET)
	public String deleteDebtor(@PathVariable("debtorId") Long debtorId) {
		if (debtorId > 0)
			debtorRepository.delete(debtorRepository.findOne(debtorId));
		return "redirect:" + "/manage/debtor/list";
	}

	@RequestMapping(value = "/manage/debtor/{debtorId}/generatesummary", method = RequestMethod.GET)
	public String formGenerateSummary(ModelMap model,
									  @PathVariable("debtorId") Long debtorId) {
		Debtor debtor = debtorService.getById(debtorId);
		model.addAttribute("debtorId", debtorId);
		model.addAttribute("tLoans", debtor.getLoans());
		GenerateSummaryForm gsForm = new GenerateSummaryForm();
		model.addAttribute("gsForm", gsForm);

		return "/manage/debtor/generatesummary";
	}

	@RequestMapping(value = "/manage/debtor/{debtorId}/generatesummary", method = RequestMethod.POST)
	public String executeGenerateSummary(GenerateSummaryForm gsForm) {
		List<Loan> selectedLoans = gsForm.getLoans();
		Date date = gsForm.getDate();

		for (Loan loan : selectedLoans
		) {
			this.jobItemService.runManualCalculateProcedure(loan.getId(), date);
		}

		return "redirect:" + "/manage/debtor/{debtorId}/view";
	}

	//BEGIN - TYPE
	@RequestMapping(value = {"/manage/debtor/type/list"}, method = RequestMethod.GET)
	public String listDebtorTypes(ModelMap model) {

		List<DebtorType> types = debtorTypeService.list();
		model.addAttribute("types", types);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/type/list";
	}

	@RequestMapping(value = "/manage/debtor/type/{typeId}/save", method = RequestMethod.GET)
	public String formDebtorType(ModelMap model, @PathVariable("typeId") Long typeId) {
		if (typeId == 0) {
			model.addAttribute("debtorType", new DebtorType());
		}

		if (typeId > 0) {
			model.addAttribute("debtorType", debtorTypeService.getById(typeId));
		}
		return "/manage/debtor/type/save";
	}

	@RequestMapping(value = "/manage/debtor/type/save", method = RequestMethod.POST)
	public String saveDebtorType(DebtorType type, ModelMap model) {
		if (type.getId() == 0)
			debtorTypeService.add(type);
		else
			debtorTypeService.update(type);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/type/list";
	}

	@RequestMapping(value = "/manage/debtor/type/delete", method = RequestMethod.POST)
	public String deleteDebtorType(long id) {
		if (id > 0)
			debtorTypeService.remove(debtorTypeService.getById(id));
		return "redirect:" + "/manage/debtor/type/list";
	}
	//END - TYPE

	//BEGIN - ORGFORM
	@RequestMapping(value = {"/manage/debtor/orgform/list"}, method = RequestMethod.GET)
	public String listOrgForm(ModelMap model) {

		List<OrganizationForm> orgForms = formService.list();
		model.addAttribute("orgForms", orgForms);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/orgform/list";
	}

	@RequestMapping(value = "/manage/debtor/orgform/{formId}/save", method = RequestMethod.GET)
	public String formOrgForm(ModelMap model, @PathVariable("formId") Long formId) {
		if (formId == 0) {
			model.addAttribute("orgForm", new OrganizationForm());
		}

		if (formId > 0) {
			model.addAttribute("orgForm", formService.getById(formId));
		}
		return "/manage/debtor/orgform/save";
	}

	@RequestMapping(value = "/manage/debtor/orgform/save", method = RequestMethod.POST)
	public String saveOrgForm(OrganizationForm form, ModelMap model) {
		if (form.getId() == 0)
			formService.add(form);
		else
			formService.update(form);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/orgform/list";
	}

	@RequestMapping(value = "/manage/debtor/orgform/delete", method = RequestMethod.POST)
	public String deleteOgrForm(long id) {
		if (id > 0)
			formService.remove(formService.getById(id));
		return "redirect:" + "/manage/debtor/orgform/list";
	}
	//END - ORGFORM

	//BEGIN - WORK SECTOR
	@RequestMapping(value = {"/manage/debtor/worksector/list"}, method = RequestMethod.GET)
	public String listWorkSector(ModelMap model) {

		List<WorkSector> workSectors = sectorService.list();
		model.addAttribute("workSectors", workSectors);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/manage/debtor/worksector/list";
	}

	@RequestMapping(value = "/manage/debtor/worksector/{sectorId}/save", method = RequestMethod.GET)
	public String formWorkSector(ModelMap model, @PathVariable("sectorId") Long sectorId) {
		if (sectorId == 0) {
			model.addAttribute("workSector", new WorkSector());
		}

		if (sectorId > 0) {
			model.addAttribute("workSector", sectorService.getById(sectorId));
		}
		return "/manage/debtor/worksector/save";
	}

	@RequestMapping(value = "/manage/debtor/worksector/save", method = RequestMethod.POST)
	public String saveWorkSector(WorkSector sector, ModelMap model) {
		if (sector.getId() == 0)
			sectorService.add(sector);
		else
			sectorService.update(sector);

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/worksector/list";
	}

	@RequestMapping(value = "/manage/debtor/worksector/delete", method = RequestMethod.POST)
	public String deleteWorkSector(long id) {
		if (id > 0)
			sectorService.remove(sectorService.getById(id));
		return "redirect:" + "/manage/debtor/worksector/list";
	}

	@RequestMapping(value = "/manage/debtor/{debtorId}/loan/summary/view", method = RequestMethod.GET)
	public String getSelectedLoanSummary(ModelMap model, @PathVariable("debtorId") Long debtorId, String date, String name) throws ParseException
	{

		Set<LoanSummary> loanSummarySet = new HashSet<>();
		LoanSummaryModel1 sumLoanSummary = new LoanSummaryModel1();
		HashMap<LoanSummaryModel1, LoanSummaryModel1> summaries = new HashMap<>();

		Date newDate = new Date();

		for (String id : name.split("-")) {
			if (!id.equals("")) {
				Loan loan = loanService.getById(Long.valueOf(id));

				Date srokDate = null;

				for (PaymentSchedule schedule : loan.getPaymentSchedules()) {
					if (schedule.getPrincipalPayment() > 0) {
						if (srokDate == null) {
							srokDate = schedule.getExpectedDate();
						} else {
							if (schedule.getExpectedDate() != null)
								if (schedule.getExpectedDate().after(srokDate)) ;
							{
								srokDate = schedule.getExpectedDate();
							}
						}

					}

				}

				if (srokDate == null) srokDate = loan.getRegDate();

				newDate = new SimpleDateFormat("dd.MM.yyyy", new Locale("ru", "RU")).parse(date);

				LoanSummary loanSummary = loanSummaryService.getByOnDateAndLoanId(newDate, Long.valueOf(id));
				LoanSummaryModel1 loanSummary1=new LoanSummaryModel1();
				if (loanSummary.getRecord_status() == 1 && loanSummary.getLoanSummaryType() == LoanSummaryType.MANUAL) {

					loanSummarySet.add(loanSummaryService.getById(loanSummary.getId()));

					String name1 = "";

					Double rate = currencyRateService.findByDateAndType(loanSummary.getOnDate(), loan.getCurrency()).getRate();

					if(loan.getCloseRate()!=null)
					if(loan.getCloseRate()>0) rate = loan.getCloseRate();


					name1 = loan.getCreditOrder().getRegNumber() + " №" + loan.getRegNumber() + " от " + new SimpleDateFormat("dd.MM.yyyy", new Locale("ru", "RU")).format(loan.getRegDate()) + "г. в тыс. " + loan.getCurrency().getName();

					if (loan.getCurrency().getId() != 17) {
						name1 = loan.getCreditOrder().getRegNumber() + " №" + loan.getRegNumber() + " от " + new SimpleDateFormat("dd.MM.yyyy", new Locale("ru", "RU")).format(loan.getRegDate()) + "г. в тыс. " + loan.getCurrency().getName();
					} else {
						name1 = loan.getCreditOrder().getRegNumber() + " №" + loan.getRegNumber() + " от " + new SimpleDateFormat("dd.MM.yyyy", new Locale("ru", "RU")).format(loan.getRegDate()) + "г. в тоннах " + loan.getCurrency().getName();
					}

					loanSummary1.setUuid(name1);
					loanSummary1.setVersion(loan.getId());
					loanSummary1.setId(debtorId);
					loanSummary1.setOnDate(srokDate);


					loanSummary1.setLoanAmount(conditional(loanSummary.getLoanAmount()));
					loanSummary1.setTotalDisbursed(conditional(loanSummary.getTotalDisbursed()));
					loanSummary1.setPaidPrincipal(conditional(loanSummary.getPaidPrincipal()));
					loanSummary1.setPaidInterest(conditional(loanSummary.getPaidInterest()));
					loanSummary1.setPaidPenalty(conditional(loanSummary.getPaidPenalty()));
					loanSummary1.setPaidFee(conditional(loanSummary.getPaidFee()));
					loanSummary1.setTotalOutstanding(conditional(loanSummary.getTotalOutstanding()));
					loanSummary1.setOutstadingPrincipal(conditional(loanSummary.getOutstadingPrincipal()));
					loanSummary1.setOutstadingInterest(conditional(loanSummary.getOutstadingInterest()));
					loanSummary1.setOutstadingPenalty(conditional(loanSummary.getOutstadingPenalty()));
					loanSummary1.setOutstadingFee(conditional(loanSummary.getOutstadingFee()));
					loanSummary1.setTotalOverdue(conditional(loanSummary.getTotalOverdue()));
					loanSummary1.setOverduePrincipal(conditional(loanSummary.getOverduePrincipal()));
					loanSummary1.setOverdueInterest(conditional(loanSummary.getOverdueInterest()));
					loanSummary1.setOverduePenalty(conditional(loanSummary.getOverduePenalty()));
					loanSummary1.setOverdueFee(conditional(loanSummary.getOverdueFee()));
					loanSummary1.setTotalPrincipalPaid(conditional(loanSummary.getTotalPrincipalPaid()));
					loanSummary1.setTotalInterestPaid(conditional(loanSummary.getTotalInterestPaid()));
					loanSummary1.setTotalPenaltyPaid(conditional(loanSummary.getTotalPenaltyPaid()));
					loanSummary1.setTotalFeePaid(conditional(loanSummary.getTotalFeePaid()));
					loanSummary1.setTotalPaidKGS(conditional(loanSummary.getTotalPaidKGS()));
					loanSummary1.setTotalPaid(conditional(loanSummary.getTotalPaid()));

					Boolean notKGS = false;

					LoanSummaryModel1 newLoanSummary = new LoanSummaryModel1();

					if (loan.getCurrency().getId() != 1) {
						notKGS = true;
						newLoanSummary.setVersion(Long.valueOf(1));
						newLoanSummary.setUuid("в тыс. сомах по курсу " + rate);
						newLoanSummary.setLoanAmount((loanSummary.getLoanAmount() * rate));
						newLoanSummary.setTotalDisbursed((loanSummary.getTotalDisbursed() * rate));
						newLoanSummary.setPaidPrincipal((loanSummary.getPaidPrincipal() * rate));
						newLoanSummary.setPaidInterest((loanSummary.getPaidInterest() * rate));
						newLoanSummary.setPaidPenalty((loanSummary.getPaidPenalty() * rate));
						newLoanSummary.setPaidFee((loanSummary.getPaidFee() * rate));
						newLoanSummary.setTotalOutstanding((loanSummary.getTotalOutstanding() * rate));
						newLoanSummary.setOutstadingPrincipal((loanSummary.getOutstadingPrincipal() * rate));
						newLoanSummary.setOutstadingInterest((loanSummary.getOutstadingInterest() * rate));
						newLoanSummary.setOutstadingPenalty((loanSummary.getOutstadingPenalty() * rate));
						newLoanSummary.setOutstadingFee((loanSummary.getOutstadingFee() * rate));
						newLoanSummary.setTotalOverdue((loanSummary.getTotalOverdue() * rate));
						newLoanSummary.setOverduePrincipal((loanSummary.getOverduePrincipal() * rate));
						newLoanSummary.setOverdueInterest((loanSummary.getOverdueInterest() * rate));
						newLoanSummary.setOverduePenalty((loanSummary.getOverduePenalty() * rate));
						newLoanSummary.setOverdueFee((loanSummary.getOverdueFee() * rate));
						newLoanSummary.setTotalPrincipalPaid((loanSummary.getTotalPrincipalPaid() * rate));
						newLoanSummary.setTotalInterestPaid((loanSummary.getTotalInterestPaid() * rate));
						newLoanSummary.setTotalPenaltyPaid((loanSummary.getTotalPenaltyPaid() * rate));
						newLoanSummary.setTotalFeePaid((loanSummary.getTotalFeePaid() * rate));
						newLoanSummary.setOnDate(loanSummary.getOnDate());
						newLoanSummary.setTotalPaidKGS((loanSummary.getTotalPaidKGS()));
						newLoanSummary.setTotalPaid((loanSummary.getTotalPaid()));

						model.addAttribute("newSummary", newLoanSummary);

						summaries.put(loanSummary1, newLoanSummary);

						if (sumLoanSummary.getLoanAmount() != null) {
							sumLoanSummary.setTotalPaidKGS(sumLoanSummary.getTotalPaidKGS() + newLoanSummary.getTotalPaidKGS());
							sumLoanSummary.setTotalPaid(sumLoanSummary.getTotalPaid() + newLoanSummary.getTotalPaid());
							sumLoanSummary.setTotalFeePaid(sumLoanSummary.getTotalFeePaid() + newLoanSummary.getTotalFeePaid());
							sumLoanSummary.setTotalInterestPaid(sumLoanSummary.getTotalInterestPaid() + newLoanSummary.getTotalInterestPaid());
							sumLoanSummary.setTotalPrincipalPaid(sumLoanSummary.getTotalPrincipalPaid() + newLoanSummary.getTotalPrincipalPaid());
							sumLoanSummary.setTotalPenaltyPaid(sumLoanSummary.getTotalPenaltyPaid() + newLoanSummary.getTotalPenaltyPaid());
							sumLoanSummary.setOverdueInterest(sumLoanSummary.getOverdueInterest() + newLoanSummary.getOverdueInterest());
							sumLoanSummary.setOverduePrincipal(sumLoanSummary.getOverduePrincipal() + newLoanSummary.getOverduePrincipal());
							sumLoanSummary.setOverduePenalty(sumLoanSummary.getOverduePenalty() + newLoanSummary.getOverduePenalty());
							sumLoanSummary.setOverdueFee(sumLoanSummary.getOverdueFee() + newLoanSummary.getOverdueFee());
							sumLoanSummary.setOutstadingPenalty(sumLoanSummary.getOutstadingPenalty() + newLoanSummary.getOutstadingPenalty());
							sumLoanSummary.setOutstadingPrincipal(sumLoanSummary.getOutstadingPrincipal() + newLoanSummary.getOutstadingPrincipal());
							sumLoanSummary.setOutstadingInterest(sumLoanSummary.getOutstadingInterest() + newLoanSummary.getOutstadingInterest());
							sumLoanSummary.setOutstadingFee(sumLoanSummary.getOutstadingFee() + newLoanSummary.getOutstadingFee());
							sumLoanSummary.setTotalOverdue(sumLoanSummary.getTotalOverdue() + newLoanSummary.getTotalOverdue());
							sumLoanSummary.setTotalOutstanding(sumLoanSummary.getTotalOutstanding() + newLoanSummary.getTotalOutstanding());
							sumLoanSummary.setPaidFee(sumLoanSummary.getPaidFee() + newLoanSummary.getPaidFee());
							sumLoanSummary.setPaidInterest(sumLoanSummary.getPaidInterest() + newLoanSummary.getPaidInterest());
							sumLoanSummary.setPaidPenalty(sumLoanSummary.getPaidPenalty() + newLoanSummary.getPaidPenalty());
							sumLoanSummary.setPaidPrincipal(sumLoanSummary.getPaidPrincipal() + newLoanSummary.getPaidPrincipal());
							sumLoanSummary.setTotalDisbursed(sumLoanSummary.getTotalDisbursed() + newLoanSummary.getTotalDisbursed());
							sumLoanSummary.setLoanAmount(sumLoanSummary.getLoanAmount() + newLoanSummary.getLoanAmount());
						} else {
							sumLoanSummary.setTotalPaidKGS(newLoanSummary.getTotalPaidKGS());
							sumLoanSummary.setTotalPaid(newLoanSummary.getTotalPaid());
							sumLoanSummary.setTotalFeePaid(newLoanSummary.getTotalFeePaid());
							sumLoanSummary.setTotalInterestPaid(newLoanSummary.getTotalInterestPaid());
							sumLoanSummary.setTotalPrincipalPaid(newLoanSummary.getTotalPrincipalPaid());
							sumLoanSummary.setTotalPenaltyPaid(newLoanSummary.getTotalPenaltyPaid());
							sumLoanSummary.setOverdueInterest(newLoanSummary.getOverdueInterest());
							sumLoanSummary.setOverduePrincipal(newLoanSummary.getOverduePrincipal());
							sumLoanSummary.setOverduePenalty(newLoanSummary.getOverduePenalty());
							sumLoanSummary.setOverdueFee(newLoanSummary.getOverdueFee());
							sumLoanSummary.setOutstadingPenalty(newLoanSummary.getOutstadingPenalty());
							sumLoanSummary.setOutstadingPrincipal(newLoanSummary.getOutstadingPrincipal());
							sumLoanSummary.setOutstadingInterest(newLoanSummary.getOutstadingInterest());
							sumLoanSummary.setOutstadingFee(newLoanSummary.getOutstadingFee());
							sumLoanSummary.setTotalOverdue(newLoanSummary.getTotalOverdue());
							sumLoanSummary.setTotalOutstanding(newLoanSummary.getTotalOutstanding());
							sumLoanSummary.setPaidFee(newLoanSummary.getPaidFee());
							sumLoanSummary.setPaidInterest(newLoanSummary.getPaidInterest());
							sumLoanSummary.setPaidPenalty(newLoanSummary.getPaidPenalty());
							sumLoanSummary.setPaidPrincipal(newLoanSummary.getPaidPrincipal());
							sumLoanSummary.setTotalDisbursed(newLoanSummary.getTotalDisbursed());
							sumLoanSummary.setLoanAmount(newLoanSummary.getLoanAmount());
						}
					} else {
//                    loanSummary.setVersion(Long.valueOf(0));
						summaries.put(loanSummary1, loanSummary1);
						if (sumLoanSummary.getLoanAmount() != null) {
							sumLoanSummary.setTotalPaidKGS(sumLoanSummary.getTotalPaidKGS() + loanSummary1.getTotalPaidKGS());
							sumLoanSummary.setTotalPaid(sumLoanSummary.getTotalPaid() + loanSummary1.getTotalPaid());
							sumLoanSummary.setTotalFeePaid(sumLoanSummary.getTotalFeePaid() + loanSummary1.getTotalFeePaid());
							sumLoanSummary.setTotalInterestPaid(sumLoanSummary.getTotalInterestPaid() + loanSummary1.getTotalInterestPaid());
							sumLoanSummary.setTotalPrincipalPaid(sumLoanSummary.getTotalPrincipalPaid() + loanSummary1.getTotalPrincipalPaid());
							sumLoanSummary.setTotalPenaltyPaid(sumLoanSummary.getTotalPenaltyPaid() + loanSummary1.getTotalPenaltyPaid());
							sumLoanSummary.setOverdueInterest(sumLoanSummary.getOverdueInterest() + loanSummary1.getOverdueInterest());
							sumLoanSummary.setOverduePrincipal(sumLoanSummary.getOverduePrincipal() + loanSummary1.getOverduePrincipal());
							sumLoanSummary.setOverduePenalty(sumLoanSummary.getOverduePenalty() + loanSummary1.getOverduePenalty());
							sumLoanSummary.setOverdueFee(sumLoanSummary.getOverdueFee() + loanSummary1.getOverdueFee());
							sumLoanSummary.setOutstadingPenalty(sumLoanSummary.getOutstadingPenalty() + loanSummary1.getOutstadingPenalty());
							sumLoanSummary.setOutstadingPrincipal(sumLoanSummary.getOutstadingPrincipal() + loanSummary1.getOutstadingPrincipal());
							sumLoanSummary.setOutstadingInterest(sumLoanSummary.getOutstadingInterest() + loanSummary1.getOutstadingInterest());
							sumLoanSummary.setOutstadingFee(sumLoanSummary.getOutstadingFee() + loanSummary1.getOutstadingFee());
							sumLoanSummary.setTotalOverdue(sumLoanSummary.getTotalOverdue() + loanSummary1.getTotalOverdue());
							sumLoanSummary.setTotalOutstanding(sumLoanSummary.getTotalOutstanding() + loanSummary1.getTotalOutstanding());
							sumLoanSummary.setPaidFee(sumLoanSummary.getPaidFee() + loanSummary1.getPaidFee());
							sumLoanSummary.setPaidInterest(sumLoanSummary.getPaidInterest() + loanSummary1.getPaidInterest());
							sumLoanSummary.setPaidPenalty(sumLoanSummary.getPaidPenalty() + loanSummary1.getPaidPenalty());
							sumLoanSummary.setPaidPrincipal(sumLoanSummary.getPaidPrincipal() + loanSummary1.getPaidPrincipal());
							sumLoanSummary.setTotalDisbursed(sumLoanSummary.getTotalDisbursed() + loanSummary1.getTotalDisbursed());
							sumLoanSummary.setLoanAmount(sumLoanSummary.getLoanAmount() + loanSummary1.getLoanAmount());
						} else {
							sumLoanSummary.setTotalPaidKGS(loanSummary1.getTotalPaidKGS());
							sumLoanSummary.setTotalPaid(loanSummary1.getTotalPaid());
							sumLoanSummary.setTotalFeePaid(loanSummary1.getTotalFeePaid());
							sumLoanSummary.setTotalInterestPaid(loanSummary1.getTotalInterestPaid());
							sumLoanSummary.setTotalPrincipalPaid(loanSummary1.getTotalPrincipalPaid());
							sumLoanSummary.setTotalPenaltyPaid(loanSummary1.getTotalPenaltyPaid());
							sumLoanSummary.setOverdueInterest(loanSummary1.getOverdueInterest());
							sumLoanSummary.setOverduePrincipal(loanSummary1.getOverduePrincipal());
							sumLoanSummary.setOverduePenalty(loanSummary1.getOverduePenalty());
							sumLoanSummary.setOverdueFee(loanSummary1.getOverdueFee());
							sumLoanSummary.setOutstadingPenalty(loanSummary1.getOutstadingPenalty());
							sumLoanSummary.setOutstadingPrincipal(loanSummary1.getOutstadingPrincipal());
							sumLoanSummary.setOutstadingInterest(loanSummary1.getOutstadingInterest());
							sumLoanSummary.setOutstadingFee(loanSummary1.getOutstadingFee());
							sumLoanSummary.setTotalOverdue(loanSummary1.getTotalOverdue());
							sumLoanSummary.setTotalOutstanding(loanSummary1.getTotalOutstanding());
							sumLoanSummary.setPaidFee(loanSummary1.getPaidFee());
							sumLoanSummary.setPaidInterest(loanSummary1.getPaidInterest());
							sumLoanSummary.setPaidPenalty(loanSummary1.getPaidPenalty());
							sumLoanSummary.setPaidPrincipal(loanSummary1.getPaidPrincipal());
							sumLoanSummary.setTotalDisbursed(loanSummary1.getTotalDisbursed());
							sumLoanSummary.setLoanAmount(loanSummary1.getLoanAmount());
						}
					}

//                model.addAttribute("name",name1);
//                model.addAttribute("rate","в тыс. сомах по курсу "+rate);
				}
			}

		}

		model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtorService.getById(debtorId));
		model.addAttribute("lists", summaries);
		model.addAttribute("isCalculationSaved", true);

		model.addAttribute("totals", sumLoanSummary);
		model.addAttribute("isSaved", "");

		model.addAttribute("fromLoanList","true");
		LoanSummaryAct loanSummaryAct = new LoanSummaryAct();
		loanSummaryAct.setLoanSummaries(loanSummarySet);
		loanSummaryAct.setLoanSummaryActState(loanSummaryActStateService.getById(Long.valueOf(1)));
		loanSummaryAct.setDebtor(debtorService.getById(debtorId));
		loanSummaryAct.setAmount(sumLoanSummary.getTotalOutstanding());
		loanSummaryAct.setOnDate(newDate);

		loanSummaryActService.add(loanSummaryAct);


		return "/manage/debtor/loanSummary2";

	}
	@RequestMapping(value = "/manage/debtor/{debtorId}/loan/summary/act/{id}/view",method = RequestMethod.GET)
	public String getLoanSummaryActsSummaries(ModelMap model, @PathVariable("debtorId") Long debtorId,@PathVariable("id") Long id){

		Set<LoanSummary> loanSummarySet=new HashSet<>();
		LoanSummaryModel1 sumLoanSummary=new LoanSummaryModel1();
		HashMap<LoanSummaryModel1,LoanSummaryModel1> summaries=new HashMap<>();

		Date newDate = new Date();

		LoanSummaryAct loanSummaryAct=loanSummaryActService.getById(id);
		for (LoanSummary summary1:loanSummaryAct.getLoanSummaries()){
			LoanSummary loanSummary=loanSummaryService.getById(summary1.getId());
			LoanSummaryModel1 loanSummary1=new LoanSummaryModel1();
			if(!(Long.valueOf(id)==0)){

				Date srokDate = loanSummary.getOnDate();

				Loan loan=loanService.getById(loanSummary.getLoan().getId());
				if(srokDate==null) srokDate = loan.getRegDate();


				if(true){

					loanSummarySet.add(loanSummary);

					String name1="";

					Double rate=currencyRateService.findByDateAndType(loanSummary.getOnDate(),loan.getCurrency()).getRate();

					if(loan.getCloseRate()!=null)
					if(loan.getCloseRate()>0) rate = loan.getCloseRate();


					name1=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).format(loan.getRegDate())+"г. в тыс. "+loan.getCurrency().getName();

					if(loan.getCurrency().getId()!=17){
						name1=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).format(loan.getRegDate())+"г. в тыс. "+loan.getCurrency().getName();
					}
					else{
						name1=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).format(loan.getRegDate())+"г. в тоннах "+loan.getCurrency().getName();
					}

					loanSummary1.setUuid(name1);
					loanSummary1.setVersion(loan.getId());
					loanSummary1.setId(debtorId);
					loanSummary1.setOnDate(srokDate);



					loanSummary1.setLoanAmount(conditional(loanSummary.getLoanAmount()));
					loanSummary1.setTotalDisbursed(conditional(loanSummary.getTotalDisbursed()));
					loanSummary1.setPaidPrincipal(conditional(loanSummary.getPaidPrincipal()));
					loanSummary1.setPaidInterest(conditional(loanSummary.getPaidInterest()));
					loanSummary1.setPaidPenalty(conditional(loanSummary.getPaidPenalty()));
					loanSummary1.setPaidFee(conditional(loanSummary.getPaidFee()));
					loanSummary1.setTotalOutstanding(conditional(loanSummary.getTotalOutstanding()));
					loanSummary1.setOutstadingPrincipal(conditional(loanSummary.getOutstadingPrincipal()));
					loanSummary1.setOutstadingInterest(conditional(loanSummary.getOutstadingInterest()));
					loanSummary1.setOutstadingPenalty(conditional(loanSummary.getOutstadingPenalty()));
					loanSummary1.setOutstadingFee(conditional(loanSummary.getOutstadingFee()));
					loanSummary1.setTotalOverdue(conditional(loanSummary.getTotalOverdue()));
					loanSummary1.setOverduePrincipal(conditional(loanSummary.getOverduePrincipal()));
					loanSummary1.setOverdueInterest(conditional(loanSummary.getOverdueInterest()));
					loanSummary1.setOverduePenalty(conditional(loanSummary.getOverduePenalty()));
					loanSummary1.setOverdueFee(conditional(loanSummary.getOverdueFee()));
					loanSummary1.setTotalPrincipalPaid(conditional(loanSummary.getTotalPrincipalPaid()));
					loanSummary1.setTotalInterestPaid(conditional(loanSummary.getTotalInterestPaid()));
					loanSummary1.setTotalPenaltyPaid(conditional(loanSummary.getTotalPenaltyPaid()));
					loanSummary1.setTotalFeePaid(conditional(loanSummary.getTotalFeePaid()));
					loanSummary1.setTotalPaidKGS(conditional(loanSummary.getTotalPaidKGS()));
					loanSummary1.setTotalPaid(conditional(loanSummary.getTotalPaid()));

					Boolean notKGS=false;

					LoanSummaryModel1 newLoanSummary=new LoanSummaryModel1();

					if(loan.getCurrency().getId()!=1)
					{
						notKGS=true;
						newLoanSummary.setVersion(Long.valueOf(1));
						newLoanSummary.setUuid("в тыс. сомах по курсу "+rate);
						newLoanSummary.setLoanAmount((loanSummary.getLoanAmount()*rate));
						newLoanSummary.setTotalDisbursed((loanSummary.getTotalDisbursed()*rate));
						newLoanSummary.setPaidPrincipal((loanSummary.getPaidPrincipal()*rate));
						newLoanSummary.setPaidInterest((loanSummary.getPaidInterest()*rate));
						newLoanSummary.setPaidPenalty((loanSummary.getPaidPenalty()*rate));
						newLoanSummary.setPaidFee((loanSummary.getPaidFee()*rate));
						newLoanSummary.setTotalOutstanding((loanSummary.getTotalOutstanding()*rate));
						newLoanSummary.setOutstadingPrincipal((loanSummary.getOutstadingPrincipal()*rate));
						newLoanSummary.setOutstadingInterest((loanSummary.getOutstadingInterest()*rate));
						newLoanSummary.setOutstadingPenalty((loanSummary.getOutstadingPenalty()*rate));
						newLoanSummary.setOutstadingFee((loanSummary.getOutstadingFee()*rate));
						newLoanSummary.setTotalOverdue((loanSummary.getTotalOverdue()*rate));
						newLoanSummary.setOverduePrincipal((loanSummary.getOverduePrincipal()*rate));
						newLoanSummary.setOverdueInterest((loanSummary.getOverdueInterest()*rate));
						newLoanSummary.setOverduePenalty((loanSummary.getOverduePenalty()*rate));
						newLoanSummary.setOverdueFee((loanSummary.getOverdueFee()*rate));
						newLoanSummary.setTotalPrincipalPaid((loanSummary.getTotalPrincipalPaid()*rate));
						newLoanSummary.setTotalInterestPaid((loanSummary.getTotalInterestPaid()*rate));
						newLoanSummary.setTotalPenaltyPaid((loanSummary.getTotalPenaltyPaid()*rate));
						newLoanSummary.setTotalFeePaid((loanSummary.getTotalFeePaid()*rate));
						newLoanSummary.setOnDate(loanSummary.getOnDate());
						newLoanSummary.setTotalPaidKGS((loanSummary.getTotalPaidKGS()));
						newLoanSummary.setTotalPaid((loanSummary.getTotalPaid()));


						summaries.put(loanSummary1,newLoanSummary);

						if(sumLoanSummary.getLoanAmount()!=null) {
							sumLoanSummary.setTotalPaidKGS(sumLoanSummary.getTotalPaidKGS() + newLoanSummary.getTotalPaidKGS());
							sumLoanSummary.setTotalPaid(sumLoanSummary.getTotalPaid() + newLoanSummary.getTotalPaid());
							sumLoanSummary.setTotalFeePaid(sumLoanSummary.getTotalFeePaid() + newLoanSummary.getTotalFeePaid());
							sumLoanSummary.setTotalInterestPaid(sumLoanSummary.getTotalInterestPaid() + newLoanSummary.getTotalInterestPaid());
							sumLoanSummary.setTotalPrincipalPaid(sumLoanSummary.getTotalPrincipalPaid() + newLoanSummary.getTotalPrincipalPaid());
							sumLoanSummary.setTotalPenaltyPaid(sumLoanSummary.getTotalPenaltyPaid() + newLoanSummary.getTotalPenaltyPaid());
							sumLoanSummary.setOverdueInterest(sumLoanSummary.getOverdueInterest() + newLoanSummary.getOverdueInterest());
							sumLoanSummary.setOverduePrincipal(sumLoanSummary.getOverduePrincipal() + newLoanSummary.getOverduePrincipal());
							sumLoanSummary.setOverduePenalty(sumLoanSummary.getOverduePenalty() + newLoanSummary.getOverduePenalty());
							sumLoanSummary.setOverdueFee(sumLoanSummary.getOverdueFee() + newLoanSummary.getOverdueFee());
							sumLoanSummary.setOutstadingPenalty(sumLoanSummary.getOutstadingPenalty() + newLoanSummary.getOutstadingPenalty());
							sumLoanSummary.setOutstadingPrincipal(sumLoanSummary.getOutstadingPrincipal() + newLoanSummary.getOutstadingPrincipal());
							sumLoanSummary.setOutstadingInterest(sumLoanSummary.getOutstadingInterest() + newLoanSummary.getOutstadingInterest());
							sumLoanSummary.setOutstadingFee(sumLoanSummary.getOutstadingFee() + newLoanSummary.getOutstadingFee());
							sumLoanSummary.setTotalOverdue(sumLoanSummary.getTotalOverdue() + newLoanSummary.getTotalOverdue());
							sumLoanSummary.setTotalOutstanding(sumLoanSummary.getTotalOutstanding() + newLoanSummary.getTotalOutstanding());
							sumLoanSummary.setPaidFee(sumLoanSummary.getPaidFee() + newLoanSummary.getPaidFee());
							sumLoanSummary.setPaidInterest(sumLoanSummary.getPaidInterest() + newLoanSummary.getPaidInterest());
							sumLoanSummary.setPaidPenalty(sumLoanSummary.getPaidPenalty() + newLoanSummary.getPaidPenalty());
							sumLoanSummary.setPaidPrincipal(sumLoanSummary.getPaidPrincipal() + newLoanSummary.getPaidPrincipal());
							sumLoanSummary.setTotalDisbursed(sumLoanSummary.getTotalDisbursed() + newLoanSummary.getTotalDisbursed());
							sumLoanSummary.setLoanAmount(sumLoanSummary.getLoanAmount() + newLoanSummary.getLoanAmount());
						}
						else{
							sumLoanSummary.setTotalPaidKGS(newLoanSummary.getTotalPaidKGS());
							sumLoanSummary.setTotalPaid(newLoanSummary.getTotalPaid());
							sumLoanSummary.setTotalFeePaid(newLoanSummary.getTotalFeePaid());
							sumLoanSummary.setTotalInterestPaid(newLoanSummary.getTotalInterestPaid());
							sumLoanSummary.setTotalPrincipalPaid(newLoanSummary.getTotalPrincipalPaid());
							sumLoanSummary.setTotalPenaltyPaid(newLoanSummary.getTotalPenaltyPaid());
							sumLoanSummary.setOverdueInterest(newLoanSummary.getOverdueInterest());
							sumLoanSummary.setOverduePrincipal(newLoanSummary.getOverduePrincipal());
							sumLoanSummary.setOverduePenalty(newLoanSummary.getOverduePenalty());
							sumLoanSummary.setOverdueFee(newLoanSummary.getOverdueFee());
							sumLoanSummary.setOutstadingPenalty(newLoanSummary.getOutstadingPenalty());
							sumLoanSummary.setOutstadingPrincipal(newLoanSummary.getOutstadingPrincipal());
							sumLoanSummary.setOutstadingInterest(newLoanSummary.getOutstadingInterest());
							sumLoanSummary.setOutstadingFee(newLoanSummary.getOutstadingFee());
							sumLoanSummary.setTotalOverdue(newLoanSummary.getTotalOverdue());
							sumLoanSummary.setTotalOutstanding(newLoanSummary.getTotalOutstanding());
							sumLoanSummary.setPaidFee(newLoanSummary.getPaidFee());
							sumLoanSummary.setPaidInterest(newLoanSummary.getPaidInterest());
							sumLoanSummary.setPaidPenalty(newLoanSummary.getPaidPenalty());
							sumLoanSummary.setPaidPrincipal(newLoanSummary.getPaidPrincipal());
							sumLoanSummary.setTotalDisbursed(newLoanSummary.getTotalDisbursed());
							sumLoanSummary.setLoanAmount(newLoanSummary.getLoanAmount());
						}
					}
					else{
						summaries.put(loanSummary1,loanSummary1);
						if(sumLoanSummary.getLoanAmount()!=null) {
							sumLoanSummary.setTotalPaidKGS(sumLoanSummary.getTotalPaidKGS() + loanSummary1.getTotalPaidKGS());
							sumLoanSummary.setTotalPaid(sumLoanSummary.getTotalPaid()+loanSummary1.getTotalPaid());
							sumLoanSummary.setTotalFeePaid(sumLoanSummary.getTotalFeePaid() + loanSummary1.getTotalFeePaid());
							sumLoanSummary.setTotalInterestPaid(sumLoanSummary.getTotalInterestPaid() + loanSummary1.getTotalInterestPaid());
							sumLoanSummary.setTotalPrincipalPaid(sumLoanSummary.getTotalPrincipalPaid() + loanSummary1.getTotalPrincipalPaid());
							sumLoanSummary.setTotalPenaltyPaid(sumLoanSummary.getTotalPenaltyPaid() + loanSummary1.getTotalPenaltyPaid());
							sumLoanSummary.setOverdueInterest(sumLoanSummary.getOverdueInterest() + loanSummary1.getOverdueInterest());
							sumLoanSummary.setOverduePrincipal(sumLoanSummary.getOverduePrincipal() + loanSummary1.getOverduePrincipal());
							sumLoanSummary.setOverduePenalty(sumLoanSummary.getOverduePenalty() + loanSummary1.getOverduePenalty());
							sumLoanSummary.setOverdueFee(sumLoanSummary.getOverdueFee() + loanSummary1.getOverdueFee());
							sumLoanSummary.setOutstadingPenalty(sumLoanSummary.getOutstadingPenalty() + loanSummary1.getOutstadingPenalty());
							sumLoanSummary.setOutstadingPrincipal(sumLoanSummary.getOutstadingPrincipal() + loanSummary1.getOutstadingPrincipal());
							sumLoanSummary.setOutstadingInterest(sumLoanSummary.getOutstadingInterest() + loanSummary1.getOutstadingInterest());
							sumLoanSummary.setOutstadingFee(sumLoanSummary.getOutstadingFee() + loanSummary1.getOutstadingFee());
							sumLoanSummary.setTotalOverdue(sumLoanSummary.getTotalOverdue() + loanSummary1.getTotalOverdue());
							sumLoanSummary.setTotalOutstanding(sumLoanSummary.getTotalOutstanding() + loanSummary1.getTotalOutstanding());
							sumLoanSummary.setPaidFee(sumLoanSummary.getPaidFee() + loanSummary1.getPaidFee());
							sumLoanSummary.setPaidInterest(sumLoanSummary.getPaidInterest() + loanSummary1.getPaidInterest());
							sumLoanSummary.setPaidPenalty(sumLoanSummary.getPaidPenalty() + loanSummary1.getPaidPenalty());
							sumLoanSummary.setPaidPrincipal(sumLoanSummary.getPaidPrincipal() + loanSummary1.getPaidPrincipal());
							sumLoanSummary.setTotalDisbursed(sumLoanSummary.getTotalDisbursed() + loanSummary1.getTotalDisbursed());
							sumLoanSummary.setLoanAmount(sumLoanSummary.getLoanAmount() + loanSummary1.getLoanAmount());
						}
						else{
							sumLoanSummary.setTotalPaidKGS(loanSummary1.getTotalPaidKGS());
							sumLoanSummary.setTotalPaid(loanSummary1.getTotalPaid());
							sumLoanSummary.setTotalFeePaid(loanSummary1.getTotalFeePaid());
							sumLoanSummary.setTotalInterestPaid(loanSummary1.getTotalInterestPaid());
							sumLoanSummary.setTotalPrincipalPaid(loanSummary1.getTotalPrincipalPaid());
							sumLoanSummary.setTotalPenaltyPaid(loanSummary1.getTotalPenaltyPaid());
							sumLoanSummary.setOverdueInterest(loanSummary1.getOverdueInterest());
							sumLoanSummary.setOverduePrincipal(loanSummary1.getOverduePrincipal());
							sumLoanSummary.setOverduePenalty(loanSummary1.getOverduePenalty());
							sumLoanSummary.setOverdueFee(loanSummary1.getOverdueFee());
							sumLoanSummary.setOutstadingPenalty(loanSummary1.getOutstadingPenalty());
							sumLoanSummary.setOutstadingPrincipal(loanSummary1.getOutstadingPrincipal());
							sumLoanSummary.setOutstadingInterest(loanSummary1.getOutstadingInterest());
							sumLoanSummary.setOutstadingFee(loanSummary1.getOutstadingFee());
							sumLoanSummary.setTotalOverdue(loanSummary1.getTotalOverdue());
							sumLoanSummary.setTotalOutstanding(loanSummary1.getTotalOutstanding());
							sumLoanSummary.setPaidFee(loanSummary1.getPaidFee());
							sumLoanSummary.setPaidInterest(loanSummary1.getPaidInterest());
							sumLoanSummary.setPaidPenalty(loanSummary1.getPaidPenalty());
							sumLoanSummary.setPaidPrincipal(loanSummary1.getPaidPrincipal());
							sumLoanSummary.setTotalDisbursed(loanSummary1.getTotalDisbursed());
							sumLoanSummary.setLoanAmount(loanSummary1.getLoanAmount());
						}
					}

				}
			}

		}

		model.addAttribute("debtorId",debtorId);
		model.addAttribute("debtor",debtorService.getById(debtorId));
		model.addAttribute("lists",summaries);
		model.addAttribute("isCalculationSaved",true);
		model.addAttribute("fromLoanList","false");

		model.addAttribute("totals",sumLoanSummary);
		model.addAttribute("isSaved", "");




		return "/manage/debtor/loanSummary2";
	}

	@RequestMapping(value = "/manage/debtor/{debtorId}/summary/view",method = RequestMethod.GET)
	public String getSelectedLoanSummariesView(ModelMap model, @PathVariable("debtorId") Long debtorId,String date,String name) throws ParseException
	{

		CalculationTool calculationTool=new CalculationTool();
		LoanSummary sumLoanSummary=new LoanSummary();
		LinkedHashSet<LoanView> loanViews = new LinkedHashSet<LoanView>(0);
		HashMap<LoanSummaryModel1,LoanSummaryModel1> summaries=new HashMap<>();
		Date newDate=new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).parse(date);
		LinkedHashMap<Long, LoanDetailedSummary> loanDetailedSummaryList = new LinkedHashMap<>();

		for (String id:name.split("-")){
			if(!id.equals("")) {
				String baseQuery="select * from loan_view where v_loan_id="+id;
				Query query=entityManager.createNativeQuery(baseQuery,LoanView.class);
				LoanView loanView= (LoanView) query.getSingleResult();
				loanViews.add(loanView);
			}
		}
		loanDetailedSummaryList.putAll(calculationTool.getAllLoanDetailedSummariesByLoanViewList(loanViews, newDate ));
		for (LoanView loanView:loanViews){
			Loan loan = loanService.getById(Long.valueOf(loanView.getV_loan_id()));

				Date srokDate = null;

			for (PaymentSchedule schedule: loan.getPaymentSchedules())
			{
				if(schedule.getPrincipalPayment()>0)
				{
					if(srokDate==null)
					{
						srokDate=schedule.getExpectedDate();
					}
					else
					{
						if(schedule.getExpectedDate()!=null)
						if(schedule.getExpectedDate().after(srokDate));
						{
							srokDate = schedule.getExpectedDate();
						}
					}

				}

			}

			if(srokDate==null) srokDate = loan.getRegDate();
			LoanSummaryModel1 loanSummary1=new LoanSummaryModel1();

				LoanSummary loanSummary=calculationTool.getLoanSummaryCaluculatedByLoanIdAndOnDate(loanView,loanView.getV_loan_id(),newDate,null);

				Double rate=currencyRateService.findByDateAndType(loanSummary.getOnDate(),loan.getCurrency()).getRate();


				if(loan.getCloseRate()!=null)
				if(loan.getCloseRate()>0) rate = loan.getCloseRate();

                loanSummary1.setLoanAmount(conditional(loanSummary.getLoanAmount()));
                loanSummary1.setTotalDisbursed(conditional(loanSummary.getTotalDisbursed()));
                loanSummary1.setTotalOutstanding(conditional(loanSummary.getTotalOutstanding()));
                loanSummary1.setOutstadingPrincipal(conditional(loanSummary.getOutstadingPrincipal()));
                loanSummary1.setOutstadingInterest(conditional(loanSummary.getOutstadingInterest()));
                loanSummary1.setOutstadingPenalty(conditional(loanSummary.getOutstadingPenalty()));
                loanSummary1.setTotalOverdue(conditional(loanSummary.getTotalOverdue()));
                loanSummary1.setOverduePrincipal(conditional(loanSummary.getOverduePrincipal()));
                loanSummary1.setOverdueInterest(conditional(loanSummary.getOverdueInterest()));
                loanSummary1.setOverduePenalty(conditional(loanSummary.getOverduePenalty()));
                loanSummary1.setOnDate(srokDate);
                loanSummary1.setTotalPaidKGS(conditional(loanSummary.getTotalPaidKGS()));
                loanSummary1.setTotalPaid(conditional(loanSummary.getTotalPaid()));

				String name1="";
				if(loan.getCurrency().getId()!=17){
					name1=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).format(loan.getRegDate())+"г. в тыс. "+loan.getCurrency().getName();
				}
				else{
					name1=loan.getCreditOrder().getRegNumber()+" №"+loan.getRegNumber()+" от "+new SimpleDateFormat("dd.MM.yyyy",new Locale("ru","RU")).format(loan.getRegDate())+"г. в тоннах "+loan.getCurrency().getName();
				}
				loanSummary1.setUuid(name1);
				loanSummary1.setVersion(loan.getId());
				loanSummary1.setId(debtorId);



				LoanSummaryModel1 newLoanSummary=new LoanSummaryModel1();
				if(loan.getCurrency().getId()!=1){
					newLoanSummary.setVersion(Long.valueOf(1));
					newLoanSummary.setUuid("в тыс. сомах по курсу "+rate);
					newLoanSummary.setLoanAmount((loanSummary1.getLoanAmount()*rate));
					newLoanSummary.setTotalDisbursed((loanSummary1.getTotalDisbursed()*rate));
					newLoanSummary.setTotalOutstanding((loanSummary1.getTotalOutstanding()*rate));
					newLoanSummary.setOutstadingPrincipal((loanSummary1.getOutstadingPrincipal()*rate));
					newLoanSummary.setOutstadingInterest((loanSummary1.getOutstadingInterest()*rate));
					newLoanSummary.setOutstadingPenalty((loanSummary1.getOutstadingPenalty()*rate));
					newLoanSummary.setTotalOverdue((loanSummary1.getTotalOverdue()*rate));
                    newLoanSummary.setOverduePrincipal((loanSummary1.getOverduePrincipal()*rate));
                    newLoanSummary.setOverdueInterest((loanSummary1.getOverdueInterest()*rate));
                    newLoanSummary.setOverduePenalty((loanSummary1.getOverduePenalty()*rate));
					newLoanSummary.setOnDate(loanSummary1.getOnDate());
					newLoanSummary.setTotalPaidKGS((loanSummary1.getTotalPaidKGS()));
					newLoanSummary.setTotalPaid((loanSummary1.getTotalPaid()));

					model.addAttribute("newSummary",newLoanSummary);

					summaries.put(loanSummary1,newLoanSummary);

					if(sumLoanSummary.getLoanAmount()!=null) {
						sumLoanSummary.setTotalPaidKGS(sumLoanSummary.getTotalPaidKGS() + newLoanSummary.getTotalPaidKGS());
						sumLoanSummary.setTotalPaid(sumLoanSummary.getTotalPaid() + newLoanSummary.getTotalPaid());
						sumLoanSummary.setOutstadingPenalty(sumLoanSummary.getOutstadingPenalty() + newLoanSummary.getOutstadingPenalty());
						sumLoanSummary.setOutstadingPrincipal(sumLoanSummary.getOutstadingPrincipal() + newLoanSummary.getOutstadingPrincipal());
						sumLoanSummary.setOutstadingInterest(sumLoanSummary.getOutstadingInterest() + newLoanSummary.getOutstadingInterest());
						sumLoanSummary.setTotalOverdue(sumLoanSummary.getTotalOverdue() + newLoanSummary.getTotalOverdue());
                        sumLoanSummary.setOverduePrincipal(sumLoanSummary.getOverduePrincipal()+newLoanSummary.getOverduePrincipal());
                        sumLoanSummary.setOverdueInterest(sumLoanSummary.getOverdueInterest()+newLoanSummary.getOverdueInterest());
                        sumLoanSummary.setOverduePenalty(sumLoanSummary.getOverduePenalty()+newLoanSummary.getOverduePenalty());
						sumLoanSummary.setTotalOutstanding(sumLoanSummary.getTotalOutstanding() + newLoanSummary.getTotalOutstanding());
						sumLoanSummary.setTotalDisbursed(sumLoanSummary.getTotalDisbursed() + newLoanSummary.getTotalDisbursed());
						sumLoanSummary.setLoanAmount(sumLoanSummary.getLoanAmount() + newLoanSummary.getLoanAmount());
					}
					else{
						sumLoanSummary.setTotalPaidKGS(newLoanSummary.getTotalPaidKGS());
						sumLoanSummary.setTotalPaid(newLoanSummary.getTotalPaid());
						sumLoanSummary.setOutstadingPenalty(newLoanSummary.getOutstadingPenalty());
						sumLoanSummary.setOutstadingPrincipal(newLoanSummary.getOutstadingPrincipal());
						sumLoanSummary.setOutstadingInterest(newLoanSummary.getOutstadingInterest());
						sumLoanSummary.setOutstadingFee(newLoanSummary.getOutstadingFee());
						sumLoanSummary.setTotalOverdue(newLoanSummary.getTotalOverdue());
                        sumLoanSummary.setOverduePrincipal(newLoanSummary.getOverduePrincipal());
                        sumLoanSummary.setOverdueInterest(newLoanSummary.getOverdueInterest());
                        sumLoanSummary.setOverduePenalty(newLoanSummary.getOverduePenalty());
						sumLoanSummary.setTotalOutstanding(newLoanSummary.getTotalOutstanding());
						sumLoanSummary.setTotalDisbursed(newLoanSummary.getTotalDisbursed());
						sumLoanSummary.setLoanAmount(newLoanSummary.getLoanAmount());
					}
				}
				else{
//					loanSummary.setVersion(Long.valueOf(0));
//					loanSummary.setUuid(loanSummary.getUuid()+" в тыс. сомах по курсу "+rate);
					summaries.put(loanSummary1,loanSummary1);
					if(sumLoanSummary.getLoanAmount()!=null) {
						sumLoanSummary.setTotalPaidKGS(sumLoanSummary.getTotalPaidKGS() + loanSummary1.getTotalPaidKGS());
						sumLoanSummary.setTotalPaid(sumLoanSummary.getTotalPaid()+loanSummary1.getTotalPaid());
						sumLoanSummary.setOutstadingPenalty(sumLoanSummary.getOutstadingPenalty() + loanSummary1.getOutstadingPenalty());
						sumLoanSummary.setOutstadingPrincipal(sumLoanSummary.getOutstadingPrincipal() + loanSummary1.getOutstadingPrincipal());
						sumLoanSummary.setOutstadingInterest(sumLoanSummary.getOutstadingInterest() + loanSummary1.getOutstadingInterest());
//						sumLoanSummary.setOutstadingFee(sumLoanSummary.getOutstadingFee() + loanSummary.getOutstadingFee());
						sumLoanSummary.setTotalOverdue(sumLoanSummary.getTotalOverdue() + loanSummary1.getTotalOverdue());
                        sumLoanSummary.setOverduePrincipal(sumLoanSummary.getOverduePrincipal()+ loanSummary1.getOverduePrincipal());
                        sumLoanSummary.setOverdueInterest(sumLoanSummary.getOverdueInterest()+ loanSummary1.getOverdueInterest());
                        sumLoanSummary.setOverduePenalty(sumLoanSummary.getOverduePenalty()+ loanSummary1.getOverduePenalty());
						sumLoanSummary.setTotalOutstanding(sumLoanSummary.getTotalOutstanding() + loanSummary1.getTotalOutstanding());
						sumLoanSummary.setTotalDisbursed(sumLoanSummary.getTotalDisbursed() + loanSummary1.getTotalDisbursed());
						sumLoanSummary.setLoanAmount(sumLoanSummary.getLoanAmount() + loanSummary1.getLoanAmount());
					}
					else{
						sumLoanSummary.setTotalPaidKGS(loanSummary1.getTotalPaidKGS());
						sumLoanSummary.setTotalPaid(loanSummary1.getTotalPaid());
						sumLoanSummary.setOutstadingPenalty(loanSummary1.getOutstadingPenalty());
						sumLoanSummary.setOutstadingPrincipal(loanSummary1.getOutstadingPrincipal());
						sumLoanSummary.setOutstadingInterest(loanSummary1.getOutstadingInterest());
						sumLoanSummary.setOutstadingFee(loanSummary1.getOutstadingFee());
						sumLoanSummary.setTotalOverdue(loanSummary1.getTotalOverdue());
                        sumLoanSummary.setOverduePrincipal(loanSummary1.getOverduePrincipal()*rate);
                        sumLoanSummary.setOverdueInterest(loanSummary1.getOverdueInterest()*rate);
                        sumLoanSummary.setOverduePenalty(loanSummary1.getOverduePenalty()*rate);
						sumLoanSummary.setTotalOutstanding(loanSummary1.getTotalOutstanding());
						sumLoanSummary.setTotalDisbursed(loanSummary1.getTotalDisbursed());
						sumLoanSummary.setLoanAmount(loanSummary1.getLoanAmount());
					}
				}
			}
			model.addAttribute("debtorId",debtorId);
			model.addAttribute("debtor",debtorService.getById(debtorId));
			model.addAttribute("fromLoanList","true");
		model.addAttribute("lists",summaries);
		model.addAttribute("isCalculationSaved",false);
		model.addAttribute("totals",sumLoanSummary);
		return "/manage/debtor/loanSummary2";
	}

//	if less than zero make zero
	public Double conditional(Double num){
        if(num<0){
            return Double.valueOf(0);
        }
        else if(num==Double.valueOf(0)){
            return num;
        }
        else{
            return num/1000;
        }
    }

//    update CollectionPhases of debtors' loans'
	@RequestMapping(value = "/manage/debtor/{debtorId}/update/phases")
	public String updateCollectionPhases(@PathVariable("debtorId") Long debtorId){

		Debtor debtor=debtorService.getById(debtorId);
		for (Loan loan1:debtor.getLoans()){
			Loan loan=loanService.getById(loan1.getId());
			runUpdateOfPhases(loan);
		}
		return "redirect: /manage/debtor/{debtorId}/view";
	}

//	delete summary act of debtor
	@PostMapping("/manage/debtor/{id}/loanSummaryAct/{actId}/delete")
	public String delete(@PathVariable("id") Long id,@PathVariable("actId") Long actId){

		LoanSummaryAct loanSummaryAct=loanSummaryActService.getById(actId);
		loanSummaryActService.remove(loanSummaryAct);
		for (LoanSummary l:loanSummaryAct.getLoanSummaries()){
			LoanSummary loanSummary=loanSummaryService.getById(l.getId());
			loanSummaryService.remove(loanSummary);
		}
		return "redirect:/manage/debtor/{id}/view";
}

//	choose existing agreement for debtor and select loans
	@GetMapping("/manage/debtor/{debtorId}/collateralagreement/link")
	public String linkAgreementToDebtor(@PathVariable Long debtorId,Model model){

		Debtor debtor = debtorService.getById(debtorId);
		List<Loan> loanList = new ArrayList<>();
		for (Loan loan : debtor.getLoans()){
			Loan realLoan = loanService.getById(loan.getId());
			loanList.add(realLoan);
		}

		model.addAttribute("tLoans",loanList);
		model.addAttribute("debtorId",debtorId);
		model.addAttribute("agreementText","");


		return "/manage/debtor/collateralAgreementLinkForm";
	}

	@PostMapping("/manage/debtor/{debtorId}/collateralagreement/link")
	public String linkAgreement(@PathVariable Long debtorId,Long agreementId, String[] loanses){

        Set<Loan> loanSet = new HashSet<>();
        for (String l : loanses) {
            loanSet.add(loanService.getById(Long.valueOf(l)));
        }

        CollateralAgreement collateralAgreement = collateralAgreementService.getById(agreementId);
        for (Loan loan : collateralAgreement.getLoans()){
            Loan realLoan = loanService.getById(loan.getId());
            if (realLoan.getDebtor().getId() != debtorId){
                loanSet.add(realLoan);
            }
        }

        collateralAgreement.setLoans(loanSet);

        collateralAgreementService.update(collateralAgreement);

		return "redirect:/manage/debtor/{debtorId}/view";
	}

	public void runUpdateOfPhases(Loan loan){


		for(CollectionPhase phase1:loan.getCollectionPhases()){
			CollectionPhase phase=collectionPhaseService.getById(phase1.getId());
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
			Date startDate=phase.getStartDate();
			Date closeDate=new Date();
			CollectionProcedure procedure=collectionProcedureService.getById(phase.getCollectionProcedure().getId());
			if(phase.getCloseDate()!=null){
				closeDate=phase.getCloseDate();
			}
			else if(procedure.getCloseDate()!=null){
				closeDate=phase.getCollectionProcedure().getCloseDate();
			}

			Double sumFeeP=0.0;
			Double sumPrincipalP=0.0;
			Double sumInterestP=0.0;
			Double sumPenaltyP=0.0;
			Double sumTotalP=0.0;
			for(PhaseDetails phaseDetails1: phase.getPhaseDetails()){

				PhaseDetails phaseDetails=phaseDetailsService.getById(phaseDetails1.getId());

				List<Payment> payments=paymentService.getFromToDate(loan.getId(),startDate,closeDate);
				Double sumFeeD=0.0;
				Double sumPrincipalD=0.0;
				Double sumInterestD=0.0;
				Double sumPenaltyD=0.0;
				Double sumTotalD=0.0;
				for(Payment payment:payments){
					sumFeeD=sumFeeD+payment.getFee();
					sumPenaltyD=sumPenaltyD+payment.getPenalty();
					sumPrincipalD=sumPrincipalD+payment.getPrincipal();
					sumInterestD=sumInterestD+payment.getInterest();
					sumTotalD=sumTotalD+payment.getTotalAmount();
				}
				phaseDetails.setPaidFee(sumFeeD);
				phaseDetails.setPaidPrincipal(sumPrincipalD);
				phaseDetails.setPaidPenalty(sumPenaltyD);
				phaseDetails.setPaidInterest(sumInterestD);
				phaseDetails.setPaidTotalAmount(sumTotalD);

				phaseDetailsService.update(phaseDetails);

				sumFeeP=sumFeeP+phaseDetails.getPaidFee();
				sumPenaltyP=sumPenaltyP+phaseDetails.getPaidPenalty();
				sumPrincipalP=sumPrincipalP+phaseDetails.getPaidPrincipal();
				sumInterestP=sumInterestP+phaseDetails.getPaidInterest();
				sumTotalP=sumTotalP+phaseDetails.getPaidTotalAmount();
			}
			phase.setPaid(sumFeeP+sumPenaltyP+sumInterestP+sumPrincipalP+sumTotalP);

			collectionPhaseService.update(phase);
		}
	}

    @PostMapping("/debtorLoans/{debtorId}")
    @ResponseBody
    public String getListOfLoans(@PathVariable("debtorId") Long debtorId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getLoansByDebtorId(debtorId));
        return result;
    }

    @PostMapping("/debtorAgreements/{debtorId}")
    @ResponseBody
    public String getListOfAgreements(@PathVariable("debtorId") Long debtorId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String result = gson.toJson(getCollAgreementsByDebtorId(debtorId));
        return result;
    }

    @PostMapping("/loanSummaryActs/{debtorId}")
    @ResponseBody
    public String getListOfLoanSummaryActs(@PathVariable("debtorId") Long debtorId){
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

        Debtor debtor = debtorService.getById(debtorId);
        List<LoanSummaryAct> loanSummaryActList =this.loanSummaryActService.getLoanSummaryActByDebtor(debtor);
        List<LoanSummaryActModel> resultList = new ArrayList<>();

        for (LoanSummaryAct act : loanSummaryActList){
			LoanSummaryActModel model = new LoanSummaryActModel();

			model.setId(act.getId());
			model.setAmount(act.getAmount());
			model.setOnDate(act.getOnDate());

			resultList.add(model);
		}
		Collections.sort(resultList);

        String result = gson.toJson(resultList);
        return result;
    }

	@PostMapping("/debtorProcedures/{debtorId}")
	@ResponseBody
	public String getListOfProcedures(@PathVariable("debtorId") Long debtorId){
		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String result = gson.toJson(getProcsByDebtorId(debtorId));
		return result;
	}

    @PostMapping("/debtor/type/instantUpdate")
	@ResponseBody
	public String updateDebtorType(@RequestParam("id") Long id,@RequestParam("data") Long data){
		Debtor debtor=debtorService.getById(id);
		debtor.setDebtorType(debtorTypeService.getById(data));
		debtorService.update(debtor);
		return "OK";
	}

    @PostMapping("/debtor/description/update")
    @ResponseBody
    public String updateDebtorDescription(@RequestParam("id") Long id,@RequestParam("data") String data){
        Debtor debtor=debtorService.getById(id);
        debtor.setDescription(data);
        debtorService.update(debtor);
        return "OK";
    }

	private List<LoanModel> getLoansByDebtorId(long debtorId)
	{
		String baseQuery = "SELECT loan.id, loan.loan_class_id, loan.regNumber, loan.regDate, loan.amount, loan.currencyId, currency.name as currencyName,\n" +
				"  loan.loanTypeId, type.name as loanTypeName, loan.loanStateId, state.name as loanStateName,\n" +
				"  loan.supervisorId, IFNULL(loan.parent_id, 0) as parentLoanId, 0.0 as remainder, loan.creditOrderId\n" +
				"FROM loan loan, orderTermCurrency currency, loanType type, loanState state\n" +
				"WHERE loan.currencyId = currency.id\n" +
				"  AND loan.loanTypeId = type.id\n" +
				"  AND loan.loanStateId = state.id and loan.loanStateId = 2 \n" +
				"  AND loan.debtorId =" + debtorId + "\n" +
				"  AND  ISNULL(loan.parent_id) \n" +
                "ORDER BY  loan.regDate DESC";

		Query query = entityManager.createNativeQuery(baseQuery, LoanModel.class);

		List<LoanModel> loans = query.getResultList();

		for(LoanModel l : loans){
			Double rem = getRemainingOfLoan(l.getId());
			if(rem != -777){
				l.setRemainder(rem);
			}
		}

		return loans;
	}

	private List<CollateralAgreementModel> getCollAgreementsByDebtorId(long debtorId)
	{
	    Map<Long, CollateralAgreementModel> models = new HashMap<>();
	    Debtor debtor = debtorService.getById(debtorId);
	    for (Loan loan1: debtor.getLoans())
        {
        	Loan loan=loanService.getById(loan1.getId());
            Set<CollateralAgreement> agreements = loan.getCollateralAgreements();

            for(CollateralAgreement agreement1: agreements)
            {
				CollateralAgreement agreement=collateralAgreementService.getById(agreement1.getId());
                List<CollateralItem> items = collateralItemReposiory.findByCollateralAgreementId(agreement.getId());

				Double remainder = 0.0;
                for (Loan loan2: agreement.getLoans()){
					Double value = getRemainingOfLoan(loan2.getId());
					if(value == -777.0){
						remainder = -777.0;
					}
					else if(remainder != -777.0){
						remainder = remainder + value;
					}
				}

                for(CollateralItem item1: items)
                {
					CollateralItem item=collateralItemService.getById(item1.getId());
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
                    model.setStatus(item.getStatus());
                    model.setNum_of_loans(agreement.getLoans().size());
                    model.setRemaining(remainder);
                    model.setEstimatedValue(item.getEstimatedValue());

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
		String firstQuery="select cp.id as id,cph.id as phaseId,cphs.id as procedureStatusId,cps.name as procedureStatus,cpht.id as phaseTypeId,cpht.name as phaseType,cphs.id as phaseStatusId,cphs.name as phaseStatus,\n" +
				"       cph.startDate as startDate,cph.closeDate as closeDate\n" +
				"from collectionProcedure cp,collectionPhase cph,loan l,loanCollectionPhase lcph,procedureStatus cps,phaseType cpht,\n" +
				"     phaseStatus cphs\n" +
				"where lcph.loanId=l.id and cph.id=lcph.collectionPhaseId and cph.collectionProcedureId=cp.id\n" +
				"and cps.id=cp.procedureStatusId and cpht.id=cph.phaseTypeId and cph.record_status=1 and cphs.id=cph.phaseStatusId and l.debtorId="+String.valueOf(debtorId)+" group by id,phaseId order by id desc, startDate desc";
		Query query1=entityManager.createNativeQuery(firstQuery, ProcedureModel.class);
		List<ProcedureModel> procedureModelList=query1.getResultList();
		for (ProcedureModel procedureModel:procedureModelList){
			CollectionProcedureModel model = new CollectionProcedureModel();
			model.setId(procedureModel.getId());
			model.setProcedureStatusId(procedureModel.getProcedureStatusId());
			model.setProcedureStatusName(procedureModel.getProcedureStatus());
			model.setPhaseId(procedureModel.getPhaseId());
			model.setPhaseTypeId(procedureModel.getPhaseTypeId());
			model.setPhaseTypeName(procedureModel.getPhaseType());
			model.setStartDate(procedureModel.getStartDate());

			String secondQuery="select sum(phd.startTotalAmount) as totalStartAmount,sum(phd.closeTotalAmount) as totalCloseAmount from phaseDetails phd where collectionPhaseId="+String.valueOf(procedureModel.getPhaseId());
			Query query2=entityManager.createNativeQuery(secondQuery, ProcedurePhaseDetailsModel.class);
			ProcedurePhaseDetailsModel procedurePhaseDetailsModel= (ProcedurePhaseDetailsModel) query2.getSingleResult();

			model.setStartTotalAmount(procedurePhaseDetailsModel.getTotalStartAmount());
			model.setPhaseStatusId(procedureModel.getPhaseStatusId());
			model.setPhaseStatusName(procedureModel.getPhaseStatus());
			model.setCloseDate(procedureModel.getCloseDate());
			if(procedurePhaseDetailsModel.getTotalCloseAmount()!=null)
			model.setCloseTotalAmount(procedurePhaseDetailsModel.getTotalCloseAmount());
			else
			model.setCloseTotalAmount(Double.valueOf(0));


			if(!models.containsKey(model.getPhaseId()))
				models.put(model.getPhaseId(), model);

		}
//		for (Loan loan1: debtor.getLoans()
//				) {
//
//			Loan loan=loanService.getById(loan1.getId());
//			Set<CollectionPhase> phases = loan.getCollectionPhases();
//			for (CollectionPhase phase1: phases
//					) {
//				CollectionPhase phase=collectionPhaseService.getById(phase1.getId());
//				CollectionProcedure proc1 = phase.getCollectionProcedure();
//				CollectionProcedure proc=collectionProcedureService.getById(proc1.getId());
//
//				CollectionProcedureModel model = new CollectionProcedureModel();
//				model.setId(proc.getId());
//				model.setProcedureStatusId(proc.getProcedureStatus().getId());
//				model.setProcedureStatusName(proc.getProcedureStatus().getName());
//				model.setPhaseId(phase.getId());
//				model.setPhaseTypeId(phase.getPhaseType().getId());
//				model.setPhaseTypeName(phase.getPhaseType().getName());
//				model.setStartDate(phase.getStartDate());
//				double totalStartAmount = 0.0;
//				for(PhaseDetails details: phase.getPhaseDetails())
//				{
//                    if(details.getStartTotalAmount()!=null)
//					totalStartAmount += details.getStartTotalAmount();
//				}
//				model.setStartTotalAmount(totalStartAmount);
//
//				model.setPhaseStatusId(phase.getPhaseStatus().getId());
//				model.setPhaseStatusName(phase.getPhaseStatus().getName());
//
//				model.setCloseDate(phase.getCloseDate());
//				double totalClosetAmount = 0.0;
//				for(PhaseDetails details: phase.getPhaseDetails())
//				{
//				    if(details.getCloseTotalAmount()!=null)
//					    totalClosetAmount += details.getCloseTotalAmount();
//				}
//				model.setCloseTotalAmount(totalClosetAmount);
//
//				if(!models.containsKey(model.getPhaseId()))
//					models.put(model.getPhaseId(), model);
//			}
//		}

        List<CollectionProcedureModel> result = new ArrayList<>();
        for(CollectionProcedureModel model: models.values())
            result.add(model);

        Collections.sort(result);
        return result;
	}

	private int countLoansOfItem(Long itemId){

		String baseQuery = "select count(1)\n" +
				"from collectionPhase p where collectionProcedureId=1 and p.record_status=1";
		Query query = entityManager.createNativeQuery(baseQuery);

		BigInteger count = (BigInteger) query.getResultList().get(0);

		return count.intValue();

	}

	private Double getRemainingOfLoan(Long loanId){

	    String queryStr = "select l.totalOutstanding\n" +
                "from loanSummary l where loanSummaryType='DAILY' " +
                "and onDate =CURRENT_DATE and loanId="+loanId;

	    Query query = entityManager.createNativeQuery(queryStr);
	    try{
			Double result = (Double) query.getResultList().get(0);
			return result;
		}
	    catch (Exception e){
	    	return -777.0;
		}
    }

}
