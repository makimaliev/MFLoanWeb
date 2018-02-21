package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.service.DocumentService;
import kg.gov.mf.loan.doc.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReceiverResponsibleController {

    @Autowired
    DocumentService documentService;

    @Autowired
    DocumentTypeService documentTypeService;

    @RequestMapping(value = "/doc/da", method = RequestMethod.POST)
    public String createDocument(@ModelAttribute("document") Document document) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            documentService.create(document);
        }

        String path = documentTypeService.findById(document.getDocumentType().getId()).getInternalName();
        return "redirect:/doc?type=" + path;
    }
}
