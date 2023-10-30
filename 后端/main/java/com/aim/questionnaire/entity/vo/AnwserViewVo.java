package com.aim.questionnaire.entity.vo;

import java.util.List;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 15:21
 * @since 0.1.0
 **/
public class AnwserViewVo {
    private String questionnaireId;
    private int anwserTime;
    private List<QuestionOptionVo> questionOptionVos;

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public AnwserViewVo setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
        return this;
    }

    public int getAnwserTime() {
        return anwserTime;
    }

    public AnwserViewVo setAnwserTime(int anwserTime) {
        this.anwserTime = anwserTime;
        return this;
    }

    public List<QuestionOptionVo> getQuestionOptionVos() {
        return questionOptionVos;
    }

    public AnwserViewVo setQuestionOptionVos(List<QuestionOptionVo> questionOptionVos) {
        this.questionOptionVos = questionOptionVos;
        return this;
    }
}
