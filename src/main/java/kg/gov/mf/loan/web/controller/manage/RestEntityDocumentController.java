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

        String baseQuery = "SELECT doc.id,\n" +
                "  doc.name as docName,\n" +
                "  pck.name as packageName,\n" +
                "  tp.name as typeName,\n" +
                "  owner.name as ownerName,\n" +
                "  owner.id as ownerId,\n" +
                "  address.district_id as districtId,\n" +
                "  district.name as districtName,\n" +
                "  st.id as statusId,\n" +
                "  st.name as statusName,\n" +
                "  CASE\n" +
                "  WHEN st.id = 1 THEN 0\n" +
                "  WHEN st.id = 3 THEN 33\n" +
                "  WHEN st.id = 5 THEN  66\n" +
                "  WHEN st.id = 6 THEN 100\n" +
                "  END as progress,\n" +
                "  doc.documentPackageId as packageId,\n" +
                "  pck.appliedEntityId as entityId,\n" +
                "  ent.appliedEntityListId as entityListId,\n" +
                "  list.creditOrderId as orderId,\n" +
                "  cOrder.regNumber as orderNumber\n" +
                "FROM entityDocument doc, documentPackage pck,\n" +
                "  orderDocumentType tp, entityDocumentState st,\n" +
                "  appliedEntity ent, appliedEntityList list,\n" +
                "  owner owner,\n" +
                "  address address,\n" +
                "  district district,\n" +
                "  creditOrder cOrder\n" +
                "WHERE pck.id = doc.documentPackageId\n" +
                "      AND tp.id = doc.documentTypeId\n" +
                "      AND st.id = doc.entityDocumentStateId\n" +
                "      AND ent.id = pck.appliedEntityId\n" +
                "      AND list.id = ent.appliedEntityListId\n" +
                "      AND owner.id = ent.ownerId\n" +
                "      AND owner.addressId = address.id\n" +
                "      AND district.id = address.district_id\n" +
                "      AND cOrder.id = list.creditOrderId";

        Query query = entityManager.createNativeQuery(baseQuery, EntityDocumentModel.class);

        List<EntityDocumentModel> docs = query.getResultList();
        return docs;
    }

}
