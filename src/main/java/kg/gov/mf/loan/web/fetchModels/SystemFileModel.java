package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SystemFileModel {

    @Id
    private Long system_file_id;
    private Long attachment_id;
    private String sys_name;
    private String attachment_name;
    private String path;

    public Long getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(Long attachment_id) {
        this.attachment_id = attachment_id;
    }

    public Long getSystem_file_id() {
        return system_file_id;
    }

    public void setSystem_file_id(Long system_file_id) {
        this.system_file_id = system_file_id;
    }

    public String getSys_name() {
        return sys_name;
    }

    public void setSys_name(String sys_name) {
        this.sys_name = sys_name;
    }

    public String getAttachment_name() {
        return attachment_name;
    }

    public void setAttachment_name(String attachment_name) {
        this.attachment_name = attachment_name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
