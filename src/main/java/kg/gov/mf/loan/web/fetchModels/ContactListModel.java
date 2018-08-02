package kg.gov.mf.loan.web.fetchModels;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ContactListModel {

    @Id
    private long id;
    private String smallData;

    public String getSmallData() {
        return smallData;
    }

    public void setSmallData(String smallData) {
        this.smallData = smallData;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
