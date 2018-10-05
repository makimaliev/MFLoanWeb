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
    public LoanViewMetaModel getAllLoanViews(@RequestParam Map<String,String> datatable){
        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;


        System.out.println("==========================================================================================");
        System.out.println(parameter.keySet());
        System.out.println(parameter.values());
        System.out.println("==========================================================================================");

        if(this.s.equals("start")){

            List<LoanView> loanViewList=loanViewService.findByParameter(parameter,perPage,offset,sortStr,sortField);
            int loanViews=loanViewService.findByParamete(parameter);

            BigInteger count=BigInteger.valueOf(loanViews);


            LoanViewMetaModel loanViewMetaModel=new LoanViewMetaModel();

            Meta meta= new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

            loanViewMetaModel.setMeta(meta);
            loanViewMetaModel.setData(loanViewList);
            return loanViewMetaModel;
        }
        else{
            LinkedHashMap<String,List<String>> parameters=new LinkedHashMap<>();
            List<LoanView> loanViewList=loanViewService.findByParameter(parameter,perPage,offset,sortStr,sortField);
            int loanViews=loanViewService.findByParamete(parameters);
            BigInteger count=BigInteger.valueOf(loanViews);


            LoanViewMetaModel loanViewMetaModel=new LoanViewMetaModel();

            Meta meta= new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

            loanViewMetaModel.setMeta(meta);
            loanViewMetaModel.setData(loanViewList);
            return loanViewMetaModel;
        }



    }
}
