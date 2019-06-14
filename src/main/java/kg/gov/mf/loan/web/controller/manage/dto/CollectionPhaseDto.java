package kg.gov.mf.loan.web.controller.manage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class CollectionPhaseDto {

    private Long id;
    private Long collectionProcedure;
    private Long debtorId;

    private String debtor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", timezone = "Asia/Bishkek")
    private Date closeDate;

    private Double startAmount;
    private Double closeAmount;
    private Long type;
    private Long status;

    @JsonIgnore
    private String docNumber;
    @JsonIgnore
    private String resultDocNumber;
    @JsonIgnore
    private Long phaseGroup;
    @JsonIgnore
    private Long phaseIndex;
    @JsonIgnore
    private Long phaseSubIndex;
    @JsonIgnore
    private Long finGroup;
    @JsonIgnore
    private Long district;
    @JsonIgnore
    private Long region;

    public CollectionPhaseDto() {}

    public CollectionPhaseDto(Long id, Long collectionProcedure, Long debtorId, String debtor, Date startDate, Date closeDate, Double startAmount, Double closeAmount, Long type, Long status, String docNumber, String resultDocNumber, Long phaseGroup, Long phaseIndex, Long phaseSubIndex, Long finGroup, Long district, Long region) {
        this.id = id;
        this.collectionProcedure = collectionProcedure;
        this.debtorId = debtorId;
        this.debtor = debtor;
        this.startDate = startDate;
        this.closeDate = closeDate;
        this.startAmount = startAmount;
        this.closeAmount = closeAmount;
        this.type = type;
        this.status = status;
        this.docNumber = docNumber;
        this.resultDocNumber = resultDocNumber;
        this.phaseGroup = phaseGroup;
        this.phaseIndex = phaseIndex;
        this.phaseSubIndex = phaseSubIndex;
        this.finGroup = finGroup;
        this.district = district;
        this.region = region;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectionProcedure() {
        return collectionProcedure;
    }

    public void setCollectionProcedure(Long collectionProcedure) {
        this.collectionProcedure = collectionProcedure;
    }

    public Long getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Long debtorId) {
        this.debtorId = debtorId;
    }

    public String getDebtor() {
        return debtor;
    }

    public void setDebtor(String debtor) {
        this.debtor = debtor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Double getStartAmount() {
        return startAmount;
    }

    public void setStartAmount(Double startAmount) {
        this.startAmount = startAmount;
    }

    public Double getCloseAmount() {
        return closeAmount;
    }

    public void setCloseAmount(Double closeAmount) {
        this.closeAmount = closeAmount;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getResultDocNumber() {
        return resultDocNumber;
    }

    public void setResultDocNumber(String resultDocNumber) {
        this.resultDocNumber = resultDocNumber;
    }

    public Long getPhaseGroup() {
        return phaseGroup;
    }

    public void setPhaseGroup(Long phaseGroup) {
        this.phaseGroup = phaseGroup;
    }

    public Long getPhaseIndex() {
        return phaseIndex;
    }

    public void setPhaseIndex(Long phaseIndex) {
        this.phaseIndex = phaseIndex;
    }

    public Long getPhaseSubIndex() {
        return phaseSubIndex;
    }

    public void setPhaseSubIndex(Long phaseSubIndex) {
        this.phaseSubIndex = phaseSubIndex;
    }

    public Long getFinGroup() {
        return finGroup;
    }

    public void setFinGroup(Long finGroup) {
        this.finGroup = finGroup;
    }

    public Long getDistrict() {
        return district;
    }

    public void setDistrict(Long district) {
        this.district = district;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }
}
