package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.output.report.model.EntityDocumentView;
import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class EntityDocumentViewMetaModel {

    private Meta meta;
    private List<EntityDocumentView> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<EntityDocumentView> getData() {
        return data;
    }

    public void setData(List<EntityDocumentView> data) {
        this.data = data;
    }
}
