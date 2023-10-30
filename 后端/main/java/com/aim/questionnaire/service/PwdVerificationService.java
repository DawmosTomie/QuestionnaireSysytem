package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.entity.PwdVerification;
import com.aim.questionnaire.dao.PwdVerificationMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * <p>
 *   服务类
 * </p>
 *
 * @author 作者
 * @since 2022-11-01
 */

@Service
public class PwdVerificationService extends ServiceImpl<PwdVerificationMapper,PwdVerification>{
        @Autowired
        PwdVerificationMapper pwdVerificationMapper;

        public boolean checkPwdVerification(PwdVerification pwdVerification) {
                return new LambdaQueryChainWrapper<>(pwdVerificationMapper)
                        .eq(PwdVerification::getUsername,pwdVerification.getUsername())
                        .eq(PwdVerification::getProblemId,pwdVerification.getProblemId())
                        .eq(PwdVerification::getAnswer,pwdVerification.getAnswer())
                        .eq(PwdVerification::getRoleId,pwdVerification.getRoleId())
                        .one() != null;
        }

        public int savePwdVerification(PwdVerification pwdVerification){
                pwdVerification.setId(UUIDUtil.getOneUUID());
                return pwdVerificationMapper.insert(pwdVerification);
        }
}
