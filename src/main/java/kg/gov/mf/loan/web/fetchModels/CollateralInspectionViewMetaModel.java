package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.output.report.model.CollateralInspectionView;
import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class CollateralInspectionViewMetaModel {

    private Meta meta;
    private List<CollateralInspectionView> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<CollateralInspectionView> getData() {
        return data;
    }

    public void setData(List<CollateralInspectionView> data) {
        this.data = data;
    }
}
