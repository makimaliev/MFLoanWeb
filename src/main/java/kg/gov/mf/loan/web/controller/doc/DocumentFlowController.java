package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.doc.dao.DocViewDao;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.task.model.ChatUser;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.model.TaskStatus;
import kg.gov.mf.loan.task.service.ChatUserService;
import kg.gov.mf.loan.task.service.SystemConstantService;
import kg.gov.mf.loan.task.service.TaskActionService;
import kg.gov.mf.loan.task.service.TaskService;
import kg.gov.mf.loan.web.controller.doc.dto.SearchResult;
import org.hibernate.Query;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.*;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/doc")
public class DocumentFlowController extends BaseController {

    //region Dependencies
    final DocumentService documentService;
    final DocumentTypeService documentTypeService;
    final DocumentSubTypeService documentSubTypeService;
    final TaskService taskService;
    final CounterService registerService;
    final AccountService accountService;
    final SystemConstantService systemConstantService;
    final ChatUserService chatUserService;
    final DocViewDao documentViewDao;
    final EntityManager entityManager;
    final AttachmentService attachmentService;
    final TaskActionService taskActionService;
    final StaffService staffService;
    final DispatchDataService dispatchDataService;

    @Autowired
    public DocumentFlowController(DocumentService documentService,
                                  DocumentTypeService documentTypeService,
                                  DocumentSubTypeService documentSubTypeService,
                                  TaskService taskService,
                                  CounterService registerService,
                                  AccountService accountService,
                                  SystemConstantService systemConstantService,
                                  ChatUserService chatUserService,
                                  DocViewDao documentViewDao,
                                  EntityManager entityManager,
                                  AttachmentService attachmentService,
                                  TaskActionService taskActionService,
                                  StaffService staffService,
                                  DispatchDataService dispatchDataService) {

        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
        this.documentSubTypeService = documentSubTypeService;
        this.taskService = taskService;
        this.registerService = registerService;
        this.accountService = accountService;
        this.systemConstantService = systemConstantService;
        this.chatUserService = chatUserService;
        this.documentViewDao = documentViewDao;
        this.entityManager = entityManager;
        this.attachmentService = attachmentService;
        this.taskActionService = taskActionService;
        this.staffService = staffService;
        this.dispatchDataService = dispatchDataService;
    }
    //endregion
    //region ACTIONS
    final Transition[][][] ACTIONS =
            {
                    {   // Internal
                            { Transition.REQUEST, Transition.TORECONCILE },     // NEW
                            { Transition.REQUEST, Transition.TORECONCILE },     // DRAFT
                            { Transition.REJECT, Transition.RECONCILE },        // PENDING
                            { Transition.REQUEST, Transition.TORECONCILE },     // RECONCILED
                            { Transition.REJECT, Transition.APPROVE },          // REQUESTED
                            { /*Transition.REJECT,*/ Transition.ACCEPT },           // APPROVED
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

    // Index
    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam("type") String type, Model model) {

        if(getUser() == null)
            return "/login/login";

        DocumentType documentType = documentTypeService.getByInternalName(type);

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

        if(getUser().getStaff() != null && (type.equals("internal") || type.equals("incoming") || type.equals("outgoing")))
            model.addAttribute("documentSubTypes", documentType.getDocumentSubTypes());

        return "/doc/document/index";
    }

    // Create New Document
    @RequestMapping(value = "/new/{subType}", method = RequestMethod.GET)
    public String add(@PathVariable("subType") String subType, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = new Document();
        document.setOwner(getUser());
        document.setDocumentType(documentSubTypeService.getByInternalName(subType).getDocumentType());
        document.setDocumentSubType(documentSubTypeService.getByInternalName(subType));

        if(document.getDocumentType().getInternalName().equals("incoming"))
        {
            document.setDocIndex(registerService.getIncomingNumber());
            document.setResolution("Обработать входящий документ");
            //model.addAttribute("dto", document.getDocumentType().getInternalName());
        }

        if(document.getDocumentType().getInternalName().equals("outgoing"))
        {
            Responsible responsible = new Responsible();
            responsible.setResponsibleType(1);
            Set<Staff> staff = new HashSet<>(0);
            staff.add(userService.findByUsername("ruk001").getStaff());
            responsible.setStaff(staff);
            document.setSenderResponsible(responsible);
        }
        //**************************************************************************************************************

        model.addAttribute("hasComment", hasComment(document));
        model.addAttribute("actions", getActions(document));
        model.addAttribute("document", document);

        return "/doc/document/edit";
    }

    // Edit Document
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);

