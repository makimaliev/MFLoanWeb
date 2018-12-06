package kg.gov.mf.loan.web.controller.admin.sys;

import kg.gov.mf.loan.manage.model.orderterm.FloatingRate;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermFloatingRateType;
import kg.gov.mf.loan.manage.service.orderterm.FloatingRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermFloatingRateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class FloatingRateController {
	
	@Autowired
    private FloatingRateService floatingRateService;

	@Autowired
	OrderTermFloatingRateTypeService rateTypeService;
     
    public void setFloatingRateService(FloatingRateService rs)
    {
        this.floatingRateService = rs;
    }

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value = "/floatingRate/list", method = RequestMethod.GET)
	public String listFloatingRates(Model model) {

		List<OrderTermFloatingRateType> rateTypes = rateTypeService.list();

		model.addAttribute("floatingRate", new FloatingRate());
		model.addAttribute("types", rateTypes);

		return "admin/sys/floatingRateList";
	}
	
	
	@RequestMapping("/floatingRate/{id}/view")
	public String viewFloatingRateById(@PathVariable("id") long id, Model model) {

		FloatingRate floatingRate = this.floatingRateService.findById(id);

		model.addAttribute("floatingRate", floatingRate);

		return "admin/sys/floatingRateView";
	}
    
	
	@RequestMapping("/floatingRate/{id}/details")
	public String viewFloatingRateDetailsById(@PathVariable("id") long id, Model model) {

		FloatingRate floatingRate = this.floatingRateService.findById(id);

		model.addAttribute("floatingRate", floatingRate);

		return "admin/sys/floatingRateDetails";
	}	
	
	@RequestMapping(value = "/floatingRate/add", method = RequestMethod.GET)
	public String getFloatingRateAddForm(Model model) {

		model.addAttribute("floatingRate", new FloatingRate());
		List<OrderTermFloatingRateType> rateTypes = rateTypeService.list();
		model.addAttribute("rateTypes", rateTypes);

		return "admin/sys/floatingRateForm";
	}
	
	
	
	@RequestMapping(value = "/objectField/{objectFieldId}/floatingRate/add", method = RequestMethod.GET)
	public String getFloatingRateAddByObjectFieldIdForm(@PathVariable("objectFieldId") long objectFieldId,Model model) {

		FloatingRate modelFloatingRate = new FloatingRate();
		model.addAttribute("floatingRate",modelFloatingRate);

		return "admin/sys/floatingRateForm";
	}
	
	
	@RequestMapping("/floatingRate/{id}/edit")
	public String getFloatingRateEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("floatingRate", this.floatingRateService.findById(id));
		List<OrderTermFloatingRateType> rateTypes = rateTypeService.list();
		model.addAttribute("rateTypes", rateTypes);
		return "admin/sys/floatingRateForm";

	}

	@RequestMapping(value = "/floatingRate/save", method = RequestMethod.POST)
	public String saveFloatingRateAndRedirectToFloatingRateList(@Validated @ModelAttribute("floatingRate") FloatingRate floatingRate, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (floatingRate.getId() == 0) {
			this.floatingRateService.create(floatingRate);
		} else {
			this.floatingRateService.edit(floatingRate);
		}

		return "redirect:/floatingRate/list";

	}

	@RequestMapping("/floatingRate/{id}/remove")
	public String removeFloatingRateAndRedirectToFloatingRateList(@PathVariable("id") long id) {

		this.floatingRateService.deleteById(id);

		return "redirect:/floatingRate/list";
	}

     

     

}
