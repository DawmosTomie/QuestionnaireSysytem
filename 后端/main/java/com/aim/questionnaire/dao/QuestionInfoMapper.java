package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.QuestionInfo;
import com.aim.questionnaire.entity.vo.QuestionAnalysisVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionInfoMapper {
    int deleteByPrimaryKey(String questionId);

    int insert(QuestionInfo record);

    int insertSelective(QuestionInfo record);

    QuestionInfo selectByPrimaryKey(String questionId);

    int updateByPrimaryKeySelective(QuestionInfo record);

    int updateByPrimaryKey(QuestionInfo record);

    List<QuestionInfo> queryQuestionsByQuestionnaireId(String questionnaireId);

    List<QuestionAnalysisVo.OptionAnalysis> getQuestionAnalysis(String questionId);

  int deleteAllQuetions(String questionnaireId);
}