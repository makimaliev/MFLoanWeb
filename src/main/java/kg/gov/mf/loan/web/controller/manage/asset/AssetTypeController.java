package kg.gov.mf.loan.web.controller.manage.asset;

import kg.gov.mf.loan.manage.model.asset.AssetType;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import kg.gov.mf.loan.manage.service.asset.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AssetTypeController {

    //region services
    @Autowired
    AssetService assetService;

    @Autowired
    AssetTypeService assetTypeService;
    //endregion

    @GetMapping("/assetType/list")
    public String list(Model model){

        List<AssetType> assetTypeList=assetTypeService.list();

        model.addAttribute("list",assetTypeList);

        return "/manage/asset/assetType/list";
    }

    @GetMapping("/assetType/{id}/view")
    public String view(Model model, @PathVariable("id") Long id){

        AssetType assetType=assetTypeService.getById(id);

        model.addAttribute("type",assetType);

        return null;
    }

    @GetMapping("/assetType/{id}/save")
    public String getSave(Model model,@PathVariable("id") Long id){

        if(id==0){
            model.addAttribute("type",new AssetType());
        }
        else {
            AssetType assetType=assetTypeService.getById(id);
            model.addAttribute("type",assetType);
        }

        return "/manage/asset/assetType/save";
    }

    @PostMapping("/assetType/save")
    public String postSave(AssetType type){

        if (type.getId()==0){
            assetTypeService.add(type);
        }
        else {
            assetTypeService.update(type);
        }

        return "redirect:/assetType/list";
    }
}
