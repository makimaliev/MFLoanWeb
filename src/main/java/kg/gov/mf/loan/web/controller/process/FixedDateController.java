package kg.gov.mf.loan.web.controller.process;

import kg.gov.mf.loan.process.model.FixedDate;
import kg.gov.mf.loan.process.repository.FixedDateRepository;
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
public class FixedDateController {

    @Autowired
    FixedDateRepository fixedDateRepository;

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("dd.MM.yyyy"), true);
        binder.registerCustomEditor(Date.class, editor);
    }

    @RequestMapping(value="/job/fixeddate/list", method= RequestMethod.GET)
    public String listFixedDates(ModelMap model)
    {
        model.addAttribute("fds", fixedDateRepository.findAll());

        return "/job/fixeddate/list";
    }

    @RequestMapping(value="/job/fixeddate/{fdId}/save", method= RequestMethod.GET)
    public String formFixedDate(ModelMap model,
                             @PathVariable("fdId")Long fdId)
    {

        if(fdId == 0)
        {
            model.addAttribute("fd", new FixedDate());
        }

        if(fdId > 0)
        {
            model.addAttribute("fd", fixedDateRepository.findOne(fdId));
        }

        return "/job/fixeddate/save";
    }

    @RequestMapping(value = { "/job/fixeddate/save"}, method=RequestMethod.POST)
    public String saveOnDate(FixedDate fd)
    {
        fixedDateRepository.save(fd);

        return "redirect:" + "/job/fixeddate/list";
    }

    @RequestMapping(value="/job/fixeddate/{fdId}/delete", method=RequestMethod.POST)
    public String deleteOnDate(@PathVariable("fdId")Long fdId) {
        if(fdId > 0)
            fixedDateRepository.delete(fixedDateRepository.findOne(fdId));
        return "redirect:" + "/job/fixeddate/list";
    }
}
