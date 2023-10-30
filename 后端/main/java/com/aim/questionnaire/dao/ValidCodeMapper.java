package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.ValidCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 手机验证码表 Mapper 接口
 * </p>
 *
 * @author 作者
 * @since 2022-11-01
 */
@Repository
public interface ValidCodeMapper extends BaseMapper<ValidCode> {
    ValidCode getValidCode(@Param("telephone") String telephone,@Param("validCode")String validCode);

    ValidCode getCreateValidCode(@Param("telephone") String telephone);
}
