package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CollateralItemModel {

    @Id
    private Long id;

    private String name;

    private String description;

    private long itemTypeId;
    private String itemTypeName;

    private double quantity;

    private double risk_rate;

    private double demand_rate;

    private long quantityTypeId;

    private String quantityTypeName;

    private double collateralValue;

    private double estimatedValue;

    private long conditionTypeId;
    private String conditionTypeName;
    private int status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(long itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getRisk_rate() {
        return risk_rate;
    }

    public void setRisk_rate(double risk_rate) {
        this.risk_rate = risk_rate;
    }

    public double getDemand_rate() {
        return demand_rate;
    }

    public void setDemand_rate(double demand_rate) {
        this.demand_rate = demand_rate;
    }

    public long getQuantityTypeId() {
        return quantityTypeId;
    }

    public void setQuantityTypeId(long quantityTypeId) {
        this.quantityTypeId = quantityTypeId;
    }

    public String getQuantityTypeName() {
        return quantityTypeName;
    }

    public void setQuantityTypeName(String quantityTypeName) {
        this.quantityTypeName = quantityTypeName;
    }

    public double getCollateralValue() {
        return collateralValue;
    }

    public void setCollateralValue(double collateralValue) {
        this.collateralValue = collateralValue;
    }

    public double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public long getConditionTypeId() {
        return conditionTypeId;
    }

    public void setConditionTypeId(long conditionTypeId) {
        this.conditionTypeId = conditionTypeId;
    }

    public String getConditionTypeName() {
        return conditionTypeName;
    }

    public void setConditionTypeName(String conditionTypeName) {
        this.conditionTypeName = conditionTypeName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
