package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.SenderData;
import kg.gov.mf.loan.doc.service.SenderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SenderDataController {

    @Autowired
    SenderDataService senderDataService;

    @RequestMapping(value= "/doc/senderdata/add")
    @ResponseBody
    public String SaveOrUpdateSenderData(@ModelAttribute("senderData") SenderData senderData)
    {
        if((senderData.getId() == null) || (senderData.getId() == 0))
        {
            this.senderDataService.add(senderData);
        }
        else
        {
            this.senderDataService.update(senderData);
        }
        return "OK";
    }

    @RequestMapping(value= "/doc/senderdata/delete")
    @ResponseBody
    public String DeleteSenderData(@ModelAttribute("senderData") SenderData senderData)
    {
        this.senderDataService.remove(senderData);
        return "OK";
    }
}
