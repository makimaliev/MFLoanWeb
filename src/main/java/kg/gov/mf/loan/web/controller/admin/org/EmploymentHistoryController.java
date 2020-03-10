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
public class EmploymentHistoryController {
	
	@Autowired
    private EmploymentHistoryService employmentHistoryService;
	
	@Autowired
    private StaffService staffService;	
     
    public void setEmploymentHistoryService(EmploymentHistoryService ds)
    {
        this.employmentHistoryService = ds;
    }
    
    public void setStaffService(StaffService rs)
    {
        this.staffService = rs;
    }    
     
	@RequestMapping(value = "/employmentHistory/list", method = RequestMethod.GET)
	public String listEmploymentHistorys(Model model) {
		model.addAttribute("employmentHistory", new EmploymentHistory());
		model.addAttribute("employmentHistoryList", this.employmentHistoryService.findAll());

		return "admin/org/employmentHistoryList";
	}
	
	
	@RequestMapping("/employmentHistory/{id}/view")
	public String viewEmploymentHistoryById(@PathVariable("id") long id, Model model) {

		EmploymentHistory employmentHistory = this.employmentHistoryService.findById(id);

		model.addAttribute("employmentHistory", employmentHistory);

		return "admin/org/employmentHistoryView";
	}
	
	@RequestMapping("/employmentHistory/{id}/details")
	public String viewEmploymentHistoryDetailsById(@PathVariable("id") long id, Model model) {

		EmploymentHistory employmentHistory = this.employmentHistoryService.findById(id);

		model.addAttribute("employmentHistory", employmentHistory);

		return "admin/org/employmentHistoryDetails";
	}	
    
	
	@RequestMapping(value = "/staff/{staffId}/employmentHistory/add", method = RequestMethod.GET)
	public String getEmploymentHistoryAddFormWithRegion(@PathVariable("staffId") long staffId,Model model) {

		EmploymentHistory modelEmploymentHistory = new EmploymentHistory();
		
		modelEmploymentHistory.setStaff(this.staffService.findById(staffId));
//		model.addAttribute("staffList", this.staffService.findAll());
		
		model.addAttribute("employmentHistory",modelEmploymentHistory);

		return "admin/org/employmentHistoryForm";
	}	

	
	@RequestMapping("/employmentHistory/{id}/edit")
	public String getEmploymentHistoryEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("employmentHistory", this.employmentHistoryService.findById(id));
//		model.addAttribute("staffList", this.staffService.findAll());
		
		return "admin/org/employmentHistoryForm";

	}
	
	@RequestMapping(value = "/employmentHistory/save", method = RequestMethod.POST)
	public ModelAndView saveEmploymentHistoryAndRedirectToRegionDetails(@Validated @ModelAttribute("employmentHistory") EmploymentHistory employmentHistory, BindingResult result,ModelMap model) {

		if (result.hasErrors()) {

		} else if (employmentHistory.getId() == 0) {
			employmentHistory.setStaff(this.staffService.findById(employmentHistory.getStaff().getId()));
			this.employmentHistoryService.create(employmentHistory);
		} else {
			employmentHistory.setStaff(this.staffService.findById(employmentHistory.getStaff().getId()));
			this.employmentHistoryService.edit(employmentHistory);
		}

		String url = "/staff/"+employmentHistory.getStaff().getId()+"/details";

        return new ModelAndView("redirect:"+url, model);

	}	
	
	@RequestMapping("/employmentHistory/{id}/remove")
	public String removeEmploymentHistoryAndRedirectToEmploymentHistoryList(@PathVariable("id") long id) {

		this.employmentHistoryService.deleteById(id);

		return "redirect:/region/list";
	}

     

     

}
