package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.doc.dao.DocViewDao;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.task.model.ChatUser;
import kg.gov.mf.loan.task.model.SystemConstant;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.service.ChatUserService;
import kg.gov.mf.loan.task.service.SystemConstantService;
import kg.gov.mf.loan.task.service.TaskService;
import kg.gov.mf.loan.web.controller.doc.dto.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/doc")
public class DocumentFlowController extends BaseController {

    @Autowired
    EntityManager entityManager;


    @Autowired
    ResponsibleService responsibleService;

    @Autowired
    StaffService staffService;

    @Autowired
    DocViewDao documentViewDao;

    //region Dependencies
    private DocumentService documentService;
    private DocumentTypeService documentTypeService;
    private DocumentSubTypeService documentSubTypeService;
    private TaskService taskService;
    private CounterService registerService;
    private AccountService accountService;
    private SystemConstantService systemConstantService;
    private ChatUserService chatUserService;

    //private DocumentRepository documentRepository;

    @Autowired
    public DocumentFlowController(DocumentService documentService,
                                  DocumentTypeService documentTypeService,
                                  DocumentSubTypeService documentSubTypeService,
                                  TaskService taskService,
                                  CounterService registerService,
                                  AccountService accountService,
                                  SystemConstantService systemConstantService,
                                  ChatUserService chatUserService) {

        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
        this.documentSubTypeService = documentSubTypeService;
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
                            { Transition.REJECT, Transition.RECONCILE },        // PENDING
                            { Transition.REQUEST, Transition.TORECONCILE },     // RECONCILED
                            { Transition.REJECT, Transition.APPROVE },          // REQUESTED
                            { Transition.ACCEPT },                              // APPROVED
                            {},                                                 // REJECTED
                            {},                                                 // REGISTERED
                            { Transition.DONE, Transition.SEND },               // ACCEPTED
                            { Transition.DONE, Transition.SEND },               // SENT
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
                            { Transition.DONE, Transition.REJECT, Transition.SEND },               // ACCEPTED
                            { Transition.DONE, Transition.SEND },               // SENT
                            { Transition.DONE }                                 // STARTED
                    },
                    {   // Outgoing
                            { Transition.REQUEST },                             // NEW
                            { Transition.REQUEST },                             // DRAFT
                            {},                                                 // PENDING
                            {},                                                 // RECONCILED
                            {},                                                 // REQUESTED
                            { Transition.REGISTER },                            // APPROVED
                            {},                                                 // REJECTED
                            { Transition.DONE },                                // REGISTERED
                            {},                                                 // ACCEPTED
                            {},                                                 // SENT
                            {},                                                 // STARTED
                            {}                                                  // DONE
                    },
                    {   // OTHERS
                            { Transition.REQUEST },                             // NEW
                            { Transition.REQUEST },                             // DRAFT
                            {},                                                 // PENDING
                            {},                                                 // RECONCILED
                            {},                                                 // REQUESTED
                            { Transition.REGISTER },                            // APPROVED
                            {},                                                 // REJECTED
                            { Transition.DONE },                                // REGISTERED
                            {},                                                 // ACCEPTED
                            {},                                                 // SENT
                            {},                                                 // STARTED
                            {}                                                  // DONE
                    },
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

        DocumentType documentType = documentTypeService.getByInternalName(type);

        Map<String, String> vars = new HashMap<>();
        vars.put("assignedTo", String.valueOf(getUser().getId()));
        vars.put("status", "OPEN");
        List<Task> t = taskService.getTasks(vars);
        int[] tasks = new int[t.size()];
        int i = 0;
        for(Task task : t)
        {
            tasks[i] = (int)task.getObjectId();
            ++i;
        }

        model.addAttribute("tasks", tasks);
        model.addAttribute("ACTIONS", ACTIONS);
        model.addAttribute("title", documentType.getName());
        model.addAttribute("type", type);

        Map<String, String> states = new HashMap<>(0);
        for(State state : State.values())
        {
            states.put(state.name(), state.text());
        }
        model.addAttribute("states", states);

