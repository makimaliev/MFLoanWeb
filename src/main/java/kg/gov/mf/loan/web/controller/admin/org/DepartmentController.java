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
public class DepartmentController {
	
	@Autowired
    private DepartmentService departmentService;
     
    public void setDepartmentService(DepartmentService rs)
    {
        this.departmentService = rs;
    }

	@Autowired
    private OrganizationService organizationService;
     
    public void setOrganizationService(OrganizationService rs)
    {
        this.organizationService = rs;
    }
    
    
	@RequestMapping(value = "/department/list", method = RequestMethod.GET)
	public String listDepartments(Model model) {
		model.addAttribute("department", new Department());
		model.addAttribute("departmentList", this.departmentService.findAll());

		return "admin/org/departmentList";
	}
	
	
	@RequestMapping(value = "/department/table", method = RequestMethod.GET)
	public String showDepartmentTable(Model model) {
		model.addAttribute("department", new Department());
		model.addAttribute("departmentList", this.departmentService.findAll());

		return "admin/org/departmentTable";
	}	
	
	@RequestMapping("/department/{id}/view")
	public String viewDepartmentById(@PathVariable("id") long id, Model model) {

		Department department = this.departmentService.findById(id);

		model.addAttribute("department", department);

		return "admin/org/departmentView";
	}
    
	
	@RequestMapping(value = "/department/add", method = RequestMethod.GET)
	public String getDepartmentAddForm(Model model) {

		model.addAttribute("department", new Department());

		return "admin/org/departmentForm";
	}
	
	
	@RequestMapping(value = "/organization/{organizationId}/department/add", method = RequestMethod.GET)
	public String getBankDataAddFormWithOrganization(@PathVariable("organizationId") long organizationId,Model model) {

		Department modelDepartment = new Department();
		
		modelDepartment.setOrganization(this.organizationService.findById(organizationId));
		model.addAttribute("department",modelDepartment);


		return "admin/org/departmentForm";
	}
	
	
	

	@RequestMapping("/department/{id}/edit")
	public String getDepartmentEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("department", this.departmentService.findById(id));
		return "admin/org/departmentForm";

	}

	@RequestMapping(value = "/department/save", method = RequestMethod.POST)
	public String saveDepartmentAndRedirectToDepartmentList(@Validated @ModelAttribute("department") Department department, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (department.getId() == 0) {
			this.departmentService.create(department);
		} else {
			this.departmentService.edit(department);
		}

//		return "redirect:/organization/list";

		return "redirect:/organization/"+department.getOrganization().getId()+"/details";

	}

	@RequestMapping("/department/{id}/remove")
	public String removeDepartmentAndRedirectToDepartmentList(@PathVariable("id") long id) {

    	Department department = this.departmentService.findById(id);
    	long organizationId = department.getOrganization().getId();

		this.departmentService.deleteById(id);

//		return "redirect:/organization/list";

		return "redirect:/organization/"+organizationId+"/details";
	}

     

     

}
