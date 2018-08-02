package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Account;
import kg.gov.mf.loan.doc.model.Document;
import kg.gov.mf.loan.doc.service.AccountService;
import kg.gov.mf.loan.doc.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/data")
public class RestdataController extends BaseController {

    class Result {

        public Long id;
        public String text;

        public Result(Long id, String text) {
            this.id = id;
            this.text = text;
        }
    }

    private AccountService accountService;
    private DocumentService documentService;

    @Autowired
    public RestdataController(AccountService accountService, DocumentService documentService) {
        this.accountService = accountService;
        this.documentService = documentService;
    }

    @RequestMapping("/documents")
    @ResponseBody
    public List<Document> documents(@RequestParam String type) {
        List<Document> documents = new ArrayList<>();
        for(Document document : documentService.getInvolvedDocuments(type, getUser().getId())) {
            documents.add(document);
        }
        return documents;
    }

    @RequestMapping("/staff")
    @ResponseBody
    public List<Result> getStaff(@RequestParam String name) {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("staff", name))
        {
            data.add(new Result(account.getId(), account.getName()));
        }

        return data;
    }

    @RequestMapping("/department")
    @ResponseBody
    public List<Result> getDepartment(@RequestParam String name) {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("department", name)) {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/organizations")
    @ResponseBody
    public List<Result> getOrganization(@RequestParam String name) {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("organization", name)) {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/person")
    @ResponseBody
    public List<Result> getPerson(@RequestParam String name) {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("person", name))
        {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }
}
