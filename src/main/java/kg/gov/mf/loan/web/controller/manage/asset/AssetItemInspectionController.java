package kg.gov.mf.loan.web.controller.manage.asset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.asset.AssetItem;
import kg.gov.mf.loan.manage.model.asset.AssetItemInspection;
import kg.gov.mf.loan.manage.model.collateral.InspectionResultType;
import kg.gov.mf.loan.manage.service.asset.AssetItemInspectionService;
import kg.gov.mf.loan.manage.service.asset.AssetItemService;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import kg.gov.mf.loan.manage.service.collateral.InspectionResultTypeService;
import kg.gov.mf.loan.web.fetchModels.AssetItemInspectionModel;
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
public class AssetItemInspectionController {

    //region services
    @Autowired
    AssetService assetService;

    @Autowired
    AssetItemService assetItemService;

    @Autowired
    AssetItemInspectionService assetItemInspectionService;

    @Autowired
    InspectionResultTypeService inspectionResultTypeService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    //endregion


    @GetMapping("/assetItemInspection/list")
    public String list(Model model){

        List<AssetItemInspection> assetItemInspectionList=assetItemInspectionService.list();

        model.addAttribute("list",assetItemInspectionList);

        return "/manage/asset/assetItem/assetItemInspection/list";
    }

    @GetMapping("/asset/{assetId}/assetItem/{itemId}/assetItemInspection/list")
    public String listAssetItem(Model model,@PathVariable("assetId") Long assetId,@PathVariable("itemId") Long itemId){

        AssetItem assetItem=assetItemService.getById(itemId);
        Set<AssetItemInspection> assetItemInspectionSet=assetItem.getAssetItemInspections();

        model.addAttribute("assetItem",assetItem);
        model.addAttribute("list",assetItemInspectionSet);

        return null;
    }

    @GetMapping("/asset/{assetId}/assetItem/{itemId}/assetItemInspection/{inspectionId}/view")
    public String view(Model model, @PathVariable("assetId") Long assetId,@PathVariable("itemId") Long itemId
            ,@PathVariable("inspectionId") Long inspectionId){

        AssetItem assetItem=assetItemService.getById(itemId);
        AssetItemInspection assetItemInspection=assetItemInspectionService.getById(inspectionId);

        model.addAttribute("item",assetItem);
        model.addAttribute("inspection",assetItemInspection);

        return "/manage/asset/assetItem/assetItemInspection/view";
    }

    @GetMapping("/asset/{assetId}/assetItem/{itemId}/assetItemInspection/{inspectionId}/save")
    public String getSave(Model model,@PathVariable("assetId") Long assetId,@PathVariable("itemId") Long itemId
            ,@PathVariable("inspectionId") Long inspectionId){

        if(inspectionId==0){
            AssetItem assetItem=assetItemService.getById(itemId);
            AssetItemInspection assetItemInspection=new AssetItemInspection();
            assetItemInspection.setAssetItem(assetItem);
            model.addAttribute("inspection",assetItemInspection);
        }
        else {
            AssetItemInspection assetItemInspection=assetItemInspectionService.getById(inspectionId);
            model.addAttribute("inspection",assetItemInspection);
        }
        List<InspectionResultType> inspectionResultTypeList=inspectionResultTypeService.list();
        model.addAttribute("types",inspectionResultTypeList);
        model.addAttribute("assetId",assetId);
        model.addAttribute("itemId",itemId);

        return "/manage/asset/assetItem/assetItemInspection/save";
    }

    @PostMapping("/asset/{assetId}/assetItem/{itemId}/assetItemInspection/save")
    public String postSave(AssetItemInspection assetItemInspection,@PathVariable("assetId") Long assetId,@PathVariable("itemId") Long itemId){

        if (assetItemInspection.getId()==0){
            AssetItem assetItem=assetItemService.getById(itemId);
            assetItemInspection.setAssetItem(assetItem);
            assetItemInspectionService.add(assetItemInspection);
        }
        else {
            AssetItemInspection oldAssetItemInspection=assetItemInspectionService.getById(assetItemInspection.getId());
            oldAssetItemInspection.setInspectionResultType(assetItemInspection.getInspectionResultType());
            oldAssetItemInspection.setOnDate(assetItemInspection.getOnDate());
            oldAssetItemInspection.setDetails(assetItemInspection.getDetails());

            assetItemInspectionService.update(oldAssetItemInspection);
        }

        return "redirect:/asset/{assetId}/assetItem/{itemId}/view";
    }

//    Rest Requests
    @PostMapping("/api/assetItemInspections/{assetItemId}")
    @ResponseBody
    public String apiGetInspectionsByAssetId(@PathVariable("assetItemId") Long assetItemId){

        List<AssetItemInspection> assetItemInspectionList=assetItemInspectionService.getByAssetItemId(assetItemId);
        List<AssetItemInspectionModel> assetItemInspectionModelList=new ArrayList<>();

        for (AssetItemInspection assetItemInspection:assetItemInspectionList){

            AssetItemInspection inspection=assetItemInspectionService.getById(assetItemInspection.getId());
            AssetItemInspectionModel model=new AssetItemInspectionModel();

            model.setAssetItemId(assetItemId);
            model.setDetails(inspection.getDetails());
            model.setId(inspection.getId());
            model.setInspectionResultTypeName(inspection.getInspectionResultType().getName());
            model.setOnDate(inspection.getOnDate());

            assetItemInspectionModelList.add(model);
        }

        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

        String result = gson.toJson(assetItemInspectionModelList);
        return result;
    }


}
