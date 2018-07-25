package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ReconstructedListModel implements Comparable<ReconstructedListModel>{

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date onDate;

    private long oldLoanId;

    @Override
    public int compareTo(ReconstructedListModel model)
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

    public long getOldLoanId() {
        return oldLoanId;
    }

    public void setOldLoanId(long oldLoanId) {
        this.oldLoanId = oldLoanId;
    }
}
