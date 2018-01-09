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
public class RegionController {
	
	@Autowired
    private RegionService regionService;
     
    public void setRegionService(RegionService rs)
    {
        this.regionService = rs;
    }

	@RequestMapping(value = "/region/list", method = RequestMethod.GET)
	public String listRegions(Model model) {
		model.addAttribute("region", new Region());
		model.addAttribute("regionList", this.regionService.findAll());

		return "admin/org/regionList";
	}
	
	@RequestMapping("region/{id}/view")
	public String viewRegionById(@PathVariable("id") long id, Model model) {

		Region region = this.regionService.findById(id);

		model.addAttribute("region", region);

		return "admin/org/regionView";
	}
	
	@RequestMapping("region/{id}/details")
	public String viewRegionDetailsById(@PathVariable("id") long id, Model model) {

		Region region = this.regionService.findById(id);

		model.addAttribute("region", region);

		return "admin/org/regionDetails";
	}	
    
	
	@RequestMapping(value = "/region/add", method = RequestMethod.GET)
	public String getRegionAddForm(Model model) {

		model.addAttribute("region", new Region());

		return "admin/org/regionForm";
	}

	@RequestMapping("/region/{id}/edit")
	public String getRegionEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("region", this.regionService.findById(id));
		return "admin/org/regionForm";

	}

	@RequestMapping(value = "/region/save", method = RequestMethod.POST)
	public String saveRegionAndRedirectToRegionList(@Validated @ModelAttribute("region") Region region, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (region.getId() == 0) {
			this.regionService.create(region);
		} else {
			this.regionService.edit(region);
		}

		return "redirect:/region/list";

	}

	@RequestMapping("/region/{id}/remove")
	public String removeRegionAndRedirectToRegionList(@PathVariable("id") long id) {

		this.regionService.deleteById(id);

		return "redirect:/region/list";
	}

     

     

}
