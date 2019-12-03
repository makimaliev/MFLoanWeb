package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.DebtorGroup;
import kg.gov.mf.loan.manage.model.debtor.DebtorSubGroup;
import kg.gov.mf.loan.manage.service.debtor.DebtorGroupService;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.DebtorSubGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DebtorGroupController {

    //region services
    @Autowired
    DebtorGroupService debtorGroupService;

    @Autowired
    DebtorSubGroupService debtorSubGroupService;

    @Autowired
    DebtorService debtorService;
    //endregion

    @GetMapping("/debtorGroup/list")
    public String list(Model model){

        List<DebtorGroup> list = debtorGroupService.list();

        model.addAttribute("list",list);

        return "/manage/debtor/debtorGroup/list";
    }

    @GetMapping("/debtorGroup/{groupId}/save")
    public String getSaveForm(Model model, @PathVariable("groupId") Long groupId){

        if(groupId == 0){
            model.addAttribute("group",new DebtorGroup());
        }
        else if(groupId > 0){
            DebtorGroup group = debtorGroupService.getById(groupId);
            model.addAttribute("group",group);
        }

        return "/manage/debtor/debtorGroup/save";
    }

    @PostMapping("/debtorGroup/save")
    public String save(DebtorGroup debtorGroup){

        if(debtorGroup.getId() == 0){

            debtorGroupService.add(debtorGroup);
        }
        else if(debtorGroup.getId() > 0){
            debtorGroupService.update(debtorGroup);
        }

        return "redirect:/debtorGroup/list";
    }

    @GetMapping("/debtor/{debtorId}/debtorGroup/change")
    public String changeDebtorGroups(Model model,@PathVariable("debtorId") Long debtorId){

        Debtor debtor = debtorService.getById(debtorId);

        List<DebtorSubGroup> debtorSubGroupList= debtorSubGroupService.list();

        model.addAttribute("subGroupList",debtorSubGroupList);

        model.addAttribute("debtor",debtor);

        return "manage/debtor/debtorGroupForm";
    }

    @PostMapping("/debtor/{debtorId}/debtorGroup/save")
    public String saveDebtorGroupChanges(@PathVariable("debtorId") long debtorId,Debtor debtor){

        Debtor realDebtor = debtorService.getById(debtorId);

        DebtorSubGroup debtorSubGroup = debtorSubGroupService.getById(debtor.getDebtorSubGroup().getId());
        DebtorGroup debtorGroup = debtorGroupService.getById(debtorSubGroup.getDebtorGroup().getId());
        realDebtor.setDebtorGroup(debtorGroup);
        realDebtor.setDebtorSubGroup(debtorSubGroup);

        debtorService.update(realDebtor);

            return "redirect:/manage/debtor/{debtorId}/view";
    }

    @GetMapping("/debtor/{debtorId}/debtorGroup/{debtorGroupId}/getDebtorSubGroups")
    public String getSubGroupsForm(Model model, @PathVariable("debtorId") Long debtorId,
                                   @PathVariable("debtorGroupId") Long debtorGroupId){

        List<DebtorSubGroup> list = debtorSubGroupService.getByDebtorGroup(debtorGroupId);

        Debtor debtor = debtorService.getById(debtorId);

        DebtorSubGroup debtorSubGroup = new DebtorSubGroup();
        try{
            debtorSubGroup= debtorSubGroupService.getById(debtor.getDebtorSubGroup().getId());
        }
        catch (Exception e){
            System.out.println(e);
        }

        model.addAttribute("list",list);
        model.addAttribute("debtorSubGroup",debtorSubGroup);

        return "/manage/debtor/debtorSubGroupSelector";
    }
}
