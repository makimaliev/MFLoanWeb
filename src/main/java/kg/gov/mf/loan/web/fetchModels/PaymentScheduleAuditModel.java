package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class PaymentScheduleAuditModel {

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
    private short REVTYPE;

    @Id
    private Long REV;
    private Long REVEND;
    private String staffName;
    private String date;

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

    public short getREVTYPE() {
        return REVTYPE;
    }

    public void setREVTYPE(short REVTYPE) {
        this.REVTYPE = REVTYPE;
    }

    public Long getREV() {
        return REV;
    }

    public void setREV(Long REV) {
        this.REV = REV;
    }

    public Long getREVEND() {
        return REVEND;
    }

    public void setREVEND(Long REVEND) {
        this.REVEND = REVEND;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        final PaymentScheduleAuditModel other = (PaymentScheduleAuditModel) o;
        if (!Objects.equals(this.getREV(), other.getREV())) {
            return false;
        }
        return true;
    }
}
