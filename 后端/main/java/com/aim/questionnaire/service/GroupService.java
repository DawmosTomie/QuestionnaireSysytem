package com.aim.questionnaire.service;

import com.aim.questionnaire.entity.Group;
import com.aim.questionnaire.dao.GroupMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * <p>
 *   服务类
 * </p>
 *
 * @author 作者
 * @since 2022-11-09
 */

@Service
public class GroupService extends ServiceImpl<GroupMapper,Group>{

        @Autowired
        GroupMapper groupMapper;

        public List<Group> queryGroups(Map queryParam,String username) {
                return groupMapper.queryGroups(queryParam,username);
        }
}
