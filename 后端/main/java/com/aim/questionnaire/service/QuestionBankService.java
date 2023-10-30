package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.entity.QuestionBank;
import com.aim.questionnaire.dao.QuestionBankMapper;
import com.aim.questionnaire.entity.QuestionInfo;
import com.aim.questionnaire.entity.QuestionOptions;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * <p>
 *   题库表服务类
 * </p>
 *
 * @author 作者
 * @since 2022-11-17
 */

@Service
public class QuestionBankService extends ServiceImpl<QuestionBankMapper,QuestionBank>{



    @Autowired
    QuestionBankMapper questionBankMapper;
    @Autowired
    QuestionOptionsService questionOptionsService;

    public void saveBank(QuestionBank questionBank) {
        if(StringUtils.isNotBlank(questionBank.getQuestionId())){
            questionBankMapper.updateById(questionBank);
        }else{
            questionBank.setQuestionId(UUIDUtil.getOneUUID());
            questionBankMapper.insert(questionBank);
        }
        questionOptionsService.deleteQuestionOptions(questionBank.getQuestionId());
        if (CollectionUtils.isNotEmpty(questionBank.options)) {
            for (QuestionOptions option : questionBank.options) {
                option.setQuestionId(questionBank.getQuestionId());
                questionOptionsService.insert(option);
            }
        }
    }



}
