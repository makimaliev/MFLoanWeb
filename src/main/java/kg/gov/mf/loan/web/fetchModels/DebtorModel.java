package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DebtorModel {

    @Id
    private Long id;
    private String debtorName;
    private long workSectorId;
    private String workSectorName;
    private long ownerId;
    private String ownerName;
    private long districtId;
    private String districtName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public long getWorkSectorId() {
        return workSectorId;
    }

    public void setWorkSectorId(long workSectorId) {
        this.workSectorId = workSectorId;
    }

    public String getWorkSectorName() {
        return workSectorName;
    }

    public void setWorkSectorName(String workSectorName) {
        this.workSectorName = workSectorName;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
