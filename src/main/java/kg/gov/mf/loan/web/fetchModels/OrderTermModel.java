package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class OrderTermModel {
    @Id
    private Long id;

    private String description;
    private Double amount;
    private int installmentQuantity;
    private int installmentFirstDay;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date firstInstallmentDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date lastInstallmentDate;

    private int minDaysDisbFirstInst;
    private int maxDaysDisbFirstInst;
    private int graceOnPrinciplePaymentInst;
    private int graceOnPrinciplePaymentDays;
    private int graceOnInterestPaymentInst;
    private int graceOnInterestPaymentDays;
    private int graceOnInterestAccrInst;
    private int graceOnInterestAccrDays;
    private Double interestRateValue;
    private int frequencyQuantity;
    private Double penaltyOnPrincipleOverdueRateValue;

    private long fundId;
    private String fundName;

    private Double penaltyOnInterestOverdueRateValue;
    private boolean earlyRepaymentAllowed;
    private Double penaltyLimitPercent;
    private boolean collateralFree;

    private long currencyId;
    private String currencyName;

    private long frequencyTypeId;
    private String frequencyTypeName;

    private long interestRateValuePerPeriodId;
    private String interestRateValuePerPeriodName;

    private long interestTypeId;
    private String interestTypeName;

    private long penaltyOnPrincipleOverdueTypeId;
    private String penaltyOnPrincipleOverdueTypeName;

    private long penaltyOnInterestOverdueTypeId;
    private String penaltyOnInterestOverdueTypeName;

    private long daysInYearMethodId;
    private String daysInYearMethodName;

    private long daysInMonthMethodId;
    private String daysInMonthMethodName;

    private long transactionOrderId;
    private String transactionOrderName;

    private long interestAccrMethodId;
    private String interestAccrMethodName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getInstallmentQuantity() {
        return installmentQuantity;
    }

    public void setInstallmentQuantity(int installmentQuantity) {
        this.installmentQuantity = installmentQuantity;
    }

    public int getInstallmentFirstDay() {
        return installmentFirstDay;
    }

    public void setInstallmentFirstDay(int installmentFirstDay) {
        this.installmentFirstDay = installmentFirstDay;
    }

    public Date getFirstInstallmentDate() {
        return firstInstallmentDate;
    }

    public void setFirstInstallmentDate(Date firstInstallmentDate) {
        this.firstInstallmentDate = firstInstallmentDate;
    }

    public Date getLastInstallmentDate() {
        return lastInstallmentDate;
    }

    public void setLastInstallmentDate(Date lastInstallmentDate) {
        this.lastInstallmentDate = lastInstallmentDate;
    }

    public int getMinDaysDisbFirstInst() {
        return minDaysDisbFirstInst;
    }

    public void setMinDaysDisbFirstInst(int minDaysDisbFirstInst) {
        this.minDaysDisbFirstInst = minDaysDisbFirstInst;
    }

    public int getMaxDaysDisbFirstInst() {
        return maxDaysDisbFirstInst;
    }

    public void setMaxDaysDisbFirstInst(int maxDaysDisbFirstInst) {
        this.maxDaysDisbFirstInst = maxDaysDisbFirstInst;
    }

    public int getGraceOnPrinciplePaymentInst() {
        return graceOnPrinciplePaymentInst;
    }

    public void setGraceOnPrinciplePaymentInst(int graceOnPrinciplePaymentInst) {
        this.graceOnPrinciplePaymentInst = graceOnPrinciplePaymentInst;
    }

    public int getGraceOnPrinciplePaymentDays() {
        return graceOnPrinciplePaymentDays;
    }

    public void setGraceOnPrinciplePaymentDays(int graceOnPrinciplePaymentDays) {
        this.graceOnPrinciplePaymentDays = graceOnPrinciplePaymentDays;
    }

    public int getGraceOnInterestPaymentInst() {
        return graceOnInterestPaymentInst;
    }

    public void setGraceOnInterestPaymentInst(int graceOnInterestPaymentInst) {
        this.graceOnInterestPaymentInst = graceOnInterestPaymentInst;
    }

    public int getGraceOnInterestPaymentDays() {
        return graceOnInterestPaymentDays;
    }

    public void setGraceOnInterestPaymentDays(int graceOnInterestPaymentDays) {
        this.graceOnInterestPaymentDays = graceOnInterestPaymentDays;
    }

    public int getGraceOnInterestAccrInst() {
        return graceOnInterestAccrInst;
    }

    public void setGraceOnInterestAccrInst(int graceOnInterestAccrInst) {
        this.graceOnInterestAccrInst = graceOnInterestAccrInst;
    }

    public int getGraceOnInterestAccrDays() {
        return graceOnInterestAccrDays;
    }

    public void setGraceOnInterestAccrDays(int graceOnInterestAccrDays) {
        this.graceOnInterestAccrDays = graceOnInterestAccrDays;
    }

    public Double getInterestRateValue() {
        return interestRateValue;
    }

    public void setInterestRateValue(Double interestRateValue) {
        this.interestRateValue = interestRateValue;
    }

    public int getFrequencyQuantity() {
        return frequencyQuantity;
    }

    public void setFrequencyQuantity(int frequencyQuantity) {
        this.frequencyQuantity = frequencyQuantity;
    }

    public Double getPenaltyOnPrincipleOverdueRateValue() {
        return penaltyOnPrincipleOverdueRateValue;
    }

    public void setPenaltyOnPrincipleOverdueRateValue(Double penaltyOnPrincipleOverdueRateValue) {
        this.penaltyOnPrincipleOverdueRateValue = penaltyOnPrincipleOverdueRateValue;
    }

    public long getFundId() {
        return fundId;
    }

    public void setFundId(long fundId) {
        this.fundId = fundId;
    }

    public Double getPenaltyOnInterestOverdueRateValue() {
        return penaltyOnInterestOverdueRateValue;
    }

    public void setPenaltyOnInterestOverdueRateValue(Double penaltyOnInterestOverdueRateValue) {
        this.penaltyOnInterestOverdueRateValue = penaltyOnInterestOverdueRateValue;
    }

    public boolean isEarlyRepaymentAllowed() {
        return earlyRepaymentAllowed;
    }

    public void setEarlyRepaymentAllowed(boolean earlyRepaymentAllowed) {
        this.earlyRepaymentAllowed = earlyRepaymentAllowed;
    }

    public Double getPenaltyLimitPercent() {
        return penaltyLimitPercent;
    }

    public void setPenaltyLimitPercent(Double penaltyLimitPercent) {
        this.penaltyLimitPercent = penaltyLimitPercent;
    }

    public boolean isCollateralFree() {
        return collateralFree;
    }

    public void setCollateralFree(boolean collateralFree) {
        this.collateralFree = collateralFree;
    }

    public long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(long currencyId) {
        this.currencyId = currencyId;
    }

    public long getFrequencyTypeId() {
        return frequencyTypeId;
    }

    public void setFrequencyTypeId(long frequencyTypeId) {
        this.frequencyTypeId = frequencyTypeId;
    }

    public long getInterestRateValuePerPeriodId() {
        return interestRateValuePerPeriodId;
    }

    public void setInterestRateValuePerPeriodId(long interestRateValuePerPeriodId) {
        this.interestRateValuePerPeriodId = interestRateValuePerPeriodId;
    }

    public long getInterestTypeId() {
        return interestTypeId;
    }

    public void setInterestTypeId(long interestTypeId) {
        this.interestTypeId = interestTypeId;
    }

    public long getPenaltyOnPrincipleOverdueTypeId() {
        return penaltyOnPrincipleOverdueTypeId;
    }

    public void setPenaltyOnPrincipleOverdueTypeId(long penaltyOnPrincipleOverdueTypeId) {
        this.penaltyOnPrincipleOverdueTypeId = penaltyOnPrincipleOverdueTypeId;
    }

    public long getPenaltyOnInterestOverdueTypeId() {
        return penaltyOnInterestOverdueTypeId;
    }

    public void setPenaltyOnInterestOverdueTypeId(long penaltyOnInterestOverdueTypeId) {
        this.penaltyOnInterestOverdueTypeId = penaltyOnInterestOverdueTypeId;
    }

    public long getDaysInYearMethodId() {
        return daysInYearMethodId;
    }

    public void setDaysInYearMethodId(long daysInYearMethodId) {
        this.daysInYearMethodId = daysInYearMethodId;
    }

    public long getDaysInMonthMethodId() {
        return daysInMonthMethodId;
    }

    public void setDaysInMonthMethodId(long daysInMonthMethodId) {
        this.daysInMonthMethodId = daysInMonthMethodId;
    }

    public long getTransactionOrderId() {
        return transactionOrderId;
    }

    public void setTransactionOrderId(long transactionOrderId) {
        this.transactionOrderId = transactionOrderId;
    }

    public long getInterestAccrMethodId() {
        return interestAccrMethodId;
    }

    public void setInterestAccrMethodId(long interestAccrMethodId) {
        this.interestAccrMethodId = interestAccrMethodId;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getFrequencyTypeName() {
        return frequencyTypeName;
    }

    public void setFrequencyTypeName(String frequencyTypeName) {
        this.frequencyTypeName = frequencyTypeName;
    }

    public String getInterestRateValuePerPeriodName() {
        return interestRateValuePerPeriodName;
    }

    public void setInterestRateValuePerPeriodName(String interestRateValuePerPeriodName) {
        this.interestRateValuePerPeriodName = interestRateValuePerPeriodName;
    }

    public String getInterestTypeName() {
        return interestTypeName;
    }

    public void setInterestTypeName(String interestTypeName) {
        this.interestTypeName = interestTypeName;
    }

    public String getPenaltyOnPrincipleOverdueTypeName() {
        return penaltyOnPrincipleOverdueTypeName;
    }

    public void setPenaltyOnPrincipleOverdueTypeName(String penaltyOnPrincipleOverdueTypeName) {
        this.penaltyOnPrincipleOverdueTypeName = penaltyOnPrincipleOverdueTypeName;
    }

    public String getPenaltyOnInterestOverdueTypeName() {
        return penaltyOnInterestOverdueTypeName;
    }

    public void setPenaltyOnInterestOverdueTypeName(String penaltyOnInterestOverdueTypeName) {
        this.penaltyOnInterestOverdueTypeName = penaltyOnInterestOverdueTypeName;
    }

    public String getDaysInYearMethodName() {
        return daysInYearMethodName;
    }

    public void setDaysInYearMethodName(String daysInYearMethodName) {
        this.daysInYearMethodName = daysInYearMethodName;
    }

    public String getDaysInMonthMethodName() {
        return daysInMonthMethodName;
    }

    public void setDaysInMonthMethodName(String daysInMonthMethodName) {
        this.daysInMonthMethodName = daysInMonthMethodName;
    }

    public String getTransactionOrderName() {
        return transactionOrderName;
    }

    public void setTransactionOrderName(String transactionOrderName) {
        this.transactionOrderName = transactionOrderName;
    }

    public String getInterestAccrMethodName() {
        return interestAccrMethodName;
    }

    public void setInterestAccrMethodName(String interestAccrMethodName) {
        this.interestAccrMethodName = interestAccrMethodName;
    }
}
