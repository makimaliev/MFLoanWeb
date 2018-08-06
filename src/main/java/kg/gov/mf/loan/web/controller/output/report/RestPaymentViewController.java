package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.output.report.model.PaymentView;
import kg.gov.mf.loan.output.report.service.PaymentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestPaymentViewController {
    @Autowired
    PaymentViewService paymentViewService;


    @PostMapping("/paymentViews")
    private List<PaymentView> allPaymentViews(){

        List<PaymentView> all=paymentViewService.findAll();
        return all;
    }
}
