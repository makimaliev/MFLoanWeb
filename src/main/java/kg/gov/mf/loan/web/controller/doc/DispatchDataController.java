package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.DispatchData;
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

    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public String save(@Valid DispatchData dispatchData) {

        dispatchDataService.update(dispatchData);
        return "OK";
    }
}
