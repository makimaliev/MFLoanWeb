package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class DocumentFlowController {

    @Autowired
    DocumentService documentService;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    DocumentSubTypeService documentSubTypeService;

    @Autowired
    SenderDataService senderDataService;

    @Autowired
    ReceiverDataService receiverDataService;

    @Autowired
    ResultDataService resultDataService;

    @Autowired
    ResponsibleService responsibleService;

    @Autowired
    ExecutorService executorService;

    @Autowired
    InformationService informationService;

    @RequestMapping(value = "/doc")
    public String listDocuments(Model model) {

        model.addAttribute("documentType", documentTypeService.list());
        model.addAttribute("documentSubType", documentSubTypeService.list());
        model.addAttribute("user", getPrincipal());
        return "/doc/document/index";
    }

    @RequestMapping(value = "/doc/view/{id}")
    public String viewDocument(@PathVariable("id") Long id, Model model) {

        model.addAttribute("document", documentService.getById(id));
        model.addAttribute("documentType", documentTypeService.list());
        model.addAttribute("documentSubType", documentSubTypeService.list());

        model.addAttribute("senderData", senderDataService.getById(id));
        model.addAttribute("receiverData", receiverDataService.getById(id));
        model.addAttribute("resultData", resultDataService.getById(id));

        model.addAttribute("responsible", responsibleService.list());
        model.addAttribute("executor", executorService.list());
        model.addAttribute("information", informationService.list());

        return "/doc/document/view";
    }

    @RequestMapping("/doc/{id}")
    @ResponseBody
    public Document getDocumentString(@PathVariable("id") Long id)
    {
        return documentService.getById(id);
    }

    @RequestMapping("/doc/all")
    @ResponseBody
    public List<Document> allDocuments()
    {
        return documentService.list();
    }

    @RequestMapping("/doc/internal")
    @ResponseBody
    public List<Document> internalDocuments()
    {
        return documentService.internalDocuments();
    }

    @RequestMapping("/doc/incoming")
    @ResponseBody
    public List<Document> incomingDocuments()
    {
        return documentService.incomingDocuments();
    }

    @RequestMapping("/doc/outgoing")
    @ResponseBody
    public List<Document> outgoingDocuments()
    {
        return documentService.outgoingDocuments();
    }

    @RequestMapping(value= "/doc/add")
    @ResponseBody
    public String SaveOrUpdateDocument(@ModelAttribute("document") Document document) {
        if((document.getId() == null) || (document.getId() == 0))
        {
            this.documentService.add(document);
        }
        else
        {
            this.documentService.update(document);
        }
        return "OK";
    }

    @RequestMapping("/doc/remove/{id}")
    @ResponseBody
    public String removeDocument(@ModelAttribute("document") Document document) {
        this.documentService.remove(document);
        return "OK";
    }

    private String getPrincipal(){

        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
