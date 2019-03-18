package kg.gov.mf.loan.web.controller.task;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.task.model.*;
import kg.gov.mf.loan.task.service.ChatUserService;
import kg.gov.mf.loan.task.service.LoggerService;
import kg.gov.mf.loan.task.service.TaskActionService;
import kg.gov.mf.loan.task.service.TaskService;
import kg.gov.mf.loan.web.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TaskController {

    private final static Map<String, String> operators = new LinkedHashMap<String, String>() {
        {
            put("=", "Равно");
            put("<>", "Не Равно");
            put(">", "Больше");
            put("<", "Меньше");
            put(">=", "Больше или Равно");
            put("<=", "Меньше или Равно");
            put("LIKE", "Like");
            put("NOT LIKE", "NOT Like");

            put("EXISTS", "Exists");
            put("NOT EXISTS", "NOT Exists");

            put("IN", "Содержит");
            put("BETWEEN", "Между");
        }
    };

    private final TaskService taskService;
    private final UserService userService;
    private final LoggerService loggerService;
    private final ChatUserService chatUserService;
    private final TaskActionService taskActionService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, LoggerService loggerService, ChatUserService chatUserService, TaskActionService taskActionService) {
        this.taskService = taskService;
        this.userService = userService;
        this.loggerService = loggerService;
        this.chatUserService = chatUserService;
        this.taskActionService = taskActionService;
    }

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(DATE_FORMAT, true));
    }

    @RequestMapping(value = { "/log" }, method = RequestMethod.GET)
    public String log(Model model) {

        model.addAttribute("logs", loggerService.list());
        return "/task/log";
    }

    @RequestMapping(value = { "/task/edit" }, method = RequestMethod.GET)
    public String edit(Model model) {

        if(getUser() == null) return "/login/login";

        List<ObjectType> objectTypes = taskService.getEntities();

        objectTypes.sort(Comparator.comparing(ObjectType::getObject));

        model.addAttribute("operators", operators);
        model.addAttribute("taskObject", new TaskObject());
        model.addAttribute("objectTypes", objectTypes);

        return "/task/edit";
    }

    @RequestMapping(value = { "/task/add" }, method = RequestMethod.POST)
    public String addTask(@ModelAttribute("taskObject") TaskObject taskObject, Model model) {
        model.addAttribute("taskObject", taskObject);

        return "/task/list";
    }

    @RequestMapping(value = "/task/taskaction/save")
    public String saveTaskAction(@Valid TaskAction taskAction) {
        taskActionService.add(taskAction);
        return "redirect:" + "/task/list";
    }

    @RequestMapping(value = { "/task/list" }, method = RequestMethod.GET)
    public String listTasks(ModelMap model) {
        model.addAttribute("users", chatUserService.list());
        model.addAttribute("task", new Task());
        model.addAttribute("taskAction", new TaskAction());
        model.addAttribute("taskActions", taskActionService.list());
        return "/task/list";
    }

    @RequestMapping(value = { "/task/listByUserId/{userId}" }, method = RequestMethod.GET)
    public String listTasksByUserId(ModelMap model, @PathVariable("userId")Long userId) {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        model.addAttribute("tasks", tasks);
        return "/task/list";
    }

    @RequestMapping(value="/task/{taskId}/save", method=RequestMethod.GET)
    public String formTask(ModelMap model, @PathVariable("taskId")Long taskId) {

        model.addAttribute("users", userService.findAll());
        List<TaskStatus> statuses = Arrays.asList(TaskStatus.values());
        model.addAttribute("statuses",statuses);

        List<TaskPriority> priorities = Arrays.asList(TaskPriority.values());
        model.addAttribute("priorities",priorities);

        if(taskId == 0)
        {
            model.addAttribute("task", new Task());
        }

        if(taskId > 0)
        {
            model.addAttribute("task", taskService.getById(taskId));
        }

        return "/task/save";
    }

    @RequestMapping(value="/task/save", method = RequestMethod.POST)
    public String saveTask(@ModelAttribute("task") Task task) {

        if(task.getId() == 0)
        {
            task.setCreatedOn(new Date());
            task.setCreatedBy(userService.findByUsername(Utils.getPrincipal()));
            task.setModifiedOn(new Date());
            task.setModifiedByUserId(userService.findByUsername(Utils.getPrincipal()).getId());
            taskService.add(task);
        }

        else
        {
            task.setModifiedOn(new Date());
            task.setModifiedByUserId(userService.findByUsername(Utils.getPrincipal()).getId());
            taskService.update(task);
        }

        return "redirect:" + "/task/list";
    }

    @RequestMapping(value="/task/delete", method=RequestMethod.POST)
    public String deleteTask(long id) {
        if(id > 0)
            taskService.remove(taskService.getById(id));
        return "redirect:" + "/task/list";
    }

    private String getPrincipal(){
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    public User getUser() {

        return userService.findByUsername(getPrincipal());
    }

    public List<Task> getUserTasks()
    {
        return taskService.getTasksByUserId(getUser().getId());
    }

    // *****************************************************************************************************************
    // REST ************************************************************************************************************
    // *****************************************************************************************************************

    @RequestMapping(value = "/logs")
    @ResponseBody
    public DataTablesOutput<MFLog> getLogs(@Valid DataTablesInput input) {

        return null;
    }

    @RequestMapping(value = "/tasks")
    @ResponseBody
    public DataTablesOutput<Task> getTasks(@Valid DataTablesInput input) {

        return  taskService.list(getUser().getId(), input);
    }

    @RequestMapping(value = "/task/operators", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getOperators()
    {
        return operators;
    }

    @RequestMapping(value = "/task/taskObject", method = RequestMethod.GET)
    @ResponseBody
    public List getTaskObject(@ModelAttribute("taskObject") TaskObject taskObject) {

        return taskService.queryBuilder(taskObject);
    }

    @RequestMapping(value = "/task/properties")
    @ResponseBody
    public Map<String, Object> getObjectProperties(@RequestParam String name) {

        return new TreeMap<>(taskService.getFields(name));
    }
}
