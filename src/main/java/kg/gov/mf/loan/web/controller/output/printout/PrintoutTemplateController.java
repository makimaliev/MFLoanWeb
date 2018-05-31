package kg.gov.mf.loan.web.controller.output.printout;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import kg.gov.mf.loan.output.printout.model.Printout;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.printout.service.*;
import kg.gov.mf.loan.output.printout.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class PrintoutTemplateController {
	
	@Autowired
    private PrintoutTemplateService printoutTemplateService;


	@Autowired
    private PrintoutService printoutService;
	
     
    public void setPrintoutTemplateService(PrintoutTemplateService rs)
    {
        this.printoutTemplateService = rs;
    }

	@RequestMapping(value = "/printoutTemplate/list", method = RequestMethod.GET)
	public String listPrintoutTemplates(Model model) {
		model.addAttribute("printoutTemplate", new PrintoutTemplate());

		model.addAttribute("printoutTemplateList", this.printoutTemplateService.findAll());


		return "output/printout/printoutTemplateList";
	}
	
	@RequestMapping("printoutTemplate/{id}/view")
	public String viewPrintoutTemplateById(@PathVariable("id") long id, Model model) {

		PrintoutTemplate printoutTemplate = this.printoutTemplateService.findById(id);


		model.addAttribute("printoutTemplate", printoutTemplate);

		return "output/printout/printoutTemplateView";
	}
	
	@RequestMapping("printoutTemplate/{id}/details")
	public String viewPrintoutTemplateDetailsById(@PathVariable("id") long id, Model model) {

		PrintoutTemplate printoutTemplate = this.printoutTemplateService.findById(id);

		model.addAttribute("printoutTemplate", printoutTemplate);

		return "output/printout/printoutTemplateDetails";
	}	
    
	
	@RequestMapping(value = "/printoutTemplate/add", method = RequestMethod.GET)
	public String getPrintoutTemplateAddForm(Model model) {

		PrintoutTemplate modelPrintoutTemplate = new PrintoutTemplate();
		
		modelPrintoutTemplate.setPrintout(this.printoutService.findById((long)1));
		model.addAttribute("printoutList", this.printoutService.findAll());

		model.addAttribute("printoutTemplate",modelPrintoutTemplate);
		

		return "output/printout/printoutTemplateForm";
	}
	
	@RequestMapping(value = "/printout/{printoutId}/printoutTemplate/add", method = RequestMethod.GET)
	public String getPrintoutTemplateAddFormWithPrintout(@PathVariable("printoutId") long printoutId,Model model) {

		PrintoutTemplate modelPrintoutTemplate = new PrintoutTemplate();
		
		modelPrintoutTemplate.setPrintout(this.printoutService.findById(printoutId));
		model.addAttribute("printoutList", this.printoutService.findAll());




		model.addAttribute("printoutTemplate",modelPrintoutTemplate);

		return "output/printout/printoutTemplateForm";
	}	

	@RequestMapping("/printoutTemplate/{id}/edit")
	public String getPrintoutTemplateEditForm(@PathVariable("id") long id, Model model) {

/*
    	PrintoutTemplate modelPrintoutTemplate = this.printoutTemplateService.findById(id);
    	modelPrintoutTemplate.setGenerationParameters(this.printoutTemplateService.findById(id).getGenerationParameters());
*/



		model.addAttribute("printoutTemplate", this.printoutTemplateService.findById(id));

		model.addAttribute("printoutList", this.printoutService.findAll());


		return "output/printout/printoutTemplateForm";

	}

	@RequestMapping(value = "/printoutTemplate/save", method = RequestMethod.POST)
	public String savePrintoutTemplateAndRedirectToPrintoutTemplateList(@Validated @ModelAttribute("printoutTemplate") PrintoutTemplate printoutTemplate,
																	BindingResult result) {

		Printout printout = this.printoutService.findById(printoutTemplate.getPrintout().getId());




		printoutTemplate.setPrintout(printout);
		
		if (result.hasErrors()) {
			System.out.println(" ==== BINDING ERROR ====" + result.getAllErrors().toString());
		} else if (printoutTemplate.getId() == 0) {
			this.printoutTemplateService.create(printoutTemplate);
		} else {
			this.printoutTemplateService.edit(printoutTemplate);
		}

		return "redirect:/printoutTemplate/list";

	}

	@RequestMapping("/printoutTemplate/{id}/remove")
	public String removePrintoutTemplateAndRedirectToPrintoutTemplateList(@PathVariable("id") long id) {

		this.printoutTemplateService.deleteById(id);

		return "redirect:/printoutTemplate/list";
	}


	@RequestMapping("/printoutTemplate/{id}/objectId/{object_id}/generate")
	public void generatePrintoutByPrintoutTemplate(@PathVariable("id") long id, @PathVariable("object_id") long object_id, HttpServletResponse response) {




		try
		{


			PrintoutTemplate printoutTemplate = this.printoutTemplateService.findById(id);




			switch(printoutTemplate.getPrintout().getPrintoutType().toString())
			{
				case "PAYMENT_SUMMARY":


					try {

						PrintoutGeneratorPaymentSummary printoutGeneratorPaymentSummary = new PrintoutGeneratorPaymentSummary();

						Document document = new Document(PageSize.A4, 10, 10, 10, 10);

						PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

						response.setContentType("application/pdf");
						response.setHeader("Content-disposition","attachment; filename=xx.pdf");

						printoutGeneratorPaymentSummary.generatePrintoutByTemplate(printoutTemplate, null, object_id,document);




					} catch (Exception e) {
						e.printStackTrace();
					}

					break;



			}

		}
		catch (Exception ex)
		{

		}



	}


}