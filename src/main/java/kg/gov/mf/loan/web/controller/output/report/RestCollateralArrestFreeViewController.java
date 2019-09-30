package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.output.report.model.CollateralArrestFreeView;
import kg.gov.mf.loan.output.report.service.CollateralArrestFreeViewService;
import kg.gov.mf.loan.web.fetchModels.CollateralArrestFreeViewMetaModel;
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
public class RestCollateralArrestFreeViewController {


    @Autowired
    CollateralArrestFreeViewService collateralArrestFreeViewService;

    @PostMapping("/collateralArrestFreeViews")
    public CollateralArrestFreeViewMetaModel getAll(@RequestParam Map<String,String> datatable){

        LinkedHashMap<String,List<String>> parameters=new LinkedHashMap<>();


        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");

        boolean searchByDebtorName= datatable.containsKey("datatable[query][debtorName]");
        if(searchByDebtorName){
            String debtorName = datatable.get("datatable[query][debtorName]");
            List<String> searchWord= Arrays.asList(debtorName);
            parameters.put("r=ycv_debtor_name",searchWord);
        }

        boolean searchByDetail= datatable.containsKey("datatable[query][detail]");
        if(searchByDetail){
            String detail = datatable.get("datatable[query][detail]");
            List<String> searchWord=Arrays.asList(detail);
            parameters.put("r=ycv_ciaf_details",searchWord);
        }

        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        if (searchByDistrict){
            String districtStr =datatable.get("datatable[query][districtId]");
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
            parameters.put("r=aov_ciaf_onDate",froms);
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = datatable.get("datatable[query][toDater]");
            List<String> tos=Arrays.asList(toDateStr);
            parameters.put("r=bov_ciaf_onDate",tos);
        }



        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;


        List<CollateralArrestFreeView> all=this.collateralArrestFreeViewService.findByParameter(parameters,offset,100);

        BigInteger count= BigInteger.valueOf(0);
        if (all.size()==100){
            count=BigInteger.valueOf(100);
        }
        else{
            count=BigInteger.valueOf(all.size());

        }
//
        Meta meta=new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, "desc","v_ci_id");
//
        CollateralArrestFreeViewMetaModel metaModel=new CollateralArrestFreeViewMetaModel();

        metaModel.setMeta(meta);
        if(all.size()>perPage){
            metaModel.setData(all.subList(0,perPage));
        }
        else{
            metaModel.setData(all);
        }

        return metaModel;
    }
}
