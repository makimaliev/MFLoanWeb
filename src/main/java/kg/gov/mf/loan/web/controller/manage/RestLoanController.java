package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestLoanController {

    @Autowired
    LoanRepository loanRepository;

    @GetMapping("/loans")
    public Page<Loan> getAllDebtors(Pageable pageable) {
        return loanRepository.findAll(pageable);
    }

    @GetMapping("/loans/{loanId}")
    public Loan getDebtorById(@PathVariable(value = "loanId") Long loanId) {
        return loanRepository.findOne(loanId);
    }

}
