package kg.gov.mf.loan.web.controller.doc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kg.gov.mf.loan.admin.org.model.Department;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.admin.org.service.DepartmentService;
import kg.gov.mf.loan.admin.org.service.OrganizationService;
import kg.gov.mf.loan.admin.org.service.PersonService;
import kg.gov.mf.loan.admin.org.service.StaffService;
import kg.gov.mf.loan.doc.model.*;
import kg.gov.mf.loan.doc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.*;

@Controller
public class DocumentFlowController {

    public class Resposibles
    {
        private Long id;
        private String Name;

        public Resposibles() {
        }

        public Resposibles(Long id, String name) {
            this.id = id;
            Name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }

    @Autowired
    DocumentService documentService;

    @Autowired
    DocumentTypeService documentTypeService;

    @Autowired
    DocumentSubTypeService documentSubTypeService;

    @Autowired
    ResponsibleService responsibleService;

    @Autowired
    ExecutorService executorService;

    @Autowired
    InformationService informationService;

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    PersonService personService;

    @RequestMapping(value = "/doc", params = "type")
    public String listDocuments(@RequestParam("type") String type, Model model) {

        model.addAttribute("document", new Document());
        model.addAttribute("documentType", documentTypeService.findAll());
        model.addAttribute("documentSubType", documentSubTypeService.findAll());
        model.addAttribute("type", type);

        if (type == "internal") {
            model.addAttribute("documents", documentService.internalDocuments());
        } else if (type == "incoming") {
            model.addAttribute("documents", documentService.incomingDocuments());
        } else if (type == "outgoing") {
            model.addAttribute("documents", documentService.outgoingDocuments());
        } else if (type == "archived") {
            model.addAttribute("documents", documentService.archivedDocuments());
        }

        String responsible[] = {"Staff", "Department", "Organization", "Person"};

        model.addAttribute("responsible", responsible);
        model.addAttribute("responsibleList", responsibleList());

        model.addAttribute("staff", staffService.findAll());
        model.addAttribute("department", departmentService.findAll());
        model.addAttribute("organization", organizationService.findAll());
        model.addAttribute("person", personService.findAll());

        model.addAttribute("documentType", documentTypeService.findAll());
        model.addAttribute("documentTypeId", documentTypeService.getIdByInternalname(type));
        model.addAttribute("documentSubType", documentSubTypeService.findAll());
        model.addAttribute("type", type);
        model.addAttribute("user", getPrincipal());
        return "/doc/document/index";
    }

    @RequestMapping(value = "/doc/add", method = RequestMethod.POST)
    @ResponseBody
    public String SaveOrUpdateDocument(@ModelAttribute("document") Document document) {

        if((document.getId() == null) || (document.getId() == 0))
        {
            this.documentService.create(document);
        }
        else
        {
            this.documentService.edit(document);
        }

        return "ok";
    }

    @RequestMapping(value = "/doc/view/{id}")
    public String viewDocument(@PathVariable("id") Long id, Model model)
    {
        model.addAttribute("document", documentService.findById(id));
        return "/doc/document/view";
    }

    @RequestMapping("/doc/remove/{id}")
    @ResponseBody
    public String removeDocument(@ModelAttribute("document") Document document)
    {
        documentService.deleteById(document);
        return "OK";
    }

    @RequestMapping("/doc/{id}")
    @ResponseBody
    public Document getDocumentString(@PathVariable("id") Long id)
    {
        return documentService.findById(id);
    }

    @RequestMapping("/doc/internal")
    @ResponseBody
    public List<Document> internalDocuments()
    {
        return documentService.internalDocuments();
    }

    @RequestMapping("/doc/incoming")
    @ResponseBody
    public List<Document> incomingDocuments()
    {
        return documentService.incomingDocuments();
    }

    @RequestMapping("/doc/outgoing")
    @ResponseBody
    public List<Document> outgoingDocuments()
    {
        return documentService.outgoingDocuments();
    }

    @RequestMapping("/doc/archived")
    @ResponseBody
    public List<Document> archivedDocuments()
    {
        return documentService.archivedDocuments();
    }

    private String getPrincipal(){

        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    private List<Map<Long, String>> responsibleList()
    //private List<List<Resposibles>> responsibleList()
    {

        //region As LIST
        final List<Resposibles> list_staff = new ArrayList<Resposibles>() {
            {
                for(Staff s : staffService.findAll())
                {
                    add(new Resposibles(s.getId(), s.getName()));
                }
            }
        };
        final List<Resposibles> list_department = new ArrayList<Resposibles>() {
            {
                for(Department d : departmentService.findAll())
                {
                    add(new Resposibles(d.getId(), d.getName()));
                }
            }
        };
        final List<Resposibles> list_organization = new ArrayList<Resposibles>() {
            {
                for(Organization o : organizationService.findAll())
                {
                    add(new Resposibles(o.getId(), o.getName()));
                }
            }
        };
        final List<Resposibles> list_person = new ArrayList<Resposibles>() {
            {
                for(Person p : personService.findAll())
                {
                    add(new Resposibles(p.getId(), p.getName()));
                }
            }
        };
        List<List<Resposibles>> list = new ArrayList<List<Resposibles>>() {
            {
                add(list_staff);
                add(list_department);
                add(list_organization);
                add(list_person);
            }
        };
        //endregion

        //region As MAP
        final Map<Long, String> map_staff = new HashMap<Long, String>() {
            {
                for(Staff s : staffService.findAll())
                {
                    put(s.getId(), s.getName());
                }
            }
        };
        final Map<Long, String> map_department = new HashMap<Long, String>() {
            {
                for(Department d : departmentService.findAll())
                {
                    put(d.getId(), d.getName());
                }
            }
        };
        final Map<Long, String> map_organization = new HashMap<Long, String>() {
            {
                for(Organization o : organizationService.findAll())
                {
                    put(o.getId(), o.getName());
                }
            }
        };
        final Map<Long, String> map_person = new HashMap<Long, String>() {
            {
                for(Person p : personService.findAll())
                {
                    put(p.getId(), p.getName());
                }
            }
        };

        List<Map<Long, String>> map = new ArrayList<Map<Long, String>>() {
            {
                add(map_staff);
                add(map_department);
                add(map_organization);
                add(map_person);
            }
        };
        //endregion

        return map;
    }
}
