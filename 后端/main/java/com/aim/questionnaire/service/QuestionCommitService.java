package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.entity.vo.AnwserViewVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.aim.questionnaire.entity.QuestionCommit;
import com.aim.questionnaire.dao.QuestionCommitMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 15:17
 * @since 0.1.0
 **/
@Service
public class QuestionCommitService {

    @Resource
    private QuestionCommitMapper questionCommitMapper;
    @Resource
    private QuestionAnwsersService anwsersService;


    public int deleteByPrimaryKey(String commitId) {
        return questionCommitMapper.deleteByPrimaryKey(commitId);
    }


    public int insert(QuestionCommit record) {
        return questionCommitMapper.insert(record);
    }


    public int insertSelective(QuestionCommit record) {
        return questionCommitMapper.insertSelective(record);
    }


    public QuestionCommit selectByPrimaryKey(String commitId) {
        return questionCommitMapper.selectByPrimaryKey(commitId);
    }


    public int updateByPrimaryKeySelective(QuestionCommit record) {
        return questionCommitMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(QuestionCommit record) {
        return questionCommitMapper.updateByPrimaryKey(record);
    }

    public QuestionCommit createCommit(AnwserViewVo anwserViewVo, String ip, String userId) {
        QuestionCommit commit = new QuestionCommit();
        commit.setCommitId(UUIDUtil.getOneUUID());
        commit.setAnwserTime(anwserViewVo.getAnwserTime());
        commit.setCommitIp(ip);
        commit.setCommitUserId(userId);
        commit.setCommmitTime(new Date());
        commit.setQuestionnaireId(anwserViewVo.getQuestionnaireId());
        questionCommitMapper.insert(commit);
        anwsersService.createAnwsers(commit.getCommitId(), anwserViewVo.getQuestionOptionVos());
        return commit;
    }


    public List<QuestionCommit> queryCommits(String queryParam) {
        return questionCommitMapper.queryCommits(queryParam);
    }
    public List<QuestionCommit> queryAllCommits(Map queryParam) {
        return questionCommitMapper.queryAllCommits(queryParam);
    }
}
