package kg.gov.mf.loan.web.controller.admin.org;

import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.repository.org.StaffRepository;
import kg.gov.mf.loan.web.fetchModels.ContactListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StaffController {
	
	@Autowired
    private StaffService staffService;

	@Autowired
	StaffRepository staffRepository;

	@Autowired
	AddressService addressService;

	@Autowired
	EmploymentHistoryService employmentHistoryService;
	@Autowired
	EntityManager entityManager;
     
    public void setStaffService(StaffService rs)
    {
        this.staffService = rs;
    }

	@Autowired
	private UserService userService;

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
    

	
    public void setEmploymentHistoryService(EmploymentHistoryService ds)
    {
        this.employmentHistoryService = ds;
    }    
    
	@RequestMapping(value = "/staff/list", method = RequestMethod.GET)
	public String listStaffs(Model model) {
		model.addAttribute("staff", new Staff());

//		List<Staff> staffList = this.staffService.findAll();
//		for (Staff staffInList:staffList)
//		{
//			staffInList.setUser(userService.findByStaff(staffInList));
//		}


//		model.addAttribute("staffList", staffList);
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

		Person person = this.personService.findById(staff.getPerson().getId());

		Address address = this.addressService.findById(person.getAddress().getId());

		person.setAddress(address);

		staff.setPerson(person);

		if(staff.getEmploymentHistory()==null)
		{
			EmploymentHistory employmentHistory = new EmploymentHistory();
			employmentHistory.setStaff(staff);
			staff.setEmploymentHistory(employmentHistory);


			staffService.edit(staff);
			staff = this.staffService.findById(id);
		}
		else{
			staff.setEmploymentHistory(this.employmentHistoryService.findById(staff.getEmploymentHistory().getId()));
		}

		model.addAttribute("staff", staff);

		return "admin/org/staffDetails";
	}
	
//	@RequestMapping(value = "/staff/add", method = RequestMethod.GET)
//	public String getStaffAddForm(Model model) {
//
//		model.addAttribute("staff", new Staff());
//
//		model.addAttribute("departmentList", this.departmentService.findAll());
//		model.addAttribute("organizationList", this.organizationService.findAll());
//		model.addAttribute("positionList", this.positionService.findAll());
//		model.addAttribute("personList", this.personService.findAll());
//
//
//		return "admin/org/staffForm";
//	}
	
	@RequestMapping(value = "/organization/{organizationId}/department/{departmentId}/staff/add", method = RequestMethod.GET)
	public String getStaffAddFormOrganization(Model model,@PathVariable("organizationId") long organization_id, @PathVariable("departmentId") long department_id) {

		Staff modelStaff = new Staff();

		modelStaff.setOrganization(this.organizationService.findById(organization_id));
		modelStaff.setDepartment(this.departmentService.findById(department_id));

		model.addAttribute("staff", modelStaff);

		model.addAttribute("organization", this.organizationService.findById(organization_id));
		model.addAttribute("department", this.departmentService.findById(department_id ));


//		model.addAttribute("departmentList", this.departmentService.findAll());
//		model.addAttribute("organizationList", this.organizationService.findAll());
//		model.addAttribute("organizationList", this.positionService.findAll());
		model.addAttribute("positionList", this.positionService.findByDepartment(this.departmentService.findById(department_id)));

//		model.addAttribute("personList", this.personService.findLast100());


		return "admin/org/staffForm";
	}


	@RequestMapping(value = "/department/{departmentId}/staff/list", method = RequestMethod.GET)
	public String getStaffListByDepartment(Model model,@PathVariable("departmentId") long department_id) {

		model.addAttribute("staffList", this.staffService.findAllByDepartment(departmentService.findById(department_id)));
//		model.addAttribute("organizationList", this.organizationService.findAll());

		return "admin/org/staffView";
	}




	@RequestMapping("/staff/{id}/edit")
	public String getStaffEditForm(@PathVariable("id") long id, Model model) {

    	Staff staff = this.staffService.findById(id);
		model.addAttribute("staff", staff);

		model.addAttribute("organization", staff.getOrganization());
		model.addAttribute("department", staff.getDepartment());

		model.addAttribute("positionList", this.positionService.findByDepartment(staff.getDepartment()));

		List<Person> personList = new ArrayList<Person>();

		personList.add(staff.getPerson());

		model.addAttribute("personList", personList);

		return "admin/org/staffForm";

	}

	@RequestMapping(value = "/staff/save", method = RequestMethod.POST)
	public String saveStaffAndRedirectToStaffList(@Validated @ModelAttribute("staff") Staff staff, BindingResult result) {


		EmploymentHistory employmentHistory = staff.getEmploymentHistory();
		employmentHistory.setStaff(staff);

		staff.setName(staff.getPerson().getIdentityDoc().getIdentityDocDetails().getFullname());
		staff.setEnabled(true);

		if (result.hasErrors()) {

		} else if (staff.getId() == 0) {
			


			this.staffService.create(staff);
		} else {
			
			this.staffService.edit(staff);
		}

//		return "redirect:/staff/list";

		return "redirect:/organization/"+staff.getOrganization().getId()+"/details";

	}

	@RequestMapping("/staff/{id}/remove")
	public String removeStaffAndRedirectToStaffList(@PathVariable("id") long id) {

		this.staffService.deleteById(id);

		return "redirect:/staff/list";
	}

	class Result {

		public Long id;
		public String text;

		public Result(Long id, String text) {
			this.id = id;
			this.text = text;
		}
	}


	@GetMapping("/staffByName/list")
	@ResponseBody
	public List<Result> findByStaffName(@RequestParam(value = "name",required = true) String name){

		List<Result> data = new ArrayList<>();
		String baseQuery="select s.id,s.name as smallData from staff s where s.id not in (select u.staff_id from users u where u.staff_id is not null) \n" +
				" and s.organization_id=1 and s.name like \"%"+name+"%\"";
		Query query=entityManager.createNativeQuery(baseQuery, ContactListModel.class);
		int counter=0;
		List<ContactListModel> results=query.getResultList();
		for(ContactListModel staff : results)
		{
			counter++;
			data.add(new Result(staff.getId(),staff.getSmallData()));
			if(counter>10) break;
		}

		return data;

	}

	@GetMapping("/roleByName/list")
	@ResponseBody
	public List<Result> findByRoleName(@RequestParam(value = "name",required = true) String name){

		List<Result> data = new ArrayList<>();
		String baseQuery="select id, name as smallData from role where name like \"%"+name+"%\"";
		Query query=entityManager.createNativeQuery(baseQuery, ContactListModel.class);
		int counter=0;
		List<ContactListModel> results=query.getResultList();
		for(ContactListModel role : results)
		{
			counter++;
			data.add(new Result(role.getId(),role.getSmallData()));
			if(counter>10) break;
		}

		return data;

	}


     

     

}
