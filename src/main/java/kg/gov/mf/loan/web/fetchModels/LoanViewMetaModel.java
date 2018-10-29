package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.output.report.model.LoanView;

import kg.gov.mf.loan.web.util.Meta;


import java.util.List;


public class LoanViewMetaModel {

    private Meta meta;
    private List<LoanView> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<LoanView> getData() {
        return data;
    }

    public void setData(List<LoanView> data) {
        this.data = data;
    }
}
