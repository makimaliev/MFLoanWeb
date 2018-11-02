package kg.gov.mf.loan.web.controller.manage;


import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.manage.service.collateral.CollateralItemService;
import kg.gov.mf.loan.manage.service.collateral.ItemTypeService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CollateralItemViewController {

    @Autowired
    RegionService regionService;

    @Autowired
    DistrictService districtService;

    @Autowired
    ItemTypeService itemTypeService;


    @RequestMapping(value = {"/manage/collateralItemViews","/manage/collateralItemViews/list"}, method = RequestMethod.GET)
    public String ListOfCollateralItemViews(ModelMap model){

        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());
        model.addAttribute("itemTypes",itemTypeService.list());
        model.addAttribute("loggedinuser", Utils.getPrincipal());

        return "/manage/collateral/collateralItemViewList";

    }
}
