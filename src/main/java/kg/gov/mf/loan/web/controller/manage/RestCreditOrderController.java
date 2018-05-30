package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.repository.order.CreditOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestCreditOrderController {

    @Autowired
    CreditOrderRepository creditOrderRepository;

    @GetMapping("/orders")
    public Page<CreditOrder> getAllOrders(Pageable pageable) {
        return creditOrderRepository.findAll(pageable);
    }

    @GetMapping("/orders/search")
    public String[] getOrdersByRegNumber(@RequestParam(value = "q") String q) {
        List<CreditOrder> orders = creditOrderRepository.findByRegNumberContains(q);
        String[] sOrders = new String[orders.size()];
        int i = 0;
        for (CreditOrder order:orders
                ) {
            sOrders[i++] = order.getRegNumber();
        }

        return sOrders;
    }
}
