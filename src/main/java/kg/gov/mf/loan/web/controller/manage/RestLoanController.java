package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.admin.org.model.Staff;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.repository.org.StaffRepository;
import kg.gov.mf.loan.web.controller.doc.dto.SearchResult;
import kg.gov.mf.loan.web.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestLoanController {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    StaffRepository staffRepository;

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping("/loans")
    public Page<Loan> getAllDebtors(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }


    private static HashMap<String,Long> debtorIds=new HashMap<>();

    @PostMapping("/getDebtorId")
    public void setDebtorId(@RequestParam(value = "q") Long q){

        if(debtorIds.size()>=200){
            debtorIds.clear();
        }
        this.debtorIds.put(Utils.getPrincipal(),q);
    }
    @GetMapping("/loans/search")
    public String[] getLoansByRegNumber( @RequestParam(value = "q") String q) {

        List<Loan> loans = loanRepository.findByDebtorIdAndRegNumberContains(debtorIds.get(Utils.getPrincipal()),q);
        String[] sLoans = new String[loans.size()];
        int i = 0;
        for (Loan loan:loans
             ) {
            sLoans[i++] = "[" + loan.getId() + "] " + loan.getRegNumber();
        }

        return sLoans;
    }

    @GetMapping("/staffs/search")
    public String[] getStaffByName(@RequestParam(value = "q") String q) {
//        List<Staff> staffList = staffRepository.findByNameContains(q);
        List<Staff> staffList=staffRepository.findByOrganizationIdAndNameContains(1,q);
        String[] sStaffList = new String[staffList.size()];
        int i = 0;
        for (Staff staff:staffList
                ) {
            sStaffList[i++] = "[" + staff.getId() + "] " + staff.getName();
        }

        return sStaffList;
    }

    @GetMapping("/loans/{loanId}")
    public Loan getDebtorById(@PathVariable(value = "loanId") Long loanId) {
        return loanRepository.findOne(loanId);
    }

    @GetMapping("/search/loans")
    public List<SearchResult> apiGetLoansByRegNumber(@RequestParam(value="name",required = false) String regNumber,@RequestParam(value="debtors")String debtors ) {

        List<SearchResult> result = new ArrayList<>();

        String[] splittedDebtorIds=debtors.split(",");
        String debtorIds="";
        for (String splittedId:splittedDebtorIds){
            if (!splittedId.equals("") ){
                if(debtorIds.equals("")){
                    debtorIds=splittedId;
                }
                else{
                    debtorIds=debtorIds+","+splittedId;
                }
            }
        }

        String searchQuery="select *\n" +
                "from loan where regNumber like '%"+regNumber+"%' and debtorId in ("+debtorIds+") limit 5";
        Query query=entityManager.createNativeQuery(searchQuery,Loan.class);
        List<Loan> loans= query.getResultList();
        for (Loan loan:loans) {
            result.add(new SearchResult(loan.getId(),loan.getRegNumber()));
        }

        return result;
    }

}
