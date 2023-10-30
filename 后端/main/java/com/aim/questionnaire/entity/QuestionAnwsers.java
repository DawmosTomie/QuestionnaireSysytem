package com.aim.questionnaire.entity;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 15:17
 * @since 0.1.0
 **/
public class QuestionAnwsers {
    private String anwserId;

    private String commitId;

    private String questionId;

    private String anwser;

    public String getAnwserId() {
        return anwserId;
    }

    public void setAnwserId(String anwserId) {
        this.anwserId = anwserId;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnwser() {
        return anwser;
    }

    public void setAnwser(String anwser) {
        this.anwser = anwser;
    }
}