package kg.gov.mf.loan.web.controller.doc;


import kg.gov.mf.loan.admin.org.service.DepartmentService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.StaffService;
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


    //**************************************************************************************
    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    PersonService personService;
    //**************************************************************************************

    @RequestMapping(value = "/doc", params = "type")
    public String listDocuments(@RequestParam("type") String type, Model model) {

        /*
        model.addAttribute("staff", staffService.findAll());
        model.addAttribute("department", departmentService.findAll());
        model.addAttribute("organization", organizationService.findAll());
        model.addAttribute("person", personService.findAll());
        */
        
        model.addAttribute("documentType", documentTypeService.findAll());
        model.addAttribute("documentTypeId", documentTypeService.getIdByInternalname(type));
        model.addAttribute("documentSubType", documentSubTypeService.findAll());
        model.addAttribute("type", type);
        model.addAttribute("user", getPrincipal());
        return "/doc/document/index";
    }

    @RequestMapping(value = "/doc/view/{id}")
    public String viewDocument(@PathVariable("id") Long id, Model model) {

        model.addAttribute("document", documentService.findById(id));
        model.addAttribute("documentType", documentTypeService.findAll());
        model.addAttribute("documentSubType", documentSubTypeService.findAll());

        model.addAttribute("senderData", senderDataService.findById(id));
        model.addAttribute("receiverData", receiverDataService.findById(id));
        model.addAttribute("resultData", resultDataService.findById(id));

        model.addAttribute("responsible", responsibleService.findAll());
        model.addAttribute("executor", executorService.findAll());
        model.addAttribute("information", informationService.findAll());

        return "/doc/document/view";
    }

    @RequestMapping("/doc/{id}")
    @ResponseBody
    public Document getDocumentString(@PathVariable("id") Long id)
    {
        return documentService.findById(id);
    }

    @RequestMapping("/doc/all")
    @ResponseBody
    public List<Document> allDocuments()
    {
        return documentService.findAll();
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

    @RequestMapping("/doc/archived")
    @ResponseBody
    public List<Document> archivedDocuments()
    {
        return documentService.archivedDocuments();
    }

    @RequestMapping(value= "/doc/add")
    @ResponseBody
    public String SaveOrUpdateDocument(@ModelAttribute("document") Document document) {
        if((document.getId() == null) || (document.getId() == 0))
        {
            this.documentService.create(document);
        }
        else
        {
            this.documentService.edit(document);
        }
        return "OK";
    }

    @RequestMapping("/doc/remove/{id}")
    @ResponseBody
    public String removeDocument(@ModelAttribute("document") Document document) {
        this.documentService.deleteById(document);
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
