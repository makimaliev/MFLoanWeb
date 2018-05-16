package kg.gov.mf.loan.web.controller.manage;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import kg.gov.mf.loan.manage.dao.EntitySpecificationsBuilder;
import kg.gov.mf.loan.manage.dao.debtor.DebtDao;
import kg.gov.mf.loan.manage.model.collateral.CollateralAgreement;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;
import kg.gov.mf.loan.manage.model.collection.CollectionProcedure;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.util.SearchOperation;
import kg.gov.mf.loan.web.util.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	DebtDao debtDao;

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = {5, 10, 20, 50, 100};
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	/*
	@RequestMapping(value = { "/manage/debtor/{debtorId}/view"})
    public String viewOrder(ModelMap model, @PathVariable("debtorId")Long debtorId) {


		Debtor debtor = debtorService.getById(debtorId);
        model.addAttribute("debtor", debtor);

        model.addAttribute("loans", debtor.getLoans());
        Set<CollateralAgreement> allAgreements = new HashSet<>();
        Set<CollectionProcedure> procs = new HashSet<>();
		for (Loan loan: debtor.getLoans()
			 ) {
			Set<CollateralAgreement> agreements = loan.getCollateralAgreements();
			for (CollateralAgreement agreement: agreements
				 ) {
				allAgreements.add(agreement);
			}

			CollectionPhase phase = loan.getCollectionPhase();
			if(phase != null){
				if(phase.getCollectionProcedure() != null)
					procs.add(phase.getCollectionProcedure());
			}
		}

        model.addAttribute("agreements", allAgreements);
        model.addAttribute("procs", procs);

        List<CreditOrder> orders = orderService.list();
        model.addAttribute("orders", orders);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/view";
    }
	*/
	@RequestMapping(value = { "/manage/debtor/", "/manage/debtor/list" }, method = RequestMethod.GET)
    public String listDebtors(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<Debtor> debtors = debtorService.listByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = debtorService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("debtor", new Debtor());
		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("debtors", debtors);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/list";
    }

	@RequestMapping(method = RequestMethod.GET, value = "/manage/debtor/search")
	public String findAllBySpecification(@RequestParam(value = "q") String q,
										 @RequestParam("pageSize") Optional<Integer> pageSize,
										 @RequestParam("page") Optional<Integer> page, ModelMap model) {
		EntitySpecificationsBuilder builder = new EntitySpecificationsBuilder();
		String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
		Pattern pattern = Pattern.compile(
				"(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
		Matcher matcher = pattern.matcher(q + ",");
		while (matcher.find()) {
			builder.with(
					matcher.group(1),
					matcher.group(2),
					matcher.group(4),
					matcher.group(3),
					matcher.group(5));
		}

		Specification<Debtor> spec = builder.build();

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
		Pageable pageable = new PageRequest(0, 20, sort);

		Page<Debtor> page1 = debtDao.findAll(spec, pageable);
		List<Debtor> debtors = page1.getContent();

		//List<Debtor> debtors = debtDao.findAll(spec);
		int count = debtDao.findAll(spec, pageable).getTotalPages();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("debtor", new Debtor());
		model.addAttribute("count", count);
		model.addAttribute("debtors", debtors);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("loggedinuser", Utils.getPrincipal());

		return "/manage/debtor/list";
	}
}
