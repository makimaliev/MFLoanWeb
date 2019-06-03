package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CollectionPhaseModel1 implements Comparable<CollectionPhaseModel1> {

    @Id
    private long v_cph_id;
    private long  v_debtor_id;
    private String v_debtor_name;
    private long v_cph_collectionProcedureId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date v_cph_startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date v_cph_closeDate;

    private Long v_cph_phaseTypeId;
    private Long v_cph_phaseStatusId;
    private Double v_cph_start_total_amount;
    private Double v_cph_close_total_amount;
    private Long v_debtor_owner_id;

    @Override
    public int compareTo(CollectionPhaseModel1 model)
    {
        return model.v_cph_startDate.compareTo(this.v_cph_startDate);
    }

    public long getV_cph_id() {
        return v_cph_id;
    }

    public void setV_cph_id(long v_cph_id) {
        this.v_cph_id = v_cph_id;
    }

    public long getV_debtor_id() {
        return v_debtor_id;
    }

    public void setV_debtor_id(long v_debtor_id) {
        this.v_debtor_id = v_debtor_id;
    }

    public String getV_debtor_name() {
        return v_debtor_name;
    }

    public void setV_debtor_name(String v_debtor_name) {
        this.v_debtor_name = v_debtor_name;
    }

    public long getV_cph_collectionProcedureId() {
        return v_cph_collectionProcedureId;
    }

    public void setV_cph_collectionProcedureId(long v_cph_collectionProcedureId) {
        this.v_cph_collectionProcedureId = v_cph_collectionProcedureId;
    }

    public Date getV_cph_startDate() {
        return v_cph_startDate;
    }

    public void setV_cph_startDate(Date v_cph_startDate) {
        this.v_cph_startDate = v_cph_startDate;
    }

    public Date getV_cph_closeDate() {
        return v_cph_closeDate;
    }

    public void setV_cph_closeDate(Date v_cph_closeDate) {
        this.v_cph_closeDate = v_cph_closeDate;
    }

    public Long getV_cph_phaseTypeId() {
        return v_cph_phaseTypeId;
    }

    public void setV_cph_phaseTypeId(Long v_cph_phaseTypeId) {
        this.v_cph_phaseTypeId = v_cph_phaseTypeId;
    }

    public Long getV_cph_phaseStatusId() {
        return v_cph_phaseStatusId;
    }

    public void setV_cph_phaseStatusId(Long v_cph_phaseStatusId) {
        this.v_cph_phaseStatusId = v_cph_phaseStatusId;
    }

    public Double getV_cph_start_total_amount() {
        return v_cph_start_total_amount;
    }

    public void setV_cph_start_total_amount(Double v_cph_start_total_amount) {
        this.v_cph_start_total_amount = v_cph_start_total_amount;
    }

    public Double getV_cph_close_total_amount() {
        return v_cph_close_total_amount;
    }

    public void setV_cph_close_total_amount(Double v_cph_close_total_amount) {
        this.v_cph_close_total_amount = v_cph_close_total_amount;
    }

    public Long getV_debtor_owner_id() {
        return v_debtor_owner_id;
    }

    public void setV_debtor_owner_id(Long v_debtor_owner_id) {
        this.v_debtor_owner_id = v_debtor_owner_id;
    }
}
