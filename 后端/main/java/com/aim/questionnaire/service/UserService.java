package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.common.utils.EasyExcelUtil;
import com.aim.questionnaire.common.utils.MD5Utils;
import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.entity.UserEntity;
import com.alibaba.excel.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wln on 2018\8\9 0009.
 */
@Service
public class UserService {

    @Autowired
    private UserEntityMapper userEntityMapper;

    /**
     * 查询用户列表（模糊搜索）
     *
     * @return
     */
    public List<UserEntity> queryUserList(Map<String, Object> map) {
        return userEntityMapper.queryUserList(map);

    }

    public UserEntity getUserByUserName(String userName) {
        UserEntity user = new UserEntity();
        user.setUsername(userName);
        List<UserEntity> list = userEntityMapper.selectUserInfo(user);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;

    }

    public UserEntity getUserByTelephone(String telephone) {
        return userEntityMapper.getUserByTelephone(telephone);

    }

    /**
     * 创建用户的基本信息
     *
     * @param userEntity
     * @return
     */
    public int addUserInfo(UserEntity userEntity) {
        if (StringUtils.isNotBlank(userEntity.getUsername())) {
            UserEntity userResult = userEntityMapper.queryExistUser(userEntity.getUsername());
            if (userResult != null) {
                //用户名已经存在
                return 3;
            }
        }

        String id = UUIDUtil.getOneUUID();
        userEntity.setId(id);
        //创建时间
        Date date = DateUtil.getCreateTime();
        userEntity.setCreationDate(date);
        userEntity.setLastUpdateDate(date);
        //创建人
        String user = MDC.get("username");
        userEntity.setCreatedBy(user);
        userEntity.setLastUpdatedBy(user);

        userEntity.setStatus("01");

        int result = userEntityMapper.insert(userEntity);
        return result;
    }

    /**
     * 编辑用户的基本信息
     *
     * @param userEntity
     * @return
     */
    public int modifyUserInfo(UserEntity userEntity) {
        return userEntityMapper.updateByPrimaryKeySelective(userEntity);
    }

    /**
     * 修改用户状态
     *
     * @return
     */
    public int modifyUserStatus(Map<String, Object> map) {
        if (map.containsKey("status")) {
            UserEntity user = userEntityMapper.selectByPrimaryKey(((String) map.getOrDefault("userId", "")));
            if (user != null) {
                user.setStatus(((String) map.get("status")));
                return userEntityMapper.updateByPrimaryKey(user);
            }
        }

        return 0;
    }

    /**
     * 根据id查询用户信息
     *
     * @param userEntity
     * @return
     */
    public Map<String, Object> selectUserInfoById(UserEntity userEntity) {

        return null;
    }

    /**
     * 删除用户信息
     *
     * @param userEntity
     * @return
     */
    public int deteleUserInfoById(UserEntity userEntity) {

        return 0;
    }

    public void deteleUser(UserEntity userEntity) {
        userEntity.setStatus("00");
        userEntityMapper.updateByPrimaryKey(userEntity);
    }

    public void invalidUser(UserEntity userEntity) {
        userEntity.setStatus("02");
        userEntityMapper.updateByPrimaryKey(userEntity);
    }

    public void resetPwd(UserEntity userEntity) {
        UserEntity user = new UserEntity();
        user.setId(userEntity.getId());
        user.setPassword(MD5Utils.getMD5Code(StringUtils.defaultString(userEntity.getPassword(),"111111")));
        userEntityMapper.updateByPrimaryKeySelective(user);
    }

    public void updatePwd(UserEntity userEntity) {
        UserEntity user = new UserEntity();
        user.setId(userEntity.getId());
        user.setPassword(userEntity.getPassword());
        userEntityMapper.updateByPrimaryKey(user);
    }

    public void batchUpload(File file) {
        Map<String, List<Map<String, Object>>> datas = EasyExcelUtil.getMultiSheetsDataList(file);
        // 第一个sheet是学生，第二个是老师
        int index = 0;
        for (List<Map<String, Object>> value : datas.values()) {
            if (value == null || value.size() == 0) {
                continue;
            }
            for (Map<String, Object> stringObjectMap : value) {
                UserEntity userEntity = new UserEntity();
                userEntity.setId(UUIDUtil.getOneUUID());
                userEntity.setStatus("01");
                userEntity.setUsername((String) stringObjectMap.get("账号"));
                userEntity.setReaalName((String) stringObjectMap.get("账号"));
                userEntity.setGender((String) stringObjectMap.get("性别"));
                userEntity.setTelephone((String) stringObjectMap.get("手机号"));
                userEntity.setEmail((String) stringObjectMap.get("邮箱"));
                userEntity.setPassword(MD5Utils.getMD5Code("11111111"));
                userEntity.setRoleId("user");
                userEntityMapper.insert(userEntity);
            }


        }
    }
}
