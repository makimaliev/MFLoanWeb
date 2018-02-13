package kg.gov.mf.loan.web.controller.process;

import kg.gov.mf.loan.process.model.Job;
import kg.gov.mf.loan.process.service.JobService;
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
    JobService jobService;

    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = { "/job/list" }, method = RequestMethod.GET)
    public String listJobs(ModelMap model) throws SchedulerException
    {
        List<Job> jobs = jobService.list();
        HashMap<Job, Boolean> jobWithStates = new HashMap<>();

        for (Job job : jobs)
        {
            jobWithStates.put(job,isJobActive(new JobKey(job.getName(), job.getName())));
        }

        model.addAttribute("jobWithStates", jobWithStates);

        return "/job/list";
    }

    @RequestMapping(value="/job/{jobId}/save", method=RequestMethod.GET)
    public String formJob(ModelMap model, @PathVariable("jobId")Long jobId)
    {

        if(jobId == 0)
        {
            model.addAttribute("job", new Job());
        }

        if(jobId > 0)
        {
            model.addAttribute("job", jobService.getById(jobId));
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
    public String saveJob(Job job)
    {
        if(job.getId() == 0)
            jobService.add(job);
        else
            jobService.update(job);

        return "redirect:" + "/job/list";
    }

    @RequestMapping(value="/job/startAll")
    public String startAllJobs() throws SchedulerException
    {

        List<Job> jobs = jobService.list();

        Reflections reflections = new Reflections("kg.gov.mf.loan.process.job");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);

        for (Job jobItem: jobs) {
            for (Class<?> o: classes
                    ) {
                if(o.getName().equals(jobItem.getName()) && jobItem.isEnabled())
                {
                    JobDetail jobDetail = context.getBean(JobDetail.class, jobItem.getName(), jobItem.getName(), o);
                    Trigger cronTrigger = context.getBean(Trigger.class, jobItem.getCronExpression(), jobItem.getName());
                    if(!isJobActive(jobDetail)){
                        scheduler.getContext().put("onDate", jobItem.getOnDate());
                        scheduler.scheduleJob(jobDetail, cronTrigger);
                    }

                }
            }

        }

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
        return "redirect:" + "/job/list";
    }

    @RequestMapping(value="/job/delete", method=RequestMethod.POST)
    public String deleteJob(long id) {
        if(id > 0)
            jobService.remove(jobService.getById(id));
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