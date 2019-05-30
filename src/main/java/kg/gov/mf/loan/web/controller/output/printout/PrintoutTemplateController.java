package kg.gov.mf.loan.web.controller.output.printout;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;
import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.loan.LoanSummaryAct;
import kg.gov.mf.loan.manage.service.loan.LoanSummaryActService;
import kg.gov.mf.loan.output.printout.model.Printout;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.printout.service.PrintoutService;
import kg.gov.mf.loan.output.printout.service.PrintoutTemplateService;
import kg.gov.mf.loan.output.printout.utils.*;
import kg.gov.mf.loan.web.util.Utils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.poi.xwpf.usermodel.*;
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
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Controller
public class PrintoutTemplateController {

	@Autowired
	AddressService addressService;
	
	@Autowired
    private PrintoutTemplateService printoutTemplateService;


	@Autowired
    private PrintoutService printoutService;

	@Autowired
	LoanSummaryActService loanSummaryActService;

	@Autowired
	UserService userService;

	@Autowired
	PersonService personService;

	@Autowired
	StaffService staffService;

	@Autowired
	IdentityDocService identityDocService;

	@Autowired
	EmploymentHistoryService employmentHistoryService;



	private static String UPLOADED_FOLDER =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "c://temp//";


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

						Document document = new Document(PageSize.A4, 25, 25, 10, 10);

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

						Document document = new Document(PageSize.A4, 25, 25, 10, 10);

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

						Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

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

						Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

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

                        Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

                        PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

                        response.setContentType("application/pdf");
                        response.setHeader("Content-disposition","attachment; filename=xx.pdf");



                        printoutGeneratorLoanSummary.generatePrintoutByTemplate(printoutTemplate, onDate, object_id,document,"false");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;


				case "COLLECTION_SUMMARY":


