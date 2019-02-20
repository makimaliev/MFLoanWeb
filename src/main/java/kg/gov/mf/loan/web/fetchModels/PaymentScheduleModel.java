package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class PaymentScheduleModel implements Comparable<PaymentScheduleModel>{

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expectedDate;

    private Double disbursement;

    private Double principalPayment;

    private Double interestPayment;

    private Double collectedInterestPayment;

    private Double collectedPenaltyPayment;

    private long installmentStateId;
    private String installmentStateName;
    private int record_status;
    private int counter;

    @Override
    public int compareTo(PaymentScheduleModel model)
    {
        return this.expectedDate.compareTo(model.expectedDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Double getDisbursement() {
        return disbursement;
    }

    public void setDisbursement(Double disbursement) {
        this.disbursement = disbursement;
    }

    public Double getPrincipalPayment() {
        return principalPayment;
    }

    public void setPrincipalPayment(Double principalPayment) {
        this.principalPayment = principalPayment;
    }

    public Double getInterestPayment() {
        return interestPayment;
    }

    public void setInterestPayment(Double interestPayment) {
        this.interestPayment = interestPayment;
    }

    public Double getCollectedInterestPayment() {
        return collectedInterestPayment;
    }

    public void setCollectedInterestPayment(Double collectedInterestPayment) {
        this.collectedInterestPayment = collectedInterestPayment;
    }

    public Double getCollectedPenaltyPayment() {
        return collectedPenaltyPayment;
    }

    public void setCollectedPenaltyPayment(Double collectedPenaltyPayment) {
        this.collectedPenaltyPayment = collectedPenaltyPayment;
    }

    public long getInstallmentStateId() {
        return installmentStateId;
    }

    public void setInstallmentStateId(long installmentStateId) {
        this.installmentStateId = installmentStateId;
    }

    public String getInstallmentStateName() {
        return installmentStateName;
    }

    public void setInstallmentStateName(String installmentStateName) {
        this.installmentStateName = installmentStateName;
    }

    public int getRecord_status() {
        return record_status;
    }

    public void setRecord_status(int record_status) {
        this.record_status = record_status;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
