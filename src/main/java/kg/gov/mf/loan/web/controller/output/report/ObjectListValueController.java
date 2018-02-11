package kg.gov.mf.loan.web.controller.output.report;

import kg.gov.mf.loan.output.report.model.ObjectListValue;
import kg.gov.mf.loan.output.report.service.ObjectListValueService;
import kg.gov.mf.loan.output.report.service.ReportTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ObjectListValueController {
	
	@Autowired
    private ObjectListValueService objectListValueService;
	

   public void setObjectListValueService(ObjectListValueService rs)
    {
        this.objectListValueService = rs;
    }

	@RequestMapping(value = "/objectListValue/list", method = RequestMethod.GET)
	public String listObjectListValues(Model model) {
		model.addAttribute("objectListValue", new ObjectListValue());
		model.addAttribute("objectListValueList", this.objectListValueService.findAll());

		return "output/report/objectListValueList";
	}
	
	@RequestMapping("objectListValue/{id}/view")
	public String viewObjectListValueById(@PathVariable("id") long id, Model model) {

		ObjectListValue objectListValue = this.objectListValueService.findById(id);

		model.addAttribute("objectListValue", objectListValue);

		return "output/report/objectListValueView";
	}
	
	@RequestMapping("objectListValue/{id}/details")
	public String viewObjectListValueDetailsById(@PathVariable("id") long id, Model model) {

		ObjectListValue objectListValue = this.objectListValueService.findById(id);

		model.addAttribute("objectListValue", objectListValue);

		return "output/report/objectListValueDetails";
	}	
    
	
	@RequestMapping(value = "/objectListValue/add", method = RequestMethod.GET)
	public String getObjectListValueAddForm(Model model) {

		model.addAttribute("objectListValue", new ObjectListValue());

		return "output/report/objectListValueForm";
	}
	
	@RequestMapping("/objectListValue/{id}/edit")
	public String getObjectListValueEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("objectListValue", this.objectListValueService.findById(id));
		return "output/report/objectListValueForm";

	}

	@RequestMapping(value = "/objectListValue/save", method = RequestMethod.POST)
	public String saveObjectListValueAndRedirectToObjectListValueList(@Validated @ModelAttribute("objectListValue") ObjectListValue objectListValue, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (objectListValue.getId() == 0) {
			this.objectListValueService.create(objectListValue);
		} else {
			this.objectListValueService.edit(objectListValue);
		}

		return "redirect:/objectListValue/list";

	}

	@RequestMapping("/objectListValue/{id}/remove")
	public String removeObjectListValueAndRedirectToObjectListValueList(@PathVariable("id") long id) {

		this.objectListValueService.deleteById(id);

		return "redirect:/objectListValue/list";
	}

     

     

}
