package kg.gov.mf.loan.web.controller.manage.asset;

import kg.gov.mf.loan.manage.model.asset.AssetStatus;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import kg.gov.mf.loan.manage.service.asset.AssetStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AssetStatusController {

    //region services
    @Autowired
    AssetService assetService;

    @Autowired
    AssetStatusService assetStatusService;
    //endregion

    @GetMapping("/assetStatus/list")
    public String list(Model model){

        List<AssetStatus> assetStatusList=assetStatusService.list();

        model.addAttribute("list",assetStatusList);

        return "/manage/asset/assetStatus/list";
    }

    @GetMapping("/assetStatus/{id}/view")
    public String view(Model model, @PathVariable("id") Long id){

        AssetStatus assetStatus=assetStatusService.getById(id);

        model.addAttribute("status",assetStatus);

        return null;
    }

    @GetMapping("/assetStatus/{id}/save")
    public String getSave(Model model,@PathVariable("id") Long id){

        if(id==0){
            model.addAttribute("status",new AssetStatus());
        }
        else {
            AssetStatus assetStatus=assetStatusService.getById(id);
            model.addAttribute("status",assetStatus);
        }

        return "/manage/asset/assetStatus/save";
    }

    @PostMapping("/assetStatus/save")
    public String postSave(AssetStatus status){

        if (status.getId()==0){
            assetStatusService.add(status);
        }
        else {
            assetStatusService.update(status);
        }

        return "redirect:/assetStatus/list";
    }
}
