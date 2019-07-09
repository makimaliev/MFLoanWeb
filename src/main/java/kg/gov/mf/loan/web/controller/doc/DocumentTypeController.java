package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Counter;
import kg.gov.mf.loan.doc.model.DocumentType;
import kg.gov.mf.loan.doc.model.State;
import kg.gov.mf.loan.doc.service.CounterService;
import kg.gov.mf.loan.doc.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Objects;

@Controller
@RequestMapping("/doc/documentType")
public class DocumentTypeController extends BaseController {

    DocumentTypeService documentTypeService;
    CounterService counterService;

    @Autowired
    public DocumentTypeController(DocumentTypeService documentTypeService, CounterService counterService) {
        this.documentTypeService = documentTypeService;
        this.counterService = counterService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {

        if(getUser() == null)
            return "/login/login";

        model.addAttribute("documentTypes", documentTypeService.list());
        return "/doc/documentType/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String add(Model model) {

        if(getUser() == null)
            return "/login/login";

        model.addAttribute("documentType", new DocumentType());
        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null)
            return "/login/login";

        DocumentType documentType = documentTypeService.getById(id);
        Counter counter;
        Long c = 1000L;
        /*
        if (documentType.getInternalName().equals("incoming"))
        {
            counter = counterService.getCounter(0, 0,0);
            c = counter.getIncoming();
        }
        else if(documentType.getInternalName().equals("outgoing"))
        {
            counter = counterService.getCounter(0, 0, 0);
            c = counter.getOutgoing();
        }
        else if(documentType.getInternalName().equals("internal"))
        {
            if(document.getDocumentState() == State.REQUESTED) {
                counter = counterService.getCounter(department, documentType,0);
                c = counter.getOutgoing();
            }
            else {
                counter = counterService.getCounter(userService.findById(userId).getStaff().getDepartment().getId(), documentType,0);
                c = counter.getIncoming();
            }
        }
        else
        {
            counter = counterService.getCounter(department, documentType, documentSubType);
            c = counter.getOutgoing();
        }
        */
        model.addAttribute("documentType", documentType);
        model.addAttribute("docno", 100);
        model.addAttribute("counter", c);
        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute("documentType") DocumentType documentType) {
        documentTypeService.remove(documentType);
        return "redirect:/doc/documentType";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("documentType") DocumentType documentType) {

        if(documentType.getId() == 0)
        {
            documentTypeService.add(documentType);
        }
        else
        {
            documentTypeService.update(documentType);
        }

        return "redirect:/doc/documentType";
    }
}
