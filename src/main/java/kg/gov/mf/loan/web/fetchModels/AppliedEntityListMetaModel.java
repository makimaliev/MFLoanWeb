package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class AppliedEntityListMetaModel {

    private Meta meta;
    private List<AppliedEntityModel> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<AppliedEntityModel> getData() {
        return data;
    }

    public void setData(List<AppliedEntityModel> data) {
        this.data = data;
    }
}
