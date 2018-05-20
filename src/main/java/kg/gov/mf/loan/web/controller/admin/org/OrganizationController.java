package kg.gov.mf.loan.web.controller.admin.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;








import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;

import kg.gov.mf.loan.admin.sys.model.*;
import kg.gov.mf.loan.admin.sys.service.*;

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
    private InformationService informationService;
	
    public void setInformationService(InformationService rs)
    {
        this.informationService = rs;
    } 
    
    
	@RequestMapping(value = "/organization/list", method = RequestMethod.GET)
	public String listOrganizations(Model model) {
		
		model.addAttribute("organization", new Organization());
		model.addAttribute("organizationList", this.organizationService.findLast100());
		return "admin/org/organizationList";
	}
	
	
	@RequestMapping(value = "/organization/table", method = RequestMethod.GET)
	public String showOrganizationTable(Model model) {
		model.addAttribute("organization", new Organization());
		model.addAttribute("organizationList", this.organizationService.findLast100());

		return "admin/org/organizationTable";
	}	
	
	@RequestMapping("/organization/{id}/view")
	public String viewOrganizationById(@PathVariable("id") long id, Model model) {

		Organization organization = this.organizationService.findById(id);

		model.addAttribute("organization", organization);
		model.addAttribute("positionList", this.positionService.findAll());
		model.addAttribute("informationList", this.informationService.findInformationBySystemObjectTypeIdAndSystemObjectId(2, organization.getId()));		
		

		return "admin/org/organizationView";
	}
	
	
	@RequestMapping("/organization/{id}/details")
	public String viewOrganizationDetailsById(@PathVariable("id") long id, Model model) {

		Organization organization = this.organizationService.findById(id);

		model.addAttribute("organization", organization);
		model.addAttribute("positionList", this.positionService.findAll());
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
		
		Region modelRegion = new Region();
		modelRegion.setId(1);

		District modelDistrict = new District();
		modelDistrict.setId(1);

		Aokmotu modelAokmotu = new Aokmotu();
		modelAokmotu.setId(1);
		
		Village modelVillage = new Village();
		modelVillage.setId(1);
		
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
		model.addAttribute("districtList", this.districtService.findAll());			
		model.addAttribute("aokmotuList", this.aokmotuService.findAll());			
		model.addAttribute("villageList", this.villageService.findAll());	
		model.addAttribute("identityDocTypeList", this.identityDocTypeService.findAll());
		model.addAttribute("identityDocGivenByList", this.identityDocGivenByService.findAll());		
		model.addAttribute("orgFormList", this.orgFormService.findAll());
		
		return "admin/org/organizationForm";
	}
	
	

	@RequestMapping("/organization/{id}/edit")
	public String getOrganizationEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("organization", this.organizationService.findById(id));
		model.addAttribute("regionList", this.regionService.findAll());
		model.addAttribute("districtList", this.districtService.findAll());			
		model.addAttribute("aokmotuList", this.aokmotuService.findAll());			
		model.addAttribute("villageList", this.villageService.findAll());
		model.addAttribute("identityDocTypeList", this.identityDocTypeService.findAll());
		model.addAttribute("identityDocGivenByList", this.identityDocGivenByService.findAll());		
		model.addAttribute("orgFormList", this.orgFormService.findAll());		
		
		return "admin/org/organizationForm";

	}

	@RequestMapping(value = "/organization/save", method = RequestMethod.POST)
	public String saveOrganizationAndRedirectToOrganizationList(@Validated @ModelAttribute("organization") Organization organization, BindingResult result) {

		
		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (organization.getId() == 0) {
			
//			organization.setAddress(this.addressService.findById(organization.getAddress().getId()));
//			organization.setIdentityDoc(this.identityDocService.findById(organization.getIdentityDoc().getId()));
			
			//organization.setOrgForm(this.orgFormService.findById(organization.getOrgForm().getId()));
			
			this.organizationService.create(organization);
		} else {
			this.organizationService.edit(organization);
		}

		return "redirect:/organization/list";

	}

	@RequestMapping("/organization/{id}/remove")
	public String removeOrganizationAndRedirectToOrganizationList(@PathVariable("id") long id) {

		this.organizationService.deleteById(id);

		return "redirect:/organization/list";
	}

     

     

}
