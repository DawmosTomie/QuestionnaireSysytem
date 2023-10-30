package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.QuestionnaireInfoMapper;
import com.aim.questionnaire.dao.QuestionnaireShareMapper;
import com.aim.questionnaire.entity.*;
import com.aim.questionnaire.entity.vo.AnwserViewVo;
import com.aim.questionnaire.entity.vo.QuestionAnalysisVo;
import com.aim.questionnaire.entity.vo.ShareLnkVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestionnaireInfoService {
    @Autowired
    private QuestionnaireInfoMapper questionnaireInfoMapper;
    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private QuestionCommitService questionCommitService;
    @Autowired
    private QuestionOptionsService questionOptionsService;
    @Autowired
    private QuestionnaireShareMapper questionnaireShareMapper;

    public QuestionnaireInfo getQuestionnaireInfoById(String id) {
        return questionnaireInfoMapper.selectByPrimaryKey(id);
    }

    public List<QuestionnaireInfo> queryProjectQustionnaire(String projectId) {
        return questionnaireInfoMapper.queryProjectQuestionnaire(projectId);
    }

    public String add(QuestionnaireInfo questionnaireInfo) {
        if(StringUtils.isNotBlank(questionnaireInfo.getQuestionnaireId())){
            questionnaireInfoMapper.updateByPrimaryKeySelective(questionnaireInfo);
            return questionnaireInfo.getQuestionnaireId();
        }
        questionnaireInfo.setQuestionnaireId(UUIDUtil.getOneUUID());
        questionnaireInfo.setStatus(0);
        questionnaireInfoMapper.insert(questionnaireInfo);
        return questionnaireInfo.getQuestionnaireId();
    }

    public List<QuestionInfo> queryQuestions(String questionnaireId) {
        return questionInfoService.queryQuestionsWithOptionsByQuestionnaireId(questionnaireId);
    }

    public QuestionnaireInfo answerView(String questionnaireId, String commitId) {
        QuestionnaireInfo questionnaireInfo = questionnaireInfoMapper.selectByPrimaryKey(questionnaireId);
        if (questionnaireInfo != null) {
            if(StringUtils.isNotBlank(commitId)){
                questionnaireInfo.setQuestions(queryQuestionsWithAnwser(questionnaireId,commitId));
            }else{
                questionnaireInfo.setQuestions(queryQuestions(questionnaireId));
            }
        }
        return questionnaireInfo;
    }

    @Autowired
    QuestionAnwsersService questionAnwsersService;

    private List<QuestionInfo> queryQuestionsWithAnwser(String questionnaireId, String commitId) {
        List<QuestionInfo> list = questionInfoService.queryQuestionsWithOptionsByQuestionnaireId(questionnaireId);
        if(CollectionUtils.isNotEmpty(list)){
            for (QuestionInfo questionInfo : list) {
                List<QuestionAnwsers> anwsers = questionAnwsersService.queryAnwsersByCommit(questionInfo.getQuestionId(),commitId);
                if(CollectionUtils.isNotEmpty(anwsers)){
                    if(StringUtils.equals("checkbox",questionInfo.getType())){
                        questionInfo.checkboxValue = anwsers.stream().map(QuestionAnwsers::getAnwser).collect(Collectors.toList());
                    }else if(StringUtils.equals("radio",questionInfo.getType())){
                        questionInfo.radioValue = anwsers.get(0).getAnwser();
                    }else {
                        questionInfo.textValue = anwsers.get(0).getAnwser();
                    }
                }
            }
        }
        return list;
    }

    public int updateQustionnaire(QuestionnaireInfo questionnaireInfo) {
        return questionnaireInfoMapper.updateByPrimaryKeySelective(questionnaireInfo);
    }

    public QuestionCommit anwser(AnwserViewVo anwserViewVo, String ip, String userId) {
        QuestionCommit questionCommit = questionCommitService.createCommit(anwserViewVo, ip, userId);
        return questionCommit;
    }

    public List<QuestionAnalysisVo> analysis(String questionnaireId) {
        QuestionnaireInfo questionnaireInfo = questionnaireInfoMapper.selectByPrimaryKey(questionnaireId);
        List<QuestionAnalysisVo> analysisVos = new ArrayList<>();
        if (questionnaireInfo != null) {
            List<QuestionInfo> questionInfos = questionInfoService.queryQuestionsWithOptionsByQuestionnaireId(questionnaireId);
            for (QuestionInfo questionInfo : questionInfos) {
                QuestionAnalysisVo analysisVo = new QuestionAnalysisVo();
                analysisVo.setQuestionId(questionInfo.getQuestionId());
                analysisVo.setTitle(questionInfo.getQuestionTitle());
                analysisVo.setType(questionInfo.getType());
                analysisVo.setResult(questionInfoService.getQuestionAnalysis(questionInfo.getQuestionId()));
                analysisVos.add(analysisVo);
            }
        }
        return analysisVos;
    }

    public List<QuestionnaireInfo> queryAllTemplates(PageInfo pageInfo) {
        return questionnaireInfoMapper.queryAllTemplates();
    }

    public void useTemplate(QuestionnaireInfo questionnaireInfo) {
        String templateId = questionnaireInfo.getQuestionnaireId();
        String quesitonnaireId = UUIDUtil.getOneUUID();
        questionnaireInfo.setIsTemplate(0);
        questionnaireInfo.setQuestionnaireId(quesitonnaireId);
        questionnaireInfo.setStatus(0);
        questionnaireInfo.setTemplateId(templateId);
        List<QuestionInfo> questionInfos = questionInfoService.queryQuestionsWithOptionsByQuestionnaireId(templateId);
        if (CollectionUtils.isNotEmpty(questionInfos)) {
            for (QuestionInfo questionInfo : questionInfos) {
                questionInfo.setQuestionnaireId(quesitonnaireId);
                questionInfo.setQuestionId(null);
                if (CollectionUtils.isNotEmpty(questionInfo.getOptions())) {
                    for (QuestionOptions option : questionInfo.getOptions()) {
                        option.setQuestionId(questionInfo.getQuestionId());
                        option.setOptionId(UUIDUtil.getOneUUID());
                        questionOptionsService.insert(option);
                    }
                }
                questionInfoService.save(questionInfo);
            }
        }
        questionnaireInfoMapper.insert(questionnaireInfo);
    }

    public QuestionnaireShare getQuestionnaireShare(String questionnaireId) {
        return questionnaireShareMapper.getQuestionnaireShare(questionnaireId);
    }

    public void createShare(ShareLnkVo shareInfo) {
        List<String> userIds = shareInfo.getUserIds();
        if (CollectionUtils.isNotEmpty(userIds)) {

            for (String userId : userIds) {
                QuestionnaireShare questionnaireShare = new QuestionnaireShare();
                questionnaireShare.setQuestionnaireId(shareInfo.getQuestionnaireId());
                questionnaireShare.setShareLink(shareInfo.getUrl() + "/display/" + shareInfo.getQuestionnaireId());
                questionnaireShare.setShareBy(MDC.get("username"));
                questionnaireShare.setShareTim(new Date());
                questionnaireShare.setUserId(userId);
                questionnaireShareMapper.insert(questionnaireShare);
            }
        }
    }

    public List<QuestionnaireInfo> queryAllQuestionnaires(Map<String, Object> questionnaireInfo) {
        return questionnaireInfoMapper.queryAll(questionnaireInfo);
    }

    public boolean hasPermission(String questionnaireId, String username) {
        return CollectionUtils.isNotEmpty(questionnaireShareMapper.queryQuesionnaires(questionnaireId, username));
    }

    public List<QuestionnaireInfo> queryRecommend(String crowd) {
        return questionnaireInfoMapper.querySurveyCrowd(crowd);
    }

    public List<Map<String,Long>> queryTemplateSurveyCrowd(){
        return questionnaireInfoMapper.queryTemplateSurveyCrowd();
    }
}
