package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.loan.LoanSummaryActState;
import kg.gov.mf.loan.manage.service.loan.LoanSummaryActStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/loansummaryactstate")
public class LoanSummaryActStateController {

    //region services
    @Autowired
    LoanSummaryActStateService loanSummaryActStateService;
    //endregion


    @GetMapping("/list")
    public String list(Model model){

        List<LoanSummaryActState> list = loanSummaryActStateService.list();

        model.addAttribute("list",list);

        return "/manage/debtor/loan/loansummaryact/state/list";
    }

    @GetMapping("/{id}/save")
    public String form(@PathVariable Long id, Model model){

        if(id==null || id==0){
            model.addAttribute("object",new LoanSummaryActState());
        }
        else if(id > 0){
            LoanSummaryActState object = loanSummaryActStateService.getById(id);
            model.addAttribute("object",object);
        }

        return "/manage/debtor/loan/loansummaryact/state/save";
    }

    @PostMapping("/save")
    public String save(LoanSummaryActState object){

        Long id = object.getId();
        if(id==null || id==0){
            loanSummaryActStateService.add(object);
        }
        else if(id > 0){
            loanSummaryActStateService.update(object);
        }

        return "redirect:/loansummaryactstate/list";
    }

}
