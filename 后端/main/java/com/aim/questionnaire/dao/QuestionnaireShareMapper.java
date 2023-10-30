package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.QuestionnaireShare;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface QuestionnaireShareMapper {
    int deleteByPrimaryKey(String questionnaireId);

    int insert(QuestionnaireShare record);

    int insertSelective(QuestionnaireShare record);

    QuestionnaireShare selectByPrimaryKey(String questionnaireId);

    int updateByPrimaryKeySelective(QuestionnaireShare record);

    int updateByPrimaryKey(QuestionnaireShare record);

    QuestionnaireShare getQuestionnaireShare(String questionnaireId);

    List<QuestionnaireShare> queryQuesionnaires(@Param("questionnaireId") String questionnaireId, @Param("userName") String username);
}