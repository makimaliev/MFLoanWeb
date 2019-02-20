package kg.gov.mf.loan.web.fetchModels;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class DocumentModel {
    @Id
    private Long id;
    private Long docIndex;
    private String indexNo;
    private String documentState;
    private String owner;
    private String documentSubType;
    private Date documentDueDate;
    private String title;
    private String senderRegisteredNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date senderRegisteredDate;
    private String receiverRegisteredNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date receiverRegisteredDate;
    private String senderResponsible;
    private String senderExecutor;
    private String receiverResponsible;
    private String receiverExecutor;
    private String hasTask;
    private int pageCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDocIndex() {
        return docIndex;
    }

    public void setDocIndex(Long docIndex) {
        this.docIndex = docIndex;
    }

    public String getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(String indexNo) {
        this.indexNo = indexNo;
    }

    public String getDocumentState() {
        return documentState;
    }

    public void setDocumentState(String documentState) {
        this.documentState = documentState;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDocumentSubType() {
        return documentSubType;
    }

    public void setDocumentSubType(String documentSubType) {
        this.documentSubType = documentSubType;
    }

    public Date getDocumentDueDate() {
        return documentDueDate;
    }

    public void setDocumentDueDate(Date documentDueDate) {
        this.documentDueDate = documentDueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSenderRegisteredNumber() {
        return senderRegisteredNumber;
    }

    public void setSenderRegisteredNumber(String senderRegisteredNumber) {
        this.senderRegisteredNumber = senderRegisteredNumber;
    }

    public Date getSenderRegisteredDate() {
        return senderRegisteredDate;
    }

    public void setSenderRegisteredDate(Date senderRegisteredDate) {
        this.senderRegisteredDate = senderRegisteredDate;
    }

    public String getReceiverRegisteredNumber() {
        return receiverRegisteredNumber;
    }

    public void setReceiverRegisteredNumber(String receiverRegisteredNumber) {
        this.receiverRegisteredNumber = receiverRegisteredNumber;
    }

    public Date getReceiverRegisteredDate() {
        return receiverRegisteredDate;
    }

    public void setReceiverRegisteredDate(Date receiverRegisteredDate) {
        this.receiverRegisteredDate = receiverRegisteredDate;
    }

    public String getSenderResponsible() {
        return senderResponsible;
    }

    public void setSenderResponsible(String senderResponsible) {
        this.senderResponsible = senderResponsible;
    }

    public String getSenderExecutor() {
        return senderExecutor;
    }

    public void setSenderExecutor(String senderExecutor) {
        this.senderExecutor = senderExecutor;
    }

    public String getReceiverResponsible() {
        return receiverResponsible;
    }

    public void setReceiverResponsible(String receiverResponsible) {
        this.receiverResponsible = receiverResponsible;
    }

    public String getReceiverExecutor() {
        return receiverExecutor;
    }

    public void setReceiverExecutor(String receiverExecutor) {
        this.receiverExecutor = receiverExecutor;
    }

    public String getHasTask() {
        return hasTask;
    }

    public void setHasTask(String hasTask) {
        this.hasTask = hasTask;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
