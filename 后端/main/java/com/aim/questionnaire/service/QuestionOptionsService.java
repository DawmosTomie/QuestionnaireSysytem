package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.QuestionOptionsMapper;
import com.aim.questionnaire.entity.QuestionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionOptionsService {
    @Autowired
    private QuestionOptionsMapper questionOptionsMapper;

    public List<QuestionOptions> queryQuestionOptions(String questionId) {
        return questionOptionsMapper.queryQuestionOptions(questionId);
    }

    public int deleteQuestionOptions(String questionId) {
        return questionOptionsMapper.deleteQuestionOptions(questionId);
    }

    public int insert(QuestionOptions option) {
        option.setOptionId(UUIDUtil.getOneUUID());
        return questionOptionsMapper.insert(option);
    }
}
