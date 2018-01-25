package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.model.DocumentSubType;
import kg.gov.mf.loan.doc.service.DocumentSubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DocumentSubTypeController {

    @Autowired
    DocumentSubTypeService documentSubTypeService;

    @RequestMapping(value = "/doc/documentSubType")
    public String listDocumentSubTypes(Model model) {

        model.addAttribute("documentSubTypes", documentSubTypeService.findAll());
        return "/doc/documentSubType/list";
    }

    @RequestMapping(value = "/doc/documentSubType/new")
    public String newDocumentSubType(Model model) {
        model.addAttribute("documentSubType", new DocumentSubType());
        model.addAttribute("documentSubTypes", documentSubTypeService.findAll());

        return "/doc/documentSubType/edit";
    }

    @RequestMapping(value = "/doc/documentSubType/edit/{id}")
    public String editDocumentSubType(@PathVariable("id") Long id, Model model) {

        DocumentSubType documentSubType = documentSubTypeService.findById(id);

        model.addAttribute("documentSubType", documentSubType);
        model.addAttribute("documentSubTypes", documentSubTypeService.findAll());

        return "/doc/documentSubType/edit";
    }

    @RequestMapping(value = "/doc/documentSubType/delete/{id}")
    public String deleteDocumentSubType(@ModelAttribute("documentSubType") DocumentSubType documentSubType) {
        documentSubTypeService.deleteById(documentSubType);
        return "redirect:/doc/documentSubType";
    }

    @RequestMapping(value = "/doc/documentSubType/save")
    public String saveDocumentSubType(@ModelAttribute("documentSubType") DocumentSubType documentSubType) {
        documentSubTypeService.edit(documentSubType);
        return "redirect:/doc/documentSubType";
    }

}