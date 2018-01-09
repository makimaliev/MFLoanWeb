package kg.gov.mf.loan.web.controller.admin.sys;

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

@Controller
public class ObjectTypeController {
	
	@Autowired
    private ObjectTypeService objectTypeService;
	
    public void setObjectTypeService(ObjectTypeService rs)
    {
        this.objectTypeService = rs;
    }

    
	@Autowired
    private InformationService informationService;
	
    public void setInformationService(InformationService rs)
    {
        this.informationService = rs;
    }
    
    
	@RequestMapping(value = "/objectType/list", method = RequestMethod.GET)
	public String listObjectTypes(Model model) {
		
		model.addAttribute("objectType", new ObjectType());
		model.addAttribute("objectTypeList", this.objectTypeService.findAll());
		return "admin/sys/objectTypeList";
	}
	
	@RequestMapping("/objectType/{id}/view")
	public String viewObjectTypeById(@PathVariable("id") long id, Model model) {

		ObjectType objectType = this.objectTypeService.findById(id);

		model.addAttribute("objectType", objectType);


		return "admin/sys/objectTypeView";
	}
	
	@RequestMapping("/objectType/{id}/details")
	public String viewObjectTypeDetailsById(@PathVariable("id") long id, Model model) {

		ObjectType objectType = this.objectTypeService.findById(id);

		model.addAttribute("objectType", objectType);


		return "admin/sys/objectTypeDetails";
	}	
    
	
	@RequestMapping(value = "/objectType/add", method = RequestMethod.GET)
	public String getObjectTypeAddForm(Model model) {

		model.addAttribute("objectType", new ObjectType());
		model.addAttribute("objectTypeList", this.objectTypeService.findAll());		

		return "admin/sys/objectTypeForm";
	}
	

	@RequestMapping("/objectType/{id}/edit")
	public String getObjectTypeEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("objectType", this.objectTypeService.findById(id));
		model.addAttribute("objectTypeList", this.objectTypeService.findAll());		
	
		
		return "admin/sys/objectTypeForm";

	}


	
	
	@RequestMapping(value = "/objectType/save", method = RequestMethod.POST)
	public ModelAndView saveObjectTypeAndRedirectToInformationViewPage(@Validated @ModelAttribute("objectType") ObjectType objectType, BindingResult result,ModelMap model) {

		
		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (objectType.getId() == 0) {
			this.objectTypeService.create(objectType);
		} else {
			this.objectTypeService.edit(objectType);
		}
		
		//String url = "/information/"+objectType.getInformation().getId()+"/details";

        return new ModelAndView("redirect:/objectType/list", model);

	}	
	

	@RequestMapping("/objectType/{id}/remove")
	public String removeObjectTypeAndRedirectToObjectTypeList(@PathVariable("id") long id) {

		this.objectTypeService.deleteById(id);

		return "redirect:/objectType/list";
	}

     

     

}
