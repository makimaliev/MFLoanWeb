package kg.gov.mf.loan.web.controller.manage.asset;


import kg.gov.mf.loan.manage.model.asset.Asset;
import kg.gov.mf.loan.manage.model.asset.AssetExpense;
import kg.gov.mf.loan.manage.service.asset.AssetExpenseService;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class AssetExpenseController {

    //region services
    @Autowired
    AssetService assetService;

    @Autowired
    AssetExpenseService assetExpenseService;
    //endregion

    @GetMapping("/asset/{id}/assetExpense/list")
    public String list(Model model, @PathVariable("id") Long id){

        Asset asset=assetService.getById(id);
        Set<AssetExpense> assetExpenses=asset.getAssetExpenses();

        model.addAttribute("asset",asset);
        model.addAttribute("expenses",assetExpenses);

        return "/manage/asset/assetExpense/list";
    }

    @GetMapping("/asset/{id}/assetExpense/{eId}/view")
    public String view(Model model, @PathVariable("id") Long id,@PathVariable("eId") Long eId){

        AssetExpense assetExpenses=assetExpenseService.getById(eId);

        model.addAttribute("assetId",id);
        model.addAttribute("expenses",assetExpenses);

        return "/manage/asset/assetExpense/view";
    }

    @GetMapping("/asset/{id}/assetExpense/{eId}/save")
    public String getSave(Model model,@PathVariable("id") Long id,@PathVariable("eId") Long eId){

        if(id==0){
            Asset asset=assetService.getById(id);
            AssetExpense assetExpense=new AssetExpense();
            assetExpense.setAsset(asset);
            model.addAttribute("expense",assetExpense);
        }
        else{
            AssetExpense assetExpense=assetExpenseService.getById(eId);
            model.addAttribute("expense",assetExpense);
        }

        return "/manage/asset/assetExpense/save";
    }

    @PostMapping("/asset/{id}/assetExpense/save")
    public String postSave(AssetExpense expense,@PathVariable("id") Long id){

        if(id==0){
            assetExpenseService.add(expense);
        }
        else{
            assetExpenseService.update(expense);
        }

        return "redirect:/asset/{id}/assetExpense/"+expense.getId()+"/view";
    }
}
