package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CollectionProcedureModel {

    @Id
    private Long id;

    private long procedureStatusId;
    private String procedureStatusName;

    private long phaseId;

    private long phaseTypeId;
    private String phaseTypeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date startDate;

    private Double startTotalAmount;

    private long phaseStatusId;
    private String phaseStatusName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date closeDate;

    private Double closeTotalAmount;

    public long getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(long phaseId) {
        this.phaseId = phaseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getProcedureStatusId() {
        return procedureStatusId;
    }

    public void setProcedureStatusId(long procedureStatusId) {
        this.procedureStatusId = procedureStatusId;
    }

    public String getProcedureStatusName() {
        return procedureStatusName;
    }

    public void setProcedureStatusName(String procedureStatusName) {
        this.procedureStatusName = procedureStatusName;
    }

    public long getPhaseTypeId() {
        return phaseTypeId;
    }

    public void setPhaseTypeId(long phaseTypeId) {
        this.phaseTypeId = phaseTypeId;
    }

    public String getPhaseTypeName() {
        return phaseTypeName;
    }

    public void setPhaseTypeName(String phaseTypeName) {
        this.phaseTypeName = phaseTypeName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getStartTotalAmount() {
        return startTotalAmount;
    }

    public void setStartTotalAmount(Double startTotalAmount) {
        this.startTotalAmount = startTotalAmount;
    }

    public long getPhaseStatusId() {
        return phaseStatusId;
    }

    public void setPhaseStatusId(long phaseStatusId) {
        this.phaseStatusId = phaseStatusId;
    }

    public String getPhaseStatusName() {
        return phaseStatusName;
    }

    public void setPhaseStatusName(String phaseStatusName) {
        this.phaseStatusName = phaseStatusName;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Double getCloseTotalAmount() {
        return closeTotalAmount;
    }

    public void setCloseTotalAmount(Double closeTotalAmount) {
        this.closeTotalAmount = closeTotalAmount;
    }
}
