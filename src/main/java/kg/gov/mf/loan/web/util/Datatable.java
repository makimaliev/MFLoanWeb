package kg.gov.mf.loan.web.util;

import java.util.List;

public class Datatable<E> {

    private Meta meta;

    List<E> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
}
