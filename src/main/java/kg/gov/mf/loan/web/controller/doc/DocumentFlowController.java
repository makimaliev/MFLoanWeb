package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.StaffService;
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

    //region Dependencies
    private DocumentService documentService;
    private DocumentTypeService documentTypeService;
    private DocumentSubTypeService documentSubTypeService;
    private DocumentStatusService documentStatusService;
    private TaskService taskService;
    private RegisterService registerService;
    private StaffService staffService;

    @Autowired
    public DocumentFlowController(DocumentService documentService,
                                  DocumentTypeService documentTypeService,
                                  DocumentSubTypeService documentSubTypeService,
                                  DocumentStatusService documentStatusService,
                                  TaskService taskService,
                                  RegisterService registerService,
                                  StaffService staffService)
    {
        this.documentService = documentService;
        this.documentTypeService = documentTypeService;
        this.documentSubTypeService = documentSubTypeService;
        this.documentStatusService = documentStatusService;
        this.taskService = taskService;
        this.registerService = registerService;
        this.staffService = staffService;
    }

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
    //region TYPE
    private String[][][] ACTIONS =
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
                    {"send"},
                    {},
                    {"start", "reject"},
                    {"done"}
                },
                {
                    {"create", "request"},
                    {"request"},
                    {"approve", "reject"},
                    {"register"}
                }
            };
    //endregion

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam("type") String type, Model model) {

        if(getUser() == null)
            return "/login/login";

        List<Document> documents = documentService.getInvolvedDocuments(type, getUser().getId());
        DocumentType documentType = documentTypeService.getByInternalName(type);
        String title = documentType.getName();

        List<Task> tasks = taskService.getOpenTasks(getUser().getId());

        String json = "[";
        for(Task task : tasks)
        {
            json += "{'taskId':'" + task.getObjectId() + "'},";
        }
        json += "]";

        model.addAttribute("tasks", tasks);
        model.addAttribute("json", json);
        model.addAttribute("ACTIONS", ACTIONS);
        model.addAttribute("row", CURRENTVIEW.valueOf(type.toUpperCase()).ordinal());
        model.addAttribute("ds", documentStatusService);
        model.addAttribute("documents", documents);
        model.addAttribute("title", title);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentSubTypes", documentType.getDocumentSubTypes());
        model.addAttribute("type", type);

        return "/doc/document/index";
    }

    @RequestMapping(value = "/new/{subType}", method = RequestMethod.GET)
    public String add(@PathVariable("subType") String subType, Model model) {

        if(getUser() == null)
            return "/login/login";

        DocumentType documentType = documentSubTypeService.getByInternalName(subType).getDocumentType();
        String currentView = documentType.getInternalName();

        List<DocumentStatus> actions = new ArrayList<>();
        for(String action : ACTIONS[CURRENTVIEW.valueOf(currentView.toUpperCase()).ordinal()][0])
        {
            actions.add(documentStatusService.getByInternalName(action));
        }

        Document document = new Document();
        document.setOwner(getUser().getId());
        document.setDocumentType(documentType);
        document.setDocumentSubType(documentSubTypeService.getByInternalName(subType));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 10);

        document.setSenderDueDate(cal.getTime());

        if(documentType.getInternalName().equals("incoming"))
        {
            Responsible responsible = new Responsible();
            responsible.setResponsibleType(1);
            responsible.getStaff().add(getUser().getStaff());
            document.setReceiverResponsible(responsible);
        }

        model.addAttribute("actions", actions);
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentState",  document.getDocumentState().toString());

        return "/doc/document/" + currentView + "/" + currentView;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null)
            return "/login/login";

        Document document = documentService.findById(id);
        String currentView = document.getDocumentType().getInternalName();

        int row = CURRENTVIEW.valueOf(currentView.toUpperCase()).ordinal();
        int col = document.getDocumentState().ordinal();

        boolean hasReject = false;

        if(ACTIONS[row][col].length > 1){
            hasReject = ACTIONS[row][col][1].equals("reject") ? true : false;
        }

        List<DocumentStatus> actions = new ArrayList<>();
        for(String action : ACTIONS[row][col])
        {
            actions.add(documentStatusService.getByInternalName(action));
        }

        model.addAttribute("hasReject", hasReject);
        model.addAttribute("actions", actions);
        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);
        model.addAttribute("documentState", document.getDocumentState().toString());

        return "/doc/document/" + currentView + "/" + currentView;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("document") Document document,
                               @RequestParam("action") String action,
                               @RequestParam("senderFiles") MultipartFile[] senderFiles,
                               @RequestParam("receiverFiles") MultipartFile[] receiverFiles) throws IOException {

        String docType = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();

        if(document.getId() != null)
        {
            Document doc = documentService.findById(document.getId());
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

        Document document = documentService.findById(id);
        String currentView = document.getDocumentType().getInternalName();

        model.addAttribute("document", document);
        model.addAttribute("responsible", responsible);

        return "/doc/document/" + currentView + "/view";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {

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

    private DispatchData setDispatchData(State state, String description /*, User toUser */) {

        DispatchData dispatchData = new DispatchData();

        dispatchData.setParent(false);
        dispatchData.setDescription(description);
        dispatchData.setDispatchType(state);
        dispatchData.setDispatchInitTime(new Date());
        dispatchData.setDispatchBy(getUser());
        dispatchData.setDispatchTo(userService.findById(1L));
        //dispatchData.setDispatchResponseTime();

        return dispatchData;
    }
    private Task addTask(Document document, User user) {

        Task task = new Task();
        //task.setDescription("");
        //task.setResolutionSummary("");
        //task.setProgress("");
        task.setSummary(document.getGeneralStatus().getActionName());

        task.setCreatedBy(getUser());
        task.setIdentifiedByUserId(getUser().getId());
        task.setModifiedByUserId(getUser().getId());

        task.setAssignedToUserId(user.getId());
        task.setTargetResolutionDate(document.getSenderDueDate());
        task.setTargetResolutionDate(document.getSenderDueDate());

        /*
        if(document.getDocumentState().ordinal() < 7)
        {
            task.setTargetResolutionDate(document.getSenderDueDate());
        }
        else
        {
            task.setTargetResolutionDate(document.getReceiverDueDate());
        }
        */

        task.setObjectId(document.getId());
        task.setObjectType("/doc/edit/" + document.getId().toString());

        return task;
    }

    private void saveInternalDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        document.setGeneralStatus(documentStatus);

        if(document.getId() == null)
        {
            document.getUsers().add(getUser());
            document.setDocumentState(document.getDocumentState().next(Transition.valueOf(action.toUpperCase())));
            document.setSenderStatus(documentStatus);

            if(action.equals("request"))
            {
                document.getSenderDispatchData().add(setDispatchData(State.DRAFT, ""));
                document.getSenderDispatchData().add(setDispatchData(document.getDocumentState(), ""));
                document = documentService.save(document);

                for(Staff staff : document.getSenderResponsible().getStaff())
                {   // Add Sender Responsible
                    taskService.add(addTask(document, getUser(staff)));
                    document.getUsers().add(getUser(staff));
                }
                documentService.update(document);
            }
            else
            {
                document.getSenderDispatchData().add(setDispatchData(document.getDocumentState(), ""));
                documentService.save(document);
            }
        }
        else
        {
            Document doc = documentService.findById(document.getId());
            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState().next(Transition.valueOf(action.toUpperCase())));
            document.setSenderDispatchData(doc.getSenderDispatchData());

            if(doc.getDocumentState().ordinal() < 3)
            {   // Sender Data
                document.setSenderStatus(documentStatus);

                if(action.equals("reject"))
                {
                    taskService.completeTask(document.getId(), getUser()); // ????????????????????????????????????????????
                    document.getSenderDispatchData().add(setDispatchData(State.REJECTED, document.getComment()));
                }
                else {

                    document.getSenderDispatchData().add(setDispatchData(document.getDocumentState(), ""));

                    if (action.equals("request"))
                    {   // Add Sender Responsible
                        for (Staff staff : document.getSenderResponsible().getStaff())
                        {   // Add task and Users
                            taskService.add(addTask(document, getUser(staff)));
                            document.getUsers().add(getUser(staff));
                        }
                    }
                    if (action.equals("approve"))
                    {
                        taskService.completeTask(document.getId(), getUser());

                        document.setSenderRegisteredNumber(registerService.generateRegistrationNumber());
                        document.setSenderRegisteredDate(new Date());

                        document.getSenderDispatchData().add(setDispatchData(State.REGISTERED, "Исходящий № : "
                                + document.getSenderRegisteredNumber() + "<br> Дата : "
                                + DATE_FORMAT.format(document.getSenderRegisteredDate()))
                        );
                        // Add Task and Receiver Responsible
                        if (document.getReceiverResponsible().getResponsibleType() == 1) {
                            for (Staff staff : document.getReceiverResponsible().getStaff()) {
                                taskService.add(addTask(document, getUser(staff)));
                                document.getUsers().add(getUser(staff));
                            }
                        } else {
                            for (Department department : document.getReceiverResponsible().getDepartments()) {
                                taskService.add(addTask(document, getUser(department)));
                                document.getUsers().add(getUser(department));
                            }
                        }
                        //endregion
                    }
                }

            }
            else
            {   // Receiver Data
                document.setReceiverDispatchData(doc.getReceiverDispatchData());                                      // add existing Receiver DispatchData
                document.setReceiverStatus(documentStatus);                                                           // update Receiver DocumentStatus
                document.getReceiverDispatchData().add(setDispatchData(document.getDocumentState(), ""));  // add new Receiver DispatchData

                if(action.equals("accept"))
                {
                    taskService.completeTask(document.getId(), getUser());

                    document.setReceiverRegisteredNumber(registerService.generateRegistrationNumber());
                    document.setReceiverRegisteredDate(new Date());

                    document.getReceiverDispatchData().add(setDispatchData(State.REGISTERED, "Входящий № : "
                            + document.getReceiverRegisteredNumber() + "<br> Дата : "
                            + DATE_FORMAT.format(document.getReceiverRegisteredDate()))
                    );
                    //region Add task
                    if(document.getReceiverResponsible().getResponsibleType() == 1)
                    {
                        for(Staff staff : document.getReceiverResponsible().getStaff()) {
                            taskService.add(addTask(document, getUser(staff)));
                        }
                    }
                    else
                    {
                        for(Department department : document.getReceiverResponsible().getDepartments()) {
                            taskService.add(addTask(document, getUser(department)));
                        }
                    }
                    //endregion
                }

                if(action.equals("send"))
                {   // Add Receiver Executer
                    taskService.completeTask(document.getId(), getUser());
                    for(Staff staff : document.getReceiverExecutor().getStaff()) {
                        taskService.add(addTask(document, getUser(staff)));
                        document.getUsers().add(getUser(staff));
                    }
                }

                if(action.equals("start"))
                {
                    taskService.completeTask(document.getId(), getUser());
                    for(Staff staff : document.getReceiverExecutor().getStaff()) {
                        taskService.add(addTask(document, getUser(staff)));
                    }
                }

                if(action.equals("done"))
                {
                    taskService.completeTask(document.getId(), getUser());
                }

                if(action.equals("reject"))
                {
                    taskService.completeTask(document.getId(), getUser());
                    document.getReceiverDispatchData().add(setDispatchData(State.REJECTED, document.getComment()));
                }
            }
            documentService.update(document);
        }
    }
    private void saveIncomingDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        document.setGeneralStatus(documentStatus);

        if(document.getId() == null)
        {
            document.getUsers().add(getUser());
            document.setDocumentState(document.getDocumentState().next(Transition.valueOf(action.toUpperCase())));

            if(action.equals("register"))
            {
                document.getReceiverDispatchData().add(setDispatchData(State.DRAFT, ""));
                document.getReceiverDispatchData().add(setDispatchData(State.REGISTERED, "Входящий № : "
                        + document.getReceiverRegisteredNumber() + "<br> Дата : "
                        + DATE_FORMAT.format(document.getReceiverRegisteredDate()))
                );
                document = documentService.save(document);
                //region Add task and Users
                taskService.add(addTask(document, getUser()));
                //endregion
                documentService.update(document);
            }
            else
            {
                document.getReceiverDispatchData().add(setDispatchData(document.getDocumentState(), ""));
                documentService.save(document);
            }
        }
        else
        {
            Document doc = documentService.findById(document.getId());
            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState().next(Transition.valueOf(action.toUpperCase())));
            document.setReceiverDispatchData(doc.getReceiverDispatchData());                                      // add existing Receiver DispatchData
            document.setReceiverStatus(documentStatus);                                                           // update Receiver DocumentStatus

            if(action.equals("reject"))
            {
                taskService.completeTask(document.getId(), getUser());
                document.getReceiverDispatchData().add(setDispatchData(State.REJECTED, document.getComment()));
            }
            else
            {
                if (action.equals("register"))
                {
                    document.getReceiverDispatchData().add(setDispatchData(State.REGISTERED, "Входящий № : "
                            + document.getReceiverRegisteredNumber() + "<br> Дата : "
                            + DATE_FORMAT.format(document.getReceiverRegisteredDate()))
                    );
                    //region Add task and Users
                    taskService.add(addTask(document, getUser()));
                    //endregion
                }
                else {
                    document.getReceiverDispatchData().add(setDispatchData(document.getDocumentState(), ""));  // add new Receiver DispatchData
                }
            }

            if(action.equals("send"))
            {
                taskService.completeTask(document.getId(), getUser());
                for(Staff staff : document.getReceiverExecutor().getStaff()) {
                    taskService.add(addTask(document, getUser(staff)));
                    document.getUsers().add(getUser(staff));
                }
            }

            if(action.equals("start"))
            {
                taskService.completeTask(document.getId(), getUser());
                taskService.add(addTask(document, getUser()));
            }

            if(action.equals("done"))
            {
                taskService.completeTask(document.getId(), getUser());
            }

            documentService.update(document);
        }
    }
    private void saveOutgoingDocument(Document document, String action) {

        DocumentStatus documentStatus = documentStatusService.getByInternalName(action);
        document.setGeneralStatus(documentStatus);

        if(document.getId() == null)
        {
            document.getUsers().add(getUser());
            document.setDocumentState(document.getDocumentState().next(Transition.valueOf(action.toUpperCase())));
            document.setSenderStatus(documentStatus);

            if(action.equals("request"))
            {
                document.getSenderDispatchData().add(setDispatchData(State.DRAFT, ""));
                document = documentService.save(document);
                //region Add task and Users
                if (document.getSenderResponsible().getResponsibleType() == 1) {
                    for (Staff staff : document.getReceiverResponsible().getStaff()) {
                        taskService.add(addTask(document, getUser(staff)));
                        document.getUsers().add(getUser(staff));
                    }
                } else {
                    // Static user
                    User user = getUser(); //userService.findById(8);
                    taskService.add(addTask(document, user));
                    document.getUsers().add(user);
                }
                //endregion
                documentService.update(document);
            }
            else
            {
                document.getSenderDispatchData().add(setDispatchData(document.getDocumentState(), ""));
                documentService.save(document);
            }
        }
        else
        {
            Document doc = documentService.findById(document.getId());
            document.setUsers(doc.getUsers());
            document.setDocumentState(doc.getDocumentState().next(Transition.valueOf(action.toUpperCase())));
            document.setSenderDispatchData(doc.getSenderDispatchData());

            document.setSenderStatus(documentStatus);

            if(action.equals("reject"))
            {
                taskService.completeTask(document.getId(), getUser());
                document.getSenderDispatchData().add(setDispatchData(State.REJECTED, document.getComment()));
            }
            else
            {
                document.getSenderDispatchData().add(setDispatchData(document.getDocumentState(), ""));

                if (action.equals("request")) {
                    //region Add task and Users
                    if (document.getSenderResponsible().getResponsibleType() == 1) {
                        for (Staff staff : document.getReceiverResponsible().getStaff()) {
                            taskService.add(addTask(document, getUser(staff)));
                            document.getUsers().add(getUser(staff));
                        }
                    } else {
                        // Static user
                        User user = userService.findById(8);
                        taskService.add(addTask(document, user));
                        document.getUsers().add(user);
                    }
                    //endregion
                }
                if (action.equals("approve")) {
                    taskService.completeTask(document.getId(), getUser());

                    document.setSenderRegisteredNumber(registerService.generateRegistrationNumber());
                    document.setSenderRegisteredDate(new Date());

                    document.getSenderDispatchData().add(setDispatchData(State.REGISTERED, "Исходящий № : "
                            + document.getSenderRegisteredNumber() + "<br> Дата : "
                            + DATE_FORMAT.format(document.getSenderRegisteredDate()))
                    );

                    document.getSenderDispatchData().add(setDispatchData(State.DONE, ""));

                    //region Add task and Users
                    if (document.getReceiverResponsible().getResponsibleType() == 1) {
                        for (Staff staff : document.getReceiverResponsible().getStaff()) {
                            taskService.add(addTask(document, getUser(staff)));
                            document.getUsers().add(getUser(staff));
                        }
                    } else {
                        for (Department department : document.getReceiverResponsible().getDepartments()) {
                            //taskService.add(addTask(document, department));
                            document.getUsers().add(getUser(department));
                        }
                    }
                    //endregion
                    document.setGeneralStatus(documentStatusService.getByInternalName("done"));
                    document.setDocumentState(State.DONE);
                }
            }
            documentService.update(document);
        }
    }
}