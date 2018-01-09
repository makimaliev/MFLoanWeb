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

@Controller
public class StaffController {
	
	@Autowired
    private StaffService staffService;
     
    public void setStaffService(StaffService rs)
    {
        this.staffService = rs;
    }

	@Autowired
    private OrganizationService organizationService;
     
    public void setOrganizationService(OrganizationService rs)
    {
        this.organizationService = rs;
    }
    
	@Autowired
    private DepartmentService departmentService;
     
    public void setDepartmentService(DepartmentService rs)
    {
        this.departmentService = rs;
    }


	@Autowired
    private PositionService positionService;
     
    public void setPositionService(PositionService rs)
    {
        this.positionService = rs;
    }
    

	@Autowired
    private PersonService personService;
	
    public void setPersonService(PersonService rs)
    {
        this.personService = rs;
    }
    
	@Autowired
    private EmploymentHistoryService employmentHistoryService;
	
    public void setEmploymentHistoryService(EmploymentHistoryService ds)
    {
        this.employmentHistoryService = ds;
    }    
    
	@RequestMapping(value = "/staff/list", method = RequestMethod.GET)
	public String listStaffs(Model model) {
		model.addAttribute("staff", new Staff());
		model.addAttribute("staffList", this.staffService.findAll());
		model.addAttribute("organizationList", this.organizationService.findAll());		

		return "admin/org/staffList";
	}
	
	
	@RequestMapping(value = "/staff/table", method = RequestMethod.GET)
	public String showStaffTable(Model model) {
		model.addAttribute("staff", new Staff());
		model.addAttribute("staffList", this.staffService.findAll());
		model.addAttribute("organizationList", this.organizationService.findAll());		

		return "admin/org/staffTable";
	}	
	
	@RequestMapping("/staff/{id}/view")
	public String viewStaffById(@PathVariable("id") long id, Model model) {

		Staff staff = this.staffService.findById(id);

		model.addAttribute("staff", staff);

		return "admin/org/staffView";
	}
    
	@RequestMapping("/staff/{id}/details")
	public String viewStaffDetailsById(@PathVariable("id") long id, Model model) {

		Staff staff = this.staffService.findById(id);

		model.addAttribute("staff", staff);

		return "admin/org/staffDetails";
	}
	
	@RequestMapping(value = "/staff/add", method = RequestMethod.GET)
	public String getStaffAddForm(Model model) {

		model.addAttribute("staff", new Staff());

		model.addAttribute("departmentList", this.departmentService.findAll());	
		model.addAttribute("organizationList", this.organizationService.findAll());	
		model.addAttribute("positionList", this.positionService.findAll());	
		model.addAttribute("personList", this.personService.findAll());		
		

		return "admin/org/staffForm";
	}
	
	@RequestMapping(value = "/organization/{organizationId}/staff/add", method = RequestMethod.GET)
	public String getStaffAddFormOrganization(Model model,@PathVariable("organizationId") long organization_id) {

		Staff modelStaff = new Staff();
		
		modelStaff.setOrganization(this.organizationService.findById(organization_id));
		
		model.addAttribute("staff", modelStaff);

		model.addAttribute("departmentList", this.departmentService.findAll());	
		model.addAttribute("organizationList", this.organizationService.findAll());	
		model.addAttribute("positionList", this.positionService.findAll());	
		model.addAttribute("personList", this.personService.findAll());		
		

		return "admin/org/staffForm";
	}
	
	
	
	@RequestMapping("/staff/{id}/edit")
	public String getStaffEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("staff", this.staffService.findById(id));
		
		model.addAttribute("departmentList", this.departmentService.findAll());
		model.addAttribute("organizationList", this.organizationService.findAll());	
		model.addAttribute("positionList", this.positionService.findAll());
		model.addAttribute("personList", this.personService.findAll());		
		
		return "admin/org/staffForm";

	}

	@RequestMapping(value = "/staff/save", method = RequestMethod.POST)
	public String saveStaffAndRedirectToStaffList(@Validated @ModelAttribute("staff") Staff staff, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (staff.getId() == 0) {
			
			EmploymentHistory employmentHistory = staff.getEmploymentHistory();
			employmentHistory.setStaff(staff);
			
			
			this.staffService.create(staff);
		} else {
			
			EmploymentHistory employmentHistory = staff.getEmploymentHistory();
			employmentHistory.setStaff(staff);
			
			this.staffService.edit(staff);
		}

		return "redirect:/staff/list";

	}

	@RequestMapping("/staff/{id}/remove")
	public String removeStaffAndRedirectToStaffList(@PathVariable("id") long id) {

		this.staffService.deleteById(id);

		return "redirect:/staff/list";
	}

     

     

}
