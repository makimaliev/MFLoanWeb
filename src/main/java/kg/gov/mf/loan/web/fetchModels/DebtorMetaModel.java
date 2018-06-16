package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class DebtorMetaModel {

    private Meta meta;
    private List<DebtorModel> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<DebtorModel> getData() {
        return data;
    }

    public void setData(List<DebtorModel> data) {
        this.data = data;
    }
}
