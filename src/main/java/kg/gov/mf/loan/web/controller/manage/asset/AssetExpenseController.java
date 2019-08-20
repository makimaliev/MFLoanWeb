package kg.gov.mf.loan.web.controller.manage.asset;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.asset.Asset;
import kg.gov.mf.loan.manage.model.asset.AssetExpense;
import kg.gov.mf.loan.manage.service.asset.AssetExpenseService;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import kg.gov.mf.loan.web.fetchModels.AssetExpenseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class AssetExpenseController {

    //region services
    @Autowired
    AssetService assetService;

    @Autowired
    AssetExpenseService assetExpenseService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }
    //endregion

    @GetMapping("/asset/{assetId}/assetExpense/list")
    public String list(Model model, @PathVariable("assetId") Long assetId){

        Asset asset=assetService.getById(assetId);
        Set<AssetExpense> assetExpenses=asset.getAssetExpenses();

        model.addAttribute("asset",asset);
        model.addAttribute("expenses",assetExpenses);

        return "/manage/asset/assetExpense/list";
    }

    @GetMapping("/asset/{assetId}/assetExpense/{expenseId}/view")
    public String view(Model model, @PathVariable("assetId") Long assetId,@PathVariable("expenseId") Long expenseId){

        AssetExpense assetExpenses=assetExpenseService.getById(expenseId);

        model.addAttribute("assetId",assetId);
        model.addAttribute("expenses",assetExpenses);

        return "/manage/asset/assetExpense/view";
    }

    @GetMapping("/asset/{assetId}/assetExpense/{expenseId}/save")
    public String getSave(Model model,@PathVariable("assetId") Long assetId,@PathVariable("expenseId") Long expenseId){

        if(expenseId==0){
            Asset asset=assetService.getById(assetId);
            AssetExpense assetExpense=new AssetExpense();
            assetExpense.setAsset(asset);
            model.addAttribute("expense",assetExpense);
        }
        else{
            AssetExpense assetExpense=assetExpenseService.getById(expenseId);
            model.addAttribute("expense",assetExpense);
        }

        return "/manage/asset/assetExpense/save";
    }

    @PostMapping("/asset/{assetId}/assetExpense/save")
    public String postSave(AssetExpense expense,@PathVariable("assetId") Long assetId){

        if(expense.getId()==0){
            assetExpenseService.add(expense);
        }
        else{
            assetExpenseService.update(expense);
        }

        return "redirect:/asset/{assetId}/view";
    }

    //REST REQUESTS

    @PostMapping("/api/assetExpenses/{assetId}")
    @ResponseBody
    public String apiGetAssetExpensesByAssetId(@PathVariable("assetId") Long assetId){

        List<AssetExpense> assetExpenseList=assetExpenseService.getByAssetId(assetId);
        List<AssetExpenseModel> assetExpenseModelList=new ArrayList<>();

        for(AssetExpense assetExpense:assetExpenseList){
            AssetExpenseModel model=new AssetExpenseModel();
            AssetExpense expense=assetExpenseService.getById(assetExpense.getId());

            model.setAmount(expense.getAmount());
            model.setDetails(expense.getDetails());
            model.setId(expense.getId());
            model.setOnDate(expense.getDate());

            assetExpenseModelList.add(model);
        }
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

        String result = gson.toJson(assetExpenseModelList);
        return result;
    }
}
