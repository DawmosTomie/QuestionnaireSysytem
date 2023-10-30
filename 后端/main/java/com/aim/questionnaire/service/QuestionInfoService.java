package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.QuestionInfoMapper;
import com.aim.questionnaire.entity.QuestionInfo;
import com.aim.questionnaire.entity.QuestionOptions;
import com.aim.questionnaire.entity.vo.QuestionAnalysisVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class QuestionInfoService {
    @Autowired
    private QuestionInfoMapper questionInfoMapper;
    @Autowired
    private QuestionOptionsService questionOptionsService;

    public List<QuestionInfo> queryQuestionsByQuestionnaireId(String questionnaireId) {
        return questionInfoMapper.queryQuestionsByQuestionnaireId(questionnaireId);
    }

    public List<QuestionInfo> queryQuestionsWithOptionsByQuestionnaireId(String questionnaireId) {
        List<QuestionInfo> list = questionInfoMapper.queryQuestionsByQuestionnaireId(questionnaireId);
        if (CollectionUtils.isNotEmpty(list)) {
            for (QuestionInfo questionInfo : list) {
                questionInfo.setOptions(questionOptionsService.queryQuestionOptions(questionInfo.getQuestionId()));
            }
        }
        return list;
    }

    public void save(QuestionInfo questionInfo) {
        if (StringUtils.isEmpty(questionInfo.getQuestionId())) {
            questionInfo.setQuestionId(UUIDUtil.getOneUUID());
            questionInfoMapper.insert(questionInfo);
        } else {
            questionInfoMapper.updateByPrimaryKey(questionInfo);
        }
        questionOptionsService.deleteQuestionOptions(questionInfo.getQuestionId());
        if (CollectionUtils.isNotEmpty(questionInfo.getOptions())) {
            for (QuestionOptions option : questionInfo.getOptions()) {
                option.setQuestionId(questionInfo.getQuestionId());
                questionOptionsService.insert(option);
            }
        }

    }

    public List<QuestionAnalysisVo.OptionAnalysis> getQuestionAnalysis(String questionId) {
        List<QuestionAnalysisVo.OptionAnalysis> analyses = questionInfoMapper.getQuestionAnalysis(questionId);
        if (CollectionUtils.isNotEmpty(analyses)) {
            double total = analyses.stream().mapToDouble(QuestionAnalysisVo.OptionAnalysis::getCount).sum();
            for (QuestionAnalysisVo.OptionAnalysis analysis : analyses) {
                analysis.setPercent(analysis.getCount() / total);
            }
        }
        return analyses;
    }

    public int deleteAllQuetions(String questionnaireId){
        return questionInfoMapper.deleteAllQuetions(questionnaireId);
    }

    public int deleteById(String questionId) {
        return questionInfoMapper.deleteByPrimaryKey(questionId);
    }

    public QuestionInfo getById(String questionId) {
        return questionInfoMapper.selectByPrimaryKey(questionId);
    }

    public void updateQuestionInfo(QuestionInfo questionInfo) {
        questionInfoMapper.updateByPrimaryKeySelective(questionInfo);
    }
}
