package kg.gov.mf.loan.web.controller.manage;

import java.util.List;

public class DataResponse<E> {

    private int draw;

    private long recordsTotal;

    private long recordsFiltered;

    List<List<E>> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<List<E>> getData() {
        return data;
    }

    public void setData(List<List<E>> data) {
        this.data = data;
    }
}
