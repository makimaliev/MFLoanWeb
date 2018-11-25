package kg.gov.mf.loan.web.controller.admin.sys;


import kg.gov.mf.loan.web.fetchModels.FloatingRateMetaModel;
import kg.gov.mf.loan.web.fetchModels.FloatingRateModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class RestFloatingRateController {


    @PersistenceContext
    private EntityManager entityManager;

    private String fromDateQuery="";
    private String toDateQuery="";
    private String searchQuery="";
    private String typeQuery="";

    @PostMapping("/makeZero")
    public void setAllToZero(@RequestParam (value = "datas")String datas){
        if(datas.equals("off")){
            this.fromDateQuery="";
            this.toDateQuery="";
            this.searchQuery="";
            this.typeQuery="";
        }
    }

    @PostMapping("/fromFilter")
    public void setFromDateQuery(@RequestParam (value = "fromDate") String fromDate){
        if(fromDate.equals("") &&fromDate.equals(" ")){
            this.fromDateQuery="";
        }
        else{
            String result=fromDate.substring(6,10)+"-"+fromDate.substring(3,5)+"-"+fromDate.substring(0,2);
            this.fromDateQuery="and f.date>=\""+result+"\"\n";
        }
    }
    @PostMapping("/toFilter")
    public void setToDateQuery(@RequestParam (value = "toDate")String toDate) {
        if(toDate.equals("")&&toDate.equals(" ")){
            this.toDateQuery="";
        }
        else{
            String result=toDate.substring(6,10)+"-"+toDate.substring(3,5)+"-"+toDate.substring(0,2);
            this.toDateQuery="and f.date<=\""+result+"\" \n";
        }
    }
    @PostMapping("/find")
    public void setSearchQuery(@RequestParam (value = "search")String search) {
        if(search.equals("")){
            this.searchQuery="";
        }
        else {
            this.searchQuery="and ((f.date like \"%"+search+"%\") or (f.rate like \"%"+search+"%\") or (o.name like \"%"+search+"%\"))\n";
        }
    }
    @PostMapping("/getType")
    public void setTypeQuery(@RequestParam (value = "type")String type) {
        String query="";
        if(type.equals("")){
            this.typeQuery="";
        }
        else{
            String[] splittedType=type.split(",");
            for(int i=0;i<splittedType.length-1;i++){
                if(splittedType[i].equals("")){
                    this.typeQuery="";
                    return ;
                }
                else if(i!=splittedType.length-2){
                    query=query+"(o.id=\""+splittedType[i]+"\") or";
                }
                else {
                    query=query+"(o.id=\""+splittedType[i]+"\")";
                }
            }
            this.typeQuery="and ("+query+")\n";
            System.out.println("==========================================================");
            System.out.println(typeQuery);
            System.out.println("==========================================================");
//            this.typeQuery="and o.name=\""+type+"\" \n";
        }
    }

    @PostMapping("/floatingRates")
    public FloatingRateMetaModel getAllFloatingRates(@RequestParam Map<String,String> datatable){
        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        List<FloatingRateModel> floatingRateModelList;

        String baseQuery="Select f.id as i, f.date as d, f.rate as r, o.name as t \n" +
                "from floating_rate f, orderTermFloatingRateType o \n" +
                "where f.floating_rate_type_id=o.id\n" +
                this.fromDateQuery+
                this.toDateQuery+
                this.searchQuery+
                this.typeQuery+
                "order by " + sortField + " " + sortStr + " LIMIT " + offset + "," + perPage;
        String countQuery="Select count(1) \n" +
                "from floating_rate f, orderTermFloatingRateType o \n" +
                "where f.floating_rate_type_id=o.id\n" +
                this.fromDateQuery+
                this.toDateQuery+
                this.searchQuery+
                this.typeQuery;

        Query query=entityManager.createNativeQuery(baseQuery,FloatingRateModel.class);
        floatingRateModelList=query.getResultList();

        BigInteger count=(BigInteger) entityManager.createNativeQuery(countQuery).getResultList().get(0);

        FloatingRateMetaModel floatingRateMetaModel=new FloatingRateMetaModel();
        Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
        floatingRateMetaModel.setMeta(meta);
        floatingRateMetaModel.setData(floatingRateModelList);

        return floatingRateMetaModel;


    }
}
