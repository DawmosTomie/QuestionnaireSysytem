package com.aim.questionnaire.entity.vo;

import com.aim.questionnaire.entity.QuestionInfo;
import org.springframework.web.multipart.MultipartFile;

public class QuestionImageVo extends QuestionInfo {
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
