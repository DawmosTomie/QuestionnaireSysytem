package com.aim.questionnaire.dao;

import  com.aim.questionnaire.entity.Tenant;
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
public interface TenantMapper extends BaseMapper<Tenant> {

  List<Tenant> queryTenants(Map queryParam);
}
