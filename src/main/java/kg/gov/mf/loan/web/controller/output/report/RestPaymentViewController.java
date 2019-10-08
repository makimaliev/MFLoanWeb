package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.output.report.model.PaymentView;
import kg.gov.mf.loan.output.report.service.PaymentViewService;
import kg.gov.mf.loan.web.fetchModels.PaymentViewMetaModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/api")
public class RestPaymentViewController {
    @Autowired
    PaymentViewService paymentViewService;

    @PostMapping("/paymentViews")
    private PaymentViewMetaModel allPaymentViews(@RequestParam Map<String,String> datatable){

        LinkedHashMap<String,List<String>> parameters=new LinkedHashMap<>();


        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        boolean searchByDebtorName= datatable.containsKey("datatable[query][debtorName]");
        if(searchByDebtorName){
            String debtorName = searchByDebtorName?datatable.get("datatable[query][debtorName]"):null;
            List<String> searchWord=Arrays.asList(debtorName);
            parameters.put("r=ycv_debtor_name",searchWord);
        }

        boolean searchByPaymentNumber= datatable.containsKey("datatable[query][paymentNum]");
        if(searchByPaymentNumber){
            String paymentNumber = searchByPaymentNumber?datatable.get("datatable[query][paymentNum]"):null;
            List<String> searchWord=Arrays.asList(paymentNumber);
            parameters.put("r=ycv_payment_number",searchWord);
        }

        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        if (searchByDistrict){
            String districtStr = searchByDistrict?datatable.get("datatable[query][districtId]"):null;
            List<String> districtIds=Arrays.asList(districtStr);
            parameters.put("r=inv_debtor_district_id",districtIds);
        }

        boolean searchByRegion = datatable.containsKey("datatable[query][regionId]");
        if (searchByRegion){
            String regionStr = searchByRegion?datatable.get("datatable[query][regionId]"):null;
            List<String> regionIds=Arrays.asList(regionStr );
            parameters.put("r=inv_debtor_region_id",regionIds);
        }

        boolean getFromDate= datatable.containsKey("datatable[query][fromDater]");
        if (getFromDate){
            String fromDateStr = getFromDate?datatable.get("datatable[query][fromDater]"):null;
            List<String> froms=Arrays.asList(fromDateStr);
            parameters.put("r=aov_payment_date",froms);
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = getToDate?datatable.get("datatable[query][toDater]"):null;
            List<String> tos=Arrays.asList(toDateStr);
            parameters.put("r=bov_payment_date",tos);
        }

        boolean getFromAmount= datatable.containsKey("datatable[query][fromAmounter]");
        if (getFromAmount){
            String fromAmountStr = getFromAmount?datatable.get("datatable[query][fromAmounter]"):null;
            List<String> froms=Arrays.asList(fromAmountStr);
            parameters.put("r=gev_payment_total_amount",froms);
        }


        boolean getToAmount= datatable.containsKey("datatable[query][toAmounter]");
        if (getToAmount){
            String toAmountStr = getToAmount?datatable.get("datatable[query][toAmounter]"):null;
            List<String> tos=Arrays.asList(toAmountStr);
            parameters.put("r=lev_payment_total_amount",tos);
        }
        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;





        List<PaymentView> all=paymentViewService.findByParameter(parameters,offset,10,sortStr,sortField);

//        Long loanViews=paymentViewService.getCount(parameters);
        BigInteger count=BigInteger.valueOf(100);

        PaymentViewMetaModel metaModel=new PaymentViewMetaModel();

        Meta meta= new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

        metaModel.setMeta(meta);
        metaModel.setData(all);

        return metaModel;
    }

}
