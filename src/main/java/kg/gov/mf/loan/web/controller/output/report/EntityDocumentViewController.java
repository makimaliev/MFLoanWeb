package kg.gov.mf.loan.web.controller.output.report;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntityDocumentViewController {



    @RequestMapping(value = {"/entityDocumentView","/entityDocumentView/list"},method = RequestMethod.GET)
    public String listOfEntityDocuments(){
        return "output/report/entityDocumentViewList";
    }
}
