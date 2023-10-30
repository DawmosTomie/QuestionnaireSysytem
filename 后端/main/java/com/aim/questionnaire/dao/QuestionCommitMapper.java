package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.QuestionCommit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 15:17
 * @since 0.1.0
 **/
@Repository
public interface QuestionCommitMapper {
    int deleteByPrimaryKey(String commitId);

    int insert(QuestionCommit record);

    int insertSelective(QuestionCommit record);

    QuestionCommit selectByPrimaryKey(String commitId);

    int updateByPrimaryKeySelective(QuestionCommit record);

    int updateByPrimaryKey(QuestionCommit record);

    List<QuestionCommit> queryCommits(String queryParam);

    List<QuestionCommit> queryAllCommits(@Param("queryParam")Map queryParam);
}