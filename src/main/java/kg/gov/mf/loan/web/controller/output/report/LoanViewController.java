package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.admin.org.service.DistrictService;
import kg.gov.mf.loan.admin.org.service.RegionService;
import kg.gov.mf.loan.manage.service.loan.LoanFinGroupService;
import kg.gov.mf.loan.manage.service.loan.LoanTypeService;
import kg.gov.mf.loan.output.report.model.GroupType;
import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.model.ReferenceView;
import kg.gov.mf.loan.output.report.service.ContentParameterService;
import kg.gov.mf.loan.output.report.service.GroupTypeService;
import kg.gov.mf.loan.output.report.service.MessageByLocaleService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.output.report.utils.ReportTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static java.lang.Character.isDigit;


@Controller
public class LoanViewController {


    @Autowired
    ReferenceViewService referenceViewService;

    @Autowired
    MessageByLocaleService messageByLocaleService;

    @Autowired
    GroupTypeService groupTypeService;

    @Autowired
    ContentParameterService contentParameterService;

    @Autowired
    RegionService regionService;

    @Autowired
    DistrictService districtService;

    @Autowired
    LoanFinGroupService loanFinGroupService;

    @Autowired
    LoanTypeService loanTypeService;



    LinkedHashMap<String,String> selected=new LinkedHashMap<>();

    ArrayList<String> myList=new ArrayList<>();
    List<String> selectedFieldNames=new ArrayList<>();
    List<String> selectedFieldNamesWithId=new ArrayList<>();
    List<String> selectedFieldNamesTranslated=new ArrayList<>();
    List<String> allField=new ArrayList<>();
    List<String> allFieldTranslated=new ArrayList<>();
//    List<HashMap<Object,Object>> resultListOfReferenceView=new ArrayList<>();
    HashMap<String,HashMap<Object,Object>> resultListOfReferenceView =new HashMap<>();

    @PostMapping(value = "/loanView/selectedOnes")
    @ResponseBody
    public void sendSelectedItems(@RequestParam(value = "groupTypeId") String groupTypeId ,
                                  @RequestParam(value = "fieldName") String fieldName,
                                  @RequestParam(value = "listTypes") List<String> listTypes, Model model ){
        String myIds="";
        for(int i=0;i<listTypes.size();i++){
            String s="";
            for(int j=0;j<listTypes.get(i).length();j++){
                if(isDigit((listTypes.get(i).charAt(j)))){
                    s=s+(listTypes.get(i).charAt(j));
                }
                else{
                    if(!s.equals("")){
                        myIds=myIds+s+",";
                        s="";
                    }
                }
            }
        }
        System.out.println("=================================================================================");
        System.out.println(fieldName);
        System.out.println(myIds);
        System.out.println("=================================================================================");
        selected.put("?"+fieldName,myIds+"?");
        if (!groupTypeId.equals("")){
            Long longGroupTypeId=Long.valueOf(groupTypeId);
            showSearchMultiSelect(longGroupTypeId,model);
        }
    }
    @PostMapping("/loanView/selectedsClear")
    @ResponseBody
    public void clearSelected(@RequestParam(value = "end") String end){

        this.selected.clear();
    }

    @RequestMapping(value = {"/loanView","/loanView/list","/manage/debtor/list"},method = RequestMethod.GET)
    public String allLoanViews(Model model){
        for (String i:selectedFieldNamesWithId){
            String resultOfContentParameter=this.contentParameterService.findByFieldName(i);
            HashMap<Object,Object> resultOfReferenceView=this.referenceViewService.getByListType(resultOfContentParameter);
            if(!resultListOfReferenceView.keySet().contains(i)){
                this.resultListOfReferenceView.put(i,resultOfReferenceView);
            }
        }
        boolean b=(selectedFieldNames.size()==0);
        selectedFieldNamesTranslated.clear();
        if(b){
            String a=messageByLocaleService.getMessage("v_debtor_name");
            selectedFieldNamesTranslated.add(a);
            selectedFieldNames.add("v_debtor_name");
            a=messageByLocaleService.getMessage("v_loan_amount");
            selectedFieldNames.add("v_loan_amount");
            selectedFieldNamesTranslated.add(a);
            a=messageByLocaleService.getMessage("v_loan_reg_number");
            selectedFieldNames.add("v_loan_reg_number");
            selectedFieldNamesTranslated.add(a);
            a=messageByLocaleService.getMessage("v_loan_reg_date");
            selectedFieldNames.add("v_loan_reg_date");
            selectedFieldNamesTranslated.add(a);
        }
        else{
            for (String s:selectedFieldNames) {
                String asa=messageByLocaleService.getMessage(s);
                if(!selectedFieldNamesTranslated.contains(asa)){
                    selectedFieldNamesTranslated.add(asa);
                }
            }
        }
//        List<LoanFinGroup> finGroups = loanFinGroupService.list();
//        model.addAttribute("finGroups", finGroups);

        List<ReferenceView> loanTypes=referenceViewService.findByParameter("loan_type");
        model.addAttribute("loanTypes",loanTypes);

        List<ReferenceView> supervisors=referenceViewService.findByParameter("supervisor");
        model.addAttribute("supervisors",supervisors);

        List<ReferenceView> finGroups=referenceViewService.findByParameter("fin_group");
        model.addAttribute("finGroups",finGroups);

        List<ReferenceView> districts=referenceViewService.findByParameter("district");
        model.addAttribute("districts",districts);

        List<ReferenceView> currencies=referenceViewService.findByParameter("currency_type");
        model.addAttribute("currencies",currencies);


        model.addAttribute("selectedFields",selectedFieldNames);
        model.addAttribute("selectedFieldsTranslated",selectedFieldNamesTranslated);
        model.addAttribute("selectedFieldNamesWithId",resultListOfReferenceView);
        model.addAttribute("regions",regionService.findAll());

        return "output/report/loanViewList";
    }

