package  com.aim.questionnaire.entity;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-11-09
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员用户名
     */
    private String username;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 标明角色
     */
    private String roleName;

    /**
     * 管理员性别
     */
    private String sex;

    /**
     * 管理员手机号
     */
    private Integer telephone;

    /**
     * 管理员姓名
     */
    private String realname;


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

    public Integer getTelephone() {
        return telephone;
    }

    public void setTelephone(Integer telephone) {
        this.telephone = telephone;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    @Override
    public String toString() {
        return "Admin{" +
        "username=" + username +
        ", password=" + password +
        ", roleName=" + roleName +
        ", sex=" + sex +
        ", telephone=" + telephone +
        ", realname=" + realname +
        "}";
    }
}
