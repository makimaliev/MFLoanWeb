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

        if(getUser() == null)
            return "/login/login";

        model.addAttribute("documentTypes", documentTypeService.list());
        return "/doc/documentType/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String add(Model model) {

        if(getUser() == null)
            return "/login/login";

        model.addAttribute("documentType", new DocumentType());
        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null)
            return "/login/login";

        DocumentType documentType = documentTypeService.getById(id);

        model.addAttribute("documentType", documentType);
        return "/doc/documentType/edit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute("documentType") DocumentType documentType) {
        documentTypeService.remove(documentType);
        return "redirect:/doc/documentType";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("documentType") DocumentType documentType) {

        if(documentType.getId() == 0)
        {
            documentTypeService.add(documentType);
        }
        else
        {
            documentTypeService.update(documentType);
        }

        return "redirect:/doc/documentType";
    }
}
