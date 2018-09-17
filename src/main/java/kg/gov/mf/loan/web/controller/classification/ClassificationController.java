package kg.gov.mf.loan.web.controller.classification;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Controller
public class ClassificationController {

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = { "/classify/list" }, method = RequestMethod.GET)
    public String listJobs(ModelMap model)
    {
        showFields();
        return "/classify/list";
    }

    private void showFields()
    {
        String baseQuery = "desc loan";

        Query query = entityManager.createNativeQuery(baseQuery);

        List<Object[]> fields = query.getResultList();

        for (Object field[]: fields)
        {
            System.out.println(field[0]);
            System.out.println(field[1]);
        }

    }
}