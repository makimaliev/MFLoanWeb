package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.DocumentStatusService;
import kg.gov.mf.loan.doc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class DocumentFlowController {

    //region Dependencies

    @Autowired
    DocumentService documentService;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    DocumentSubTypeService documentSubTypeService;

    @Autowired
    UserService userService;

    @Autowired
    DocumentStatusService documentStatusService;

    @Autowired
    AccountService accountService;

    //endregion

    public DocumentFlowController() {
    }

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

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/doc/edit/{id}", method = RequestMethod.GET)
    public String editDocument(@PathVariable("id") Long id, Model model) {

        model.addAttribute("document", documentService.findById(id));
        model.addAttribute("responsible", responsible);

        model.addAttribute("staff", accountService.getStaff());
        model.addAttribute("department", accountService.getDepartments());
        model.addAttribute("organization", accountService.getOrganizations());
        model.addAttribute("person", accountService.getPerson());

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/doc/save", method = RequestMethod.POST)
    public String saveDocument(@ModelAttribute("document") Document document, @RequestParam("action") String action, @RequestParam("senderFile") MultipartFile senderFile, @RequestParam("receiverFile") MultipartFile receiverFile ) throws IOException {

        //region Attachment
        String path = "D:\\cp\\";

        if (!senderFile.isEmpty())
        {
            try {
                String fileName = null;
                fileName = senderFile.getOriginalFilename();
                byte[] bytes = senderFile.getBytes();
                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)));
                buffStream.write(bytes);
                buffStream.close();
                document.setSenderFileName(fileName);
            } catch (Exception e) {
            }
        }

        if (!receiverFile.isEmpty())
        {
            try {
                String fileName = null;
                fileName = receiverFile.getOriginalFilename();
                byte[] bytes = receiverFile.getBytes();
                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(new File(path + fileName)));
                buffStream.write(bytes);
                buffStream.close();
                document.setReceiverFileName(fileName);
            } catch (Exception e) {
            }
        }
        //endregion

        DocumentStatus ds = documentStatusService.getByInternalName(action);
        document.setGeneralStatus(ds);
        DispatchData dd = setDispatchData(action);

        if((document.getId() == null) || (document.getId() == 0))
        {
            document.getSenderDispatchData().add(setDispatchData("create"));
            document.setSenderStatus(documentStatusService.getByInternalName(action));
            documentService.create(document);
        }
        else
        {
            Document doc = documentService.findById(document.getId());
            document.setSenderDispatchData(doc.getSenderDispatchData()); // add existing DD
            document.setSenderStatus(doc.getSenderStatus());

            if(doc.getSenderStatus().getInternalName().equals("approve"))
            {   // Receiver Data
                document.setSenderResponsible(doc.getSenderResponsible());
                document.setSenderExecutor(doc.getSenderExecutor());
                document.setReceiverResponsible(doc.getReceiverResponsible());

                document.setReceiverDispatchData(doc.getReceiverDispatchData()); // add existing DD
                document.getReceiverDispatchData().add(dd); // add new DD
                document.setReceiverStatus(ds);

                if(action.equals("accept"))
                {
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());
                }

                if(action.equals("done"))
                {
                    document.setReceiverExecutor(doc.getReceiverExecutor());
                }
            }
            else
            {   // Sender Data
                document.setSenderStatus(ds);
                document.getSenderDispatchData().add(dd); // add new DD

                if(action.equals("approve"))
                {
                    document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                    document.setSenderRegisteredDate(new Date());
                }
            }

            documentService.edit(document);
        }

        String url = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + url;
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

    DispatchData setDispatchData(String internalName) {
        DocumentStatus documentStatus = documentStatusService.getByInternalName(internalName);
        DispatchData dispatchData = new DispatchData();
        dispatchData.setDescription(documentStatus.getName());
        dispatchData.setParent(false);
        dispatchData.setDispatchBy(userService.findById(1L));
        dispatchData.setDispatchTo(userService.findById(2L));
        dispatchData.setDispatchType(documentStatus);
        return dispatchData;
    }
}
