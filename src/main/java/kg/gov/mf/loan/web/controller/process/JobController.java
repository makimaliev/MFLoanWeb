package kg.gov.mf.loan.web.controller.process;

import kg.gov.mf.loan.process.job.OneTimeJob;
import kg.gov.mf.loan.process.model.Job;
import kg.gov.mf.loan.process.service.JobService;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    JobService jobService;

    @Autowired
    private ApplicationContext context;

    @RequestMapping(value = { "/job/list" }, method = RequestMethod.GET)
    public String listJobs(ModelMap model) {

        List<Job> jobs = jobService.list();
        model.addAttribute("jobs", jobs);

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
    public String startAllJobs(RedirectAttributes redirectAttributes) throws SchedulerException
    {

        List<Job> jobs = jobService.list();
        Reflections reflections = new Reflections("kg.gov.mf.loan.process.job");
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Component.class);

        for (Job jobItem: jobs) {
            for (Class<?> o: classes
                    ) {
                if(o.getName().equals(jobItem.getName()))
                {
                    JobDetail jobDetail = context.getBean(JobDetail.class, jobItem.getName(), jobItem.getName(), o);
                    Trigger cronTrigger = context.getBean(Trigger.class, jobItem.getCronExpression(), jobItem.getName());
                    scheduler.scheduleJob(jobDetail, cronTrigger);
                }
            }

        }

        redirectAttributes.addAttribute("started", 1);

        return "redirect:" + "/job/list";
    }

    @RequestMapping(value="/job/delete", method=RequestMethod.POST)
    public String deleteJob(long id) {
        if(id > 0)
            jobService.remove(jobService.getById(id));
        return "redirect:" + "/job/list";
    }
}