        Map<String, String> vars = new HashMap<>();
        vars.put("objectId", String.valueOf(id));
        vars.put("status", "OPEN");
        vars.put("objectType", "Document");

        Task task = taskService.getTask(getUser(), vars);

        if(task != null) {

            task.setModifiedByUserId(getUser().getId());
            taskService.update(task);

            if(task.getProgress() != null)
            {
                document.setDocumentState(State.valueOf(task.getProgress()));
            }
        }

        if(document.getDocumentType().getInternalName().equals("outgoing") && document.getDocIndex() == 0)
        {
            document.setSenderRegisteredDate(new Date());
        }

        Map<String, Object> var = new HashMap<>();
        var.put("objectId", document.getId());
        var.put("objectType", "Document");

        model.addAttribute("tasks", taskService.getTasks(var));
        model.addAttribute("uid", getUser().getId());
        model.addAttribute("hasComment", hasComment(document));
        model.addAttribute("actions", getActions(document));
        model.addAttribute("document", document);
        model.addAttribute("documentState", document.getDocumentState().toString());

        return "/doc/document/edit";
    }

    // Save Document
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("document") Document document, @RequestParam("action") String action, @RequestParam(value = "files", required=false) long files[]) {

        if(getUser() == null) return "/login/login";

        String docType = documentTypeService.getById(document.getDocumentType().getId()).getInternalName();

        if(document.getId() != 0)
        {
            Document doc = documentService.getById(document.getId());
            document.setAttachments(doc.getAttachments());
            document.setUsers(doc.getUsers());
        }

        document.getUsers().add(getUser().getId());

        for(long file : files)
        {
            if(file != 0)
                document.getAttachments().add(attachmentService.getById(file));
        }

        if(action.equals("UPDATE"))
        {
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

    // View Document
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        //hqlTester();

        Map<String, Object> vars = new HashMap<>();
        vars.put("objectId", id);
        vars.put("objectType", "Document");

        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("uid", getUser().getId());
        model.addAttribute("document", documentService.getById(id));
        return "/doc/document/view";
    }

    // User Task List
    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public String report() {

        if(getUser() == null)
            return "/login/login";

        return "/doc/document/taskReport";
    }

    List<Transition> getActions(Document document) {
        int row = document.getDocumentType().getId() < 4 ? (int)document.getDocumentType().getId()-1 : 3;
        int col = document.getDocumentState().ordinal();

        List<Transition> actions = new ArrayList<>();
        for(Transition action : ACTIONS[row][col])
        {
            actions.add(action);
        }
        return actions;
    }
    boolean hasComment(Document document) {

        boolean hasComment = false;

        int row = document.getDocumentType().getId() < 4 ? (int)document.getDocumentType().getId()-1 : 3;
        int col = document.getDocumentState().ordinal();

        if(ACTIONS[row][col].length > 1){
            hasComment = ACTIONS[row][col][0].equals(Transition.REJECT) || ACTIONS[row][col][0].equals(Transition.DONE) ? true : false;
        }
        return hasComment;
    }
    DispatchData setDispatchData(State state, String description) {

        DispatchData dispatchData = new DispatchData();

        dispatchData.setDescription(description);
        dispatchData.setDispatchType(state);
        dispatchData.setDispatchInitTime(new Date());
        dispatchData.setDispatchBy(getUser());

        return dispatchData;
    }
    void addTask(String description, Long id, User user, Date dueDate, User toUser, State stateToBeginWith, String tmp) {

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

    void runTask(Long objectId, User user, String result) {

        Map<String, String> vars = new HashMap<>();
        vars.put("status", "OPEN");
        vars.put("objectId", objectId.toString());
        vars.put("objectType", "Document");
        vars.put("assignedTo", String.valueOf(user.getId()));

        Task task = taskService.getTask(user, vars);
        if(task != null)
        {
            task.setStatus(TaskStatus.RUNNING);
            task.setResolutionSummary(result);
            task.setModifiedByUserId(getUser().getId());
            taskService.update(task);
        }
    }
    void closeTask(Long objectId, User user, String result) {

        Map<String, String> vars = new HashMap<>();
        vars.put("status", "RUNNING");
        vars.put("objectId", objectId.toString());
        vars.put("objectType", "Document");
        vars.put("assignedTo", String.valueOf(user.getId()));

        Task task = taskService.getTask(user, vars);
        if(task != null)
        {
            task.setActualResolutionDate(new Date());
            task.setStatus(TaskStatus.CLOSED);
            task.setResolutionSummary(result);
            task.setModifiedByUserId(user.getId());
            taskService.update(task);
        }
    }
    Integer taskCount(long documentId, User createdBy) {

        Map<String, Object> vars = new HashMap<>();
        vars.put("objectType", "Document");
        vars.put("objectId", documentId);
        vars.put("status", TaskStatus.RUNNING);
        vars.put("createdBy", createdBy);

        return taskService.getTasks(vars).size();
    }

    void hqlTester() {

        String department = "19-02-02";
        String q = "select t from Task t where t.assignedTo.staff.department.description = :department and t.status = 'OPEN'";
        List list = entityManager.createQuery(q, Task.class)
                .setParameter("department", department)
                .getResultList();
        Integer size = list.size();
    }

    Document saveInternalDocument(Document document, String action) {

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
                    document.getUsers().add(getUser(staff).getId());
                    addTask(State.PENDING.text(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), State.PENDING, "");
                    document.getDispatchData().add(setDispatchData(State.PENDING, staff.getName()));
                }
            }

            for(Staff staff : document.getSenderResponsible().getStaff())
            {
                document.getUsers().add(getUser(staff).getId());
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
            document.setDispatchData(doc.getDispatchData());

            if (action.equals("TORECONCILE"))
            {
                //region Description
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.setDocumentState(Transition.valueOf(action.toUpperCase()).state());

                for(Staff staff : document.getReconciler())
                {
                    if(!doc.getUsers().contains(staff.getUser().getId()))
                    {
                        document.getUsers().add(getUser(staff).getId());
                        addTask(State.PENDING.text(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), State.PENDING, "");
                        document.getDispatchData().add(setDispatchData(State.PENDING, staff.getName()));
                    }
                }

                for(Staff staff : document.getSenderResponsible().getStaff())
                {
                    //document.getUsers().add(getUser(staff));
                    addTask(State.REQUESTED.text(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), null, "");
                    document.getDispatchData().add(setDispatchData(State.REQUESTED, staff.getName()));
                }

                document.setDocumentState(State.REQUESTED);
                //endregion
            }

            if (action.equals("REQUEST"))
            {
                //region Description
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.setDocumentState(Transition.valueOf(action.toUpperCase()).state());

                for(Staff staff : document.getSenderResponsible().getStaff())
                {
                    //document.getUsers().add(getUser(staff));
                    addTask(State.REQUESTED.text(), document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), null, "");
                    document.getDispatchData().add(setDispatchData(State.REQUESTED, staff.getName()));
                }

                document.setDocumentState(State.REQUESTED);
                //endregion
            }

            if (action.equals("RECONCILE"))
            {
                //region Description
                taskService.completeTask(document.getId(), getUser(), Transition.valueOf(action).state().text());
                document.getDispatchData().add(setDispatchData(State.RECONCILED, ""));

                Map<String, Object> vars = new HashMap<>();
                vars.put("objectId", document.getId());
                vars.put("status", TaskStatus.OPEN);
                vars.put("objectType", "Document");
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
                    document.getUsers().add(getUser(staff).getId());
                    addTask("Документ на обработку", document.getId(), getUser(), document.getDocumentDueDate(), getUser(staff), null, "");
                }

                document.getDispatchData().add(setDispatchData(State.APPROVED, description));
                document.setDocumentState(State.APPROVED);
                //endregion
            }

            if(action.equals("REJECT"))
            {
                //region Description
                if(document.getDocumentState() == State.REQUESTED)
                {
                    taskService.completeTask(document.getId(), getUser(), State.REJECTED.text() + "<br>" + document.getComment());
                    addTask(description, document.getId(), getUser(), document.getDocumentDueDate(), document.getOwner(), State.DRAFT, "");
                    document.getDispatchData().add(setDispatchData(State.REJECTED, description));
                }
                if(document.getDocumentState() == State.APPROVED)
                {
                    taskService.completeTask(document.getId(), getUser(), State.REJECTED.text() + "<br>" + document.getComment());
                    document.getDispatchData().add(setDispatchData(State.REJECTED, description));
                }
                if(document.getDocumentState() == State.PENDING)
                {
                    taskService.completeTask(document.getId(), getUser(), State.REJECTED.text() + "<br>" + document.getComment());
                    document.getDispatchData().add(setDispatchData(State.REJECTED, description));

                    Map<String, Object> vars = new HashMap<>();
                    vars.put("objectId", document.getId());
                    vars.put("status", TaskStatus.OPEN);
                    vars.put("objectType", "Document");
                    int taskCount = taskService.getTasks(vars).size();

                    if (doc.getDocumentState().equals(State.ACCEPTED) && taskCount == 0)
                    {
                        document.setDocumentState(State.DONE);
                    }
                }
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
                    document.getUsers().add(getUser(staff).getId());
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

                Map<String, Object> vars = new HashMap<>();
                vars.put("objectId", document.getId());
                vars.put("status", TaskStatus.OPEN);
                vars.put("objectType", "Document");
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
    Document saveIncomingDocument(Document document, String action) {

        String description = document.getComment() != null /*|| !document.getComment().isEmpty()*/ ? document.getComment() : "Выполнен";

        //region New Document
        if(document.getId() == 0) {
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
            document.setDocumentState(doc.getDocumentState());
            document.setDispatchData(doc.getDispatchData());

            // ******************************************** Complete Tasks *********************************************
            if (action.equals("APPROVE"))
            {
                //region APPROVE
                Map<String, String> vars = new HashMap<>();
                vars.put("objectId", String.valueOf(document.getId()));
                vars.put("status", "OPEN");
                vars.put("objectType", "Document");
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
                            document.getUsers().add(getUser(staff).getId());
                            addTask(res, document.getId(), getUser(), document.getTaskDueDate(), getUser(staff), null, "");
                        }
                    }
                    else {
                        document.getUsers().add(getUser(staff).getId());
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
                    document.getUsers().add(getUser(staff).getId());
                    addTask(document.getResolution(), document.getId(), getUser(), document.getTaskDueDate(), getUser(staff), State.SENT, "");
                }
                //endregion
            }

            if (action.equals("DONE"))
            {
                //region DONE
                taskService.completeTask(document.getId(), getUser(), document.getComment());
                document.getDispatchData().add(setDispatchData(Transition.valueOf(action).state(), description));

                Map<String, Object> vars = new HashMap<>();
                vars.put("objectId", document.getId());
                vars.put("status", TaskStatus.OPEN);
                vars.put("objectType", "Document");
                int taskCount = taskService.getTasks(vars).size();

                if (taskCount == 0)
                {
                    document.setDocumentState(State.DONE);
                }
                //endregion
            }

            if (action.equals("REJECT"))
            {
                //region REJECT
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
    Document saveOutgoingDocument(Document document, String action) {

        String description;

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
                    document.getUsers().add(getUser(staff).getId());
                    document.getDispatchData().add(setDispatchData(State.PENDING, staff.getName()));
                }
            }

            for (Staff staff : document.getSenderResponsible().getStaff())
            {
                document.getUsers().add(getUser(staff).getId());
                document.getDispatchData().add(setDispatchData(State.REQUESTED, staff.getName()));
            }

            document.setDocumentState(State.APPROVED);

            for (User user : systemConstantService.getById(1).getOutgoingRegistrator())
            {
                document.getUsers().add(user.getId());
                addTask(Transition.REGISTER.text(), document.getId(), null, document.getDocumentDueDate(), user, State.APPROVED, "");
            }
            documentService.update(document);

        }
        //endregion
        //region Existing Document
        else
        {
            Document doc = documentService.getById(document.getId());
            document.setDocumentState(doc.getDocumentState());
            document.setDispatchData(doc.getDispatchData());

            if (action.equals("REGISTER"))
            {
                //region [Description, User, AutoTask]
                document.setSenderRegisteredNumber(registerService.generateRegistrationNumber(document, getUser().getId()));
                //document.setSenderRegisteredDate(new Date());
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
                    //document.getUsers().add(user.getId());
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
    // Update Document without changing State
    public void update(Document document) {

        Document doc = documentService.getById(document.getId());

        document.setUsers(doc.getUsers());
        document.setDispatchData(doc.getDispatchData());
        documentService.update(document);
    }

    // *****************************************************************************************************************
    // REST ************************************************************************************************************
    // *****************************************************************************************************************

    // Ajax Register or Done
    @RequestMapping(value = "/save/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String register(@PathVariable("id") Long id, @RequestParam("action") String action) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);
        if(action.equals("REGISTER")) {
            document.setSenderRegisteredDate(new Date());
            document = saveOutgoingDocument(document, "REGISTER");
            return document.getSenderRegisteredNumber();
        } else
        {
            saveOutgoingDocument(document, "DONE");
            return "OK";
        }
    }

    // Get Accounts by Type
    // Begin ***********************************************************************************************************
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
    public List<SearchResult> getUsers(@RequestParam String name) {

        List<SearchResult> data = new ArrayList<>();

        for(ChatUser chatUser : chatUserService.getAllByName(name))
        {
            data.add(new SearchResult(chatUser.getId(), chatUser.getName()));
        }
        return data;
    }
    // End *************************************************************************************************************

    // Get Document List
    @RequestMapping("/documents")
    @ResponseBody
    public DataTablesOutput<DocView> getDocuments(@Valid DataTablesInput input, @RequestParam("docType") String docType) {

        long dt = documentTypeService.getByInternalName(docType).getId();
        int count = documentViewDao.count(dt);

        DataTableResult dataTableResult = documentViewDao.list(dt, getUser().getId(), input);

        DataTablesOutput<DocView> docs = new DataTablesOutput<>();

        if(getUser() == null)
            docs.setError("Войдите заново");

        docs.setDraw(input.getDraw());
        docs.setRecordsTotal(count);
        docs.setRecordsFiltered(dataTableResult.getCount());
        docs.setData(dataTableResult.getData());

        return docs;
    }

    // Get Dispatch Data for selected Document
    @RequestMapping("/dispatchData/{id}")
    @ResponseBody
    public List<DispatchData> getDispatchData(@PathVariable("id") long id) {

        List<DispatchData> dispatchDataList = new ArrayList<>();
        dispatchDataList.addAll(documentService.getById(id).getDispatchData());

        return dispatchDataList;
    }

    // Get Tasks for selected Document
    @RequestMapping("/tasks/{id}")
    @ResponseBody
    public List getTasks(@PathVariable("id") long id) {

        String query =
                "select \n" +
                        "DATE_FORMAT(t.createdOn, '%d.%m.%Y') as createdOn, \n" +
                        "COALESCE(s1.name, 'Система') as createdBy, \n" +
                        "t.description as description, \n" +
                        "DATE_FORMAT(t.targetResolutionDate, '%d.%m.%Y') as targetResolutionDate, \n" +
                        "s2.name as assignedTo, \n" +
                        "t.resolutionSummary as resolutionSummary, \n" +
                        "DATE_FORMAT(t.actualResolutionDate, '%d.%m.%Y') as actualResolutionDate \n" +
                        "from task t \n" +
                        "LEFT JOIN users ucb ON t.createdByUserId = ucb.id \n" +
                        "LEFT JOIN staff s1 ON ucb.staff_id = s1.id \n" +
                        "LEFT JOIN users uat ON t.assignedTo = uat.id \n" +
                        "LEFT JOIN staff s2 ON uat.staff_id = s2.id \n" +
                        "where t.objectType = 'Document' and t.objectId = :objectId";

        List<Map<String,Object>> result = entityManager.createNativeQuery(query)
                .setParameter("objectId", id)
                .unwrap(Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .list();

        return result;
    }

    // Get Tasks assigned to Current User
    @RequestMapping("/tasks/all")
    @ResponseBody
    public List getDocumentTasks() {

        String query = "select " +
                "t.objectId as id, " +
                "t.modifiedByUserId as mod " +
                "from Task t " +
                "where (t.assignedToUserId = :userId or t.assignedTo.id = :userId) and t.status = 'OPEN' and t.objectType = 'Document'";

        List<Map<String,Object>> result = entityManager.createQuery(query)
                .setParameter("userId", getUser().getId())
                .unwrap(Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .list();

        return result;
    }

    // Get Tasks created by Current User
    @RequestMapping(value = "/tasks/report/{status}")
    @ResponseBody
    public List getTaskReport(@PathVariable("status") String status) {

        //Staff staff = staffService.findById(getUser().getStaff().getId());
        String query = "select " +
                "t.objectId as objectId, " +
                "DATE_FORMAT(t.createdOn, '%d.%m.%Y') as createdOn, " +
                "t.assignedTo.staff.name as assignedTo, " +
                "t.description as description, " +
                "DATE_FORMAT(t.targetResolutionDate, '%d.%m.%Y') as targetResolutionDate, " +
                "t.resolutionSummary as resolutionSummary, " +
                "DATE_FORMAT(t.actualResolutionDate, '%d.%m.%Y') as actualResolutionDate " +
                "from Task t " +
                "where t.createdBy = :createdBy " +
                "and t.objectType = 'Document' ";

        List<Map<String,Object>> result = entityManager.createQuery(query)
                .setParameter("createdBy", getUser())
                .unwrap(Query.class)
                .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE)
                .list();

        return result;
    }

    // Get Incoming Documents list for ClosedWithID
    @RequestMapping("/incomingdocuments")
    @ResponseBody
    public List getDocs(@RequestParam String name) {

        return documentService.searchOutgoingDocuments(name);
    }

    // Get Attachments for selected Document
    @RequestMapping("/documentattachments/{documentId}")
    @ResponseBody
    public List<Attachment> getDocAttachments(@PathVariable("documentId") long documentId) {

        List<Attachment> data = new ArrayList<>();

        for(Attachment attachment : documentService.getById(documentId).getAttachments())
        {
            data.add(attachment);
        }

        return data;
    }
}