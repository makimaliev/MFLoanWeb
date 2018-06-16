package kg.gov.mf.loan.web.controller.manage;

import kg.gov.mf.loan.web.fetchModels.EntityDocumentModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestEntityDocumentController {

    /** The entity manager. */
    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/entityDocuments")
    public List<EntityDocumentModel> getDocuments() {

        String baseQuery = "SELECT doc.id, doc.name as docName,\n" +
                "  pck.name as packageName,\n" +
                "  tp.name as typeName, owner.name as ownerName, owner.id as ownerId,\n" +
                "  address.district_id as districtId,\n" +
                "  district.name as districtName,\n" +
                "  st.id as statusId,\n" +
                "  st.name as statusName,\n" +
                "  CASE\n" +
                "    WHEN st.id = 1 THEN 0\n" +
                "    WHEN st.id = 3 THEN 33\n" +
                "    WHEN st.id = 5 THEN  66\n" +
                "    WHEN st.id = 6 THEN 100\n" +
                "  END as progress,\n" +
                "  doc.documentPackageId as packageId, pck.appliedEntityId as entityId, ent.appliedEntityListId as entityListId,\n" +
                "  list.creditOrderId as orderId\n" +
                "FROM entityDocument doc, documentPackage pck,\n" +
                "  orderDocumentType tp, entityDocumentState st,\n" +
                "  appliedEntity ent, appliedEntityList list,\n" +
                "  owner owner,\n" +
                "  address address,\n" +
                "  district district\n" +
                "WHERE pck.id = doc.documentPackageId\n" +
                "and tp.id = doc.documentTypeId\n" +
                "and st.id = doc.entityDocumentStateId\n" +
                "and ent.id = pck.appliedEntityId\n" +
                "and list.id = ent.appliedEntityListId\n" +
                "and owner.id = ent.ownerId\n" +
                "and owner.addressId = address.id\n" +
                "and district.id = address.district_id";

        Query query = entityManager.createNativeQuery(baseQuery, EntityDocumentModel.class);

        List<EntityDocumentModel> docs = query.getResultList();
        return docs;
    }

}
