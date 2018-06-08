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
public class PositionController {
	
	@Autowired
    private PositionService positionService;
     
    public void setPositionService(PositionService rs)
    {
        this.positionService = rs;
    }


	@Autowired
    private DepartmentService departmentService;
     
    public void setDepartmentService(DepartmentService rs)
    {
        this.departmentService = rs;
    }    
    
    
	@RequestMapping(value = "/position/list", method = RequestMethod.GET)
	public String listPositions(Model model) {
		model.addAttribute("position", new Position());
		model.addAttribute("positionList", this.positionService.findAll());

		return "admin/org/positionList";
	}
	
	
	@RequestMapping(value = "/position/table", method = RequestMethod.GET)
	public String showPositionTable(Model model) {
		model.addAttribute("position", new Position());
		model.addAttribute("positionList", this.positionService.findAll());

		return "admin/org/positionTable";
	}	
	
	@RequestMapping("/position/{id}/view")
	public String viewPositionById(@PathVariable("id") long id, Model model) {

		Position position = this.positionService.findById(id);

		model.addAttribute("position", position);

		return "admin/org/positionView";
	}
    
	
	@RequestMapping(value = "/position/add", method = RequestMethod.GET)
	public String getPositionAddForm(Model model) {

		model.addAttribute("position", new Position());

		return "admin/org/positionForm";
	}
	
	
	@RequestMapping(value = "/department/{departmentId}/position/add", method = RequestMethod.GET)
	public String getBankDataAddFormWithOrganization(@PathVariable("departmentId") long departmentId,Model model) {

		Position modelPosition = new Position();
		
		modelPosition.setDepartment(this.departmentService.findById(departmentId));
		model.addAttribute("position",modelPosition);


		return "admin/org/positionForm";
	}
	
	

	@RequestMapping("/position/{id}/edit")
	public String getPositionEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("position", this.positionService.findById(id));
		return "admin/org/positionForm";

	}

	@RequestMapping(value = "/position/save", method = RequestMethod.POST)
	public String savePositionAndRedirectToPositionList(@Validated @ModelAttribute("position") Position position, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (position.getId() == 0) {
			this.positionService.create(position);
		} else {
			this.positionService.edit(position);
		}

		Department department = this.departmentService.findById(position.getDepartment().getId());



		return "redirect:/organization/"+department.getOrganization().getId()+"/details";

	}

	@RequestMapping("/position/{id}/remove")
	public String removePositionAndRedirectToPositionList(@PathVariable("id") long id) {

		this.positionService.deleteById(id);

		return "redirect:/organization/list";
	}

     

     

}
