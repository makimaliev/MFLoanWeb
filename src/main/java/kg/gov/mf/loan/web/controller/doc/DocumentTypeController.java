package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.DocumentType;
import kg.gov.mf.loan.doc.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/doc/documentType")
public class DocumentTypeController extends BaseController {

    DocumentTypeService documentTypeService;

    @Autowired
    public DocumentTypeController(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("documentTypes", documentTypeService.findAll());
        return "/doc/documentType/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("documentType", new DocumentType());
        model.addAttribute("documentTypes", documentTypeService.findAll());

        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        DocumentType documentType = documentTypeService.findById(id);

        model.addAttribute("documentType", documentType);
        model.addAttribute("documentTypes", documentTypeService.findAll());

        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute("documentType") DocumentType documentType) {
        documentTypeService.deleteById(documentType);
        return "redirect:/doc/documentType";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("documentType") DocumentType documentType) {
        documentTypeService.save(documentType);
        return "redirect:/doc/documentType";
    }
}
