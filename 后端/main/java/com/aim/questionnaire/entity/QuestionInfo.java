package com.aim.questionnaire.entity;

import java.util.List;

/**
 * 问卷表
 */
public class QuestionInfo {
    private String questionId;

    private String questionTitle;

    private String questionnaireId;

    private String type;

    private Integer status;

    private Integer isMust;

    private Integer sortNo;

    private String image;

    private List<QuestionOptions> options;

    public String radioValue;

    public List<String> checkboxValue;

    public String textValue;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<QuestionOptions> getOptions() {
        return options;
    }

    public void setOptions(List<QuestionOptions> options) {
        this.options = options;
    }


}