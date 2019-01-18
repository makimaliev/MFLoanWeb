package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.output.report.model.LoanView;
import kg.gov.mf.loan.output.report.service.ContentParameterService;
import kg.gov.mf.loan.output.report.service.LoanViewService;
import kg.gov.mf.loan.output.report.service.ReferenceViewService;
import kg.gov.mf.loan.web.fetchModels.LoanViewMetaModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.*;

import kg.gov.mf.loan.web.util.Meta;

import static java.lang.Character.isDigit;


@RestController
@RequestMapping("/api")
public class RestLoanViewController {

    @Autowired
    LoanViewService loanViewService;

    @Autowired
    ReferenceViewService referenceViewService;


    public static LinkedHashMap<String,List<String>> parameter=new LinkedHashMap<>();
    String s="";


    @PostMapping("/loanView/searchData")
    public void setSearchValue(@RequestParam (value = "fieldName") String fieldName,@RequestParam(value = "listTypes") List<String> listTypes){
        List<String> myList=new ArrayList<>();
        if(listTypes.size()!=0){
            for(int i=0;i<listTypes.size();i++){
                String s="";
                for(int j=0;j<listTypes.get(i).length();j++){
                    if(isDigit((listTypes.get(i).charAt(j)))){
                        s=s+(listTypes.get(i).charAt(j));
                    }
                    else{
                        if(!s.equals("")){
                            myList.add(s);
                            s="";
                        }
                    }
                }
            }


            if(!myList.isEmpty()){

                parameter.put("r=in"+fieldName,myList);
            }
        }
    }

    @PostMapping("/loanViewSearch/start")
    public void setS(@RequestParam(value = "startSearch") String startSearch){
        this.s=startSearch;
    }
    @PostMapping("/clearParameter")
    public void clearParameter(){
        parameter.clear();
    }
    @PostMapping("/loanViews")
    public List<LoanView> getAllLoanViews(@RequestParam Map<String,String> datatable){

        LinkedHashMap<String,List<String>> parameters=new LinkedHashMap<>();
        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        boolean searchByDebtorName= datatable.containsKey("datatable[query][debtorName]");
        if(searchByDebtorName){
            String debtorName = datatable.get("datatable[query][debtorName]");
            List<String> searchWord= Arrays.asList(debtorName);
            parameters.put("r=ycv_debtor_name",searchWord);
        }

        boolean searchByRegNumber= datatable.containsKey("datatable[query][regNumber]");
        if(searchByRegNumber){
            String regNum = datatable.get("datatable[query][regNumber]");
            List<String> searchWord= Arrays.asList(regNum);
            parameters.put("r=ycv_loan_reg_number",searchWord);
        }

        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        if (searchByDistrict){
            String districtStr = datatable.get("datatable[query][districtId]");
            List<String> districtIds=Arrays.asList(districtStr);
            parameters.put("r=inv_debtor_district_id",districtIds);
        }

        boolean searchByRegion = datatable.containsKey("datatable[query][regionId]");
        if (searchByRegion){
            String regionStr = datatable.get("datatable[query][regionId]");
            List<String> regionIds=Arrays.asList(regionStr );
            parameters.put("r=inv_debtor_region_id",regionIds);
        }

        boolean getFromDate= datatable.containsKey("datatable[query][fromDater]");
        if (getFromDate){
            String fromDateStr = datatable.get("datatable[query][fromDater]");
            List<String> froms=Arrays.asList(fromDateStr);
            parameters.put("r=aov_loan_reg_date",froms);
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = datatable.get("datatable[query][toDater]");
            List<String> tos=Arrays.asList(toDateStr);
            parameters.put("r=bov_loan_reg_date",tos);
        }

        boolean searchByFinGroup = datatable.containsKey("datatable[query][finGroupId]");
        if (searchByFinGroup){
            String finGroupStr = datatable.get("datatable[query][finGroupId]");
            List<String> finGroupIds=Arrays.asList(finGroupStr);
            parameters.put("r=inv_loan_fin_group_id",finGroupIds);
        }

        boolean searchByCreditNumber= datatable.containsKey("datatable[query][creditNumber]");
        if(searchByCreditNumber){
            String creditNumber = datatable.get("datatable[query][creditNumber]");
            List<String> searchWord= Arrays.asList(creditNumber);
            parameters.put("r=ycv_credit_order_reg_number",searchWord);
        }

        boolean searchByLoanType = datatable.containsKey("datatable[query][loanTypeId]");
        if (searchByLoanType ){
            String loanTypeStr = datatable.get("datatable[query][loanTypeId]");
            List<String> loanTypeIds=Arrays.asList(loanTypeStr);
            parameters.put("r=inv_loan_type_id",loanTypeIds);
        }

        boolean searchBySupervisor= datatable.containsKey("datatable[query][supervisorId]");
        if (searchBySupervisor){
            String supervisorStr = datatable.get("datatable[query][supervisorId]");
            List<String> supervisorIds=Arrays.asList(supervisorStr);
            parameters.put("r=inv_loan_supervisor_id",supervisorIds);
        }


        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;


        System.out.println("==========================================================================================");
        System.out.println(parameters.keySet());
        System.out.println(parameters.values());
        System.out.println("==========================================================================================");

//        if(this.s.equals("start")){

            List<LoanView> loanViewList=loanViewService.findByParameter(parameters,perPage,offset,sortStr,sortField);
//            long loanViews=loanViewService.getCount(parameters);
//
//            BigInteger count=BigInteger.valueOf(loanViews);
//
//
//            LoanViewMetaModel loanViewMetaModel=new LoanViewMetaModel();
//
//            Meta meta= new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
//
//            loanViewMetaModel.setMeta(meta);
//            loanViewMetaModel.setData(loanViewList);
            return loanViewList;
//        }
        /*else{
            List<LoanView> loanViewList=loanViewService.findByParameter(parameters,perPage,offset,sortStr,sortField);
            long loanViews=loanViewService.getCount(parameters);
            BigInteger count=BigInteger.valueOf(loanViews);


            LoanViewMetaModel loanViewMetaModel=new LoanViewMetaModel();

            Meta meta= new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

            loanViewMetaModel.setMeta(meta);
            loanViewMetaModel.setData(loanViewList);
            return loanViewMetaModel;
        }*/



    }
}
