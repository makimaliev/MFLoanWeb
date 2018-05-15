package kg.gov.mf.loan.web.controller.manage;

import com.google.common.base.Joiner;
import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.EntitySpecificationsBuilder;
import kg.gov.mf.loan.manage.repository.debtor.DebtorRepository;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.manage.util.SearchOperation;
import kg.gov.mf.loan.web.exception.ResourceNotFoundExcption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.debtor.Debtor;

import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	@GetMapping("/debtors")
	public Page<Debtor> getAllDebtors(Pageable pageable) {
		return debtorRepository.findAll(pageable);
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

		EntitySpecificationsBuilder builder = new EntitySpecificationsBuilder();
		String operationSetExper = Joiner.on("|").join(SearchOperation.SIMPLE_OPERATION_SET);
		Pattern pattern = Pattern.compile(
				"(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
		Matcher matcher = pattern.matcher(q + ",");
		while (matcher.find()) {
			builder.with(
					matcher.group(1),
					matcher.group(2),
					matcher.group(4),
					matcher.group(3),
					matcher.group(5));
		}

		Specification<Debtor> spec = builder.build();
		return debtorRepository.findAll(spec, pageable);
	}

	@PutMapping("/debtors/{debtorId}")
	public Debtor updateDebtor(@PathVariable (value = "debtorId") Long debtorId,
								 @Valid @RequestBody Debtor debtorRequest) {

		Debtor debtor = debtorRepository.findOne(debtorId);
		if(debtor == null)
			throw new ResourceNotFoundExcption("DebtorId " + debtorId + " not found");

		debtor.setDebtorType(debtorRequest.getDebtorType());
		debtor.setName(debtorRequest.getName());
		debtor.setOrganizationForm(debtorRequest.getOrganizationForm());
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
