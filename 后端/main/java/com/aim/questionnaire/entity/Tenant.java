package  com.aim.questionnaire.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-11-09
 */
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 租户用户名
     */
    private String username;

    /**
     * 租户密码
     */
    private String password;

    /**
     * 标明角色
     */
    private String roleName;

    /**
     * 租户性别
     */
    private String sex;

    /**
     * 租户电话
     */
    private String telephone;

    /**
     * 租户是否具备登录权限
     */
    private Integer loginEnable;

    /**
     * 租户剩余群组数量
     */
    private Integer groupNum;

    /**
     * 租户剩余问卷数量
     */
    private Integer questionnaireNum;

    /**
     * 租户剩余答卷数量
     */
    private Integer answerNum;

    /**
     * 租户余额
     */
    private String balance;

    /**
     * 租户姓名
     */
    private String realname;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getLoginEnable() {
        return loginEnable;
    }

    public void setLoginEnable(Integer loginEnable) {
        this.loginEnable = loginEnable;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Integer getQuestionnaireNum() {
        return questionnaireNum;
    }

    public void setQuestionnaireNum(Integer questionnaireNum) {
        this.questionnaireNum = questionnaireNum;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Override
    public String toString() {
        return "Tenant{" +
        "id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", roleName=" + roleName +
        ", sex=" + sex +
        ", telephone=" + telephone +
        ", loginEnable=" + loginEnable +
        ", groupNum=" + groupNum +
        ", questionnaire Num=" + questionnaireNum +
        ", answerNum=" + answerNum +
        ", balance=" + balance +
        ", realname=" + realname +
        "}";
    }
}
