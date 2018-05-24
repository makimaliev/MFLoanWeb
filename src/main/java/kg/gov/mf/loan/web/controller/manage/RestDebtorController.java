package kg.gov.mf.loan.web.controller.manage;

import com.querydsl.core.types.dsl.BooleanExpression;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.QDebtor;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.debtor.DebtorRepository;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.web.exception.ResourceNotFoundExcption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.debtor.Debtor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class RestDebtorController {

	@Autowired
	DebtorRepository debtorRepository;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	LoanRepository loanRepository;

	@GetMapping("/owners")
	public Page<Owner> getAllOwners(Pageable pageable) {
		return ownerRepository.findAll(pageable);
	}

	@GetMapping("/owners/{ownerId}")
	public Owner getOwnerById(@PathVariable (value = "ownerId") Long ownerId) {
		return ownerRepository.findOne(ownerId);
	}

	@PostMapping("/owners")
	public Owner createOwner(@Valid @RequestBody Owner owner) {
		return ownerRepository.save(owner);
	}

	@PutMapping("/owners/{ownerId}")
	public Owner updateOwner(@PathVariable Long ownerId, @Valid @RequestBody Owner ownerRequest) {
		Owner owner = ownerRepository.findOne(ownerId);
		if(owner == null)
			throw new ResourceNotFoundExcption("OwnerId " + ownerId + " not found");
		owner.setEntityId(ownerRequest.getEntityId());
		owner.setName(ownerRequest.getName());
		owner.setOwnerType(ownerRequest.getOwnerType());

		return ownerRepository.save(owner);
	}

	@DeleteMapping("/owners/{ownerId}")
	public ResponseEntity<?> deletePost(@PathVariable Long ownerId) {
		Owner owner = ownerRepository.findOne(ownerId);
		if(owner == null)
			throw new ResourceNotFoundExcption("OwnerId " + ownerId + " not found");

		ownerRepository.delete(owner);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/owners/{ownerId}/debtors")
	public Page<Debtor> getDebtorsByOwnerId(Pageable pageable, @PathVariable (value = "ownerId") Long ownerId) {
		Owner owner = ownerRepository.findOne(ownerId);
		if(owner == null)
			throw new ResourceNotFoundExcption("OwnerId " + ownerId + " not found");
		return debtorRepository.findByOwner(owner, pageable);

	}

	@PostMapping("/owners/{ownerId}/debtors")
	public Debtor createDebtor(@PathVariable (value = "ownerId") Long ownerId,
							   @Valid @RequestBody Debtor debtor)
	{
		Owner owner = ownerRepository.findOne(ownerId);
		if(owner == null)
			throw new ResourceNotFoundExcption("OwnerId " + ownerId + " not found");

		debtor.setOwner(owner);
		return debtorRepository.save(debtor);
	}

	@PostMapping("/debtors")
		public DataResponse getAllDebtors(@RequestParam("draw") int draw, @RequestParam("start") int start, @RequestParam("length") int length) {

		/*
		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();

		List<Object> vals = new ArrayList<>();
		vals.add("Text 1");
		vals.add(1);
		vals.add(DateUtils.format(today, DateUtils.FORMAT_POSTGRES_DATE));
		vals.add("John Doe");
		vals.add("John Doe");
		vals.add(100.1);
		vals.add(10);
		//vals.add(1);
		vals.add("Text 2");

        DataResponse<Object> dr = new DataResponse<>();
        List<List<Object>> data = new ArrayList<>();
        data.add(vals);
        dr.setData(data);
        dr.setRecordsFiltered(10);
        dr.setRecordsTotal(10);
        dr.setDraw(1);

        return dr;
        */
		long count = debtorRepository.count();
		Pageable pageable = new PageRequest(start/length, length);
		DataResponse<Object> dr = new DataResponse<>();
		List<List<Object>> data = new ArrayList<>();
		List<Debtor> debtors = debtorRepository.findAll(pageable).getContent();
		for (Debtor debtor: debtors
			 ) {
			List<Object> vals = new ArrayList<>();
			vals.add(debtor.getId());
			vals.add(debtor.getName());
			vals.add(debtor.getWorkSector().getName());
			vals.add(debtor.getOwner().getAddress().getDistrict().getName());
			vals.add("<a href=\"javascript:;\" class=\"btn btn-sm btn-outline grey-salsa\"><i class=\"fa fa-search\"></i> View</a>'");
			data.add(vals);
		}

		dr.setData(data);
		dr.setRecordsFiltered(count);
		dr.setRecordsTotal(count);
		dr.setDraw(draw);

		return dr;
	}

	@GetMapping("/debtors/{debtorId}")
	public Debtor getDebtorById(@PathVariable (value = "debtorId") Long debtorId) {
		return debtorRepository.findOne(debtorId);
	}

	@GetMapping("/debtors/{debtorId}/loans")
	public Page<Loan> getAllLoansByDebtorId(@PathVariable (value = "debtorId") Long debtorId,
											Pageable pageable) {
		return loanRepository.findByDebtorId(debtorId, pageable);
	}

	@GetMapping("/debtors/search")
	public Page<Debtor> searchDebtors(Pageable pageable, @RequestParam(value = "q") String q) {

		QDebtor debtor = QDebtor.debtor;
		BooleanExpression hasNameLike = debtor.name.like("%" + q + "%");
		return debtorRepository.findAll(hasNameLike, pageable);
	}

	@PutMapping("/debtors/{debtorId}")
	public Debtor updateDebtor(@PathVariable (value = "debtorId") Long debtorId,
								 @Valid @RequestBody Debtor debtorRequest) {

		Debtor debtor = debtorRepository.findOne(debtorId);
		if(debtor == null)
			throw new ResourceNotFoundExcption("DebtorId " + debtorId + " not found");

		debtor.setDebtorType(debtorRequest.getDebtorType());
		debtor.setName(debtorRequest.getName());
		debtor.setOrgForm(debtorRequest.getOrgForm());
		debtor.setWorkSector(debtorRequest.getWorkSector());

		return debtorRepository.save(debtor);
	}

	@DeleteMapping("/debtors/{debtorId}")
	public ResponseEntity<?> deleteDebtor(@PathVariable (value = "debtorId") Long debtorId) {
		Debtor debtor = debtorRepository.findOne(debtorId);
		if(debtor == null)
			throw new ResourceNotFoundExcption("DebtorId " + debtorId + " not found");

		debtorRepository.delete(debtor);
		return ResponseEntity.ok().build();
	}
}
