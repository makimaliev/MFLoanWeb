package kg.gov.mf.loan.web.controller.output.printout;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import kg.gov.mf.loan.output.printout.model.Printout;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.printout.service.*;
import kg.gov.mf.loan.output.printout.utils.*;
import kg.gov.mf.loan.web.util.Utils;
import org.apache.poi.ss.formula.ptg.MemAreaPtg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static kg.gov.mf.loan.web.util.Utils.getPrincipal;

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

	@RequestMapping("printoutTemplate/{id}/objectId/{objectId}/select")
	public String viewPrintoutTemplateSelectPageByObjectId(@PathVariable("id") long id, @PathVariable("objectId") long objectId,Model model) {

		Printout printout = this.printoutService.findById(id);

		model.addAttribute("objectId", objectId);
		model.addAttribute("printoutTemplateList", printout.getPrintoutTemplates());

		return "output/printout/printoutTemplateSelectList";
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
	public void generatePrintoutByPrintoutTemplate(@PathVariable("id") long id, @PathVariable("object_id") long object_id, String date,  String name,
												   HttpServletResponse response) {
		try
		{


			PrintoutTemplate printoutTemplate = this.printoutTemplateService.findById(id);


			SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

			if(name!=null&& !name.equals("loanId"))
			{
				printoutTemplate.setName(name);
			}


			Date onDate = null;

			if(date!=null)
				onDate = DateFormatShort.parse(date);


			switch(printoutTemplate.getPrintout().getPrintoutType().toString())
			{
				// Реестр погашений
				case "PAYMENT_SUMMARY":


					try {

						PrintoutGeneratorPaymentSummary printoutGeneratorPaymentSummary = new PrintoutGeneratorPaymentSummary();

						Document document = new Document(PageSize.A4, 10, 10, 10, 10);

						PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

						response.setContentType("application/pdf");
						response.setHeader("Content-disposition","attachment; filename=xx.pdf");

						SimpleDateFormat dt= new SimpleDateFormat("yyyyy-mm-dd");
						try{

							if(name.equals("loanId")){
								printoutGeneratorPaymentSummary.generatePrintoutByTemplate(printoutTemplate,onDate , object_id,document,"true");
							}
							else
								printoutGeneratorPaymentSummary.generatePrintoutByTemplate(printoutTemplate, null, object_id,document,"false");
						}
						catch (Exception e){
							printoutGeneratorPaymentSummary.generatePrintoutByTemplate(printoutTemplate, null, object_id,document,"false");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;

				// Уведомление
				case "LOAN_NOTIFICATION":


					try {

						PrintoutGeneratorCreditNotification printoutGeneratorCreditNotification= new PrintoutGeneratorCreditNotification();

						Document document = new Document(PageSize.A4, 10, 10, 10, 10);

						PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

						response.setContentType("application/pdf");
						response.setHeader("Content-disposition","attachment; filename=xx.pdf");

						printoutGeneratorCreditNotification.generatePrintoutByTemplate(printoutTemplate, null, object_id,document);




					} catch (Exception e) {
						e.printStackTrace();
					}

					break;

					// Детальный расчет
				case "LOAN_DETAILED_SUMMARY":


					try {

						PrintoutGeneratorLoanDetailedSummary printoutGeneratorLoanDetailedSummary= new PrintoutGeneratorLoanDetailedSummary();

						Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);

						PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

						response.setContentType("application/pdf");
						response.setHeader("Content-disposition","attachment; filename=xx.pdf");


						printoutGeneratorLoanDetailedSummary.generatePrintoutByTemplate(printoutTemplate, null, object_id,document);




					} catch (Exception e) {
						e.printStackTrace();
					}

					break;

					// Претензия
				case "LOAN_CLAIM":


					try {

						PrintoutGeneratorCreditClaim printoutGeneratorCreditClaim= new PrintoutGeneratorCreditClaim();

						Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);

						PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

						response.setContentType("application/pdf");
						response.setHeader("Content-disposition","attachment; filename=xx.pdf");

						printoutGeneratorCreditClaim.generatePrintoutByTemplate(printoutTemplate, null, object_id,document);

					} catch (Exception e) {
						e.printStackTrace();
					}

					break;

					// Акт сверки
                case "LOAN_SUMMARY":


                    try {

                        PrintoutGeneratorLoanSummary printoutGeneratorLoanSummary = new PrintoutGeneratorLoanSummary();

                        Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);

                        PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition","attachment; filename=xx.pdf");



                        printoutGeneratorLoanSummary.generatePrintoutByTemplate(printoutTemplate, onDate, object_id,document);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;


				case "COLLECTION_SUMMARY":


					try {

						PrintoutGeneratorPhaseSummary printoutGeneratorPhaseSummary = new PrintoutGeneratorPhaseSummary();

						Document document = new Document(PageSize.A4, 10, 10, 10, 10);


						RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document,response.getOutputStream());

						response.setContentType("application/rtf");
						response.setHeader("Content-disposition","attachment; filename=claim.rtf");

						printoutGeneratorPhaseSummary.generatePrintoutByTemplate(printoutTemplate, null, object_id,document);


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



	@RequestMapping("/printoutType/{id}/objectId/{object_id}/generate")
	public void generatePrintout(@PathVariable("id") long id, @PathVariable("object_id") long object_id,
												   HttpServletResponse response) {
		try {

			PrintoutGeneratorCreditNotification printoutGeneratorCreditNotification= new PrintoutGeneratorCreditNotification();

			Document document = new Document(PageSize.A4, 10, 10, 10, 10);

			PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment; filename=xx.pdf");

			printoutGeneratorCreditNotification.generatePrintoutByTemplate(null, null, object_id,document);




		} catch (Exception e) {
			e.printStackTrace();
		}




	}

	@RequestMapping("/printoutType/1/objectId/{object_id}/generate")
	public void generatePrintoutByType1(@PathVariable("object_id") long object_id, String date,  String name,
								 HttpServletResponse response) {
		try {

			PrintoutGeneratorRevisionDoc printoutGeneratorRevisionDoc= new PrintoutGeneratorRevisionDoc();

			PrintoutTemplate printoutTemplate = new PrintoutTemplate();

			SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

			printoutTemplate.setName(name);
			Date onDate = DateFormatShort.parse(date);


			Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);

			PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment; filename=xx.pdf");

			printoutGeneratorRevisionDoc.generatePrintoutByTemplate(printoutTemplate, onDate, object_id,document);




		} catch (Exception e) {
			e.printStackTrace();
		}




	}

    @RequestMapping("/printoutType/2/objectId/{object_id}/generate")
    public void generatePrintoutByType2(@PathVariable("object_id") long object_id,
                                        HttpServletResponse response) {
        try {

            PrintoutGeneratorPhaseSummary printoutGeneratorPhaseSummary= new PrintoutGeneratorPhaseSummary();

            Document document = new Document(PageSize.A4, 20, 20, 10, 10);

//            PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

			RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document,response.getOutputStream());

            response.setContentType("application/rtf");
            response.setHeader("Content-disposition","attachment; filename=xx.rtf");

            printoutGeneratorPhaseSummary.generatePrintoutByTemplate(null, null, object_id,document);




        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    @RequestMapping("/printoutType/3/objectId/{object_id}/generate")
    public void generatePrintoutByType3(@PathVariable("object_id") long object_id,
                                        HttpServletResponse response) {
        try {

            PrintoutGeneratorCreditClaim printoutGeneratorCreditClaim= new PrintoutGeneratorCreditClaim();

            Document document = new Document(PageSize.A4, 20, 20, 10, 10);

			RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document,response.getOutputStream());

            response.setContentType("application/rtf");
            response.setHeader("Content-disposition","attachment; filename=xx.rtf");

            printoutGeneratorCreditClaim.generatePrintoutByTemplate(null, null, object_id,document);




        } catch (Exception e) {
            e.printStackTrace();
        }




    }
	@RequestMapping("/printoutType/4/objectId/{object_id}/generate")
	public void generateSomething(@PathVariable("object_id") long object_id,
										HttpServletResponse response) {
		try {

			PrintoutGeneratorInspectionAct printoutGeneratorInspectionAct= new PrintoutGeneratorInspectionAct();

			Document document = new Document(PageSize.A4, 20, 20, 10, 10);

			RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document,response.getOutputStream());

			response.setContentType("application/rtf");
			response.setHeader("Content-disposition","attachment; filename=xx.rtf");

			String username= Utils.getPrincipal();

			printoutGeneratorInspectionAct.generatePrintout(object_id,document,username);




		} catch (Exception e) {
			e.printStackTrace();
		}




	}
}
