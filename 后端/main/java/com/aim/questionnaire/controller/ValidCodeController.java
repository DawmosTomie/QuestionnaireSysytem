package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.dao.AdminMapper;
import com.aim.questionnaire.dao.RespondentMapper;
import com.aim.questionnaire.dao.TenantMapper;
import com.aim.questionnaire.dao.UserEntityMapper;
import com.aim.questionnaire.entity.Admin;
import com.aim.questionnaire.entity.Respondent;
import com.aim.questionnaire.entity.Tenant;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.entity.UserOnlineBo;
import com.aim.questionnaire.entity.ValidCode;
import com.aim.questionnaire.service.TokenService;
import com.aim.questionnaire.service.UserService;
import com.aim.questionnaire.service.ValidCodeService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.text.ParseException;
import java.util.Calendar;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/validCode")
public class ValidCodeController {
    @Autowired
    private ValidCodeService validCodeService;
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

    @RequestMapping(value = "/send", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity sendValid(@RequestBody UserEntity userEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

        String telephone = userEntity.getTelephone();
        if (StringUtils.isBlank(telephone)) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("手机号为空");
            return httpResponseEntity;
        }
        try {
            Date now = new Date();
            ValidCode validCheck = validCodeService.getValidCode(telephone);
            if (validCheck != null && !Constans.validCodeStatus_2.equals(validCheck.getCurrentStatus()) && !Constans.validCodeStatus_5.equals(validCheck.getCurrentStatus())) {
                if (DateUtil.dateAdd(validCheck.getCreateTime(), Calendar.MINUTE, 1).after(now)) {
                    httpResponseEntity.setCode(Constans.EXIST_CODE);
                    httpResponseEntity.setData(null);
                    httpResponseEntity.setMessage("1分钟内已发送过短信，无需重复点击");
                    return httpResponseEntity;
                }
            }
            ValidCode vc = validCodeService.getCreateValidCode(telephone);
            int callNum = 0;   // 20分钟内发送次数
            int todayNum = 0;  // 当天发送次数
            if (vc != null) {
                    // 20分钟之内发送次数超过五次，提示：您短时间内验证码" + typeValue + "已达上限，请20分钟之后再试
                    if (vc.getCallNum() != null && DateUtils.addMinutes(vc.getCreateTime(), 20).after(now)) {
                        callNum = vc.getCallNum();
                        if (callNum >= 8) {
                            httpResponseEntity.setCode(Constans.EXIST_CODE);
                            httpResponseEntity.setData(null);
                            httpResponseEntity.setMessage("您短时间内验证码已达上限，请20分钟之后重试");
                            return httpResponseEntity;
                        }
                        callNum++;
                    } else { // 超过20分钟，发送次数重新开始
                        callNum = 1;
                    }

                    // 获取当天发送总条数
                    if (vc.getTodayNum() != null) {
                        todayNum = vc.getTodayNum();
                        if (todayNum >= 20) {
                            httpResponseEntity.setCode(Constans.EXIST_CODE);
                            httpResponseEntity.setData(null);
                            httpResponseEntity.setMessage("您今日发送验证码次数已达到上限，请明天再试");
                            return httpResponseEntity;
                        }
                        todayNum++;
                    } else {
                        todayNum = 1;
                    }

                // 如果前一次验证码发送还在有效期内，在下次能够继续发送短信的时候，把前一次的验证码置为失效
                if (!Constans.validCodeStatus_2.equals(vc.getCurrentStatus()) && !Constans.validCodeStatus_5.equals(vc.getCurrentStatus())) { // 上一条短信未验证
                    if (DateUtils.addMinutes(vc.getCreateTime(), 1).after(now)) {
                        httpResponseEntity.setCode(Constans.EXIST_CODE);
                        httpResponseEntity.setData(null);
                        httpResponseEntity.setMessage("1分钟内已发送过短信，无需重复点击");
                        return httpResponseEntity;
                    }
                    vc.setCurrentStatus(Constans.validCodeStatus_5);
                    validCodeService.updateById(vc);
                }
            } else {
                callNum = 1;
                todayNum = 1;
            }

            ValidCode mvc = new ValidCode();
            mvc.setPhoneNumber(telephone);
            mvc.setCreateTime(now);
            mvc.setCurrentStatus(Constans.validCodeStatus_0);
            mvc.setDeadLine(DateUtils.addMinutes(now, 20));
            mvc.setValidCode(RandomStringUtils.randomNumeric(6));
            mvc.setSerialNo(RandomStringUtils.randomAlphanumeric(6));
            mvc.setCallNum(callNum);
            mvc.setTodayNum(todayNum);
            validCodeService.getBaseMapper().insert(mvc);
        } catch (Exception e) {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setData(null);
            httpResponseEntity.setMessage("验证码发送失败,请稍后重试");
            return httpResponseEntity;
        }
        httpResponseEntity.setCode(Constans.SUCCESS_CODE);
        httpResponseEntity.setData(null);
        return httpResponseEntity;
    }

    /**
     * 验证验证码
     * @author lihd
     * */
    @RequestMapping(value = "/check", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity checkValid(@RequestBody UserEntity userEntity) throws ParseException {
        String telephone = userEntity.getTelephone();
        String validCode = userEntity.getPassword();
        if (StringUtils.isBlank(telephone)) {
            return HttpResponseEntity.getErrorReponse("电话号码为空");
        }
        if (StringUtils.isBlank(validCode)) {
            return HttpResponseEntity.getErrorReponse("验证码为空");
        }
        if (validCodeService.disableValidCode(telephone, validCode)) {
            HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
            switch (userEntity.getRoleId()){
                case "user":
                    UserEntity userEntity1 = userEntityMapper.login(userEntity.getUsername(),userEntity.getPassword());
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

        }
        return HttpResponseEntity.getErrorReponse("验证码错误，请重试");

    }
}
