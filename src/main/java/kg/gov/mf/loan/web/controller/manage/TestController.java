package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.dao.debtor.DebtDao;
import kg.gov.mf.loan.manage.model.debtor.Debtor;
import kg.gov.mf.loan.manage.model.debtor.DebtorType;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.repository.debtor.DebtorRepository;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.service.debtor.DebtorService;
import kg.gov.mf.loan.manage.service.debtor.DebtorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    DebtorRepository debtorRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @GetMapping("/debtors")
    public Page<Debtor> getAllDebtors(Pageable pageable) {
        return debtorRepository.findAll(pageable);
    }

    @GetMapping("/owner/{id}")
    public Owner getOwner(@PathVariable("id") long id) {
        return ownerRepository.findOne(id);
    }

}
