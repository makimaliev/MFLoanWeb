package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class CreditTermAuditModel {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date startDate;

    private Double interestRateValue;

    private long ratePeriodId;
    private String ratePeriodName;

    private long floatingRateTypeId;
    private String floatingRateTypeName;

    private Double penaltyOnPrincipleOverdueRateValue;

    private long penaltyOnPrincipleOverdueRateTypeId;
    private String penaltyOnPrincipleOverdueRateTypeName;

    private Double penaltyOnInterestOverdueRateValue;

    private long penaltyOnInterestOverdueRateTypeId;
    private String penaltyOnInterestOverdueRateTypeName;

    private Double penaltyLimitPercent;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date penaltyLimitEndDate;

    private long transactionOrderId;
    private String transactionOrderName;

    private long daysInMonthMethodId;
    private String daysInMonthMethodName;

    private long daysInYearMethodId;
    private String daysInYearMethodName;

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Double getInterestRateValue() {
        return interestRateValue;
    }

    public void setInterestRateValue(Double interestRateValue) {
        this.interestRateValue = interestRateValue;
    }

    public long getRatePeriodId() {
        return ratePeriodId;
    }

    public void setRatePeriodId(long ratePeriodId) {
        this.ratePeriodId = ratePeriodId;
    }

    public String getRatePeriodName() {
        return ratePeriodName;
    }

    public void setRatePeriodName(String ratePeriodName) {
        this.ratePeriodName = ratePeriodName;
    }

    public long getFloatingRateTypeId() {
        return floatingRateTypeId;
    }

    public void setFloatingRateTypeId(long floatingRateTypeId) {
        this.floatingRateTypeId = floatingRateTypeId;
    }

    public String getFloatingRateTypeName() {
        return floatingRateTypeName;
    }

    public void setFloatingRateTypeName(String floatingRateTypeName) {
        this.floatingRateTypeName = floatingRateTypeName;
    }

    public Double getPenaltyOnPrincipleOverdueRateValue() {
        return penaltyOnPrincipleOverdueRateValue;
    }

    public void setPenaltyOnPrincipleOverdueRateValue(Double penaltyOnPrincipleOverdueRateValue) {
        this.penaltyOnPrincipleOverdueRateValue = penaltyOnPrincipleOverdueRateValue;
    }

    public long getPenaltyOnPrincipleOverdueRateTypeId() {
        return penaltyOnPrincipleOverdueRateTypeId;
    }

    public void setPenaltyOnPrincipleOverdueRateTypeId(long penaltyOnPrincipleOverdueRateTypeId) {
        this.penaltyOnPrincipleOverdueRateTypeId = penaltyOnPrincipleOverdueRateTypeId;
    }

    public String getPenaltyOnPrincipleOverdueRateTypeName() {
        return penaltyOnPrincipleOverdueRateTypeName;
    }

    public void setPenaltyOnPrincipleOverdueRateTypeName(String penaltyOnPrincipleOverdueRateTypeName) {
        this.penaltyOnPrincipleOverdueRateTypeName = penaltyOnPrincipleOverdueRateTypeName;
    }

    public Double getPenaltyOnInterestOverdueRateValue() {
        return penaltyOnInterestOverdueRateValue;
    }

    public void setPenaltyOnInterestOverdueRateValue(Double penaltyOnInterestOverdueRateValue) {
        this.penaltyOnInterestOverdueRateValue = penaltyOnInterestOverdueRateValue;
    }

    public long getPenaltyOnInterestOverdueRateTypeId() {
        return penaltyOnInterestOverdueRateTypeId;
    }

    public void setPenaltyOnInterestOverdueRateTypeId(long penaltyOnInterestOverdueRateTypeId) {
        this.penaltyOnInterestOverdueRateTypeId = penaltyOnInterestOverdueRateTypeId;
    }

    public String getPenaltyOnInterestOverdueRateTypeName() {
        return penaltyOnInterestOverdueRateTypeName;
    }

    public void setPenaltyOnInterestOverdueRateTypeName(String penaltyOnInterestOverdueRateTypeName) {
        this.penaltyOnInterestOverdueRateTypeName = penaltyOnInterestOverdueRateTypeName;
    }

    public Double getPenaltyLimitPercent() {
        return penaltyLimitPercent;
    }

    public void setPenaltyLimitPercent(Double penaltyLimitPercent) {
        this.penaltyLimitPercent = penaltyLimitPercent;
    }

    public Date getPenaltyLimitEndDate() {
        return penaltyLimitEndDate;
    }

    public void setPenaltyLimitEndDate(Date penaltyLimitEndDate) {
        this.penaltyLimitEndDate = penaltyLimitEndDate;
    }

    public long getTransactionOrderId() {
        return transactionOrderId;
    }

    public void setTransactionOrderId(long transactionOrderId) {
        this.transactionOrderId = transactionOrderId;
    }

    public String getTransactionOrderName() {
        return transactionOrderName;
    }

    public void setTransactionOrderName(String transactionOrderName) {
        this.transactionOrderName = transactionOrderName;
    }

    public long getDaysInMonthMethodId() {
        return daysInMonthMethodId;
    }

    public void setDaysInMonthMethodId(long daysInMonthMethodId) {
        this.daysInMonthMethodId = daysInMonthMethodId;
    }

    public String getDaysInMonthMethodName() {
        return daysInMonthMethodName;
    }

    public void setDaysInMonthMethodName(String daysInMonthMethodName) {
        this.daysInMonthMethodName = daysInMonthMethodName;
    }

    public long getDaysInYearMethodId() {
        return daysInYearMethodId;
    }

    public void setDaysInYearMethodId(long daysInYearMethodId) {
        this.daysInYearMethodId = daysInYearMethodId;
    }

    public String getDaysInYearMethodName() {
        return daysInYearMethodName;
    }

    public void setDaysInYearMethodName(String daysInYearMethodName) {
        this.daysInYearMethodName = daysInYearMethodName;
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
        final CreditTermAuditModel other = (CreditTermAuditModel) o;
        if (!Objects.equals(this.getREV(), other.getREV())) {
            return false;
        }
        return true;
    }
}
