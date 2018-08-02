package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class PersonMetaModel {

    private Meta meta;
    private List<PersonModel> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<PersonModel> getData() {
        return data;
    }

    public void setData(List<PersonModel> data) {
        this.data = data;
    }
}
