package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.QuestionOptions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionOptionsMapper {
    int deleteByPrimaryKey(String optionId);

    int insert(QuestionOptions record);

    int insertSelective(QuestionOptions record);

    QuestionOptions selectByPrimaryKey(String optionId);

    int updateByPrimaryKeySelective(QuestionOptions record);

    int updateByPrimaryKey(QuestionOptions record);

    List<QuestionOptions> queryQuestionOptions(String questionId);

    int deleteQuestionOptions(String questionId);
}