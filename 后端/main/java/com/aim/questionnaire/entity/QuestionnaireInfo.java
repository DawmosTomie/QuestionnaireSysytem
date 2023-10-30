package com.aim.questionnaire.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 问卷信息表
 */
public class QuestionnaireInfo {
    private String questionnaireId;

    private String title;

    private String userId;

    private Integer status;

    private String desc;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;

    private String projectId;

    private Integer isTemplate;

    private String surveyCrowd;

    String templateId;

    public Long childNum;


    private List<QuestionInfo> questions;

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(Integer isTemplate) {
        this.isTemplate = isTemplate;
    }

    public List<QuestionInfo> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionInfo> questions) {
        this.questions = questions;
    }

    public String getSurveyCrowd() {
        return surveyCrowd;
    }

    public void setSurveyCrowd(String surveyCrowd) {
        this.surveyCrowd = surveyCrowd;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}