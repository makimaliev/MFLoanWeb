package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LoanGoodsModel {

    @Id
    private Long id;

    private Double quantity;

    private long unitTypeId;

    private long goodsTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public long getUnitTypeId() {
        return unitTypeId;
    }

    public void setUnitTypeId(long unitTypeId) {
        this.unitTypeId = unitTypeId;
    }

    public long getGoodsTypeId() {
        return goodsTypeId;
    }

    public void setGoodsTypeId(long goodsTypeId) {
        this.goodsTypeId = goodsTypeId;
    }
}
