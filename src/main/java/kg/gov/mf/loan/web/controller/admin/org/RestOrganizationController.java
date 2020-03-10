package kg.gov.mf.loan.web.controller.admin.org;
import kg.gov.mf.loan.admin.org.model.Organization;
import kg.gov.mf.loan.admin.org.repository.OrganizationRepository;
import kg.gov.mf.loan.web.exception.ResourceNotFoundExcption;
import kg.gov.mf.loan.web.fetchModels.ContactListModel;
import kg.gov.mf.loan.web.fetchModels.OrganizationMetaModel;
import kg.gov.mf.loan.web.fetchModels.OrganizationModel;
import kg.gov.mf.loan.web.util.Meta;
import org.hibernate.jpa.criteria.ValueHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;

import static javax.print.attribute.standard.MediaSizeName.A;

@RestController
@RequestMapping("/api")
public class RestOrganizationController {
    @Autowired
    OrganizationRepository organizationRepository;
    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;



    @PostMapping("/organizations")
    public OrganizationMetaModel getOrganizations(@RequestParam Map<String, String> datatable){

        String pageStr = datatable.get("datatable[pagination][page]");
        String perPageStr = datatable.get("datatable[pagination][perpage]");
        String sortStr = datatable.get("datatable[sort][sort]");
        String sortField = datatable.get("datatable[sort][field]");

        Integer page = Integer.parseInt(pageStr);
        Integer perPage = Integer.parseInt(perPageStr);
        Integer offset = (page-1)*perPage;

        boolean searchBy = datatable.containsKey("datatable[query][generalSearch]");
        String searchStr = searchBy?datatable.get("datatable[query][generalSearch]"):null;

        boolean searchByDistrict = datatable.containsKey("datatable[query][districtId]");
        String districtStr = searchByDistrict?datatable.get("datatable[query][districtId]"):null;


        String baseQuery="SELECT o.id as id,o.name as organizationName, o.description as organizationDescription , o.address_id as addressId," +
                " a.district_id as districtId, d.name as districtName, concat(a.line,' ',i.number,' ', i.date,' ',i.pin) as bigData " +
                "from organization o, address a, district d,identity_doc i " +
                "where a.id=o.address_id " +
                "and d.id=a.district_id " +
                "and i.id=o.identity_doc_id " +
                getDistrictQuery(searchByDistrict,districtStr)+
                " order by " + sortField + " " + sortStr;
        Query query=entityManager.createNativeQuery(baseQuery,OrganizationModel.class);
        List<OrganizationModel> organizations =query.getResultList();

        OrganizationMetaModel metaModel=new OrganizationMetaModel();

        setContactList(organizations);
//        setBankDataOrganization(organizations);
        setStaffName(organizations);
        List<OrganizationModel> organizationModels=new ArrayList<>();
        if(searchBy && searchStr!=null){
            organizationModels=searchFunction(organizations,searchStr);
        }
        else{
            organizationModels=organizations;
        }
        BigInteger count=BigInteger.valueOf(organizationModels.size());
        Meta meta=new Meta(page, count.divide(BigInteger.valueOf(perPage)), perPage, count, sortStr, sortField);
        metaModel.setMeta(meta);
//        organizationModels=sortFunction(organizationModels,sortStr,sortField);
        List<OrganizationModel> smallList=new ArrayList<>();
        for(int i=offset;i<offset+perPage;i++){
            try {
                smallList.add(organizationModels.get(i));
            }
            catch (Exception e){

            }
        }
        metaModel.setData(smallList);
        return metaModel;
    }

    public List<OrganizationModel> sortFunction(List<OrganizationModel> organizations,String sortStr,String sortField){
        int key=0;

        String alphabet="0123456789абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        if(sortField.equals("organizationName")){
            key=1;
            while(key<organizations.size()){
                List<OrganizationModel> smallList=new ArrayList<>();
                for(int i=0;i<key;i++){
                    smallList.add(organizations.get(i));
                }
                while(smallList.size()!=0){
                    String a=smallList.get(smallList.size()/2).getOrganizationName().toLowerCase();
                    a=a.replaceAll("\"","");
                    String b=organizations.get(key).getOrganizationName().toLowerCase();
                    b=b.replaceAll("\"","");
                    int compare=a.compareTo(b);
                    if(compare>0){
                        for(int j=smallList.size()/2;j<smallList.size();j++){
                            smallList.remove(j);
                        }
                        if(smallList.size()==1){
                            if(organizations.indexOf(smallList.get(0))-1==-1){
                                organizations.add(0,organizations.get(key));
                                OrganizationModel o=organizations.get(organizations.indexOf(smallList.get(0)));
                                organizations.remove(o);
                                organizations.add(0,o);
                            }
                            else{
                                organizations.add(organizations.indexOf(smallList.get(0))-1,organizations.get(key));
                            }
                            organizations.remove(key);
                            break;
                        }
                    }
                    else if(compare<0){
                        for(int j=0;j<smallList.size()/2;j++){
                            smallList.remove(j);
                        }
                        if(smallList.size()==1){
                            organizations.add(organizations.indexOf(smallList.get(0)),organizations.get(key));
                            organizations.remove(key);
                            break;
                        }
                    }
                    else{
                        organizations.add(organizations.indexOf(smallList.get(smallList.size()/2)),organizations.get(key));
                        organizations.remove(key);
                        break;
                    }

                }
                key++;
            }
        }

        return organizations;
    }
    private String getDistrictQuery(boolean searchByDistrict, String districtStr){
        if(searchByDistrict)
            return "AND a.district_id = " + districtStr + " \n";
        else
            return "";
    }

    public List<OrganizationModel> searchFunction(List<OrganizationModel> organizations,String searchStr){
        searchStr=searchStr.toLowerCase();
        List<OrganizationModel> smallList=new ArrayList<>();
        for(int i=0;i<organizations.size();i++){
            try {
                if(organizations.get(i).getBigData().toLowerCase().contains(searchStr) || organizations.get(i).getDistrictName().toLowerCase().contains(searchStr)|| organizations.get(i).getOrganizationDescription().toLowerCase().contains(searchStr)|| organizations.get(i).getOrganizationName().toLowerCase().contains(searchStr)){
                    smallList.add(organizations.get(i));
                }
            }
            catch (Exception e){}
        }

        return smallList;
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
        for(OrganizationModel i :organizations){
            for(ContactListModel j:staffs){
                if(i.getId()==j.getId() && j.getSmallData()!=null&& !i.getBigData().contains(j.getSmallData())){
                    i.setBigData(i.getBigData()+ " " + j.getSmallData());
                }
            }
        }
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