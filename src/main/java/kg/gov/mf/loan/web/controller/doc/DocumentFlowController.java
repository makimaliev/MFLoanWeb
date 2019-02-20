package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.task.model.ChatUser;
import kg.gov.mf.loan.task.model.SystemConstant;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.service.ChatUserService;
import kg.gov.mf.loan.task.service.SystemConstantService;
import kg.gov.mf.loan.task.service.TaskService;
import kg.gov.mf.loan.web.controller.doc.dto.DataTableResult;
import kg.gov.mf.loan.web.controller.doc.dto.JsonDocument;
import kg.gov.mf.loan.web.controller.doc.dto.SearchResult;
import kg.gov.mf.loan.web.fetchModels.DocumentMetaModel;
import kg.gov.mf.loan.web.fetchModels.DocumentModel;
import kg.gov.mf.loan.web.util.Meta;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.text.ParseException;
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
                            { Transition.DONE, Transition.SEND },               // ACCEPTED
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

        List<Document> documents = documentService.getDocuments(getUser().getId(), type, null);

        DocumentType documentType = documentTypeService.getByInternalName(type);

        Map<String, String> vars = new HashMap<>();
        vars.put("assignedTo", String.valueOf(getUser().getId()));
        vars.put("status", "OPEN");

        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("ACTIONS", ACTIONS);
        model.addAttribute("documents", documents);
        model.addAttribute("title", documentType.getName());
        model.addAttribute("type", type);

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

        //document.setDocumentState(curretState);

        //model.addAttribute("stages", getStages(currentView));
        model.addAttribute("hasComment", hasComment(document));
        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("actions", getActions(document));
        model.addAttribute("document", document);
        model.addAttribute("documentState", document.getDocumentState().toString());

        model.addAttribute("mydocs", documentService.getDocuments(0,"incoming", null));

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

        String description = document.getComment();

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
                    document.getUsers().add(getUser(staff));
                    addTask(res, document.getId(), getUser(), document.getTaskDueDate(), getUser(staff), null, "");
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

    @RequestMapping(value = "/documentList")
    @ResponseBody
    public List<JsonDocument> getDocuments(String documentType) {

        List<JsonDocument> jsonDocuments = new ArrayList<>();

        for(Document document : documentService.getDocuments(0, "outgoing", null))
        {
            JsonDocument jd = new JsonDocument();
            jd.setId(document.getId());
            jd.setDocIndex(document.getDocIndex());
            jd.setPageCount(document.getPageCount());
            jd.setIndexNo(document.getIndexNo());
            jd.setDocumentState(document.getDocumentState().text());
            jd.setOwner(document.getOwner().getStaff().getName());
            jd.setDocumentSubType(document.getDocumentSubType().getName());
            jd.setDocumentDueDate(document.getDocumentDueDate());
            jd.setTitle(document.getTitle());
            jd.setSenderRegisteredNumber(document.getSenderRegisteredNumber());
            jd.setSenderRegisteredDate(document.getSenderRegisteredDate());
            jd.setReceiverRegisteredNumber(document.getReceiverRegisteredNumber());
            jd.setReceiverRegisteredDate(document.getReceiverRegisteredDate());
            // Senders
            List<String> senders = new ArrayList<>();
            if(document.getSenderResponsible().getResponsibleType() == 1)
            {
                for(Staff staff : document.getSenderResponsible().getStaff())
                {
                    senders.add(staff.getName());
                }
            } else if(document.getSenderResponsible().getResponsibleType() == 2)
            {
                for(Department department : document.getSenderResponsible().getDepartments())
                {
                    senders.add(department.getName());
                }
            }
            else if(document.getSenderResponsible().getResponsibleType() == 3)
            {
                for(Organization organization : document.getSenderResponsible().getOrganizations())
                {
                    senders.add(organization.getName());
                }
            } else {
                for(Person person : document.getSenderResponsible().getPerson())
                {
                    senders.add(person.getName());
                }
            }

            List<String> receivers = new ArrayList<>();
            if(document.getReceiverResponsible().getResponsibleType() == 1)
            {
                for(Staff staff : document.getReceiverResponsible().getStaff())
                {
                    receivers.add(staff.getName());
                }
            } else if(document.getReceiverResponsible().getResponsibleType() == 2)
            {
                for(Department department : document.getReceiverResponsible().getDepartments())
                {
                    receivers.add(department.getName());
                }
            }
            else if(document.getReceiverResponsible().getResponsibleType() == 3)
            {
                for(Organization organization : document.getReceiverResponsible().getOrganizations())
                {
                    receivers.add(organization.getName());
                }
            } else {
                for(Person person : document.getReceiverResponsible().getPerson())
                {
                    receivers.add(person.getName());
                }
            }

            jd.setSenderResponsible(senders.toArray(new String[0]));
            jd.setSenderResponsible(receivers.toArray(new String[0]));

            jsonDocuments.add(jd);
        }

        return jsonDocuments;
    }

