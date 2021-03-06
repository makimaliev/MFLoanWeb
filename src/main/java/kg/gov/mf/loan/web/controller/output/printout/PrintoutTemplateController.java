package kg.gov.mf.loan.web.controller.output.printout;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lowagie.text.Document;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.rtf.RtfWriter2;
import kg.gov.mf.loan.admin.org.model.*;
import kg.gov.mf.loan.admin.org.service.*;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.LoanSummaryAct;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.LoanSummaryActService;
import kg.gov.mf.loan.output.printout.model.Printout;
import kg.gov.mf.loan.output.printout.model.PrintoutTemplate;
import kg.gov.mf.loan.output.printout.service.PrintoutService;
import kg.gov.mf.loan.output.printout.service.PrintoutTemplateService;
import kg.gov.mf.loan.output.printout.utils.*;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import kg.gov.mf.loan.web.fetchModels.PaymentScheduleModel;
import kg.gov.mf.loan.web.util.Utils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
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

	@Autowired
	LoanService loanService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	OwnerService ownerService;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	ContactService contactService;

	@PersistenceContext
	private EntityManager entityManager;



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


	@RequestMapping("/printoutType/5/objectId/{object_id}/generate")
	public void printoutCollateralDamaged(@PathVariable("object_id") long object_id,
								  HttpServletResponse response) {
		try {

			PrintoutGeneratorCollateralDocDamaged printoutGeneratorCollateralDocDamaged = new PrintoutGeneratorCollateralDocDamaged();

			Document document = new Document(PageSize.A4, 35, 35, 10, 10);

			RtfWriter2 rtfWriter2 = RtfWriter2.getInstance(document,response.getOutputStream());

			response.setContentType("application/rtf");
			response.setHeader("Content-disposition","attachment; filename=xx.rtf");

			String username= Utils.getPrincipal();

			printoutGeneratorCollateralDocDamaged.generatePrintout(object_id,document,username);




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

			response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
			response.setHeader("Content-Disposition", "attachment; filename=filename.docx");

			doc.write(response.getOutputStream());

//                response.getOutputStream().flush();
//                response.getOutputStream().close();

			doc.close();

		}
		catch (Exception ex)
		{

		}





	}

	@RequestMapping("/printout/{type_id}/loanId/{loan_id}/generate")
	public void generateDocByTypeAndLoanId(@PathVariable("loan_id") long loan_id,
											 @PathVariable("type_id") long type_id,
								  HttpServletResponse response) {
		try
		{

            User user=userService.findById(userService.findByUsername(Utils.getPrincipal()).getId());

            Staff staff=staffService.findById(user.getStaff().getId());

            Person staffPerson = personService.findById(staff.getPerson().getId());

            Contact staffContact = contactService.findById(staffPerson.getContact().getId());

            Loan loan = loanService.getById(Long.valueOf(loan_id));

			Debtor debtor = debtorService.getById(loan.getDebtor().getId());

			Owner owner = ownerService.getById(debtor.getOwner().getId());

			Address address = addressService.findById(owner.getAddress().getId());


            Person person=personService.findById(Long.valueOf(loan_id));

            if(owner.getOwnerType().name().equals(("ORGANIZATION")))
			{
				Organization organization = organizationService.findById(owner.getEntityId());

				for (Department department1 : organization.getDepartment())
				{
					Department department = departmentService.findById(department1.getId());

					for (Staff staff1 : staffService.findAllByDepartment(department))
					{
						Staff staff2 = staffService.findById(staff1.getId());

						person = personService.findById(staff2.getPerson().getId());

						break;
					}
					break;
				}



			}
			else person = null;

            IdentityDoc identityDoc= new IdentityDoc();

            XWPFDocument doc=new XWPFDocument();

		    switch ((int) type_id){

                case 1:

                    String filePath = UPLOADED_FOLDER+ "/"+"билдируу 2019.docx";

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
                                    replace2(r,user,staff,address,debtor, loan, person,identityDoc, staffContact );
                                }
                            }
                        }
                    }
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            response.setHeader("Content-Disposition", "attachment; filename=filename.docx");

            doc.write(response.getOutputStream());

            doc.close();

		}
		catch (Exception ex)
		{

		}

	}


	private void replace2(XWPFRun run,User user,Staff staff,Address address, Debtor debtor, Loan loan, Person person, IdentityDoc identityDoc, Contact contact)
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

					String newText = "";
					String oldText = "";

					switch (varId)
					{
							// region name
						case 30:

							run.getParagraph().setAlignment(ParagraphAlignment.LEFT);
							if(address!=null){

								if(address.getRegion().getName().contains("г."))
								{
									newText=address.getRegion().getName().replace("г.", "");
									newText=newText.concat(" ш.");
								}
								else
								{
									newText=address.getRegion().getName().replace("ская","");
									newText=newText.concat(" облусу");
								}
							}
							break;


						// district name
						case 31:
							run.getParagraph().setAlignment(ParagraphAlignment.LEFT);
							if(address!=null){

								if(address.getDistrict().getName().contains("г."))
								{
									newText=address.getDistrict().getName().replace("г.", "");
									newText=newText.concat(" ш.");
								}
								else
								{
									newText=address.getDistrict().getName().replace("ский","");
									newText=newText.concat(" району");
								}
							}
							break;

							// address line
						case 32:
							run.getParagraph().setAlignment(ParagraphAlignment.LEFT);
							if(address!=null){

								if(address.getLine()!=null)
								{
									newText=address.getLine();
								}
								else
								{
									newText="";
								}
							}
							break;

							// debtor name
						case 33:
							run.getParagraph().setAlignment(ParagraphAlignment.LEFT);
							if(debtor!=null){

								if(debtor.getName()!=null)
								{
									newText=debtor.getName();
								}
								else
								{
									newText="";
								}
							}
							break;

							// debtor chief
						case 34:
							run.getParagraph().setAlignment(ParagraphAlignment.LEFT);
							if(person!=null){

								if(person.getName()!=null)
								{
									newText=person.getName();
								}
								else
								{
									newText="";
								}
							}
							else
							{
								newText = "";
							}
							break;

							// date of loan
						case 35:
							if(loan!=null){

								newText=DateToString(loan.getRegDate())+"ж.";

								break;
							}

							// number of loan
						case 36:
							if(loan!=null){
								newText=loan.getRegNumber();
								break;
							}


							// amount of loan
						case 37:
							if(loan!=null){
								newText=FormatNumber(loan.getAmount());
								break;
							}

							// last date of loan
						case 38:
							if(loan!=null)
							{
								if(loan.getLastDate()!=null)
								{
									newText=DateToString(loan.getLastDate())+"ж. на";
								}
								else
								{
									newText="";
								}

								break;
							}

							// name of staff
						case 39:
							if(staff!=null)
							{
								if(staff.getName()!=null)
								{

									String[] splitted=staff.getName().split(" ");
									String words="";

									if(splitted.length==3)
									{
										words=splitted[1].substring(0,1)+". "+splitted[0];
									}
									else if (splitted.length==2)
									{
										words=splitted[1].substring(0,1)+". "+splitted[0];
									}
									else
									{
										words=staff.getName();
									}

									newText=words;

								}

								break;
							}

						case 40:
							if(contact!=null)
							{
								if(contact.getName()!=null)
								{
									newText=contact.getName();
								}
								else
								{
									newText="";
								}

								break;
							}

					}

					oldText = "(="+varIdString+"=)";

					if(text.contains(oldText))
					{
						text = text.replace(oldText,newText);
					}

					run.setText(text, 0);
				}
				catch (Exception ex)
				{

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

	public String DateToString(Date date)
	{

		SimpleDateFormat DateFormatShort = new SimpleDateFormat("dd.MM.yyyy");

		if(date == null)
			return "";
		else
			return DateFormatShort.format(date);
	}

	public String FormatNumber(double number)
	{
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
		symbols.setDecimalSeparator(',');
		symbols.setGroupingSeparator(' ');

		String pattern = ",##0.00";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);

		return decimalFormat.format(number);
	}

	private void writetable(XWPFTable tbl){


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
                for(int j=2;j<row.getTableCells().size();j++){
                    cell=row.getTableCells().get(j);
                    if(j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("12 000,00",0);
                    }

                    else{
						cell.getParagraphs().get(0).getRuns().get(0).setText("3 000,00",0);
					}

                }
            }
            row=tbl.getRows().get(tbl.getRows().size()-3);
            cell=row.getTableCells().get(2);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);

            cell=row.getTableCells().get(3);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);

            cell=row.getTableCells().get(4);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);

            cell=row.getTableCells().get(5);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);

            cell=row.getTableCells().get(6);
			cell.getParagraphs().get(0).getRuns().get(0).setText("8 000,00",0);

        }
        else if(kvartal==2 ){
            for(int i=5;i<tbl.getRows().size()-2;i++){
                row=tbl.getRows().get(i);
                for(int j=2;j<row.getTableCells().size();j++){
                    cell=row.getTableCells().get(j);
                    if(i==5 && j==2){}
                    else if(i==5 && j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("9 000,00",0);
                    }
                    else if(i==tbl.getRows().size()-3 && j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("9 000,00",0);
                    }
                    else if(j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("12 000,00",0);
                    }
                    else if(i==tbl.getRows().size()-3 && ((j==3)||(j==4)||(j==5))){
						cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);
                    }
                    else if(j!=6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("3 000,00",0);
                    }

                }
            }
            row=tbl.getRows().get(tbl.getRows().size()-2);
            cell=row.getTableCells().get(2);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);
