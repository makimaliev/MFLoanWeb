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

        String baseQuery = "SELECT doc.id, doc.name as docName, pck.name as packageName, tp.name as typeName, st.name as stateName,\n" +
                "  doc.documentPackageId as packageId, pck.appliedEntityId as entityId, ent.appliedEntityListId as entityListId,\n" +
                "  list.creditOrderId as orderId\n" +
                "FROM entityDocument doc, documentPackage pck, orderDocumentType tp, entityDocumentState st, appliedEntity ent, appliedEntityList list\n" +
                "WHERE pck.id = doc.documentPackageId\n" +
                "and tp.id = doc.documentTypeId\n" +
                "and st.id = doc.entityDocumentStateId\n" +
                "and ent.id = pck.appliedEntityId\n" +
                "and list.id = ent.appliedEntityListId";

        Query query = entityManager.createNativeQuery(baseQuery, EntityDocumentModel.class);

        List<EntityDocumentModel> docs = query.getResultList();
        return docs;
    }

}
