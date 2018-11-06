package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CollateralArrestFreeViewController {

    @Autowired
    RegionService regionService;

    @Autowired
    DistrictService districtService;



    @RequestMapping("/collateralArrestFreeView")
    public String getAll(ModelMap model){

        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());
        model.addAttribute("loggedinuser", Utils.getPrincipal());

        return "/output/report/collateralArrestFreeViewList";
    }
}
