package kg.gov.mf.loan.web.controller.classification;

import kg.gov.mf.loan.web.fetchModels.ClassificationForm;
import kg.gov.mf.loan.web.fetchModels.FieldProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ClassificationController {

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = { "/classify/list" }, method = RequestMethod.GET)
    public String listClassifications(ModelMap model)
    {
        //showFields();
        return "/classify/list";
    }

    @RequestMapping(value="/classify/{clId}/save", method=RequestMethod.GET)
    public String formClassification(ModelMap model, @PathVariable("clId")Long clId)
    {
        model.put("classificationForm", new ClassificationForm());
        getViews(model);
        return "/classify/save";
    }

    @RequestMapping(value = "/classify/fields", method = RequestMethod.GET)
    public @ResponseBody
    List<FieldProperty> findFields(
            @RequestParam(value = "viewName", required = true) String viewName) {
        return getFields(viewName);
    }

    private void getViews(ModelMap model)
    {
        String baseQuery = "SELECT TABLE_NAME\n" +
                "        FROM information_schema.views\n" +
                "        WHERE TABLE_SCHEMA = 'mfloan'";

        List<String> views = entityManager.createNativeQuery(baseQuery).getResultList();

        model.put("viewNames", views);

        List<String> outputs = new ArrayList<>();

        for (String view: views)
        {
            String output = "";
            String[] parts = view.split("_");
            int pSize = parts.length;
            for (int i = 0; i < pSize - 1; i++) {
                output = output + " " + parts[i].substring(0,1).toUpperCase() + parts[i].substring(1);
            }

            outputs.add(output);
        }

        model.put("viewOutputs", outputs);
    }

    private List<FieldProperty> getFields(String viewName)
    {
        String baseQuery = "desc " + viewName;

        Query query = entityManager.createNativeQuery(baseQuery);

        List<Object[]> fields = query.getResultList();

        List<FieldProperty> fieldsOutput = new ArrayList<>();

        for (Object field[]: fields)
        {
            fieldsOutput.add(new FieldProperty(field[0] + "", field[1] + ""));
        }

        return fieldsOutput;
    }
}