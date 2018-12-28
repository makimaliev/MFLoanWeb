package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProcedurePhaseDetailsModel {
   @Id
    private Double totalStartAmount;
    private Double totalCloseAmount;



    public Double getTotalStartAmount() {
        return totalStartAmount;
    }

    public void setTotalStartAmount(Double totalStartAmount) {
        this.totalStartAmount = totalStartAmount;
    }

    public Double getTotalCloseAmount() {
        return totalCloseAmount;
    }

    public void setTotalCloseAmount(Double totalCloseAmount) {
        this.totalCloseAmount = totalCloseAmount;
    }
}
