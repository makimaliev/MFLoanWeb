package kg.gov.mf.loan.web.controller.admin.org;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;

@Controller
public class EmploymentHistoryEventController {
	
	@Autowired
    private EmploymentHistoryEventService employmentHistoryEventService;
	
	@Autowired
    private EmploymentHistoryService employmentHistoryService;	
     
    public void setEmploymentHistoryEventService(EmploymentHistoryEventService ds)
    {
        this.employmentHistoryEventService = ds;
    }
    
    public void setEmploymentHistoryService(EmploymentHistoryService rs)
    {
        this.employmentHistoryService = rs;
    }    
    
    
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}    
    
	@Autowired
    private EmploymentHistoryEventTypeService employmentHistoryEventTypeService;
     
    public void setEmploymentHistoryEventTypeService(EmploymentHistoryEventTypeService rs)
    {
        this.employmentHistoryEventTypeService = rs;
    }
     
	@RequestMapping(value = "/employmentHistoryEvent/list", method = RequestMethod.GET)
	public String listEmploymentHistoryEvents(Model model) {
		model.addAttribute("employmentHistoryEvent", new EmploymentHistoryEvent());
		model.addAttribute("employmentHistoryEventList", this.employmentHistoryEventService.findAll());

		return "admin/org/employmentHistoryEventList";
	}
	
	
	@RequestMapping("/employmentHistoryEvent/{id}/view")
	public String viewEmploymentHistoryEventById(@PathVariable("id") long id, Model model) {

		EmploymentHistoryEvent employmentHistoryEvent = this.employmentHistoryEventService.findById(id);

		model.addAttribute("employmentHistoryEvent", employmentHistoryEvent);
		model.addAttribute("employmentHistoryEventTypeList", this.employmentHistoryEventTypeService.findAll());

		return "admin/org/employmentHistoryEventView";
	}
	
	@RequestMapping("/employmentHistoryEvent/{id}/details")
	public String viewEmploymentHistoryEventDetailsById(@PathVariable("id") long id, Model model) {

		EmploymentHistoryEvent employmentHistoryEvent = this.employmentHistoryEventService.findById(id);

		model.addAttribute("employmentHistoryEvent", employmentHistoryEvent);

		return "admin/org/employmentHistoryEventDetails";
	}	
    
	
	@RequestMapping(value = "/employmentHistory/{employmentHistoryId}/employmentHistoryEvent/add", method = RequestMethod.GET)
	public String getEmploymentHistoryEventAddFormWithEmploymentHistory(@PathVariable("employmentHistoryId") long employmentHistoryId,Model model) {

		EmploymentHistoryEvent modelEmploymentHistoryEvent = new EmploymentHistoryEvent();
		
		modelEmploymentHistoryEvent.setEmploymentHistory(this.employmentHistoryService.findById(employmentHistoryId));
		model.addAttribute("employmentHistoryList", this.employmentHistoryService.findAll());		
		model.addAttribute("employmentHistoryEventTypeList", this.employmentHistoryEventTypeService.findAll());
		
		model.addAttribute("employmentHistoryEvent",modelEmploymentHistoryEvent);

		return "admin/org/employmentHistoryEventForm";
	}	

	
	@RequestMapping("/employmentHistoryEvent/{id}/edit")
	public String getEmploymentHistoryEventEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("employmentHistoryEvent", this.employmentHistoryEventService.findById(id));
		model.addAttribute("employmentHistoryList", this.employmentHistoryService.findAll());
		model.addAttribute("employmentHistoryEventTypeList", this.employmentHistoryEventTypeService.findAll());
		
		return "admin/org/employmentHistoryEventForm";

	}
	
	@RequestMapping(value = "/employmentHistoryEvent/save", method = RequestMethod.POST)
	public ModelAndView saveEmploymentHistoryEventAndRedirectToEmploymentHistoryDetails(@Validated @ModelAttribute("employmentHistoryEvent") EmploymentHistoryEvent employmentHistoryEvent, BindingResult result,ModelMap model) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (employmentHistoryEvent.getId() == 0) {
			//employmentHistoryEvent.setEmploymentHistory(this.employmentHistoryService.findById(employmentHistoryEvent.getEmploymentHistory().getId()));
			
			this.employmentHistoryEventService.create(employmentHistoryEvent);
		} else {
			//employmentHistoryEvent.setEmploymentHistory(this.employmentHistoryService.findById(employmentHistoryEvent.getEmploymentHistory().getId()));
			this.employmentHistoryEventService.edit(employmentHistoryEvent);
		}

//		String url = "/employmentHistory/"+employmentHistoryEvent.getEmploymentHistory().getId()+"/details";

		EmploymentHistory employmentHistory = this.employmentHistoryService.findById(employmentHistoryEvent.getEmploymentHistory().getId());

		Staff staff = employmentHistory.getStaff();


		String url = "/staff/"+staff.getId()+"/details";

        return new ModelAndView("redirect:"+url, model);

	}	
	
	@RequestMapping("/employmentHistoryEvent/{id}/remove")
	public String removeEmploymentHistoryEventAndRedirectToEmploymentHistoryEventList(@PathVariable("id") long id) {

		this.employmentHistoryEventService.deleteById(id);

		return "redirect:/employmentHistory/list";
	}

     

     

}
