package com.aim.questionnaire.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-11-01
 */
@TableName("pwd_verification")
public class PwdVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private Integer problemId;

    private String answer;

    private String roleId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRoleId() {
        return roleId;
    }

    public PwdVerification setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public String toString() {
        return "PwdVerification{" +
        "id=" + id +
        ", problemId=" + problemId +
        ", answer=" + answer +
        "}";
    }
}
