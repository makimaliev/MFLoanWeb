package kg.gov.mf.loan.web.controller.process;

import kg.gov.mf.loan.process.model.JobItem;
import kg.gov.mf.loan.process.service.JobItemService;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

@Controller
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    JobItemService jobItemService;

    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = { "/job/list" }, method = RequestMethod.GET)
    public String listJobs(ModelMap model) throws SchedulerException
    {
        List<JobItem> jobs = jobItemService.listByParam("id");
        HashMap<JobItem, Boolean> jobWithStates = new HashMap<>();

        for (JobItem job : jobs)
        {
            jobWithStates.put(job,isJobActive(new JobKey(job.getName(), job.getName())));
        }

        model.addAttribute("jobWithStates", jobWithStates);

        return "/job/list";
    }

    @RequestMapping(value="/job/{jobId}/view")
    public String viewJob(ModelMap model, @PathVariable("jobId")Long jobId)
    {
        JobItem job = jobItemService.getById(jobId);
        model.addAttribute("job", job);
        model.addAttribute("onDates", job.getOnDates());

        return "/job/view";
    }


    @RequestMapping(value="/job/{jobId}/save", method=RequestMethod.GET)
    public String formJob(ModelMap model, @PathVariable("jobId")Long jobId)
    {

        if(jobId == 0)
        {
            model.addAttribute("job", new JobItem());
        }

        if(jobId > 0)
        {
            model.addAttribute("job", jobItemService.getById(jobId));
        }

        Reflections reflections = new Reflections("kg.gov.mf.loan.process.job");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);
        List<String> names = new ArrayList<>();
        for (Class<?> o: classes
             ) {
            names.add(o.getName());
        }

        model.addAttribute("names", names);

        return "/job/save";
    }

    @RequestMapping(value="/job/save", method=RequestMethod.POST)
    public String saveJob(JobItem job)
    {
        if(job.getId() == 0)
            jobItemService.add(job);
        else
            jobItemService.update(job);

        return "redirect:" + "/job/list";
    }

    @RequestMapping(value = "job/{jobId}/start")
    public String startJob(@PathVariable("jobId")long jobId) throws SchedulerException
    {
        if(jobId > 0)
        {
            JobItem jobItem = jobItemService.getById(jobId);
            Reflections reflections = new Reflections("kg.gov.mf.loan.process.job");
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);
            for (Class<?> o: classes)
            {
                if(o.getName().equals(jobItem.getName()) && jobItem.isEnabled() && jobItem.getId() == jobId)
                {
                    JobDetail jobDetail = context.getBean(JobDetail.class, jobItem.getName(), jobItem.getName(), o);
                    Trigger cronTrigger = context.getBean(Trigger.class, jobItem.getCronExpression(), jobItem.getName());
                    if(!isJobActive(jobDetail)){
                        scheduler.getContext().put("jobItem", jobItem);
                        scheduler.scheduleJob(jobDetail, cronTrigger);
                        jobItem.setActive(1);
                        jobItemService.update(jobItem);
                    }
                }
            }
        }

        return "redirect:" + "/job/list";
    }

    @RequestMapping(value="/job/startAll")
    public String startAllJobs() throws SchedulerException
    {
        List<JobItem> jobs = jobItemService.list();

        Reflections reflections = new Reflections("kg.gov.mf.loan.process.job");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);

        for (JobItem jobItem: jobs) {
            for (Class<?> o: classes
                    ) {
                if(o.getName().equals(jobItem.getName()) && jobItem.isEnabled())
                {
                    JobDetail jobDetail = context.getBean(JobDetail.class, jobItem.getName(), jobItem.getName(), o);
                    Trigger cronTrigger = context.getBean(Trigger.class, jobItem.getCronExpression(), jobItem.getName());
                    if(!isJobActive(jobDetail)){
                        scheduler.getContext().put("jobItem", jobItem);
                        scheduler.scheduleJob(jobDetail, cronTrigger);
                        jobItem.setActive(1);
                        jobItemService.update(jobItem);
                    }
                }
            }

        }

        return "redirect:" + "/job/list";
    }

    @RequestMapping(value="job/{jobId}/stop")
    public String stopJob(@PathVariable("jobId")long jobId) throws SchedulerException
    {
        JobItem jobItem = jobItemService.getById(jobId);
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                if(jobKey.getName().equals(jobItem.getName()) && jobItem.getId() == jobId)
                    scheduler.deleteJob(jobKey);
            }
        }
        jobItem.setActive(0);
        jobItemService.update(jobItem);
        return "redirect:" + "/job/list";
    }

    @RequestMapping(value="/job/stopAll")
    public String stopAllJobs() throws SchedulerException
    {
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                scheduler.deleteJob(jobKey);
            }

        }

        List<JobItem> jobItems = jobItemService.list();
        for (JobItem jobItem : jobItems){
            jobItem.setActive(0);
            jobItemService.update(jobItem);
        }
        return "redirect:" + "/job/list";
    }

    @RequestMapping(value="/job/{jobId}/delete", method=RequestMethod.GET)
    public String deleteJob(@PathVariable("jobId")Long jobId) {
        if(jobId > 0)
            jobItemService.remove(jobItemService.getById(jobId));
        return "redirect:" + "/job/list";
    }

    public JobKey getJobKey(String nameOfJob) throws SchedulerException
    {
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                if(nameOfJob.equals(jobKey.getName()));
                    return jobKey;

                //String jobGroup = jobKey.getGroup();
                //get job's trigger
                /*
                List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
                Date nextFireTime = triggers.get(0).getNextFireTime();

                System.out.println("[jobName] : " + jobName + " [groupName] : "
                        + jobGroup + " - " + nextFireTime);
                */
            }

        }
        return null;
    }

    private Boolean isJobActive(JobKey jobKey) throws SchedulerException {

        if(jobKey == null)
            return  false;
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if(jobDetail == null)
            return false;
        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
        for (Trigger trigger : triggers) {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            if (Trigger.TriggerState.NORMAL.equals(triggerState)) {
                return true;
            }
        }
        return false;
    }

    private Boolean isJobActive(JobDetail jobDetail) throws SchedulerException {

        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
        for (Trigger trigger : triggers) {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            if (Trigger.TriggerState.NORMAL.equals(triggerState)) {
                return true;
            }
        }
        return false;
    }
}