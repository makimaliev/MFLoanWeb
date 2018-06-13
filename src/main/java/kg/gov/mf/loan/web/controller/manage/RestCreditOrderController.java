package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.manage.repository.order.CreditOrderRepository;
import kg.gov.mf.loan.web.fetchModels.CreditOrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestCreditOrderController {

    @Autowired
    CreditOrderRepository creditOrderRepository;

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/orders/search")
    public String[] getOrdersByRegNumber(@RequestParam(value = "q") String q) {
        List<CreditOrder> orders = creditOrderRepository.findByRegNumberContains(q);
        String[] sOrders = new String[orders.size()];
        int i = 0;
        for (CreditOrder order:orders
                ) {
            sOrders[i++] = "[" + order.getId() + "] "+ order.getRegNumber();
        }

        return sOrders;
    }

    @PostMapping("/orders")
    public List<CreditOrderModel> getOrders() {

        String baseQuery = "select co.id as id, co.regNumber as regNumber, co.regDate as regDate, co.description as description, st.name as creditOrderState, tp.name as creditOrderType " +
                "from creditOrder co, creditOrderState st, creditOrderType tp where co.creditOrderStateId = st.id and co.creditOrderTypeId = tp.id";

        Query query = entityManager.createNativeQuery(baseQuery, CreditOrderModel.class);

        List<CreditOrderModel> orders = query.getResultList();
        return orders;
    }
}
