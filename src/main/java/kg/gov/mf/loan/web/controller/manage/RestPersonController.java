package kg.gov.mf.loan.web.controller.manage;


import kg.gov.mf.loan.admin.org.model.Person;
import kg.gov.mf.loan.admin.org.repository.PersonRepository;
import kg.gov.mf.loan.web.fetchModels.PersonMetaModel;
import kg.gov.mf.loan.web.fetchModels.PersonModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestPersonController {

    @Autowired
    PersonRepository personRepository;

    /** The entity manager. */
    @PersistenceContext
    EntityManager entityManager;


    @GetMapping("/person/search")
    public String[] getPersonByName(@RequestParam(value = "s") String s){
        List<Person> people=personRepository.findByNameContains(s);
        String[] sPeople=new String[people.size()];
        int i=0;
        for(Person person:people){
            sPeople[i++]="["+person.getId()+"]"+person.getName();
        }
        return sPeople;
    }
    @PostMapping("/persons")
    public  PersonMetaModel getPersons(@RequestParam Map<String, String> datatable){

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;


        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        String districtStr = searchByDistrict?datatable.get("datatable[query][districtId]"):null;


        String baseQuery="Select p.id as id, p.name as personName,p.description as personDescription,p.address_id as addressId," +
                " a.district_id as districtId,d.name as districtName " +
                "from person p,address a, district d " +
                "where a.id=p.address_id " +
                "AND a.district_id = d.id " +
                getDistrictQuery(searchByDistrict,districtStr);

        String countQuery="Select count(1)\n" +
                "from person p,address a, district d " +
                "where a.id=p.address_id " +
                "AND a.district_id = d.id " +
                getDistrictQuery(searchByDistrict,districtStr);
        BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);
        PersonMetaModel metaModel= new PersonMetaModel();
        Meta meta=new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);


        Query query=entityManager.createNativeQuery(baseQuery,PersonModel.class);
        List<PersonModel> persons=query.getResultList();

        metaModel.setMeta(meta);
        metaModel.setData(persons);

        return metaModel;
    }
    private String getDistrictQuery(boolean searchByDistrict, String districtStr){
        if(searchByDistrict)
            return "AND a.district_id = " + districtStr + " \n";
        else
            return "";
    }
}
