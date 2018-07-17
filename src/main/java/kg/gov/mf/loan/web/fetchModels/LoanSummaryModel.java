package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.gov.mf.loan.manage.model.process.LoanSummaryType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class LoanSummaryModel implements Comparable<LoanSummaryModel>{

    @Id
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date onDate;

    private Double loanAmount;
    private Double totalDisbursed;
    private Double totalPaid;
    private Double paidPrincipal;
    private Double paidInterest;
    private Double paidPenalty;
    private Double paidFee;
    private Double totalOutstanding;
    private Double outstadingPrincipal;
    private Double outstadingInterest;
    private Double outstadingPenalty;
    private Double outstadingFee;
    private Double totalOverdue;
    private Double overduePrincipal;
    private Double overdueInterest;
    private Double overduePenalty;
    private Double overdueFee;
    private Double totalPrincipalPaid;
    private Double totalInterestPaid;
    private Double totalPenaltyPaid;
    private Double totalFeePaid;
    private LoanSummaryType loanSummaryType;

    @Override
    public int compareTo(LoanSummaryModel model)
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

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getTotalDisbursed() {
        return totalDisbursed;
    }

    public void setTotalDisbursed(Double totalDisbursed) {
        this.totalDisbursed = totalDisbursed;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double getPaidPrincipal() {
        return paidPrincipal;
    }

    public void setPaidPrincipal(Double paidPrincipal) {
        this.paidPrincipal = paidPrincipal;
    }

    public Double getPaidInterest() {
        return paidInterest;
    }

    public void setPaidInterest(Double paidInterest) {
        this.paidInterest = paidInterest;
    }

    public Double getPaidPenalty() {
        return paidPenalty;
    }

    public void setPaidPenalty(Double paidPenalty) {
        this.paidPenalty = paidPenalty;
    }

    public Double getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(Double paidFee) {
        this.paidFee = paidFee;
    }

    public Double getTotalOutstanding() {
        return totalOutstanding;
    }

    public void setTotalOutstanding(Double totalOutstanding) {
        this.totalOutstanding = totalOutstanding;
    }

    public Double getOutstadingPrincipal() {
        return outstadingPrincipal;
    }

    public void setOutstadingPrincipal(Double outstadingPrincipal) {
        this.outstadingPrincipal = outstadingPrincipal;
    }

    public Double getOutstadingInterest() {
        return outstadingInterest;
    }

    public void setOutstadingInterest(Double outstadingInterest) {
        this.outstadingInterest = outstadingInterest;
    }

    public Double getOutstadingPenalty() {
        return outstadingPenalty;
    }

    public void setOutstadingPenalty(Double outstadingPenalty) {
        this.outstadingPenalty = outstadingPenalty;
    }

    public Double getOutstadingFee() {
        return outstadingFee;
    }

    public void setOutstadingFee(Double outstadingFee) {
        this.outstadingFee = outstadingFee;
    }

    public Double getTotalOverdue() {
        return totalOverdue;
    }

    public void setTotalOverdue(Double totalOverdue) {
        this.totalOverdue = totalOverdue;
    }

    public Double getOverduePrincipal() {
        return overduePrincipal;
    }

    public void setOverduePrincipal(Double overduePrincipal) {
        this.overduePrincipal = overduePrincipal;
    }

    public Double getOverdueInterest() {
        return overdueInterest;
    }

    public void setOverdueInterest(Double overdueInterest) {
        this.overdueInterest = overdueInterest;
    }

    public Double getOverduePenalty() {
        return overduePenalty;
    }

    public void setOverduePenalty(Double overduePenalty) {
        this.overduePenalty = overduePenalty;
    }

    public Double getOverdueFee() {
        return overdueFee;
    }

    public void setOverdueFee(Double overdueFee) {
        this.overdueFee = overdueFee;
    }

    public Double getTotalPrincipalPaid() {
        return totalPrincipalPaid;
    }

    public void setTotalPrincipalPaid(Double totalPrincipalPaid) {
        this.totalPrincipalPaid = totalPrincipalPaid;
    }

    public Double getTotalInterestPaid() {
        return totalInterestPaid;
    }

    public void setTotalInterestPaid(Double totalInterestPaid) {
        this.totalInterestPaid = totalInterestPaid;
    }

    public Double getTotalPenaltyPaid() {
        return totalPenaltyPaid;
    }

    public void setTotalPenaltyPaid(Double totalPenaltyPaid) {
        this.totalPenaltyPaid = totalPenaltyPaid;
    }

    public Double getTotalFeePaid() {
        return totalFeePaid;
    }

    public void setTotalFeePaid(Double totalFeePaid) {
        this.totalFeePaid = totalFeePaid;
    }

    public LoanSummaryType getLoanSummaryType() {
        return loanSummaryType;
    }

    public void setLoanSummaryType(LoanSummaryType loanSummaryType) {
        this.loanSummaryType = loanSummaryType;
    }
}
