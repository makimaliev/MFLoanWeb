package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class jsTreeStateModel {

    @Id
    private Boolean opened;
    private Boolean selected;

    public jsTreeStateModel(Boolean opened,Boolean selected){
        this.opened=opened;
        this.selected=selected;
    }

    public Boolean getOpened() {
        return opened;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "{opened:"+this.getOpened()+",selected:"+this.getSelected()+"}";
    }
}
