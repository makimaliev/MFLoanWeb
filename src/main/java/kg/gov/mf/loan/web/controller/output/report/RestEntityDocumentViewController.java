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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestEntityDocumentViewController {

    @Autowired
    EntityDocumentViewService entityDocumentViewService;


    @RequestMapping("/entityDocumentViews")
    public EntityDocumentViewMetaModel getAllEntityDocumentViews(@RequestParam Map<String,String> datatable){

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        LinkedHashMap<String,List<String>> parameter=new LinkedHashMap<>();
        List<EntityDocumentView> entityDocumentViewList=entityDocumentViewService.findByParameter(parameter,perPage,offset,sortStr,sortField);
        List<EntityDocumentView> entityDocumentViews=entityDocumentViewService.findByParameter(parameter);

        BigInteger count=BigInteger.valueOf(entityDocumentViews.size());
        EntityDocumentViewMetaModel entityDocumentViewMetaModel=new EntityDocumentViewMetaModel();
        Meta meta= new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);

        entityDocumentViewMetaModel.setData(entityDocumentViewList);
        entityDocumentViewMetaModel.setMeta(meta);

        return entityDocumentViewMetaModel;

    }
}
