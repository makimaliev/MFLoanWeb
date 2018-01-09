package kg.gov.mf.loan.web.controller.admin.org;

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

import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;

@Controller
public class AokmotuController {
	
	@Autowired
    private AokmotuService aokmotuService;
	
	@Autowired
    private DistrictService districtService;	
     
    public void setAokmotuService(AokmotuService ds)
    {
        this.aokmotuService = ds;
    }
    
    public void setDistrictService(DistrictService rs)
    {
        this.districtService = rs;
    }    
     
	@RequestMapping(value = "/aokmotu/list", method = RequestMethod.GET)
	public String listAokmotus(Model model) {
		model.addAttribute("aokmotu", new Aokmotu());
		model.addAttribute("aokmotuList", this.aokmotuService.findAll());

		return "admin/org/aokmotuList";
	}
	
	
	@RequestMapping("/aokmotu/{id}/view")
	public String viewAokmotuById(@PathVariable("id") long id, Model model) {

		Aokmotu aokmotu = this.aokmotuService.findById(id);

		model.addAttribute("aokmotu", aokmotu);

		return "admin/org/aokmotuView";
	}
	
	@RequestMapping("/aokmotu/{id}/details")
	public String viewAokmotuDetailsById(@PathVariable("id") long id, Model model) {

		Aokmotu aokmotu = this.aokmotuService.findById(id);

		model.addAttribute("aokmotu", aokmotu);

		return "admin/org/aokmotuDetails";
	}	
    
	
	@RequestMapping(value = "/aokmotu/add", method = RequestMethod.GET)
	public String getAokmotuAddForm(Model model) {

		model.addAttribute("aokmotu", new Aokmotu());
		model.addAttribute("districtList", this.districtService.findAll());

		return "admin/org/aokmotuForm";
	}
	
	
	@RequestMapping(value = "/district/{districtId}/aokmotu/add", method = RequestMethod.GET)
	public String showAokmotuAddFormWithDistrict(@PathVariable("districtId") long districtId,Model model) {

		Aokmotu modelAokmotu = new Aokmotu();
		
		modelAokmotu.setDistrict(this.districtService.findById(districtId));
		model.addAttribute("districtList", this.districtService.findAll());		
		
		model.addAttribute("aokmotu",modelAokmotu);

		return "admin/org/aokmotuForm";
	}	
	
	
	
	

	@RequestMapping("/aokmotu/{id}/edit")
	public String editAokmotu(@PathVariable("id") long id, Model model) {
		model.addAttribute("aokmotu", this.aokmotuService.findById(id));
		model.addAttribute("districtList", this.districtService.findAll());
		
		return "admin/org/aokmotuForm";

	}

	@RequestMapping(value = "/aokmotu/save", method = RequestMethod.POST)
	public ModelAndView saveAokmotu(@Validated @ModelAttribute("aokmotu") Aokmotu aokmotu, BindingResult result,ModelMap model) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (aokmotu.getId() == 0) {
			aokmotu.setDistrict(this.districtService.findById(aokmotu.getDistrict().getId()));			
			this.aokmotuService.create(aokmotu);
		} else {
			aokmotu.setDistrict(this.districtService.findById(aokmotu.getDistrict().getId()));			
			this.aokmotuService.edit(aokmotu);
		}

		String url = "/district/"+aokmotu.getDistrict().getId()+"/details";

        return new ModelAndView("redirect:"+url, model);

	}

	@RequestMapping("/aokmotu/{id}/remove")
	public String removeAokmotu(@PathVariable("id") long id) {

		this.aokmotuService.deleteById(id);

		return "redirect:/district/list";
	}

     

     

}
