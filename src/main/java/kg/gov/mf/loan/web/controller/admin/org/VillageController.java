package kg.gov.mf.loan.web.controller.admin.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;

import java.util.List;

@Controller
public class VillageController {
	
	@Autowired
    private VillageService villageService;
	
	@Autowired
    private AokmotuService aokmotuService;	
     
    public void setVillageService(VillageService ds)
    {
        this.villageService = ds;
    }
    
    public void setAokmotuService(AokmotuService rs)
    {
        this.aokmotuService = rs;
    }    
     
	@RequestMapping(value = "/village/list", method = RequestMethod.GET)
	public String listVillages(Model model) {
		model.addAttribute("village", new Village());
		model.addAttribute("villageList", this.villageService.findAll());

		return "admin/org/villageList";
	}

	@RequestMapping(value = "/villageByAokmotuId/list", method = RequestMethod.GET)
	public @ResponseBody
	List<Village> findVillageByAokmotuId(@RequestParam(value = "aokmotuId", required = true) Long aokmotuId) {

		Aokmotu aokmotuById = this.aokmotuService.findById(aokmotuId);

		return villageService.findByAokmotu(aokmotuById);
	}
	
	
	@RequestMapping("/village/{id}/view")
	public String viewVillageById(@PathVariable("id") long id, Model model) {

		Village village = this.villageService.findById(id);

		model.addAttribute("village", village);

		return "admin/org/villageView";
	}
	
	@RequestMapping("/village/{id}/details")
	public String viewVillageDetailsById(@PathVariable("id") long id, Model model) {

		Village village = this.villageService.findById(id);

		model.addAttribute("village", village);

		return "admin/org/villageDetails";
	}	
    
	
	@RequestMapping(value = "/village/add", method = RequestMethod.GET)
	public String getVillageAddForm(Model model) {

		model.addAttribute("village", new Village());
		model.addAttribute("aokmotuList", this.aokmotuService.findAll());

		return "admin/org/villageForm";
	}
	
	
	@RequestMapping(value = "/aokmotu/{aokmotuId}/village/add", method = RequestMethod.GET)
	public String showVillageAddFormWithDistrict(@PathVariable("aokmotuId") long aokmotuId,Model model) {

		Village modelVillage = new Village();
		
		modelVillage.setAokmotu(this.aokmotuService.findById(aokmotuId));
		model.addAttribute("aokmotuList", this.aokmotuService.findAll());		
		
		model.addAttribute("village",modelVillage);

		return "admin/org/villageForm";
	}	
	
	
	
	

	@RequestMapping("/village/{id}/edit")
	public String editVillage(@PathVariable("id") long id, Model model) {
		model.addAttribute("village", this.villageService.findById(id));
		model.addAttribute("aokmotuList", this.aokmotuService.findAll());
		
		return "admin/org/villageForm";

	}

	@RequestMapping(value = "/village/save", method = RequestMethod.POST)
	public ModelAndView saveVillage(@Validated @ModelAttribute("village") Village village, BindingResult result,ModelMap model) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (village.getId() == 0) {
			village.setAokmotu(this.aokmotuService.findById(village.getAokmotu().getId()));			
			this.villageService.create(village);
		} else {
			village.setAokmotu(this.aokmotuService.findById(village.getAokmotu().getId()));			
			this.villageService.edit(village);
		}

		String url = "/aokmotu/"+village.getAokmotu().getId()+"/details";

        return new ModelAndView("redirect:"+url, model);

	}

	@RequestMapping("/village/{id}/remove")
	public String removeVillage(@PathVariable("id") long id) {

		this.villageService.deleteById(id);

		return "redirect:/aokmotu/list";
	}

     

     

}
