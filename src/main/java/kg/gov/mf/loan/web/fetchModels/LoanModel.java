package kg.gov.mf.loan.web.fetchModels;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class LoanModel implements Comparable<LoanModel> {

    @Id
    private Long id;

    private Integer loan_class_id;

    private String regNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date regDate;

    private Double amount;
    private long currencyId;
    private String currencyName;
    private long loanTypeId;
    private String loanTypeName;
    private long loanStateId;
    private String loanStateName;
    private long supervisorId;
    private long parentLoanId;
    private long creditOrderId;
    private double remainder;

    @Override
    public int compareTo(LoanModel model)
    {
        return model.regDate.compareTo(this.regDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLoan_class_id() {
        return loan_class_id;
    }

    public void setLoan_class_id(Integer loan_class_id) {
        this.loan_class_id = loan_class_id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public long getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(long loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public String getLoanTypeName() {
        return loanTypeName;
    }

    public void setLoanTypeName(String loanTypeName) {
        this.loanTypeName = loanTypeName;
    }

    public long getLoanStateId() {
        return loanStateId;
    }

    public void setLoanStateId(long loanStateId) {
        this.loanStateId = loanStateId;
    }

    public String getLoanStateName() {
        return loanStateName;
    }

    public void setLoanStateName(String loanStateName) {
        this.loanStateName = loanStateName;
    }

    public long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public long getParentLoanId() {
        return parentLoanId;
    }

    public void setParentLoanId(long parentLoanId) {
        this.parentLoanId = parentLoanId;
    }

    public long getCreditOrderId() {
        return creditOrderId;
    }

    public void setCreditOrderId(long creditOrderId) {
        this.creditOrderId = creditOrderId;
    }

    public double getRemainder() {
        return remainder;
    }

    public void setRemainder(double remainder) {
        this.remainder = remainder;
    }
}
