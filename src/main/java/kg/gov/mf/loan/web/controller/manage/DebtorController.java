package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.DebtorType;
import kg.gov.mf.loan.manage.model.debtor.OrganizationForm;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.model.debtor.WorkSector;
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
        
        model.addAttribute("loans", debtor.getLoans());
        model.addAttribute("agreements", agreementService.list());
        model.addAttribute("procs", procService.list());
        
        List<CreditOrder> orders = orderService.list();
        model.addAttribute("orders", orders);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/view";
    }
	
	@RequestMapping(value = { "/manage/debtor/", "/manage/debtor/list" }, method = RequestMethod.GET)
    public String listDebtors(ModelMap model) {
		
        List<Debtor> debtors = debtorService.list();
        model.addAttribute("debtors", debtors);

        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/list";
    }

	@RequestMapping(value="/manage/debtor/{debtorId}/save", method=RequestMethod.GET)
	public String formDebtor(Model model, @PathVariable("debtorId")Long debtorId)
	{
		List<Owner> entities = new ArrayList<Owner>();
		List<Person> persons = personService.findAll();
		List<Organization> orgs = orgService.findAll();
		for (Person p : persons) {
			entities.add(new Owner(OwnerType.PERSON, p));
		}
		
		for (Organization org : orgs) {
			entities.add(new Owner(OwnerType.ORGANIZATION, org));
		}
		
		model.addAttribute("entities", entities);
		
		List<DebtorType> types = debtorTypeService.list();
		model.addAttribute("types", types);

		List<OrganizationForm> forms = formService.list();
		model.addAttribute("forms", forms);

		List<WorkSector> sectors = sectorService.list();
		model.addAttribute("sectors", sectors);
		
		if(debtorId == 0)
		{
			Debtor debtor = new Debtor();
			Owner owner = new Owner();
			owner.setId(-1);
			debtor.setOwner(owner);
			model.addAttribute("debtor", debtor);
		}

		if(debtorId > 0)
		{
			Debtor debtor = debtorService.getById(debtorId);
			Owner owner = debtor.getOwner();
			for (Owner entity : entities) {
				if(owner.getEntityId() == entity.getEntityId() && owner.getOwnerType().equals(entity.getOwnerType())) {
					entities.remove(entity);
					entities.add(owner);
					break;
				}
			}
			model.addAttribute("debtor", debtor);
		}
		
		return "/manage/debtor/save";
	}
	
	@RequestMapping(value="/manage/debtor/save", method=RequestMethod.POST)
	public String saveDebtor(Debtor debtor)
	{
		if(debtor.getId() == 0)
			debtorService.add(debtor);
		else
		{
			Owner oldOwner = debtorService.getById(debtor.getId()).getOwner();
			debtorService.update(debtor);
			ownerService.remove(oldOwner);
		}
			
		return "redirect:" + "/manage/debtor/list";
	}
	
	@RequestMapping(value="/manage/debtor/delete", method=RequestMethod.POST)
    public String deleteDebtor(long id) {
		if(id > 0)
			debtorService.remove(debtorService.getById(id));
        return "redirect:" + "/manage/debtor/list";
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
	
}
