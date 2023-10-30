package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.dao.AdminMapper;
import com.aim.questionnaire.dao.RespondentMapper;
import com.aim.questionnaire.dao.TenantMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.entity.Admin;
import com.aim.questionnaire.entity.PwdVerification;
import com.aim.questionnaire.entity.Respondent;
import com.aim.questionnaire.entity.Tenant;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.entity.UserOnlineBo;
import com.aim.questionnaire.exception.CommonJsonException;
import com.aim.questionnaire.service.PwdVerificationService;
import com.aim.questionnaire.service.TokenService;
import com.aim.questionnaire.service.UserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by wln on 2018\8\9 0009.
 */
@RestController
@RequestMapping("/admin")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserEntityMapper userEntityMapper;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RespondentMapper respondentMapper;
    @Autowired
    private TenantMapper tenantMapper;

    /**
     * 用户登录
     *
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity userLogin(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        switch (userEntity.getRoleId()){
            case "user":
                UserEntity userEntity1 = userEntityMapper.login(userEntity.getUsername(),userEntity.getPassword());
                System.out.println("--------------------------look----------------------!"+userEntity.getUsername()+"-------"+userEntity.getPassword());
                if(userEntity1 != null){
                    //账号密码正确且有权限
                    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                    UserOnlineBo bo = new UserOnlineBo(userEntity);
                    bo.setRoleId("user");
                    bo.setSessionId(tokenService.generateToken(bo.getUsername(),"user"));
                    httpResponseEntity.setData(bo);
                    httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
                    return httpResponseEntity;
                }
                break;
            case "admin":
                System.out.println("-------------------");
                Admin admin = new LambdaQueryChainWrapper<Admin>(adminMapper)
                        .eq(Admin::getUsername,userEntity.getUsername())
                        .eq(Admin::getPassword,userEntity.getPassword())
                        .one();
                if(admin != null){
                    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                    UserOnlineBo bo = new UserOnlineBo(admin.getUsername(),"admin");
                    bo.setSessionId(tokenService.generateToken(admin.getUsername(),"admin"));
                    httpResponseEntity.setData(bo);
                    httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
                    return httpResponseEntity;
                }
                break;
            case "anwserer":
                Respondent respondent = new LambdaQueryChainWrapper<Respondent>(respondentMapper)
                    .eq(Respondent::getUsername,userEntity.getUsername())
                    .eq(Respondent::getPassword,userEntity.getPassword())
                    .one();
                if(respondent != null){
                    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                    UserOnlineBo bo = new UserOnlineBo(respondent.getUsername(),"anwserer");
                    bo.setSessionId(tokenService.generateToken(respondent.getUsername(),"anwserer"));
                    httpResponseEntity.setData(bo);
                    httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
                    httpResponseEntity.setMessage(Constans.LOGOUT_PERMISSION_MESSAGE);
                    return httpResponseEntity;
                }
                break;
            case "tenet":
                Tenant tenant = new LambdaQueryChainWrapper<Tenant>(tenantMapper)
                    .eq(Tenant::getUsername,userEntity.getUsername())
                    .eq(Tenant::getPassword,userEntity.getPassword())
                    .one();
                if(tenant != null){
                    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                    UserOnlineBo bo = new UserOnlineBo(tenant.getUsername(),"tenet");
                    bo.setSessionId(tokenService.generateToken(tenant.getUsername(),"tenet"));
                    httpResponseEntity.setData(bo);
                    httpResponseEntity.setMessage(Constans.LOGIN_MESSAGE);
                    return httpResponseEntity;
                }
                break;
            default:
                break;
        }
        //账号密码不正确
        httpResponseEntity.setCode(Constans.EXIST_CODE);
        httpResponseEntity.setData(null);
        httpResponseEntity.setMessage(Constans.LOGIN_USERNAME_PASSWORD_MESSAGE);
        return httpResponseEntity;
    }

    /**
     * 查询用户列表（模糊搜索）
     *
     * @return
     */
    @RequestMapping(value = "/queryUserList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryUserList(@RequestBody JSONObject map) {
        PageInfo page = map.getObject("page", PageInfo.class);
        PageInfo pageInfo = PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(() -> userService.queryUserList(map.getObject("queryParam", Map.class)));
        pageInfo.getList().stream().forEach(i ->
        {
            UserEntity userEntity = ((UserEntity) i);
            if (userEntity.getStartTime() != null && userEntity.getStopTime() != null && new Date().compareTo(userEntity.getStopTime()) >= 0) {
                userEntity.setStatus("02");
            }
        });
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(pageInfo);
        httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
        return httpResponseEntity;
    }

    /**
     * 创建用户的基本信息
     *
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addUserInfo(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (userEntity.getStartTime() != null && userEntity.getStopTime() != null && userEntity.getStartTime().compareTo(userEntity.getStopTime()) >= 0) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("有效期格式错误，截止日期不能小于开始日期");
            return httpResponseEntity;
        }
        try {
            int result = userService.addUserInfo(userEntity);
            if (result == 3) {
                httpResponseEntity.setCode(Constans.USER_USERNAME_CODE);
                httpResponseEntity.setMessage(Constans.USER_USERNAME_MESSAGE);
            } else {
                httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
            }
        } catch (Exception e) {
            logger.info("addUserInfo 创建用户的基本信息>>>>>>>>>>>" + e.getLocalizedMessage());
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    @Autowired
    private PwdVerificationService pwdVerificationService;

    @RequestMapping(value = "/checkSecurityQuestion", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity checkSecurityQuestion(@RequestBody PwdVerification pwdVerification) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if(pwdVerificationService.checkPwdVerification(pwdVerification)){
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
            return httpResponseEntity;
        }else{
            return HttpResponseEntity.getErrorReponse("");
        }
    }

    @RequestMapping(value = "/savePwdVerify", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity savePwdVerify(@RequestBody PwdVerification pwdVerification) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if(pwdVerificationService.savePwdVerification(pwdVerification) > 0){
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
            return httpResponseEntity;
        }else{
            return HttpResponseEntity.getErrorReponse("");
        }
    }

    /**
     * 编辑用户的基本信息
     *
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/modifyUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUserInfo(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (userEntity.getStartTime() != null && userEntity.getStopTime() != null && userEntity.getStartTime().compareTo(userEntity.getStopTime()) >= 0) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("有效期格式错误，截止日期不能小于开始日期");
            return httpResponseEntity;
        }
        userService.modifyUserInfo(userEntity);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
        return httpResponseEntity;
    }

    /**
     * 根据用户id查询用户基本信息
     *
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity selectUserInfoById(@RequestBody(required = false) UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (userEntity == null) {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            try {
                httpResponseEntity.setData(tokenService.getUserInfo());
            } catch (CommonJsonException e) {
                httpResponseEntity.setCode(Constans.EXIST_CODE);
            }
        }
        return httpResponseEntity;
    }

    @RequestMapping(value = "/selectUserInfoByName", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity selectUserInfoByName() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(tokenService.getUserInfo());
        return httpResponseEntity;
    }


    /**
     * 修改用户状态
     *
     * @return
     */
    @RequestMapping(value = "/modifyUserStatus", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyUserStatus(@RequestBody Map<String, Object> map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        userService.modifyUserStatus(map);
        return httpResponseEntity;
    }

    /**
     * 删除用户信息
     * 不做物理删除
     *
     * @param userEntity
     * @return
     */
    @RequestMapping(value = "/deleteUserInfoById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deteleUserInfoById(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        userService.deteleUser(userEntity);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity resetPwd(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        httpResponseEntity.setCode("666");
        userService.resetPwd(userEntity);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity updatePwd(@RequestBody Map map) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        String telephone = (String)map.get("telephone");
        UserEntity userEntity = userEntityMapper.selectByTelephone(telephone);
        if (userEntity == null) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("没有查到用户信息");
            return httpResponseEntity;
        }
        String newPwd = (String) map.get("newPwd");
        userEntity.setPassword(newPwd);
        userService.updatePwd(userEntity);
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        return httpResponseEntity;
    }

    @RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
    public HttpResponseEntity batchUpload(@RequestParam MultipartFile file) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (httpResponseEntity == null) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("没有文件上传");
            return httpResponseEntity;
        }
        String s2 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        File curFile = new File(s2 + "/" + file.getOriginalFilename());
        try {
            file.transferTo(curFile);
        } catch (IOException e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("文件上传失败");
            return httpResponseEntity;
        }
        userService.batchUpload(curFile);
        httpResponseEntity.setCode("666");
        return httpResponseEntity;
    }


    /**
     * 用户没有权限
     *
     * @return
     */
    @RequestMapping(value = "/error")
    public HttpResponseEntity logout() {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        return httpResponseEntity;
    }
}
