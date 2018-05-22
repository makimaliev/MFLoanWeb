package kg.gov.mf.loan.web.controller.manage;

import java.util.List;

public class DataResponse<E> {

    private int draw;

    private int recordsTotal;

    private int recordsFiltered;

    List<List<E>> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<List<E>> getData() {
        return data;
    }

    public void setData(List<List<E>> data) {
        this.data = data;
    }
}
