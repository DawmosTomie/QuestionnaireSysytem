package com.aim.questionnaire.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 手机验证码表
 * </p>
 *
 * @author 作者
 * @since 2022-11-01
 */
@TableName("valid_code")
public class ValidCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 验证码
     */
    private String validCode;

    /**
     * 序列号
     */
    private String serialNo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 过期时间
     */
    private Date deadLine;

    private String clientType;

    /**
     * 状态（validCodeStatus）
     */
    private String currentStatus;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 验证码类型（validCodeType）
     */
    private String codeType;

    private String content;

    private String ip;

    /**
     * 20分钟内发送次数
     */
    private Integer callNum;

    /**
     * 当天发送次数
     */
    private Integer todayNum;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 失败次数
     */
    private Integer failureCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getCallNum() {
        return callNum;
    }

    public void setCallNum(Integer callNum) {
        this.callNum = callNum;
    }

    public Integer getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(Integer todayNum) {
        this.todayNum = todayNum;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    @Override
    public String toString() {
        return "ValidCode{" +
        "id=" + id +
        ", phoneNumber=" + phoneNumber +
        ", validCode=" + validCode +
        ", serialNo=" + serialNo +
        ", createTime=" + createTime +
        ", deadLine=" + deadLine +
        ", clientType=" + clientType +
        ", currentStatus=" + currentStatus +
        ", userType=" + userType +
        ", codeType=" + codeType +
        ", content=" + content +
        ", ip=" + ip +
        ", callNum=" + callNum +
        ", todayNum=" + todayNum +
        ", sendTime=" + sendTime +
        ", failureCount=" + failureCount +
        "}";
    }
}
