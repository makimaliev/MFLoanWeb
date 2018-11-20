package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import kg.gov.mf.loan.output.report.service.CollectionPhaseViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestCollectionPhaseViewController {

    @Autowired
    CollectionPhaseViewService collectionPhaseViewService;

    @PostMapping("/collectionPhaseViews")
    public List<CollectionPhaseView> getAll(@RequestParam Map<String,String> datatable){

        LinkedHashMap<String,List<String>> parameters=new LinkedHashMap<>();


        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        boolean searchByDebtorName= datatable.containsKey("datatable[query][debtorName]");
        if(searchByDebtorName){
            String debtorName = datatable.get("datatable[query][debtorName]");
            List<String> searchWord= Arrays.asList(debtorName);
            parameters.put("r=ycv_debtor_name",searchWord);
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
            parameters.put("r=aov_cph_startDate",froms);
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = datatable.get("datatable[query][toDater]");
            List<String> tos=Arrays.asList(toDateStr);
            parameters.put("r=bov_cph_startDate",tos);
        }

        boolean searchByPhaseType= datatable.containsKey("datatable[query][phaseTypeId]");
        if (searchByPhaseType){
            String phaseTypeStr = datatable.get("datatable[query][phaseTypeId]");
            List<String> phaseTypes=Arrays.asList(phaseTypeStr  );
            parameters.put("r=inv_cph_phaseTypeId",phaseTypes);
        }

        boolean searchByStatus= datatable.containsKey("datatable[query][statusId]");
        if (searchByStatus){
            String statusStr = datatable.get("datatable[query][statusId]");
            List<String> statuses=Arrays.asList(statusStr  );
            parameters.put("r=inv_cph_phaseStatusId",statuses);
        }

        List<CollectionPhaseView> all=collectionPhaseViewService.findByParameter(parameters,offset,perPage,sortStr,sortField);

        return all;
    }

}
