package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class LoanSummaryActModel implements Comparable<LoanSummaryActModel> {

    @Id
    private Long id;

    private Date onDate;
    private Date registeredDate;
    private Date signedDate;
    private Double amount;
    private String debtorName;
    private Long debtorId;
    private String reg_number;
    private String districtName;
    private String regionName;
    private Long state;
    private Long supervisorId;
    private String au_created_by;
    private Date au_created_date;
    private String au_last_modified_by;
    private Date au_last_modified_date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public Date getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(Date signedDate) {
        this.signedDate = signedDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public void setDebtorName(String debtorName) {
        this.debtorName = debtorName;
    }

    public Long getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Long debtorId) {
        this.debtorId = debtorId;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getAu_created_by() {
        return au_created_by;
    }

    public void setAu_created_by(String au_created_by) {
        this.au_created_by = au_created_by;
    }

    public Date getAu_created_date() {
        return au_created_date;
    }

    public void setAu_created_date(Date au_created_date) {
        this.au_created_date = au_created_date;
    }

    public String getAu_last_modified_by() {
        return au_last_modified_by;
    }

    public void setAu_last_modified_by(String au_last_modified_by) {
        this.au_last_modified_by = au_last_modified_by;
    }

    public Date getAu_last_modified_date() {
        return au_last_modified_date;
    }

    public void setAu_last_modified_date(Date au_last_modified_date) {
        this.au_last_modified_date = au_last_modified_date;
    }

    @Override
    public int compareTo(LoanSummaryActModel model) {
        if(model.onDate==null || this.onDate==null)
            return 1;
        return model.onDate.compareTo(this.onDate);
    }
}
