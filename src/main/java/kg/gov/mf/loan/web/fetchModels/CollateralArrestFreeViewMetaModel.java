package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.output.report.model.CollateralArrestFreeView;
import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class CollateralArrestFreeViewMetaModel {
    private Meta meta;
    private List<CollateralArrestFreeView> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<CollateralArrestFreeView> getData() {
        return data;
    }

    public void setData(List<CollateralArrestFreeView> data) {
        this.data = data;
    }
}
