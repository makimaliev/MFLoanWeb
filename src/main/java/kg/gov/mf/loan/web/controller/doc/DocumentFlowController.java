package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.task.model.ChatUser;
import kg.gov.mf.loan.task.model.SystemConstant;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.service.ChatUserService;
import kg.gov.mf.loan.task.service.SystemConstantService;
import kg.gov.mf.loan.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/doc")
public class DocumentFlowController extends BaseController {

    class DataTableResult {

        public int draw;
        public int recordsTotal;
        public int recordsFiltered;
        public List<Document> data = new ArrayList<>();
        public String error = "";
    }
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
    private CounterService registerService;
    private AccountService accountService;
    private SystemConstantService systemConstantService;
    private ChatUserService chatUserService;

    @Autowired
    public DocumentFlowController(DocumentService documentService,
                                  DocumentTypeService documentTypeService,
                                  DocumentSubTypeService documentSubTypeService,
                                  DocumentStatusService documentStatusService,
                                  TaskService taskService,
                                  CounterService registerService,
                                  AccountService accountService,
                                  SystemConstantService systemConstantService,
                                  ChatUserService chatUserService) {

        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
        this.documentSubTypeService = documentSubTypeService;
        this.documentStatusService = documentStatusService;
        this.taskService = taskService;
        this.registerService = registerService;
        this.accountService = accountService;
        this.systemConstantService = systemConstantService;
        this.chatUserService = chatUserService;
    }
    //endregion
    //region TYPE
    private Transition[][][] ACTIONS =
            {
                {   // Internal
                    { Transition.REQUEST, Transition.TORECONCILE },     // NEW
                    { Transition.REQUEST, Transition.TORECONCILE },     // DRAFT
                    { Transition.RECONCILE, Transition.REJECT },        // PENDING
                    { Transition.REQUEST, Transition.TORECONCILE },     // RECONCILED
                    { Transition.APPROVE, Transition.REJECT },          // REQUESTED
                    { Transition.ACCEPT },                              // APPROVED
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
                    { Transition.APPROVE },                             // REGISTERED
                    { Transition.SEND },                                // ACCEPTED
                    { Transition.START, Transition.SEND },              // SENT
                    { Transition.DONE }                                 // STARTED
                },
                {   // Outgoing
                    { Transition.REQUEST, Transition.TORECONCILE },     // NEW
                    { Transition.REQUEST, Transition.TORECONCILE },     // DRAFT
                    { Transition.RECONCILE, Transition.REJECT },        // PENDING
                    { Transition.REQUEST, Transition.TORECONCILE },     // RECONCILED
                    { Transition.APPROVE, Transition.REJECT },          // REQUESTED
                    { Transition.REGISTER },                            // APPROVED
                    {},                                                 // REJECTED
                    { Transition.DONE },                                // REGISTERED
                    {},                                                 // ACCEPTED
                    {},                                                 // SENT
                    {},                                                 // STARTED
                    {}                                                  // DONE
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

        if(document.getDocumentType().getInternalName() == "incoming")
        {
            document.getReceiverResponsible().setResponsibleType(1);
        }

        /*
        if(document.getDocumentType().getInternalName() == "outgoing")
        {
            document.getSenderResponsible().setResponsibleType(1);
            document.getSenderResponsible().getStaff().add(userService.findByUsername("").getStaff());
        }
        */
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

        model.addAttribute("mydocs", documentService.getDocuments(getUser().getId()));

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);

        State curretState = document.getDocumentState();

        //region XTRA
        Map<String, String> vars = new HashMap<>();
        vars.put("objectId", String.valueOf(document.getId()));
        vars.put("status", "OPEN");
        Task task = taskService.getTask(getUser(), vars);


        if(task != null && task.getProgress() != null)
            document.setDocumentState(State.valueOf(task.getProgress()));

        vars.clear();
        vars.put("objectId", String.valueOf(document.getId()));
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

        // *************************************************************************************************************
        document.setDocumentState(curretState);

        //

        model.addAttribute("cu", getUser().getUsername());
        model.addAttribute("cuid", getUser().getId());
        model.addAttribute("stages", getStages(currentView));
        model.addAttribute("hasReject", hasReject);
        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("actions", actions);
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentState", document.getDocumentState().toString());

        model.addAttribute("mydocs", documentService.getDocuments(getUser().getId()));

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("document") Document document,
                       @RequestParam("action") String action,
                       @RequestParam(value = "files", required=false) long files[]) {

        if(getUser() == null) return "/login/login";

        String docType = documentTypeService.getById(document.getDocumentType().getId()).getInternalName();

        document.setDocumentType(documentTypeService.getById(document.getDocumentType().getId()));

        if(document.getId() != 0)
        {
            Document doc = documentService.getById(document.getId());
            document.setAttachments(doc.getAttachments());
        }

        if(files.length != 0)
        {
            for(long file : files)
            {
                if(file != 0)
                    document.getAttachments().add(attachmentService.getById(file));
            }
        }

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

    //******************************************************************************************************************
    @RequestMapping(value = "/save/{id}", method = RequestMethod.GET)
    public String register(@PathVariable("id") Long id) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);

