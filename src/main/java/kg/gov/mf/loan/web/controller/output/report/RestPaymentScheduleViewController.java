package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.output.report.model.PaymentScheduleView;
import kg.gov.mf.loan.output.report.service.PaymentScheduleViewService;
import kg.gov.mf.loan.web.fetchModels.PaymentScheduleViewMetaModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestPaymentScheduleViewController {


    @Autowired
    PaymentScheduleViewService paymentScheduleViewService;

    @PostMapping("/paymentScheduleViews")
    public PaymentScheduleViewMetaModel getAll(@RequestParam Map<String,String> datatable){
        LinkedHashMap<String,List<String>> parameter=new LinkedHashMap<>();

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        boolean searchByDebtorName=datatable.containsKey("datatable[query][debtorName]");
        if(searchByDebtorName){
            List<String> searchWord= Arrays.asList(datatable.get("datatable[query][debtorName]"));
            parameter.put("r=ycv_debtor_name",searchWord);
        }

//        boolean searchByCreditOrderId=datatable.containsKey("datatable[query][creditOrder]");
//        if(searchByCreditOrderId){
//            List<String> searchWord= Arrays.asList(datatable.get("datatable[query][creditOrder]"));
//            parameter.put("r=inv_credit_order_type_id",searchWord);
//        }

        boolean getFromDate= datatable.containsKey("datatable[query][fromDater]");
        if (getFromDate){
            String fromDateStr = datatable.get("datatable[query][fromDater]");
            List<String> froms=Arrays.asList(fromDateStr);
            parameter.put("r=aov_ps_expected_date",froms);
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = datatable.get("datatable[query][toDater]");
            List<String> tos=Arrays.asList(toDateStr);
            parameter.put("r=bov_ps_expected_date",tos);
        }

        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        if (searchByDistrict){
            String districtStr = datatable.get("datatable[query][districtId]");
            List<String> districtIds=Arrays.asList(districtStr);
            parameter.put("r=inv_debtor_district_id",districtIds);
        }

        boolean searchByRegion = datatable.containsKey("datatable[query][regionId]");
        if (searchByRegion){
            String regionStr = datatable.get("datatable[query][regionId]");
            List<String> regionIds=Arrays.asList(regionStr );
            parameter.put("r=inv_debtor_region_id",regionIds);
        }

        PaymentScheduleViewMetaModel metaModel=new PaymentScheduleViewMetaModel();

        BigInteger count=BigInteger.valueOf(100);
        Meta meta=new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, "desc", "v_ps_id");

        List<PaymentScheduleView> all=this.paymentScheduleViewService.findByParameter(parameter,offset,10);
        if(all.size()>perPage){
            metaModel.setData(all.subList(0,perPage));
        }
        else{
            metaModel.setData(all);
        }


        metaModel.setMeta(meta);

        return metaModel;
    }

}
