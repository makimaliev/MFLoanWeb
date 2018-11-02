package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.output.report.model.PaymentScheduleView;
import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class PaymentScheduleViewMetaModel {

    private Meta meta;
    private List<PaymentScheduleView> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<PaymentScheduleView> getData() {
        return data;
    }

    public void setData(List<PaymentScheduleView> data) {
        this.data = data;
    }
}
