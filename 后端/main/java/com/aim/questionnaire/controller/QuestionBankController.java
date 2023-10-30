package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.ExcelUtils;
import com.aim.questionnaire.entity.QuestionBank;
import com.aim.questionnaire.entity.QuestionInfo;
import com.aim.questionnaire.entity.QuestionnaireInfo;
import com.aim.questionnaire.service.QuestionBankService;
import com.aim.questionnaire.service.QuestionOptionsService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 题库表 前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-11-17
 */
@RestController
@RequestMapping("/qnBank")
public class QuestionBankController {

    @Autowired
    private QuestionBankService questionBankService;
    @Autowired
    private QuestionOptionsService questionOptionsService;

    @RequestMapping(value = "/all", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity all() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        List<QuestionBank> banks = questionBankService.list();
        if(CollectionUtils.isNotEmpty(banks)){
            for (QuestionBank bank : banks) {
                bank.options = questionOptionsService.queryQuestionOptions(bank.getQuestionId());
            }
        }
        httpResponseEntity.setData(banks);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity delete(@RequestBody QuestionBank questionBank) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        questionBankService.getBaseMapper().deleteById(questionBank);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity save(@RequestBody QuestionBank questionBank) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        questionBankService.saveBank(questionBank);

        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(questionBank);
        return httpResponseEntity;
    }


    @RequestMapping(value = "/batchUpload", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity batchUpload(@RequestParam("file") MultipartFile file) throws IOException {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        List<QuestionBank> questionBanks = ExcelUtils.excelToQuestionBank(file);
        // 遍历list,导入数据库。
        for (QuestionBank questionBank : questionBanks) {
            questionBankService.save(questionBank);
        }
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        return httpResponseEntity;
    }

}
