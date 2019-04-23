package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.loan.DestinationAccount;
import kg.gov.mf.loan.manage.service.loan.DestinationAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DestinationAccountController {

    @Autowired
    DestinationAccountService destinationAccountService;

    @GetMapping("/destinationAccount/list")
    public String list(ModelMap model){

        model.addAttribute("destinationAccounts",destinationAccountService.list());

        return "/manage/debtor/loan/destinationaccount/list";
    }

    @GetMapping("/manage/destinationAccount/{id}/save")
    public String addEdit(ModelMap model, @PathVariable("id") long id){
        if(id==0){
            model.addAttribute("destinationAccount",new DestinationAccount());
        }
        else if(id>0){
            model.addAttribute("destinationAccount",destinationAccountService.getById(id));
        }

        return "/manage/debtor/loan/destinationaccount/form";

    }

    @PostMapping("/manage/destinationAccount/save")
    public String save(DestinationAccount destinationAccount){
        if(destinationAccount.getId()==0){
            destinationAccountService.add(destinationAccount);
        }
        else if(destinationAccount.getId()>0){
            destinationAccountService.update(destinationAccount);
        }
        return "redirect:/destinationAccount/list";
    }

}
