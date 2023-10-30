package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.QuestionAnwsers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 15:17
 * @since 0.1.0
 **/
@Repository
public interface QuestionAnwsersMapper {
    int deleteByPrimaryKey(String anwserId);

    int insert(QuestionAnwsers record);

    int insertSelective(QuestionAnwsers record);

    QuestionAnwsers selectByPrimaryKey(String anwserId);

    int updateByPrimaryKeySelective(QuestionAnwsers record);

    int updateByPrimaryKey(QuestionAnwsers record);

    List<QuestionAnwsers> queryAnwsersByCommit(@Param("questionId") String questionId,@Param("commitId") String commitId);

    List<QuestionAnwsers> queryAnwsersOnlyByCommit(String commitId);
}