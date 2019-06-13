package kg.gov.mf.loan.web.controller.manage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import kg.gov.mf.loan.output.report.service.CollectionPhaseViewService;
import kg.gov.mf.loan.web.fetchModels.CollectionPhaseMetaModel;
import kg.gov.mf.loan.web.fetchModels.CollectionPhaseModel1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class RestCollectionPhaseViewController {

    @Autowired
    EntityManager entityManager;

    @Autowired
    CollectionPhaseViewService collectionPhaseViewService;

    @PostMapping("/collectionPhaseViews")
    public Set<CollectionPhaseView> getAll(@RequestParam Map<String,String> datatable){

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


        Set<CollectionPhaseView> all=collectionPhaseViewService.findByParameter(parameters,offset,perPage,sortStr,sortField);

        return all;
    }

    @GetMapping("/collectionPhaseViews/second")
    public CollectionPhaseMetaModel getAllSecond(@RequestParam Map<String,String> datatable){

        LinkedHashMap<String,List<String>> parameters=new LinkedHashMap<>();


        String perPageStr= datatable.get("length");
        String sortStr = datatable.get("order[0][dir]");
        String sortField = datatable.get("order[0][column]");

        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = Integer.valueOf(datatable.get("start"));
        String draw=datatable.get("draw");

        String searchStrs=datatable.get("search[value]");
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> searchMap=new HashMap<>();
        try {
            searchMap= mapper.readValue(searchStrs, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String s:searchMap.keySet()){
            List<String> searchList=new ArrayList<>();
            String valueOfMap= searchMap.get(s);
            String[] splitedMapValues;
            if(valueOfMap!="") {
                if (valueOfMap.charAt(0) == '?') {
                    splitedMapValues = valueOfMap.substring(1).split("\\?");
                } else {
                    splitedMapValues = valueOfMap.split("\\?");
                }
                for (String searchWord : splitedMapValues) {
                    if (!searchWord.equals("") || searchWord != null)
                        searchList.add(searchWord);
                }
                parameters.put("r=" + s, searchList);
            }
        }
        parameters.put("orderBy",Arrays.asList("v_debtor_name"));
        parameters.put("orderBy",Arrays.asList("v_cph_startDate"));


        Set<CollectionPhaseModel1> list=new HashSet<>();
        Set<CollectionPhaseView> all=collectionPhaseViewService.findByParameter(parameters,offset,perPage,"asc","v_debtor_name");
        int total=1000;
        for (CollectionPhaseView phaseView:all) {
            CollectionPhaseModel1 model=new CollectionPhaseModel1();
            model.setV_cph_close_total_amount(Double.valueOf(phaseView.getV_cph_close_total_amount()));
            model.setV_cph_start_total_amount(Double.valueOf(phaseView.getV_cph_start_total_amount()));
            model.setV_cph_closeDate(phaseView.getV_cph_closeDate());
            model.setV_cph_startDate(phaseView.getV_cph_startDate());
            model.setV_cph_id(phaseView.getV_cph_id());
            model.setV_cph_phaseStatusId(phaseView.getV_cph_phaseStatusId());
            model.setV_cph_phaseTypeId(phaseView.getV_cph_phaseTypeId());
            model.setV_debtor_id(phaseView.getV_debtor_id());
            model.setV_debtor_name(phaseView.getV_debtor_name());
            model.setV_cph_collectionProcedureId(phaseView.getV_cph_collectionProcedureId());
            model.setV_debtor_owner_id(phaseView.getV_debtor_owner_id());
            model.setV_cph_index_id(phaseView.getV_cph_index_id());
            list.add(model);
        }
        CollectionPhaseMetaModel metaModel=new CollectionPhaseMetaModel();
        metaModel.setDraw(Integer.valueOf(draw));
        metaModel.setRecordsTotal(total);
        metaModel.setRecordsFiltered(total);
        metaModel.setData(list);


        return metaModel;
    }

}
