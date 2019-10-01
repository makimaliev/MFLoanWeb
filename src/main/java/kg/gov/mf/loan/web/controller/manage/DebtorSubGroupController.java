package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.DebtorSubGroup;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.DebtorSubGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DebtorSubGroupController {

    //region services
    @Autowired
    DebtorSubGroupService debtorSubGroupService;

    @Autowired
    DebtorService debtorService;
    //endregion

    @GetMapping("/debtorSubGroup/list")
    public String list(Model model){

        List<DebtorSubGroup> list = debtorSubGroupService.list();

        model.addAttribute("list",list);

        return "/manage/debtor/debtorSubGroup/list";
    }

    @GetMapping("/debtorSubGroup/{subGroupId}/save")
    public String getSaveForm(Model model, @PathVariable("subGroupId") Long subGroupId){

        if(subGroupId == 0){
            model.addAttribute("group",new DebtorSubGroup());
        }
        else if(subGroupId > 0){
            DebtorSubGroup group = debtorSubGroupService.getById(subGroupId);
            model.addAttribute("group",group);
        }

        return "/manage/debtor/debtorSubGroup/save";
    }

    @PostMapping("/debtorSubGroup/save")
    public String save(DebtorSubGroup debtorSubGroup){

        if(debtorSubGroup.getId() == 0){

            debtorSubGroupService.add(debtorSubGroup);
        }
        else if(debtorSubGroup.getId() > 0){
            debtorSubGroupService.update(debtorSubGroup);
        }

        return "redirect:/debtorSubGroup/list";
    }


    @PostMapping("/debtorSaveSubGroup/instantUpdate")
    @ResponseBody
    public String saveDebtorGroupChanges(Long id,Long debtorIdd){



        Debtor debtor=debtorService.getById(debtorIdd);
        DebtorSubGroup debtorSubGroup = debtorSubGroupService.getById(id);

        debtor.setDebtorSubGroup(debtorSubGroup);
        debtorService.update(debtor);

        return "OK";
    }
}
