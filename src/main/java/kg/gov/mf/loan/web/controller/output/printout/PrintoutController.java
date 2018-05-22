package kg.gov.mf.loan.web.controller.output.printout;

import kg.gov.mf.loan.output.printout.model.Printout;
import kg.gov.mf.loan.output.printout.service.PrintoutService;
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
public class PrintoutController {
	
	@Autowired
    private PrintoutService printoutService;
     
    public void setPrintoutService(PrintoutService rs)
    {
        this.printoutService = rs;
    }

	@RequestMapping(value = "/printout/list", method = RequestMethod.GET)
	public String listPrintouts(Model model) {
		model.addAttribute("printout", new Printout());
		model.addAttribute("printoutList", this.printoutService.findAll());

		return "output/printout/printoutList";
	}
	
	@RequestMapping("printout/{id}/view")
	public String viewPrintoutById(@PathVariable("id") long id, Model model) {

		Printout printout = this.printoutService.findById(id);

		model.addAttribute("printout", printout);

		return "output/printout/printoutView";
	}
	
	@RequestMapping("printout/{id}/details")
	public String viewPrintoutDetailsById(@PathVariable("id") long id, Model model) {

		Printout printout = this.printoutService.findById(id);

		model.addAttribute("printout", printout);

		return "output/printout/printoutDetails";
	}	
    
	
	@RequestMapping(value = "/printout/add", method = RequestMethod.GET)
	public String getPrintoutAddForm(Model model) {

		model.addAttribute("printout", new Printout());

		return "output/printout/printoutForm";
	}

	@RequestMapping("/printout/{id}/edit")
	public String getPrintoutEditForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("printout", this.printoutService.findById(id));
		return "output/printout/printoutForm";

	}

	@RequestMapping(value = "/printout/save", method = RequestMethod.POST)
	public String savePrintoutAndRedirectToPrintoutList(@Validated @ModelAttribute("printout") Printout printout, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (printout.getId() == 0) {
			this.printoutService.create(printout);
		} else {
			this.printoutService.edit(printout);
		}

		return "redirect:/printout/list";

	}

	@RequestMapping("/printout/{id}/remove")
	public String removePrintoutAndRedirectToPrintoutList(@PathVariable("id") long id) {

		this.printoutService.deleteById(id);

		return "redirect:/printout/list";
	}

     

     

}
