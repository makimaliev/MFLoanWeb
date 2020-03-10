package kg.gov.mf.loan.web.controller.admin.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kg.gov.mf.loan.admin.sys.model.Role;
import kg.gov.mf.loan.admin.sys.service.PermissionService;
import kg.gov.mf.loan.admin.sys.service.RoleService;

@Controller
public class RoleController 
{
	@Autowired
    private RoleService roleService;
     
    public void setRoleService(RoleService ps)
    {
        this.roleService = ps;
    }
    
	@Autowired
    private PermissionService permissionService;
     
    public void setPermissionService(PermissionService ps)
    {
        this.permissionService = ps;
    }    
     
	@RequestMapping(value = "/role/list", method = RequestMethod.GET)
	public String listRoles(Model model) {
		model.addAttribute("role", new Role());
		model.addAttribute("roleList", this.roleService.findAll());

		return "admin/sys/roleList";
	}
	
	@RequestMapping("/role/{id}/view")
	public String viewrole(@PathVariable("id") int id, Model model) {

		Role role = this.roleService.findById(id);
		

		model.addAttribute("role", role);
		model.addAttribute("roleList", this.roleService.findAll());

		return "admin/sys/roleView";
	}
	
	@RequestMapping("/role/{id}/details")
	public String viewRoleDetails(@PathVariable("id") int id, Model model) {

		Role role = this.roleService.findById(id);
		

		model.addAttribute("role", role);
		model.addAttribute("roleList", this.roleService.findAll());

		return "admin/sys/roleDetails";
	}	
    
	
	@RequestMapping(value = "/role/add", method = RequestMethod.GET)
	public String getRoleAddForm(Model model) {

		model.addAttribute("role", new Role());
		model.addAttribute("permissionList", this.permissionService.findAll());		

		return "admin/sys/roleForm";
	}

	@RequestMapping("/role/{id}/edit")
	public String editRole(@PathVariable("id") int id, Model model) {
		
		model.addAttribute("role", this.roleService.findById(id));
		model.addAttribute("permissionList", this.permissionService.findAll());	
		
		return "admin/sys/roleForm";

	}

	@RequestMapping(value = "/role/save", method = RequestMethod.POST)
	public String saveRole(@Validated @ModelAttribute("role") Role p, BindingResult result) {

		if (result.hasErrors()) {

		} else if (p.getId() == 0) {
			this.roleService.create(p);
		} else {
			this.roleService.edit(p);
		}

		return "redirect:/role/list";

	}

	@RequestMapping("/role/{id}/remove")
	public String removeRole(@PathVariable("id") int id) {

		this.roleService.deleteById(id);

		return "redirect:/role/list";
	}

     

     
}