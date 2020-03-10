package kg.gov.mf.loan.web.controller.admin.sys;

import kg.gov.mf.loan.manage.model.orderterm.CurrencyRate;
import kg.gov.mf.loan.manage.model.orderterm.OrderTermCurrency;
import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.manage.service.orderterm.OrderTermCurrencyService;
import kg.gov.mf.loan.web.fetchModels.CurrencyRateModel;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CurrencyRateController {
	
	@Autowired
    private CurrencyRateService currencyRateService;

	@Autowired
	OrderTermCurrencyService currService;

	@PersistenceContext
	EntityManager entityManager;

	private String fromDate="";
	private String toDate="";

    public void setCurrencyRateService(CurrencyRateService rs)
    {
        this.currencyRateService = rs;
    }

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
		binder.registerCustomEditor(Date.class, editor);
	}


	@RequestMapping(value = "/currencyRate/list", method = RequestMethod.GET)
	public String listCurrencyRates(Model model) {

		model.addAttribute("fromdate",this.fromDate);
		model.addAttribute("todate",this.toDate);

		List<OrderTermCurrency> currencies = currService.list();
		model.addAttribute("types", currencies);

		model.addAttribute("currencyRate", new CurrencyRate());
		model.addAttribute("loggedinuser", Utils.getPrincipal());

		return "admin/sys/currencyRateList";
	}

	@RequestMapping(value = "/currencyRatesDates",method = RequestMethod.POST)
	public String something(@RequestParam(value = "fromDate") String fromDate,@RequestParam(value = "toDate") String toDate){
    	this.fromDate=fromDate;
    	this.toDate=toDate;
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
