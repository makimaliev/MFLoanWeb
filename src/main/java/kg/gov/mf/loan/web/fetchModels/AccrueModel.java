package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class AccrueModel implements Comparable<AccrueModel>{

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date toDate;

    private int daysInPeriod;

    private Double interestAccrued;

    private Double penaltyAccrued;

    private Double penaltyOnPrincipalOverdue;

    private Double penaltyOnInterestOverdue;

    private boolean lastInstPassed;

    @Override
    public int compareTo(AccrueModel model)
    {
        return model.fromDate.compareTo(this.fromDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getDaysInPeriod() {
        return daysInPeriod;
    }

    public void setDaysInPeriod(int daysInPeriod) {
        this.daysInPeriod = daysInPeriod;
    }

    public Double getInterestAccrued() {
        return interestAccrued;
    }

    public void setInterestAccrued(Double interestAccrued) {
        this.interestAccrued = interestAccrued;
    }

    public Double getPenaltyAccrued() {
        return penaltyAccrued;
    }

    public void setPenaltyAccrued(Double penaltyAccrued) {
        this.penaltyAccrued = penaltyAccrued;
    }

    public Double getPenaltyOnPrincipalOverdue() {
        return penaltyOnPrincipalOverdue;
    }

    public void setPenaltyOnPrincipalOverdue(Double penaltyOnPrincipalOverdue) {
        this.penaltyOnPrincipalOverdue = penaltyOnPrincipalOverdue;
    }

    public Double getPenaltyOnInterestOverdue() {
        return penaltyOnInterestOverdue;
    }

    public void setPenaltyOnInterestOverdue(Double penaltyOnInterestOverdue) {
        this.penaltyOnInterestOverdue = penaltyOnInterestOverdue;
    }

    public boolean isLastInstPassed() {
        return lastInstPassed;
    }

    public void setLastInstPassed(boolean lastInstPassed) {
        this.lastInstPassed = lastInstPassed;
    }
}
