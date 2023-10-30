package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.QuestionBank;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 题库表 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-11-17
 */
@Repository
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {
    String doImport(MultipartFile param);
}
