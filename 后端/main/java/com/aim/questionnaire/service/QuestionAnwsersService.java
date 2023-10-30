package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.entity.vo.QuestionOptionVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.aim.questionnaire.dao.QuestionAnwsersMapper;
import com.aim.questionnaire.entity.QuestionAnwsers;

import java.util.List;

/**
 * @author tsao
 * @version 0.1.0
 * @create 2022-06-17 15:17
 * @since 0.1.0
 **/
@Service
public class QuestionAnwsersService {

    @Resource
    private QuestionAnwsersMapper questionAnwsersMapper;


    public int deleteByPrimaryKey(String anwserId) {
        return questionAnwsersMapper.deleteByPrimaryKey(anwserId);
    }


    public int insert(QuestionAnwsers record) {
        return questionAnwsersMapper.insert(record);
    }


    public int insertSelective(QuestionAnwsers record) {
        return questionAnwsersMapper.insertSelective(record);
    }


    public QuestionAnwsers selectByPrimaryKey(String commitId) {
        return questionAnwsersMapper.selectByPrimaryKey(commitId);
    }


    public int updateByPrimaryKeySelective(QuestionAnwsers record) {
        return questionAnwsersMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(QuestionAnwsers record) {
        return questionAnwsersMapper.updateByPrimaryKey(record);
    }

    public void createAnwsers(String commitId, List<QuestionOptionVo> questionOptionVos) {
        if (CollectionUtils.isNotEmpty(questionOptionVos)) {
            for (QuestionOptionVo questionOptionVo : questionOptionVos) {
                if (StringUtils.isNotBlank(questionOptionVo.getData())) {
                    for (String answer : StringUtils.split(questionOptionVo.getData(), ",")) {
                        QuestionAnwsers questionAnwsers = new QuestionAnwsers();
                        questionAnwsers.setAnwserId(UUIDUtil.getOneUUID());
                        questionAnwsers.setAnwser(answer);
                        questionAnwsers.setCommitId(commitId);
                        questionAnwsers.setQuestionId(questionOptionVo.getQuestionId());
                        questionAnwsersMapper.insert(questionAnwsers);
                    }
                }
            }
        }
    }

    public List<QuestionAnwsers> queryAnwsersByCommit(String questionId,String commitId) {
        return questionAnwsersMapper.queryAnwsersByCommit(questionId,commitId);
    }

    public List<QuestionAnwsers> queryAnwsersOnlyByCommit(String commitId) {
        return questionAnwsersMapper.queryAnwsersOnlyByCommit(commitId);
    }
}
