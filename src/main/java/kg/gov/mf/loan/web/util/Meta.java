package kg.gov.mf.loan.web.util;

import java.math.BigInteger;

public class Meta {

    private Integer page;
    private BigInteger pages;
    private Integer perpage;
    private BigInteger total;
    private String sort;
    private String field;

    public Meta(Integer page, BigInteger pages, Integer perpage, BigInteger total, String sort, String field) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.sort = sort;
        this.field = field;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public BigInteger getPages() {
        return pages;
    }

    public void setPages(BigInteger pages) {
        this.pages = pages;
    }

    public Integer getPerpage() {
        return perpage;
    }

    public void setPerpage(Integer perpage) {
        this.perpage = perpage;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
