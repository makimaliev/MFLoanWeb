package kg.gov.mf.loan.web.fetchModels;

import java.util.Set;

public class CollectionPhaseMetaModel {

    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private Set<CollectionPhaseModel1> data;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Set<CollectionPhaseModel1> getData() {
        return data;
    }

    public void setData(Set<CollectionPhaseModel1> data) {
        this.data = data;
    }
}
