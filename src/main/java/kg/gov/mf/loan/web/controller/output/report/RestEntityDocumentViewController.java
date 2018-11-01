package kg.gov.mf.loan.web.controller.output.report;


import kg.gov.mf.loan.output.report.model.EntityDocumentView;
import kg.gov.mf.loan.output.report.service.EntityDocumentViewService;
import kg.gov.mf.loan.web.fetchModels.EntityDocumentViewMetaModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RestEntityDocumentViewController {

    @Autowired
    EntityDocumentViewService entityDocumentViewService;


    @RequestMapping("/entityDocumentViews")
    public List<EntityDocumentView> getAllEntityDocumentViews(@RequestParam Map<String,String> datatable){

        LinkedHashMap<String,List<String>> parameter=new LinkedHashMap<>();

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;


        boolean searchByEntityDocumnetName = datatable.containsKey("datatable[sort][entityDocumentName]");
        if(searchByEntityDocumnetName){
            List<String> searchWord= Arrays.asList(datatable.get("datatable[sort][entityDocumentName]"));
            parameter.put("r=ycv_entity_document_name",searchWord);
        }

        boolean searchByComplDesc= datatable.containsKey("datatable[sort][completedDesc]");
        if(searchByComplDesc){
            List<String> searchWord= Arrays.asList(datatable.get("datatable[sort][completedDesc]"));
            parameter.put("r=ycv_entity_document_completedDescription",searchWord);
        }

        boolean getFromDate= datatable.containsKey("datatable[query][fromDater]");
        if (getFromDate){
            String fromDateStr = datatable.get("datatable[query][fromDater]");
            List<String> froms=Arrays.asList(fromDateStr);
            parameter.put("r=aov_entity_document_completedDate",froms);
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDater]");
        if (getToDate){
            String toDateStr = datatable.get("datatable[query][toDater]");
            List<String> tos=Arrays.asList(toDateStr);
            parameter.put("r=bov_entity_document_completedDate",tos);
        }

        boolean searchByOwnerName= datatable.containsKey("datatable[sort][ownerName]");
        if(searchByOwnerName){
            List<String> searchWord= Arrays.asList(datatable.get("datatable[sort][ownerName]"));
            parameter.put("r=ycv_owner_name",searchWord);
        }

        boolean searchByRegionId = datatable.containsKey("datatable[sort][regionId]");
        if(searchByRegionId){
            List<String> searchWord= Arrays.asList(datatable.get("datatable[sort][regionId]"));
            parameter.put("r=inv_owner_region_id",searchWord);
        }

        boolean searchByDistrictId= datatable.containsKey("datatable[sort][districtId]");
        if(searchByDistrictId){
            List<String> searchWord= Arrays.asList(datatable.get("datatable[sort][districtId]"));
            parameter.put("r=inv_owner_district_id",searchWord);
        }


        List<EntityDocumentView> all=entityDocumentViewService.findByParameter(parameter,perPage,offset,sortStr,sortField);
//        List<EntityDocumentView> entityDocumentViews=entityDocumentViewService.findByParameter(parameter);

//        BigInteger count=BigInteger.valueOf(entityDocumentViews.size());
//        EntityDocumentViewMetaModel entityDocumentViewMetaModel=new EntityDocumentViewMetaModel();
//        Meta meta= new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
//
//        entityDocumentViewMetaModel.setData(entityDocumentViewList);
//        entityDocumentViewMetaModel.setMeta(meta);

        return all;

    }
}
