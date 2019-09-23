package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.loan.Judgement;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.loan.JudgementRepository;
import kg.gov.mf.loan.manage.service.loan.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class JudgementController {


    //region services
    @Autowired
    JudgementRepository judgementRepository;

    @Autowired
    LoanService loanService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
    //endregion


    @GetMapping("/manage/debtor/{debtorId}/loan/{loanId}/judgement/list")
    public String list(@PathVariable("debtorId") Long debtorId,
                       @PathVariable("loanId") Long loanId, Model model){

        List<Judgement> judgementList = judgementRepository.findAll();

        model.addAttribute("judgementList",judgementList);

        return "/manage/order/orderterm/judgement/list";
    }

    @GetMapping("/manage/debtor/{debtorId}/loan/{loanId}/judgement/{judgementId}/view")
    public String view(@PathVariable("debtorId") Long debtorId,
                       @PathVariable("loanId") Long loanId,
                       @PathVariable("judgementId") Long judgementId, Model model){

        Judgement judgement=judgementRepository.findOne(judgementId);

        model.addAttribute("judgement",judgement);
        model.addAttribute("debtorId",debtorId);
        model.addAttribute("loanId",loanId);

        return "/manage/order/orderterm/judgement/view";
    }

    @GetMapping("/manage/debtor/{debtorId}/loan/{loanId}/judgement/{judgementId}/save")
    public String saveForm(@PathVariable("debtorId") Long debtorId,
                           @PathVariable("loanId") Long loanId,
                           @PathVariable("judgementId") Long judgementId, Model model){

        if(judgementId == 0){
            model.addAttribute("judgement",new Judgement());
        }
        else if (judgementId > 0){
            Judgement judgement = judgementRepository.findOne(judgementId);
            model.addAttribute("judgement",judgement);
        }

        model.addAttribute("debtorId",debtorId);
        model.addAttribute("loanId",loanId);

        return "/manage/order/orderterm/judgement/save";
    }

    @PostMapping("/manage/debtor/{debtorId}/loan/{loanId}/judgement/save")
    public String save(@PathVariable("debtorId") Long debtorId,
                       @PathVariable("loanId") Long loanId,Judgement judgement){

        Loan loan = loanService.getById(loanId);

        judgement.setLoan(loan);

        judgementRepository.save(judgement);

        return "redirect:/manage/debtor/{debtorId}/loan/{loanId}/view";
    }

}