//    @RequestMapping("/documents")
//    @ResponseBody
//    public DataTableResult getDocuments(HttpServletRequest request) {
//
//        Map<String, String[]> map = request.getParameterMap();
//
//        List<String> columns = new ArrayList<String>();
//        int col = 0;
//
//        for (Map.Entry<String, String[]> entry : map.entrySet())
//        {
//            if(entry.getKey().contains("columns"))
//            {
//                if (entry.getKey().matches("columns\\[\\d+\\]\\[searchable\\]"))
//                {
//                    if (entry.getValue()[0].equals("true"))
//                    {
//                        columns.add(request.getParameter("columns[" + col + "][name]"));
//                    }
//                    col++;
//                }
//            }
//        }
//
//        String orderColumn = request.getParameter("order[0][column]") != null ? request.getParameter("order[0][column]") : "id";
//        String orderDirection = request.getParameter("order[0][dir]") != null ? request.getParameter("order[0][dir]") : "asc";
//        String columnToOrder = request.getParameter("columns[" + orderColumn + "][name]") != "0" ? request.getParameter("columns[" + orderColumn + "][name]") : "id";
//        String docType = request.getParameter("documentType");
//        String searchValue = request.getParameter("search[value]");
//        int start = Integer.valueOf(request.getParameter("start"));
//        int length = Integer.valueOf(request.getParameter("length"));
//        int draw = Integer.valueOf(request.getParameter("draw"));
//        int count = documentService.count(docType);
//
//        List<Document> data = documentService.list(docType, null, 0, start, length, columnToOrder, orderDirection, columns.toArray(new String[0]), searchValue);
//
//        DataTableResult dataTableResult = new DataTableResult();
//        dataTableResult.setDraw(draw);
//        dataTableResult.setRecordsTotal(count);
//        dataTableResult.setRecordsFiltered(count);
//        dataTableResult.setData(data);
//        //dataTableResult.setError(searchValue);
//
//        return dataTableResult;
//    }

    @RequestMapping("/documents/{docType}")
    @ResponseBody
    public DocumentMetaModel getDocuments(@RequestParam Map<String, String> datatable,@PathVariable("docType") String docType) throws ParseException {

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;
        String titleQuery="";
        String receiverQuery="";
        String senderQuery="";
        String fromDateQuery="";
        String toDateQuery="";
        String subTypeQuery="";


        boolean searchByTitle= datatable.containsKey("datatable[query][title]");
        if(searchByTitle){
            String word= datatable.get("datatable[query][title]");
            titleQuery="and title like '%"+word+"%'\n";
        }

        boolean searchByReceiver= datatable.containsKey("datatable[query][receiver]");
        if(searchByReceiver){
            String word= datatable.get("datatable[query][receiver]");
            receiverQuery="and receiverResponsible in( select r_id from responsible_view where r_name like '%"+word+"%')\n";
        }

        boolean searchBySender= datatable.containsKey("datatable[query][sender]");
        if(searchBySender){
            String word= datatable.get("datatable[query][sender]");

            senderQuery="and senderResponsible in( select r_id from responsible_view where r_name like '%"+word+"%')\n";
        }

        boolean searchByDocType= datatable.containsKey("datatable[query][docType]");
        if(searchByDocType){
            String word= datatable.get("datatable[query][docType]");
            subTypeQuery="and documentSubType = '"+word+"'\n";
        }

        SimpleDateFormat dt = new SimpleDateFormat("yyyy.MM.dd");

        boolean getFromDate= datatable.containsKey("datatable[query][fromDate]");
        if (getFromDate){
            String from = datatable.get("datatable[query][fromDate]");
            String[] words=from.split("\\.");
            fromDateQuery="and senderRegisteredDate>='"+words[2]+"-"+words[1]+"-"+words[0]+"'\n";
        }

        boolean getToDate= datatable.containsKey("datatable[query][toDate]");
        if (getToDate){
            String to = datatable.get("datatable[query][toDate]");
            String[] words=to.split("\\.");
            toDateQuery="and senderRegisteredDate<='"+words[2]+"-"+words[1]+"-"+words[0]+"'\n";
        }


        DocumentType documentType=documentTypeService.getByInternalName(docType);
        User user=userService.findByUsername(Utils.getPrincipal());

        String baseQuery="select d.id ,d.docIndex,d.indexNo,d.title,d.documentDueDate,\n" +
                "       d.receiverRegisteredDate,d.receiverRegisteredNumber,\n" +
                "       d.senderRegisteredDate,d.senderRegisteredNumber,\n" +
                "       d.documentState,dst.name as documentSubType,d.owner,\n" +
                "       d.senderResponsible as senderResponsible,d.pageCount,\n" +
                "       d.receiverResponsible as receiverResponsible,\n" +
                "       d.senderExecutor as senderExecutor,d.documentState, \n" +
                "       d.receiverExecutor as receiverExecutor, 'false' as hasTask\n"+
                "from df_document d,cat_document_subtype dst,df_document_users du where d.documentType="+documentType.getId()+" and  dst.id=d.documentSubType " +
                titleQuery+
                senderQuery+
                receiverQuery+
                subTypeQuery+
                fromDateQuery+
                toDateQuery+
                "and du.Document_id=d.id and du.users_id="+user.getId()+" \n"+
                "order by " + sortField + " " + sortStr + " LIMIT " + offset +"," + perPage;

        Query query=entityManager.createNativeQuery(baseQuery, DocumentModel.class);

        String countQuery="select count(1)\n" +
                "from df_document d,cat_document_subtype dst,df_document_users du where d.documentType="+documentType.getId()+" and  dst.id=d.documentSubType " +
                titleQuery+
                senderQuery+
                receiverQuery+
                subTypeQuery+
                fromDateQuery+
                toDateQuery+
                "and du.Document_id=d.id and du.users_id="+user.getId();
        BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);

        List<DocumentModel> documents=query.getResultList();

        for(DocumentModel documentModel:documents){
            User user1=userService.findById(Long.valueOf(documentModel.getOwner()));
            State state=State.valueOf(documentModel.getDocumentState());
            documentModel.setDocumentState(state.text());
            documentModel.setOwner(user1.getStaff().getName());

            Map<String, String> vars = new HashMap<>();
            vars.put("objectId", String.valueOf(documentModel.getId()));
            vars.put("status", "OPEN");
            try {
                Task taskList = taskService.getTask(getUser(), vars);
                if(taskList!=null)
                    documentModel.setHasTask("true");
            }
            catch (Exception e){}

//            List<Task> tasks=taskService.getTasksByObjectId(documentModel.getId());
//            for(Task task:tasks){
//                if(task.getAssignedTo()==user && task.getStatus()==TaskStatus.OPEN){
//                    documentModel.setHasTask("true");
//                }
//            }
            String senders = "";
            try {
                Responsible senderResponsible = responsibleService.getById(Long.valueOf(documentModel.getSenderResponsible()));
                if (senderResponsible.getResponsibleType() == 1) {
                    for (Staff staff : senderResponsible.getStaff()) {
                        if (senders.length() == 0)
                            senders = senders + staff.getName();
                        else
                            senders = senders + "<br> " + staff.getName();
                    }
                } else if (senderResponsible.getResponsibleType() == 2) {
                    for (Department department : senderResponsible.getDepartments()) {
                        if (senders.length() == 0)
                            senders = senders + department.getName();
                        else
                            senders = senders + "<br> " + department.getName();
                    }
                } else if (senderResponsible.getResponsibleType() == 3) {
                    for (Organization organization : senderResponsible.getOrganizations()) {
                        if (senders.length() == 0)
                            senders = senders + organization.getName();
                        else
                            senders = senders + "<br> " + organization.getName();
                    }
                } else {
                    for (Person person : senderResponsible.getPerson()) {
                        if (senders.length() == 0)
                            senders = senders + person.getName();
                        else
                            senders = senders + "<br> " + person.getName();
                    }
                }
            }
            catch (Exception e){

            }
            documentModel.setSenderResponsible(senders);
            String receivers = "";
            Responsible receiverResponsible=responsibleService.getById(Long.valueOf(documentModel.getReceiverResponsible()));
            if(receiverResponsible.getResponsibleType() == 1)
            {
                for(Staff staff : receiverResponsible.getStaff())
                {
                    if(receivers.length()==0)
                        receivers=receivers+staff.getName();
                    else
                        receivers=receivers+"<br> "+staff.getName();
                }
            } else if(receiverResponsible.getResponsibleType() == 2)
            {
                for(Department department : receiverResponsible.getDepartments())
                {
                    if(receivers.length()==0)
                        receivers=receivers+department.getName();
                    else
                        receivers=receivers+"<br> "+department.getName();
                }
            }
            else if(receiverResponsible.getResponsibleType() == 3)
            {
                for(Organization organization : receiverResponsible.getOrganizations())
                {
                    if(receivers.length()==0)
                        receivers=receivers+organization.getName();
                    else
                        receivers=receivers+"<br> "+organization.getName();
                }
            } else {
                for(Person person : receiverResponsible.getPerson())
                {
                    if(receivers.length()==0)
                        receivers=receivers+person.getName();
                    else
                        receivers=receivers+"<br> "+person.getName();
                }
            }
            documentModel.setReceiverResponsible(receivers);
        }

        DocumentMetaModel metaModel = new DocumentMetaModel();
        Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
        metaModel.setMeta(meta);
        metaModel.setData(documents);

//        try {
//            query.getResultList();
//        }
//        catch(Exception e){
//            System.out.println(e);
//        }

        return metaModel;
    }

    @RequestMapping(value = "/dt/documents")
    @ResponseBody
    public DataTablesOutput<Document> getDocuments(@Valid @RequestBody DataTablesInput input) {

        int count = documentService.count("incoming");
        List<Document> data = documentService.list("incoming", null, 0, 0, 0, "id", "asc", null, null);

        DataTablesOutput<Document> docs = new DataTablesOutput<>();

        docs.setDraw(input.getDraw());
        docs.setRecordsTotal(count);
        docs.setRecordsFiltered(count);
        docs.setData(data);

        return docs;
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