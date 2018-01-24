package kg.gov.mf.loan.web.controller.output.report;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
public class ReportController {
	
	@Autowired
    private ReportService reportService;
     
    public void setReportService(ReportService rs)
    {
        this.reportService = rs;
    }

	@RequestMapping(value = "/report/list", method = RequestMethod.GET)
	public String listReports(Model model) {
		model.addAttribute("report", new Report());
		model.addAttribute("reportList", this.reportService.findAll());

		return "output/report/reportList";
	}
	
	@RequestMapping("report/{id}/view")
	public String viewReportById(@PathVariable("id") long id, Model model) {

		Report report = this.reportService.findById(id);

		model.addAttribute("report", report);

		return "output/report/reportView";
	}
	
	@RequestMapping("report/{id}/details")
	public String viewReportDetailsById(@PathVariable("id") long id, Model model) {

		Report report = this.reportService.findById(id);

		model.addAttribute("report", report);

		return "output/report/reportDetails";
	}	
    
	
	@RequestMapping(value = "/report/add", method = RequestMethod.GET)
	public String getReportAddForm(Model model) {

		model.addAttribute("report", new Report());

		return "output/report/reportForm";
	}

	@RequestMapping("/report/{id}/edit")
	public String getReportEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("report", this.reportService.findById(id));
		return "output/report/reportForm";

	}

	@RequestMapping(value = "/report/save", method = RequestMethod.POST)
	public String saveReportAndRedirectToReportList(@Validated @ModelAttribute("report") Report report, BindingResult result) {

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
