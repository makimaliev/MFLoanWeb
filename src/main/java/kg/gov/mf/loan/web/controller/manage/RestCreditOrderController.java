package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.repository.order.CreditOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestCreditOrderController {

    @Autowired
    CreditOrderRepository creditOrderRepository;

    @GetMapping("/orders")
    public Page<CreditOrder> getAllOrders(Pageable pageable) {
        return creditOrderRepository.findAll(pageable);
    }
}