//            cell.setText("2 000,00");
            cell=row.getTableCells().get(6);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);
//            cell.setText("2 000,00");

        }
        else if(kvartal==3 ){
            for(int i=5;i<tbl.getRows().size()-2;i++){
                row=tbl.getRows().get(i);
                for(int j=2;j<row.getTableCells().size();j++){
                    cell=row.getTableCells().get(j);
                    if((i==5 && j==2)||(i==5 && j==3)){}
                    else if(i==5 && j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("6 000,00",0);
                    }
                    else if(i==tbl.getRows().size()-3 && j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("10 000,00",0);
                    }
                    else if(j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("12 000,00",0);
                    }
                    else if(i==tbl.getRows().size()-3 && ((j==4)||(j==5))){
						cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);
                    }
                    else if(j!=6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("3 000,00",0);
                    }

                }
            }
            row=tbl.getRows().get(tbl.getRows().size()-2);
			cell=row.getTableCells().get(2);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);
//            cell.setText("2 000,00");
            cell=row.getTableCells().get(3);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);

            cell=row.getTableCells().get(6);
			cell.getParagraphs().get(0).getRuns().get(0).setText("4 000,00",0);
        }
        else if(kvartal==4 ){
            for(int i=5;i<tbl.getRows().size()-2;i++){
                row=tbl.getRows().get(i);
                for(int j=2;j<row.getTableCells().size();j++){
                    cell=row.getTableCells().get(j);
                    if((i==5 && j==2)||(i==5 && j==3)||(i==5 && j==4)){}
                    else if(i==5 && j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("3 000,00",0);
                    }
                    else if(i==tbl.getRows().size()-3 && j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("11 000,00",0);
                    }
                    else if(j==6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("12 000,00",0);
                    }
                    else if(i==tbl.getRows().size()-2 && (j==5)){
						cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);
                    }
					else if(i==tbl.getRows().size()-3 && (j==5)){
						cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);
					}
                    else if(j!=6){
						cell.getParagraphs().get(0).getRuns().get(0).setText("3 000,00",0);
                    }

                }
            }
            row=tbl.getRows().get(tbl.getRows().size()-2);
            cell=row.getTableCells().get(2);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);

            cell=row.getTableCells().get(3);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);

            cell=row.getTableCells().get(4);
			cell.getParagraphs().get(0).getRuns().get(0).setText("2 000,00",0);

            cell=row.getTableCells().get(6);
			cell.getParagraphs().get(0).getRuns().get(0).setText("6 000,00",0);

        }

        row=tbl.getRows().get(tbl.getRows().size()-1);
        cell=row.getTableCells().get(1);
		cell.getParagraphs().get(0).getRuns().get(0).setText("50 000,00",0);

        cell=row.getTableCells().get(2);
		cell.getParagraphs().get(0).getRuns().get(0).setText("50 000,00",0);

        cell=row.getTableCells().get(3);
		cell.getParagraphs().get(0).getRuns().get(0).setText("50 000,00",0);

        cell=row.getTableCells().get(4);
		cell.getParagraphs().get(0).getRuns().get(0).setText("50 000,00",0);

        cell=row.getTableCells().get(5);
		cell.getParagraphs().get(0).getRuns().get(0).setText("200 000,00",0);

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

					String passportInfo="";
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
					String docDate=" ";
					String givenBY=" ";
					if(identityDoc.getDate()!=null)
						docDate=format.format(identityDoc.getDate());
					if(identityDoc.getGivenBy()!=null)
						givenBY=identityDoc.getGivenBy();

					passportInfo=identityDoc.getIdentityDocType().getName()+":"+identityDoc.getNumber()+",выдан  "+givenBY+" от "+docDate+" г.";

					switch (varId)
					{
						case 1:

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
						    if(splitted.length==3){
								words=splitted[0]+"a "+splitted[1]+" "+splitted[2]+"a";
							}
							else if (splitted.length==2){
								words=splitted[0]+"a "+splitted[1];
							}
							else{
								words=staff.getName();
							}
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
							newText=passportInfo;
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
                                newText ="Адрес проживания: "+addressText+" "+person.getAddress_line2();
                            }
						    else{
								newText="";
                            }
							break;
						case 14:
							newText=person.getIdentityDoc().getPin();
							break;
						case 15:
							newText=passportInfo;
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
                        case 22:
                            if(person.getContact()!=null){
                                newText=person.getContact().getName();
                                break;
                            }


                            // region name
						case 30:

							run.getParagraph().setAlignment(ParagraphAlignment.LEFT);
							if(person.getAddress()!=null){

								if(address.getRegion().getName().contains("г."))
								{
									newText=address.getRegion().getName().replace("г.", "");
									newText=newText.concat(" ш.");
								}
								else
								{
									newText=address.getRegion().getName().replace("ская","");
									newText=newText.concat(" облусу");
								}
							}
							break;


						// district name
						case 31:
							if(person.getContact()!=null)
							{
								newText=person.getContact().getName();
								break;
							}

							// address line
						case 32:
							if(person.getContact()!=null){
								newText=person.getContact().getName();
								break;
							}

							// debtor name
						case 33:
							if(person.getContact()!=null){
								newText=person.getContact().getName();
								break;
							}

							// debtor chief
						case 34:
							if(person.getContact()!=null){
								newText=person.getContact().getName();
								break;
							}

							// date of loan
						case 35:
							if(person.getContact()!=null){
								newText=person.getContact().getName();
								break;
							}

							// number of loan
						case 36:
							if(person.getContact()!=null){
								newText=person.getContact().getName();
								break;
							}


							// amount of loan
						case 37:
							if(person.getContact()!=null){
								newText=person.getContact().getName();
								break;
							}

							// last fate of loan
						case 38:
							if(person.getContact()!=null){
								newText=person.getContact().getName();
								break;
							}

							// name of staff
						case 39:
							if(person.getContact()!=null){
								newText=person.getContact().getName();
								break;
							}













					}

					oldText = "(="+varIdString+"=)";

					if(text.contains(oldText))
					{
						text = text.replace(oldText,newText);
					}

					run.setText(text, 0);



				}
				catch (Exception ex)
				{

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

	//generate pdf of payment schedules

	@PostMapping("/orderterm/printout/paymentschedules/pdf")
	public void generatePdfOfPaymentSchedules(
											  HttpServletResponse response, String paymentSchedules){


		try{
			Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();

			Type type = new TypeToken<List<PaymentScheduleModel>>(){}.getType();
			List<PaymentScheduleModel> result = gson.fromJson(paymentSchedules, type);

			Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

			PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment; filename=xx.pdf");



			generatePdf(null,result,document,null);
		}
		catch (Exception e){

		}

	}

	@PostMapping("/manage/debtor/{debtorId}/loan/{loanId}/paymentschedules/pdf")
	public void generatePdfOfPaymentSchedulesForLoan(
			@PathVariable Long debtorId, @PathVariable Long loanId,
			HttpServletResponse response, String paymentSchedules){


		try{
			Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();

			Type type = new TypeToken<List<PaymentScheduleModel>>(){}.getType();
			List<PaymentScheduleModel> result = gson.fromJson(paymentSchedules, type);

			Document document = new Document(PageSize.A4.rotate(), 25, 25, 10, 10);

			PdfWriter pdfWriter = PdfWriter.getInstance(document,response.getOutputStream());

			response.setContentType("application/pdf");
			response.setHeader("Content-disposition","attachment; filename=xx.pdf");



			generatePdf(null,result,document, loanId);
		}
		catch (Exception e){

		}

	}

	//generate excel of payment schedules
	@PostMapping("/orderterm/printout/paymentschedules/excel")
	public void generateExcelOfPaymentSchedules(
											  HttpServletResponse response, String paymentSchedules){


		Gson gson = new GsonBuilder().setDateFormat("yyyy.MM.dd").create();

		Type type = new TypeToken<List<PaymentScheduleModel>>(){}.getType();
		List<PaymentScheduleModel> result = gson.fromJson(paymentSchedules, type);

		ReportTool reportTool = new ReportTool();

		try{

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition","attachment; filename=report.xls");
			String[] columns = {"№", "Дата", "Освоение","Осн.сумма","Проценты","Нак.проценты", "Нак.штр."};

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("График");



			int rownum = 0;
			Cell cell;
//			Row row;
			//
			HSSFCellStyle style = createStyleForTitle(workbook);

			HSSFRow rowhead = sheet.createRow((short) 0);

			for (int i=0; i<7;i++){
				cell = rowhead.createCell(i, CellType.STRING);
				cell.setCellValue(columns[i]);
				cell.setCellStyle(style);
			}

			// Data

			CellStyle dateCellStyle = workbook.createCellStyle();
			CreationHelper createHelper = workbook.getCreationHelper();
			dateCellStyle.setDataFormat(
					createHelper.createDataFormat().getFormat("yyyy.MM.dd"));

			CellStyle doubleCellStyle = workbook.createCellStyle();
			doubleCellStyle.setDataFormat(
					workbook.getCreationHelper().createDataFormat().getFormat("###,##0.##"));

			for (PaymentScheduleModel paymentSchedule: result) {
				rownum++;
				HSSFRow row = sheet.createRow(rownum);

				cell = row.createCell(0, CellType.NUMERIC);
				cell.setCellValue(rownum);

				cell = row.createCell(1, CellType.NUMERIC);
				cell.setCellValue(paymentSchedule.getExpectedDate());
				cell.setCellStyle(dateCellStyle);

				cell = row.createCell(2, CellType.NUMERIC);
				cell.setCellValue((paymentSchedule.getDisbursement()));
				cell.setCellStyle(doubleCellStyle);

				cell = row.createCell(3, CellType.NUMERIC);
				cell.setCellValue((paymentSchedule.getPrincipalPayment()));
				cell.setCellStyle(doubleCellStyle);

				cell = row.createCell(4, CellType.NUMERIC);
				cell.setCellValue((paymentSchedule.getInterestPayment()));
				cell.setCellStyle(doubleCellStyle);

				cell = row.createCell(5, CellType.NUMERIC);
				cell.setCellValue((paymentSchedule.getCollectedInterestPayment()));
				cell.setCellStyle(doubleCellStyle);

				cell = row.createCell(6, CellType.NUMERIC);
				cell.setCellValue((paymentSchedule.getCollectedPenaltyPayment()));
				cell.setCellStyle(doubleCellStyle);

			}
			for(int i = 0; i < columns.length; i++) {
				sheet.autoSizeColumn(i);
			}

			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.close();

		}
		catch (Exception e){

		}

	}

	private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}

	public void generatePdf(PrintoutTemplate template, List<PaymentScheduleModel> paymentSchedules, Document document, Long loanId){

		try{
			ReportTool reportTool = new ReportTool();


			//*********** Document ********************************************************************************
			//*******************************************************************************************

			PdfPTable table = null;
			PdfPTable table2 = null;
			PdfPCell cell  = null;

			float TitleColumnPadding  = 3;
			int   TitleColumnBorder   = 0;
			float FooterColumnPadding = 3;
			int   FooterColumnBorder  = 0;

			BaseFont myfont        = BaseFont.createFont("//timesNewRoman.ttf",BaseFont.IDENTITY_H, BaseFont.EMBEDDED);


			Font HeaderFont   = new  Font(myfont,7,Font.NORMAL, CMYKColor.BLACK);
			Font      ColumnFont   = new  Font(myfont,7,Font.NORMAL,CMYKColor.BLACK);
			Font      TitleFont1    = new  Font(myfont,16,Font.BOLD,CMYKColor.RED);
			Font      TitleFont    = new  Font(myfont,11,Font.BOLD,CMYKColor.BLACK);
			Font      SubTitleFont = new  Font(myfont,9,Font.BOLD,CMYKColor.BLACK);

			CMYKColor HeaderColor = new CMYKColor(2,0,0,22);
			CMYKColor ColumnColor = new CMYKColor (0,0,0,0);
			CMYKColor FooterColor = new CMYKColor (2,0,0,22);

			document.open();


//            if (paymentSchedules.size()>0 ){


			//***********Title********************************************************************************
			//*******************************************************************************************
			table=new PdfPTable(1);
			table.setWidthPercentage(80);

			cell = new PdfPCell (new Paragraph("График",TitleFont1));
			cell.setFixedHeight(40);
			cell.setHorizontalAlignment (Element.ALIGN_LEFT);
			cell.setVerticalAlignment(Element.ALIGN_TOP);
			cell.setPadding(TitleColumnPadding);
			cell.setBorder(TitleColumnBorder);
			table.addCell(cell);

			if(loanId != null){
				Loan loan = loanService.getById(loanId);
				Debtor debtor = debtorService.getById(loan.getDebtor().getId());


				cell = new PdfPCell (new Paragraph("ЗАЕМЩИК : "+ debtor.getName(),TitleFont));
				cell.setFixedHeight(20);
				cell.setHorizontalAlignment (Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPadding(TitleColumnPadding);
				cell.setBorder(TitleColumnBorder);
				table.addCell(cell);

				cell = new PdfPCell (new Paragraph("КРЕДИТ : "+ loan.getRegNumber(),TitleFont));
				cell.setFixedHeight(20);
				cell.setHorizontalAlignment (Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPadding(TitleColumnPadding);
				cell.setBorder(TitleColumnBorder);
				table.addCell(cell);

				cell = new PdfPCell (new Paragraph("ОСНОВАНИЕ ВЫДАЧИ КРЕДИТА : "+ loan.getCreditOrder().getRegNumber(),TitleFont));
				cell.setFixedHeight(20);
				cell.setHorizontalAlignment (Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_TOP);
				cell.setPadding(TitleColumnPadding);
				cell.setBorder(TitleColumnBorder);
				table.addCell(cell);
			}


			document.add(table);

			//************ Header *******************************************************************
			//*******************************************************************************
			table2=new PdfPTable(7);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);

			int iColWindth[] = new int[7];

			for(int i=0;i<7;i++)
				iColWindth[i] = 6;

			iColWindth[0] = 3;

			table2.setWidths(iColWindth);


			cell = new PdfPCell(new Paragraph("№",HeaderFont));
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor (HeaderColor);
			table2.addCell (cell);

			cell = new PdfPCell(new Paragraph("Дата",HeaderFont));
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor (HeaderColor);
			table2.addCell (cell);

			cell = new PdfPCell(new Paragraph("Освоение",HeaderFont));
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor (HeaderColor);
			table2.addCell (cell);

			cell = new PdfPCell(new Paragraph("Осн.сумма",HeaderFont));
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor (HeaderColor);
			table2.addCell (cell);

			cell = new PdfPCell(new Paragraph("Проценты",HeaderFont));
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor (HeaderColor);
			table2.addCell (cell);

			cell = new PdfPCell(new Paragraph("Нак.проценты",HeaderFont));
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor (HeaderColor);
			table2.addCell (cell);

			cell = new PdfPCell(new Paragraph("Нак.штр.",HeaderFont));
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			cell.setBackgroundColor (HeaderColor);
			table2.addCell (cell);


			int counter=1;
			for(PaymentScheduleModel paymentSchedule : paymentSchedules)
			{
				cell = new PdfPCell(new Paragraph(String.valueOf(counter++),ColumnFont));
				cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor (ColumnColor);
				table2.addCell (cell);

				cell = new PdfPCell(new Paragraph(reportTool.DateToString(paymentSchedule.getExpectedDate()),ColumnFont));
				cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor (ColumnColor);
				table2.addCell (cell);

				cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentSchedule.getDisbursement()),ColumnFont));
				cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor (ColumnColor);
				cell.setNoWrap(true);
				table2.addCell (cell);

				cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentSchedule.getPrincipalPayment()),ColumnFont));
				cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor (ColumnColor);
				table2.addCell (cell);

				cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentSchedule.getInterestPayment()),ColumnFont));
				cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor (ColumnColor);
				table2.addCell (cell);

				cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentSchedule.getCollectedInterestPayment()),ColumnFont));
				cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor (ColumnColor);
				table2.addCell (cell);

				cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentSchedule.getCollectedPenaltyPayment()),ColumnFont));
				cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setBackgroundColor (ColumnColor);
				table2.addCell (cell);



			}





			//************************************************************************
			//************************************************************************
