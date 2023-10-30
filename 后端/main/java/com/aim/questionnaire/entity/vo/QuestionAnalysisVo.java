package com.aim.questionnaire.entity.vo;

import java.util.List;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 16:35
 * @since 0.1.0
 **/
public class QuestionAnalysisVo {
    private String title;
    private String questionId;
    private String type;
    private List<OptionAnalysis> result;

    public String getTitle() {
        return title;
    }

    public QuestionAnalysisVo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getQuestionId() {
        return questionId;
    }

    public QuestionAnalysisVo setQuestionId(String questionId) {
        this.questionId = questionId;
        return this;
    }

    public String getType() {
        return type;
    }

    public QuestionAnalysisVo setType(String type) {
        this.type = type;
        return this;
    }

    public List<OptionAnalysis> getResult() {
        return result;
    }

    public QuestionAnalysisVo setResult(List<OptionAnalysis> result) {
        this.result = result;
        return this;
    }

    public static class OptionAnalysis {
        private String optionId;
        private String option;
        private int count;
        private double percent;

        public String getOptionId() {
            return optionId;
        }

        public OptionAnalysis setOptionId(String optionId) {
            this.optionId = optionId;
            return this;
        }

        public String getOption() {
            return option;
        }

        public OptionAnalysis setOption(String option) {
            this.option = option;
            return this;
        }

        public int getCount() {
            return count;
        }

        public OptionAnalysis setCount(int count) {
            this.count = count;
            return this;
        }

        public double getPercent() {
            return percent;
        }

        public OptionAnalysis setPercent(double percent) {
            this.percent = percent;
            return this;
        }
    }
}
