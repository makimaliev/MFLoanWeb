package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class CurrencyRateMetaModel {
    private Meta meta;
    private List<CurrencyRateModel> data;


    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<CurrencyRateModel> getData() {
        return data;
    }

    public void setData(List<CurrencyRateModel> data) {
        this.data = data;
    }
}
