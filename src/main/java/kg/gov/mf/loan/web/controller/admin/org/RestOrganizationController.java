package kg.gov.mf.loan.web.controller.admin.org;


import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.repository.OrganizationRepository;
import kg.gov.mf.loan.web.exception.ResourceNotFoundExcption;
import kg.gov.mf.loan.web.fetchModels.ContactListModel;
import kg.gov.mf.loan.web.fetchModels.OrganizationMetaModel;
import kg.gov.mf.loan.web.fetchModels.OrganizationModel;
import kg.gov.mf.loan.web.util.Meta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RestOrganizationController {

    @Autowired
    OrganizationRepository organizationRepository;

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

//    @GetMapping("/organizations/search")
//    public String[] getOrganizationsByName(@RequestParam(value = "s" )String s){
//        List<Organization> organizations=organizationRepository.findByName(s);
//        String[] sOrganizations=new String[organizations.size()];
//        int i=-1;
//        for (Organization organization:organizations){
//            sOrganizations[i++]="["+organization.getId()+"]"+organization.getName();
//        }
//        return sOrganizations;
//    }


    @PostMapping("/organizations")
    public OrganizationMetaModel getOrganizations(@RequestParam Map<String, String> datatable){

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        boolean searchByDistrict= datatable.containsKey("datatable[query][districtId]");
        String districtStr = searchByDistrict?datatable.get("datatable[query][districtId]"):null;

        boolean generalSearch= datatable.containsKey("datatable[query][generalSearch]");
        String searchStr = generalSearch?datatable.get("datatable[query][generalSearch]"):null;




        String baseQuery="SELECT o.id as id,o.name as organizationName, o.description as organizationDescription , o.address_id as addressId," +
                " a.district_id as districtId, d.name as districtName, concat(a.line,' ',i.number,' ', i.date,' ',i.pin) as bigData " +
                "from organization o, address a, district d,identity_doc i " +
                "where a.id=o.address_id " +
                "and d.id=a.district_id " +
                "and i.id=o.identity_doc_id "+
                getDistrictQuery(searchByDistrict,districtStr)+
                getSearchQuery(generalSearch,searchStr)+
                " order by " + sortField + " " + sortStr + " LIMIT " + offset +"," + perPage;

        String countQuery="Select count(1) " +
                "from organization o, address a, district d,identity_doc i " +
                "where a.id=o.address_id " +
                "and d.id=a.district_id " +
                "and i.id=o.identity_doc_id " +
                getDistrictQuery(searchByDistrict,districtStr)+
                getSearchQuery(generalSearch,searchStr);
        BigInteger count = (BigInteger)entityManager.createNativeQuery(countQuery).getResultList().get(0);
        Query query=entityManager.createNativeQuery(baseQuery,OrganizationModel.class);
        List<OrganizationModel> organizations =query.getResultList();
        setContactList(organizations);
        setBankDataOrganization(organizations);
        setStaffName(organizations);
        OrganizationMetaModel organizationMetaModel=new OrganizationMetaModel();
        Meta meta=new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
        organizationMetaModel.setMeta(meta);
        organizationMetaModel.setData(organizations);
        return organizationMetaModel;
    }


    private String getDistrictQuery(boolean searchByDistrict, String districtStr){
        if(searchByDistrict)
            return "AND a.district_id = " + districtStr + " \n";
        else
            return "";
    }

    private String getSearchQuery(boolean generalSearch, String searchStr){
        if(generalSearch)
            return "AND ( a.line like '%"+searchStr+"%'\n" +
                    "      or i.pin  like '%"+searchStr+"%'\n" +
                    "      or i.number like '%"+searchStr+"%'\n" +
                    "      or o.name like '%"+searchStr+"%'\n" +
                    "      or o.description like '%"+searchStr+"%'\n" +
                    "      or d.name like '%"+searchStr+"%') ";
        else
            return "";
    }

    private void setSmallData(String tableName,String column,String tableOrgId,List<OrganizationModel> organizations){
        String baseQuery="select "+tableName+".organization_id as id,"+tableName+"."+column+" as smallData \n" +
                "from "+tableName+", organization o \n" +
                "where "+tableName+"."+tableOrgId+"=o.id ";
        Query query=entityManager.createNativeQuery(baseQuery);
        List <ContactListModel> smallDatas=query.getResultList();
        for(OrganizationModel i :organizations){
            for(ContactListModel j:smallDatas){
                if(i.getId()==j.getId() && j.getSmallData()!=null){
                    i.setBigData(i.getBigData()+ " " + j.getSmallData());
                }
            }
        }
    }
    private void setBankDataOrganization(List<OrganizationModel> organizations){
        String baseQuery="select b.organization_id as id, b.account_number as smallData \n" +
                "from bank_data b, organization o \n" +
                "where b.organization_id=o.id ";
        Query query=entityManager.createNativeQuery(baseQuery);
        List <ContactListModel> bankAccounts=query.getResultList();
        for(OrganizationModel i :organizations){
            for(ContactListModel j:bankAccounts){
                if(i.getId()==j.getId() && j.getSmallData()!=null){
                    i.setBigData(i.getBigData()+ " " + j.getSmallData());
                }
            }
        }
    }
    private void setStaffName(List<OrganizationModel> organizations){

        String baseQuery="select s.organization_id as id, s.name as smallData \n" +
                "from organization o, staff s \n" +
                "where s.organization_id = o.id";
        Query query=entityManager.createNativeQuery(baseQuery,ContactListModel.class);
        List<ContactListModel> staffs=query.getResultList();
//        System.out.println("====================================================================================================");
        for(OrganizationModel i :organizations){
            for(ContactListModel j:staffs){
                if(i.getId()==j.getId() && j.getSmallData()!=null){
//                    System.out.println(j.getSmallData()+":"+i.getOrganizationName());
                    i.setBigData(i.getBigData()+ " " + j.getSmallData());
                }
            }
        }
//        System.out.println("====================================================================================================");

    }
    private void setContactList(List<OrganizationModel> organizations){
        String baseQuery="select o.id as id, c.name as smallData \n" +
                "from organization o, contact c \n" +
                "where o.contact_id = c.id";
        Query query=entityManager.createNativeQuery(baseQuery,ContactListModel.class);
        List<ContactListModel> contacts=query.getResultList();
        for(OrganizationModel i :organizations){
            for(ContactListModel j:contacts){
                if(i.getId()==j.getId() && j.getSmallData()!=null){
                    i.setBigData(i.getBigData()+ " " + j.getSmallData());
                }
            }

        }
    }

    @DeleteMapping("/organizations/{orgId}")
    public ResponseEntity<?> deleteOrganization(@PathVariable(value = "orgId") long orgId){
        Organization organization=organizationRepository.findOne(orgId);
        if(organization==null)
            throw new ResourceNotFoundExcption("OrganizationId "+orgId+" not found");
        organizationRepository.delete(organization);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/organizations/{orgId}")
    public Organization updateOrganization(
            @PathVariable (value = "orgId") long orgId, @Valid @RequestBody Organization orgRequest){
        Organization organization=organizationRepository.findOne(orgId);
        if(organization == null)
            throw new ResourceNotFoundExcption("OrganizationId " + orgId + " not found");

        return organization;
    }

}
