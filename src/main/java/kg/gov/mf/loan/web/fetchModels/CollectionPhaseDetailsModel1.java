package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CollectionPhaseDetailsModel1 {

    @Id
    private Long id;
    private Double closeTotalAmount;
    private Double closePrincipal;
    private Double closeInterest;
    private Double closePenalty;
    private Double startTotalAmount;
    private Double startPrincipal;
    private Double startInterest;
    private Double startPenalty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
