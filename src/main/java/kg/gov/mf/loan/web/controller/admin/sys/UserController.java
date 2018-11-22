package kg.gov.mf.loan.web.controller.admin.sys;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.StaffService;
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

import kg.gov.mf.loan.admin.org.model.District;
import kg.gov.mf.loan.admin.sys.model.*;
import kg.gov.mf.loan.admin.sys.service.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
	
	@Autowired
    private UserService userService;
	
    public void setUserService(UserService rs)
    {
        this.userService = rs;
    }


    @Autowired
	private OrganizationService organizationService;


    
	@Autowired
    private InformationService informationService;
	
    public void setInformationService(InformationService rs)
    {
        this.informationService = rs;
    }
    

	@Autowired
    private RoleService roleService;
	
    public void setRoleService(RoleService rs)
    {
        this.roleService = rs;
    }
    
    
    @Autowired
    private SupervisorTermService supervisorTermService;
	
    public void setSupervisorTermService(SupervisorTermService rs)
    {
        this.supervisorTermService = rs;
    }
    

    @Autowired
	private StaffService staffService;
    
	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String listUsers(Model model) {
		
		model.addAttribute("user", new User());
		model.addAttribute("userList", this.userService.findAll());
		//model.addAttribute("roleList", this.roleService.findAll());
		return "admin/sys/userList";
	}
	
	@RequestMapping("/user/{id}/view")
	public String viewUserById(@PathVariable("id") long id, Model model) {

		User user = this.userService.findById(id);
		
		model.addAttribute("roleList", this.roleService.findAll());		
		model.addAttribute("user", user);


		return "admin/sys/userView";
	}
	
	@RequestMapping("/user/{id}/details")
	public String viewUserDetailsById(@PathVariable("id") long id, Model model) {

		User user = this.userService.findById(id);

		model.addAttribute("user", user);
		model.addAttribute("roleList", this.roleService.findAll());

		return "admin/sys/userDetails";
	}	
    
	
	@RequestMapping(value = "/user/add", method = RequestMethod.GET)
	public String getUserAddForm(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("userList", this.userService.findAll());
		model.addAttribute("roleList", this.roleService.findAll());	
		model.addAttribute("supervisorTermList", this.supervisorTermService.findAll());

		List<Staff> staffList = new ArrayList<>();

		Organization gaubk = this.organizationService.findById((long)1);

		for (Department department: gaubk.getDepartment())
		{
			staffList.addAll(this.staffService.findAllByDepartment(department));
		}

		model.addAttribute("staffList", staffList);

		return "admin/sys/userForm";
	}
	

	@RequestMapping("/user/{id}/edit")
	public String getUserEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("user", this.userService.findById(id));
		model.addAttribute("userList", this.userService.findAll());
		model.addAttribute("roleList", this.roleService.findAll());		
		model.addAttribute("supervisorTermList", this.supervisorTermService.findAll());

		List<Staff> staffList = new ArrayList<>();

		Organization gaubk = this.organizationService.findById((long)1);

		for (Department department: gaubk.getDepartment())
		{
			staffList.addAll(this.staffService.findAllByDepartment(department));
		}

		model.addAttribute("staffList", staffList);

		return "admin/sys/userForm";

	}


	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	public ModelAndView saveUserAndRedirectToInformationViewPage(@Validated @ModelAttribute("user") User user, BindingResult result,ModelMap model) {

		
		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (user.getId() == 0) {
			this.userService.create(user);
		} else {
			this.userService.edit(user);
		}
		
		String url = "/user/list";

        return new ModelAndView("redirect:"+url, model);

	}	
	

	@RequestMapping("/user/{id}/remove")
	public String removeUserAndRedirectToUserList(@PathVariable("id") long id) {

		this.userService.deleteById(id);

		return "redirect:/user/list";
	}

     

     

}
