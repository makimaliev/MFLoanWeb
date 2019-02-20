package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class DocumentMetaModel {

    private Meta meta;
    private List<DocumentModel> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<DocumentModel> getData() {
        return data;
    }

    public void setData(List<DocumentModel> data) {
        this.data = data;
    }
}
