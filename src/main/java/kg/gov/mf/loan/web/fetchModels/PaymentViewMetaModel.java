package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.output.report.model.PaymentView;
import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class PaymentViewMetaModel {

    private Meta meta;
    private List<PaymentView> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<PaymentView> getData() {
        return data;
    }

    public void setData(List<PaymentView> data) {
        this.data = data;
    }
}
