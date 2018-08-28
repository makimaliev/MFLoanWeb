package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class FloatingRateMetaModel {
    private Meta meta;
    private List<FloatingRateModel> data;


    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<FloatingRateModel> getData() {
        return data;
    }

    public void setData(List<FloatingRateModel> data) {
        this.data = data;
    }
}
