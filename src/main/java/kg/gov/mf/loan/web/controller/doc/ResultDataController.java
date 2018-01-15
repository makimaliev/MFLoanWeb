package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.ResultData;
import kg.gov.mf.loan.doc.service.ResultDataService;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ResultDataController {

    @Autowired
    ResultDataService resultDataService;

    @RequestMapping(value= "/doc/resultdata/add")
    @ResponseBody
    public String SaveOrUpdateResultData(@ModelAttribute("resultData") ResultData resultData)
    {
        if((resultData.getId() == null) || (resultData.getId() == 0))
        {
            this.resultDataService.create(resultData);
        }
        else
        {
            this.resultDataService.edit(resultData);
        }
        return "OK";
    }

    @RequestMapping(value= "/doc/resultdata/delete")
    @ResponseBody
    public String DeleteResultData(@ModelAttribute("resultData") ResultData resultData)
    {
        this.resultDataService.deleteById(resultData);
        return "OK";
    }
}