        String docType = documentTypeService.getById(document.getDocumentType().getId()).getInternalName();

        saveOutgoingDocument(document, "REGISTER");

        return "redirect:/doc?type=" + docType;
    }
    //******************************************************************************************************************

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("document") Document document,
                         @RequestParam("action") String action) {

        if(getUser() == null) return "/login/login";

        String docType = documentTypeService.getById(document.getDocumentType().getId()).getInternalName();

        Document doc = documentService.getById(document.getId());

        document.setUsers(doc.getUsers());
        document.setDocumentState(doc.getDocumentState());
        document.setDispatchData(doc.getDispatchData());


        return "redirect:/doc?type=" + docType;
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);

        Map<String, String> vars = new HashMap<>();
        vars.put("objectId", String.valueOf(document.getId()));

        model.addAttribute("tasks", taskService.getTasks(vars));
        //model.addAttribute("accounts", chatUserService.list(10,5));
        model.addAttribute("stages", getStages(document.getDocumentType().getInternalName()));
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);

        return "/doc/document/view";
    }

    @RequestMapping(value = "/admin/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);
        documentService.remove(document);

        for(Task task : taskService.getTasksByObjectId(id))
        {
            taskService.remove(task);
        }

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
            stages.add(Transition.CREATE);
            stages.add(Transition.REGISTER);
            stages.add(Transition.START);
            stages.add(Transition.DONE);
        }

        if(docType.equals("outgoing"))
        {
            stages.add(Transition.CREATE);
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

    private void addTask(String description, Long id, User user, Date dueDate, User toUser, State stateToBeginWith) {

        Task task = new Task();
        task.setDescription(description);
        task.setSummary("");

        if(stateToBeginWith != null) {
            task.setProgress(stateToBeginWith.toString());
        }

        task.setObjectId(id);
        task.setObjectType(Document.class.getSimpleName());
        task.setCreatedBy(user);
        task.setAssignedTo(toUser);
        task.setTargetResolutionDate(dueDate);

        taskService.add(task);
    }
    private void saveInternalDocument(Document document, String action) {

        String description = document.getComment();

        //region New Document
        if(document.getId() == 0)
        {
            document.getUsers().add(getUser());
            document.setDocumentState(Transition.valueOf(action.toUpperCase()).state());
            document.getDispatchData().add(setDispatchData(State.DRAFT, ""));
            documentService.add(document);

            if(action.equals("TORECONCILE"))
            {
                for(Staff staff : document.getReconciler())
                {
                    document.getUsers().add(getUser(staff));
                    addTask(State.PENDING.text(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), State.PENDING);
                    document.getDispatchData().add(setDispatchData(State.PENDING, staff.getName()));
                }
            }

            for(Staff staff : document.getSenderResponsible().getStaff())
            {
                document.getUsers().add(getUser(staff));
                addTask(State.REQUESTED.text(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), null);
                document.getDispatchData().add(setDispatchData(State.REQUESTED, staff.getName()));
            }

            document.setDocumentState(State.REQUESTED);
            documentService.update(document);
        }
        //endregion
        //region Existing Document
        else
        {
            Document doc = documentService.getById(document.getId());

            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState());
            document.setDispatchData(doc.getDispatchData());

            if (action.equals("RECONCILE"))
            {
                //region Description
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.getDispatchData().add(setDispatchData(State.RECONCILED, ""));

                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", String.valueOf(document.getId()));
                vars.put("status", "OPEN");
                int taskCount = taskService.getTasks(vars).size();


                if (document.getDocumentState().equals(State.STARTED) && taskCount == 0)
                {
                    document.setDocumentState(State.DONE);
                }
                //endregion
            }

            if (action.equals("APPROVE"))
            {
                //region [Description, User, Task]
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());

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
                        addTask("Документ на обработку", document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), null);
                    }
                }
                else
                {
                    for (Department department : document.getReceiverResponsible().getDepartments())
                    {
                        document.getUsers().add(getUser(department));
                        addTask("Документ на обработку", document.getId(), getUser(), document.getDocumentDueDate(), getUser(department), null);
                    }
                }
                document.getDispatchData().add(setDispatchData(State.APPROVED, description));
                document.setDocumentState(State.APPROVED);
                //endregion
            }

            if(action.equals("REJECT"))
            {
                taskService.completeTask(document.getId(), getUser(), State.REJECTED.text() + "<br>" + document.getComment());
                addTask("Доработать", document.getId(), getUser(), document.getDocumentDueDate(), getUser(document.getOwner()), State.DRAFT);
                document.getDispatchData().add(setDispatchData(State.REJECTED, description));
            }

            if (action.equals("ACCEPT"))
            {
                //region [Description, User, AutoTask]
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());

                document.setReceiverRegisteredNumber(registerService.generateRegistrationNumber(doc));
                document.setReceiverRegisteredDate(new Date());
                description = "<strong>Зарегистрирован</strong>"
                            + "<br>Входящий № : " + document.getReceiverRegisteredNumber()
                            + "<br>Дата : " + DATE_FORMAT.format(document.getReceiverRegisteredDate());

                if (document.getReceiverResponsible().getResponsibleType() == 1) {
                    for (Staff staff : document.getReceiverResponsible().getStaff())
                    {
                        addTask("Назначить исполнителя", document.getId(), null, document.getDocumentDueDate(), getUser(staff), State.ACCEPTED);
                    }
                } else {
                    for (Department department : document.getReceiverResponsible().getDepartments())
                    {
                        addTask("Назначить исполнителя", document.getId(), null, document.getDocumentDueDate(), getUser(department), State.ACCEPTED);
                    }
                }

                document.getDispatchData().add(setDispatchData(State.ACCEPTED, description));
                document.setDocumentState(State.ACCEPTED);
                //endregion
            }

            if (action.equals("SEND"))
            {
                //region [User, Task, Executor]
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());

                Executor executor = new Executor();
                executor.setExecutorType(1);
                Set<Staff> staffSet = new HashSet<>();

                for (Staff staff : document.getExecutor())
                {
                    staffSet.add(staff);
                    document.getUsers().add(getUser(staff));
                    addTask("На исполнение", document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), State.SENT);
                    document.getDispatchData().add(setDispatchData(State.SENT, staff.getName()));
                }

                executor.setStaff(staffSet);
                document.setReceiverExecutor(executor);
                document.setDocumentState(State.STARTED);
                //endregion
            }

            if (action.equals("START"))
            {
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                addTask("Завершить задачу", document.getId(),null, document.getDocumentDueDate(), getUser(), State.STARTED);
                document.getDispatchData().add(setDispatchData(State.STARTED, ""));
            }

            if(action.equals("DONE"))
            {
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.getDispatchData().add(setDispatchData(State.DONE, ""));

                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", String.valueOf(document.getId()));
                vars.put("status", "OPEN");
                int taskCount = taskService.getTasks(vars).size();

                if (action.equals("DONE") && taskCount == 0)
                {
                    document.setDocumentState(Transition.valueOf(action).state());
                }
            }


            // *********************************************************************************************************
            // *************************************** Add Dispatch Data ***********************************************
            // *********************************************************************************************************

            documentService.update(document);
        }
        //endregion
    }
    private void saveIncomingDocument(Document document, String action) {

        String description = document.getComment();

        //region New Document
        if(document.getId() == 0)
        {
            document.getUsers().add(getUser());
            document.setDocumentState(Transition.valueOf(action).state());

            description =
                    "№ Исходящего документа : " + document.getSenderRegisteredNumber()
                    + "<br>Дата : " + DATE_FORMAT.format(document.getSenderRegisteredDate());

            if(!document.getTitle().isEmpty())
            {
                description = description + "<hr>№ Входящего документа и Дата МФКР : " + document.getTitle();
            }

            document.getDispatchData().add(setDispatchData(State.DRAFT, description));

            document.setReceiverRegisteredNumber(registerService.generateRegistrationNumber(document));
            document.setReceiverRegisteredDate(new Date());
            description = "№ Входящего документа : " + document.getReceiverRegisteredNumber()
                    + "<br>Дата : " + DATE_FORMAT.format(document.getReceiverRegisteredDate());
            document.getDispatchData().add(setDispatchData(State.REGISTERED, description));
            documentService.add(document);

            addTask("Отправить ответственным", document.getId(), null, document.getDocumentDueDate(), getUser(), null);
        }
        //endregion
        //region Existing Document
        else
        {
            Document doc = documentService.getById(document.getId());

            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState());
            document.setDispatchData(doc.getDispatchData());

            // ******************************************** Complete Tasks *********************************************
            if (action.equals("APPROVE"))
            {
                //region APPROVE
                taskService.completeTask(document.getId(), getUser(), "Выполнен");
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));
                document.setDocumentState(State.ACCEPTED);

                for (Staff staff : document.getReceiverResponsible().getStaff())
                {
                    document.getUsers().add(getUser(staff));
                    addTask("На исполнение", document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), null);
                }
                //endregion
            }

            if (action.equals("SEND"))
            {
                //region SEND
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

                Executor executor = new Executor();
                executor.setExecutorType(1);

                if(doc.getReceiverExecutor() != null)
                {
                    document.setReceiverExecutor(doc.getReceiverExecutor());
                }
                else
                {
                    document.setReceiverExecutor(executor);
                }

                for (Staff staff : document.getExecutor())
                {
                    document.getReceiverExecutor().getStaff().add(staff);
                    document.getUsers().add(getUser(staff));
                    addTask("На исполнение", document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), State.SENT);
                }
                //endregion
            }

            if (action.equals("START"))
            {
                //region START
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));
                document.getReceiverExecutor().getStaff().add(getUser().getStaff());
                addTask("Завершить задачу", document.getId(),null, document.getDocumentDueDate(), getUser(), State.STARTED);
                //endregion
            }

            if (action.equals("DONE"))
            {
                //region DONE
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", String.valueOf(document.getId()));
                vars.put("status", "OPEN");
                int taskCount = taskService.getTasks(vars).size();

                if (taskCount == 0)
                {
                    document.setDocumentState(State.DONE);
                }
                //endregion
            }

            documentService.update(document);
        }
        //endregion
    }
    private void saveOutgoingDocument(Document document, String action) {

        String description = document.getComment();

        //region New Document
        if(document.getId() == 0)
        {
            document.getUsers().add(getUser());
            document.getDispatchData().add(setDispatchData(State.DRAFT, ""));
            documentService.add(document);

            if(document.getReconciler() != null)
            {
                for (Staff staff : document.getReconciler())
                {
                    document.getUsers().add(getUser(staff));
                    document.getDispatchData().add(setDispatchData(State.PENDING, staff.getName()));
                }
            }

            for (Staff staff : document.getSenderResponsible().getStaff())
            {
                document.getUsers().add(getUser(staff));
                document.getDispatchData().add(setDispatchData(State.REQUESTED, staff.getName()));
            }

            document.setDocumentState(State.APPROVED);

            for (User user : systemConstantService.getById(1).getOutgoingRegistrator())
            {
                document.getUsers().add(user);
                addTask(Transition.REGISTER.text(), document.getId(), null, document.getDocumentDueDate(), user, State.APPROVED);
            }
            documentService.update(document);

        }
        //endregion
        //region Existing Document
        else
        {
            Document doc = documentService.getById(document.getId());

            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState());
            document.setDispatchData(doc.getDispatchData());

            if (action.equals("TORECONCILE") || action.equals("REQUEST"))
            {
                //region Description
                taskService.completeTask(document.getId(), getUser(), "Завершен");

                if(document.getReconciler() != null)
                {
                    for (Staff staff : document.getReconciler())
                    {
                        document.getUsers().add(getUser(staff));
                        document.getDispatchData().add(setDispatchData(State.RECONCILED, staff.getName()));
                    }
                }

                for (Staff staff : document.getSenderResponsible().getStaff())
                {
                    document.getUsers().add(getUser(staff));
                    document.getDispatchData().add(setDispatchData(State.APPROVED, staff.getName()));
                }

                document.setDocumentState(State.APPROVED);

                for (User user : systemConstantService.getById(1).getOutgoingRegistrator())
                {
                    document.getUsers().add(user);
                    addTask(Transition.REGISTER.text(), document.getId(), null, document.getDocumentDueDate(), user, State.APPROVED);
                }
                //endregion
            }

            if (action.equals("REJECT"))
            {
                //region Description
                taskService.completeTask(document.getId(), getUser(), "Отклонен : " + description);

                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", String.valueOf(document.getId()));
                vars.put("status", "OPEN");

                if (taskService.getTasks(vars).size() == 0)
                {
                    addTask("Доработать документ", document.getId(), null, document.getDocumentDueDate(), getUser(document.getOwner()), State.DRAFT);
                    document.setDocumentState(State.REJECTED);
                }
                document.getDispatchData().add(setDispatchData(State.APPROVED, "Отклонен : " + description));
                //endregion
            }

            if (action.equals("REGISTER"))
            {
                //region [Description, User, AutoTask]
                document.setSenderRegisteredNumber(registerService.generateRegistrationNumber(document));
                document.setSenderRegisteredDate(new Date());
                description = "Исходящий № : " + document.getSenderRegisteredNumber()
                        + "<br>Дата : " + DATE_FORMAT.format(document.getSenderRegisteredDate());

                document.getDispatchData().add(setDispatchData(State.REGISTERED, description));
                document.setDocumentState(State.REGISTERED);

                for (User user : systemConstantService.getById(1).getOutgoingRegistrator())
                {
                    taskService.completeTask(document.getId(), user, State.REGISTERED.text());
                }

                for (User user : systemConstantService.getById(1).getOutgoingRegistrator())
                {
                    document.getUsers().add(user);
                    addTask(Transition.DONE.text(), document.getId(), null, document.getDocumentDueDate(), user, null);
                }
                //endregion
            }

            if (action.equals("DONE"))
            {
                //region [Description, User, AutoTask]
                for (User user : systemConstantService.getById(1).getOutgoingRegistrator())
                {
                    taskService.completeTask(document.getId(), user, "Завершен");
                }
                document.getDispatchData().add(setDispatchData(State.DONE, ""));
                document.setDocumentState(State.DONE);
                //endregion
            }

            documentService.update(document);
        }
        //endregion
    }

    // *****************************************************************************************************************
    @RequestMapping(value = "/constants", method = RequestMethod.GET)
    public String constants(Model model) {

        if(getUser() == null)
            return "/login/login";

        SystemConstant systemConstant = systemConstantService.list().isEmpty()
                ? new SystemConstant()
                : systemConstantService.list().get(0);

        model.addAttribute("systemConstant", systemConstant);

        return "/doc/document/constants";
    }

    @RequestMapping(value = "/save/constants", method = RequestMethod.POST)
    public String saveConstants(@ModelAttribute("systemConstant") SystemConstant systemConstant, Model model) {

        if(getUser() == null)
            return "/login/login";

        if(systemConstant.getId() == 0)
        {
            systemConstantService.add(systemConstant);
        }
        else
        {
            systemConstantService.update(systemConstant);
        }

        model.addAttribute("systemConstant", systemConstant);

        return "/doc/document/constants";
    }

    // *****************************************************************************************************************
    // REST ************************************************************************************************************
    // *****************************************************************************************************************
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

    @RequestMapping("/data/user")
    @ResponseBody
    public List<Result> getUser(@RequestParam String name) {

        List<Result> data = new ArrayList<>();

        for(ChatUser chatUser : chatUserService.getAllByName(name))
        {
            data.add(new Result(chatUser.getId(), chatUser.getName()));
        }
        return data;
    }

    @RequestMapping("/documents")
    @ResponseBody
    public DataTableResult getDocuments(HttpServletRequest request) {

        DataTableResult dataTableResult = new DataTableResult();
        dataTableResult.draw = 1;
        dataTableResult.recordsTotal = documentService.list().size();
        dataTableResult.data = documentService.list();

        return dataTableResult;
    }
}