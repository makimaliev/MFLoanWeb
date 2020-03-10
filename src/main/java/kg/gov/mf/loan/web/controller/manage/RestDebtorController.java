package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.manage.model.debtor.Owner;
import kg.gov.mf.loan.manage.model.debtor.OwnerType;
import kg.gov.mf.loan.manage.model.loan.Loan;
import kg.gov.mf.loan.manage.repository.debtor.DebtorRepository;
import kg.gov.mf.loan.manage.repository.debtor.OwnerRepository;
import kg.gov.mf.loan.manage.repository.loan.LoanRepository;
import kg.gov.mf.loan.web.controller.doc.dto.SearchResult;
import kg.gov.mf.loan.web.exception.ResourceNotFoundExcption;
import kg.gov.mf.loan.web.fetchModels.DebtorMetaModel;
import kg.gov.mf.loan.web.fetchModels.DebtorModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kg.gov.mf.loan.manage.model.debtor.Debtor;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class RestDebtorController {

	@Autowired
	DebtorRepository debtorRepository;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	LoanRepository loanRepository;

	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;

	@GetMapping("/owners")
	public Page<Owner> getAllOwners(Pageable pageable) {
		return ownerRepository.findAll(pageable);
	}

	@GetMapping("/owners/search")
	public String[] getOwnersByName(@RequestParam(value = "q") String q) {
		List<Owner> owners = ownerRepository.findByNameContains(q);
		String[] sOwners = new String[owners.size()];
		int i = 0;
		for (Owner owner:owners
				) {
			sOwners[i++] = "[" + owner.getId() + "] "
					+ owner.getName()
					+ " (" + (owner.getOwnerType().equals(OwnerType.ORGANIZATION)? "Организация":"Физ. лицо") +")";
		}

		return sOwners;
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
	public DebtorMetaModel getAllDebtors(@RequestParam Map<String, String> datatable) {

	    String pageStr = datatable.get("datatable[pagination][page]");
	    String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        boolean searchBySector = datatable.containsKey("datatable[query][workSectorId]");
        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        boolean searchByOwner = datatable.containsKey("datatable[query][generalSearch]");

        String workSectorStr = searchBySector?datatable.get("datatable[query][workSectorId]"):null;
        String districtStr = searchByDistrict?datatable.get("datatable[query][districtId]"):null;
        String ownerStr = searchByOwner?datatable.get("datatable[query][generalSearch]"):null;

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

		String baseQuery = "SELECT debtor.id as id, debtor.name as debtorName,\n" +
				"debtor.workSectorId, ws.name as workSectorName, debtor.ownerId, owner.name as ownerName,\n" +
				"address.district_id as districtId, district.name as districtName\n" +
				"FROM debtor debtor, workSector ws,\n" +
				"  owner owner, address address,\n" +
				"  district district\n" +
				"WHERE ws.id = debtor.workSectorId\n" +
				"AND owner.id = debtor.ownerId\n" +
				"AND owner.addressId = address.id\n" +
				"AND address.district_id = district.id " +
                getWorkSectorQuery(searchBySector, workSectorStr) +
                getDistrictQuery(searchByDistrict, districtStr) +
                getOwnerQuery(searchByOwner, ownerStr) +
                "order by " + sortField + " " + sortStr + " LIMIT " + offset +"," + perPage;

		Query query = entityManager.createNativeQuery(baseQuery, DebtorModel.class);

		List<DebtorModel> debtors = query.getResultList();

		String countQuery = "SELECT count(1)\n" +
                "FROM debtor debtor, workSector ws,\n" +
                "  owner owner, address address,\n" +
                "  district district\n" +
                "WHERE ws.id = debtor.workSectorId\n" +
                "AND owner.id = debtor.ownerId\n" +
                "AND owner.addressId = address.id\n" +
                "AND address.district_id = district.id " +
                getWorkSectorQuery(searchBySector, workSectorStr) +
                getDistrictQuery(searchByDistrict, districtStr) +
                getOwnerQuery(searchByOwner, ownerStr);

		BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);

        DebtorMetaModel metaModel = new DebtorMetaModel();
        Meta meta = new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
        metaModel.setMeta(meta);
        metaModel.setData(debtors);
        return metaModel;
	}

	private String getWorkSectorQuery(boolean searchBySector, String workSectorStr){
	    if(searchBySector)
	        return "AND debtor.workSectorId = " + workSectorStr + " \n";
	    else
	        return "";
    }

    private String getDistrictQuery(boolean searchByDistrict, String districtStr){
        if(searchByDistrict)
            return "AND address.district_id = " + districtStr + " \n";
        else
            return "";
    }

    private String getOwnerQuery(boolean searchByOwner, String ownerStr){
        if(searchByOwner)
            return "AND debtor.name like '%" + ownerStr + "%' \n";
        else
            return "";
    }

	/*
	@PostMapping("/debtors")
	public Datatable getAllDebtors(HttpServletRequest request)
	{
		long count = debtorRepository.count();

		Pageable pageable = new PageRequest(0, 10);

		Datatable<DebtorV> result = new Datatable<>();
		List<Debtor> debtors = debtorRepository.findAll(pageable).getContent();
		List<DebtorV> debtorVS = new ArrayList<>();
		for (Debtor debtor: debtors
			 ) {
			debtorVS.add(new DebtorV(debtor.getId(), debtor.getName(), debtor.getDebtorType().getName(), debtor.getWorkSector().getName(), debtor.getOwner().getAddress().getDistrict().getName()));
		}
		result.setMeta(new Meta(1,1, 10, 100, "asc", "id"));
		result.setData(debtorVS);

		return result;

	}
	*/

	@GetMapping("/debtors/{debtorId}")
	public Debtor getDebtorById(@PathVariable (value = "debtorId") Long debtorId) {
		return debtorRepository.findOne(debtorId);
	}

	@GetMapping("/getLoansByDebtor")
	public String[] getAllLoansByDebtorId(@RequestParam ("debtorId") Long debtorId) {
		List<Loan> loans = loanRepository.findByDebtorId(debtorId);
		String[] sLoans = new String[loans.size()];
		int i = 0;
		for (Loan loan:loans
				) {
			sLoans[i++] = "[" + loan.getId() + "] " + loan.getRegNumber();
		}

		return sLoans;
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

	@GetMapping("/debtors/search")
	public List<SearchResult> getDebtorsByName(@RequestParam(value="name",required = false) String name ) {

        List<SearchResult> result = new ArrayList<>();

		String searchQuery="select *\n" +
				"from debtor where name like '%"+name+"%' limit 5";
		Query query=entityManager.createNativeQuery(searchQuery,Debtor.class);
		List<Debtor> debtors= query.getResultList();
		String[] strDebtors= new String[debtors.size()];
		int i = 0;
		for (Debtor debtor:debtors) {
			result.add(new SearchResult(debtor.getId(),debtor.getName()));
		}

		return result;
	}
}
