package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.gov.mf.loan.manage.model.collection.CollectionPhase;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CollectionPhaseModel implements Comparable<CollectionPhaseModel> {

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date closeDate;

    private long lastEvent;
    private long lastStatusId;

    private long phaseStatusId;
    private String phaseStatusName;

    private long phaseTypeId;
    private String phaseTypeName;

    private long procedureId;

    @Override
    public int compareTo(CollectionPhaseModel model)
    {
        return model.startDate.compareTo(this.startDate);
    }

    public long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(long procedureId) {
        this.procedureId = procedureId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public long getLastEvent() {
        return lastEvent;
    }

    public void setLastEvent(long lastEvent) {
        this.lastEvent = lastEvent;
    }

    public long getLastStatusId() {
        return lastStatusId;
    }

    public void setLastStatusId(long lastStatusId) {
        this.lastStatusId = lastStatusId;
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
}
