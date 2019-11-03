package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class OrderTermMetaModel {

    private Meta meta;
    private List<OrderTermModel> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<OrderTermModel> getData() {
        return data;
    }

    public void setData(List<OrderTermModel> data) {
        this.data = data;
    }
}
