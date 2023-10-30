package com.aim.questionnaire.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 15:17
 * @since 0.1.0
 **/
public class QuestionCommit {
    private String commitId;

    /**
     * 提交人
     */
    private String commitUserId;

    private String questionnaireId;

    /**
     * 提交时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commmitTime;

    /**
     * 答题时长
     */
    private Integer anwserTime;

    /**
     * 提交ip
     */
    private String commitIp;

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getCommitUserId() {
        return commitUserId;
    }

    public void setCommitUserId(String commitUserId) {
        this.commitUserId = commitUserId;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Date getCommmitTime() {
        return commmitTime;
    }

    public void setCommmitTime(Date commmitTime) {
        this.commmitTime = commmitTime;
    }

    public Integer getAnwserTime() {
        return anwserTime;
    }

    public void setAnwserTime(Integer anwserTime) {
        this.anwserTime = anwserTime;
    }

    public String getCommitIp() {
        return commitIp;
    }

    public void setCommitIp(String commitIp) {
        this.commitIp = commitIp;
    }
}