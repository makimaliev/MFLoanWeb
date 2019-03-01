package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CollateralAgreementModel implements Comparable<CollateralAgreementModel> {

    @Id
    private Long id;

    private String agreementNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date agreementDate;

    private String collateralOfficeRegNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date collateralOfficeRegDate;

    private String notaryOfficeRegNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date notaryOfficeRegDate;

    private String arrestRegNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date arrestRegDate;

    private long ownerId;

    @Column(name="itemId")
    private long itemId;

    private String itemName;
    private String itemDescription;
    private long itemTypeId;
    private String itemTypeName;
    private Double quantity;
    private long quantityTypeId;
    private String quantityTypeName;
    private Double collateralValue;
    private int status;

    @Override
    public int compareTo(CollateralAgreementModel model)
    {
        if(model.agreementDate==null || this.agreementDate==null)
            return 1;
        return model.agreementDate.compareTo(this.agreementDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public Date getAgreementDate() {
        return agreementDate;
    }

    public void setAgreementDate(Date agreementDate) {
        this.agreementDate = agreementDate;
    }

    public String getCollateralOfficeRegNumber() {
        return collateralOfficeRegNumber;
    }

    public void setCollateralOfficeRegNumber(String collateralOfficeRegNumber) {
        this.collateralOfficeRegNumber = collateralOfficeRegNumber;
    }

    public Date getCollateralOfficeRegDate() {
        return collateralOfficeRegDate;
    }

    public void setCollateralOfficeRegDate(Date collateralOfficeRegDate) {
        this.collateralOfficeRegDate = collateralOfficeRegDate;
    }

    public String getNotaryOfficeRegNumber() {
        return notaryOfficeRegNumber;
    }

    public void setNotaryOfficeRegNumber(String notaryOfficeRegNumber) {
        this.notaryOfficeRegNumber = notaryOfficeRegNumber;
    }

    public Date getNotaryOfficeRegDate() {
        return notaryOfficeRegDate;
    }

    public void setNotaryOfficeRegDate(Date notaryOfficeRegDate) {
        this.notaryOfficeRegDate = notaryOfficeRegDate;
    }

    public String getArrestRegNumber() {
        return arrestRegNumber;
    }

    public void setArrestRegNumber(String arrestRegNumber) {
        this.arrestRegNumber = arrestRegNumber;
    }

    public Date getArrestRegDate() {
        return arrestRegDate;
    }

    public void setArrestRegDate(Date arrestRegDate) {
        this.arrestRegDate = arrestRegDate;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
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

    public Double getCollateralValue() {
        return collateralValue;
    }

    public void setCollateralValue(Double collateralValue) {
        this.collateralValue = collateralValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
