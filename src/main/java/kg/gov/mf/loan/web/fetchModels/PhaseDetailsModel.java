package kg.gov.mf.loan.web.fetchModels;

import java.util.Date;

public class PhaseDetailsModel {
    private Long id;
    private Long loanId;
    private String loanRegNumber;
    private Long loanStateId;
    private Double startTotalAmount;
    private Double startPrincipal;
    private Double startInterest;
    private Double startPenalty;
    private Double startFee;
    private Double closeTotalAmount;
    private Double closePrincipal;
    private Double closeInterest;
    private Double closePenalty;
    private Double closeFee;
    private Double paidTotalAmount;
    private Double paidPrincipal;
    private Double paidInterest;
    private Double paidPenalty;
    private Double paidFee;
    private Date initDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public String getLoanRegNumber() {
        return loanRegNumber;
    }

    public void setLoanRegNumber(String loanRegNumber) {
        this.loanRegNumber = loanRegNumber;
    }

    public Double getStartTotalAmount() {
        return startTotalAmount;
    }

    public void setStartTotalAmount(Double startTotalAmount) {
        this.startTotalAmount = startTotalAmount;
    }

    public Double getStartPrincipal() {
        return startPrincipal;
    }

    public void setStartPrincipal(Double startPrincipal) {
        this.startPrincipal = startPrincipal;
    }

    public Double getStartInterest() {
        return startInterest;
    }

    public void setStartInterest(Double startInterest) {
        this.startInterest = startInterest;
    }

    public Double getStartPenalty() {
        return startPenalty;
    }

    public void setStartPenalty(Double startPenalty) {
        this.startPenalty = startPenalty;
    }

    public Double getStartFee() {
        return startFee;
    }

    public void setStartFee(Double startFee) {
        this.startFee = startFee;
    }

    public Double getCloseTotalAmount() {
        return closeTotalAmount;
    }

    public void setCloseTotalAmount(Double closeTotalAmount) {
        this.closeTotalAmount = closeTotalAmount;
    }

    public Double getClosePrincipal() {
        return closePrincipal;
    }

    public void setClosePrincipal(Double closePrincipal) {
        this.closePrincipal = closePrincipal;
    }

    public Double getCloseInterest() {
        return closeInterest;
    }

    public void setCloseInterest(Double closeInterest) {
        this.closeInterest = closeInterest;
    }

    public Double getClosePenalty() {
        return closePenalty;
    }

    public void setClosePenalty(Double closePenalty) {
        this.closePenalty = closePenalty;
    }

    public Double getCloseFee() {
        return closeFee;
    }

    public void setCloseFee(Double closeFee) {
        this.closeFee = closeFee;
    }

    public Double getPaidTotalAmount() {
        return paidTotalAmount;
    }

    public void setPaidTotalAmount(Double paidTotalAmount) {
        this.paidTotalAmount = paidTotalAmount;
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

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Long getLoanStateId() {
        return loanStateId;
    }

    public void setLoanStateId(Long loanStateId) {
        this.loanStateId = loanStateId;
    }
}
