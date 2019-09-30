package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.collateral.InspectionStatus;
import kg.gov.mf.loan.manage.service.collateral.InspectionStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/inspectionStatus")
public class InspectionStatusController {

    //region services
    @Autowired
    InspectionStatusService inspectionStatusService;
    //endregion

    @GetMapping("/list")
    public String list(Model model){

        List<InspectionStatus> list = inspectionStatusService.list();

        model.addAttribute("list",list);

        return "/manage/debtor/collateralagreement/collateralitem/insresult/inspectionStatus/list";
    }

    @GetMapping("/{statusId}/save")
    public String getSaveForm(Model model, @PathVariable("statusId") Long statusId){

        if(statusId == 0){
            model.addAttribute("status",new InspectionStatus());
        }
        else if(statusId > 0){
            InspectionStatus status = inspectionStatusService.getById(statusId);
            model.addAttribute("status",status);
        }

        return "/manage/debtor/collateralagreement/collateralitem/insresult/inspectionStatus/save";
    }

    @PostMapping("/save")
    public String save(InspectionStatus inspectionStatus){

        if(inspectionStatus.getId() == 0){

            inspectionStatusService.add(inspectionStatus);
        }
        else if(inspectionStatus.getId() > 0){
            inspectionStatusService.update(inspectionStatus);
        }

        return "redirect:/inspectionStatus/list";
    }
}
