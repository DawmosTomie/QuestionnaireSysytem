package com.aim.questionnaire.entity.vo;

import com.aim.questionnaire.entity.QuestionOptions;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 15:22
 * @since 0.1.0
 **/
public class QuestionOptionVo extends QuestionOptions {
    private String radioValue;
    private String textValue;
    private List<String> checkboxValue;

    public String getRadioValue() {
        return radioValue;
    }

    public QuestionOptionVo setRadioValue(String radioValue) {
        this.radioValue = radioValue;
        return this;
    }

    public String getTextValue() {
        return textValue;
    }

    public QuestionOptionVo setTextValue(String textValue) {
        this.textValue = textValue;
        return this;
    }

    public String getData() {
        if (!StringUtils.isEmpty(textValue)) {
            return textValue;
        }
        if (!StringUtils.isEmpty(radioValue)) {
            return radioValue;
        }
        if (!CollectionUtils.isEmpty(checkboxValue)) {
            return StringUtils.join(checkboxValue, ",");
        }
        return null;
    }

    public List<String> getCheckboxValue() {
        return checkboxValue;
    }

    public QuestionOptionVo setCheckboxValue(List<String> checkboxValue) {
        this.checkboxValue = checkboxValue;
        return this;
    }
}
