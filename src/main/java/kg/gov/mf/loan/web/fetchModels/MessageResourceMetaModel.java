package kg.gov.mf.loan.web.fetchModels;

import kg.gov.mf.loan.admin.sys.model.MessageResource;
import kg.gov.mf.loan.web.util.Meta;

import java.util.List;

public class MessageResourceMetaModel {

    private Meta meta;
    private List<MessageResource> data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<MessageResource> getData() {
        return data;
    }

    public void setData(List<MessageResource> data) {
        this.data = data;
    }
}
