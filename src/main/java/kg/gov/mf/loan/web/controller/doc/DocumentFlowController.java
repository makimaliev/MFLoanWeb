package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.service.DepartmentService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.DocumentStatusService;
import kg.gov.mf.loan.doc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import static kg.gov.mf.loan.web.controller.doc.DispatchDataController.getDispatchData;

@Controller
public class DocumentFlowController {

    @Autowired
    DocumentService documentService;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    DocumentSubTypeService documentSubTypeService;

    @Autowired
    ExecutorService executorService;

    @Autowired
    InformationService informationService;

    /*
    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    PersonService personService;
    */

    @Autowired
    UserService userService;

    @Autowired
    DocumentStatusService documentStatusService;

    @Autowired
    AccountService accountService;

    class Prop {

        public boolean title;
        public boolean description;
        public boolean documentType;
        public boolean documentSubType;
        public boolean generalStatus;

        public boolean senderRegisteredDate;
        public boolean senderRegisteredNumber;
        public boolean senderDueDate;
        public boolean senderExecutorType;
        public boolean senderExecutor;
        public boolean senderResponsibleType;
        public boolean senderResponsible;
        public boolean senderStatus;
        public boolean senderInformation;

        public boolean receiverRegisteredDate;
        public boolean receiverRegisteredNumber;
        public boolean receiverDueDate;
        public boolean receiverExecutorType;
        public boolean receiverExecutor;
        public boolean receiverResponsibleType;
        public boolean receiverResponsible;
        public boolean receiverStatus;
        public boolean receiverInformation;

        public Prop(boolean state) {
            title = state;
            description = state;
            documentType = state;
            documentSubType = state;
            generalStatus = state;
            senderRegisteredDate = state;
            senderRegisteredNumber = state;
            senderDueDate = state;
            senderExecutorType = state;
            senderExecutor = state;
            senderResponsibleType = state;
            senderResponsible = state;
            senderStatus = state;
            senderInformation = state;

            receiverRegisteredDate = state;
            receiverRegisteredNumber = state;
            receiverDueDate = state;
            receiverExecutorType = state;
            receiverExecutor = state;
            receiverResponsibleType = state;
            receiverResponsible = state;
            receiverStatus = state;
            receiverInformation = state;
        }
    }

    final static Map<Integer, String> responsible = new HashMap<Integer, String>() {
        {
            put(1, "Staff");
            put(2, "Department");
            put(3, "Organization");
            put(4, "Person");
        }
    };

    @RequestMapping(value = "/doc", params = "type", method = RequestMethod.GET)
    public String index(@RequestParam("type") String type, Model model) {

        model.addAttribute("responsible", responsible);
        model.addAttribute("documents", documentService.getDocuments(type));
        model.addAttribute("documentSubTypes", documentTypeService.getByInternalName(type).getDocumentSubTypes());
        model.addAttribute("type", type);

        return "/doc/document/index";
    }

    @RequestMapping(value = "/doc/new", params = {"type", "subtype"}, method = RequestMethod.GET)
    public String newDocument(@RequestParam("type") String type, @RequestParam("subtype") String subtype, Model model) {

        Document document = new Document();
        document.setDocumentType(documentTypeService.getByInternalName(type));
        document.setDocumentSubType(documentSubTypeService.getByInternalName(subtype));

        if(type.equals("internal"))
        {
            Responsible resp = new Responsible();
            resp.setResponsibleType(1);
            document.setSenderResponsible(resp);

            Executor exec = new Executor();
            exec.setExecutorType(1);
            document.setSenderExecutor(exec);
        }

        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);

        model.addAttribute("staff", accountService.getStaff());
        model.addAttribute("department", accountService.getDepartments());
        model.addAttribute("organization", accountService.getOrganizations());
        model.addAttribute("person", accountService.getPerson());

