package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class CollateralAgreementModel {

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

    private long itemId;

    private String itemName;
    private String itemDescription;
    private long itemTypeId;
    private String itemTypeName;
    private Double quantity;
    private long quantityTypeId;
    private String quantityTypeName;
    private Double collateralValue;
}
