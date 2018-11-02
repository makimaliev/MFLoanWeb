package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PaymentScheduleViewController {

    @Autowired
    RegionService regionService;

    @Autowired
    DistrictService districtService;

    @Autowired
    ReferenceViewService referenceViewService;

    @RequestMapping("/paymentScheduleView")
    public  String getList(ModelMap model){

        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());
        model.addAttribute("credit_orders",referenceViewService.findByParameter("credit_order"));

        return "/output/report/paymentScheduleViewList";

    }
}
