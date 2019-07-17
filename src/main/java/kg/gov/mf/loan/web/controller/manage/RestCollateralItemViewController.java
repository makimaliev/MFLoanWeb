package kg.gov.mf.loan.web.controller.manage;


import kg.gov.mf.loan.output.report.model.CollateralItemView;
import kg.gov.mf.loan.output.report.service.CollateralItemViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class RestCollateralItemViewController {


    @Autowired
    CollateralItemViewService collateralItemViewService;

    @Autowired
    EntityManager entityManager;

    @PostMapping("/collateralItemViews")
    public List<CollateralItemView> getAll(@RequestParam Map<String,String> datatable){

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

        boolean searchByAgreementNumber= datatable.containsKey("datatable[query][agreementNum]");
        if(searchByAgreementNumber){
            String agreementNumber = datatable.get("datatable[query][agreementNum]");
            List<String> searchWord=Arrays.asList(agreementNumber );
            parameters.put("r=ycv_ca_agreementNumber",searchWord);
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
            parameters.put("r=aov_ca_agreementDate",froms);
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = datatable.get("datatable[query][toDater]");
            List<String> tos=Arrays.asList(toDateStr);
            parameters.put("r=bov_ca_agreementDate",tos);
        }

        boolean searchByItemType= datatable.containsKey("datatable[query][itemTypeId]");
        if (searchByItemType){
            String itemTypeStr = datatable.get("datatable[query][itemTypeId]");
            List<String> itemTypes=Arrays.asList(itemTypeStr );
            parameters.put("r=inv_ci_itemTypeId",itemTypes);
        }


        boolean searchByItemName= datatable.containsKey("datatable[query][itemName]");
        if (searchByItemName){
            String itemNameStr = datatable.get("datatable[query][itemName]");
            List<String> itemNames=Arrays.asList(itemNameStr );
            parameters.put("r=ycv_ci_name",itemNames);
        }
        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;


        List<CollateralItemView> all=collateralItemViewService. findByParameter(parameters,offset,100,sortStr,sortField);

//        BigInteger count=BigInteger.valueOf(collateralItemViewService.getCount(parameters));
//        BigInteger count=BigInteger.valueOf(35455);
//
//        Meta meta=new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
//
//        CollateralItemViewMetaModel metaModel=new CollateralItemViewMetaModel();
//
//        System.out.println("==================================COUNT=====================================================================");
//        System.out.println(count);
//        System.out.println("=======================================================================================================");
//
//        metaModel.setMeta(meta);
//        metaModel.setData(all);



        return all;
    }

}