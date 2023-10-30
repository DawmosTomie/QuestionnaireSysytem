package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.entity.QuestionAnwsers;
import com.aim.questionnaire.entity.QuestionCommit;
import com.aim.questionnaire.service.QuestionAnwsersService;
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
@RequestMapping("/qnAnswer")
public class QuestionAnwsersController {

    @Autowired
    private QuestionAnwsersService questionAnwsersService;
    @Autowired
    private QuestionnaireInfoService questionnaireInfoService;

    @RequestMapping(value = "/get", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity get(@RequestBody Map questionAnwsers) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        List<QuestionAnwsers> questionAnwsersList=questionAnwsersService.queryAnwsersOnlyByCommit((String) questionAnwsers.get("commitId"));

        //QuestionnaireInfo questionnaireInfo1 = questionnaireInfoService.getQuestionnaireInfoById(questionnaireInfo.getQuestionnaireId());
        httpResponseEntity.setData(questionAnwsersList);
        return httpResponseEntity;
    }
}
