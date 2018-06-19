package kg.gov.mf.loan.web.controller.admin.sys;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import kg.gov.mf.loan.admin.org.model.Region;
import kg.gov.mf.loan.manage.service.debtor.DebtorTypeService;
import kg.gov.mf.loan.manage.service.debtor.OrganizationFormService;
import kg.gov.mf.loan.manage.service.debtor.WorkSectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import antlr.collections.List;
import kg.gov.mf.loan.admin.org.model.District;
import kg.gov.mf.loan.admin.org.service.DepartmentService;
import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.admin.sys.model.*;
import kg.gov.mf.loan.admin.sys.service.*;

@Controller
public class SupervisorTermController {
	
	@Autowired
    private SupervisorTermService supervisorTermService;
	
    public void setSupervisorTermService(SupervisorTermService rs)
    {
        this.supervisorTermService = rs;
    }

    
	@Autowired
    private InformationService informationService;
	
    public void setInformationService(InformationService rs)
    {
        this.informationService = rs;
    }
    
	@Autowired
    private UserService userService;
	
    public void setUserService(UserService rs)
    {
        this.userService = rs;
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
    private DepartmentService departmentService;
     
    public void setDepartmentService(DepartmentService rs)
    {
        this.departmentService = rs;
    }

	@Autowired
	DebtorTypeService debtorTypeService;

	@Autowired
	WorkSectorService workSectorService;

	@Autowired
	OrganizationFormService organizationFormService;

	@RequestMapping(value = "/supervisorTerm/list", method = RequestMethod.GET)
	public String listSupervisorTerms(Model model) {
		
		model.addAttribute("supervisorTerm", new SupervisorTerm());
		model.addAttribute("supervisorTermList", this.supervisorTermService.findAll());
		return "admin/sys/supervisorTermList";
	}
	
	@RequestMapping("/supervisorTerm/{id}/view")
	public String viewSupervisorTermById(@PathVariable("id") long id, Model model) {

		SupervisorTerm supervisorTerm = this.supervisorTermService.findById(id);

		model.addAttribute("supervisorTerm", supervisorTerm);


		return "admin/sys/supervisorTermView";
	}
	
	@RequestMapping("/supervisorTerm/{id}/details")
	public String viewSupervisorTermDetailsById(@PathVariable("id") long id, Model model) {

		SupervisorTerm supervisorTerm = this.supervisorTermService.findById(id);

		model.addAttribute("supervisorTerm", supervisorTerm);


		return "admin/sys/supervisorTermDetails";
	}	
    
	
	@RequestMapping(value = "/supervisorTerm/add", method = RequestMethod.GET)
	public String getSupervisorTermAddForm(Model model) {

		model.addAttribute("supervisorTerm", new SupervisorTerm());

//		model.addAttribute("supervisorTermList", this.supervisorTermService.findAll());


		Region modelRegion = (Region) this.regionService.findById(1);

		District modelDistrict = (District) this.districtService.findByRegion(modelRegion).get(0);


		model.addAttribute("regionList", this.regionService.findAll());
		model.addAttribute("districtList", this.districtService.findByRegion(modelRegion));

		model.addAttribute("departmentList", this.departmentService.findAll());
		model.addAttribute("organizationFormList", this.organizationFormService.list());
		model.addAttribute("workSectorList", this.workSectorService.list());

		

		return "admin/sys/supervisorTermForm";
	}
	

	@RequestMapping(value = "/user/{userId}/supervisorTerm/add", method = RequestMethod.GET)
	public String getSupervisorTermAddByUserIdForm(@PathVariable("userId") long userId,Model model) {

		SupervisorTerm modelSupervisorTerm = new SupervisorTerm();
		
		Set<User> modelList = modelSupervisorTerm.getUsers();
		modelList.add(this.userService.findById(userId));
		
		
		
		
		
		modelSupervisorTerm.setUsers(modelList);
		model.addAttribute("supervisorTerm",modelSupervisorTerm);

		Region modelRegion = (Region) this.regionService.findById(1);

		District modelDistrict = (District) this.districtService.findByRegion(modelRegion).get(0);


		model.addAttribute("regionList", this.regionService.findAll());
		model.addAttribute("districtList", this.districtService.findByRegion(modelRegion));

		model.addAttribute("departmentList", this.departmentService.findAll());
		model.addAttribute("organizationFormList", this.organizationFormService.list());
		model.addAttribute("workSectorList", this.workSectorService.list());


		return "admin/sys/supervisorTermForm";
	}
	
	
	
	@RequestMapping("/supervisorTerm/{id}/edit")
	public String getSupervisorTermEditForm(@PathVariable("id") long id, Model model) {


    	SupervisorTerm supervisorTerm = this.supervisorTermService.findById(id);
		model.addAttribute("supervisorTerm", supervisorTerm);

//		model.addAttribute("supervisorTermList", this.supervisorTermService.findAll());



		model.addAttribute("regionList", this.regionService.findAll());


		model.addAttribute("districtList", this.districtService.findByRegion(supervisorTerm.getRegion()));
		model.addAttribute("departmentList", this.departmentService.findAll());
		model.addAttribute("organizationFormList", this.organizationFormService.list());
		model.addAttribute("workSectorList", this.workSectorService.list());
				
	
		
		return "admin/sys/supervisorTermForm";

	}


	
	
	@RequestMapping(value = "/supervisorTerm/save", method = RequestMethod.POST)
	public ModelAndView saveSupervisorTermAndRedirectToInformationViewPage(@Validated @ModelAttribute("supervisorTerm") SupervisorTerm supervisorTerm, BindingResult result,ModelMap model) {

		
		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (supervisorTerm.getId() == 0) {
			this.supervisorTermService.create(supervisorTerm);
		} else {
			this.supervisorTermService.edit(supervisorTerm);
		}
		
		//String url = "/information/"+supervisorTerm.getInformation().getId()+"/details";

        return new ModelAndView("redirect:/supervisorTerm/list", model);

	}	
	

	@RequestMapping("/supervisorTerm/{id}/remove")
	public String removeSupervisorTermAndRedirectToSupervisorTermList(@PathVariable("id") long id) {

		this.supervisorTermService.deleteById(id);

		return "redirect:/supervisorTerm/list";
	}

     

     

}
