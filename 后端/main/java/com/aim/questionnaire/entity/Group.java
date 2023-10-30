package com.aim.questionnaire.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 作者
 * @since 2022-11-09
 */
@TableName("cust_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String grpName;

    private String grpDesc;

    private String userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGrpName() {
        return grpName;
    }

    public void setGrpName(String grpGrpName) {
        this.grpName = grpGrpName;
    }

    public String getGrpDesc() {
        return grpDesc;
    }

    public void setGrpDesc(String grpGrpDesc) {
        this.grpDesc = grpGrpDesc;
    }

    public String getUserId() {
        return userId;
    }

    public Group setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "Group{" +
        "id=" + id +
        ", grpGrpName=" + grpName +
        ", grpGrpDesc=" + grpDesc +
        "}";
    }
}
