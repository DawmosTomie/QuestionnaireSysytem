package com.aim.questionnaire.dao;

import  com.aim.questionnaire.entity.Respondent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-11-09
 */
@Repository
public interface RespondentMapper extends BaseMapper<Respondent> {

  List<Respondent> queryRespondents(Map queryParam);
}
