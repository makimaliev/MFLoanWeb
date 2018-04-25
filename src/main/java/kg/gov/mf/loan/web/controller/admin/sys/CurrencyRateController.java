package kg.gov.mf.loan.web.controller.admin.sys;

import kg.gov.mf.loan.manage.model.orderterm.CurrencyRate;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class CurrencyRateController {
	
	@Autowired
    private CurrencyRateService currencyRateService;

	@Autowired
	OrderTermCurrencyService currService;
     
    public void setCurrencyRateService(CurrencyRateService rs)
    {
        this.currencyRateService = rs;
    }

	@RequestMapping(value = "/currencyRate/list", method = RequestMethod.GET)
	public String listCurrencyRates(Model model) {
		model.addAttribute("currencyRate", new CurrencyRate());
		model.addAttribute("currencyRateList", this.currencyRateService.findAll());

		return "admin/sys/currencyRateList";
	}
	
	
	@RequestMapping("/currencyRate/{id}/view")
	public String viewCurrencyRateById(@PathVariable("id") long id, Model model) {

		CurrencyRate currencyRate = this.currencyRateService.findById(id);

		model.addAttribute("currencyRate", currencyRate);

		return "admin/sys/currencyRateView";
	}
    
	
	@RequestMapping("/currencyRate/{id}/details")
	public String viewCurrencyRateDetailsById(@PathVariable("id") long id, Model model) {

		CurrencyRate currencyRate = this.currencyRateService.findById(id);

		model.addAttribute("currencyRate", currencyRate);

		return "admin/sys/currencyRateDetails";
	}	
	
	@RequestMapping(value = "/currencyRate/add", method = RequestMethod.GET)
	public String getCurrencyRateAddForm(Model model) {

		model.addAttribute("currencyRate", new CurrencyRate());
		List<OrderTermCurrency> currs = currService.list();
		model.addAttribute("currencies", currs);

		return "admin/sys/currencyRateForm";
	}
	
	
	
	@RequestMapping(value = "/objectField/{objectFieldId}/currencyRate/add", method = RequestMethod.GET)
	public String getCurrencyRateAddByObjectFieldIdForm(@PathVariable("objectFieldId") long objectFieldId,Model model) {

		CurrencyRate modelCurrencyRate = new CurrencyRate();
		model.addAttribute("currencyRate",modelCurrencyRate);

		return "admin/sys/currencyRateForm";
	}
	
	
	@RequestMapping("/currencyRate/{id}/edit")
	public String getCurrencyRateEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("currencyRate", this.currencyRateService.findById(id));
		List<OrderTermCurrency> currs = currService.list();
		model.addAttribute("currencies", currs);
		return "admin/sys/currencyRateForm";

	}

	@RequestMapping(value = "/currencyRate/save", method = RequestMethod.POST)
	public String saveCurrencyRateAndRedirectToCurrencyRateList(@Validated @ModelAttribute("currencyRate") CurrencyRate currencyRate, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (currencyRate.getId() == 0) {
			this.currencyRateService.create(currencyRate);
		} else {
			this.currencyRateService.edit(currencyRate);
		}

		return "redirect:/currencyRate/list";

	}

	@RequestMapping("/currencyRate/{id}/remove")
	public String removeCurrencyRateAndRedirectToCurrencyRateList(@PathVariable("id") long id) {

		this.currencyRateService.deleteById(id);

		return "redirect:/currencyRate/list";
	}

     

     

}
