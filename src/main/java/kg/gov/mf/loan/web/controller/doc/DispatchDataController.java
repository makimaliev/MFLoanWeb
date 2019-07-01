package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.DispatchData;
import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.service.DispatchDataService;
import kg.gov.mf.loan.doc.service.DocumentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/dispatchData")
public class DispatchDataController extends BaseController {

    private final DocumentService documentService;
    private final DispatchDataService dispatchDataService;

    public DispatchDataController(DocumentService documentService, DispatchDataService dispatchDataService) {
        this.documentService = documentService;
        this.dispatchDataService = dispatchDataService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<DispatchData> index(@RequestParam("id") Long id) {

        return dispatchDataService.getByDocumentId(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public DispatchData get(@PathVariable("id") Long id) {

        return dispatchDataService.getById(id);
    }

    @RequestMapping(value = "/save/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String save(@PathVariable("id") Long id, @Valid DispatchData dispatchData) {

        if(dispatchData.getId() == 0) {
            Document document = documentService.getById(id);
            document.getDispatchData().add(dispatchData);
            documentService.update(document);
        } else {
            dispatchDataService.update(dispatchData);
        }
        return "OK";
    }
}
