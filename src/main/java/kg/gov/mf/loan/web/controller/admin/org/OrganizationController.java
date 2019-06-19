package kg.gov.mf.loan.web.controller.admin.org;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.repository.OrganizationRepository;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.admin.sys.model.Attachment;
import kg.gov.mf.loan.admin.sys.model.Information;
import kg.gov.mf.loan.admin.sys.model.SystemFile;
import kg.gov.mf.loan.admin.sys.service.AttachmentService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.SystemFileService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.repository.org.StaffRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.web.fetchModels.PersonOrganizationDocModel;
import kg.gov.mf.loan.web.fetchModels.SystemFileModel;
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

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrganizationController {

	@Autowired
	EntityManager entityManager;

	@Autowired
	PersonService personService;

	@Autowired
	DepartmentService departmentService;
	
	@Autowired
    private OrganizationService organizationService;

	@Autowired
	StaffService staffService;

	@Autowired
	IdentityDocDetailsService identityDocDetailsService;

	@Autowired
	StaffRepository staffRepository;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	SystemFileService systemFileService;
	
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

		String hasDebtor="true";
		try{
			Owner owner=this.ownerService.getByEntityId(organization.getId(),"ORGANIZATION");
			Debtor debtor=debtorService.getByOwnerId(owner.getId());
			model.addAttribute("debtorId",debtor.getId());
		}
		catch (Exception e){
			hasDebtor="false";
			model.addAttribute("debtorId",0);
		}
		model.addAttribute("hasDebtor", hasDebtor);
		List<Position> organizationPositionList = new ArrayList<Position>();

		for (Department department:organization.getDepartment())
		{
			organizationPositionList.addAll(this.positionService.findByDepartment(department));
		}

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String jsonSysFiles= gson.toJson(getSystemFilesByOrganizationId(id));
		model.addAttribute("files", jsonSysFiles);

		model.addAttribute("docs",getDocs(id));
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

		String person1Name=" ";
		String person1Surname=" ";
		String person1Lastname=" ";

		model.addAttribute("person1Surname",person1Surname);
		model.addAttribute("person1Name",person1Name);
		model.addAttribute("person1Lastname",person1Lastname);

		String person2Name=" ";
		String person2Surname=" ";
		String person2Lastname=" ";

		model.addAttribute("person2Surname",person2Surname);
		model.addAttribute("person2Name",person2Name);
		model.addAttribute("person2Lastname",person2Lastname);

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

		Person person1=null;
		Person person2=null;

		Position position1=new Position();
		Position position2=new Position();
		for(Department department1:organization.getDepartment()){
			Department department=departmentService.findById(department1.getId());
			if(department.getPosition().size()!=2){
				department.getPosition().clear();
				//		add Positions

				position1.setName("Руководитель");
				position1.setDepartment(department);
				positionService.create(position1);


				position2.setName("Главный бухгалтер");
				position2.setDepartment(department);
				positionService.create(position2);
				department.setOrganization(organization);
				departmentService.edit(department);
			}
			List<Staff> staffList=staffService.findAllByDepartment(department);
			Iterator iterator = department.getPosition().iterator();
			List<Position> positions=new ArrayList<>();
			while(iterator.hasNext()){
				positions.add((Position)iterator.next());
			}
			if(staffList.size()>=2){
				Staff staf1=staffList.get(0);
				Staff staff1=staffService.findById(staf1.getId());
				staff1.setPosition(positions.get(0));
				person1=staff1.getPerson();
				staffService.edit(staff1);

				Staff staf2=staffList.get(1);
				Staff staff2=staffService.findById(staf2.getId());
				staff2.setPosition(positions.get(1));
				person2=staff2.getPerson();
				staffService.edit(staff2);
			}
			else if(staffList.size()==1){
				Staff staf1=staffList.get(0);
				Staff staff1=staffService.findById(staf1.getId());
				person1=staff1.getPerson();

				IdentityDoc accountantIdentityDoc=new IdentityDoc();
				accountantIdentityDoc.setName(" ");
				accountantIdentityDoc.setNumber(" ");
				accountantIdentityDoc.setPin(" ");
				accountantIdentityDoc.setIdentityDocDetails(identityDocDetailsService.findById(1L));
				accountantIdentityDoc.setIdentityDocGivenBy(identityDocGivenByService.findById(1L));
				accountantIdentityDoc.setIdentityDocType(identityDocTypeService.findById(1L));

				person2=new Person();
				person2.setIdentityDoc(accountantIdentityDoc);
				person2.setContact(organization.getContact());
				person2.setAddress(organization.getAddress());
				person2.setName(" ");
				person2.setDescription(organization.getName());
				personService.create(person2);


//    	add staff
				Staff staff2=new Staff();
				staff2.setName(" ");
				staff2.setPerson(person2);
				staff2.setDepartment(department);
				staff2.setPosition(position2);
				staff2.setOrganization(organization);
				staffService.create(staff2);

			}
			break;
		}
		String[] personSplitted1=person1.getName().split("\\s+");
		String person1Name=" ";
		String person1Surname=" ";
		String person1Lastname=" ";
		if(personSplitted1.length>=3){
			person1Surname=personSplitted1[0];
			person1Name=personSplitted1[1];
			person1Lastname=personSplitted1[2];
		}
		else if(personSplitted1.length==2){
			person1Surname=personSplitted1[0];
			person1Name=personSplitted1[1];
		}
		else if(personSplitted1.length==1){
			person1Name=personSplitted1[0];
		}
		model.addAttribute("person1Surname",person1Surname);
		model.addAttribute("person1Name",person1Name);
		model.addAttribute("person1Lastname",person1Lastname);

		String[] personSplitted2=person2.getName().split("\\s+");
		String person2Name=" ";
		String person2Surname=" ";
		String person2Lastname=" ";
		if(personSplitted2.length>=3){
			person2Surname=personSplitted2[0];
			person2Name=personSplitted2[1];
			person2Lastname=personSplitted2[2];
		}
		else if(personSplitted2.length==2){
			person2Surname=personSplitted2[0];
			person2Name=personSplitted2[1];
		}
		else if(personSplitted2.length==1){
			person2Name=personSplitted2[0];
		}
		model.addAttribute("person2Surname",person2Surname);
		model.addAttribute("person2Name",person2Name);
		model.addAttribute("person2Lastname",person2Lastname);

		return "admin/org/organizationForm";

	}

	@RequestMapping(value = "/organization/save", method = RequestMethod.POST)
	public String saveOrganizationAndRedirectToOrganizationList(@Validated @ModelAttribute("organization") Organization organization,
																BindingResult result,String person1,String person2) {

		organization.setName(organization.getIdentityDoc().getIdentityDocDetails().getFullname());

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		}
		else if (organization.getId() == 0) {

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

			addNewPersonAndEtc(organization,person1,person2);

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

			Organization organization1=organizationService.findById(organization.getId());
			if(organization1.getDepartment().size()==0){
				addNewPersonAndEtc(organization1,person1,person2);
			}
			else{
				for(Department department1:organization1.getDepartment()){
					Department department=departmentService.findById(department1.getId());
					List<Staff> staffList=staffService.findAllByDepartment(department);
					if(staffList.size()==2){
						int counter=0;
						for(Staff staff1:staffList){
							counter++;
							Staff staff=staffService.findById(staff1.getId());
							Person person=staff.getPerson();
							if(counter==1){
								String sd =person1.replaceAll("\\s+","");
								person.setName(sd.replaceAll(","," "));
							}
							else{
								String sd= person2.replaceAll("\\s+","");
								person.setName(sd.replaceAll(","," "));
							}
							staff1.setName(person.getName());
							staffService.edit(staff1);
							personService.edit(person);
						}
						break;
					}

				}
			}

		}

		return "redirect:/organization/"+organization.getId()+"/details";

	}

	public void addNewPersonAndEtc(Organization organization,String person1,String person2){
		//    	add Department
		Department department=new Department();
		department.setName("Руководство");
		department.setOrganization(organization);
		Set<Department> departmentList=organization.getDepartment();
		departmentService.create(department);
		departmentList.add(department);
		organization.setDepartment(departmentList);
		organizationRepository.save(organization);

//		add Positions
		Position position1=new Position();
		position1.setName("Руководитель");
		position1.setDepartment(department);
		positionService.create(position1);

		Position position2=new Position();
		position2.setName("Главный бухгалтер");
		position2.setDepartment(department);
		positionService.create(position2);


//    	add director
		IdentityDoc directorIdentityDoc=new IdentityDoc();
		directorIdentityDoc.setName(" ");
		directorIdentityDoc.setNumber(" ");
		directorIdentityDoc.setPin(" ");
		directorIdentityDoc.setIdentityDocDetails(identityDocDetailsService.findById(1L));
		directorIdentityDoc.setIdentityDocGivenBy(identityDocGivenByService.findById(1L));
		directorIdentityDoc.setIdentityDocType(identityDocTypeService.findById(1L));

		Person director=new Person();
		director.setIdentityDoc(directorIdentityDoc);
		director.setAddress(organization.getAddress());
		director.setContact(organization.getContact());
		String directorName=person1.replaceAll(","," ");
		director.setName(directorName);
		director.setDescription(organization.getName()+"  Руководитель");
		personService.create(director);


//    	add accountant
		IdentityDoc accountantIdentityDoc=new IdentityDoc();
		accountantIdentityDoc.setName(" ");
		accountantIdentityDoc.setNumber(" ");
		accountantIdentityDoc.setPin(" ");
		accountantIdentityDoc.setIdentityDocDetails(identityDocDetailsService.findById(1L));
		accountantIdentityDoc.setIdentityDocGivenBy(identityDocGivenByService.findById(1L));
		accountantIdentityDoc.setIdentityDocType(identityDocTypeService.findById(1L));

		Person accountant=new Person();
		accountant.setIdentityDoc(accountantIdentityDoc);
		accountant.setContact(organization.getContact());
		accountant.setAddress(organization.getAddress());
		String accountantName=person2.replaceAll(","," ");
		accountant.setName(accountantName);
		accountant.setDescription(organization.getName()+"  Главный бухгалтер");
		personService.create(accountant);

//    	add director staff
		Staff staff1=new Staff();
		staff1.setName(directorName);
		staff1.setPerson(director);
		staff1.setDepartment(department);
		staff1.setPosition(position1);
		staff1.setOrganization(organization);
		staffService.create(staff1);

//    	add accountant staff
		Staff staff2=new Staff();
		staff2.setName(accountantName);
		staff2.setPerson(accountant);
		staff2.setDepartment(department);
		staff2.setPosition(position2);
		staff2.setOrganization(organization);
		staffService.create(staff2);
	}

	@RequestMapping("/organization/{id}/remove")
	public String removeOrganizationAndRedirectToOrganizationList(@PathVariable("id") long id) {

    	Organization organization = this.organizationService.findById(id);
		this.organizationService.delete(organization);

		return "redirect:/organization/list";
	}

//    add information form
	@RequestMapping("/organization/{id}/addInformation")
	public String getAddInformationForm(Model model, @PathVariable("id") Long id){


		Organization organization=organizationService.findById(id);
		model.addAttribute("object",organization);
		model.addAttribute("systemObjectTypeId",2);

		model.addAttribute("attachment",new Attachment());

		return "/manage/debtor/loan/payment/addInformationForm";

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
	public String  getDocs(Long id){

		String baseQuery="select d.title,d.id,dt.name as type\n" +
				"from df_document d,person p,cat_responsible_organization ro,cat_document_type dt\n" +
				"where (d.receiverResponsible=ro.Responsible_id or d.senderResponsible=ro.Responsible_id) " +
				"and dt.id=d.documentType and ro.organizations_id="+id+" group by d.id";
		Query query=entityManager.createNativeQuery(baseQuery, PersonOrganizationDocModel.class);

		List<PersonOrganizationDocModel> list=query.getResultList();

		Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
		String result = gson.toJson(list);

		return result;
	}

	private List<SystemFileModel> getSystemFilesByOrganizationId(Long organizationId){

		List<SystemFileModel> list=new ArrayList<>();
		List<Information> informations=informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2,organizationId);
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
}
