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

import kg.gov.mf.loan.admin.sys.model.Permission;
import kg.gov.mf.loan.admin.sys.service.PermissionService;

@Controller
public class PermissionController 
{
	@Autowired
    private PermissionService permissionService;
     
    public void setPermissionService(PermissionService ps)
    {
        this.permissionService = ps;
    }
     
	@RequestMapping(value = "/permission/list", method = RequestMethod.GET)
	public String listPermissions(Model model) {
		model.addAttribute("permission", new Permission());
		model.addAttribute("permissionList", this.permissionService.findAll());

		return "admin/sys/permissionList";
	}
	
	@RequestMapping("/permission/{id}/view")
	public String viewpermission(@PathVariable("id") int id, Model model) {

		Permission permission = this.permissionService.findById(id);
		

		model.addAttribute("permission", permission);
		model.addAttribute("permissionList", this.permissionService.findAll());

		return "admin/sys/permissionView";
	}
	
	
	@RequestMapping("/permission/{id}/details")
	public String viewPermissionDetails(@PathVariable("id") int id, Model model) {

		Permission permission = this.permissionService.findById(id);
		

		model.addAttribute("permission", permission);
		model.addAttribute("permissionList", this.permissionService.findAll());

		return "admin/sys/permissionDetails";
	}	
    
	
	@RequestMapping(value = "/permission/add", method = RequestMethod.GET)
	public String getPermissionAddForm(Model model) {

		model.addAttribute("permission", new Permission());

		return "admin/sys/permissionForm";
	}

	@RequestMapping("/permission/{id}/edit")
	public String editPermission(@PathVariable("id") int id, Model model) {
		model.addAttribute("permission", this.permissionService.findById(id));
		return "admin/sys/permissionForm";

	}

	@RequestMapping(value = "/permission/save", method = RequestMethod.POST)
	public String savePermission(@Validated @ModelAttribute("permission") Permission p, BindingResult result) {

		if (result.hasErrors()) {

		} else if (p.getId() == 0) {
			this.permissionService.create(p);
		} else {
			this.permissionService.edit(p);
		}

		return "redirect:/permission/list";

	}

	@RequestMapping("/permission/{id}/remove")
	public String removePermission(@PathVariable("id") int id) {

		this.permissionService.deleteById(id);

		return "redirect:/permission/list";
	}

     

     
}