package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.collateral.ArrestFreeStatus;
import kg.gov.mf.loan.manage.service.collateral.ArrestFreeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/arrestFreeStatus")
public class ArrestFreeStatusController {

    //region services
    @Autowired
    ArrestFreeStatusService arrestFreeStatusService;
    //endregion

    @GetMapping("/list")
    public String list(Model model){

        List<ArrestFreeStatus> list = arrestFreeStatusService.list();

        model.addAttribute("list",list);

        return "/manage/debtor/collateralagreement/collateralitem/arrestfree/arrestFreeStatus/list";
    }

    @GetMapping("/{statusId}/save")
    public String getSaveForm(Model model, @PathVariable("statusId") Long statusId){

        if(statusId == 0){
            model.addAttribute("status",new ArrestFreeStatus());
        }
        else if(statusId > 0){
            ArrestFreeStatus status = arrestFreeStatusService.getById(statusId);
            model.addAttribute("status",status);
        }

        return "/manage/debtor/collateralagreement/collateralitem/arrestfree/arrestFreeStatus/save";
    }

    @PostMapping("/save")
    public String save(ArrestFreeStatus arrestFreeStatus){

        if(arrestFreeStatus.getId() == 0){

            arrestFreeStatusService.add(arrestFreeStatus);
        }
        else if(arrestFreeStatus.getId() > 0){
            arrestFreeStatusService.update(arrestFreeStatus);
        }

        return "redirect:/arrestFreeStatus/list";
    }
}