package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.DocumentStatusService;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.doc.model.Transition;
import kg.gov.mf.loan.manage.model.order.CreditOrder;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.service.TaskService;
import kg.gov.mf.loan.web.util.Pager;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
    private StaffService staffService;
    private RegisterService registerService;

    @Autowired
    public DocumentFlowController(DocumentService documentService,
                                  DocumentTypeService documentTypeService,
                                  DocumentSubTypeService documentSubTypeService,
                                  DocumentStatusService documentStatusService,
                                  AccountService accountService,
                                  TaskService taskService,
                                  AttachmentService attachmentService,
                                  HttpServletRequest request,
                                  StaffService staffService,
                                  RegisterService registerService)
    {
        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
        this.documentSubTypeService = documentSubTypeService;
        this.documentStatusService = documentStatusService;
        this.accountService = accountService;
        this.taskService = taskService;
        this.attachmentService = attachmentService;
        this.request = request;
        this.staffService = staffService;
        this.registerService = registerService;
    }
    //endregion

    private static final int BUTTONS_TO_SHOW = 5;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 10;
    private static final int[] PAGE_SIZES = {5, 10, 20, 50, 100};

    private final static Map<Integer, String> responsible = new HashMap<Integer, String>() {
        {
            put(1, "Сотрудник");
            put(2, "Отдел");
            put(3, "Организация");
            put(4, "ФИЗ Лицо");
        }
    };
    private String path =  SystemUtils.IS_OS_LINUX ? "/opt/uploads/" : "C:/temp/";

    //region TYPE
    private enum TYPE { internal, incoming, outgoing}
    private String[][][] actionString =
            {
                {
                    {"create", "request"},
                    {"request"},
                    {"approve", "reject"},
                    {"accept", "reject"},
                    {},
                    {},
                    {"send"},
                    {"start", "reject"},
                    {"done"}
                },
                {
                    {"create", "register"},
                    {"register"},
                    {},
                    {},
                    {},
                    {"accept", "reject"},
                    {"send"},
                    {"start", "reject"},
                    {"done"}
                },
                {
                    {"create", "request"},
                    {"request"},
                    {"approve", "reject"},
                    {"register", "done"},
                    {},
                    {"register", "done"}
                }
            };
    //endregion

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam("type") String type, /* @RequestParam("pageSize") Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page, */ Model model) {
        /*
        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        List<Document> documents = type.equals("archived") ? documentService.getArchivedDocuments(getUser().getId()) : documentService.getDocuments(type, getUser().getId());
                //documentService.listByParam("id", evalPage*evalPageSize, evalPageSize);
        int count = documentService.findAll().size();

        Pager pager = new Pager(count/evalPageSize+1, evalPage, BUTTONS_TO_SHOW);

        model.addAttribute("count", count/evalPageSize+1);
        model.addAttribute("selectedPageSize", evalPageSize);
        model.addAttribute("pageSizes", PAGE_SIZES);
        model.addAttribute("pager", pager);
        model.addAttribute("current", evalPage);
        */
        List<Document> documents = type.equals("incoming") ? documentService.getInvolvedDocuments(getUser().getId()) : documentService.getDocuments(type, getUser().getId());
        model.addAttribute("documents", documents);
        String title = documentTypeService.getByInternalName(type).getName();
        //model.addAttribute("documents", documentService.getDocuments(type, getUser().getId()));
        model.addAttribute("title", title);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentSubTypes", documentTypeService.getByInternalName(type).getDocumentSubTypes());
        model.addAttribute("type", type);

        return "/doc/document/index";
    }

    @RequestMapping(value = "/new/{subType}", method = RequestMethod.GET)
    public String newDocument(@PathVariable("subType") String type, Model model) {

        DocumentType documentType = documentSubTypeService.getByInternalName(type).getDocumentType();
        String currentView = documentType.getInternalName();

        int row = TYPE.valueOf(currentView).ordinal();

        List<DocumentStatus> actions = new ArrayList<>();
        for(String action : actionString[row][0])
        {
            actions.add(documentStatusService.getByInternalName(action));
        }

        Document document = new Document();
        document.setOwner(getUser().getId());
        document.setDocumentType(documentType);
        document.setDocumentSubType(documentSubTypeService.getByInternalName(type));

        if(currentView.equals("internal"))
        {
            Responsible resp = new Responsible();
            resp.setResponsibleType(1);
            document.setSenderResponsible(resp);

            Staff staff = staffService.findById(getUser().getId());

            Executor exec = new Executor();
            exec.setExecutorType(1);
            exec.getStaff().add(staff);
            document.setSenderExecutor(exec);
        }
        else if(currentView.equals("outgoing"))
        {

        }

        model.addAttribute("actions", actions);
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentState",  document.getDocumentState().toString());
        model.addAttribute("staff", accountService.getStaff());
        model.addAttribute("department", accountService.getDepartments());
        model.addAttribute("organization", accountService.getOrganizations());
        model.addAttribute("person", accountService.getPerson());

        return "/doc/document/" + currentView + "/" + currentView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editDocument(@PathVariable("id") Long id, Model model) {

        Document document = documentService.findById(id);
        String currentView = document.getDocumentType().getInternalName();

        int row = TYPE.valueOf(currentView).ordinal();
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

        return "/doc/document/" + currentView + "/" + currentView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveDocument(@ModelAttribute("document") Document document,
                               @RequestParam("action") String action,
                               @RequestParam("senderFiles") MultipartFile[] senderFiles,
                               @RequestParam("receiverFiles") MultipartFile[] receiverFiles) {

        String docType = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();

        //region ATA
        /*
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        Iterator<String> itr = multipartRequest.getFileNames();

        try {

            while (itr.hasNext()) {

                String uuid = UUID.randomUUID().toString();
                String fsname = uuid + ".atach";

                String uploadedFile = itr.next();
                MultipartFile file = multipartRequest.getFile(uploadedFile);

                byte[] bytes = file.getBytes();
                Path p = Paths.get(path + fsname);
                Files.write(p, bytes);

                Attachment attachment = new Attachment();
                attachment.setName(file.getOriginalFilename());
                attachment.setInternalName(fsname);
                attachment.setMimeType(file.getContentType());

                document.getSenderAttachment().add(attachment);
            }
        } catch (Exception e)
        {

        }
        */
        //endregion

        //region ATTACHMENTS
        for(MultipartFile multipartFile : senderFiles)
        {
            if(multipartFile.getOriginalFilename().length() > 1) {

                try {
                    String uuid = UUID.randomUUID().toString();
                    String fsname = uuid + ".atach";

                    byte[] bytes = multipartFile.getBytes();
                    Path p = Paths.get(path + fsname);
                    Files.write(p, bytes);

                    /*
                    Attachment attachment = new Attachment();
                    attachment.setName(multipartFile.getOriginalFilename());
                    attachment.setInternalName(fsname);
                    attachment.setMimeType(multipartFile.getContentType());
                    */


                    //document.getSenderAttachment().add(attachment);
                } catch (Exception e) {
                    String str = "You failed to upload " + multipartFile.getOriginalFilename() + ": " + e.getMessage();
                }
            }
        }

        /*
        for(MultipartFile multipartFile : receiverFiles)
        {
            if(!multipartFile.getOriginalFilename().isEmpty()) {

                String uuid = UUID.randomUUID().toString();
                String fsname = uuid + ".atach";

                Attachment attachment = new Attachment();
                attachment.setName(multipartFile.getOriginalFilename());
                attachment.setInternalName(fsname);
                attachment.setMimeType(multipartFile.getContentType());

                byte[] bytes = multipartFile.getBytes();
                Path p = Paths.get(path + fsname);
                Files.write(p, bytes);

                document.getReceiverAttachment().add(attachment);
            }
        }
        */
        //endregion

        /*
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
        */
        return "redirect:/doc?type=" + docType;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String viewDocument(@PathVariable("id") Long id, Model model) {

        Document document = documentService.findById(id);
        String currentView = document.getDocumentType().getInternalName();

        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);

        return "/doc/document/" + currentView + "/" + "view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteDocument(@PathVariable("id") Long id) {

        Document document = documentService.findById(id);

        for(Task task : taskService.getTasksByObjectId(document.getId()))
        {
            taskService.remove(task);
        }

        documentService.deleteById(document);

        /*
        if(document.getDocumentState().toString() == "DRAFT")
        {
            for(Task task : taskService.getTasksByObjectId(document.getId()))
            {
                taskService.remove(task);
            }
            documentService.deleteById(document);
        }
        */
        return "redirect:/doc?type=" + document.getDocumentType().getInternalName();
    }

    @RequestMapping(value = "/download/{attachmentId}", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response, @PathVariable("attachmentId") Long attachmentId) throws IOException {

        Attachment attachment = attachmentService.findById(attachmentId);

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

    private DispatchData setDispatchData(String internalName, Document document) {

        DispatchData dispatchData = new DispatchData();
        dispatchData.setDescription(document.getDocumentState().stringValue());
        dispatchData.setDispatchBy(getUser());
        dispatchData.setDispatchTo(userService.findById(2L));
        dispatchData.setDispatchInitTime(new Date());
        dispatchData.setDispatchType(document.getDocumentState());

        if(document.getDocumentState() == State.NEW) //documentStatus.getInternalName() == "create"
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
        task.setSummary("Документ : ");
        //task.setSummary("Документ : " + document.getDocumentState().next(Transition.valueOf(document.getGeneralStatus().getInternalName().toUpperCase())));
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

        DispatchData dispatchData = setDispatchData(action, document);
        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);

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
            document.setDocumentState(doc.getDocumentState().next(Transition.valueOf(action.toUpperCase())));

            document.setSenderDispatchData(doc.getSenderDispatchData());            // add existing Sender DispatchData
            document.setSenderStatus(doc.getSenderStatus());                        // add existing Sender DocumentStatus

            //document.setSenderAttachment(doc.getSenderAttachment());
            //document.setReceiverAttachment(doc.getReceiverAttachment());

            if(doc.getSenderStatus().getInternalName().equals("approve"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                taskService.completeTask(document.getId(), getUser());

                if(action.equals("accept"))
                {
                    document.setReceiverRegisteredNumber(registerService.generateRegistrationNumber());
                    document.setReceiverRegisteredDate(new Date());
                    //region Add task
                    for(Staff s : document.getReceiverExecutor().getStaff()) {
                        taskService.add(addTask(document, s));
                    }
                }

                if(action.equals("start"))
                {
                    for(Staff s : document.getReceiverExecutor().getStaff()) {
                        taskService.add(addTask(document, s));
                    }
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

                    document.setSenderRegisteredNumber(registerService.generateRegistrationNumber());
                    document.setSenderRegisteredDate(new Date());
                    taskService.completeTask(document.getId(), getUser());
                    //region Add task
                    Integer responsibleType = document.getReceiverResponsible().getResponsibleType();
                    if(responsibleType == 1)
                    {
                        for(Staff s : document.getReceiverResponsible().getStaff()) {
                            taskService.add(addTask(document, s));
                        }
                    }
                    else
                    {
                        for(Department d : document.getReceiverResponsible().getDepartments()) {
                            taskService.add(addTask(document, d));
                        }
                    }
                    //endregion
                }
            }
            documentService.update(document);
        }
    }
    private void saveIncomingDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        DispatchData dispatchData = setDispatchData(action, document);

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

            if(doc.getSenderStatus().getInternalName().equals("register"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                taskService.completeTask(document.getId(), getUser());

                if(action.equals("accept"))
                {
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());
                    //region Add Task
                    Integer responsibleType = document.getReceiverResponsible().getResponsibleType();
                    if(responsibleType == 1)
                    {
                        for(Staff s : document.getReceiverExecutor().getStaff()) {
                            taskService.add(addTask(document, s));
                        }
                    }
                    else if(responsibleType == 2)
                    {
                        for(Department d : document.getReceiverExecutor().getDepartments()) {
                            taskService.add(addTask(document, d));
                        }
                    }
                    else if(responsibleType == 3)
                    {
                        for(Organization o : document.getReceiverExecutor().getOrganizations()) {
                            taskService.add(addTask(document, o));
                        }
                    }
                    else
                    {
                        for(Person p : document.getReceiverExecutor().getPerson()) {
                            taskService.add(addTask(document, p));
                        }
                    }
                    //endregion
                }

                if(action.equals("start"))
                {
                    //region Add Task
                    Integer responsibleType = document.getReceiverResponsible().getResponsibleType();
                    if(responsibleType == 1)
                    {
                        for(Staff s : document.getReceiverExecutor().getStaff()) {
                            taskService.add(addTask(document, s));
                        }
                    }
                    else if(responsibleType == 2)
                    {
                        for(Department d : document.getReceiverExecutor().getDepartments()) {
                            taskService.add(addTask(document, d));
                        }
                    }
                    else if(responsibleType == 3)
                    {
                        for(Organization o : document.getReceiverExecutor().getOrganizations()) {
                            taskService.add(addTask(document, o));
                        }
                    }
                    else
                    {
                        for(Person p : document.getReceiverExecutor().getPerson()) {
                            taskService.add(addTask(document, p));
                        }
                    }
                    //endregion
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
                    //region ADD Task
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
                    //endregion
                }
            }
            documentService.update(document);
        }
    }
    private void saveOutgoingDocument(Document document, String action) {

        DispatchData dispatchData = setDispatchData(action, document);
        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);

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
            document.setDocumentState(doc.getDocumentState().next(Transition.valueOf(action.toUpperCase())));

            document.setSenderDispatchData(doc.getSenderDispatchData());            // add existing Sender DispatchData
            document.setSenderStatus(doc.getSenderStatus());                        // add existing Sender DocumentStatus

            //document.setSenderAttachment(doc.getSenderAttachment());
            //document.setReceiverAttachment(doc.getReceiverAttachment());

            if(doc.getSenderStatus().getInternalName().equals("approve"))
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());    // add existing Receiver DispatchData
                document.getReceiverDispatchData().add(dispatchData);               // add new Receiver DispatchData
                document.setReceiverStatus(documentStatus);                         // update Receiver DocumentStatus

                taskService.completeTask(document.getId(), getUser());

                if(action.equals("register"))
                {
                    document.setReceiverRegisteredNumber("DOCR-" + new Random().nextInt(100));
                    document.setReceiverRegisteredDate(new Date());
                    document.setDocumentState(State.DONE);
                    document.setGeneralStatus(documentStatusService.getByInternalName("done"));
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
                    //region Add task
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
                    //endregion
                }
            }
            documentService.update(document);
        }

    }
}