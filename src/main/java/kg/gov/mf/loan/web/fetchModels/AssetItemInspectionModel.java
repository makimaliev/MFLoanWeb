package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class AssetItemInspectionModel {

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date onDate;

    private String inspectionResultTypeName;
    private Long assetItemId;
    private String details;

    //region GETTERS-SETTERS

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

    public String getInspectionResultTypeName() {
        return inspectionResultTypeName;
    }

    public void setInspectionResultTypeName(String inspectionResultTypeName) {
        this.inspectionResultTypeName = inspectionResultTypeName;
    }

    public Long getAssetItemId() {
        return assetItemId;
    }

    public void setAssetItemId(Long assetItemId) {
        this.assetItemId = assetItemId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    //endregion

}
