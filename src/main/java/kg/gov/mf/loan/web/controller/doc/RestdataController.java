package kg.gov.mf.loan.web.controller.doc;

import kg.gov.mf.loan.doc.model.Account;
import kg.gov.mf.loan.doc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestdataController {

    class Result {

        public Long id;
        public String text;

        public Result(Long id, String text) {
            this.id = id;
            this.text = text;
        }
    }

    private AccountService accountService;

    @Autowired
    public RestdataController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/data/staff")
    @ResponseBody
    public List<Result> getStaff(@RequestParam String name)
    {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("staff", name)) {
            data.add(new Result(account.getId(), account.getName()));
        }

        return data;
    }

    @RequestMapping("/data/department")
    @ResponseBody
    public List<Result> getDepartment(@RequestParam String name)
    {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("department", name)) {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/data/organizations")
    @ResponseBody
    public List<Result> getOrganization(@RequestParam String name)
    {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("organization", name)) {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }

    @RequestMapping("/data/person")
    @ResponseBody
    public List<Result> getPerson(@RequestParam String name)
    {
        List<Result> data = new ArrayList<>();
        for(Account account : accountService.getByName("person", name)) {
            data.add(new Result(account.getId(), account.getName()));
        }
        return data;
    }
}
