package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class LoanSummaryActMetaModel {
    private Meta meta;
    private List<LoanSummaryActModel> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<LoanSummaryActModel> getData() {
        return data;
    }

    public void setData(List<LoanSummaryActModel> data) {
        this.data = data;
    }
}
