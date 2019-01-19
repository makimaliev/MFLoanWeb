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

        boolean searchByFinGroup= datatable.containsKey("datatable[query][finGroupId]");
        if (searchByFinGroup){
            String searchStr = datatable.get("datatable[query][finGroupId]");
            List<String> searchStrs=Arrays.asList(searchStr);
            parameters.put("r=inv_loan_fin_group_id",searchStrs);
        }

        boolean searchByIndex= datatable.containsKey("datatable[query][indexId]");
        if (searchByIndex){
            String searchStr = datatable.get("datatable[query][indexId]");
            List<String> searchStrs=Arrays.asList(searchStr);
            parameters.put("r=inv_cph_index_id",searchStrs);
        }

        boolean searchByGroup= datatable.containsKey("datatable[query][groupId]");
        if (searchByGroup){
            String searchStr = datatable.get("datatable[query][groupId]");
            List<String> searchStrs=Arrays.asList(searchStr);
            parameters.put("r=inv_cph_group_id",searchStrs);
        }
        boolean searchBySubIndex= datatable.containsKey("datatable[query][subIndexId]");
        if (searchBySubIndex){
            String searchStr = datatable.get("datatable[query][subIndexId]");
            List<String> searchStrs=Arrays.asList(searchStr);
            parameters.put("r=inv_cph_sub_index_id",searchStrs);
        }

        boolean searchByDocNumber= datatable.containsKey("datatable[query][docNumber]");
        if(searchByDocNumber){
            String searchStr = datatable.get("datatable[query][docNumber]");
            List<String> searchStrs= Arrays.asList(searchStr);
            parameters.put("r=ycv_cph_doc_number",searchStrs);
        }

        boolean searchByResultDocNumber= datatable.containsKey("datatable[query][resultDocNumber]");
        if(searchByResultDocNumber){
            String searchStr = datatable.get("datatable[query][resultDocNumber]");
            List<String> searchStrs= Arrays.asList(searchStr);
            parameters.put("r=ycv_cph_result_doc_number",searchStrs);
        }


        List<CollectionPhaseView> all=collectionPhaseViewService.findByParameter(parameters,offset,perPage,sortStr,sortField);

        return all;
    }

}
