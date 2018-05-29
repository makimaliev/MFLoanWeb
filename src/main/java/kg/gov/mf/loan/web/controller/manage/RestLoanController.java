package kg.gov.mf.loan.web.controller.manage;

import com.querydsl.core.types.dsl.BooleanExpression;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.model.loan.QLoan;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RestLoanController {

    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/loans")
    public Page<Loan> getAllDebtors(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }

    @GetMapping("/loans/search")
    public String[] findAllBySpecification(@RequestParam(value = "q") String q) {
        List<Loan> loans = loanRepository.findByRegNumberContains(q);
        String[] sLoans = new String[loans.size()];
        int i = 0;
        for (Loan loan:loans
             ) {
            sLoans[i++] = loan.getRegNumber();
        }

        return sLoans;
    }

    @GetMapping("/loans/{loanId}")
    public Loan getDebtorById(@PathVariable(value = "loanId") Long loanId) {
        return loanRepository.findOne(loanId);
    }

}
