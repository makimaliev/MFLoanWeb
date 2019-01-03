package kg.gov.mf.loan.web.controller.admin.org;

import kg.gov.mf.loan.admin.org.repository.OrganizationRepository;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.web.util.Pager;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;

import kg.gov.mf.loan.admin.sys.service.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class OrganizationController {
	
	@Autowired
    private OrganizationService organizationService;
	
    public void setOrganizationService(OrganizationService rs)
    {
        this.organizationService = rs;
    }

    
	@Autowired
    private AddressService addressService;
	
    public void setAddressService(AddressService rs)
    {
        this.addressService = rs;
    }
    
	@Autowired
    private RegionService regionService;
	
    public void setRegionService(RegionService rs)
    {
        this.regionService = rs;
    }    
    

	@Autowired
    private DistrictService districtService;
	
    public void setDistrictService(DistrictService ds)
    {
        this.districtService = ds;
    }    
    
    
	@Autowired
    private AokmotuService aokmotuService;
	
    public void setAokmotuService(AokmotuService ds)
    {
        this.aokmotuService = ds;
    }    
    
	@Autowired
    private VillageService villageService;
	
    public void setVillageService(VillageService ds)
    {
        this.villageService = ds;
    }
    
    
	@Autowired
    private IdentityDocTypeService identityDocTypeService;
	
    public void setIdentityDocTypeService(IdentityDocTypeService rs)
    {
        this.identityDocTypeService = rs;
    }
    
	@Autowired
    private IdentityDocService identityDocService;
	
    public void setIdentityDocService(IdentityDocService rs)
    {
        this.identityDocService = rs;
    }    
    
	@Autowired
    private IdentityDocGivenByService identityDocGivenByService;
	
    public void setIdentityDocGivenByService(IdentityDocGivenByService rs)
    {
        this.identityDocGivenByService = rs;
    }    
    
    
	@Autowired
    private OrgFormService orgFormService;
     
    public void setOrgFormService(OrgFormService rs)
    {
        this.orgFormService = rs;
    }    
    
    
	@Autowired
    private PositionService positionService;
     
    public void setPositionService(PositionService rs)
    {
        this.positionService = rs;
    }


    @Autowired
	private OwnerService ownerService;
    
    
    
	@Autowired
    private InformationService informationService;



	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	DebtorService debtorService;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 10;
	private static final int[] PAGE_SIZES = {5, 10, 20, 50, 100};
	
    public void setInformationService(InformationService rs)
    {
        this.informationService = rs;
    } 
    
    
//	@RequestMapping(value = "/organization/list", method = RequestMethod.GET)
//	public String listOrganizations(Model model) {
//
//		model.addAttribute("organization", new Organization());
//		model.addAttribute("organizationList", this.organizationService.findLast100());
//		return "admin/org/organizationList";
//	}

	@RequestMapping(value = { "/organization", "/organization/list" }, method = RequestMethod.GET)
	public String listDebtors(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<Organization> organizations = organizationService.findByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = organizationService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("organization", new Organization());
		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("organizationList", organizations);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("districts", districtService.findAll());

		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/admin/org/organizationList";
	}
	
	
//	@RequestMapping(value = "/organization/table", method = RequestMethod.GET)
//	public String showOrganizationTable(Model model) {
//		model.addAttribute("organization", new Organization());
//		model.addAttribute("organizationList", this.organizationService.findLast100());
//
//		return "admin/org/organizationTable";
//	}
	
	@RequestMapping("/organization/{id}/view")
	public String viewOrganizationById(@PathVariable("id") long id, Model model) {

		Organization organization = this.organizationService.findById(id);


		List<Position> organizationPositionList = new ArrayList<Position>();

		for (Department department:organization.getDepartment())
		{
			organizationPositionList.addAll(this.positionService.findByDepartment(department));
		}

		model.addAttribute("organization", organization);
		model.addAttribute("positionList", organizationPositionList);
		model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, organization.getId()));		
		

		return "admin/org/organizationView";
	}
	
	
	@RequestMapping("/organization/{id}/details")
	public String viewOrganizationDetailsById(@PathVariable("id") long id, Model model) {

		Organization organization = this.organizationService.findById(id);

		List<Position> organizationPositionList = new ArrayList<Position>();

		for (Department department:organization.getDepartment())
		{
			organizationPositionList.addAll(this.positionService.findByDepartment(department));
		}

		model.addAttribute("organization", organization);
		model.addAttribute("positionList", organizationPositionList);
		model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, organization.getId()));		
		

		return "admin/org/organizationDetails";
	}
    
	
	@RequestMapping(value = "/organization/add", method = RequestMethod.GET)
	public String getOrganizationAddForm(Model model) {

		Organization modelOrganization = new Organization();

		OrgForm modelOrgForm = new OrgForm();
		modelOrgForm.setId(1);
		modelOrganization.setOrgForm(modelOrgForm);




		Address modelAddress = new Address();
		modelAddress.setId(0);

		Region modelRegion = (Region) this.regionService.findById(1);

		District modelDistrict = (District) this.districtService.findByRegion(modelRegion).get(0);

		Aokmotu modelAokmotu = (Aokmotu) this.aokmotuService.findByDistrict(modelDistrict).get(0);

		Village modelVillage = (Village) this.villageService.findByAokmotu(modelAokmotu).get(0);


		modelAddress.setRegion(modelRegion);
		modelAddress.setDistrict(modelDistrict);
		modelAddress.setAokmotu(modelAokmotu);
		modelAddress.setVillage(modelVillage);

		modelOrganization.setAddress(modelAddress);

		IdentityDoc modelIdentityDoc = new IdentityDoc();
		modelIdentityDoc.setId(0);
		
		IdentityDocType modelIdentityDocType = new IdentityDocType();
		modelIdentityDocType.setId(1);
		modelIdentityDoc.setIdentityDocType(modelIdentityDocType);
		
		IdentityDocGivenBy modelIdentityDocGivenBy = new IdentityDocGivenBy();
		modelIdentityDocGivenBy.setId(1);
		modelIdentityDoc.setIdentityDocGivenBy(modelIdentityDocGivenBy);

		modelOrganization.setIdentityDoc(modelIdentityDoc);
		

		
		model.addAttribute("organization", modelOrganization);
		
		model.addAttribute("regionList", this.regionService.findAll());
		model.addAttribute("districtList", this.districtService.findByRegion(modelRegion));
		model.addAttribute("aokmotuList", this.aokmotuService.findByDistrict(modelDistrict));
		model.addAttribute("villageList", this.villageService.findByAokmotu(modelAokmotu));
		model.addAttribute("identityDocTypeList", this.identityDocTypeService.findAll());
		model.addAttribute("identityDocGivenByList", this.identityDocGivenByService.findAll());		
		model.addAttribute("orgFormList", this.orgFormService.findAll());
		
		return "admin/org/organizationForm";
	}
	
	

	@RequestMapping("/organization/{id}/edit")
	public String getOrganizationEditForm(@PathVariable("id") long id, Model model) {

    	Organization organization = this.organizationService.findById(id);

		model.addAttribute("organization", organization);
		model.addAttribute("regionList", this.regionService.findAll());


//		model.addAttribute("districtList", this.districtService.findAll());
//		model.addAttribute("aokmotuList", this.aokmotuService.findAll());
//		model.addAttribute("villageList", this.villageService.findAll());


		model.addAttribute("districtList", this.districtService.findByRegion(organization.getAddress().getRegion()));
		model.addAttribute("aokmotuList", this.aokmotuService.findByDistrict(organization.getAddress().getDistrict()));

		if (organization.getAddress().getAokmotu() != null)
		{
			model.addAttribute("villageList", this.villageService.findByAokmotu(organization.getAddress().getAokmotu()));
		}
		else
		{
			model.addAttribute("villageList", this.villageService.findById(1));
		}

		model.addAttribute("identityDocTypeList", this.identityDocTypeService.findAll());
		model.addAttribute("identityDocGivenByList", this.identityDocGivenByService.findAll());		
		model.addAttribute("orgFormList", this.orgFormService.findAll());		
		
		return "admin/org/organizationForm";

	}

	@RequestMapping(value = "/organization/save", method = RequestMethod.POST)
	public String saveOrganizationAndRedirectToOrganizationList(@Validated @ModelAttribute("organization") Organization organization, BindingResult result) {

		organization.setName(organization.getIdentityDoc().getIdentityDocDetails().getFullname());

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (organization.getId() == 0) {
			
//			organization.setAddress(this.addressService.findById(organization.getAddress().getId()));
//			organization.setIdentityDoc(this.identityDocService.findById(organization.getIdentityDoc().getId()));
			
			//organization.setOrgForm(this.orgFormService.findById(organization.getOrgForm().getId()));

			organization.setName(organization.getIdentityDoc().getIdentityDocDetails().getFullname());

			this.organizationService.create(organization);

			Owner newOwner = new Owner();

			newOwner.setName(organization.getName());
			newOwner.setEntityId(organization.getId());
			newOwner.setOwnerType(OwnerType.ORGANIZATION);
			newOwner.setAddress(organization.getAddress());

			this.ownerService.add(newOwner);

		} else {
			this.organizationService.edit(organization);
			Owner owner=this.ownerService.getByEntityId(organization.getId(),"ORGANIZATION");
			owner.setName(organization.getName());
			this.ownerService.update(owner);
			try{
				Debtor debtor=debtorService.getByOwnerId(owner.getId());
				debtor.setName(owner.getName());
				debtorService.update(debtor);
			}
			catch (Exception e){
				System.out.println(e);
			}
		}



		return "redirect:/organization/"+organization.getId()+"/details";

	}

	@RequestMapping("/organization/{id}/remove")
	public String removeOrganizationAndRedirectToOrganizationList(@PathVariable("id") long id) {

    	Organization organization = this.organizationService.findById(id);
		this.organizationService.delete(organization);

		return "redirect:/organization/list";
	}


	/*
	@RequestMapping(method = RequestMethod.GET, value = "/organization/search")
	public String findAllBySpecification(@RequestParam(value = "q") String q,
										 @RequestParam("pageSize") Optional<Integer> pageSize,
										 @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
		Pageable pageable = new PageRequest(evalPage, evalPageSize, sort);

		QOrganization organization = QOrganization.organization;

		BooleanExpression hasNameLike = organization.name.like("%" + q + "%");
		Page<Organization> page1 = organizationRepository.findAll(hasNameLike, pageable);
		List<Organization> organizations = page1.getContent();

		int count = organizationRepository.findAll(hasNameLike, pageable).getTotalPages();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("organization", new Organization());
		model.addAttribute("count", count);
		model.addAttribute("organizationList", organizations);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);
		model.addAttribute("q", q);

		model.addAttribute("loggedinuser", Utils.getPrincipal());

		return "/admin/org/organizationList";
	}
	*/

}
