package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.output.report.service.PaymentViewService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
@RequestMapping("/paymentview")
public class PaymentViewController {


    @RequestMapping(value = {"/","/list"}, method = RequestMethod.GET)
    private String getAll(ModelMap model){


        model.addAttribute("loggedinuser", Utils.getPrincipal());


        return "/output/report/paymentViewList";
    }
}
