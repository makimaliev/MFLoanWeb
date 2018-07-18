package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class DebtTransferModel implements Comparable<DebtTransferModel>{

    @Id
    private Long id;

    private String number;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date date;

    private Double quantity;

    private Double pricePerUnit;

    private long unitTypeId;

    private Double totalCost;

    private long transferPaymentId;

    private long transferCreditId;

    private long transferPersonId;

    private long goodsTypeId;

    @Override
    public int compareTo(DebtTransferModel model)
    {
        return model.date.compareTo(this.date);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public long getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(long unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public long getTransferPaymentId() {
        return transferPaymentId;
    }

    public void setTransferPaymentId(long transferPaymentId) {
        this.transferPaymentId = transferPaymentId;
    }

    public long getTransferCreditId() {
        return transferCreditId;
    }

    public void setTransferCreditId(long transferCreditId) {
        this.transferCreditId = transferCreditId;
    }

    public long getTransferPersonId() {
        return transferPersonId;
    }

    public void setTransferPersonId(long transferPersonId) {
        this.transferPersonId = transferPersonId;
    }

    public long getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(long goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }
}
