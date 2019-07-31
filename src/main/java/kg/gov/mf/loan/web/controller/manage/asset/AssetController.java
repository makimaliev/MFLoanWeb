package kg.gov.mf.loan.web.controller.manage.asset;

import kg.gov.mf.loan.manage.model.asset.Asset;
import kg.gov.mf.loan.manage.model.asset.AssetItem;
import kg.gov.mf.loan.manage.model.asset.AssetStatus;
import kg.gov.mf.loan.manage.model.asset.AssetType;
import kg.gov.mf.loan.manage.service.asset.AssetItemService;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import kg.gov.mf.loan.manage.service.asset.AssetStatusService;
import kg.gov.mf.loan.manage.service.asset.AssetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/asset")
public class AssetController {

    //region services
    @Autowired
    AssetService assetService;

    @Autowired
    AssetTypeService assetTypeService;

    @Autowired
    AssetStatusService assetStatusService;

    @Autowired
    AssetItemService assetItemService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    //endregion

    @GetMapping("/list")
    public String list(Model model){

        List<Asset> assets=assetService.list();
        List<AssetType> assetTypelist=assetTypeService.list();
        List<AssetStatus> assetStatuslist=assetStatusService.list();

        model.addAttribute("list",assets);
        model.addAttribute("types",assetTypelist);
        model.addAttribute("statuses",assetStatuslist);

        return "/manage/asset/list";
    }

    @GetMapping("/{id}/view")
    public String view(Model model, @PathVariable("id") Long id){

        Asset asset=assetService.getById(id);
        Set<AssetItem> assetItemList=asset.getAssetItems();

        model.addAttribute("assetId",asset.getId());
        model.addAttribute("asset",asset);
        model.addAttribute("items",assetItemList);

        return "/manage/asset/view";
    }

    @GetMapping("/{id}/save")
    public String getSave(Model model,@PathVariable("id") Long id){

        if(id==null || id==0){
            model.addAttribute("asset",new Asset());
        }
        else if(id>0){
            Asset asset=assetService.getById(id);
            model.addAttribute("asset",asset);
        }

        List<AssetType> assetTypelist=assetTypeService.list();
        List<AssetStatus> assetStatuslist=assetStatusService.list();

        model.addAttribute("types",assetTypelist);
        model.addAttribute("statuses",assetStatuslist);

        return "/manage/asset/save";
    }

    @PostMapping("/save")
    public String postSave(Asset asset) throws ParseException {

        if (asset.getId()==0){
            assetService.add(asset);
        }
        else{
            assetService.update(asset);
        }

        return "redirect:/asset/"+asset.getId()+"/view";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id){

        Asset asset=assetService.getById(id);
        assetService.remove(asset);

        return "redirect:/asset/list";
    }
}
