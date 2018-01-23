package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.model.DocumentType;
import kg.gov.mf.loan.doc.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocumentTypeController {

    @Autowired
    DocumentTypeService documentTypeService;

    @RequestMapping(value = "/doc/documentType")
    public String listDocumentTypes(Model model) {
        model.addAttribute("documentTypes", documentTypeService.findAll());
        return "/doc/documentType/list";
    }

    @RequestMapping(value = "/doc/documentType/save")
    public String saveDocumentType(@ModelAttribute("documentType") DocumentType documentType) {
        if((documentType.getId() == null) || (documentType.getId() == 0))
        {
            this.documentTypeService.create(documentType);
        }
        else
        {
            this.documentTypeService.edit(documentType);
        }
        return "/doc/documentType/list";
    }

    @RequestMapping(value = "/doc/documentType/delete/{id}")
    public String deleteDocumentType(@ModelAttribute("documentType") DocumentType documentType) {
        documentTypeService.deleteById(documentType);
        return "/doc/documentType/list";
    }

    @RequestMapping(value = "/doc/documentType/edit/{id}")
    public String editDocumentType(@ModelAttribute("documentType") DocumentType documentType, Model model) {
        model.addAttribute("documentType", documentTypeService.findById(documentType.getId()));
        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/doc/documentType/new")
    public String newDocumentType(Model model) {
        model.addAttribute("documentType", new DocumentType());
        return "/doc/documentType/edit";
    }
}
