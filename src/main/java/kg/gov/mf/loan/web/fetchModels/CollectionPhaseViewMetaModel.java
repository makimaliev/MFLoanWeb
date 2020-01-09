package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.output.report.model.CollectionPhaseView;
import kg.gov.mf.loan.web.util.Meta;

import java.util.List;
import java.util.Set;

public class CollectionPhaseViewMetaModel {
    private Meta meta;
    private List<CollectionPhaseView> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<CollectionPhaseView> getData() {
        return data;
    }

    public void setData(List<CollectionPhaseView> data) {
        this.data = data;
    }
}