    @RequestMapping("/loanView/search")
    public String searchModal(Model model){
        model.addAttribute("groupTypes", this.groupTypeService.findAll());
        return "output/report/loanViewSearchForm";
    }
    @RequestMapping("/loanView/columnChoose")
    public String columnChooseModal(Model model)throws SecurityException{
        List<String> allFieldNames= new ArrayList<>();
        List<String> allFieldNamesTranslated= new ArrayList<>();
        Field[] allFields = LoanView.class.getDeclaredFields();
        for (Field field : allFields) {
            String fieldName=field.toString().split("\\.")[field.toString().split("\\.").length-1];
            if(!fieldName.equals("serialVersionUID")&& !fieldName.equals("v_loan_id")){
                String a=messageByLocaleService.getMessage(fieldName);
                allFieldNamesTranslated.add(a);
                allFieldNames.add(fieldName);
            }
        }
        Field[] superFields=LoanView.class.getSuperclass().getDeclaredFields();
        for (Field field : superFields) {
            String fieldName=field.toString().split("\\.")[field.toString().split("\\.").length-1];
            if(!fieldName.equals("serialVersionUID")){
                String a=messageByLocaleService.getMessage(fieldName);
                allFieldNamesTranslated.add(a);
                allFieldNames.add(fieldName);

            }
        }
        allField=allFieldNames;
        allFieldTranslated=allFieldNamesTranslated;
        model.addAttribute("variables",allFieldNames);
        model.addAttribute("variablesTranslated",allFieldNamesTranslated  );
        return "output/report/loanViewColumnChooseForm";
    }
    @PostMapping("/loanView/selectedFieldNames")
    @ResponseBody
    public void setSelectedFieldNames(@RequestParam(value = "fieldNames") String fieldNames){
        if(!selectedFieldNamesTranslated.contains(fieldNames)){
            int inde=allFieldTranslated.indexOf(fieldNames);
            String field=allField.get(inde);
            this.selectedFieldNames.add(field);
            this.selectedFieldNamesWithId.add(field);
            this.selectedFieldNamesTranslated.add(fieldNames);
        }
    }

    @PostMapping("/loanView/selectedFieldNames/delete")
    @ResponseBody
    public void deleteSelectedFieldNames(@RequestParam(value = "fieldNames") String fieldNames){
        int inde=allFieldTranslated.indexOf(fieldNames);
        String field=allField.get(inde);
        this.selectedFieldNames.remove(field);
        this.selectedFieldNamesWithId.remove(field);
        this.selectedFieldNamesTranslated.remove(fieldNames);
        if(this.resultListOfReferenceView.containsKey(field)){
            this.resultListOfReferenceView.remove(field);
        }
    }
    @PostMapping("/loanView/{groupTypeId}/view")
    public String showSearchMultiSelect(@PathVariable("groupTypeId") long groupTypeId, Model model){
        ReportTool reportTool=new ReportTool();
        ArrayList listTypes=new ArrayList();
        GroupType groupType=groupTypeService.findById(groupTypeId);
        String groupTypeName= reportTool.getMapNameOfGroupType(groupType);
        List<ReferenceView> som =referenceViewService.findByParameter(groupTypeName);
        for(ReferenceView s :som){
            listTypes.add(s.getId());
        }
        model.addAttribute("selecteds",selected);
        model.addAttribute("groupTypeId",groupTypeId);
        model.addAttribute("fieldName",groupType.getField_name());
        model.addAttribute("listTypes",som);
        model.addAttribute("listTypeArray",listTypes);
        return "output/report/loanViewSearchFormView";
    }
}
