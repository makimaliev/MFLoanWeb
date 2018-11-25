package kg.gov.mf.loan.web.controller.admin.sys;

import kg.gov.mf.loan.manage.service.orderterm.CurrencyRateService;
import kg.gov.mf.loan.web.fetchModels.CurrencyRateMetaModel;
import kg.gov.mf.loan.web.fetchModels.CurrencyRateModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestCurrencyRateController {

    @Autowired
    CurrencyRateService currencyRateService;

    @PersistenceContext
    private EntityManager entityManager;

    private String fromDateQuery="";
    private String toDateQuery="";
    private String searchQuery="";
    private String typeQuery="";

    @PostMapping("/makeNull")
    public void setAllToZero(@RequestParam (value = "datas")String datas){
        if(datas.equals("off")){
            this.fromDateQuery="";
            this.toDateQuery="";
            this.searchQuery="";
            this.typeQuery="";
        }
    }

    @PostMapping("/fromDateFilter")
    public void setFromDateQuery(@RequestParam (value = "fromDate")String fromDate) {
        if(fromDate.equals("") &&fromDate.equals(" ")){
            this.fromDateQuery="";
        }
        else{
            String result=fromDate.substring(6,10)+"-"+fromDate.substring(3,5)+"-"+fromDate.substring(0,2);
            this.fromDateQuery="and c.date>=\""+result+"\"\n";
        }
    }
    @PostMapping("/toDateFilter")
    public void setToDateQuery(@RequestParam (value = "toDate")String toDate) {
        if(toDate.equals("") &&toDate.equals(" ")){
            this.fromDateQuery="";
        }
        else{
            String result=toDate.substring(6,10)+"-"+toDate.substring(3,5)+"-"+toDate.substring(0,2);
            this.toDateQuery="and c.date<=\""+result+"\"\n";
        }
    }
    @PostMapping("/search")
    public void setSearchQuery(@RequestParam (value = "search")String search) {
        if(search.equals("")){
            this.searchQuery="";
        }
        else {
            this.searchQuery="and ((c.date like \"%"+search+"%\") or (c.rate like \"%"+search+"%\") or (o.name like \"%"+search+"%\"))\n";
        }
    }
    @PostMapping("/type")
    public void setTypeQuery(@RequestParam (value = "type")String type) {
        String query="";
        if(type.equals("10")||type.equals("")){
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



    @PostMapping("/currencyRates")
    public CurrencyRateMetaModel getAllCurrencyRates(@RequestParam Map<String, String > datatable){
        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;
        List<CurrencyRateModel> currencyRates;
        String baseQuery="Select c.id as i, c.date as d, c.rate as r, o.name as t \n" +
                "from currency_rate c, orderTermCurrency o \n" +
                "where c.currency_type_id=o.id\n" +
                this.fromDateQuery+
                this.toDateQuery+
                this.searchQuery+
                this.typeQuery+
                "order by " + sortField + " " + sortStr + " LIMIT " + offset + "," + perPage;
        String countQuery="Select count(1) \n" +
                "from currency_rate c, orderTermCurrency o \n" +
                "where c.currency_type_id=o.id\n" +
                this.fromDateQuery+
                this.toDateQuery+
                this.searchQuery+
                this.typeQuery;



        CurrencyRateMetaModel metaModel=new CurrencyRateMetaModel();
        Query query=entityManager.createNativeQuery(baseQuery,CurrencyRateModel.class);
        currencyRates=query.getResultList();

        BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);

        Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
        metaModel.setMeta(meta);

        metaModel.setData(currencyRates);

        return metaModel;

    }

//    @GetMapping("/currencyRates")
//    public List<CurrencyRate> findAllBySpecification(@RequestParam(value = "search") String search) {
//        CurrencyRateSpecificationBuilder builder = new CurrencyRateSpecificationBuilder();
//        Pattern pattern = Pattern.compile("\\w+[^] \\w[^]\\W[^]\\w[^]\\W");
//        Matcher matcher = pattern.matcher(search + ",");
//        while (matcher.find()) {
//            builder.with(
//                    matcher.group(1),
//                    matcher.group(2));
//        }
//
//        Specification<CurrencyRate> spec = builder.build();
//        return dao.findAll();
//    }

}
