package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SupervisorPlanViewController {

    @Autowired
    RegionService regionService;

    @Autowired
    DistrictService districtService;


    @RequestMapping("/supervisorPlanView")
    public String getList(ModelMap model){

        model.addAttribute("regions",regionService.findAll());
        model.addAttribute("districts",districtService.findAll());

        return "output/report/supervisorPlanViewList";
    }
}
