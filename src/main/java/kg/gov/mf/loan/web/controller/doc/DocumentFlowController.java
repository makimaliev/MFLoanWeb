package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.DocumentStatusService;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.doc.model.State;
import kg.gov.mf.loan.doc.model.Transition;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.model.TaskPriority;
import kg.gov.mf.loan.task.model.TaskStatus;
import kg.gov.mf.loan.task.service.TaskService;
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
@RequestMapping("/doc")
public class DocumentFlowController extends BaseController {

    //region Dependencies
    private DocumentService documentService;
    private DocumentTypeService documentTypeService;
    private DocumentSubTypeService documentSubTypeService;
    private DocumentStatusService documentStatusService;
    private AccountService accountService;
    private TaskService taskService;

    @Autowired
    public void setDocumentService(DocumentService documentService) {
        this.documentService = documentService;
    }

    @Autowired
    public void setDocumentTypeService(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @Autowired
    public void setDocumentSubTypeService(DocumentSubTypeService documentSubTypeService) {
        this.documentSubTypeService = documentSubTypeService;
    }

    @Autowired
    public void setDocumentStatusService(DocumentStatusService documentStatusService) {
        this.documentStatusService = documentStatusService;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    //endregion

    public DocumentFlowController(){}

    final static Map<Integer, String> responsible = new HashMap<Integer, String>() {
        {
            put(1, "Staff");
            put(2, "Department");
            put(3, "Organization");
            put(4, "Person");
        }
    };

    @RequestMapping(params = "type", method = RequestMethod.GET)
    public String index(@RequestParam("type") String type, Model model) {
        model.addAttribute("responsible", responsible);

        if(type.equals("archived"))
        {
            model.addAttribute("documents", documentService.getArchivedDocuments(getUser().getId()));
        } else {
            model.addAttribute("documents", documentService.getDocuments(type, getUser().getId()));
        }

        model.addAttribute("documentSubTypes", documentTypeService.getByInternalName(type).getDocumentSubTypes());
        model.addAttribute("type", type);

        return "/doc/document/index";
    }

    @RequestMapping(value = "/new", params = {"type", "subtype"}, method = RequestMethod.GET)
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

        model.addAttribute("documentState",  document.getDocumentState().toString());

        model.addAttribute("staff", accountService.getStaff());
        model.addAttribute("department", accountService.getDepartments());
        model.addAttribute("organization", accountService.getOrganizations());
        model.addAttribute("person", accountService.getPerson());

        return "/doc/document/" + document.getDocumentType().getInternalName();
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editDocument(@PathVariable("id") Long id, Model model) {

        Document document = documentService.findById(id);

        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);

        model.addAttribute("staff", accountService.getStaff());
        model.addAttribute("department", accountService.getDepartments());
        model.addAttribute("organization", accountService.getOrganizations());
        model.addAttribute("person", accountService.getPerson());

        model.addAttribute("documentState", document.getDocumentState().toString());

        return "/doc/document/" + document.getDocumentType().getInternalName();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveDocument(@ModelAttribute("document") Document document,
                               @RequestParam("action") String action,
                               @RequestParam("senderFile") MultipartFile senderFile,
                               @RequestParam("receiverFile") MultipartFile receiverFile) throws IOException {

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

        String docType = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();

        if(docType.equals("internal"))
        {
            saveInternalDocument(document, action);
        }
        else if(docType.equals("incoming"))
        {
            saveIncomingDocument(document, action);
        }
        else
        {
            saveOutgoingDocument(document, action);
        }

        return "redirect:/doc?type=" + docType;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewDocument(@PathVariable("id") Long id, Model model) {

        model.addAttribute("document", documentService.findById(id));
        model.addAttribute("responsible", responsible);

        return "/doc/document/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteDocument(@PathVariable("id") Long id) {
        Document document = documentService.findById(id);
        String docType = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();

        for(Task task : taskService.getTasksByObjectId(document.getId()))
        {
            taskService.remove(task);
        }

        documentService.deleteById(document);
        return "redirect:/doc?type=" + docType;
    }

    private DispatchData setDispatchData(String internalName) {
        DocumentStatus documentStatus = documentStatusService.getByInternalName(internalName);

        DispatchData dispatchData = new DispatchData();
            dispatchData.setDescription(documentStatus.getName());
            if(documentStatus.getInternalName() == "create")
            {
                dispatchData.setParent(true);
            }
            else
            {
                dispatchData.setParent(false);
            }

            dispatchData.setDispatchBy(getUser());
            dispatchData.setDispatchTo(userService.findById(2L));
            dispatchData.setDispatchInitTime(new Date());
            dispatchData.setDispatchType(documentStatus);

        return dispatchData;
    }

    private Task addTask(String objectType, Document document, String action, Staff staff) {

        Task task = new Task();

        task.setSummary("Document : " + action.toUpperCase());
        task.setDescription(document.getDescription());
        task.setResolutionSummary(document.getDescription());
        task.setProgress(action);
        task.setIdentifiedByUserId(getUser().getId());
        task.setModifiedByUserId(getUser().getId());
        task.setAssignedToUserId(getUser(staff).getId());
        task.setIdentifiedDate(new Date());
        task.setObjectType(objectType);
        task.setObjectId(document.getId());
        task.setStatus(TaskStatus.OPEN);
        task.setPriority(TaskPriority.HIGH);
        task.setTargetResolutionDate(document.getReceiverDueDate() != null ? document.getReceiverDueDate() : new Date());
        task.setCreatedOn(new Date());
        task.setModifiedOn(new Date());
        task.setCreatedBy(getUser());

        return task;
    }

    private Document setER(Document document) {

        if(document.getSenderResponsible() != null)
            switch(document.getSenderResponsible().getResponsibleType()) {
                case 1:
                    for(Staff s : document.getSenderResponsible().getStaff())
                    {
                        document.getUsers().add(getUser(s));
                    }
                    break;
                case 2:
                    for(Department d : document.getSenderResponsible().getDepartments())
                    {
                        document.getUsers().add(getUser(d));
                    }
                    break;
                case 3:
                    for(Organization o : document.getSenderResponsible().getOrganizations())
                    {
                        document.getUsers().add(getUser(o));
                    }
                    break;
                case 4:
                    for(Person p : document.getSenderResponsible().getPerson())
                    {
                        document.getUsers().add(getUser(p));
                    }
                    break;
                default:
                    break;
            }

        if(document.getSenderExecutor() != null)
            switch(document.getSenderExecutor().getExecutorType()) {
                case 1:
                    for(Staff s : document.getSenderExecutor().getStaff())
                    {
                        document.getUsers().add(getUser(s));
                    }
                    break;
                case 2:
                    for(Department d : document.getSenderExecutor().getDepartments())
                    {
                        document.getUsers().add(getUser(d));
                    }
                    break;
                case 3:
                    for(Organization o : document.getSenderExecutor().getOrganizations())
                    {
                        document.getUsers().add(getUser(o));
                    }
                    break;
                case 4:
                    for(Person p : document.getSenderExecutor().getPerson())
                    {
                        document.getUsers().add(getUser(p));
                    }
                    break;
                default:
                    break;
            }

        if(document.getReceiverResponsible() != null)
            switch(document.getReceiverResponsible().getResponsibleType()) {
                case 1:
                    for(Staff s : document.getReceiverResponsible().getStaff())
                    {
                        document.getUsers().add(getUser(s));
                    }
                    break;
                case 2:
                    for(Department d : document.getReceiverResponsible().getDepartments())
                    {
                        document.getUsers().add(getUser(d));
                    }
                    break;
                case 3:
                    for(Organization o : document.getReceiverResponsible().getOrganizations())
                    {
                        document.getUsers().add(getUser(o));
                    }
                    break;
                case 4:
                    for(Person p : document.getReceiverResponsible().getPerson())
                    {
                        document.getUsers().add(getUser(p));
                    }
                    break;
                default:
                    break;
            }

        if(document.getReceiverExecutor() != null)
            switch(document.getReceiverExecutor().getExecutorType()) {
                case 1:
                    for(Staff s : document.getReceiverExecutor().getStaff())
                    {
                        document.getUsers().add(getUser(s));
                    }
                    break;
                case 2:
                    for(Department d : document.getReceiverExecutor().getDepartments())
                    {
                        document.getUsers().add(getUser(d));
                    }
                    break;
                case 3:
                    for(Organization o : document.getReceiverExecutor().getOrganizations())
                    {
                        document.getUsers().add(getUser(o));
                    }
                    break;
                case 4:
                    for(Person p : document.getReceiverExecutor().getPerson())
                    {
                        document.getUsers().add(getUser(p));
                    }
                    break;
                default:
                    break;
            }
        return document;
    }

    private void saveInternalDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        DispatchData dispatchData = setDispatchData(action);

        document.setGeneralStatus(documentStatus);
        document.getUsers().add(getUser());
        document = setER(document);

        if(document.getId() == null)
        {
            document.getSenderDispatchData().add(dispatchData);
            document.setSenderStatus(documentStatus);

            if(action.equals("toapprove"))
            {
                document.setDocumentState(State.WAITING_FOR_APPROVAL);
                documentService.save(document);

                for(final Staff s : document.getSenderResponsible().getStaff()) {
                    taskService.add(addTask("/doc/edit/", document, action, s));
                }
            } else {
                documentService.save(document);
            }
        }
        else
        {
            Document doc = documentService.findById(document.getId());

            document.setSenderDispatchData(doc.getSenderDispatchData());            // add existing Sender DispatchData
            document.setSenderStatus(doc.getSenderStatus());                        // add existing Sender DocumentStatus

            if (action.equals("create")) {
                document.setDocumentState(State.DRAFT);
            } else {
                document.setDocumentState(doc.getDocumentState().next(Transition.NONE));
            }

            if (action.equals("archive")) {
                document.setDocumentState(State.ARCHIVED);
            }

            if(doc.getSenderStatus().getInternalName().equals("approve"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                if(action.equals("accept"))
                {
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());
                    document.setDocumentState(State.ACCEPTED);
                }
            }
            else
            {   // Sender Data
                document.getSenderDispatchData().add(dispatchData);                 // add new Sender DispatchData
                document.setSenderStatus(documentStatus);                           // update Sender DocumentStatus

                if(action.equals("toapprove"))
                {
                    document.setDocumentState(State.WAITING_FOR_APPROVAL);
                    for(final Staff s : doc.getSenderResponsible().getStaff()) {
                        taskService.add(addTask("/doc/edit/", document, action, s));
                    }
                }

                if(action.equals("approve"))
                {
                    document.setDocumentState(State.APPROVED);
                    document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                    document.setSenderRegisteredDate(new Date());
                }
            }
            documentService.update(document);
        }
    }

    private void saveIncomingDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        DispatchData dispatchData = setDispatchData(action);

        document.setGeneralStatus(documentStatus);
        document.getUsers().add(getUser());
        document = setER(document);

        if(document.getId() == null)
        {
            document.getSenderDispatchData().add(dispatchData);                     // add new Sender DispatchData
            document.setSenderStatus(documentStatus);                               // update Sender DocumentStatus

            if(action.equals("register"))
            {
                document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                document.setSenderRegisteredDate(new Date());
                document.setDocumentState(State.REGISTERED);
                documentService.save(document);

                for(final Staff s : document.getSenderResponsible().getStaff()) {
                    taskService.add(addTask("/doc/edit/", document, action, s));
                }
            } else {
                documentService.save(document);
            }
        }
        else
        {
            Document doc = documentService.findById(document.getId());

            document.setSenderDispatchData(doc.getSenderDispatchData());            // add existing Sender DispatchData
            document.setSenderStatus(doc.getSenderStatus());                        // add existing Sender DocumentStatus

            if (action.equals("create")) {
                document.setDocumentState(State.DRAFT);
            } else {
                document.setDocumentState(doc.getDocumentState().next(Transition.NONE));
            }

            if (action.equals("archive")) {
                document.setDocumentState(State.ARCHIVED);
            }

            if(doc.getSenderStatus().getInternalName().equals("register"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                if(action.equals("accept"))
                {
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());
                    document.setDocumentState(State.ACCEPTED);
                }
            }
            else
            {   // Sender Data
                document.getSenderDispatchData().add(dispatchData);                 // add new Sender DispatchData
                document.setSenderStatus(documentStatus);                           // update Sender DocumentStatus

                if(action.equals("register"))
                {
                    document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                    document.setSenderRegisteredDate(new Date());
                    document.setDocumentState(State.REGISTERED);

                    for(final Staff s : doc.getSenderResponsible().getStaff()) {
                        taskService.add(addTask("/doc/edit/", document, action, s));
                    }
                }
            }
            documentService.update(document);
        }
    }

    private void saveOutgoingDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        DispatchData dispatchData = setDispatchData(action);

        document.setGeneralStatus(documentStatus);
        document.getUsers().add(getUser());
        document = setER(document);

        if(document.getId() == null)
        {
            document.getSenderDispatchData().add(dispatchData);
            document.setSenderStatus(documentStatus);

            if(action.equals("toapprove"))
            {
                document.setDocumentState(State.WAITING_FOR_APPROVAL);
                documentService.save(document);

                for(final Staff s : document.getSenderResponsible().getStaff()) {
                    taskService.add(addTask("/doc/edit/", document, action, s));
                }
            } else {
                documentService.save(document);
            }
        }
        else
        {
            Document doc = documentService.findById(document.getId());

            document.setSenderDispatchData(doc.getSenderDispatchData());            // add existing Sender DispatchData
            document.setSenderStatus(doc.getSenderStatus());                        // add existing Sender DocumentStatus

            if (action.equals("create")) {
                document.setDocumentState(State.DRAFT);
            } else {
                document.setDocumentState(doc.getDocumentState().next(Transition.NONE));
            }

            if (action.equals("archive")) {
                document.setDocumentState(State.ARCHIVED);
            }

            if(doc.getSenderStatus().getInternalName().equals("approve"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                if(action.equals("register"))
                {
                    document.setDocumentState(State.REGISTERED);
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());
                }
            }
            else
            {   // Sender Data
                document.getSenderDispatchData().add(dispatchData);                 // add new Sender DispatchData
                document.setSenderStatus(documentStatus);                           // update Sender DocumentStatus

                if(action.equals("toapprove"))
                {
                    document.setDocumentState(State.WAITING_FOR_APPROVAL);
                    for(final Staff s : doc.getSenderResponsible().getStaff()) {
                        taskService.add(addTask("/doc/edit/", document, action, s));
                    }
                }

                if(action.equals("approve"))
                {
                    document.setDocumentState(State.APPROVED);
                    document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                    document.setSenderRegisteredDate(new Date());
                }
            }
            documentService.update(document);
        }
    }
}
