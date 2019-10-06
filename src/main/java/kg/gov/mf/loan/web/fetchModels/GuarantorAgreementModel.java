package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class GuarantorAgreementModel implements Comparable<GuarantorAgreementModel>{

    @Id
    private long id;

    private int record_status;

    private String notaryOfficeRegNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date notaryOfficeRegDate;

    private String owner;
    private Long ownerId;

    private String notary;

    private String debtor;
    private Long debtorId;
    private String ownerType;
    private Long regionId;
    private Long districtId;
    private String region;
    private String district;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRecord_status() {
        return record_status;
    }

    public void setRecord_status(int record_status) {
        this.record_status = record_status;
    }

    public String getNotaryOfficeRegNumber() {
        return notaryOfficeRegNumber;
    }

    public void setNotaryOfficeRegNumber(String notaryOfficeRegNumber) {
        this.notaryOfficeRegNumber = notaryOfficeRegNumber;
    }

    public Date getNotaryOfficeRegDate() {
        return notaryOfficeRegDate;
    }

    public void setNotaryOfficeRegDate(Date notaryOfficeRegDate) {
        this.notaryOfficeRegDate = notaryOfficeRegDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getNotary() {
        return notary;
    }

    public void setNotary(String notary) {
        this.notary = notary;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public Long getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Long debtorId) {
        this.debtorId = debtorId;
    }

    public String getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public int compareTo(GuarantorAgreementModel guarantorAgreementModel) {
        return guarantorAgreementModel.getNotaryOfficeRegDate().compareTo(this.notaryOfficeRegDate);
    }
}
