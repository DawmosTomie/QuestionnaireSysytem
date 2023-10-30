package com.aim.questionnaire.entity;

import java.util.Date;
import java.util.List;

public class QuestionnaireShare {
    private String questionnaireId;

    private String shareLink;

    private String shareBy;

    private Date shareTim;

    private String userId;

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId == null ? null : questionnaireId.trim();
    }

    public String getShareLink() {
        return shareLink;
    }

    public void setShareLink(String shareLink) {
        this.shareLink = shareLink == null ? null : shareLink.trim();
    }

    public String getShareBy() {
        return shareBy;
    }

    public void setShareBy(String shareBy) {
        this.shareBy = shareBy == null ? null : shareBy.trim();
    }

    public Date getShareTim() {
        return shareTim;
    }

    public void setShareTim(Date shareTim) {
        this.shareTim = shareTim;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}