package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class BankruptModel implements Comparable<BankruptModel>{

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date startedOnDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date finishedOnDate;

    @Override
    public int compareTo(BankruptModel model)
    {
        return model.startedOnDate.compareTo(this.startedOnDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartedOnDate() {
        return startedOnDate;
    }

    public void setStartedOnDate(Date startedOnDate) {
        this.startedOnDate = startedOnDate;
    }

    public Date getFinishedOnDate() {
        return finishedOnDate;
    }

    public void setFinishedOnDate(Date finishedOnDate) {
        this.finishedOnDate = finishedOnDate;
    }
}
