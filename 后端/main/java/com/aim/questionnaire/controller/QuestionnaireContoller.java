package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.entity.QuestionInfo;
import com.aim.questionnaire.entity.QuestionnaireInfo;
import com.aim.questionnaire.entity.QuestionnaireShare;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.entity.po.PageInfoObject;
import com.aim.questionnaire.entity.vo.AnwserViewVo;
import com.aim.questionnaire.entity.vo.ShareLnkVo;
import com.aim.questionnaire.service.*;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireContoller {
    @Autowired
    private QuestionnaireInfoService questionnaireInfoService;
    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/get", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity get(@RequestBody QuestionnaireInfo questionnaireInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        QuestionnaireInfo questionnaireInfo1 = questionnaireInfoService.getQuestionnaireInfoById(questionnaireInfo.getQuestionnaireId());
        httpResponseEntity.setData(questionnaireInfo1);
        return httpResponseEntity;
    }


    @RequestMapping(value = "/all", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity all(@RequestBody PageInfoObject pageInfoObject) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        PageInfo page = PageHelper.startPage(pageInfoObject.getPage().getPageNum(), pageInfoObject.getPage().getPageSize()).doSelectPageInfo(() -> questionnaireInfoService.queryAllQuestionnaires(pageInfoObject.getQueryParam()));
        httpResponseEntity.setData(page);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity add(@RequestBody QuestionnaireInfo questionnaireInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (questionnaireInfo.getStartTime() != null && questionnaireInfo.getEndTime() != null && questionnaireInfo.getStartTime().compareTo(questionnaireInfo.getEndTime()) >= 0) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("有效期格式错误，截止日期不能小于开始日期");
            return httpResponseEntity;
        }
        httpResponseEntity.setCode("666");
        httpResponseEntity.setData(questionnaireInfoService.add(questionnaireInfo));
        //questionInfoService.deleteAllQuetions(questionnaireInfo.getQuestionnaireId());
        if(CollectionUtils.isNotEmpty(questionnaireInfo.getQuestions())){
            for (QuestionInfo question : questionnaireInfo.getQuestions()) {
              question.setQuestionnaireId(questionnaireInfo.getQuestionnaireId());
              questionInfoService.save(question);
            }
        }

        return httpResponseEntity;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity update(@RequestBody QuestionnaireInfo questionnaireInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (questionnaireInfo.getStartTime() != null && questionnaireInfo.getEndTime() != null && questionnaireInfo.getStartTime().compareTo(questionnaireInfo.getEndTime()) >= 0) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("有效期格式错误，截止日期不能小于开始日期");
            return httpResponseEntity;
        }
        QuestionnaireInfo questionnaireInfoData = questionnaireInfoService.getQuestionnaireInfoById(questionnaireInfo.getQuestionnaireId());
        if (questionnaireInfoData == null) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("问卷不存在");
        } else {
            if (questionnaireInfoData.getStatus() == 1) {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage("问卷进行中，无法修改");
            } else {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                questionnaireInfoService.updateQustionnaire(questionnaireInfo);
            }
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity delete(@RequestBody QuestionnaireInfo questionnaireInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        QuestionnaireInfo questionnaireInfoData = questionnaireInfoService.getQuestionnaireInfoById(questionnaireInfo.getQuestionnaireId());
        if (questionnaireInfoData == null) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("问卷不存在");
        } else {
            if (questionnaireInfoData.getStatus() == 1) {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage("问卷进行中，无法删除");
            } else {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                questionnaireInfoData.setStatus(3);
                questionnaireInfoService.updateQustionnaire(questionnaireInfoData);
            }
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/queryQuestions", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryQuestions(@RequestBody Map questionnaire) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String questionnaireId = (String) questionnaire.get("questionnaireId");
        httpResponseEntity.setCode("666");
        httpResponseEntity.setData((questionnaireId));
        httpResponseEntity.setData(questionnaireInfoService.queryQuestions(questionnaireId));
        return httpResponseEntity;
    }


    @RequestMapping(value = "/answerView", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity answerView(@RequestBody Map questionnaireId) {

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        System.out.println("------------------------------"+questionnaireId.get(questionnaireId));
        String id= (String) questionnaireId.get("questionnaireId");
        if(questionnaireId.get("questionnaireId")!=null){
            QuestionnaireInfo questionnaireInfo=questionnaireInfoService.getQuestionnaireInfoById(id);
            List<QuestionInfo> questionInfo=questionnaireInfoService.queryQuestions(id);
            questionnaireInfo.setQuestions(questionInfo);
            httpResponseEntity.setData(questionnaireInfo);}
        httpResponseEntity.setCode("666");
        return httpResponseEntity;
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity publish(@RequestBody QuestionnaireInfo questionnaireInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (questionnaireInfo.getStatus() == 0) {
            QuestionnaireShare questionnaireShare = questionnaireInfoService.getQuestionnaireShare(questionnaireInfo.getQuestionnaireId());
            if (questionnaireShare != null) {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage("问卷已分享过，无法暂停");
                return httpResponseEntity;
            }
        }
        httpResponseEntity.setCode("666");
        questionnaireInfoService.updateQustionnaire(questionnaireInfo);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/anwser", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity anwser(@RequestBody AnwserViewVo anwserViewVo, HttpServletRequest request) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        String userId = MDC.get("username");
        questionnaireInfoService.anwser(anwserViewVo, getIpAddr(request), userId);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/analysis", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity analysis(@RequestBody Map questionnaireId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        httpResponseEntity.setData(questionnaireInfoService.analysis(((String) questionnaireId.get("questionnaireId"))));
        return httpResponseEntity;
    }


    @RequestMapping(value = "/templates", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity templates(@RequestBody PageInfo pageInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        httpResponseEntity.setData(new PageInfo<>(questionnaireInfoService.queryAllTemplates(pageInfo)));
        return httpResponseEntity;
    }

    @RequestMapping(value = "/useTemplate", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity useTemplate(@RequestBody QuestionnaireInfo questionnaireInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        QuestionnaireInfo questionnaireInfo1 = questionnaireInfoService.getQuestionnaireInfoById(questionnaireInfo.getQuestionnaireId());
        if (questionnaireInfo1 == null) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("模板不存在");
            return httpResponseEntity;
        }
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        questionnaireInfoService.useTemplate(questionnaireInfo);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/queryTemplateSurveyCrowd", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryTemplateSurveyCrowd(@RequestBody QuestionnaireInfo questionnaireInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(questionnaireInfoService.queryTemplateSurveyCrowd());
        return httpResponseEntity;
    }

    @RequestMapping(value = "/querySurveyCrowd", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity querySurveyCrowd(@RequestBody QuestionnaireInfo questionnaireInfo) {

        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        List<QuestionnaireInfo> questionnaireInfos = questionnaireInfoService.queryRecommend(questionnaireInfo.getSurveyCrowd());

        httpResponseEntity.setData(questionnaireInfos);
        httpResponseEntity.setCode("666");
        return httpResponseEntity;
    }


    @RequestMapping(value = "/hasPermission", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity hasPermission(@RequestBody Map<String, String> questionnaireId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        QuestionnaireInfo questionnaireInfo = questionnaireInfoService.getQuestionnaireInfoById(questionnaireId.get("questionnaireId"));
        if (questionnaireInfo == null) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("模板不存在");
            return httpResponseEntity;
        } else {
            UserEntity userEntity = tokenService.getUserInfo();
            if (userEntity != null && "1".equals(userEntity.getRoleId())) {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setData(true);
                return httpResponseEntity;
            }
            if (!DateUtil.isNowBetween(questionnaireInfo.getStartTime(), questionnaireInfo.getEndTime())) {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
                httpResponseEntity.setMessage("问卷未生效");
                return httpResponseEntity;
            }
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setData(questionnaireInfoService.hasPermission(questionnaireId.get("questionnaireId"), MDC.get("username")));
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/share", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity share(@RequestBody ShareLnkVo shareInfo) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        QuestionnaireInfo questionnaireInfo = questionnaireInfoService.getQuestionnaireInfoById(shareInfo.getQuestionnaireId());
        if (questionnaireInfo == null) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("问卷不存在");
            return httpResponseEntity;
        }
        if (questionnaireInfo.getStatus() != 1 || !DateUtil.isNowBetween(questionnaireInfo.getStartTime(), questionnaireInfo.getEndTime())) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("问卷未生效，无法分享");
            return httpResponseEntity;
        }
        questionnaireInfoService.createShare(shareInfo);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        return httpResponseEntity;
    }

    @Autowired
    private QuestionCommitService questionCommitService;
    @RequestMapping(value = "/commits", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity commits(@RequestBody PageInfoObject pageInfoObject) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        PageInfo page = PageHelper.startPage(pageInfoObject.getPage().getPageNum(), pageInfoObject.getPage().getPageSize()).doSelectPageInfo(() -> questionCommitService.queryAllCommits(pageInfoObject.getQueryParam()));
        httpResponseEntity.setData(page);
        return httpResponseEntity;
    }

    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

}
