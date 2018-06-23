package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.DocumentSubType;
import kg.gov.mf.loan.doc.model.DocumentType;
import kg.gov.mf.loan.doc.service.DocumentSubTypeService;
import kg.gov.mf.loan.doc.service.DocumentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/doc/documentSubType")
public class DocumentSubTypeController extends BaseController {

    DocumentTypeService documentTypeService;
    DocumentSubTypeService documentSubTypeService;

    @Autowired
    public DocumentSubTypeController(DocumentTypeService documentTypeService, DocumentSubTypeService documentSubTypeService) {
        this.documentTypeService = documentTypeService;
        this.documentSubTypeService = documentSubTypeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("documentSubTypes", documentSubTypeService.findAll());
        return "/doc/documentSubType/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute("documentSubType", new DocumentSubType());
        model.addAttribute("documentTypes", documentTypeService.findAll());

        return "/doc/documentSubType/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        DocumentSubType documentSubType = documentSubTypeService.findById(id);

        model.addAttribute("documentSubType", documentSubType);
        model.addAttribute("documentTypes", documentTypeService.findAll());

        return "/doc/documentSubType/edit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute("documentSubType") DocumentSubType documentSubType) {
        documentSubTypeService.deleteById(documentSubType);
        return "redirect:/doc/documentSubType";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("documentSubType") DocumentSubType documentSubType) {
        documentSubTypeService.save(documentSubType);
        return "redirect:/doc/documentSubType";
    }

}
