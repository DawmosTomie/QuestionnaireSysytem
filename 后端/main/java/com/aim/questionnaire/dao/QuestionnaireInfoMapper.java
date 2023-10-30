package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.QuestionnaireInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface QuestionnaireInfoMapper {
    int deleteByPrimaryKey(String questionnaireId);

    int insert(QuestionnaireInfo record);

    int insertSelective(QuestionnaireInfo record);

    QuestionnaireInfo selectByPrimaryKey(String questionnaireId);

    int updateByPrimaryKeySelective(QuestionnaireInfo record);

    int updateByPrimaryKey(QuestionnaireInfo record);

    List<QuestionnaireInfo> queryProjectQuestionnaire(String id);

    List<QuestionnaireInfo> queryAllTemplates();

    List<QuestionnaireInfo> queryAll(@Param("queryParam") Map<String, Object> questionnaireInfo);

    List<QuestionnaireInfo> querySurveyCrowd(String crowd);

    List<Map<String,Long>> queryTemplateSurveyCrowd();
}