        /*
        model.addAttribute("staff", staffService.findAll());
        model.addAttribute("department", departmentService.findAll());
        model.addAttribute("organization", organizationService.findAll());
        model.addAttribute("person", personService.findAll());
        */

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/doc/edit/{id}", method = RequestMethod.GET)
    public String editDocument(@PathVariable("id") final Long id, Model model) {

        model.addAttribute("document", documentService.findById(id));
        model.addAttribute("responsible", responsible);

        model.addAttribute("staff", accountService.getStaff());
        model.addAttribute("department", accountService.getDepartments());
        model.addAttribute("organization", accountService.getOrganizations());
        model.addAttribute("person", accountService.getPerson());

        /*
        model.addAttribute("staff", staffService.findAll());
        model.addAttribute("department", departmentService.findAll());
        model.addAttribute("organization", organizationService.findAll());
        model.addAttribute("person", personService.findAll());
        */


        return "/doc/document/edit";
    }

    @RequestMapping(value = "/doc/save", method = RequestMethod.POST)
    public String saveDocument(@ModelAttribute("document") Document document, @RequestParam(value="action") String action) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            document.getSenderDispatchData().add(setDispatchData("create"));
            document.setGeneralStatus(documentStatusService.getByInternalName("create"));
            document.setSenderStatus(documentStatusService.getByInternalName("create"));
            documentService.create(document);

        } else {

            Document doc = documentService.findById(document.getId());
            DocumentStatus ds = documentStatusService.getByInternalName(action);

            if(doc.getSenderStatus().getInternalName().equals("toapprove") && action.equals("approve")) {

                document = doc;

                document.getSenderDispatchData().add(setDispatchData(action));
                document.setGeneralStatus(ds);
                document.setSenderStatus(ds);

                DocumentStatus d1 = documentStatusService.getByInternalName("register");
                document.getSenderDispatchData().add(setDispatchData("register"));
                document.setGeneralStatus(d1);
                document.setSenderStatus(d1);
                document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                document.setSenderRegisteredDate(new Date());

            } else if(doc.getSenderStatus().getInternalName().equals("register") && action.equals("accept")) {

                document = doc;

                document.getReceiverDispatchData().add(setDispatchData(action));
                document.setGeneralStatus(ds);
                document.setReceiverStatus(ds);

                document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                document.setReceiverRegisteredDate(new Date());

            } else if(doc.getReceiverStatus() != null && action.equals("start")) {

                Document d = document;

                document = doc;
                document.setReceiverExecutor(d.getReceiverExecutor());
                document.getReceiverDispatchData().add(setDispatchData(action));
                document.setGeneralStatus(ds);
                document.setReceiverStatus(ds);

            } else if(doc.getReceiverStatus() != null && action.equals("done")) {

                document = doc;

                document.getReceiverDispatchData().add(setDispatchData(action));
                document.setGeneralStatus(ds);
                document.setReceiverStatus(ds);

            } else {

                for (DispatchData d : doc.getSenderDispatchData()) {
                    document.getSenderDispatchData().add(d);
                }
                document.getSenderDispatchData().add(setDispatchData(action));
                document.setGeneralStatus(ds);
                document.setSenderStatus(ds);
            }

            documentService.edit(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }

    @RequestMapping(value = "/doc/view/{id}", method = RequestMethod.GET)
    public String viewDocument(@PathVariable("id") Long id, Model model) {
        model.addAttribute("document", documentService.findById(id));
        model.addAttribute("responsible", responsible);
        return "/doc/document/view";
    }

    @RequestMapping(value = "/doc/delete/{id}", method = RequestMethod.GET)
    public String deleteDocument(@PathVariable("id") Long id) {
        Document document = documentService.findById(id);
        documentService.deleteById(document);
        return "redirect:/doc?type=" + document.getDocumentType().getInternalName();
    }

    public DispatchData setDispatchData(String internalName) {
        return getDispatchData(documentStatusService.getByInternalName(internalName), userService.findById(1L), userService.findById(2L));
    }
}
