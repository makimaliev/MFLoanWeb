package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.doc.model.DispatchData;
import kg.gov.mf.loan.doc.model.DispatchType;
import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.model.DocumentStatus;
import kg.gov.mf.loan.doc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DispatchDataController {

    @Autowired
    UserService userService;

    @Autowired
    DocumentService documentService;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    DocumentStatusService documentStatusService;

    @RequestMapping(value = "/doc/create", method = RequestMethod.POST)
    public String createDocument(@ModelAttribute("document") Document document) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            document.getSenderDispatchData().add(setDispatchData("create"));
            documentService.create(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }

    @RequestMapping(value = "/doc/accept", method = RequestMethod.POST)
    public String acceptDocument(@ModelAttribute("document") Document document) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            document.getSenderDispatchData().add(setDispatchData("accept"));
            documentService.create(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }

    @RequestMapping(value = "/doc/reject", method = RequestMethod.POST)
    public String rejectDocument(@ModelAttribute("document") Document document) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            document.getSenderDispatchData().add(setDispatchData("reject"));
            documentService.create(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }

    @RequestMapping(value = "/doc/reconcile", method = RequestMethod.POST)
    public String reconcileDocument(@ModelAttribute("document") Document document) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            document.getSenderDispatchData().add(setDispatchData("reconcile"));
            documentService.create(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }

    @RequestMapping(value = "/doc/approve", method = RequestMethod.POST)
    public String approveDocument(@ModelAttribute("document") Document document) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            document.getSenderDispatchData().add(setDispatchData("approve"));
            document.getSenderDispatchData().add(setDispatchData("register"));
            document.getSenderDispatchData().add(setDispatchData("toexecute"));
            documentService.create(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }

    @RequestMapping(value = "/doc/send", method = RequestMethod.POST)
    public String registerDocument(@ModelAttribute("document") Document document) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            document.getSenderDispatchData().add(setDispatchData("register"));
            document.getSenderDispatchData().add(setDispatchData("sent"));
            documentService.create(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }

    public DispatchData setDispatchData(String internalName) {
        return getDispatchData(documentStatusService.getByInternalName(internalName), userService.findById(1L), userService.findById(2L));
    }

    static DispatchData getDispatchData(DocumentStatus documentStatus, User byId, User byId2) {
        DispatchData dispatchData = new DispatchData();
        dispatchData.setDescription(documentStatus.getName());
        dispatchData.setParent(false);
        dispatchData.setDispatchBy(byId);
        dispatchData.setDispatchTo(byId2);
        dispatchData.setDispatchType(documentStatus);
        return dispatchData;
    }
}
