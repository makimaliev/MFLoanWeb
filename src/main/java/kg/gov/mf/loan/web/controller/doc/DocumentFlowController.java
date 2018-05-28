package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.DocumentStatusService;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.doc.model.Transition;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
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
    private AttachmentService attachmentService;
    private HttpServletRequest request;

    @Autowired
    public DocumentFlowController(DocumentService documentService,
                                  DocumentTypeService documentTypeService,
                                  DocumentSubTypeService documentSubTypeService,
                                  DocumentStatusService documentStatusService,
                                  AccountService accountService,
                                  TaskService taskService,
                                  AttachmentService attachmentService,
                                  HttpServletRequest request)
    {
        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
        this.documentSubTypeService = documentSubTypeService;
        this.documentStatusService = documentStatusService;
        this.accountService = accountService;
        this.taskService = taskService;
        this.attachmentService = attachmentService;
        this.request = request;
    }
    //endregion
    //region TYPE
    enum TYPE { internal, incoming, outgoing}
    String[][][] actionString =
            {
                {
                    {"create", "request"},
                    {"approve", "reject"},
                    {"accept"},
                    {},
                    {},
                    {"start"},
                    {"done"},
                    {"archive"}
                },
                {
                    {"create", "register"},
                    {},
                    {},
                    {},
                    {"accept"},
                    {"start"},
                    {"done"},
                    {"archive"}
                },
                {
                    {"create", "request"},
                    {"approve", "reject"},
                    {"register"},
                    {},
                    {"archive"}
                }
            };
    //endregion
    final static Map<Integer, String> responsible = new HashMap<Integer, String>() {
        {
            put(1, "Сотрудник");
            put(2, "Отдел");
            put(3, "Организация");
            put(4, "ФИЗ Лицо");
        }
    };

    @RequestMapping(params = "type", method = RequestMethod.GET)
    public String index(@RequestParam("type") String type, Model model) {

        String title = documentTypeService.getByInternalName(type).getName();

        if(type.equals("archived"))
        {
            model.addAttribute("documents", documentService.getArchivedDocuments(getUser().getId()));
        } else {
            model.addAttribute("documents", documentService.getDocuments(type, getUser().getId()));
        }

        model.addAttribute("title", title);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentSubTypes", documentTypeService.getByInternalName(type).getDocumentSubTypes());
        model.addAttribute("type", type);

        return "/doc/document/index";
    }

    @RequestMapping(value = "/new", params = {"type", "subtype"}, method = RequestMethod.GET)
    public String newDocument(@RequestParam("type") String type, @RequestParam("subtype") String subtype, Model model) {

        Document document = new Document();
        document.setOwner(getUser().getId());
        document.setDocumentType(documentTypeService.getByInternalName(type));
        document.setDocumentSubType(documentSubTypeService.getByInternalName(subtype));

        List<DocumentStatus> actions = new ArrayList<>();

        if(type.equals("internal") | type.equals("outgoing"))
        {
            Responsible resp = new Responsible();
            resp.setResponsibleType(1);
            document.setSenderResponsible(resp);

            Executor exec = new Executor();
            exec.setExecutorType(1);
            document.setSenderExecutor(exec);

            actions.add(documentStatusService.getByInternalName("create"));
            actions.add(documentStatusService.getByInternalName("request"));
        }
        else
        {
            actions.add(documentStatusService.getByInternalName("create"));
            actions.add(documentStatusService.getByInternalName("register"));
        }

        model.addAttribute("actions", actions);
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
        String documentType = document.getDocumentType().getInternalName();

        int row = TYPE.valueOf(documentType).ordinal();
        int col = document.getDocumentState().ordinal();

        List<DocumentStatus> actions = new ArrayList<>();
        for(String action : actionString[row][col])
        {
            actions.add(documentStatusService.getByInternalName(action));
        }

        model.addAttribute("actions", actions);
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);
        model.addAttribute("staff", accountService.getStaff());
        model.addAttribute("department", accountService.getDepartments());
        model.addAttribute("organization", accountService.getOrganizations());
        model.addAttribute("person", accountService.getPerson());
        model.addAttribute("documentState", document.getDocumentState().toString());

        return "/doc/document/" + documentType;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveDocument(@ModelAttribute("document") Document document,
                               @RequestParam("action") String action,
                               @RequestParam("senderFile") MultipartFile senderFile,
                               @RequestParam("receiverFile") MultipartFile receiverFile) throws IOException {

        //region Attachment
        String path = request.getServletContext().getRealPath("/");
        //region Sender File
        if (!senderFile.isEmpty())
        {
            try {
                String fileName = senderFile.getOriginalFilename();
                String fsName = UUID.randomUUID().toString();

                File file = new File(path + fsName);
                byte[] bytes = senderFile.getBytes();
                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(file));
                buffStream.write(bytes);
                buffStream.close();

                Attachment attachment = new Attachment();
                attachment.setName(fileName);
                attachment.setInternalName(fsName);

                document.setSenderAttachment(attachment);
                document.setSenderFileName(fileName);
            } catch (Exception e) {
            }
        }
        //endregion
        //region Receiver File
        if (!receiverFile.isEmpty())
        {
            try {
                String fileName = receiverFile.getOriginalFilename();
                String fsName = UUID.randomUUID().toString();

                File file = new File(path + fsName);
                byte[] bytes = receiverFile.getBytes();
                BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(file));
                buffStream.write(bytes);
                buffStream.close();

                Attachment attachment = new Attachment();
                attachment.setName(fileName);
                attachment.setInternalName(fsName);

                document.setReceiverAttachment(attachment);
                document.setReceiverFileName(fileName);
            } catch (Exception e) {
            }
        }
        //endregion
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

        for(Task task : taskService.getTasksByObjectId(document.getId(), getUser().getId()))
        {
            taskService.remove(task);
        }
        documentService.deleteById(document);

        /*
        if(document.getDocumentState().toString() == "DRAFT")
        {
            for(Task task : taskService.getTasksByObjectId(document.getId(), getUser().getId()))
            {
                taskService.remove(task);
            }
            documentService.deleteById(document);
        }
        */
        return "redirect:/doc?type=" + docType;
    }

    @RequestMapping(value = "/download/{attachmentId}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("attachmentId") Long attachmentId) throws IOException {

        Attachment attachment = attachmentService.findById(attachmentId);
        String path = request.getServletContext().getRealPath("/");

        File file = new File(path + attachment.getInternalName());
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());

        if(mimeType==null){
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + attachment.getName() +"\""));
        response.setContentLength((int)file.length());

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

    private DispatchData setDispatchData(String internalName) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(internalName);
        DispatchData dispatchData = new DispatchData();
        dispatchData.setDescription(documentStatus.getName());
        dispatchData.setDispatchBy(getUser());
        dispatchData.setDispatchTo(userService.findById(2L));
        dispatchData.setDispatchInitTime(new Date());
        dispatchData.setDispatchType(documentStatus);

        if(documentStatus.getInternalName() == "create")
        {
            dispatchData.setParent(true);
        }
        else
        {
            dispatchData.setParent(false);
        }

        return dispatchData;
    }
    private Task addTask(Document document, Object responsible) {

        Integer responsibleType = document.getReceiverResponsible().getResponsibleType();

        Task task = new Task();

        task.setSummary("Document : " + document.getGeneralStatus().getInternalName().toUpperCase());
        task.setDescription(document.getDescription());
        task.setResolutionSummary(document.getDescription());
        task.setProgress(document.getGeneralStatus().getInternalName().toUpperCase());
        task.setIdentifiedByUserId(getUser().getId());
        task.setModifiedByUserId(getUser().getId());
        task.setObjectType("/doc/edit/" + document.getId());
        task.setObjectId(document.getId());
        task.setTargetResolutionDate(document.getReceiverDueDate() != null ? document.getReceiverDueDate() : new Date());
        task.setCreatedBy(getUser());

        if(responsibleType == 1)
        {
            task.setAssignedToUserId(getUser((Staff)responsible).getId());
        }
        else if(responsibleType == 2)
        {
            task.setAssignedToUserId(getUser((Department)responsible).getId());
        }
        else if(responsibleType == 3)
        {
            task.setAssignedToUserId(getUser((Organization) responsible).getId());
        }
        else
        {
            task.setAssignedToUserId(getUser((Person)responsible).getId());
        }

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

        DispatchData dispatchData = setDispatchData(action);
        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        document.setDocumentState(document.getDocumentState().next(Transition.valueOf(action.toUpperCase())));

        document = setER(document);
        document.setGeneralStatus(documentStatus);
        document.getUsers().add(getUser());

        if(document.getId() == null)
        {
            document.getSenderDispatchData().add(dispatchData);
            document.setSenderStatus(documentStatus);

            if(action.equals("request"))
            {
                documentService.save(document);
                for(final Staff s : document.getSenderResponsible().getStaff()) {
                    taskService.add(addTask(document, s));
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

            document.setSenderAttachment(doc.getSenderAttachment());
            document.setReceiverAttachment(doc.getReceiverAttachment());

            if(doc.getSenderStatus().getInternalName().equals("approve"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                if(action.equals("accept"))
                {
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());

                    Integer responsibleType = document.getReceiverResponsible().getResponsibleType();
                    if(responsibleType == 1)
                    {
                        for(Staff s : document.getReceiverResponsible().getStaff()) {
                            taskService.add(addTask(document, s));
                        }
                    }
                    else if(responsibleType == 2)
                    {
                        for(Department d : document.getReceiverResponsible().getDepartments()) {
                            taskService.add(addTask(document, d));
                        }
                    }
                    else if(responsibleType == 3)
                    {
                        for(Organization o : document.getReceiverResponsible().getOrganizations()) {
                            taskService.add(addTask(document, o));
                        }
                    }
                    else
                    {
                        for(Person p : document.getReceiverResponsible().getPerson()) {
                            taskService.add(addTask(document, p));
                        }
                    }
                }

                if(action.equals("start"))
                {
                    taskService.completeTask(document.getId(), getUser());
                }
            }
            else
            {   // Sender Data
                document.getSenderDispatchData().add(dispatchData);                 // add new Sender DispatchData
                document.setSenderStatus(documentStatus);                           // update Sender DocumentStatus

                if(action.equals("request"))
                {
                    for(final Staff s : doc.getSenderResponsible().getStaff()) {
                        taskService.add(addTask(document, s));
                    }
                }

                if(action.equals("approve"))
                {
                    document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                    document.setSenderRegisteredDate(new Date());
                    taskService.completeTask(document.getId(), getUser());
                }
            }
            documentService.update(document);
        }
    }
    private void saveIncomingDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        DispatchData dispatchData = setDispatchData(action);

        document = setER(document);
        document.setGeneralStatus(documentStatus);
        document.getUsers().add(getUser());

        if(document.getId() == null)
        {
            document.getSenderDispatchData().add(dispatchData);                     // add new Sender DispatchData
            document.setSenderStatus(documentStatus);                               // update Sender DocumentStatus
            document.setDocumentState(document.getDocumentState().next(Transition.valueOf(action.toUpperCase())));

            if(action.equals("register"))
            {
                document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                document.setSenderRegisteredDate(new Date());
                documentService.save(document);

                for(final Staff s : document.getSenderResponsible().getStaff()) {
                    taskService.add(addTask(document, s));
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
            document.setDocumentState(doc.getDocumentState().next(Transition.valueOf(action.toUpperCase())));

            if(doc.getSenderStatus().getInternalName().equals("register"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                if(action.equals("accept"))
                {
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());

                    Integer responsibleType = document.getReceiverResponsible().getResponsibleType();
                    if(responsibleType == 1)
                    {
                        for(Staff s : document.getReceiverResponsible().getStaff()) {
                            taskService.add(addTask(document, s));
                        }
                    }
                    else if(responsibleType == 2)
                    {
                        for(Department d : document.getReceiverResponsible().getDepartments()) {
                            taskService.add(addTask(document, d));
                        }
                    }
                    else if(responsibleType == 3)
                    {
                        for(Organization o : document.getReceiverResponsible().getOrganizations()) {
                            taskService.add(addTask(document, o));
                        }
                    }
                    else
                    {
                        for(Person p : document.getReceiverResponsible().getPerson()) {
                            taskService.add(addTask(document, p));
                        }
                    }
                }

                if(action.equals("start"))
                {
                    taskService.completeTask(document.getId(), getUser());
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

                    Integer responsibleType = document.getReceiverResponsible().getResponsibleType();
                    if(responsibleType == 1)
                    {
                        for(Staff s : document.getReceiverResponsible().getStaff()) {
                            taskService.add(addTask(document, s));
                        }
                    }
                    else if(responsibleType == 2)
                    {
                        for(Department d : document.getReceiverResponsible().getDepartments()) {
                            taskService.add(addTask(document, d));
                        }
                    }
                    else if(responsibleType == 3)
                    {
                        for(Organization o : document.getReceiverResponsible().getOrganizations()) {
                            taskService.add(addTask(document, o));
                        }
                    }
                    else
                    {
                        for(Person p : document.getReceiverResponsible().getPerson()) {
                            taskService.add(addTask(document, p));
                        }
                    }
                }
            }
            documentService.update(document);
        }
    }
    private void saveOutgoingDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        DispatchData dispatchData = setDispatchData(action);

        document = setER(document);
        document.setGeneralStatus(documentStatus);
        document.getUsers().add(getUser());

        if(document.getId() == null)
        {
            document.getSenderDispatchData().add(dispatchData);
            document.setSenderStatus(documentStatus);

            document.setDocumentState(document.getDocumentState().next(Transition.valueOf(action.toUpperCase())));

            if(action.equals("request"))
            {
                document = documentService.save(document);

                for(final Staff s : document.getSenderResponsible().getStaff()) {
                    taskService.add(addTask(document, s));
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

            document.setDocumentState(doc.getDocumentState().next(Transition.valueOf(action.toUpperCase())));

            if(doc.getSenderStatus().getInternalName().equals("approve"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                if(action.equals("register"))
                {
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());
                }

                if(action.equals("done"))
                {
                    taskService.completeTask(document.getId(), getUser());
                }
            }
            else
            {   // Sender Data
                document.getSenderDispatchData().add(dispatchData);                 // add new Sender DispatchData
                document.setSenderStatus(documentStatus);                           // update Sender DocumentStatus

                if(action.equals("request"))
                {
                    for(final Staff s : doc.getSenderResponsible().getStaff()) {
                        taskService.add(addTask(document, s));
                    }
                }

                if(action.equals("approve"))
                {
                    document.setSenderRegisteredNumber("DOCS-" + new Random().nextInt(100));
                    document.setSenderRegisteredDate(new Date());
                    taskService.completeTask(document.getId(), getUser());
                }
            }
            documentService.update(document);
        }
    }
}
