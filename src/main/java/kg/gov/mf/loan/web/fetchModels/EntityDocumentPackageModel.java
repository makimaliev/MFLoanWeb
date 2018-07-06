package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class EntityDocumentPackageModel {

    @Id
    private Long id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date completedDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date approvedDate;

    private Double completedRatio;
    private Double approvedRatio;

    private Double registeredRatio;
    private long orderDocumentPackageId;

    private long stateId;
    private String stateName;

    private long typeId;
    private String typeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Double getCompletedRatio() {
        return completedRatio;
    }

    public void setCompletedRatio(Double completedRatio) {
        this.completedRatio = completedRatio;
    }

    public Double getApprovedRatio() {
        return approvedRatio;
    }

    public void setApprovedRatio(Double approvedRatio) {
        this.approvedRatio = approvedRatio;
    }

    public Double getRegisteredRatio() {
        return registeredRatio;
    }

    public void setRegisteredRatio(Double registeredRatio) {
        this.registeredRatio = registeredRatio;
    }

    public long getOrderDocumentPackageId() {
        return orderDocumentPackageId;
    }

    public void setOrderDocumentPackageId(long orderDocumentPackageId) {
        this.orderDocumentPackageId = orderDocumentPackageId;
    }

    public long getStateId() {
        return stateId;
    }

    public void setStateId(long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
