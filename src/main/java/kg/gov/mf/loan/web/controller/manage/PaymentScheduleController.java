package kg.gov.mf.loan.web.controller.manage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.repository.loan.PaymentScheduleRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.web.fetchModels.PaymentAuditModel;
import kg.gov.mf.loan.web.fetchModels.PaymentScheduleAuditModel;
import kg.gov.mf.loan.web.util.Pager;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.loan.InstallmentState;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.PaymentSchedule;
import kg.gov.mf.loan.manage.service.loan.InstallmentStateService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.PaymentScheduleService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PaymentScheduleController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	PaymentScheduleService paymentScheduleService;
	
	@Autowired
	InstallmentStateService installmentStateService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	PaymentScheduleRepository paymentScheduleRepository;

	@Autowired
	UserService userService;

	@Autowired
	StaffService staffService;

	@PersistenceContext
	private EntityManager entityManager;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/{psId}/view", method=RequestMethod.GET)
	public String view(ModelMap model,
								 @PathVariable("debtorId")Long debtorId,
								 @PathVariable("loanId")Long loanId,
								 @PathVariable("psId")Long psId){
		PaymentSchedule paymentSchedule=paymentScheduleService.getById(psId);

		model.addAttribute("schedule",paymentSchedule);

		String createdByStr=null;
		String modifiedByStr=null;

		if(paymentSchedule.getAuCreatedBy()!=null){
			if(paymentSchedule.getAuCreatedBy().equals("admin")){
				createdByStr="Система";
			}
			else{
				User createdByUser=userService.findByUsername(paymentSchedule.getAuCreatedBy());
				Staff createdByStaff=createdByUser.getStaff();
				createdByStr=createdByStaff.getName();
			}
		}
		if(paymentSchedule.getAuLastModifiedBy()!=null){
			if(paymentSchedule.getAuLastModifiedBy().equals("admin")){
				modifiedByStr="Система";
			}
			else{
				User lastModifiedByUser=userService.findByUsername(paymentSchedule.getAuLastModifiedBy());
				Staff lastModifiedByStaff=lastModifiedByUser.getStaff();
				modifiedByStr=lastModifiedByStaff.getName();
			}
		}

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();
        String jsonHistory= gson.toJson(historyOfPayment(psId));
        model.addAttribute("jsonHistory", jsonHistory);

		model.addAttribute("createdBy",createdByStr);
		model.addAttribute("modifiedBy",modifiedByStr);

		return "/manage/debtor/loan/paymentschedule/view";
	}
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/{psId}/save", method=RequestMethod.GET)
	public String formCreditTerm(ModelMap model, 
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId,
			@PathVariable("psId")Long psId)
	{
		
		if(psId == 0)
		{
			PaymentSchedule paymentSchedule= new PaymentSchedule();
			paymentSchedule.setExpectedDate(new Date());
			paymentSchedule.setCollectedInterestPayment(0.0);
			paymentSchedule.setCollectedPenaltyPayment(0.0);
			paymentSchedule.setDisbursement(0.0);
			paymentSchedule.setInterestPayment(0.0);
			paymentSchedule.setPrincipalPayment(0.0);
			paymentSchedule.setRecord_status(1);
			paymentSchedule.setInstallmentState(installmentStateService.getById(Long.valueOf(1)));
			model.addAttribute("paymentSchedule", paymentSchedule);
		}
			
		if(psId > 0)
		{
			model.addAttribute("paymentSchedule", paymentScheduleService.getById(psId));
		}
		
		List<InstallmentState> iStates = installmentStateService.list();
        model.addAttribute("iStates", iStates);
		
        model.addAttribute("debtorId", debtorId);
		model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
		model.addAttribute("loan", loanService.getById(loanId));

		return "/manage/debtor/loan/paymentschedule/save";
	}
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/save"}, method=RequestMethod.POST)
    public String savePaymentSchedule(PaymentSchedule paymentSchedule, 
    		@PathVariable("debtorId")Long debtorId, 
    		@PathVariable("loanId")Long loanId, 
    		ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		paymentSchedule.setLoan(loan);

		if(paymentSchedule.getExpectedDate()==null){
			paymentSchedule.setExpectedDate(new Date());
		}
		if(paymentSchedule.getDisbursement()==null){
			paymentSchedule.setDisbursement(0.0);
		}
		if(paymentSchedule.getPrincipalPayment()==null){
			paymentSchedule.setPrincipalPayment(0.0);
		}
		if(paymentSchedule.getInterestPayment()==null){
			paymentSchedule.setInterestPayment(0.0);
		}
		if(paymentSchedule.getCollectedInterestPayment()==null){
			paymentSchedule.setCollectedInterestPayment(0.0);
		}
		if(paymentSchedule.getCollectedPenaltyPayment()==null){
			paymentSchedule.setCollectedPenaltyPayment(0.0);
		}
		if(paymentSchedule.getId() == 0) {
			//paymentScheduleService.add(paymentSchedule);
			paymentScheduleRepository.save(paymentSchedule);
		}
		else {
			paymentScheduleRepository.save(paymentSchedule);
			//paymentScheduleService.update(paymentSchedule);
		}

		if(paymentSchedule.getExpectedDate().after(loan.getLastDate())) {
			/*ScheduledExecutorService someScheduler = Executors.newScheduledThreadPool(1);
			Runnable runnable = new Runnable() {
				public void run() {
					getTheLastScheduleDate(loanId);
				}
			};
			long timeDelay = 10; // You can specify 1 what
			someScheduler.schedule(runnable, timeDelay, TimeUnit.SECONDS);*/

			getTheLastScheduleDate(loan);
		}

		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view#paymentSchedules";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/{psId}/delete", method=RequestMethod.POST)
    public String deletePaymentSchedule(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("psId")Long psId) {
		if(psId > 0)
            paymentScheduleRepository.delete(paymentScheduleRepository.findOne(psId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value = { "/manage/debtor/loan/paymentschedule/installmentstate/list" }, method = RequestMethod.GET)
    public String listAppliedEntityStates(ModelMap model) {
 
		List<InstallmentState> states = installmentStateService.list();
		model.addAttribute("states", states);
        
        model.addAttribute("loggedinuser", Utils.getPrincipal());
        return "/manage/debtor/loan/paymentschedule/installmentstate/list";
    }
	
	@RequestMapping(value="/manage/debtor/loan/paymentschedule/installmentstate/{stateId}/save", method=RequestMethod.GET)
	public String formEState(ModelMap model, @PathVariable("stateId")Long stateId) {
		if(stateId == 0)
		{
			model.addAttribute("state", new InstallmentState());
		}
		
		if(stateId > 0)
		{
			model.addAttribute("state", installmentStateService.getById(stateId));
		}
		return "/manage/debtor/loan/paymentschedule/installmentstate/save";
	}
	
	@RequestMapping(value="/manage/debtor/loan/paymentschedule/installmentstate/save", method=RequestMethod.POST)
    public String saveInstallmentState(InstallmentState state, ModelMap model) {
		if(state.getId() == 0)
			installmentStateService.add(state);
		else
			installmentStateService.update(state);
		
		model.addAttribute("loggedinuser", Utils.getPrincipal());
		return "redirect:" + "/manage/debtor/loan/paymentschedule/installmentstate/list";
    }
	
	@RequestMapping(value="/manage/debtor/loan/paymentschedule/installmentstate/delete", method=RequestMethod.POST)
    public String deleteInstallmentState(long id) {
		if(id > 0)
			installmentStateService.remove(installmentStateService.getById(id));
		return "redirect:" + "/manage/debtor/loan/paymentschedule/installmentstate/list";
    }

//	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/upload", method=RequestMethod.GET)
//	public String uploadFileForm(ModelMap model,@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId){
//
//		model.addAttribute("debtor",debtorService.getById(debtorId));
//		model.addAttribute("loan",loanService.getById(loanId));
//		model.addAttribute("debtorId",debtorId);
//		model.addAttribute("loanId",loanId);
//
//		return "/manage/debtor/loan/paymentschedule/uploadFile";
//	}
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/paymentschedule/uploadFile", method=RequestMethod.POST)
	public String processUploadedFile(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, MultipartHttpServletRequest request){

		Loan loan = this.loanService.getById(loanId);

		boolean updateLastDate=false;
		//String seleteQuery="select p.id,p.record_status,p.version,p.uuid,p.collectedInterestPayment,p.collectedPenaltyPayment,p.disbursement,p.expectedDate,p.interestPayment,p.principalPayment,p.installmentStateId,p.loanId  from paymentSchedule p where p.loanId="+String.valueOf(loanId);
		//Query deleteQuery=  entityManager.createNativeQuery(seleteQuery,PaymentSchedule.class);
		//List<PaymentSchedule> paymentSchedules=deleteQuery.getResultList();

		List<PaymentSchedule> paymentSchedules = entityManager
				.createQuery("select p from PaymentSchedule p where p.loan.id = " + loanId, PaymentSchedule.class)
				.getResultList();

//		Set<PaymentSchedule> paymentSchedules=loan.getPaymentSchedules();

		for (PaymentSchedule paymentSchedule1:paymentSchedules){
			PaymentSchedule paymentSchedule=this.paymentScheduleService.getById(paymentSchedule1.getId());
			this.paymentScheduleRepository.delete(paymentSchedule);
		}
		try {
			Iterator<String> itr = request.getFileNames();

			while (itr.hasNext()) {
				String uploadedFile = itr.next();
				MultipartFile part = request.getFile(uploadedFile);

				HSSFWorkbook     WorkBookMain     = new HSSFWorkbook(part.getInputStream());
				HSSFSheet SheetMain        = null;
				HSSFRow RowMain          = null;
				HSSFCell CellDate         = null;
				HSSFCell         CellPayment      = null;
				HSSFCell         CellProfit       = null;
				HSSFCell         CellPercent         = null;
				HSSFCell         CellCPercent         = null;
				HSSFCell         CellCPenalty         = null;


				SheetMain =  WorkBookMain.getSheetAt(0);

				for(int yDet=2;yDet<SheetMain.getLastRowNum()+1;yDet++)
				{
					RowMain      = SheetMain.getRow(yDet);
					CellDate     = RowMain.getCell((short)1);
					CellProfit   = RowMain.getCell((short)2);
					CellPayment  = RowMain.getCell((short)3);
					CellPercent  = RowMain.getCell((short)4);
					CellCPercent = RowMain.getCell((short)5);
					CellCPenalty = RowMain.getCell((short)6);

					if(CellDate != null)
					{
						if(CellDate.getDateCellValue()!=null )
						{
							Date date = new Date(CellDate.getDateCellValue().getTime());
							PaymentSchedule paymentSchedule=new PaymentSchedule();
							paymentSchedule.setExpectedDate(date);
							paymentSchedule.setLoan(loanService.getById(loanId));
							paymentSchedule.setDisbursement(CellProfit.getNumericCellValue());
							paymentSchedule.setPrincipalPayment(CellPayment.getNumericCellValue());
							paymentSchedule.setInterestPayment(CellPercent.getNumericCellValue());
							paymentSchedule.setCollectedInterestPayment(CellCPercent.getNumericCellValue());
							paymentSchedule.setCollectedPenaltyPayment(CellCPenalty.getNumericCellValue());
							paymentSchedule.setInstallmentState(installmentStateService.getById(Long.valueOf(1)));
							paymentScheduleRepository.save(paymentSchedule);
							if(!updateLastDate && paymentSchedule.getExpectedDate().after(loan.getLastDate())){
								updateLastDate=true;
							}

						}
					}
				}
			}
		}
		catch (Exception e) {

		}
		if(updateLastDate) {
			/*ScheduledExecutorService someScheduler = Executors.newScheduledThreadPool(1);
			Runnable runnable = new Runnable() {
				public void run() {
					getTheLastScheduleDate(loan.getId());
				}
			};
			long timeDelay = 10; // You can specify 1 what
			someScheduler.schedule(runnable, timeDelay, TimeUnit.SECONDS);*/
			getTheLastScheduleDate(loan);
		}
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
	}

	public void getTheLastScheduleDate(Loan loan){
		String getMaxDate="select max(p.expectedDate)\n" +
				"from paymentSchedule p where loanId="+loan.getId();
		Query query=entityManager.createNativeQuery(getMaxDate);
		Date maxDate= (Date) query.getSingleResult();
		if(maxDate.after(loan.getLastDate())){
			loan.setLastDate(maxDate);
			loanService.update(loan);
		}
	}

	//    get audited history of paymentSchedule
	private List<PaymentScheduleAuditModel> historyOfPayment(Long sheduleId){

		String getPaymentSchedules="select p.*,p.REVTYPE,iSt.name as installmentStateName,s.name as staffName,uR.timestamp as date\n" +
                "from paymentSchedule_AUD p,installmentState iSt,user_revisions uR,users u,staff s " +
                "where p.installmentStateId=iSt.id and u.username=uR.username and u.staff_id=s.id and uR.id=p.REV and p.id="+sheduleId;
		Query query=entityManager.createNativeQuery(getPaymentSchedules,PaymentScheduleAuditModel.class);

		return query.getResultList();
	}
}
