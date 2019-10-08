package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.output.report.model.SupervisorPlanView;
import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class SupervisorPlanViewMetaModel {

    private Meta meta;

    private List<SupervisorPlanView> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<SupervisorPlanView> getData() {
        return data;
    }

    public void setData(List<SupervisorPlanView> data) {
        this.data = data;
    }
}
