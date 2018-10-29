package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class OrganizationMetaModel {
    private Meta meta;
    private List<OrganizationModel> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<OrganizationModel> getData() {
        return data;
    }

    public void setData(List<OrganizationModel> data) {
        this.data = data;
    }
}
