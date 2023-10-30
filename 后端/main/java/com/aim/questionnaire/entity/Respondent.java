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
public class Respondent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 答者id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 答者用户名
     */
    private String username;

    /**
     * 答者真实姓名
     */
    private String realname;

    /**
     * 答者密码
     */
    private String password;

    /**
     * 角色标注
     */
    private String roleName;

    /**
     * 答者性别
     */
    private String sex;

    /**
     * 答者电话
     */
    private String telephone;

    /**
     * 答者隶属的父级id
     */
    private String parentId;

    /**
     * 答者隶属的群组id
     */
    private Integer groupId;


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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "Respondent{" +
        "id=" + id +
        ", username=" + username +
        ", realname=" + realname +
        ", password=" + password +
        ", roleName=" + roleName +
        ", sex=" + sex +
        ", telephone=" + telephone +
        ", parentId=" + parentId +
        ", groupId=" + groupId +
        "}";
    }
}
