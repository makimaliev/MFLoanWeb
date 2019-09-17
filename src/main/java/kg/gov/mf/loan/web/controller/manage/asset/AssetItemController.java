package kg.gov.mf.loan.web.controller.manage.asset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kg.gov.mf.loan.manage.model.asset.Asset;
import kg.gov.mf.loan.manage.model.asset.AssetItem;
import kg.gov.mf.loan.manage.model.asset.AssetItemDetails;
import kg.gov.mf.loan.manage.model.collateral.ItemType;
import kg.gov.mf.loan.manage.model.collateral.QuantityType;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.service.asset.AssetItemDetailsService;
import kg.gov.mf.loan.manage.service.asset.AssetItemService;
import kg.gov.mf.loan.manage.service.asset.AssetService;
import kg.gov.mf.loan.manage.service.collateral.ItemTypeService;
import kg.gov.mf.loan.manage.service.collateral.QuantityTypeService;
import kg.gov.mf.loan.manage.service.debtor.OwnerService;
import kg.gov.mf.loan.web.fetchModels.AssetItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("")
public class AssetItemController {
    //region services
    @Autowired
    AssetService assetService;

    @Autowired
    AssetItemService assetItemService;

    @Autowired
    AssetItemDetailsService assetItemDetailsService;

    @Autowired
    QuantityTypeService quantityTypeService;

    @Autowired
    ItemTypeService itemTypeService;

    @Autowired
    OwnerService ownerService;

    //endregion

    @GetMapping("/asset/{id}/assetItem/list")
    public String list(Model model,@PathVariable("id") Long id){

        Asset asset=assetService.getById(id);
        List<AssetItem> assetItemList=assetItemService.list();

        model.addAttribute("list",assetItemList);
        model.addAttribute("asset",asset);

        return "/manage/asset/assetItem/list";
    }

    @GetMapping("/asset/{id}/assetItems/list")
    public String listAsset(Model model,@PathVariable("id") Long id){

        Asset asset=assetService.getById(id);
        Set<AssetItem> assetItemList=asset.getAssetItems();

        model.addAttribute("asset",asset);
        model.addAttribute("list",assetItemList);

        return "/manage/asset/assetItem/list";
    }

    @GetMapping("/asset/{id}/assetItem/{iId}/view")
    public String view(Model model, @PathVariable("id") Long id,@PathVariable("iId") Long iId){

        Asset asset=assetService.getById(id);
        AssetItem assetItem=assetItemService.getById(iId);

        model.addAttribute("asset",asset);
        model.addAttribute("item",assetItem);

        return "/manage/asset/assetItem/view";
    }

    @GetMapping("/asset/{id}/assetItem/{iId}/save")
    public String getSave(Model model,@PathVariable("id") Long id,@PathVariable("iId") Long iId){

        Asset asset=assetService.getById(id);

        if(iId == 0)
        {
            AssetItem assetItem=new AssetItem();
            assetItem.setQuantityType(quantityTypeService.getById(Long.valueOf(1)));
            assetItem.setQuantity(1.0);
            assetItem.setCollateralValue(0.0);
            assetItem.setEstimatedValue(0.0);
            assetItem.setAsset(asset);
            AssetItemDetails assetItemDetails=new AssetItemDetails();
//			assetItem.setAssetItemDetails(assetItemDetails);
//			assetItemDetails.setAssetItem(assetItem);
            model.addAttribute("ownerText","");
            model.addAttribute("organizationText","");
            assetItem.setItemType(itemTypeService.getById(Long.valueOf(1)));
            model.addAttribute("item", assetItem);
            model.addAttribute("itemDetails", assetItemDetails);
        }

        if(iId> 0)
        {
            AssetItem tItem = assetItemService.getById(iId);
            AssetItemDetails tDetails = assetItemDetailsService.getById(tItem.getAssetItemDetails().getId());
            model.addAttribute("item", tItem);
            model.addAttribute("itemDetails", tDetails);
            Owner owner=ownerService.getById(tItem.getOwner().getId());
            if(owner.getOwnerType().name()=="ORGANIZATION"){
                model.addAttribute("ownerText","["+owner.getId()+"] "+owner.getName()+" (Организация)");
            }
            else{
                model.addAttribute("ownerText","["+owner.getId()+"] "+owner.getName()+" (Физ. лицо)");
            }

            Owner owner1 = null;
            if(tItem.getOrganization()!=null)
            {
                owner1=ownerService.getById(tItem.getOrganization().getId());
                model.addAttribute("organizationText","["+owner1.getId()+"] "+owner1.getName()+" (Организация)");
            }
        }

        List<QuantityType> quantityTypes=quantityTypeService.list();
        List<ItemType> itemTypes=itemTypeService.list();

        model.addAttribute("asset",asset);
        model.addAttribute("quantities",quantityTypes);
        model.addAttribute("itemTypes",itemTypes);

        return "/manage/asset/assetItem/save";
    }

