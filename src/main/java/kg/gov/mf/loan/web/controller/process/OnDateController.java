package kg.gov.mf.loan.web.controller.process;

import kg.gov.mf.loan.process.model.JobItem;
import kg.gov.mf.loan.process.model.OnDate;
import kg.gov.mf.loan.process.service.JobItemService;
import kg.gov.mf.loan.process.service.OnDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class OnDateController {

    @Autowired
    OnDateService onDateService;

    @Autowired
    JobItemService jobService;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value="/job/{jobId}/ondate/{onDateId}/save", method= RequestMethod.GET)
    public String formOnDate(ModelMap model,
                             @PathVariable("jobId")Long jobId,
                             @PathVariable("onDateId")Long onDateId)
    {

        if(onDateId == 0)
        {
            model.addAttribute("onDate", new OnDate());
        }

        if(onDateId > 0)
        {
            model.addAttribute("onDate", onDateService.getById(onDateId));
        }

        model.addAttribute("jobId", jobId);

        return "/job/ondate/save";
    }

    @RequestMapping(value = { "/job/{jobId}/ondate/save"}, method=RequestMethod.POST)
    public String saveOnDate(OnDate onDate,
                                      @PathVariable("jobId")Long jobId,
                                      ModelMap model)
    {
        JobItem job = jobService.getById(jobId);
        onDate.setJobItem(job);

        if(onDate.getId() == 0)
            onDateService.add(onDate);
        else
            onDateService.update(onDate);

        return "redirect:" + "/job/{jobId}/view";
    }

    @RequestMapping(value="/job/{jobId}/ondate/delete", method=RequestMethod.POST)
    public String deleteOnDate(long id, @PathVariable("jobId")Long jobId) {
        if(id > 0)
            onDateService.remove(onDateService.getById(id));
        return "redirect:" + "/job/{jobId}/view";
    }

}