					try {

						PrintoutGeneratorPhaseSummary printoutGeneratorPhaseSummary = new PrintoutGeneratorPhaseSummary();

						Document document = new Document(PageSize.A4, 25, 25, 10, 10);


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

			Document document = new Document(PageSize.A4, 25, 25, 10, 10);

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


			Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

			PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment; filename=xx.pdf");

			printoutGeneratorRevisionDoc.generatePrintoutByTemplate(printoutTemplate, onDate, object_id,document,"loan");




		} catch (Exception e) {
			e.printStackTrace();
		}




	}

    @RequestMapping("/printoutType/2/objectId/{object_id}/generate")
    public void generatePrintoutByType2(@PathVariable("object_id") long object_id,
                                        HttpServletResponse response) {
        try {

            PrintoutGeneratorPhaseSummary printoutGeneratorPhaseSummary= new PrintoutGeneratorPhaseSummary();

            Document document = new Document(PageSize.A4, 25, 25, 10, 10);

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

            Document document = new Document(PageSize.A4, 25, 25, 10, 10);

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

			Document document = new Document(PageSize.A4, 35, 35, 10, 10);

			RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document,response.getOutputStream());

			response.setContentType("application/rtf");
			response.setHeader("Content-disposition","attachment; filename=xx.rtf");

			String username= Utils.getPrincipal();

			printoutGeneratorInspectionAct.generatePrintout(object_id,document,username);




		} catch (Exception e) {
			e.printStackTrace();
		}




	}

	@RequestMapping("/printoutType/act/{id}/generate")
	public void printForAct(@PathVariable("id") long id,
							HttpServletResponse response){

		try {
			PrintoutTemplate printoutTemplate = new PrintoutTemplate();

			PrintoutGeneratorLoanSummary printoutGeneratorLoanSummary = new PrintoutGeneratorLoanSummary();

			Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

			PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment; filename=xx.pdf");

			LoanSummaryAct loanSummaryAct=loanSummaryActService.getById(id);


			printoutGeneratorLoanSummary.generatePrintoutByTemplate(printoutTemplate, loanSummaryAct.getOnDate(), id,document,"true");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/printoutType/summary/act/{id}/generate")
	public void generatePrintoutByType1(@PathVariable("id") long id,
										HttpServletResponse response) {
		try {

			PrintoutGeneratorRevisionDoc printoutGeneratorRevisionDoc= new PrintoutGeneratorRevisionDoc();

			PrintoutTemplate printoutTemplate = new PrintoutTemplate();

			SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");


			Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

			PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment; filename=xx.pdf");

			LoanSummaryAct loanSummaryAct = loanSummaryActService.getById(id);

			printoutGeneratorRevisionDoc.generatePrintoutByTemplate(printoutTemplate, loanSummaryAct.getOnDate(), id,document,"act");




		} catch (Exception e) {
			e.printStackTrace();
		}




	}

    @RequestMapping("/printoutType/debtor/{id}/generate")
    public void generateDebtorInfo(@PathVariable("id") long id,  String date,  String name,
                                        HttpServletResponse response) {
        try {

            PrintoutGeneratorRevisionDoc printoutGeneratorRevisionDoc= new PrintoutGeneratorRevisionDoc();

            PrintoutTemplate printoutTemplate = new PrintoutTemplate();

            SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

            printoutTemplate.setName(name);

            Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

            PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition","attachment; filename=xx.pdf");


            printoutGeneratorRevisionDoc.generatePersonInfo(printoutTemplate, DateFormatShort.parse(date), id,document);




        } catch (Exception e) {
            e.printStackTrace();
        }




    }


	@RequestMapping("/printoutTemplate/kg/objectId/{object_id}/generate")
	public void generateKyrgyz(@PathVariable("object_id") long object_id, String date,  String name,
							   HttpServletResponse response){

		try
		{


			PrintoutTemplate printoutTemplate = this.printoutTemplateService.findById(Long.valueOf(4));


			SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

			if(name!=null&& !name.equals("loanId"))
			{
				printoutTemplate.setName(name);
			}


			Date onDate = null;

			if(date!=null)
				onDate = DateFormatShort.parse(date);



					try {

						PrintoutGeneratorLoanSummary printoutGeneratorLoanSummary = new PrintoutGeneratorLoanSummary();

						Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

						PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

						response.setContentType("application/pdf");
						response.setHeader("Content-disposition","attachment; filename=xx.pdf");



						printoutGeneratorLoanSummary.generatePrintoutByTemplateInKg(printoutTemplate, onDate, object_id,document,"false");

					} catch (Exception e) {
						e.printStackTrace();
					}

		}
		catch (Exception ex)
		{

		}

	}


	@RequestMapping("/printout/{type_id}/objectId/{object_id}/generate")
	public void generateDocByTypeAndPersonId(@PathVariable("object_id") long object_id,
											 @PathVariable("type_id") long type_id,
								  HttpServletResponse response) {
		try
		{

            User user1=userService.findByUsername(Utils.getPrincipal());
            User user=userService.findById(user1.getId());
            Staff staff=staffService.findById(user.getStaff().getId());
            Person person=personService.findById(Long.valueOf(object_id));
            IdentityDoc identityDoc=identityDocService.findById(person.getIdentityDoc().getId());
            XWPFDocument doc=new XWPFDocument();

		    switch ((int) type_id){

                case 1:

                    String filePath = UPLOADED_FOLDER+ "/"+"1. Договор займа.docx";

                    File file = new File(filePath);

                    FileInputStream fInput = new FileInputStream(file.getAbsolutePath());
                    doc = new XWPFDocument(fInput);

                    for (XWPFParagraph p : doc.getParagraphs())
                    {
                        List<XWPFRun> runs = p.getRuns();

                        if (runs != null)
                        {
                            for (XWPFRun r : runs)
                            {
                                String text = r.getText(0);
                                if (text != null && text.contains("(="))
                                {
                                    replace(r,user,staff,person,identityDoc);
//							text = text.replace("(=", "$");
//							r.setText(text, 0);
                                }
                            }
                        }
                    }

                    for (XWPFTable tbl : doc.getTables())
                    {
                        for (XWPFTableRow row : tbl.getRows())
                        {
                            for (XWPFTableCell cell : row.getTableCells())
                            {
                                for (XWPFParagraph p : cell.getParagraphs())
                                {
                                    for (XWPFRun r : p.getRuns()) {
                                        try {
                                            String text = r.getText(0);
                                            if (text != null && text.contains("(=") && text.contains("21")) {
                                                replace(r, user, staff, person, identityDoc);
                                                writetable(tbl);
                                            } else if (text != null && text.contains("(=")) {
                                                replace(r, user, staff, person, identityDoc);
//									text = text.replace("(=", "$");
//									r.setText(text,0);

                                            }
                                        }
                                        catch (Exception e){
                                            System.out.println(e);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;

                case 2:
                    filePath = UPLOADED_FOLDER+ "/"+"2. Договор бп (finish).docx";

                    file = new File(filePath);

                    fInput = new FileInputStream(file.getAbsolutePath());
                    doc = new XWPFDocument(fInput);

                    for (XWPFParagraph p : doc.getParagraphs())
                    {
                        List<XWPFRun> runs = p.getRuns();

                        if (runs != null)
                        {
                            for (XWPFRun r : runs)
                            {
                                String text = r.getText(0);
                                if (text != null && text.contains("(="))
                                {
                                    replace(r,user,staff,person,identityDoc);
                                }
                            }
                        }
                    }

                    for (XWPFTable tbl : doc.getTables())
                    {
                        for (XWPFTableRow row : tbl.getRows())
                        {
                            for (XWPFTableCell cell : row.getTableCells())
                            {
                                for (XWPFParagraph p : cell.getParagraphs())
                                {
                                    for (XWPFRun r : p.getRuns()) {
                                        String text = r.getText(0);
                                        if (text != null && text.contains("(=")) {
                                            replace(r, user, staff, person, identityDoc);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
            }

            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=filename.doc");

            doc.write(response.getOutputStream());

//                response.getOutputStream().flush();
//                response.getOutputStream().close();

            doc.close();

		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}





	}

	private void writetable(XWPFTable tbl){


//        for (XWPFTableRow row : tbl.getRows())
//        {
//            for (XWPFTableCell cell : row.getTableCells())
//            {
//                System.out.println(cell.getParagraphs());
//                for (XWPFParagraph p : cell.getParagraphs())
//                {
//                    for (XWPFRun r : p.getRuns()) {
//                        String text = r.getText(0);
//
//                    }
//                }
//            }
//        }
        XWPFTableRow row;
        XWPFTableCell cell;
        XWPFParagraph paragraph;
        XWPFRun run;

        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int smth=localDate.getYear()-1;
        int kvartal=0;
        for(int i=2;i<tbl.getRows().size()-1;i++){
            row=tbl.getRows().get(i);
            for(int j=1;j<row.getTableCells().size();j++){
                cell=row.getTableCells().get(j);
                if(j==1){
                    smth=smth+1;
                    cell.setText(String.valueOf(smth));
                }

            }
        }
        if((localDate.getMonthValue()<=1 && localDate.getDayOfMonth()<=15)||localDate.getMonthValue()<1){
            kvartal=1;
        }
        else if((localDate.getMonthValue()<=4 && localDate.getDayOfMonth()<=15)||localDate.getMonthValue()<4){
            kvartal=2;
        }
        else if((localDate.getMonthValue()<=7 && localDate.getDayOfMonth()<=15)||localDate.getMonthValue()<7){
            kvartal=3;
        }
        else{
            kvartal=4;
        }
        if(kvartal==1 ){
            for(int i=5;i<tbl.getRows().size()-3;i++){
                row=tbl.getRows().get(i);
                for(int j=1;j<row.getTableCells().size();j++){
                    cell=row.getTableCells().get(j);
                    cell.removeParagraph(0);
                    paragraph = cell.addParagraph();
                    paragraph.setAlignment(ParagraphAlignment.RIGHT);
                    run = paragraph.createRun();
                    run.setText("3 000,00");
                    cell.setParagraph(paragraph);
//                    cell.setText("3 000,00");
                    if(j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("12 000,00");
                        cell.setParagraph(paragraph);
                    }

                }
            }
            row=tbl.getRows().get(tbl.getRows().size()-2);
            cell=row.getTableCells().get(2);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);

            cell=row.getTableCells().get(3);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);

            cell=row.getTableCells().get(4);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);

            cell=row.getTableCells().get(5);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);

            cell=row.getTableCells().get(6);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("8 000,00");
            cell.setParagraph(paragraph);

        }
        else if(kvartal==2 ){
            for(int i=5;i<tbl.getRows().size()-2;i++){
                row=tbl.getRows().get(i);
                for(int j=2;j<row.getTableCells().size();j++){
                    cell=row.getTableCells().get(j);
                    if(i==5 && j==2){}
                    else if(i==5 && j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("9 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(i==tbl.getRows().size()-3 && j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("9 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("12 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(i==tbl.getRows().size()-3 && ((j==3)||(j==4)||(j==5))){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("2 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(j!=6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("3 000,00");
                        cell.setParagraph(paragraph);
                    }

                }
            }
            row=tbl.getRows().get(tbl.getRows().size()-2);
            cell=row.getTableCells().get(2);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);
//            cell.setText("2 000,00");
            cell=row.getTableCells().get(6);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);
//            cell.setText("2 000,00");

        }
        else if(kvartal==3 ){
            for(int i=5;i<tbl.getRows().size()-2;i++){
                row=tbl.getRows().get(i);
                for(int j=2;j<row.getTableCells().size();j++){
                    cell=row.getTableCells().get(j);
                    if((i==5 && j==2)||(i==5 && j==3)){}
                    else if(i==5 && j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("6 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(i==tbl.getRows().size()-3 && j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("10 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("12 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(i==tbl.getRows().size()-3 && ((j==4)||(j==5))){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("2 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(j!=6){
						cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("3 000,00");
                        cell.setParagraph(paragraph);
                    }

                }
            }
            row=tbl.getRows().get(tbl.getRows().size()-2);
            cell=row.getTableCells().get(2);
            cell.getCTTc();
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);
//            cell.setText("2 000,00");
            cell=row.getTableCells().get(3);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);

            cell=row.getTableCells().get(6);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("4 000,00");
            cell.setParagraph(paragraph);
        }
        else if(kvartal==4 ){
            for(int i=5;i<tbl.getRows().size()-2;i++){
                row=tbl.getRows().get(i);
                for(int j=2;j<row.getTableCells().size();j++){
                    cell=row.getTableCells().get(j);
                    if((i==5 && j==2)||(i==5 && j==3)||(i==5 && j==4)){}
                    else if(i==5 && j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("3 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(i==tbl.getRows().size()-2 && j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("11 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(j==6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("12 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(i==tbl.getRows().size()-2 && (j==5)){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("2 000,00");
                        cell.setParagraph(paragraph);
                    }
                    else if(j!=6){
                        cell.removeParagraph(0);
                        paragraph = cell.addParagraph();
                        paragraph.setAlignment(ParagraphAlignment.RIGHT);
                        run = paragraph.createRun();
                        run.setText("3 000,00");
                        cell.setParagraph(paragraph);
                    }

                }
            }
            row=tbl.getRows().get(tbl.getRows().size()-2);
            cell=row.getTableCells().get(2);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);

            cell=row.getTableCells().get(3);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);

            cell=row.getTableCells().get(4);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("2 000,00");
            cell.setParagraph(paragraph);

            cell=row.getTableCells().get(6);
            cell.removeParagraph(0);
            paragraph = cell.addParagraph();
            paragraph.setAlignment(ParagraphAlignment.RIGHT);
            run = paragraph.createRun();
            run.setText("6 000,00");
            cell.setParagraph(paragraph);

        }

        row=tbl.getRows().get(tbl.getRows().size()-1);
        cell=row.getTableCells().get(1);
        cell.removeParagraph(0);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("50 000,00");
        cell.setParagraph(paragraph);

        cell=row.getTableCells().get(2);
        cell.removeParagraph(0);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("50 000,00");
        cell.setParagraph(paragraph);

        cell=row.getTableCells().get(3);
        cell.removeParagraph(0);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("50 000,00");
        cell.setParagraph(paragraph);

        cell=row.getTableCells().get(4);
        cell.removeParagraph(0);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("50 000,00");
        cell.setParagraph(paragraph);

        cell=row.getTableCells().get(5);
        cell.removeParagraph(0);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("200 000,00");
        cell.setParagraph(paragraph);

    }
	private void replace(XWPFRun run,User user,Staff staff,Person person,IdentityDoc identityDoc)
	{
		String text = run.getText(0);
		try
		{
			int startPosition = 0;
			int closePosition = 0;

			if(text.contains("(="))
			{
				startPosition = text.lastIndexOf("(=")+2;
				closePosition = text.lastIndexOf("=)");

				try
				{
					String varIdString = text.substring(startPosition,closePosition);
					int varId = Integer.parseInt(varIdString);

					Date today = new Date();
                    LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					String newText = "";
					String oldText = "";
					if(varId>=7 && varId!=21){
                        run.getParagraph().setAlignment(ParagraphAlignment.CENTER);
                    }
                    String addressText="";
					Address address=new Address();
                    if(person.getAddress()!=null){
						 address= addressService.findById(person.getAddress().getId());
						if(address.getRegion().getName().contains("г.")){
//							addressText=address.getRegion().getName()+", ";
 						}
						else
							addressText=address.getRegion().getName()+" обл. ";
						if(address.getDistrict().getName().contains("г."))
							addressText=addressText+address.getDistrict().getName()+", ";
						else
							addressText=addressText+address.getDistrict().getName()+" р. ";
					}

					switch (varId)
					{
						case 1: System.out.println("1");

							int day = localDate.getDayOfMonth();
							newText = " "+String.valueOf(day)+" ";


							break;

						case 2:
							int month = localDate.getMonthValue();


							switch (month)
							{
								case 1: newText = "января"; break;
								case 2: newText = "февраля"; break;
								case 3: newText = "марта"; break;
								case 4: newText = "апреля"; break;
								case 5: newText = "мая"; break;
								case 6: newText = "июня"; break;
								case 7: newText = "июля"; break;
								case 8: newText = "августа"; break;
								case 9: newText = "сентября"; break;
								case 10: newText = "октября"; break;
								case 11: newText = "ноября"; break;
								case 12: newText = "декабря"; break;
							}


							break;
						case 3:
						    String[] splitted=staff.getName().split(" ");
						    String words="";
						    words=splitted[0]+"a "+splitted[1]+" "+splitted[2]+"a";
							newText=words;
							break;
						case 4:
							EmploymentHistory employmentHistory=employmentHistoryService.findById(staff.getEmploymentHistory().getId());
							newText=employmentHistory.getNumber();
							break;
						case 5:
							newText=person.getName();
							break;
						case 6:
							newText=identityDoc.getIdentityDocType().getName()+" "+identityDoc.getNumber();
							break;
						case 7:
							newText=staff.getDepartment().getName();
							break;
						case 8:
							newText=staff.getPerson().getAddress().getLine();
							break;
						case 9:
							newText=staff.getPosition().getName();
							break;
						case 10:
							String[] fullname=staff.getPerson().getName().split(" ");
//							String surname=fullname[0]+" "+fullname[1].charAt(0)+"."+fullname[2].charAt(0)+".";
							String surname="";
							for(int i=0;i<fullname.length;i++){
								if(i==0){
									surname=fullname[i];
								}
								else{
									surname=surname+" "+fullname[i].charAt(0)+". ";
								}
							}
							newText=surname;
							break;
						case 11:
							newText=person.getName();
							break;
						case 12:
							if(person.getAddress()!=null) {
								newText =addressText+ address.getLine();
							}
							else
								newText=addressText;
							break;
						case 13:
						    if(person.getAddress_line2()!=null&&person.getAddress_line2().length()>3) {
                                newText =addressText+" "+person.getAddress_line2();
                            }
						    else{
								newText=addressText+" "+person.getAddress().getLine();
                            }
							break;
						case 14:
							newText=person.getIdentityDoc().getPin();
							break;
						case 15:
                            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
							String docDate=" ";
							String givenBY=" ";
                            if(identityDoc.getDate()!=null)
                            	docDate=format.format(identityDoc.getDate());
                            if(identityDoc.getGivenBy()!=null)
                            	givenBY=identityDoc.getGivenBy();

							newText=identityDoc.getIdentityDocType().getName()+":"+identityDoc.getNumber()+", "+docDate+", "+givenBY;
							break;
						case 16:
							newText=staff.getPosition().getName();
							break;
						case 17:
							newText=person.getName();
							break;
                        case 20:
                            newText=String.valueOf(localDate.getYear());
                            break;


					}

					oldText = "(="+varIdString+"=)";

					if(text.contains(oldText))
					{
						text = text.replace(oldText,newText);
					}

					run.setText(text, 0);

					System.out.println(run.getText(0));

				}
				catch (Exception ex)
				{
					System.out.println(ex);
				}




			}

			if(text.contains("(="))
			{
				replace(run,user,staff,person,identityDoc);
			}


		}
		catch (Exception ex)
		{

		}

	}

}


