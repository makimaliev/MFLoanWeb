package kg.gov.mf.loan.web.controller.manage.asset;

import kg.gov.mf.loan.manage.model.asset.AssetExpenseType;
import kg.gov.mf.loan.manage.service.asset.AssetExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AssetExpenseTypeController {

    //region services
    @Autowired
    AssetExpenseTypeService assetExpenseTypeService;
    //endregion

    @GetMapping("/assetExpenseType/list")
    public String list(Model model){

        List<AssetExpenseType> assetExpenseTypeList=assetExpenseTypeService.list();

        model.addAttribute("list",assetExpenseTypeList);

        return "/manage/asset/assetExpense/assetExpenseType/list";
    }

    @GetMapping("/assetExpenseType/{id}/view")
    public String view(Model model, @PathVariable("id") Long id){

        AssetExpenseType assetExpenseType=assetExpenseTypeService.getById(id);

        model.addAttribute("type",assetExpenseType);

        return null;
    }

    @GetMapping("/assetExpenseType/{id}/save")
    public String getSave(Model model, @PathVariable("id") Long id){

        if(id==0){
            model.addAttribute("type",new AssetExpenseType());
        }
        else {
            AssetExpenseType assetExpenseType=assetExpenseTypeService.getById(id);
            model.addAttribute("type",assetExpenseType);
        }

        return "/manage/asset/assetExpense/assetExpenseType/save";
    }

    @PostMapping("/assetExpenseType/save")
    public String postSave(AssetExpenseType type){

        if (type.getId()==0){
            assetExpenseTypeService.add(type);
        }
        else {
            assetExpenseTypeService.update(type);
        }

        return "redirect:/assetExpenseType/list";
    }
}
