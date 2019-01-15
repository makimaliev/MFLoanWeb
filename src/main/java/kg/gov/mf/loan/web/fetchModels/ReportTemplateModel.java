package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.gov.mf.loan.output.report.model.GroupType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


public class ReportTemplateModel{

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date onDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date additionalDate;

    private boolean showGroup1;
    private boolean showGroup2;
    private boolean showGroup3;
    private boolean showGroup4;
    private boolean showGroup5;
    private boolean showGroup6;

    private GroupType groupType1;
    private GroupType groupType2;
    private GroupType groupType3;
    private GroupType groupType4;
    private GroupType groupType5;
    private GroupType groupType6;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOnDate() {
        return onDate;
    }

    public void setOnDate(Date onDate) {
        this.onDate = onDate;
    }

    public Date getAdditionalDate() {
        return additionalDate;
    }

    public void setAdditionalDate(Date additionalDate) {
        this.additionalDate = additionalDate;
    }

    public boolean isShowGroup1() {
        return showGroup1;
    }

    public void setShowGroup1(boolean showGroup1) {
        this.showGroup1 = showGroup1;
    }

    public boolean isShowGroup2() {
        return showGroup2;
    }

    public void setShowGroup2(boolean showGroup2) {
        this.showGroup2 = showGroup2;
    }

    public boolean isShowGroup3() {
        return showGroup3;
    }

    public void setShowGroup3(boolean showGroup3) {
        this.showGroup3 = showGroup3;
    }

    public boolean isShowGroup4() {
        return showGroup4;
    }

    public void setShowGroup4(boolean showGroup4) {
        this.showGroup4 = showGroup4;
    }

    public boolean isShowGroup5() {
        return showGroup5;
    }

    public void setShowGroup5(boolean showGroup5) {
        this.showGroup5 = showGroup5;
    }

    public boolean isShowGroup6() {
        return showGroup6;
    }

    public void setShowGroup6(boolean showGroup6) {
        this.showGroup6 = showGroup6;
    }

    public GroupType getGroupType1() {
        return groupType1;
    }

    public void setGroupType1(GroupType groupType1) {
        this.groupType1 = groupType1;
    }

    public GroupType getGroupType2() {
        return groupType2;
    }

    public void setGroupType2(GroupType groupType2) {
        this.groupType2 = groupType2;
    }

    public GroupType getGroupType3() {
        return groupType3;
    }

    public void setGroupType3(GroupType groupType3) {
        this.groupType3 = groupType3;
    }

    public GroupType getGroupType4() {
        return groupType4;
    }

    public void setGroupType4(GroupType groupType4) {
        this.groupType4 = groupType4;
    }

    public GroupType getGroupType5() {
        return groupType5;
    }

    public void setGroupType5(GroupType groupType5) {
        this.groupType5 = groupType5;
    }

    public GroupType getGroupType6() {
        return groupType6;
    }

    public void setGroupType6(GroupType groupType6) {
        this.groupType6 = groupType6;
    }
}
