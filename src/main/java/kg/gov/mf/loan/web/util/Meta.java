package kg.gov.mf.loan.web.util;

public class Meta {

    private int page;
    private int pages;
    private int perpage;
    private int total;
    private String sort;
    private String field;

    public Meta(int page, int pages, int perpage, int total, String sort, String field) {
        this.page = page;
        this.pages = pages;
        this.perpage = perpage;
        this.total = total;
        this.sort = sort;
        this.field = field;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPerpage() {
        return perpage;
    }

    public void setPerpage(int perpage) {
        this.perpage = perpage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
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
