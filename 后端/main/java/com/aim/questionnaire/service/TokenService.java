package com.aim.questionnaire.service;

import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.AdminMapper;
import com.aim.questionnaire.dao.RespondentMapper;
import com.aim.questionnaire.dao.TenantMapper;
import com.aim.questionnaire.entity.Admin;
import com.aim.questionnaire.entity.Respondent;
import com.aim.questionnaire.entity.Tenant;
import com.aim.questionnaire.entity.UserOnlineBo;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.exception.CommonJsonException;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.benmanes.caffeine.cache.Cache;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    Cache<String, UserOnlineBo> cacheMap;

    @Autowired
    UserService userService;

    /**
     * 用户登录验证通过后(sso/帐密),生成token,记录用户已登录的状态
     */
    public String generateToken(String username,String roleId) {
        MDC.put("username", username);
        String token = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        //设置用户信息缓存
        setCache(token, username,roleId);
        return token;
    }

    public UserOnlineBo getUserInfo() {
        String token = MDC.get("token");
        return getUserInfoFromCache(token);
    }

    /**
     * 根据token查询用户信息
     * 如果token无效,会抛未登录的异常
     */
    private UserOnlineBo getUserInfoFromCache(String token) {
        if (StringUtils.isBlank(token)) {
            throw new CommonJsonException(Constans.LOGOUT_NO_CODE, Constans.LOGOUT_NO_MESSAGE);
        }
        UserOnlineBo info = cacheMap.getIfPresent(token);
        if (info == null) {
            throw new CommonJsonException(Constans.LOGOUT_NO_CODE, Constans.LOGOUT_NO_MESSAGE);
        }
        return info;
    }

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    private RespondentMapper respondentMapper;
    @Autowired
    private TenantMapper tenantMapper;
    private void setCache(String token, String username,String roleId) {
        UserOnlineBo info = new UserOnlineBo();
        switch (roleId){
            case "user":
                info = getUserInfoByUsername(username);
                break;
            case "admin":
                Admin admin = new LambdaQueryChainWrapper<Admin>(adminMapper)
                    .eq(Admin::getUsername,username)
                    .one();
                if(admin != null){
                    info = new UserOnlineBo(admin.getUsername(),"admin");
                }
                break;
            case "anwserer":
                Respondent respondent = new LambdaQueryChainWrapper<Respondent>(respondentMapper)
                    .eq(Respondent::getUsername,username)
                    .one();
                if(respondent != null){
                    info = new UserOnlineBo(respondent.getUsername(),"anwserer");
                }
                break;
            case "tenet":
                Tenant tenant = new LambdaQueryChainWrapper<Tenant>(tenantMapper)
                    .eq(Tenant::getUsername,username)
                    .one();
                if(tenant != null){
                    info = new UserOnlineBo(tenant.getUsername(),"tenet");
                }
                break;
            default:
                break;
        }
        cacheMap.put(token, info);
    }

    /**
     * 退出登录时,将token置为无效
     */
    public void invalidateToken() {
        String token = MDC.get("token");
        if (!StringUtils.isBlank(token)) {
            cacheMap.invalidate(token);
        }
    }

    private UserOnlineBo getUserInfoByUsername(String username) {
        UserEntity userInfo = userService.getUserByUserName(username);
        return new UserOnlineBo(userInfo);
    }
}