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

@Controller
@RequestMapping("/doc")
public class DocumentSubTypeController extends BaseController {


    //region Dependancies
    DocumentTypeService documentTypeService;
    DocumentSubTypeService documentSubTypeService;

    @Autowired
    public void setDocumentTypeService(DocumentTypeService documentTypeService) {
        this.documentTypeService = documentTypeService;
    }

    @Autowired
    public void setDocumentSubTypeService(DocumentSubTypeService documentSubTypeService) {
        this.documentSubTypeService = documentSubTypeService;
    }
    //endregion

    @RequestMapping(value = "/documentSubType")
    public String listDocumentSubTypes(Model model) {

        model.addAttribute("documentSubTypes", documentSubTypeService.findAll());
        return "/doc/documentSubType/list";
    }

    @RequestMapping(value = "/documentSubType/new")
    public String newDocumentSubType(Model model) {

        model.addAttribute("documentSubType", new DocumentSubType());
        model.addAttribute("documentTypes", documentTypeService.findAll());

        return "/doc/documentSubType/edit";
    }

    @RequestMapping(value = "/documentSubType/edit/{id}")
    public String editDocumentSubType(@PathVariable("id") Long id, Model model) {

        DocumentSubType documentSubType = documentSubTypeService.findById(id);

        model.addAttribute("documentSubType", documentSubType);
        model.addAttribute("documentTypes", documentTypeService.findAll());

        return "/doc/documentSubType/edit";
    }

    @RequestMapping(value = "/documentSubType/delete/{id}")
    public String deleteDocumentSubType(@ModelAttribute("documentSubType") DocumentSubType documentSubType) {
        documentSubTypeService.deleteById(documentSubType);
        return "redirect:/doc/documentSubType";
    }

    @RequestMapping(value = "/documentSubType/save")
    public String saveDocumentSubType(@ModelAttribute("documentSubType") DocumentSubType documentSubType) {
        documentSubTypeService.save(documentSubType);
        return "redirect:/doc/documentSubType";
    }

}
