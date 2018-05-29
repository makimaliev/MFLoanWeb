package kg.gov.mf.loan.web.util;

public class DebtorV {

    private long id;
    private String name;
    private String dType;
    private String wSector;
    private String district;

    public DebtorV(long id, String name, String dType, String wSector, String district) {
        this.id = id;
        this.name = name;
        this.dType = dType;
        this.wSector = wSector;
        this.district = district;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String getwSector() {
        return wSector;
    }

    public void setwSector(String wSector) {
        this.wSector = wSector;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
