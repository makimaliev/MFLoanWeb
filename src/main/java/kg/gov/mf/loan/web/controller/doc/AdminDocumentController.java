package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.doc.dao.DocViewDao;
import kg.gov.mf.loan.doc.model.DispatchData;
import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.model.State;
import kg.gov.mf.loan.doc.service.*;
import kg.gov.mf.loan.task.model.SystemConstant;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.service.ChatUserService;
import kg.gov.mf.loan.task.service.SystemConstantService;
import kg.gov.mf.loan.task.service.TaskActionService;
import kg.gov.mf.loan.task.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("unchecked")
@Controller
@RequestMapping
public class AdminDocumentController extends DocumentFlowController {

    public AdminDocumentController(DocumentService documentService, DocumentTypeService documentTypeService, DocumentSubTypeService documentSubTypeService, TaskService taskService, CounterService registerService, AccountService accountService, SystemConstantService systemConstantService, ChatUserService chatUserService, DocViewDao documentViewDao, EntityManager entityManager, AttachmentService attachmentService, TaskActionService taskActionService, StaffService staffService, DispatchDataService dispatchDataService) {
        super(documentService, documentTypeService, documentSubTypeService, taskService, registerService, accountService, systemConstantService, chatUserService, documentViewDao, entityManager, attachmentService, taskActionService, staffService, dispatchDataService);
    }

    // Admin Edit Document
    @RequestMapping(value = "/doc/admin/edit/{id}", method = RequestMethod.GET)
    public String adminEdit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);
        document.setUsers(document.getUsers());

        Map<String, Object> vars = new HashMap<>();
        vars.put("objectId", document.getId());
        vars.put("objectType", "Document");
        model.addAttribute("tasks", taskService.getTasks(vars));
        model.addAttribute("taskActions", taskActionService.list());

        model.addAttribute("task", new Task());
        model.addAttribute("dispatch_data", new DispatchData());
        model.addAttribute("states", State.values());
        model.addAttribute("subTypes", document.getDocumentType().getDocumentSubTypes());
        model.addAttribute("uid", getUser().getId());
        model.addAttribute("document", document);

        return "/doc/document/adminedit";
    }

    // Admin Save Document
    @RequestMapping("/doc/admin/save")
    public String adminSave(@Valid Document document) {

        if(getUser() == null) return "/login/login";

        Document doc = documentService.getById(document.getId());

        document.setDispatchData(doc.getDispatchData());

        documentService.update(document);

        return "redirect:/doc?type=" + document.getDocumentType().getInternalName();
    }

    // Admin Delete Document
    @RequestMapping(value = "/doc/admin/delete/{id}", method = RequestMethod.GET)
    public String adminDelete(@PathVariable("id") Long id) {

        if(getUser() == null) return "/login/login";

        Document document = documentService.getById(id);
        documentService.remove(document);

        for(Task task : taskService.getTasksByObjectId(id))
        {
            taskService.remove(task);
        }

        return "redirect:/doc?type=" + document.getDocumentType().getInternalName();
    }

    // Admin Save DispatchData
    @RequestMapping("/doc/dispatchData/save")
    @ResponseBody
    public String dispatchDataSave(@Valid DispatchData dispatchData) {

        if(getUser() == null) return "/login/login";

        dispatchDataService.update(dispatchData);

        return "OK";
    }

    // *****************************************************************************************************************
    @RequestMapping(value = "/doc/constants", method = RequestMethod.GET)
    public String constants(Model model) {

        if(getUser() == null)
            return "/login/login";

        SystemConstant systemConstant = systemConstantService.list().isEmpty()
                ? new SystemConstant()
                : systemConstantService.list().get(0);

        model.addAttribute("systemConstant", systemConstant);

        return "/doc/document/constants";
    }

    @RequestMapping(value = "/doc/task/save", method = RequestMethod.POST)
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

    @RequestMapping(value = "/admin/task/save", method = RequestMethod.POST)
    @ResponseBody
    public String save(@Valid Task task) {

        if(task.getId() == 0) {
            task.setUuid(UUID.randomUUID().toString());
            taskService.add(task);
        } else {
            taskService.update(task);
        }
        return "OK";
    }
}
