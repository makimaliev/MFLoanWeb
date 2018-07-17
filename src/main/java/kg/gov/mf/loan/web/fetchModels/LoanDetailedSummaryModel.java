package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class LoanDetailedSummaryModel implements Comparable<LoanDetailedSummaryModel>{

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date onDate;

    private Double disbursement;
    private Double totalDisbursement;
    private Double principalPayment;
    private Double totalPrincipalPayment;
    private Double principalPaid;
    private Double totalPrincipalPaid;
    private Double principalWriteOff;
    private Double totalPrincipalWriteOff;
    private Double principalOutstanding;
    private Double principalOverdue;

    private int daysInPeriod;

    private Double interestAccrued;
    private Double totalInterestAccrued;
    private Double totalInterestAccruedOnInterestPayment;
    private Double interestPayment;
    private Double totalInterestPayment;
    private Double collectedInterestPayment;
    private Double totalCollectedInterestPayment;
    private Double collectedInterestDisbursed;
    private Double interestPaid;
    private Double totalInterestPaid;
    private Double interestOutstanding;
    private Double interestOverdue;
    private Double penaltyAccrued;
    private Double totalPenaltyAccrued;
    private Double collectedPenaltyPayment;
    private Double totalCollectedPenaltyPayment;
    private Double collectedPenaltyDisbursed;
    private Double penaltyPaid;
    private Double totalPenaltyPaid;
    private Double penaltyOutstanding;
    private Double penaltyOverdue;

    @Override
    public int compareTo(LoanDetailedSummaryModel model)
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

    public Double getDisbursement() {
        return disbursement;
    }

    public void setDisbursement(Double disbursement) {
        this.disbursement = disbursement;
    }

    public Double getTotalDisbursement() {
        return totalDisbursement;
    }

    public void setTotalDisbursement(Double totalDisbursement) {
        this.totalDisbursement = totalDisbursement;
    }

    public Double getPrincipalPayment() {
        return principalPayment;
    }

    public void setPrincipalPayment(Double principalPayment) {
        this.principalPayment = principalPayment;
    }

    public Double getTotalPrincipalPayment() {
        return totalPrincipalPayment;
    }

    public void setTotalPrincipalPayment(Double totalPrincipalPayment) {
        this.totalPrincipalPayment = totalPrincipalPayment;
    }

    public Double getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(Double principalPaid) {
        this.principalPaid = principalPaid;
    }

    public Double getTotalPrincipalPaid() {
        return totalPrincipalPaid;
    }

    public void setTotalPrincipalPaid(Double totalPrincipalPaid) {
        this.totalPrincipalPaid = totalPrincipalPaid;
    }

    public Double getPrincipalWriteOff() {
        return principalWriteOff;
    }

    public void setPrincipalWriteOff(Double principalWriteOff) {
        this.principalWriteOff = principalWriteOff;
    }

    public Double getTotalPrincipalWriteOff() {
        return totalPrincipalWriteOff;
    }

    public void setTotalPrincipalWriteOff(Double totalPrincipalWriteOff) {
        this.totalPrincipalWriteOff = totalPrincipalWriteOff;
    }

    public Double getPrincipalOutstanding() {
        return principalOutstanding;
    }

    public void setPrincipalOutstanding(Double principalOutstanding) {
        this.principalOutstanding = principalOutstanding;
    }

    public Double getPrincipalOverdue() {
        return principalOverdue;
    }

    public void setPrincipalOverdue(Double principalOverdue) {
        this.principalOverdue = principalOverdue;
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

    public Double getTotalInterestAccrued() {
        return totalInterestAccrued;
    }

    public void setTotalInterestAccrued(Double totalInterestAccrued) {
        this.totalInterestAccrued = totalInterestAccrued;
    }

    public Double getTotalInterestAccruedOnInterestPayment() {
        return totalInterestAccruedOnInterestPayment;
    }

    public void setTotalInterestAccruedOnInterestPayment(Double totalInterestAccruedOnInterestPayment) {
        this.totalInterestAccruedOnInterestPayment = totalInterestAccruedOnInterestPayment;
    }

    public Double getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(Double interestPayment) {
        this.interestPayment = interestPayment;
    }

    public Double getTotalInterestPayment() {
        return totalInterestPayment;
    }

    public void setTotalInterestPayment(Double totalInterestPayment) {
        this.totalInterestPayment = totalInterestPayment;
    }

    public Double getCollectedInterestPayment() {
        return collectedInterestPayment;
    }

    public void setCollectedInterestPayment(Double collectedInterestPayment) {
        this.collectedInterestPayment = collectedInterestPayment;
    }

    public Double getTotalCollectedInterestPayment() {
        return totalCollectedInterestPayment;
    }

    public void setTotalCollectedInterestPayment(Double totalCollectedInterestPayment) {
        this.totalCollectedInterestPayment = totalCollectedInterestPayment;
    }

    public Double getCollectedInterestDisbursed() {
        return collectedInterestDisbursed;
    }

    public void setCollectedInterestDisbursed(Double collectedInterestDisbursed) {
        this.collectedInterestDisbursed = collectedInterestDisbursed;
    }

    public Double getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(Double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public Double getTotalInterestPaid() {
        return totalInterestPaid;
    }

    public void setTotalInterestPaid(Double totalInterestPaid) {
        this.totalInterestPaid = totalInterestPaid;
    }

    public Double getInterestOutstanding() {
        return interestOutstanding;
    }

    public void setInterestOutstanding(Double interestOutstanding) {
        this.interestOutstanding = interestOutstanding;
    }

    public Double getInterestOverdue() {
        return interestOverdue;
    }

    public void setInterestOverdue(Double interestOverdue) {
        this.interestOverdue = interestOverdue;
    }

    public Double getPenaltyAccrued() {
        return penaltyAccrued;
    }

    public void setPenaltyAccrued(Double penaltyAccrued) {
        this.penaltyAccrued = penaltyAccrued;
    }

    public Double getTotalPenaltyAccrued() {
        return totalPenaltyAccrued;
    }

    public void setTotalPenaltyAccrued(Double totalPenaltyAccrued) {
        this.totalPenaltyAccrued = totalPenaltyAccrued;
    }

    public Double getCollectedPenaltyPayment() {
        return collectedPenaltyPayment;
    }

    public void setCollectedPenaltyPayment(Double collectedPenaltyPayment) {
        this.collectedPenaltyPayment = collectedPenaltyPayment;
    }

    public Double getTotalCollectedPenaltyPayment() {
        return totalCollectedPenaltyPayment;
    }

    public void setTotalCollectedPenaltyPayment(Double totalCollectedPenaltyPayment) {
        this.totalCollectedPenaltyPayment = totalCollectedPenaltyPayment;
    }

    public Double getCollectedPenaltyDisbursed() {
        return collectedPenaltyDisbursed;
    }

    public void setCollectedPenaltyDisbursed(Double collectedPenaltyDisbursed) {
        this.collectedPenaltyDisbursed = collectedPenaltyDisbursed;
    }

    public Double getPenaltyPaid() {
        return penaltyPaid;
    }

    public void setPenaltyPaid(Double penaltyPaid) {
        this.penaltyPaid = penaltyPaid;
    }

    public Double getTotalPenaltyPaid() {
        return totalPenaltyPaid;
    }

    public void setTotalPenaltyPaid(Double totalPenaltyPaid) {
        this.totalPenaltyPaid = totalPenaltyPaid;
    }

    public Double getPenaltyOutstanding() {
        return penaltyOutstanding;
    }

    public void setPenaltyOutstanding(Double penaltyOutstanding) {
        this.penaltyOutstanding = penaltyOutstanding;
    }

    public Double getPenaltyOverdue() {
        return penaltyOverdue;
    }

    public void setPenaltyOverdue(Double penaltyOverdue) {
        this.penaltyOverdue = penaltyOverdue;
    }
}
