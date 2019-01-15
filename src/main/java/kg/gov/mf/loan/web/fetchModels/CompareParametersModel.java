package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.gov.mf.loan.output.report.model.GroupType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


public class CompareParametersModel {

    @Id
    private Long id;

    private String fieldName;
    private String comparator;
    private String comparedValue;

    private Date comparedDate;

    private short comparedValueType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComparator() {
        return comparator;
    }

    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    public String getComparedValue() {
        return comparedValue;
    }

    public void setComparedValue(String comparedValue) {
        this.comparedValue = comparedValue;
    }

    public Date getComparedDate() {
        return comparedDate;
    }

    public void setComparedDate(Date comparedDate) {
        this.comparedDate = comparedDate;
    }

    public short getComparedValueType() {
        return comparedValueType;
    }

    public void setComparedValueType(short comparedValueType) {
        this.comparedValueType = comparedValueType;
    }
}
