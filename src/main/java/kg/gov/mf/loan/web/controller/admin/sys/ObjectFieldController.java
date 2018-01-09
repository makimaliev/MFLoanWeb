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



import kg.gov.mf.loan.admin.sys.model.*;
import kg.gov.mf.loan.admin.sys.service.*;

@Controller
public class ObjectFieldController {
	
	@Autowired
    private ObjectFieldService objectFieldService;
     
    public void setObjectFieldService(ObjectFieldService rs)
    {
        this.objectFieldService = rs;
    }

	@Autowired
    private ObjectTypeService objectTypeService;
     
    public void setObjectTypeService(ObjectTypeService rs)
    {
        this.objectTypeService = rs;
    }
    
    
	@RequestMapping(value = "/objectField/list", method = RequestMethod.GET)
	public String listObjectFields(Model model) {
		model.addAttribute("objectField", new ObjectField());
		model.addAttribute("objectFieldList", this.objectFieldService.findAll());

		return "admin/sys/objectFieldList";
	}
	
	
	@RequestMapping(value = "/objectField/table", method = RequestMethod.GET)
	public String showObjectFieldTable(Model model) {
		model.addAttribute("objectField", new ObjectField());
		model.addAttribute("objectFieldList", this.objectFieldService.findAll());

		return "admin/sys/objectFieldTable";
	}	
	
	@RequestMapping("/objectField/{id}/view")
	public String viewObjectFieldById(@PathVariable("id") long id, Model model) {

		ObjectField objectField = this.objectFieldService.findById(id);

		model.addAttribute("objectField", objectField);

		return "admin/sys/objectFieldView";
	}
    
	
	@RequestMapping("/objectField/{id}/details")
	public String viewObjectFieldDetailsById(@PathVariable("id") long id, Model model) {

		ObjectField objectField = this.objectFieldService.findById(id);

		model.addAttribute("objectField", objectField);

		return "admin/sys/objectFieldDetails";
	}	
	
	@RequestMapping(value = "/objectField/add", method = RequestMethod.GET)
	public String getObjectFieldAddForm(Model model) {

		model.addAttribute("objectField", new ObjectField());

		return "admin/sys/objectFieldForm";
	}
	
	
	
	@RequestMapping(value = "/objectType/{objectTypeId}/objectField/add", method = RequestMethod.GET)
	public String getObjectFieldAddByObjectTypeIdForm(@PathVariable("objectTypeId") long objectTypeId,Model model) {

		ObjectField modelObjectField = new ObjectField();
		modelObjectField.setObjectType(this.objectTypeService.findById(objectTypeId));
		model.addAttribute("objectField",modelObjectField);

		return "admin/sys/objectFieldForm";
	}
	
	
	@RequestMapping("/objectField/{id}/edit")
	public String getObjectFieldEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("objectField", this.objectFieldService.findById(id));
		return "admin/sys/objectFieldForm";

	}

	@RequestMapping(value = "/objectField/save", method = RequestMethod.POST)
	public String saveObjectFieldAndRedirectToObjectFieldList(@Validated @ModelAttribute("objectField") ObjectField objectField, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (objectField.getId() == 0) {
			this.objectFieldService.create(objectField);
		} else {
			this.objectFieldService.edit(objectField);
		}

		return "redirect:/objectType/list";

	}

	@RequestMapping("/objectField/{id}/remove")
	public String removeObjectFieldAndRedirectToObjectFieldList(@PathVariable("id") long id) {

		this.objectFieldService.deleteById(id);

		return "redirect:/objectType/list";
	}

     

     

}
