package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoanModel1 {
    @Id
    private Long id;
    private String regNumber;
    private Long stateId;

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

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
}
