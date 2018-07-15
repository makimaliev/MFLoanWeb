package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ItemInspectionResultModel implements Comparable<ItemInspectionResultModel>{

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date onDate;

    private long inspectionResultTypeId;

    private String inspectionResultTypeName;

    private String details;

    @Override
    public int compareTo(ItemInspectionResultModel model)
    {
        return model.onDate.compareTo(this.onDate);
    }

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

    public long getInspectionResultTypeId() {
        return inspectionResultTypeId;
    }

    public void setInspectionResultTypeId(long inspectionResultTypeId) {
        this.inspectionResultTypeId = inspectionResultTypeId;
    }

    public String getInspectionResultTypeName() {
        return inspectionResultTypeName;
    }

    public void setInspectionResultTypeName(String inspectionResultTypeName) {
        this.inspectionResultTypeName = inspectionResultTypeName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
