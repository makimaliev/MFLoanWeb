package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.output.report.service.PaymentViewService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller

public class PaymentViewController {

    @Autowired
    RegionService regionService;

    @Autowired
    DistrictService districtService;



    @RequestMapping(value = {"/paymentView","/paymentView/list"}, method = RequestMethod.GET)
    private String getAll(ModelMap model){

        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());
        model.addAttribute("loggedinuser", Utils.getPrincipal());


        return "/output/report/paymentViewList";
    }
}
