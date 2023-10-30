package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.entity.QuestionCommit;
import com.aim.questionnaire.entity.QuestionnaireInfo;
import com.aim.questionnaire.service.QuestionCommitService;
import com.aim.questionnaire.service.QuestionInfoService;
import com.aim.questionnaire.service.QuestionnaireInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qnCommit")
public class QuestionCommitController {
    @Autowired
    private QuestionCommitService questionCommitService;
    @Autowired
    private QuestionnaireInfoService questionnaireInfoService;
    @Autowired
    private QuestionInfoService questionInfoService;

    @RequestMapping(value = "/get", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity get(@RequestBody Map questionCommit) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        List<QuestionCommit> questionCommitList=questionCommitService.queryCommits((String) questionCommit.get("questionnaireId"));

        //QuestionnaireInfo questionnaireInfo1 = questionnaireInfoService.getQuestionnaireInfoById(questionnaireInfo.getQuestionnaireId());
        httpResponseEntity.setData(questionCommitList);
        return httpResponseEntity;
    }
}
