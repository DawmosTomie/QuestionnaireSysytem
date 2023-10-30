package com.aim.questionnaire.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 题库表
 * </p>
 *
 * @author 作者
 * @since 2022-11-17
 */
@TableName("question_bank")
public class QuestionBank implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    @ExcelProperty(value = "questionId", index = 0)
    private String questionId;
    @ExcelProperty(value = "questionTitle", index = 1)
    private String questionTitle;
    @ExcelProperty(value = "type", index = 2)
    private String type;
    @ExcelProperty(value = "status", index = 3)
    private Integer status;
    @ExcelProperty(value = "isMust", index = 4)
    private Integer isMust;
    @ExcelProperty(value = "image", index = 5)
    private String image;

    @TableField(exist = false)
    @ExcelProperty(value = "options", index = 6)
    public List<QuestionOptions> options;


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
        "questionId=" + questionId +
        ", questionTitle=" + questionTitle +
        ", type=" + type +
        ", status=" + status +
        ", isMust=" + isMust +
        ", image=" + image +
        "}";
    }
}