//                cell = new PdfPCell(new Paragraph("Итого",ColumnFont));
//                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setBackgroundColor (FooterColor);
//                cell.setColspan(2);
//                table2.addCell (cell);


//                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentSchedules.getTotalDisbursement()),ColumnFont));
//                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setBackgroundColor(FooterColor);
//                table2.addCell (cell);

//                cell = new PdfPCell(new Paragraph(" ",ColumnFont));
//                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setBackgroundColor(FooterColor);
//                table2.addCell (cell);

//                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(paymentSchedules.getTotalPrincipalPaid()),ColumnFont));
//                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setBackgroundColor (FooterColor);
//                table2.addCell (cell);

//                cell = new PdfPCell(new Paragraph(" ",ColumnFont));
//                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setBackgroundColor(FooterColor);
//                cell.setColspan(3);
//                table2.addCell (cell);

//                cell = new PdfPCell(new Paragraph(reportTool.FormatNumber(sumOfDaysInPeriod),ColumnFont));
//                cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
//                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                cell.setBackgroundColor (FooterColor);
//                table2.addCell (cell);

			document.add(table2);



                /*cell = new PdfPCell(new Paragraph(" ",TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);

                table=new PdfPTable(1);
                table.setWidthPercentage(100);
                cell = new PdfPCell(new Paragraph("Всего на: "+reportTool.DateToString(loanDetailedSummary.getOnDate()),TitleFont));
                cell.setHorizontalAlignment (Element.ALIGN_LEFT);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(TitleColumnPadding);
                cell.setBorder(TitleColumnBorder);
                table.addCell (cell);*/
//
//                document.add(table);

//            }

			document.close();



		}
		catch (Exception e){

		}
	}



}


