package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class SupervisorPlanYearModel{

    @Id
    private Long id;

    private int year;

    private Double totalAmount;
    private Double quater1;
    private Double quater2;
    private Double quater3;
    private Double quater4;
    private Double month1;
    private Double month2;
    private Double month3;
    private Double month4;
    private Double month5;
    private Double month6;
    private Double month7;
    private Double month8;
    private Double month9;
    private Double month10;
    private Double month11;
    private Double month12;


    //region get-set


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getQuater1() {
        return quater1;
    }

    public void setQuater1(Double quater1) {
        this.quater1 = quater1;
    }

    public Double getQuater2() {
        return quater2;
    }

    public void setQuater2(Double quater2) {
        this.quater2 = quater2;
    }

    public Double getQuater3() {
        return quater3;
    }

    public void setQuater3(Double quater3) {
        this.quater3 = quater3;
    }

    public Double getQuater4() {
        return quater4;
    }

    public void setQuater4(Double quater4) {
        this.quater4 = quater4;
    }

    public Double getMonth1() {
        return month1;
    }

    public void setMonth1(Double month1) {
        this.month1 = month1;
    }

    public Double getMonth2() {
        return month2;
    }

    public void setMonth2(Double month2) {
        this.month2 = month2;
    }

    public Double getMonth3() {
        return month3;
    }

    public void setMonth3(Double month3) {
        this.month3 = month3;
    }

    public Double getMonth4() {
        return month4;
    }

    public void setMonth4(Double month4) {
        this.month4 = month4;
    }

    public Double getMonth5() {
        return month5;
    }

    public void setMonth5(Double month5) {
        this.month5 = month5;
    }

    public Double getMonth6() {
        return month6;
    }

    public void setMonth6(Double month6) {
        this.month6 = month6;
    }

    public Double getMonth7() {
        return month7;
    }

    public void setMonth7(Double month7) {
        this.month7 = month7;
    }

    public Double getMonth8() {
        return month8;
    }

    public void setMonth8(Double month8) {
        this.month8 = month8;
    }

    public Double getMonth9() {
        return month9;
    }

    public void setMonth9(Double month9) {
        this.month9 = month9;
    }

    public Double getMonth10() {
        return month10;
    }

    public void setMonth10(Double month10) {
        this.month10 = month10;
    }

    public Double getMonth11() {
        return month11;
    }

    public void setMonth11(Double month11) {
        this.month11 = month11;
    }

    public Double getMonth12() {
        return month12;
    }

    public void setMonth12(Double month12) {
        this.month12 = month12;
    }

    //endregion
}
