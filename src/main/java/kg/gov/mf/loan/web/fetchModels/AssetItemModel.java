package kg.gov.mf.loan.web.fetchModels;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AssetItemModel implements Comparable<AssetItemModel>{

    @Id
    private long id;
    private String name;
    private String description;
    private String itemTypeName;
    private Double quantity;
    private Double risk_rate;
    private Double demand_rate;
    private String quantityTypeName;
    private Double collateralValue;
    private Double estimatedValue;
    private String conditionTypeName;
    private Long ownerId;
    private String owner;
    private Long organizationId;
    private String organization;
    private int status;
    private boolean inspection_needed;
    private String inspection_needed_description;
    private Long assetItemDetailsId;

    //region GET-SET

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getItemTypeName() {
        return itemTypeName;
    }

    public void setItemTypeName(String itemTypeName) {
        this.itemTypeName = itemTypeName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getRisk_rate() {
        return risk_rate;
    }

    public void setRisk_rate(Double risk_rate) {
        this.risk_rate = risk_rate;
    }

    public Double getDemand_rate() {
        return demand_rate;
    }

    public void setDemand_rate(Double demand_rate) {
        this.demand_rate = demand_rate;
    }

    public String getQuantityTypeName() {
        return quantityTypeName;
    }

    public void setQuantityTypeName(String quantityTypeName) {
        this.quantityTypeName = quantityTypeName;
    }

    public Double getCollateralValue() {
        return collateralValue;
    }

    public void setCollateralValue(Double collateralValue) {
        this.collateralValue = collateralValue;
    }

    public Double getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(Double estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public String getConditionTypeName() {
        return conditionTypeName;
    }

    public void setConditionTypeName(String conditionTypeName) {
        this.conditionTypeName = conditionTypeName;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isInspection_needed() {
        return inspection_needed;
    }

    public void setInspection_needed(boolean inspection_needed) {
        this.inspection_needed = inspection_needed;
    }

    public String getInspection_needed_description() {
        return inspection_needed_description;
    }

    public void setInspection_needed_description(String inspection_needed_description) {
        this.inspection_needed_description = inspection_needed_description;
    }

    public Long getAssetItemDetailsId() {
        return assetItemDetailsId;
    }

    public void setAssetItemDetailsId(Long assetItemDetailsId) {
        this.assetItemDetailsId = assetItemDetailsId;
    }


    //endregion

    @Override
    public int compareTo(AssetItemModel assetItemModel) {
        return assetItemModel.getCollateralValue().compareTo(this.collateralValue);
    }
}
