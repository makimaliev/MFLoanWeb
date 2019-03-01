package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class GuarantorAgreementModel implements Comparable<GuarantorAgreementModel>{

    @Id
    private long id;

    private int record_status;

    private String notaryOfficeRegNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date notaryOfficeRegDate;

    private String owner;

    private String notary;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRecord_status() {
        return record_status;
    }

    public void setRecord_status(int record_status) {
        this.record_status = record_status;
    }

    public String getNotaryOfficeRegNumber() {
        return notaryOfficeRegNumber;
    }

    public void setNotaryOfficeRegNumber(String notaryOfficeRegNumber) {
        this.notaryOfficeRegNumber = notaryOfficeRegNumber;
    }

    public Date getNotaryOfficeRegDate() {
        return notaryOfficeRegDate;
    }

    public void setNotaryOfficeRegDate(Date notaryOfficeRegDate) {
        this.notaryOfficeRegDate = notaryOfficeRegDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNotary() {
        return notary;
    }

    public void setNotary(String notary) {
        this.notary = notary;
    }

    @Override
    public int compareTo(GuarantorAgreementModel guarantorAgreementModel) {
        return guarantorAgreementModel.getNotaryOfficeRegDate().compareTo(this.notaryOfficeRegDate);
    }
}