        Map<String, String> dtypes = new HashMap<>(0);
        for(DocumentSubType documentSubType : documentTypeService.getByInternalName(type).getDocumentSubTypes())
        {
            dtypes.put(documentSubType.getInternalName(), documentSubType.getName());
        }
        model.addAttribute("dtypes", dtypes);

        if(getUser().getStaff() != null)
            model.addAttribute("documentSubTypes", documentType.getDocumentSubTypes());

        return "/doc/document/index";
    }

    @RequestMapping(value = "/new/{subType}", method = RequestMethod.GET)
    public String add(@PathVariable("subType") String subType, Model model) {

        /*
        for(Document d : documentService.list())
        {
            documentService.remove(d);
        }
        */

        if(getUser() == null) return "/login/login";

        Document document = new Document();
        document.setOwner(getUser());
        document.setDocumentType(documentSubTypeService.getByInternalName(subType).getDocumentType());
        document.setDocumentSubType(documentSubTypeService.getByInternalName(subType));

        if(document.getDocumentType().getInternalName() == "incoming")
        {
            document.setDocIndex(registerService.getNumber());
            document.getReceiverResponsible().setResponsibleType(1);
            document.setResolution("Обработать входящий документ");
            model.addAttribute("dto", document.getDocumentType().getInternalName());
        }

        if(document.getDocumentType().getInternalName() == "outgoing")
        {
            document.getSenderResponsible().setResponsibleType(1);
            document.getSenderResponsible().getStaff().add(userService.findByUsername("ruk001").getStaff());
        }
        //**************************************************************************************************************

        model.addAttribute("no", registerService.getNumber());
        model.addAttribute("hasComment", hasComment(document));
        model.addAttribute("actions", getActions(document));
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentState",  document.getDocumentState().toString());

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);

        if(document.getDocumentType().getInternalName().equals("incoming") && document.getDocumentState().ordinal() > 7) {

            Map<String, String> vars = new HashMap<>();
            vars.put("objectId", String.valueOf(document.getId()));
            vars.put("status", "OPEN");
            Task task = taskService.getTask(getUser(), vars);
            task.setModifiedByUserId(1);
            taskService.update(task);
        }

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

        model.addAttribute("hasComment", hasComment(document));
        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("actions", getActions(document));
        model.addAttribute("document", document);
        model.addAttribute("documentState", document.getDocumentState().toString());

        //model.addAttribute("mydocs", documentService.getDocuments(0,"incoming", null));

        return "/doc/document/edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("document") Document document,
                       @RequestParam("action") String action,
                       @RequestParam(value = "files", required=false) long files[]) {

        if(getUser() == null) return "/login/login";

        String docType = documentTypeService.getById(document.getDocumentType().getId()).getInternalName();

        document.getUsers().add(getUser());

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

        if(action.equals("UPDATE")) {

            update(document);
        }
        else
        {
            if (docType.equals("internal")) {
                saveInternalDocument(document, action);
            } else if (docType.equals("incoming")) {
                saveIncomingDocument(document, action);
            } else {
                saveOutgoingDocument(document, action);
            }
        }

        return "redirect:/doc?type=" + docType;
    }

    public void update(Document document) {

        Document doc = documentService.getById(document.getId());

        document.setUsers(doc.getUsers());
        document.setDispatchData(doc.getDispatchData());
        documentService.update(document);
    }

    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        //List<Document> documents = documentService.list(null, null, 0L, 1, 20, "documentType", "asc", null, "");

        Document document = documentService.getById(id);

        Map<String, String> vars = new HashMap<>();
        vars.put("objectId", String.valueOf(document.getId()));

        model.addAttribute("tasks", taskService.getTasks(vars));
        //model.addAttribute("accounts", chatUserService.list(10,5));
        //model.addAttribute("stages", getStages(document.getDocumentType().getInternalName()));
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
    private List<Transition> getActions(Document document) {
        int row = document.getDocumentType().getId() < 4 ? (int)document.getDocumentType().getId()-1 : 3;
        int col = document.getDocumentState().ordinal();

        List<Transition> actions = new ArrayList<>();
        for(Transition action : ACTIONS[row][col])
        {
            actions.add(action);
        }
        return actions;
    }
    private boolean hasComment(Document document) {

        boolean hasComment = false;

        int row = document.getDocumentType().getId() < 4 ? (int)document.getDocumentType().getId()-1 : 3;
        int col = document.getDocumentState().ordinal();

        if(ACTIONS[row][col].length > 1){
            hasComment = ACTIONS[row][col][0].equals(Transition.REJECT) || ACTIONS[row][col][0].equals(Transition.DONE) ? true : false;
        }
        return hasComment;
    }

    private DispatchData setDispatchData(State state, String description) {

        DispatchData dispatchData = new DispatchData();

        dispatchData.setDescription(description);
        dispatchData.setDispatchType(state);
        dispatchData.setDispatchInitTime(new Date());
        dispatchData.setDispatchBy(getUser());

        return dispatchData;
    }

    private void addTask(String description, Long id, User user, Date dueDate, User toUser, State stateToBeginWith, String tmp) {

        Task task = new Task();
        task.setDescription(description);
        task.setSummary(tmp);

        if(stateToBeginWith != null) {
            task.setProgress(stateToBeginWith.toString());
        }

        task.setObjectId(id);
        task.setObjectType(Document.class.getSimpleName());
        task.setCreatedBy(user);
        task.setAssignedTo(toUser);
        task.setTargetResolutionDate(dueDate);
        task.setModifiedByUserId(0);

        taskService.add(task);
    }
    private Document saveInternalDocument(Document document, String action) {

        String description = document.getComment();

        //region New Document
        if(document.getId() == 0)
        {
            document.setDocumentState(Transition.valueOf(action.toUpperCase()).state());
            document.getDispatchData().add(setDispatchData(State.DRAFT, ""));
            documentService.add(document);

            if(action.equals("TORECONCILE"))
            {
                for(Staff staff : document.getReconciler())
                {
                    document.getUsers().add(getUser(staff));
                    addTask(State.PENDING.text(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), State.PENDING, "");
                    document.getDispatchData().add(setDispatchData(State.PENDING, staff.getName()));
                }
            }

            for(Staff staff : document.getSenderResponsible().getStaff())
            {
                document.getUsers().add(getUser(staff));
                addTask(State.REQUESTED.text(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), null, "");
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

                if (document.getDocumentState().equals(State.ACCEPTED) && taskCount == 0)
                {
                    document.setDocumentState(State.DONE);
                }
                //endregion
            }

            if (action.equals("APPROVE"))
            {
                //region [Description, User, Task]
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());

                document.setSenderRegisteredNumber(registerService.generateRegistrationNumber(doc, getUser().getId()));
                document.setSenderRegisteredDate(new Date());
                description = "<strong>Зарегистрирован</strong>"
                        + "<br>Исходящий № : " + document.getSenderRegisteredNumber()
                        + "<br>Дата : " + DATE_FORMAT.format(document.getSenderRegisteredDate());

                for (Staff staff : document.getReceiverResponsible().getStaff())
                {
                    document.getUsers().add(getUser(staff));
                    addTask("Документ на обработку", document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), null, "");
                }

                document.getDispatchData().add(setDispatchData(State.APPROVED, description));
                document.setDocumentState(State.APPROVED);
                //endregion
            }

            if(action.equals("REJECT"))
            {
                //region Description
                taskService.completeTask(document.getId(), getUser(), State.REJECTED.text() + "<br>" + document.getComment());
                addTask("Доработать", document.getId(), getUser(), document.getDocumentDueDate(), document.getOwner(), State.DRAFT, "");
                document.getDispatchData().add(setDispatchData(State.REJECTED, description));
                //endregion
            }

            if (action.equals("ACCEPT"))
            {
                //region [Description, User, AutoTask]
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());

                document.setReceiverRegisteredNumber(registerService.generateRegistrationNumber(doc, getUser().getId()));
                document.setReceiverRegisteredDate(new Date());
                description = "<strong>Зарегистрирован</strong>"
                        + "<br>Входящий № : " + document.getReceiverRegisteredNumber()
                        + "<br>Дата : " + DATE_FORMAT.format(document.getReceiverRegisteredDate());

                for (Staff staff : document.getReceiverResponsible().getStaff())
                {
                    addTask("Назначить исполнителя", document.getId(), null, document.getDocumentDueDate(), getUser(staff), State.ACCEPTED, "");
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
                    addTask(document.getResolution(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), State.SENT, "");
                    document.getDispatchData().add(setDispatchData(State.SENT, staff.getName()));
                }

                executor.setStaff(staffSet);
                document.setReceiverExecutor(executor);
                //endregion
            }

            if(action.equals("DONE"))
            {
                //region Description
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
                //endregion
            }


            // *********************************************************************************************************
            // *************************************** Add Dispatch Data ***********************************************
            // *********************************************************************************************************

            documentService.update(document);
        }
        //endregion

        return document;
    }
    private Document saveIncomingDocument(Document document, String action) {

        String description = document.getComment() != null || !document.getComment().isEmpty() ? document.getComment() : "Выполнен";

        //region New Document
        if(document.getId() == 0) {
            //document.getUsers().add(getUser());
            document.setDocumentState(Transition.valueOf(action).state());

            document.getDispatchData().add(setDispatchData(State.DRAFT, ""));
            documentService.add(document);

            document.setReceiverRegisteredNumber(registerService.generateRegistrationNumber(document, getUser().getId()));
            document.setReceiverRegisteredDate(new Date());
            document.getDispatchData().add(setDispatchData(State.REGISTERED, ""));
            documentService.update(document);

            addTask("Отправить ответственным", document.getId(), null, document.getDocumentDueDate(), getUser(), null, document.getResolution());
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
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", String.valueOf(document.getId()));
                vars.put("status", "OPEN");
                Task task = taskService.getTask(getUser(), vars);
                String res = !task.getSummary().isEmpty() ? task.getSummary() : "Обработать входящий документ";

                taskService.completeTask(document.getId(), getUser(), "Выполнен");
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));
                document.setDocumentState(State.ACCEPTED);

                for (Staff staff : document.getReceiverResponsible().getStaff())
                {
                    if(doc.getDocumentState().ordinal() > State.REGISTERED.ordinal())
                    {
                        if(!doc.getReceiverResponsible().getStaff().contains(staff)) {
                            document.getUsers().add(getUser(staff));
                            addTask(res, document.getId(), getUser(), document.getTaskDueDate(), getUser(staff), null, "");
                        }
                    }
                    else {
                        document.getUsers().add(getUser(staff));
                        addTask(res, document.getId(), getUser(), document.getTaskDueDate(), getUser(staff), null, "");
                    }
                }
                //endregion
            }

            if (action.equals("SEND"))
            {
                //region SEND
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

                if(doc.getReceiverExecutor() != null)
                {
                    document.setReceiverExecutor(doc.getReceiverExecutor());
                }
                else
                {
                    Executor executor = new Executor();
                    executor.setExecutorType(1);
                    document.setReceiverExecutor(executor);
                }

                for (Staff staff : document.getExecutor())
                {
                    document.getReceiverExecutor().getStaff().add(staff);
                    document.getUsers().add(getUser(staff));
                    addTask(document.getResolution(), document.getId(), getUser(), document.getTaskDueDate(), getUser(staff), State.SENT, "");
                }
                //endregion
            }

            if (action.equals("DONE"))
            {
                //region DONE
                taskService.completeTask(document.getId(), getUser(), document.getComment());
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

            if (action.equals("REJECT"))
            {
                //region DONE
                taskService.completeTask(document.getId(), getUser(), "Отклонен " + document.getComment());
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

                addTask("Переназначить", document.getId(), getUser(), document.getDocumentDueDate(), document.getOwner(), State.REGISTERED, "");
                //endregion
            }

            documentService.update(document);
        }
        //endregion

        return document;
    }
    private Document saveOutgoingDocument(Document document, String action) {

        String description = document.getComment();

        //region New Document
        if(document.getId() == 0)
        {
            //document.getUsers().add(getUser());
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
                addTask(Transition.REGISTER.text(), document.getId(), null, document.getDocumentDueDate(), user, State.APPROVED, "");
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

            if (action.equals("REGISTER"))
            {
                //region [Description, User, AutoTask]
                document.setSenderRegisteredNumber(registerService.generateRegistrationNumber(document, getUser().getId()));
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
                    addTask(Transition.DONE.text(), document.getId(), null, document.getDocumentDueDate(), user, null, "");
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

        return document;
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

    @RequestMapping(value = "/save/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String register(@PathVariable("id") Long id) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);
        document = saveOutgoingDocument(document, "REGISTER");

        return document.getSenderRegisteredNumber();
    }

    @RequestMapping("/data/staff")
    @ResponseBody
    public List<SearchResult> getStaff(@RequestParam String name) {

        List<SearchResult> data = new ArrayList<>();
        for(Account account : accountService.getByName("staff", name))
        {
            data.add(new SearchResult(account.getId(), account.getName()));
        }

        return data;
    }

    @RequestMapping("/data/department")
    @ResponseBody
    public List<SearchResult> getDepartment(@RequestParam String name) {

        List<SearchResult> data = new ArrayList<>();
        for(Account account : accountService.getByName("department", name)) {
            data.add(new SearchResult(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/data/organizations")
    @ResponseBody
    public List<SearchResult> getOrganization(@RequestParam String name) {
        List<SearchResult> data = new ArrayList<>();
        for(Account account : accountService.getByName("organization", name)) {
            data.add(new SearchResult(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/data/person")
    @ResponseBody
    public List<SearchResult> getPerson(@RequestParam String name) {
        List<SearchResult> data = new ArrayList<>();
        for(Account account : accountService.getByName("person", name))
        {
            data.add(new SearchResult(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/data/user")
    @ResponseBody
    public List<SearchResult> getUser(@RequestParam String name) {

        List<SearchResult> data = new ArrayList<>();

        for(ChatUser chatUser : chatUserService.getAllByName(name))
        {
            data.add(new SearchResult(chatUser.getId(), chatUser.getName()));
        }
        return data;
    }

    @RequestMapping(value = "/documents")
    @ResponseBody
    public DataTablesOutput<DocView> getDocuments(@Valid DataTablesInput input, @RequestParam("docType") String docType) {

        long dt = documentTypeService.getByInternalName(docType).getId();
        int count = documentViewDao.count(dt);

        DataTableResult dataTableResult = documentViewDao.list(dt, getUser().getId(), input);

        DataTablesOutput<DocView> docs = new DataTablesOutput<>();

        docs.setDraw(input.getDraw());
        docs.setRecordsTotal(count);
        docs.setRecordsFiltered(dataTableResult.getCount());
        docs.setData(dataTableResult.getData());

        return docs;
    }

    @RequestMapping(value = "/dispatchData/{id}")
    @ResponseBody
    public List<DispatchData> getDispatchData(@PathVariable("id") long id) {

        List<DispatchData> dispatchData = new ArrayList<>();
        dispatchData.addAll(documentService.getById(id).getDispatchData());

        return dispatchData;
    }

    @RequestMapping(value = "/tasks/{id}")
    @ResponseBody
    public List getTasks(@PathVariable("id") long id) {

        String query = "select " +
                " t.createdOn as createdOn," +
                " t.createdBy.staff.name as createdBy," +
                " t.description as description," +
                " t.targetResolutionDate as targetResolutionDate," +
                " t.assignedTo.staff.name as assignedTo," +
                " t.resolutionSummary as resolutionSummary," +
                " t.actualResolutionDate as actualResolutionDate" +
                " from Task t where t.objectId = :objectId and t.objectType = 'Document'";
        return entityManager.createQuery(query)
                .setParameter("objectId", id)
                .getResultList();
    }

    @RequestMapping("/incomingdocuments")
    @ResponseBody
    public List<SearchResult> getDocs(@RequestParam String name) {

        List<SearchResult> data = new ArrayList<>();

        for(Document document : (List<Document>)documentService.searchOutgoingDocuments(name))
        {
            data.add(new SearchResult(document.getId(), document.getDocumentSubType().getName() + " №" + document.getSenderRegisteredNumber()));
        }

        return data;
    }
}