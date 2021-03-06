package kg.gov.mf.loan.web.controller.classification;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.gov.mf.loan.manage.model.classification.*;
import kg.gov.mf.loan.manage.repository.classification.*;
import kg.gov.mf.loan.output.report.model.*;
import kg.gov.mf.loan.web.fetchModels.ClassificationForm;
import kg.gov.mf.loan.web.fetchModels.FieldProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ClassificationController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    ClassificationTableRepository classificationTableRepository;

    @Autowired
    ClassificationFieldRepository classificationFieldRepository;

    @Autowired
    ClassificationJoinRepository classificationJoinRepository;

    @Autowired
    ClassificationRepository classificationRepository;

    @Autowired
    ClassificationResultRepository crRepository;

    @Autowired
    ClassificatorRepository classificatorRepository;

    @RequestMapping(value = {"/classify/table/list" }, method = RequestMethod.GET)
    public String listClassifyTables(ModelMap model) {

        model.addAttribute("tables", classificationTableRepository.findAll());
        return "/classify/table/list";
    }

    @RequestMapping(value="/classify/table/{tableId}/view", method=RequestMethod.GET)
    public String viewClassifyTable(ModelMap model, @PathVariable("tableId")Long tableId)
    {

        ClassificationTable t = classificationTableRepository.findById(tableId);

        model.addAttribute("table", t);
        model.addAttribute("action", "view");
        getViews(model);
        model.addAttribute("fields", getFieldNames(t.getTableActualName()));

        return "/classify/table/view";
    }

    @RequestMapping(value="/classify/table/{tableId}/save", method=RequestMethod.GET)
    public String formClassifyTable(ModelMap model, @PathVariable("tableId")Long tableId)
    {
        if(tableId == 0)
        {
            model.addAttribute("table", new ClassificationTable());
            model.addAttribute("action", "add");
            getViews(model);
        }

        if(tableId > 0)
        {
            model.addAttribute("table", classificationTableRepository.findById(tableId));
            model.addAttribute("action", "edit");
        }

        return "/classify/table/view";
    }

    @RequestMapping(value="/classify/table/save", method=RequestMethod.POST)
    public String saveTable(ClassificationTable table)
    {
        if(table.getId()!=null && table.getId() > 0)
        {
            ClassificationTable orig = classificationTableRepository.findById(table.getId());
            table.setTableActualName(orig.getTableActualName());
        }
        classificationTableRepository.save(table);
        return "redirect:" + "/classify/table/list";
    }

    @RequestMapping(value="/classify/table/{tableId}/delete", method=RequestMethod.GET)
    public String deleteTable(@PathVariable("tableId")Long tableId) {
        if(tableId > 0)
            classificationTableRepository.delete(classificationTableRepository.findById(tableId));
        return "redirect:" + "/classify/table/list";
    }

    @RequestMapping(value = {"/classify/join/list" }, method = RequestMethod.GET)
    public String listClassifyJoins(ModelMap model) {

        model.addAttribute("joins", classificationJoinRepository.findAll());
        return "/classify/join/list";
    }

    @RequestMapping(value="/classify/join/{joinId}/view", method=RequestMethod.GET)
    public String viewClassifyJoin(ModelMap model, @PathVariable("joinId")Long joinId)
    {

        ClassificationJoin j = classificationJoinRepository.findById(joinId);

        model.addAttribute("join", j);
        model.addAttribute("action", "view");
        getViews(model);

        return "/classify/join/view";
    }

    @RequestMapping(value="/classify/join/{joinId}/save", method=RequestMethod.GET)
    public String formClassifyJoin(ModelMap model, @PathVariable("joinId")Long joinId)
    {
        if(joinId == 0)
        {
            model.addAttribute("join", new ClassificationJoin());
            model.addAttribute("action", "add");
            getViews(model);
        }

        if(joinId > 0)
        {
            model.addAttribute("join", classificationJoinRepository.findById(joinId));
            model.addAttribute("action", "edit");
            getViews(model);
        }

        return "/classify/join/view";
    }

    @RequestMapping(value="/classify/join/save", method=RequestMethod.POST)
    public String saveJoin(ClassificationJoin join)
    {
        if(join.getId()!=null && join.getId() > 0)
        {
            /*
            ClassificationTable orig = classificationTableRepository.findById(table.getId());
            table.setTableActualName(orig.getTableActualName());
            */
        }
        classificationJoinRepository.save(join);
        return "redirect:" + "/classify/join/list";
    }

    @RequestMapping(value="/classify/join/{joinId}/delete", method=RequestMethod.GET)
    public String deleteJoin(@PathVariable("joinId")Long joinId) {
        if(joinId > 0)
            classificationJoinRepository.delete(classificationJoinRepository.findById(joinId));
        return "redirect:" + "/classify/join/list";
    }

    @RequestMapping(value="/classify/table/{tableId}/field/list", method=RequestMethod.GET)
    public String listClassifyField(ModelMap model, @PathVariable("tableId")Long tableId)
    {
        model.addAttribute("tableId", tableId);
        List<ClassificationField> fields = classificationFieldRepository.findByTableId(tableId);
        model.addAttribute("fields", fields);

        return "/classify/table/field/list";
    }

    @RequestMapping(value="/classify/table/{tableId}/field/{fieldId}/view", method=RequestMethod.GET)
    public String viewClassifyField(ModelMap model, @PathVariable("tableId")Long tableId, @PathVariable("fieldId")Long fieldId)
    {

        ClassificationTable table = classificationTableRepository.findById(tableId);
        model.put("fieldList", getFieldNames(table.getTableActualName()));
        model.put("tableId", tableId);

        ClassificationField field = classificationFieldRepository.findById(fieldId);

        model.addAttribute("field", field);
        model.addAttribute("action", "view");
        getViews(model);
        getTables(model);

        return "/classify/table/field/view";
    }

    @RequestMapping(value="/classify/table/{tableId}/field/{fieldId}/save", method=RequestMethod.GET)
    public String formClassifyField(ModelMap model, @PathVariable("tableId")Long tableId, @PathVariable("fieldId")Long fieldId)
    {
        ClassificationTable table = classificationTableRepository.findById(tableId);
        model.put("fieldList", getFieldNames(table.getTableActualName()));
        model.put("tableId", tableId);
        getTables(model);

        if(fieldId == 0)
        {
            model.addAttribute("field", new ClassificationField());
            model.addAttribute("action", "add");
        }

        if(fieldId > 0)
        {
            model.addAttribute("field", classificationFieldRepository.findById(fieldId));
            model.addAttribute("action", "edit");
        }

        return "/classify/table/field/view";
    }

    @RequestMapping(value="/classify/table/{tableId}/field/save", method=RequestMethod.POST)
    public String saveField(ClassificationField field, @PathVariable("tableId")Long tableId)
    {
        if(field.getId() != null && field.getId() > 0)
        {
            ClassificationField orig = classificationFieldRepository.findById(field.getId());
            field.setFieldActualName(orig.getFieldActualName());
        }
        ClassificationTable t = classificationTableRepository.findById(tableId);
        field.setTable(t);
        field.setField_type(getFieldType(t.getTableActualName(), field.getFieldActualName()));
        classificationFieldRepository.save(field);
        return "redirect:" + "/classify/table/{tableId}/field/list";
    }

    @RequestMapping(value="/classify/table/{tableId}/field/{fieldId}/delete", method=RequestMethod.GET)
    public String deleteField(@PathVariable("tableId")Long tableId, @PathVariable("fieldId")Long fieldId) {
        if(fieldId > 0)
            classificationFieldRepository.delete(classificationFieldRepository.findById(fieldId));
        return "redirect:" + "/classify/table/{tableId}/field/list";
    }

    @RequestMapping(value = {"/classificator/list" }, method = RequestMethod.GET)
    public String listClassificators(ModelMap model) {

        model.addAttribute("classificators", classificatorRepository.findAll());
        return "/classificator/list";
    }

    @RequestMapping(value="/classificator/{clId}/view", method=RequestMethod.GET)
    public String viewClassificator(ModelMap model, @PathVariable("clId")Long clId)
    {

        Classificator cl = classificatorRepository.findById(clId);

        model.addAttribute("classificator", cl);
        model.addAttribute("action", "view");

        return "/classificator/view";
    }

    @RequestMapping(value="/classificator/{clId}/save", method=RequestMethod.GET)
    public String formClassificator(ModelMap model, @PathVariable("clId")Long clId)
    {
        if(clId == 0)
        {
            model.addAttribute("classificator", new Classificator());
            model.addAttribute("action", "add");
        }

        if(clId > 0)
        {
            model.addAttribute("classificator", classificatorRepository.findById(clId));
            model.addAttribute("action", "edit");
        }

        return "/classificator/view";
    }

    @RequestMapping(value="/classificator/save", method=RequestMethod.POST)
    public String saveClassificator(Classificator classificator)
    {
        classificatorRepository.save(classificator);
        return "redirect:" + "/classificator/list";
    }

    @RequestMapping(value="/classificator/{clId}/delete", method=RequestMethod.GET)
    public String deleteClassificator(@PathVariable("clId")Long clId) {
        if(clId > 0)
            classificatorRepository.delete(classificatorRepository.findById(clId));
        return "redirect:" + "/classificator/list";
    }

    @RequestMapping(value = { "/classificator/{ccId}/classify/list" }, method = RequestMethod.GET)
    public String listClassifications(ModelMap model, @PathVariable("ccId")Long ccId)
    {
        Classificator cc = classificatorRepository.findById(ccId);
        model.addAttribute("classificator",cc);
        Set<Classification> classificationList = cc.getClassifications();
        model.addAttribute("classificationList",classificationList);
        return "/classify/list";
    }

    @RequestMapping(value="/classificator/{ccId}/classify/{clId}/save", method=RequestMethod.GET)
    public String formClassification(ModelMap model, @PathVariable("ccId")Long ccId, @PathVariable("clId")Long clId)
    {
        model.put("classificator", classificatorRepository.findById(ccId));
        model.put("classificationForm", new ClassificationForm());
        model.put("tables", classificationTableRepository.findAll());
        return "/classify/save";
    }

    @RequestMapping(value = "/classify/save", method = RequestMethod.GET)
    public @ResponseBody
    String saveClassification(
            @RequestParam(value = "entityType", required = true) String entityType,
            @RequestParam(value = "entityField", required = true) String entityField,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "query", required = true) String query,
            @RequestParam(value = "ccId", required = true) Long ccId) {

        Classification classification = new Classification();
        classification.setName(name);
        classification.setEntityType(entityType);
        classification.setEntityField(entityField);
        classification.setQuery(query);
        classification.setClassificator(classificatorRepository.findById(ccId));

        classificationRepository.save(classification);

        return "success";
    }

    @RequestMapping(value="/classify/{clId}/delete", method=RequestMethod.GET)
    public String deleteClassification(@PathVariable("clId")Long clId) {
        if(clId > 0)
            classificationRepository.delete(classificationRepository.findById(clId));
        return "redirect:" + "/classify/list";
    }

    /*
    @RequestMapping(value = { "/classify/run" }, method = RequestMethod.GET)
    public String runClassification()
    {
        List<Classification> classificationList = classificationRepository.findAll();

        for (Classification cl: classificationList)
        {
            Query qTemp = entityManager.createNativeQuery(cl.getQuery());

            List<BigInteger> fields = qTemp.getResultList();

            for (BigInteger field: fields)
            {
                Long cId = cl.getId();
                Long entityId = Long.parseLong(field + "");
                ClassificationResult res = new ClassificationResult();
                res.setClassificationId(cId);
                res.setEntityId(entityId);
                crRepository.save(res);
            }
        }

        return "redirect:" + "/classificator/list";
    }
    */

    @RequestMapping(value = "/classify/fieldsByTable", method = RequestMethod.GET)
    public @ResponseBody
    List<ClassificationField> getFieldsByTable(
            @RequestParam(value = "tableId", required = true) Long tableId) {
        List<ClassificationField> result = classificationFieldRepository.findByTableId(tableId);
        return result;
    }

    @RequestMapping(value = "/classify/joinByTables", method = RequestMethod.GET)
    public @ResponseBody
    String getJoinByTables(
            @RequestParam(value = "table1", required = true) String table1, @RequestParam(value = "table2", required = true) String table2) {

        ClassificationJoin join = classificationJoinRepository.findByLeftTableNameAndRightTableName(table1, table2);

        if(join == null)
        {
            join = classificationJoinRepository.findByLeftTableNameAndRightTableName(table2, table1);
        }

        return join.getJoinText();
    }

    @RequestMapping(value = "/classify/getObjectsFromLookupTable", method = RequestMethod.GET)
    public @ResponseBody
    List<ReferenceView> getObjectsFromLookupTable(
            @RequestParam(value = "tableName", required = true) String tableName) throws ClassNotFoundException {

        String baseQuery = "SELECT * FROM reference_view WHERE list_type = '" + tableName + "'";

        Query q2 = entityManager.createNativeQuery(baseQuery);

        List<Object[]> fields = q2.getResultList();

        List<ReferenceView> result = new ArrayList<>();

        for (Object field[]: fields)
        {
            ReferenceView refView = new ReferenceView();
            refView.setId(Long.parseLong(field[1] + ""));
            refView.setList_type(field[0] + "");
            refView.setName(field[2] + "");
            result.add(refView);
        }

        return result;
    }

    @RequestMapping(value = "/classify/fields", method = RequestMethod.GET)
    public @ResponseBody
    List<FieldProperty> findFields(
            @RequestParam(value = "viewName", required = true) String viewName) {
        return getFields(viewName);
    }

    @RequestMapping(value = "/classify/fieldNames", method = RequestMethod.GET)
    public @ResponseBody
    List<String> findFieldNames(
            @RequestParam(value = "viewName", required = true) String viewName) {
        return getFieldNames(viewName);
    }

    @RequestMapping(value = "/classify/calcPercentage", method = RequestMethod.GET)
    public @ResponseBody
    String calculatePercentage(
            @RequestParam(value = "data", required = true)String data) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(data);

        String selectedField = root.path("field").asText();
        String selectedViewName = root.path("viewName").asText();
        String query = root.path("query").asText();

        String baseQuery = "SELECT count(" + selectedField + ") FROM " + selectedViewName;

        Query q = entityManager.createNativeQuery(baseQuery);

        BigInteger countBase = (BigInteger) q.getSingleResult();

        String qModified = query.replaceFirst(selectedField, "COUNT(" + selectedField +")");

        Query q2 = entityManager.createNativeQuery(qModified);

        BigInteger countActual = (BigInteger) q2.getSingleResult();

        BigDecimal decCountBase = new BigDecimal(countBase);
        BigDecimal decCountActual = new BigDecimal(countActual);
        BigDecimal result = decCountActual.divide(decCountBase, MathContext.DECIMAL32).multiply(new BigDecimal("100"));

        return result.toString();
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

    private void getTables(ModelMap model)
    {
        String baseQuery = "SELECT DISTINCT list_type FROM reference_view";

        List<String> tables = entityManager.createNativeQuery(baseQuery).getResultList();

        model.put("tableNames", tables);
    }

    private List<FieldProperty> getFields(String viewName)
    {
        if(viewName.equals("") || viewName == null)
            return null;

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

    private List<String> getFieldNames(String viewName)
    {
        if(viewName.equals("") || viewName == null)
            return null;

        String baseQuery = "desc " + viewName;

        Query query = entityManager.createNativeQuery(baseQuery);

        List<Object[]> fields = query.getResultList();

        List<String> fieldsOutput = new ArrayList<>();

        for (Object field[]: fields)
        {
            fieldsOutput.add(field[0] + "");
        }

        return fieldsOutput;
    }

    private String getFieldType(String viewName, String fieldName)
    {
        String result = null;

        String baseQuery = "desc " + viewName;

        Query query = entityManager.createNativeQuery(baseQuery);

        List<Object[]> fields = query.getResultList();

        for (Object field[]: fields)
        {
            String tFieldName = field[0] + "";
            if(tFieldName.equals(fieldName))
            {
                result = field[1] + "";
                break;
            }
        }

        return result;
    }
}