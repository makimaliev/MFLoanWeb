package kg.gov.mf.loan.web.controller.task;

import kg.gov.mf.loan.admin.sys.model.User;
import kg.gov.mf.loan.admin.sys.service.UserService;
import kg.gov.mf.loan.task.model.Task;
import kg.gov.mf.loan.task.model.TaskPriority;
import kg.gov.mf.loan.task.model.TaskStatus;
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
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value = { "/task/list" }, method = RequestMethod.GET)
    public String listTasks(ModelMap model)
    {
        List<Task> tasks = taskService.list();
        model.addAttribute("tasks", tasks);
        return "/task/list";
    }

    @RequestMapping(value = { "/tasks" }, method = RequestMethod.GET)
    public String tasks(ModelMap model)
    {
        List<Task> openTasks = taskService.getOpenTasks(userService.findByUsername(Utils.getPrincipal()).getId());
        List<Task> closedTasks = taskService.getClosedTasks(userService.findByUsername(Utils.getPrincipal()).getId());

        model.addAttribute("openTasks", openTasks);
        model.addAttribute("closedTasks", closedTasks);
        return "/task/tasks";
    }

    @RequestMapping(value = { "/task/listByUserId/{userId}" }, method = RequestMethod.GET)
    public String listTasksByUserId(ModelMap model, @PathVariable("userId")Long userId)
    {
        List<Task> tasks = taskService.getTasksByUserId(userId);
        model.addAttribute("tasks", tasks);
        return "/task/list";
    }

    @RequestMapping(value="/task/{taskId}/save", method=RequestMethod.GET)
    public String formTask(ModelMap model, @PathVariable("taskId")Long taskId)
    {

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
    public String saveTask(Task task)
    {
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

    public User getUser()
    {
        String userName = getPrincipal();
        User user = userService.findByUsername(userName);
        return user;
    }

    public List<Task> getUserTasks()
    {
        return taskService.getTasksByUserId(getUser().getId());
    }
}
