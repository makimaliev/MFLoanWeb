package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.DocumentSubType;
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

        if(getUser() == null)
            return "/login/login";

        model.addAttribute("documentSubTypes", documentSubTypeService.list());
        return "/doc/documentSubType/list";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String add(Model model) {

        if(getUser() == null)
            return "/login/login";

        model.addAttribute("documentTypes", documentTypeService.list());
        model.addAttribute("documentSubType", new DocumentSubType());
        return "/doc/documentSubType/edit";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        if(getUser() == null)
            return "/login/login";

        model.addAttribute("documentTypes", documentTypeService.list());
        model.addAttribute("documentSubType", documentSubTypeService.getById(id));
        return "/doc/documentSubType/edit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@ModelAttribute("documentSubType") DocumentSubType documentSubType) {

        if(getUser() == null)
            return "/login/login";

        documentSubTypeService.remove(documentSubType);
        return "redirect:/doc/documentSubType";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("documentSubType") DocumentSubType documentSubType) {

        if(documentSubType.getId() == 0)
        {
            documentSubTypeService.add(documentSubType);
        }
        else
        {
            documentSubTypeService.update(documentSubType);
        }

        return "redirect:/doc/documentSubType";
    }
}
