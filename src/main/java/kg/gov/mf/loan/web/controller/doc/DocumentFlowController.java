package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.DocumentStatusService;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.doc.model.Transition;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/doc")
public class DocumentFlowController extends BaseController {

    class Result {

        public Long id;
        public String text;

        public Result(Long id, String text) {
            this.id = id;
            this.text = text;
        }
    }

    //region Dependencies
    private DocumentService documentService;
    private DocumentTypeService documentTypeService;
    private DocumentSubTypeService documentSubTypeService;
    private DocumentStatusService documentStatusService;
    private TaskService taskService;
    private RegisterService registerService;
    private AccountService accountService;

    @Autowired
    public DocumentFlowController(DocumentService documentService,
                                  DocumentTypeService documentTypeService,
                                  DocumentSubTypeService documentSubTypeService,
                                  DocumentStatusService documentStatusService,
                                  TaskService taskService,
                                  RegisterService registerService,
                                  AccountService accountService)
    {
        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
        this.documentSubTypeService = documentSubTypeService;
        this.documentStatusService = documentStatusService;
        this.taskService = taskService;
        this.registerService = registerService;
        this.accountService = accountService;
    }
    //endregion
    //region TYPE
    private Transition[][][] ACTIONS =
            {
                    {   // Internal
                            { Transition.REQUEST, Transition.TORECONCILE },     // NEW
                            { Transition.REQUEST, Transition.TORECONCILE },     // DRAFT
                            { Transition.RECONCILE, Transition.REJECT },        // PENDING
                            { Transition.APPROVE, Transition.REJECT },          // REQUESTED
                            { Transition.REQUEST, Transition.TORECONCILE },     // RECONCILED
                            { Transition.ACCEPT, Transition.REJECT },           // APPROVED
                            {},                                                 // REJECTED
                            {},                                                 // REGISTERED
                            { Transition.SEND },                                // ACCEPTED
                            { Transition.START, Transition.SEND },              // SENT
                            { Transition.DONE }                                 // STARTED
                    },
                    {   // Incoming
                            { Transition.REGISTER },                            // NEW
                            { Transition.REGISTER },                            // DRAFT
                            {},                                                 // PENDING
                            {},                                                 // REQUESTED
                            {},                                                 // RECONCILED
                            {},                                                 // APPROVED
                            {},                                                 // REJECTED
                            { Transition.SEND },                                // REGISTERED
                            {},                                                 // ACCEPTED
                            { Transition.START, Transition.SEND },              // SENT
                            { Transition.DONE }                                 // STARTED
                    },
                    {   // Outgoing
                            { Transition.REQUEST, Transition.TORECONCILE },     // NEW
                            { Transition.REQUEST, Transition.TORECONCILE },     // DRAFT
                            { Transition.RECONCILE, Transition.REJECT },        // PENDING
                            { Transition.APPROVE, Transition.REJECT },          // REQUESTED
                            { Transition.REQUEST, Transition.TORECONCILE },     // RECONCILED
                            { Transition.REGISTER },                            // APPROVED
                            {},                                                 // REJECTED
                            {}                                                  // REGISTERED
                    }
            };
    //endregion
    private final static Map<Integer, String> responsible = new HashMap<Integer, String>() {
        {
            put(1, "Сотрудник");
            put(2, "Отдел");
            put(3, "Организация");
            put(4, "ФИЗ Лицо");
        }
    };
    private enum CURRENTVIEW { INTERNAL, INCOMING, OUTGOING}
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = DATE_FORMAT;
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam("type") String type, Model model) {

        if(getUser() == null)
            return "/login/login";

        List<Document> documents = documentService.getInvolvedDocuments(type, getUser().getId());
        DocumentType documentType = documentTypeService.getByInternalName(type);
        String title = documentType.getName();

        Map<String, String> vars = new HashMap<>();
        vars.put("assignedTo", String.valueOf(getUser().getId()));
        vars.put("status", "OPEN");

        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("ACTIONS", ACTIONS);
        model.addAttribute("row", CURRENTVIEW.valueOf(type.toUpperCase()).ordinal());
        model.addAttribute("ds", documentStatusService);
        model.addAttribute("documents", documents);
        model.addAttribute("title", title);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentSubTypes", documentType.getDocumentSubTypes());
        model.addAttribute("type", type);
        model.addAttribute("cu", getUser().getId());

        return "/doc/document/index";
    }

    @RequestMapping(value = "/new/{subType}", method = RequestMethod.GET)
    public String add(@PathVariable("subType") String subType, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = new Document();
        document.setOwner(getUser().getId());
        document.setDocumentType(documentSubTypeService.getByInternalName(subType).getDocumentType());
        document.setDocumentSubType(documentSubTypeService.getByInternalName(subType));
        //**************************************************************************************************************

        String currentView = document.getDocumentType().getInternalName();
        int row = CURRENTVIEW.valueOf(currentView.toUpperCase()).ordinal();
        int col = document.getDocumentState().ordinal();

        List<Transition> actions = new ArrayList<>();
        for(Transition action : ACTIONS[row][col])
        {
            actions.add(action);
        }

        boolean hasReject = false;

        if(ACTIONS[row][col].length > 1){
            hasReject = ACTIONS[row][col][1].equals(Transition.REJECT) ? true : false;
        }

        model.addAttribute("stages", getStages(currentView));
        model.addAttribute("hasReject", hasReject);
        model.addAttribute("actions", actions);
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentState",  document.getDocumentState().toString());

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);
        //region XTRA
        // *************************************************************************************************************
        Map<String, String> vars = new HashMap<>();
        vars.put("objectId", document.getId().toString());
        vars.put("status", "OPEN");
        Task task = taskService.getTask(getUser(), vars);

        if(task != null && task.getProgress() != null)
            document.setDocumentState(State.valueOf(task.getProgress()));

        vars.clear();
        vars.put("objectId", document.getId().toString());
        // *************************************************************************************************************
        //endregion

        String currentView = document.getDocumentType().getInternalName();
        int row = CURRENTVIEW.valueOf(currentView.toUpperCase()).ordinal();
        int col = document.getDocumentState().ordinal();

        List<Transition> actions = new ArrayList<>();
        for(Transition action : ACTIONS[row][col])
        {
            actions.add(action);
        }

        boolean hasReject = false;

        if(ACTIONS[row][col].length > 1){
            hasReject = ACTIONS[row][col][1].equals(Transition.REJECT) ? true : false;
        }

        model.addAttribute("stages", getStages(currentView));
        model.addAttribute("hasReject", hasReject);
        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("actions", actions);
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentState", document.getDocumentState().toString());

        return "/doc/document/edit";
        //return "/doc/document/" + currentView + "/" + currentView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("document") Document document,
                       @RequestParam("action") String action,
                       @RequestParam("senderFiles") MultipartFile[] senderFiles,
                       @RequestParam("receiverFiles") MultipartFile[] receiverFiles) throws IOException {

        String docType = documentTypeService.getById(document.getDocumentType().getId()).getInternalName();

        if(document.getId() != null)
        {
            Document doc = documentService.getById(document.getId());
            document.setSenderAttachment(doc.getSenderAttachment());
            document.setReceiverAttachment(doc.getReceiverAttachment());
        }

        //region ATTACHMENTS
        if(senderFiles.length != 0) {
            for (MultipartFile senderFile : senderFiles) {
                String fileName = senderFile.getOriginalFilename();
                if(fileName.length() > 1) {

                    String uuid = UUID.randomUUID().toString();
                    String fsname = uuid + ".atach";

                    File file = new File(path + fsname);
                    senderFile.transferTo(file);

                    Attachment attachment = new Attachment();
                    attachment.setName(senderFile.getOriginalFilename());
                    attachment.setInternalName(fsname);
                    attachment.setMimeType(senderFile.getContentType());
                    document.getSenderAttachment().add(attachment);
                }
            }
        }

        if(receiverFiles.length != 0) {
            for (MultipartFile receiverFile : receiverFiles) {
                String fileName = receiverFile.getOriginalFilename();
                if(fileName.length() > 1) {
                    String uuid = UUID.randomUUID().toString();
                    String fsname = uuid + ".atach";

                    File file = new File(path + fsname);
                    receiverFile.transferTo(file);

                    Attachment attachment = new Attachment();
                    attachment.setName(receiverFile.getOriginalFilename());
                    attachment.setInternalName(fsname);
                    attachment.setMimeType(receiverFile.getContentType());
                    document.getReceiverAttachment().add(attachment);
                }
            }
        }
        //endregion

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
    public String view(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);

        Map<String, String> vars = new HashMap<>();
        vars.put("objectId", document.getId().toString());

        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("stages", getStages(document.getDocumentType().getInternalName()));
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);

        return "/doc/document/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {

        Document document = documentService.getById(id);

        for(Task task : taskService.getTasksByObjectId(document.getId()))
        {
            taskService.remove(task);
        }

        documentService.remove(document);

        return "redirect:/doc?type=" + document.getDocumentType().getInternalName();
    }

    private List<Transition> getStages(String docType) {

        List<Transition> stages = new ArrayList<>();

        if(docType.equals("internal"))
        {
            stages.add(Transition.CREATE);
            stages.add(Transition.RECONCILE);
            stages.add(Transition.APPROVE);
            stages.add(Transition.ACCEPT);
            stages.add(Transition.START);
            stages.add(Transition.DONE);
        }

        if(docType.equals("incoming"))
        {
            stages.add(Transition.REGISTER);
            stages.add(Transition.SEND);
            stages.add(Transition.START);
            stages.add(Transition.DONE);
        }

        if(docType.equals("outgoing"))
        {
            stages.add(Transition.CREATE);
            stages.add(Transition.RECONCILE);
            stages.add(Transition.APPROVE);
            stages.add(Transition.REGISTER);
            stages.add(Transition.DONE);
        }

        return stages;
    }
    private DispatchData setDispatchData(State state, String description) {



        DispatchData dispatchData = new DispatchData();

        dispatchData.setDescription(description);
        dispatchData.setDispatchType(state);
        dispatchData.setDispatchInitTime(new Date());
        dispatchData.setDispatchBy(getUser());

        return dispatchData;
    }

    private void addTask(String description, Long id, User user, Date dueDate) {

        Task task = new Task();

        task.setDescription(description);
        task.setSummary("");
        task.setObjectId(id);
        task.setObjectType(Document.class.getSimpleName());
        task.setCreatedBy(getUser());
        task.setAssignedTo(user);
        task.setTargetResolutionDate(dueDate);

        taskService.add(task);
    }
    private void addTask(String description, Long id, User fromUser, User toUser,Date dueDate, State state) {

        Task task = new Task();

        task.setDescription(description);
        task.setSummary("");
        task.setProgress(state.toString());
        task.setObjectId(id);
        task.setObjectType(Document.class.getSimpleName());
        task.setCreatedBy(fromUser);
        task.setAssignedTo(toUser);
        task.setTargetResolutionDate(dueDate);

        taskService.add(task);
    }

    private void saveInternalDocument(Document document, String action) {

        String description = document.getComment();

        //region New Document
        if(document.getId() == null)
        {
            document.getUsers().add(getUser());
            document.setDocumentState(Transition.valueOf(action.toUpperCase()).state());
            document.getDispatchData().add(setDispatchData(State.DRAFT, ""));
            document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), ""));

            documentService.add(document);

            if(action.equals("REQUEST"))
            {
                for(Staff staff : document.getSenderResponsible().getStaff())
                {
                    document.getUsers().add(getUser(staff));
                    addTask(State.REQUESTED.text(), document.getId(), getUser(staff), document.getDocumentDueDate());
                }
                documentService.update(document);
            }

            if(action.equals("TORECONCILE"))
            {
                for(Staff staff : document.getReconciler())
                {
                    document.getUsers().add(getUser(staff));
                    addTask(State.PENDING.text(), document.getId(), getUser(staff), document.getDocumentDueDate());
                }
                documentService.update(document);
            }
        }
        //endregion
        //region Existing Document
        else
        {
            Document doc = documentService.getById(document.getId());

            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState());
            document.setDispatchData(doc.getDispatchData());

            // *********************************************************************************************************
            // ******************************************** Complete Tasks *********************************************
            // *********************************************************************************************************
            if (action.equals("RECONCILE") || action.equals("APPROVE") || action.equals("ACCEPT") || action.equals("SEND") || action.equals("START") || action.equals("DONE") || action.equals("REJECT"))
            {
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
            }

            // *********************************************************************************************************
            // *********************************** Add Tasks, Users and Description ************************************
            // *********************************************************************************************************
            if (action.equals("TORECONCILE"))
            {
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", document.getId().toString());
                vars.put("status", "OPEN");
                Task task = taskService.getTask(getUser(), vars);

                if(task != null && task.getProgress() != null && task.getCreatedBy() == null)
                {
                    taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                }

                //region [User, Task]
                for(Staff staff : document.getReconciler())
                {
                    document.getUsers().add(getUser(staff));
                    addTask(State.PENDING.text(), document.getId(), getUser(staff), document.getDocumentDueDate());
                }
                //endregion
            }

            if (action.equals("REQUEST"))
            {
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", document.getId().toString());
                vars.put("status", "OPEN");
                Task task = taskService.getTask(getUser(), vars);

                if(task != null && task.getProgress() != null && task.getCreatedBy() == null)
                {
                    taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                }

                //region [User, Task]
                for (Staff staff : document.getSenderResponsible().getStaff())
                {
                    document.getUsers().add(getUser(staff));
                    addTask(State.REQUESTED.text(), document.getId(), getUser(staff), document.getDocumentDueDate());
                }
                //endregion
            }

            if (action.equals("APPROVE"))
            {
                //region [Description, User, Task]
                document.setSenderRegisteredNumber(registerService.generateRegistrationNumber(doc));

                document.setSenderRegisteredDate(new Date());
                description = "<strong>Зарегистрирован</strong>"
                        + "<br>Исходящий № : " + document.getSenderRegisteredNumber()
                        + "<br>Дата : " + DATE_FORMAT.format(document.getSenderRegisteredDate());

                if (document.getReceiverResponsible().getResponsibleType() == 1)
                {
                    for (Staff staff : document.getReceiverResponsible().getStaff())
                    {
                        document.getUsers().add(getUser(staff));
                        addTask("Документ на обработку", document.getId(), getUser(staff), document.getDocumentDueDate());
                    }
                }
                else
                {
                    for (Department department : document.getReceiverResponsible().getDepartments())
                    {
                        document.getUsers().add(getUser(department));
                        addTask("Документ на обработку", document.getId(), getUser(department), document.getDocumentDueDate());
                    }
                }
                //endregion
            }

            if (action.equals("ACCEPT"))
            {
                //region [Description, User, AutoTask]
                document.setSenderRegisteredNumber(registerService.generateRegistrationNumber(doc));

                document.setReceiverRegisteredDate(new Date());
                description = "<strong>Зарегистрирован</strong>"
                        + "<br>Входящий № : " + document.getReceiverRegisteredNumber()
                        + "<br>Дата : " + DATE_FORMAT.format(document.getReceiverRegisteredDate());

                if (document.getReceiverResponsible().getResponsibleType() == 1) {
                    for (Staff staff : document.getReceiverResponsible().getStaff())
                    {
                        addTask("Назначить исполнителя", document.getId(), null, getUser(staff), document.getDocumentDueDate(),State.ACCEPTED);
                    }
                } else {
                    for (Department department : document.getReceiverResponsible().getDepartments())
                    {
                        addTask("Назначить исполнителя", document.getId(), null, getUser(department), document.getDocumentDueDate(),State.ACCEPTED);
                    }
                }
                //endregion
            }

            if (action.equals("SEND"))
            {
                //region [User, Task]
                for (Staff staff : document.getExecutor())
                {
                    document.getUsers().add(getUser(staff));
                    addTask("На исполнение", document.getId(), getUser(staff), document.getDocumentDueDate());
                }
                //endregion
            }

            if (action.equals("START"))
            {
                //region [User, AutoTask]
                document.getReceiverExecutor().getStaff().add(getUser().getStaff());
                addTask("Завершить задачу", document.getId(),null, getUser(), document.getDocumentDueDate(), State.STARTED);
                //endregion
            }

            // *********************************************************************************************************
            // ************************************* Update Document State *********************************************
            // *********************************************************************************************************
            if(action.equals("START") || action.equals("DONE") || action.equals("RECONCILE") || action.equals("REJECT"))
            {
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", document.getId().toString());
                vars.put("status", "OPEN");
                int taskCount = taskService.getTasks(vars).size();

                if (action.equals("RECONCILE") && taskCount == 0)
                {
                    addTask("Согласование завершено", document.getId(), null, getUser(document.getOwner()), document.getDocumentDueDate(), State.DRAFT);
                }

                if (action.equals("START") && taskCount == 0)
                {
                    document.setDocumentState(Transition.valueOf(action).state());
                }

                if (action.equals("DONE") && taskCount == 0)
                {
                    document.setDocumentState(Transition.valueOf(action).state());
                }

                if(action.equals("REJECT"))
                {
                    if(document.getDocumentState() == State.PENDING && taskCount == 0)
                    {
                        addTask("Согласование завершено", document.getId(), null, getUser(document.getOwner()), document.getDocumentDueDate(), State.DRAFT);
                    }

                    if(document.getDocumentState() == State.REQUESTED)
                    {
                        addTask("Доработать", document.getId(), null, getUser(document.getOwner()), document.getDocumentDueDate(), State.DRAFT);
                    }

                    if (document.getDocumentState() == State.APPROVED)
                    {
                        //region [Description]
                        document.setReceiverRegisteredNumber(registerService.generateRegistrationNumber());
                        document.setReceiverRegisteredDate(new Date());
                        description = "<strong>Зарегистрирован</strong>"
                                + "<br>Входящий № : " + document.getReceiverRegisteredNumber()
                                + "<br>Дата : " + DATE_FORMAT.format(document.getReceiverRegisteredDate());
                        //endregion
                        document.setDocumentState(State.DONE);
                    }
                }
            }
            else
            {
                document.setDocumentState(Transition.valueOf(action).state());
            }

            // *********************************************************************************************************
            // *************************************** Add Dispatch Data ***********************************************
            // *********************************************************************************************************
            document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

            documentService.update(document);
        }
        //endregion
    }
    private void saveIncomingDocument(Document document, String action) {

        String description = document.getComment();

        //region New Document
        if(document.getId() == null)
        {
            document.getUsers().add(getUser());
            document.setDocumentState(Transition.valueOf(action).state());

            if(action.equals("REGISTER"))
            {
                // Register Outgoing
                description = "<strong>Зарегистрирован</strong>"
                        + "<br>Исходящий № : " + document.getSenderRegisteredNumber()
                        + "<br>Дата : " + DATE_FORMAT.format(document.getSenderRegisteredDate());
                document.getDispatchData().add(setDispatchData(State.DRAFT, description));

                // Register Incoming
                document.setSenderRegisteredNumber(registerService.generateRegistrationNumber(document));

                document.setReceiverRegisteredDate(new Date());
                description = "<strong>Зарегистрирован</strong>"
                        + "<br>Входящий № : " + document.getReceiverRegisteredNumber()
                        + "<br>Дата : " + DATE_FORMAT.format(document.getReceiverRegisteredDate());
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

                documentService.add(document);

                if (document.getReceiverResponsible().getResponsibleType() == 1)
                {
                    for (Staff staff : document.getReceiverResponsible().getStaff())
                    {
                        document.getUsers().add(getUser(staff));
                        addTask("Назначить исполнителя", document.getId(), getUser(staff), document.getDocumentDueDate());
                    }
                }
                else
                {
                    for (Organization organization : document.getReceiverResponsible().getOrganizations())
                    {
                        document.getUsers().add(getUser(organization));
                        addTask("Назначить исполнителя", document.getId(), getUser(organization), document.getDocumentDueDate());
                    }
                }
                documentService.update(document);
            }
        }
        //endregion
        //region Existing Document
        else
        {
            Document doc = documentService.getById(document.getId());

            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState());
            document.setDispatchData(doc.getDispatchData());

            // *********************************************************************************************************
            // ******************************************** Complete Tasks *********************************************
            // *********************************************************************************************************
            if (action.equals("SEND") || action.equals("START") || action.equals("DONE"))
            {
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
            }

            // *********************************************************************************************************
            // *********************************** Add Tasks, Users and Description ************************************
            // *********************************************************************************************************

            if (action.equals("SEND"))
            {
                //region [User, Task]
                for (Staff staff : document.getExecutor())
                {
                    document.getUsers().add(getUser(staff));
                    addTask("На исполнение", document.getId(), getUser(staff), document.getDocumentDueDate());
                }
                //endregion
            }

            if (action.equals("START"))
            {
                //region [User, AutoTask]
                document.getReceiverExecutor().getStaff().add(getUser().getStaff());
                addTask("Завершить задачу", document.getId(),null, getUser(), document.getDocumentDueDate(), State.STARTED);
                //endregion
            }

            // *********************************************************************************************************
            // ************************************* Update Document State *********************************************
            // *********************************************************************************************************
            if(action.equals("START") || action.equals("DONE"))
            {
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", document.getId().toString());
                vars.put("status", "OPEN");
                int taskCount = taskService.getTasks(vars).size();

                if (action.equals("START") && taskCount == 0)
                {
                    document.setDocumentState(Transition.valueOf(action).state());
                }

                if (action.equals("DONE") && taskCount == 0)
                {
                    document.setDocumentState(Transition.valueOf(action).state());
                }
            }
            else
            {
                document.setDocumentState(Transition.valueOf(action).state());
            }

            // *********************************************************************************************************
            // *************************************** Add Dispatch Data ***********************************************
            // *********************************************************************************************************
            document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

            documentService.update(document);
        }
        //endregion
    }
    private void saveOutgoingDocument(Document document, String action) {

        String description = document.getComment();

        //region New Document
        if(document.getId() == null)
        {
            document.getUsers().add(getUser());
            document.setDocumentState(Transition.valueOf(action.toUpperCase()).state());
            document.getDispatchData().add(setDispatchData(State.DRAFT, ""));
            document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), ""));

            documentService.add(document);

            if(action.equals("REQUEST"))
            {
                if (document.getSenderResponsible().getResponsibleType() == 1)
                {
                    for (Staff staff : document.getSenderResponsible().getStaff())
                    {
                        document.getUsers().add(getUser(staff));
                        addTask(State.REQUESTED.text(), document.getId(), getUser(staff), document.getDocumentDueDate());
                    }
                }
                else
                {
                    for (Organization organization : document.getSenderResponsible().getOrganizations())
                    {
                        document.getUsers().add(getUser(organization));
                        addTask(State.REQUESTED.text(), document.getId(), getUser(organization), document.getDocumentDueDate());
                    }
                }

                documentService.update(document);
            }

            if(action.equals("TORECONCILE"))
            {
                for(Staff staff : document.getReconciler())
                {
                    document.getUsers().add(getUser(staff));
                    addTask(State.PENDING.text(), document.getId(), getUser(staff), document.getDocumentDueDate());
                }
                documentService.update(document);
            }
        }
        //endregion
        //region Existing Document
        else
        {
            Document doc = documentService.getById(document.getId());

            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState());
            document.setDispatchData(doc.getDispatchData());

            // *********************************************************************************************************
            // ******************************************** Complete Tasks *********************************************
            // *********************************************************************************************************
            if (action.equals("RECONCILE") || action.equals("APPROVE") || action.equals("REGISTER") || action.equals("REJECT"))
            {
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
            }

            // *********************************************************************************************************
            // *********************************** Add Tasks, Users and Description ************************************
            // *********************************************************************************************************
            if (action.equals("TORECONCILE"))
            {
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", document.getId().toString());
                vars.put("status", "OPEN");
                Task task = taskService.getTask(getUser(), vars);

                if(task != null && task.getProgress() != null && task.getCreatedBy() == null)
                {
                    taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                }

                //region [User, Task]
                for(Staff staff : document.getReconciler())
                {
                    document.getUsers().add(getUser(staff));
                    addTask(State.PENDING.text(), document.getId(), getUser(staff), document.getDocumentDueDate());
                }
                //endregion
            }

            if (action.equals("REQUEST"))
            {
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", document.getId().toString());
                vars.put("status", "OPEN");
                Task task = taskService.getTask(getUser(), vars);

                if(task != null && task.getProgress() != null && task.getCreatedBy() == null)
                {
                    taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                }

                //region [User, Task]
                if (document.getSenderResponsible().getResponsibleType() == 1)
                {
                    for (Staff staff : document.getSenderResponsible().getStaff())
                    {
                        document.getUsers().add(getUser(staff));
                        addTask(State.REQUESTED.text(), document.getId(), getUser(staff), document.getDocumentDueDate());
                    }
                }
                else
                {
                    for (Organization organization : document.getSenderResponsible().getOrganizations())
                    {
                        document.getUsers().add(getUser(organization));
                        addTask(State.REQUESTED.text(), document.getId(), getUser(organization), document.getDocumentDueDate());
                    }
                }
                //endregion
            }

            if (action.equals("APPROVE"))
            {
                document.getUsers().add(getUser(staticUser));
                addTask(Transition.REGISTER.text(), document.getId(), null, getUser(staticUser), document.getDocumentDueDate(), State.APPROVED);
            }

            if (action.equals("REGISTER"))
            {
                //region [Description, User, AutoTask]
                document.setSenderRegisteredNumber(registerService.generateRegistrationNumber(document));
                document.setSenderRegisteredDate(new Date());
                description = "<strong>Зарегистрирован</strong>"
                        + "<br>Исходящий № : " + document.getSenderRegisteredNumber()
                        + "<br>Дата : " + DATE_FORMAT.format(document.getSenderRegisteredDate());
                //endregion
            }

            // *********************************************************************************************************
            // ************************************* Update Document State *********************************************
            // *********************************************************************************************************
            if(action.equals("REJECT") || action.equals("RECONCILE") || action.equals("REGISTER"))
            {
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", document.getId().toString());
                vars.put("status", "OPEN");
                int taskCount = taskService.getTasks(vars).size();

                if (action.equals("RECONCILE") && taskCount == 0)
                {
                    addTask("Согласование завершено", document.getId(), null, getUser(document.getOwner()), document.getDocumentDueDate(), State.DRAFT);
                }

                if(document.getDocumentState() == State.PENDING && taskCount == 0)
                {
                    addTask("Согласование завершено", document.getId(), null, getUser(document.getOwner()), document.getDocumentDueDate(), State.DRAFT);
                }

                if(document.getDocumentState() == State.REQUESTED)
                {
                    addTask("Доработать : " + description, document.getId(), null, getUser(document.getOwner()), document.getDocumentDueDate(), State.DRAFT);
                }

                if(action.equals("REGISTER"))
                {
                    document.setDocumentState(State.DONE);
                }
            }
            else
            {
                document.setDocumentState(Transition.valueOf(action).state());
            }

            // *********************************************************************************************************
            // *************************************** Add Dispatch Data ***********************************************
            // *********************************************************************************************************
            document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

            documentService.update(document);
        }
        //endregion
    }

    // *****************************************************************************************************************
    // REST ************************************************************************************************************
    // *****************************************************************************************************************
    @RequestMapping("/data/documents")
    @ResponseBody
    public List<Document> documents(@RequestParam String name) {
        List<Document> documents = new ArrayList<>();
        for(Document document : documentService.getInvolvedDocuments(name, getUser().getId())) {
            documents.add(document);
        }
        return documents;
    }

    @RequestMapping("/data/staff")
    @ResponseBody
    public List<Result> getStaff(@RequestParam String name) {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("staff", name))
        {
            data.add(new Result(account.getId(), account.getName()));
        }

        return data;
    }

    @RequestMapping("/data/department")
    @ResponseBody
    public List<Result> getDepartment(@RequestParam String name) {

        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("department", name)) {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/data/organizations")
    @ResponseBody
    public List<Result> getOrganization(@RequestParam String name) {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("organization", name)) {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/data/person")
    @ResponseBody
    public List<Result> getPerson(@RequestParam String name) {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("person", name))
        {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }
}