package com.aim.questionnaire.dao;

import com.aim.questionnaire.entity.Group;
import com.aim.questionnaire.entity.Tenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface GroupMapper extends BaseMapper<Group> {

  List<Group> queryGroups(@Param("queryParam") Map queryParam,@Param("usernmae") String username);
}
