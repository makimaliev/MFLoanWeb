package kg.gov.mf.loan.web.controller.admin.org;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.repository.PersonRepository;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.web.fetchModels.PersonOrganizationDocModel;
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

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class PersonController {

	@Autowired
	EntityManager entityManager;
	
	@Autowired
    private PersonService personService;
	
    public void setPersonService(PersonService rs)
    {
        this.personService = rs;
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
    private InformationService informationService;

    @Autowired
	private OwnerService ownerService;
	
    public void setInformationService(InformationService rs)
    {
        this.informationService = rs;
    }


	@Autowired
	PersonRepository personRepository;

    @Autowired
	DebtorService debtorService;

    @Autowired
	StaffService staffService;

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


    
//	@RequestMapping(value = "/person/list", method = RequestMethod.GET)
//	public String listPersons(Model model) {
//
//		model.addAttribute("person", new Person());
//		model.addAttribute("personList", this.personService.findLast100());
//		return "admin/org/personList";
//	}


	@RequestMapping(value = { "/person/", "/person/list" }, method = RequestMethod.GET)
	public String listDebtors(@RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		List<Person> persons = personService.findByParam("id", evalPage*evalPageSize, evalPageSize);
		int count = personService.count();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("person", new Person());
		model.addAttribute("count", count/evalPageSize+1);
		model.addAttribute("personList", persons);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);

		model.addAttribute("districts", districtService.findAll());
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "/admin/org/personList";
	}


	
//	@RequestMapping(value = "/person/table", method = RequestMethod.GET)
//	public String showPersonTable(Model model) {
//		model.addAttribute("person", new Person());
//		model.addAttribute("personList", this.personService.findLast100());
//
//		return "admin/org/personTable";
//	}
	
	@RequestMapping("/person/{id}/view")
	public String viewPersonById(@PathVariable("id") long id, Model model) {

		Person person = this.personService.findById(id);

		model.addAttribute("person", person);
//		model.addAttribute("positionList", this.positionService.findAll());
		model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, person.getId()));		
		

		return "admin/org/personView";
	}

	@RequestMapping("/person/{id}/details")
	public String viewPersonDetailsById(@PathVariable("id") long id, Model model) {

		Person person = this.personService.findById(id);
		String hasDebtor="true";
		try{
			Owner owner=this.ownerService.getByEntityId(person.getId(),"PERSON");
			Debtor debtor=debtorService.getByOwnerId(owner.getId());
			model.addAttribute("debtorId",debtor.getId());
		}
		catch (Exception e){
			hasDebtor="false";
			model.addAttribute("debtorId",0);
		}
		model.addAttribute("docs",getDocs(id));
		model.addAttribute("hasDebtor", hasDebtor);
		model.addAttribute("person", person);
//		model.addAttribute("positionList", this.positionService.findAll());
		model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, person.getId()));


		return "admin/org/personDetails";
	}
    
	
	@RequestMapping(value = "/person/add", method = RequestMethod.GET)
	public String getPersonAddForm(Model model) {

		
		Person modelPerson = new Person();
		
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

		
		
		modelPerson.setAddress(modelAddress);

		IdentityDoc modelIdentityDoc = new IdentityDoc();
		modelIdentityDoc.setId(0);
		
		IdentityDocType modelIdentityDocType = new IdentityDocType();
		modelIdentityDocType.setId(2);
		modelIdentityDoc.setIdentityDocType(modelIdentityDocType);
		
		IdentityDocGivenBy modelIdentityDocGivenBy = new IdentityDocGivenBy();
		modelIdentityDocGivenBy.setId(1);
		modelIdentityDoc.setIdentityDocGivenBy(modelIdentityDocGivenBy);

		modelPerson.setIdentityDoc(modelIdentityDoc);
		

		
		model.addAttribute("person", modelPerson);
		
		model.addAttribute("regionList", this.regionService.findAll());
		model.addAttribute("districtList", this.districtService.findByRegion(modelRegion));
		model.addAttribute("aokmotuList", this.aokmotuService.findByDistrict(modelDistrict));
		model.addAttribute("villageList", this.villageService.findByAokmotu(modelAokmotu));
		model.addAttribute("identityDocTypeList", this.identityDocTypeService.findAll());
		model.addAttribute("identityDocGivenByList", this.identityDocGivenByService.findAll());		

		return "admin/org/personForm";
	}
	
	

	@RequestMapping("/person/{id}/edit")
	public String getPersonEditForm(@PathVariable("id") long id, Model model) {

		Person person = this.personService.findById(id);

		model.addAttribute("person", person);

		model.addAttribute("regionList", this.regionService.findAll());
		model.addAttribute("districtList", this.districtService.findByRegion(person.getAddress().getRegion()));
		model.addAttribute("aokmotuList", this.aokmotuService.findByDistrict(person.getAddress().getDistrict()));

		if (person.getAddress().getAokmotu() != null)
		{
			model.addAttribute("villageList", this.villageService.findByAokmotu(person.getAddress().getAokmotu()));
		}
		else
		{
			model.addAttribute("villageList", this.villageService.findById(1));
		}



		model.addAttribute("identityDocTypeList", this.identityDocTypeService.findAll());
		model.addAttribute("identityDocGivenByList", this.identityDocGivenByService.findAll());		
		model.addAttribute("orgFormList", this.orgFormService.findAll());		
		
		return "admin/org/personForm";

	}

	@RequestMapping(value = "/person/save", method = RequestMethod.POST)
	public String savePersonAndRedirectToPersonList(@Validated @ModelAttribute("person") Person person, BindingResult result,String fromPerson) {



		IdentityDocDetails identityDocDetails = person.getIdentityDoc().getIdentityDocDetails();

		identityDocDetails.setFirstname(identityDocDetails.getFirstname().substring(0,identityDocDetails.getFirstname().indexOf(",")));
		identityDocDetails.setLastname(identityDocDetails.getLastname().substring(0,identityDocDetails.getLastname().indexOf(",")));
		identityDocDetails.setMidname(identityDocDetails.getMidname().substring(0,identityDocDetails.getMidname().indexOf(",")));

		person.getIdentityDoc().getIdentityDocDetails().setFullname(person.getIdentityDoc().getIdentityDocDetails().getLastname() +" "+person.getIdentityDoc().getIdentityDocDetails().getFirstname()+" "+person.getIdentityDoc().getIdentityDocDetails().getMidname());


		person.setName(person.getIdentityDoc().getIdentityDocDetails().getFullname());


		if (result.hasErrors()) {

		}
		else if (person.getId() == 0) {
			
//			person.setAddress(this.addressService.findById(person.getAddress().getId()));
//			person.setIdentityDoc(this.identityDocService.findById(person.getIdentityDoc().getId()));
			
			//person.setOrgForm(this.orgFormService.findById(person.getOrgForm().getId()));
			if(person.getIdentityDoc().getIdentityDocType().getId()==1){
				person.getIdentityDoc().setIdentityDocGivenBy(identityDocGivenByService.findById(2));
			}
			else if(person.getIdentityDoc().getIdentityDocType().getId()==2){
				person.getIdentityDoc().setIdentityDocGivenBy(identityDocGivenByService.findById(1));
			}


			this.personService.create(person);

			Owner newOwner = new Owner();

			newOwner.setName(person.getName());
			newOwner.setEntityId(person.getId());
			newOwner.setOwnerType(OwnerType.PERSON);
			newOwner.setAddress(person.getAddress());

			this.ownerService.add(newOwner);

		}
		else {
			if(person.getIdentityDoc().getIdentityDocType().getId()==1){
				person.getIdentityDoc().setIdentityDocGivenBy(identityDocGivenByService.findById(2));
			}
			else if(person.getIdentityDoc().getIdentityDocType().getId()==2){
				person.getIdentityDoc().setIdentityDocGivenBy(identityDocGivenByService.findById(1));
			}
			Owner owner;
			Address address=person.getAddress();
			if (address.getAokmotu()==null){
				Aokmotu aokmotu=aokmotuService.findById(1L);
				address.setAokmotu(aokmotu);
//				addressService.edit(address);
			}
			addressService.edit(address);
			person.setAddress(address);
			personService.edit(person);
			try{
				owner=this.ownerService.getByEntityId(person.getId(),"PERSON");
				owner.setName(person.getName());
				this.ownerService.update(owner);
			}
			catch (Exception e){
				owner=new Owner();
				owner.setName(person.getName());
				owner.setOwnerType(OwnerType.PERSON);
				owner.setEntityId(person.getId());
				owner.setAddress(person.getAddress());
				ownerService.add(owner);
			}
			try{
				Staff staff=staffService.findByPerson(person);
				staff.setName(owner.getName());
				staffService.edit(staff);
			}
			catch(Exception e){

			}
			try{
				Debtor debtor=debtorService.getByOwnerId(owner.getId());
				debtor.setName(person.getName());
				debtorService.update(debtor);
			}
			catch (Exception e){

			}

		}

		if(fromPerson.equals("true") || fromPerson.equals("")){
			return "redirect:/person/"+person.getId()+"/details";
		}
		else{
			String[] splittedVal=fromPerson.split("-");
			return "redirect:/manage/debtor/"+splittedVal[0]+"/loan/"+splittedVal[1]+"/guarantoragreement/0/save";
		}

	}

	@RequestMapping("/person/{id}/remove")
	public String removePersonAndRedirectToPersonList(@PathVariable("id") long id) {

		this.personService.delete(this.personRepository.findOne(id));

		return "redirect:/person/list";
	}


	/*
	@RequestMapping(method = RequestMethod.GET, value = "/person/search")
	public String findAllBySpecification(@RequestParam(value = "q") String q,
										 @RequestParam("pageSize") Optional<Integer> pageSize,
										 @RequestParam("page") Optional<Integer> page, ModelMap model) {

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Sort sort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
		Pageable pageable = new PageRequest(evalPage, evalPageSize, sort);

		QPerson person = QPerson.person;

		BooleanExpression hasNameLike = person.name.like("%" + q + "%");
		Page<Person> page1 = personRepository.findAll(hasNameLike, pageable);
		List<Person> persons = page1.getContent();

		int count = personRepository.findAll(hasNameLike, pageable).getTotalPages();

		Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

		model.addAttribute("person", new Person());
		model.addAttribute("count", count);
		model.addAttribute("personList", persons);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("current", evalPage);
		model.addAttribute("q", q);

		model.addAttribute("loggedinuser", Utils.getPrincipal());

		return "/admin/org/personList";
	}
	*/

	class Result {

		public Long id;
		public String text;

		public Result(Long id, String text) {
			this.id = id;
			this.text = text;
		}
	}



	@RequestMapping(value = "/personByName/list", method = RequestMethod.GET)
	public @ResponseBody

	List<Result> findPersonByName(@RequestParam(value = "name", required = true) String name) {

		List<Result> data = new ArrayList<>();
		 int counter=0;
		for(Person person : personRepository.findByNameContains(name))
		{
			counter++;
			data.add(new Result(person.getId(), person.getName()));
			if(counter>10) break;
		}

		return data;

//		return personRepository.findByNameContains(name);
	}
     


	public String  getDocs(Long id){

		String baseQuery="select d.title,d.id,dt.name as type\n" +
				"from df_document d,person p,cat_responsible_person rp,cat_document_type dt\n" +
				"where (d.receiverResponsible=rp.Responsible_id or d.senderResponsible=rp.Responsible_id) " +
				"and dt.id=d.documentType and rp.person_id="+id+" group by d.id";
		Query query=entityManager.createNativeQuery(baseQuery,PersonOrganizationDocModel.class);

		List<PersonOrganizationDocModel> list=query.getResultList();

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String result = gson.toJson(list);

		return result;
	}
     

}
