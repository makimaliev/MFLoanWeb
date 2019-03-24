package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class GuarantorMetaModel {
    private Meta meta;
    private List<GuarantorAgreementModel> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<GuarantorAgreementModel> getData() {
        return data;
    }

    public void setData(List<GuarantorAgreementModel> data) {
        this.data = data;
    }
}
