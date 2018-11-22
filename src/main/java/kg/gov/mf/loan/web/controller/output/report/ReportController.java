package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.output.report.service.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ReportController {
	
	@Autowired
    private ReportService reportService;


	@Autowired
	private ReportTemplateService reportTemplateService;

	@Autowired
	private FilterParameterService filterParameterService;

	@Autowired
	private UserService userService;

	@Autowired
	private ContentParameterService contentParameterService;

	@Autowired
	private OutputParameterService outputParameterService;

	@Autowired
	private GroupTypeService groupTypeService;
     
    public void setReportService(ReportService rs)
    {
        this.reportService = rs;
    }

	@RequestMapping(value = "/report/list", method = RequestMethod.GET)
	public String listReports(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUserName = authentication.getName();

		User currentUser = this.userService.findByUsername(currentUserName);

    	Report modelReport = new Report();
		model.addAttribute("report", modelReport);
		model.addAttribute("reportList", this.reportService.findByUser(currentUser));

//		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
//		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
//		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
//
//
//		model.addAttribute("reportFilterParameters", modelReport.getFilterParameters());
//		model.addAttribute("reportContentParameters", modelReport.getContentParameters());
//		model.addAttribute("reportContentParameters", modelReport.getGroupTypes());

		return "output/report/reportList";
	}
	
	@RequestMapping("report/{id}/view")
	public String viewReportById(@PathVariable("id") long id, Model model) {


		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUserName = authentication.getName();

		User currentUser = this.userService.findByUsername(currentUserName);


		Report report = this.reportService.findById(id);

		List<ReportTemplate> reportTemplates = new ArrayList<>();

		reportTemplates = this.reportTemplateService.findByUser(currentUser);

		if(currentUser.getId()!=1)
		{
			Set<ReportTemplate> reportReportTemplates = new HashSet<>();

			for (ReportTemplate reportTemplate: reportTemplates)
			{
				if(report.getReportTemplates().contains(reportTemplate))
				{
					reportReportTemplates.add(this.reportTemplateService.findById(reportTemplate.getId()));
				}
			}

			report.setReportTemplates(reportReportTemplates);


		}

		model.addAttribute("report", report);

		return "output/report/reportView";
	}
	
	@RequestMapping("report/{id}/details")
	public String viewReportDetailsById(@PathVariable("id") long id, Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String currentUserName = authentication.getName();

		User currentUser = this.userService.findByUsername(currentUserName);


		Report report = this.reportService.findById(id);

		List<ReportTemplate> reportTemplates = new ArrayList<>();

		reportTemplates = this.reportTemplateService.findByUser(currentUser);

		for (ReportTemplate reportTemplate: reportTemplates)
		{
			if(currentUser.getId()!=1)
				if(!report.getReportTemplates().contains(reportTemplate))
				{
					report.getReportTemplates().remove(reportTemplate);
				}
		}

		model.addAttribute("report", report);

		return "output/report/reportDetails";
	}	
    
	
	@RequestMapping(value = "/report/add", method = RequestMethod.GET)
	public String getReportAddForm(Model model) {


		Report modelReport = new Report();
		model.addAttribute("report", modelReport);
		model.addAttribute("reportList", this.reportService.findAll());

		model.addAttribute("userList", this.userService.findAll());

		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());


		model.addAttribute("reportFilterParameters", modelReport.getFilterParameters());
		model.addAttribute("reportContentParameters", modelReport.getContentParameters());
		model.addAttribute("reportGroupTypes", modelReport.getGroupTypes());
		model.addAttribute("reportOutputParameters", modelReport.getOutputParameters());

		return "output/report/reportForm";
	}

	@RequestMapping("/report/{id}/edit")
	public String getReportEditForm(@PathVariable("id") long id, Model model) {

		Report modelReport = this.reportService.findById(id);

		model.addAttribute("report", modelReport);
		model.addAttribute("reportList", this.reportService.findAll());

		model.addAttribute("userList", this.userService.findAll());
		model.addAttribute("groupTypeList", this.groupTypeService.findAll());
		model.addAttribute("filterParameterList", this.filterParameterService.findAll());
		model.addAttribute("contentParameterList", this.contentParameterService.findAll());
		model.addAttribute("outputParameterList", this.outputParameterService.findAll());


		model.addAttribute("reportFilterParameters", modelReport.getFilterParameters());
		model.addAttribute("reportContentParameters", modelReport.getContentParameters());
		model.addAttribute("reportGroupTypes", modelReport.getGroupTypes());
		model.addAttribute("reportOutputParameters", modelReport.getOutputParameters());

		return "output/report/reportForm";

	}

	@RequestMapping(value = "/report/save", method = RequestMethod.POST)
	public String saveReportAndRedirectToReportList(@Validated @ModelAttribute("report") Report report,
													String[] reportFilterParametersIds,
													String[] reportContentParametersIds,
													String[] reportOutputParametersIds,
													String[] reportGroupTypeIds,
													BindingResult result) {



		if(reportGroupTypeIds!=null)
			for (String id: reportGroupTypeIds)
			{
				report.getGroupTypes().add(this.groupTypeService.findById(Long.valueOf(id)));
			}

		if(reportFilterParametersIds!=null)
			for (String id: reportFilterParametersIds)
			{
				report.getFilterParameters().add(this.filterParameterService.findById(Long.valueOf(id)));
			}


		if(reportContentParametersIds!=null)
			for (String id: reportContentParametersIds)
			{
				report.getContentParameters().add(this.contentParameterService.findById(Long.valueOf(id)));
			}

		if(reportOutputParametersIds!=null)
			for (String id: reportOutputParametersIds)
			{
				report.getOutputParameters().add(this.outputParameterService.findById(Long.valueOf(id)));
			}


			if(report.getUsers().size()==0)
			{
				Set<User> users = new HashSet<>();

				users.add(userService.findByUsername("admin"));

				report.setUsers(users);
			}

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (report.getId() == 0) {
			this.reportService.create(report);
		} else {
			this.reportService.edit(report);
		}

		return "redirect:/report/list";

	}

	@RequestMapping("/report/{id}/remove")
	public String removeReportAndRedirectToReportList(@PathVariable("id") long id) {

		this.reportService.deleteById(id);

		return "redirect:/report/list";
	}

     

     

}
