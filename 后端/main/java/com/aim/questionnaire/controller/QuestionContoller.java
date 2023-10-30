package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.entity.QuestionInfo;
import com.aim.questionnaire.entity.QuestionnaireInfo;
import com.aim.questionnaire.service.QuestionInfoService;
import com.aim.questionnaire.service.QuestionnaireInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionContoller {
    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private QuestionnaireInfoService questionnaireInfoService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity save(@RequestBody QuestionInfo questionInfos) {
        System.out.println(questionInfos.getQuestionnaireId());
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

            String questionnaireId = questionInfos.getQuestionnaireId();
            if (StringUtils.isNotBlank(questionnaireId)) {
                QuestionnaireInfo questionnaireInfo = questionnaireInfoService.getQuestionnaireInfoById(questionnaireId);
                if (questionnaireInfo != null) {
                    if (questionnaireInfo.getStatus() == 3) {
                        httpResponseEntity.setCode(Constans.EXIST_CODE);
                        httpResponseEntity.setMessage("调查问卷不存在，请重试");
                    } else if (questionnaireInfo.getStatus() == 1) {
                        httpResponseEntity.setCode(Constans.EXIST_CODE);
                        httpResponseEntity.setMessage("调查问卷已发布，无法修改");
                    } else {
                        questionInfoService.save(questionInfos);
                        httpResponseEntity.setCode(Constans.SUCCESS_CODE);

                    }
                } else {
                    httpResponseEntity.setCode(Constans.EXIST_CODE);
                    httpResponseEntity.setMessage("调查问卷不存在，请重试");
                }
            } else {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage("调查问卷不存在，请重试");
            }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity delete(@RequestBody QuestionInfo questionInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        questionInfoService.deleteById(questionInfo.getQuestionId());
        return httpResponseEntity;
    }

    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public HttpResponseEntity image(@RequestParam("body") String id, @RequestParam("file") MultipartFile file) throws IOException {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        QuestionInfo questionInfo = questionInfoService.getById(id);
        if (questionInfo != null) {
            questionInfo.setImage(Base64Utils.encodeToString(file.getBytes()));
            questionInfoService.updateQuestionInfo(questionInfo);
        }
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        return httpResponseEntity;
    }

}
