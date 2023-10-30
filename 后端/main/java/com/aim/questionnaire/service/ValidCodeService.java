package com.aim.questionnaire.service;

import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.common.utils.DateUtil;
import com.aim.questionnaire.dao.ValidCodeMapper;
import com.aim.questionnaire.entity.ValidCode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ValidCodeService extends ServiceImpl<ValidCodeMapper,ValidCode> {
    @Autowired
    ValidCodeMapper validCodeMapper;

    // 推送登录验证码消息
    public void excuteLoginSms(ValidCode mvc) {
        String pnumber, validCode, validType;
        pnumber = mvc.getPhoneNumber();
        validCode = mvc.getValidCode();
        validType = mvc.getCodeType();
        String msg = packValidCodeMsg(validType, validCode);
        boolean suc = true;
//        suc =  createAndSendSmsMsg(null, pnumber, msg);

        if (suc) {
            mvc.setCurrentStatus(Constans.validCodeStatus_1);
            mvc.setSendTime(new Date());
        } else {
            mvc.setCurrentStatus(Constans.validCodeStatus_3);
        }
        updateById(mvc);
    }

    public String packValidCodeMsg(String validType,String validCode){
        String content= "";
        if ("01".equals(validType)) { // 注册
            content += "注册验证码：" + validCode + "，请完成验证，如非本人操作，请忽略本短信。";
        } else if ("02".equals(validType)) { // 登录
            content += "登录验证码：" + validCode + "。工作人员不会向您索要，请勿向任何人泄露。";
        } else if ("03".equals(validType)) { // 支付
            content += "支付验证码：" + validCode + "，请勿向任何人泄露。";
        } else {
            content += "本次验证码：" + validCode + "。如非本人操作，请忽略本短信。";
        }
        return content;
    }

    /**
     * 获取验证码记录
     * @param number    手机号
     * @param code	          验证码
     * @return
     */
    public ValidCode getValidCode(String number, String code) {
        if (StringUtils.isBlank(number)) {
            return null;
        }
        return validCodeMapper.getValidCode(number, code);
    }

    /**
     * 获取手机号的最近一条验证码
     * @param number
     * @return
     */
    public ValidCode getValidCode(String number) {
        return getValidCode(number, null);
    }

    /**
     * 验证手机码:
     * @param number
     * @param code
     * @return  true|验证通过   false|验证不通过
     */
    public boolean validMobileCode(String number, String code){
        // 查询验证码记录
        ValidCode mvc = getValidCode(number, code);
        if (mvc != null) {
            // 校验验证码是否已经使用过
            if (Constans.validCodeStatus_2.equals(mvc.getCurrentStatus()) || Constans.validCodeStatus_5.equals(mvc.getCurrentStatus())) {

                return false;
            }
            // 三分钟以内必须验证
            if (DateUtil.compare(mvc.getDeadLine(), new Date()) < 0 || DateUtils.addMinutes(mvc.getCreateTime(), 3).before(new Date())) {

                mvc.setCurrentStatus(Constans.validCodeStatus_5);
                updateById(mvc);
                return false;
            }
            mvc.setCurrentStatus(Constans.validCodeStatus_2);
            updateById(mvc);
            return true;
        }
        return false;
    }

    /**
     * 当天用户输入验证码错误次数超过三次，就使之失效
     * @param number   手机号
     * @param number   验证码
     * @return
     */
    public boolean disableValidCode(String number, String code) {
        if (!validMobileCode(number, code)) {
            return false;
        }
        return true;
    }

    public ValidCode getCreateValidCode(String telephone){
        return validCodeMapper.getCreateValidCode(telephone);
    }
}
