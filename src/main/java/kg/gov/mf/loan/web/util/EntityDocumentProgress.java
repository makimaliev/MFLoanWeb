package kg.gov.mf.loan.web.util;

import kg.gov.mf.loan.manage.model.entitydocument.EntityDocument;

public class EntityDocumentProgress {

    private boolean completed = false;
    private boolean approved = false;
    private boolean registered = false;

    private int progress = 0;

    private EntityDocument document;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public EntityDocument getDocument() {
        return document;
    }

    public void setDocument(EntityDocument document) {
        this.document = document;
    }

    public void calculateProgress()
    {
        if(completed)
            progress = progress + 33;
        if(approved)
            progress = progress + 33;
        if(registered)
            progress = progress + 33;
        if(progress == 99)
            progress = 100;
    }
}
