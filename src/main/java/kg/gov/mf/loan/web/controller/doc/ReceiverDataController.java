package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.ReceiverData;
import kg.gov.mf.loan.doc.service.ReceiverDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReceiverDataController {


    @Autowired
    ReceiverDataService receiverDataService;

    @RequestMapping(value= "/doc/receiverdata/add")
    @ResponseBody
    public String SaveOrUpdateReceiverData(@ModelAttribute("receiverData") ReceiverData receiverData)
    {
        if((receiverData.getId() == null) || (receiverData.getId() == 0))
        {
            this.receiverDataService.create(receiverData);
        }
        else
        {
            this.receiverDataService.edit(receiverData);
        }
        return "OK";
    }

    @RequestMapping(value= "/doc/receiverdata/delete")
    @ResponseBody
    public String DeleteReceiverData(@ModelAttribute("receiverData") ReceiverData receiverData)
    {
        this.receiverDataService.deleteById(receiverData);
        return "OK";
    }
}
