package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.DepartmentService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.service.InformationService;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.doc.model.DispatchData;
import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    PersonService personService;

    @Autowired
    UserService userService;

    @Autowired
    DispatchTypeService dispatchTypeService;

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
        model.addAttribute("documentSubType", documentSubTypeService.findAll());
        model.addAttribute("type", type);

        return "/doc/document/index";
    }

    @RequestMapping(value = "/doc/new", params = {"type", "subtype"}, method = RequestMethod.GET)
    public String newDocument(@RequestParam("type") String type, @RequestParam("subtype") String subtype, Model model) {
        Document document = new Document();
        document.setDocumentType(documentTypeService.getByInternalName(type));
        document.setDocumentSubType(documentSubTypeService.getByInternalName(subtype));

        model.addAttribute("document", document);
        model.addAttribute("documentTypes", documentTypeService.findAll());
        model.addAttribute("documentSubTypes", documentSubTypeService.findAll());

        model.addAttribute("responsible", responsible);

        model.addAttribute("staff", staffService.findAll());
        model.addAttribute("department", departmentService.findAll());
        model.addAttribute("organization", organizationService.findAll());
        model.addAttribute("person", personService.findAll());

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/doc/edit/{id}", method = RequestMethod.GET)
    public String editDocument(@PathVariable("id") final Long id, Model model) {

        model.addAttribute("document", documentService.findById(id));
        model.addAttribute("documentTypes", documentTypeService.findAll());
        model.addAttribute("documentSubTypes", documentSubTypeService.findAll());

        model.addAttribute("responsible", responsible);

        model.addAttribute("staff", staffService.findAll());
        model.addAttribute("department", departmentService.findAll());
        model.addAttribute("organization", organizationService.findAll());
        model.addAttribute("person", personService.findAll());

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/doc/save", method = RequestMethod.POST)
    public String saveDocument(@ModelAttribute("document") Document document, @RequestParam(value="action") String action) {

        document.getSenderDispatchData().add(setDispatchData(action));
        document.getReceiverDispatchData().add(setDispatchData(action));

        if((document.getId() == null) || (document.getId() == 0))
        {
            documentService.create(document);
        }
        else
        {
            for(DispatchData d : documentService.findById(document.getId()).getSenderDispatchData())
            {
                document.getSenderDispatchData().add(d);
            }
            for(DispatchData d : documentService.findById(document.getId()).getReceiverDispatchData())
            {
                document.getReceiverDispatchData().add(d);
            }
            documentService.edit(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }

    @RequestMapping(value = "/doc/view/{id}", method = RequestMethod.GET)
    public String viewDocument(@PathVariable("id") Long id, Model model) {
        model.addAttribute("document", documentService.findById(id));
        return "/doc/document/view";
    }

    @RequestMapping(value = "/doc/delete/{id}", method = RequestMethod.GET)
    public String deleteDocument(@PathVariable("id") Long id) {
        Document document = documentService.findById(id);
        documentService.deleteById(document);
        return "redirect:/doc?type=" + document.getDocumentType().getInternalName();
    }

    public DispatchData setDispatchData(String internalName) {
        DispatchData dispatchData = new DispatchData();
        dispatchData.setDescription(dispatchTypeService.getByInternalName(internalName).getName());
        dispatchData.setParent(false);
        dispatchData.setDispatchBy(userService.findById(1L));
        dispatchData.setDispatchTo(userService.findById(2L));
        dispatchData.setDispatchType(dispatchTypeService.getByInternalName(internalName));
        return dispatchData;
    }
}
