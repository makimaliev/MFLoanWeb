package kg.gov.mf.loan.web.controller.task;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.task.model.*;
import kg.gov.mf.loan.task.service.LoggerService;
import kg.gov.mf.loan.task.service.TaskService;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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

            put("NOT", "Не");

            put("ALL", "Все");
            put("SOME", "Some");
            put("ANY", "Any");
            put("EXISTS", "Exists");

            put("AND", "И");
            put("OR", "Или");

            put("IN", "Содержит");
            put("BETWEEN", "Между");
        }
    };

    TaskService taskService;
    UserService userService;
    LoggerService loggerService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, LoggerService loggerService) {
        this.taskService = taskService;
        this.userService = userService;
        this.loggerService = loggerService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = { "/log" }, method = RequestMethod.GET)
    public String log(Model model) {

        model.addAttribute("logs", loggerService.list());
        return "/task/log";
    }

    @RequestMapping(value = { "/task/edit" }, method = RequestMethod.GET)
    public String edit(Model model) {

        List<ObjectType> objectTypes = taskService.getEntities();

        Collections.sort(objectTypes, Comparator.comparing(ObjectType::getObject));

        model.addAttribute("operators", operators);
        model.addAttribute("taskObject", new TaskObject());
        model.addAttribute("objectTypes", objectTypes);

        return "/task/edit";
    }

    @RequestMapping(value = { "/task/add" }, method = RequestMethod.POST)
    public String addTask(@ModelAttribute("taskObject") TaskObject taskObject, Model model) {

        List list = taskService.queryBuilder(taskObject);

        model.addAttribute("taskObject", taskObject);

        return "/task/list";
    }

    @RequestMapping(value = { "/task/list" }, method = RequestMethod.GET)
    public String listTasks(ModelMap model) {
        List<Task> tasks = taskService.list();
        model.addAttribute("tasks", tasks);
        return "/task/list";
    }

    @RequestMapping(value = { "/tasks" }, method = RequestMethod.GET)
    public String tasks(ModelMap model) {

        if(getUser() == null) return "/login/login";

        String userId = String.valueOf(userService.findByUsername(Utils.getPrincipal()).getId());
        Map<String, String> vars = new HashMap<>();
        vars.put("assignedTo", userId);
        vars.put("status", "OPEN");

        List<Task> openTasks = taskService.getTasks(vars);

        vars.clear();
        vars.put("assignedTo", userId);
        vars.put("status", "CLOSED");
        List<Task> closedTasks = taskService.getTasks(vars);

        /*
        Map<String, String> atrs = new HashMap<String, String>()
        {
            {
                put("objectId", "131");
                put("status", "open");
            }
        };
        List<Task> tasks = taskService.getTasks(userService.findByUsername(Utils.getPrincipal()).getId(), atrs);
        */

        model.addAttribute("openTasks", openTasks);
        model.addAttribute("closedTasks", closedTasks);
        return "/task/tasks";
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

    @RequestMapping(value="/task/save", method=RequestMethod.POST)
    public String saveTask(Task task) {

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
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    public User getUser() {
        String userName = getPrincipal();
        User user = userService.findByUsername(userName);
        return user;
    }

    public List<Task> getUserTasks()
    {
        return taskService.getTasksByUserId(getUser().getId());
    }


    // *****************************************************************************************************************
    // REST ************************************************************************************************************
    // *****************************************************************************************************************
    @RequestMapping(value = { "/task/operators" }, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getOperators()
    {
        return operators;
    }

    @RequestMapping(value = { "/task/to" }, method = RequestMethod.GET)
    @ResponseBody
    public List<TaskObject> getTaskObject(@ModelAttribute("taskObject") TaskObject taskObject) {
        return taskService.queryBuilder(taskObject);
    }

    @RequestMapping(value = "/task/op")
    @ResponseBody
    public Map<String, Object> getObjectProperties(@RequestParam String name) {
        Map<String, Object> result = new LinkedHashMap<>();

        taskService.getFields(name)
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));

        return result;
    }
}
