package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.SupervisorPlan;
import kg.gov.mf.loan.manage.repository.loan.SupervisorPlanRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import kg.gov.mf.loan.manage.service.loan.SupervisorPlanService;
import kg.gov.mf.loan.web.fetchModels.PhaseDetailsModel;
import kg.gov.mf.loan.web.fetchModels.PhaseDetailsModelList;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SupervisorPlanController {
	
	@Autowired
	LoanService loanService;
	
	@Autowired
	SupervisorPlanService spService;

	@Autowired
	DebtorService debtorService;

	@Autowired
	SupervisorPlanRepository supervisorPlanRepository;

	@Autowired
    UserService userService;

	@Autowired
    StaffService staffService;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

	private Date globalRegDate=new Date();

    @RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/sp/{spId}/view", method=RequestMethod.GET)
    public String view(ModelMap model,
                           @PathVariable("debtorId")Long debtorId,
                           @PathVariable("loanId")Long loanId,
                           @PathVariable("spId")Long spId){
        SupervisorPlan plan=spService.getById(spId);
        model.addAttribute("plan",plan);

        String createdByStr=null;
        String modifiedByStr=null;

        if(plan.getAuCreatedBy()!=null){
            if(plan.getAuCreatedBy().equals("admin")){
                createdByStr="Система";
            }
            else{
                User createdByUser=userService.findByUsername(plan.getAuCreatedBy());
                Staff createdByStaff=createdByUser.getStaff();
                createdByStr=createdByStaff.getName();
            }
        }
        if(plan.getAuLastModifiedBy()!=null){
            if(plan.getAuLastModifiedBy().equals("admin")){
                modifiedByStr="Система";
            }
            else{
                User lastModifiedByUser=userService.findByUsername(plan.getAuLastModifiedBy());
                Staff lastModifiedByStaff=lastModifiedByUser.getStaff();
                modifiedByStr=lastModifiedByStaff.getName();
            }
        }
        model.addAttribute("createdBy",createdByStr);
        model.addAttribute("modifiedBy",modifiedByStr);


        return "/manage/debtor/loan/sp/view";
    }

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/sp/add", method=RequestMethod.GET)
	public String formSupervisorPlan(ModelMap model,
			@PathVariable("debtorId")Long debtorId, 
			@PathVariable("loanId")Long loanId)
	{

		HashMap<Integer,String> monthList=new HashMap<>();
		monthList.put(1,"Январь");
		monthList.put(2,"Февраль");
		monthList.put(3,"Март");
		monthList.put(4,"Апрель");
		monthList.put(5,"Май");
		monthList.put(6,"Июнь");
		monthList.put(7,"Июль");
		monthList.put(8,"Август");
		monthList.put(9,"Сентябрь");
		monthList.put(10,"Октябрь");
		monthList.put(11,"Ноябрь");
		monthList.put(12,"Декабрь");
		model.addAttribute("monthList",monthList);

		List<Integer> years=new ArrayList<>();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for(int i=2015;i<=year+3;i++){
            years.add(i);
		}
		model.addAttribute("years",years);
		model.addAttribute("year",year);

        SupervisorPlan sp=new SupervisorPlan();
        sp.setDate(new Date());
        sp.setAmount(0.0);
        sp.setInterest(0.0);
        sp.setPenalty(0.0);
        sp.setPrincipal(0.0);
        sp.setDescription("-");
        sp.setRecord_status(1);
        model.addAttribute("sp", sp);
		
        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));
			
		return "/manage/debtor/loan/sp/add";
	}

    @RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/sp/{spId}/save", method=RequestMethod.GET)
    public String formPlan(ModelMap model,
                                 @PathVariable("debtorId")Long debtorId,
                                 @PathVariable("loanId")Long loanId,
                                 @PathVariable("spId")Long spId)
    {

        if(spId == 0)
        {
            SupervisorPlan sp=new SupervisorPlan();
            sp.setDate(new Date());
            sp.setAmount(0.0);
            sp.setInterest(0.0);
            sp.setPenalty(0.0);
            sp.setPrincipal(0.0);
            sp.setDescription("-");
            sp.setRecord_status(1);
            model.addAttribute("sp", sp);
        }

        if(spId > 0)
        {
            SupervisorPlan sp=spService.getById(spId);
            System.out.println(sp.getReg_date());
            globalRegDate=sp.getReg_date();
            model.addAttribute("sp", sp);
        }

        model.addAttribute("debtorId", debtorId);
        model.addAttribute("debtor", debtorService.getById(debtorId));
        model.addAttribute("loanId", loanId);
        model.addAttribute("loan", loanService.getById(loanId));

        return "/manage/debtor/loan/sp/save";
    }
	
	@RequestMapping(value = { "/manage/debtor/{debtorId}/loan/{loanId}/sp/save"}, method=RequestMethod.POST)
    public String saveSupervisorPlan(SupervisorPlan sp, @PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, ModelMap model)
    {
		Loan loan = loanService.getById(loanId);
		sp.setLoan(loan);
		sp.setFee(0.0);

		if(sp.getId() == 0){
			sp.setReg_by_id(1);
			sp.setReg_date(sp.getDate());
			spService.add(sp);
		}
		else {
            sp.setReg_date(globalRegDate);
            spService.update(sp);
        }
		updateLoanData(loanId);

		try
        {
            if(loan.getParent()!=null)
            {
                this.spService.updateLoanDataAfterPlanAdd(loan.getParent().getId());
            }

        }
        catch (Exception ex)
        {

        }


		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }
	
	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/sp/{spId}/delete", method=RequestMethod.GET)
    public String deleteSupervisorPlan(@PathVariable("debtorId")Long debtorId, @PathVariable("loanId")Long loanId, @PathVariable("spId")Long spId) {
		if(spId > 0)
		    supervisorPlanRepository.delete(supervisorPlanRepository.findOne(spId));
		return "redirect:" + "/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

	@RequestMapping(value="/manage/debtor/{debtorId}/loan/{loanId}/savePlans", method=RequestMethod.POST)
	@ResponseBody
	public String initializePhaseSave(@PathVariable("debtorId")Long debtorId,@PathVariable("loanId")Long loanId,
                                      @RequestBody PhaseDetailsModelList phaseDetailsModels,
                                      @RequestParam("year") Integer year) {
	    Loan loan=loanService.getById(loanId);
	    User user=userService.findByUsername(Utils.getPrincipal());
	    Long userId=user.getId();
	    if(phaseDetailsModels.getPhaseDetailsModels().size() > 0){
            List<PhaseDetailsModel> list = phaseDetailsModels.getPhaseDetailsModels();
            Date date= new Date();
            date.setYear(year-1900);
            for (PhaseDetailsModel model: list) {
                date.setMonth(Math.toIntExact(model.getLoanId())-1);
                if(model.getLoanId()-1==1){
                    date.setDate(28);
                }
                else{
                    date.setDate(30);
                }
                SupervisorPlan plan=new SupervisorPlan();
                plan.setDate(date);
                plan.setReg_date(date);
                plan.setDescription(model.getLoanRegNumber());
                plan.setPrincipal(model.getStartPrincipal());
                plan.setPenalty(model.getStartPenalty());
                plan.setInterest(model.getStartInterest());
                plan.setAmount(model.getStartTotalAmount());
                plan.setLoan(loan);
                plan.setFee(0.0);
                plan.setReg_by_id(userId);
                spService.add(plan);
            }
        }

        try
        {
            if(loan.getParent()!=null)
            {
                this.spService.updateLoanDataAfterPlanAdd(loan.getParent().getId());
            }

        }
        catch (Exception ex)
        {

        }


		return "redirect:/manage/debtor/{debtorId}/loan/{loanId}/view";
	}

	@GetMapping("/supervisorPlan/paymentScheduleList")
    public String getPaymentScheduleListInSPAdd(){
        return "/manage/debtor/loan/sp/paymentSchedulesTableDiv";
    }

    @GetMapping("/supervisorPlan/loanSummaryList")
    public String getLoanSUmmaryListInSPAdd(){
        return "/manage/debtor/loan/sp/loanSummariesTableDiv";
    }


	//function for updating loan data
    public void updateLoanData(Long loanId){

    }
}