    @PostMapping("asset/{id}/assetItem/save")
    public String postSave(@PathVariable("id") Long id,AssetItem assetItem,AssetItemDetails assetItemDetails,String date, String expl_date){

        Asset asset=assetService.getById(id);

        if (assetItem.getId()==0){
            assetItemDetailsService.add(assetItemDetails);
            assetItemDetails.setAssetItem(assetItem);
            assetItem.setAssetItemDetails(assetItemDetails);
            assetItem.setAsset(asset);
            assetItemService.add(assetItem);
            assetItemDetailsService.update(assetItemDetails);
        }
        else {
            assetItem.setAssetItemDetails(assetItemDetails);
            assetItem.setAsset(asset);
            assetItemService.update(assetItem);
        }

        return "redirect:/asset/{id}/assetItem/"+assetItem.getId()+"/view";
    }


//    Rest Requests
    @PostMapping("/api/assetItems/{assetId}")
    @ResponseBody
    public String getAssetItemsByAssetId(@PathVariable("assetId") Long assetId){
        List<AssetItemModel> assetItemModelList=new ArrayList<>();
        List<AssetItem> assetItems=assetItemService.getByAssetId(assetId);
        for (AssetItem assetItem:assetItems){
            AssetItem item=assetItemService.getById(assetItem.getId());
            AssetItemModel assetItemModel=new AssetItemModel();

            assetItemModel.setId(item.getId());
            assetItemModel.setAssetItemDetailsId((item.getAssetItemDetails() == null) ? 0 : item.getAssetItemDetails().getId());
            assetItemModel.setCollateralValue(item.getCollateralValue());
            assetItemModel.setConditionTypeName((item.getConditionType() == null) ? "" : item.getConditionType().getName());
            assetItemModel.setDemand_rate(item.getDemand_rate());
            assetItemModel.setDescription(item.getDescription());
            assetItemModel.setEstimatedValue(item.getEstimatedValue());
            assetItemModel.setInspection_needed_description(item.getInspection_needed_description());
            assetItemModel.setItemTypeName(item.getItemType().getName());
            assetItemModel.setName(item.getName());
            assetItemModel.setOrganization((item.getOrganization() == null) ? "" :item.getOrganization().getName());
            assetItemModel.setOrganizationId((item.getOrganization() == null) ? 0 :item.getOrganization().getId());
            assetItemModel.setOwner(item.getOwner().getName());
            assetItemModel.setOwnerId(item.getOwner().getId());
            assetItemModel.setQuantity(item.getQuantity());
            assetItemModel.setQuantityTypeName(item.getQuantityType().getName());
            assetItemModel.setRisk_rate(item.getRisk_rate());
            assetItemModel.setStatus(item.getStatus());
            assetItemModel.setName(item.getName());
            assetItemModelList.add(assetItemModel);
        }
        Gson gson = new GsonBuilder().setDateFormat("dd.MM.yyyy").create();

        String result = gson.toJson(assetItemModelList);
        return result;
    }
}
