package kg.gov.mf.loan.web.fetchModels;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class CreditOrderModel {

    @Id
    private Long id;
    private String regNumber;
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date regDate;
    private String creditOrderState;
    private String creditOrderType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getCreditOrderState() {
        return creditOrderState;
    }

    public void setCreditOrderState(String creditOrderState) {
        this.creditOrderState = creditOrderState;
    }

    public String getCreditOrderType() {
        return creditOrderType;
    }

    public void setCreditOrderType(String creditOrderType) {
        this.creditOrderType = creditOrderType;
    }
}
