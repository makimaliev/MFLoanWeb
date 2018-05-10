package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.DocumentType;
import kg.gov.mf.loan.doc.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doc")
public class DocumentTypeController extends BaseController {

    @Autowired
    DocumentTypeService documentTypeService;

    @RequestMapping(value = "/documentType")
    public String listdocumentTypes(Model model) {

        model.addAttribute("documentTypes", documentTypeService.findAll());
        return "/doc/documentType/list";
    }

    @RequestMapping(value = "/documentType/new")
    public String newdocumentType(Model model) {
        model.addAttribute("documentType", new DocumentType());
        model.addAttribute("documentTypes", documentTypeService.findAll());

        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/documentType/edit/{id}")
    public String editdocumentType(@PathVariable("id") Long id, Model model) {

        DocumentType documentType = documentTypeService.findById(id);

        model.addAttribute("documentType", documentType);
        model.addAttribute("documentTypes", documentTypeService.findAll());

        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/documentType/delete/{id}")
    public String deletedocumentType(@ModelAttribute("documentType") DocumentType documentType) {
        documentTypeService.deleteById(documentType);
        return "redirect:/doc/documentType";
    }

    @RequestMapping(value = "/documentType/save")
    public String savedocumentType(@ModelAttribute("documentType") DocumentType documentType) {
        documentTypeService.save(documentType);
        return "redirect:/doc/documentType";
    }
}
