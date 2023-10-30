package com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.UUIDUtil;
import com.aim.questionnaire.dao.ProjectEntityMapper;
import com.aim.questionnaire.entity.ProjectEntity;
import com.aim.questionnaire.entity.QuestionnaireInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wln on 2018\8\6 0006.
 */
@Service
public class ProjectService {

    @Autowired
    private ProjectEntityMapper projectEntityMapper;
    @Autowired
    private QuestionnaireInfoService questionnaireInfoService;


    /**
     * 添加项目
     *
     * @param projectEntity
     * @return
     */
    public int addProjectInfo(ProjectEntity projectEntity) {
        if (StringUtils.isEmpty(projectEntity.getId())) {
            projectEntity.setId(UUIDUtil.getOneUUID());
        }
        projectEntity.setCreatedBy("user");
        projectEntity.setCreationDate(new Date());
        return projectEntityMapper.insert(projectEntity);
    }

    /**
     * 修改项目
     *
     * @param projectEntity
     * @return
     */
    public int modifyProjectInfo(ProjectEntity projectEntity) {
        projectEntity.setLastUpdateDate(new Date());
        projectEntity.setCreatedBy("admin");
        return projectEntityMapper.updateByPrimaryKeySelective(projectEntity);
    }

    /**
     * 删除项目
     *
     * @param projectId
     * @return
     */
    public int deleteProjectById(String projectId) {
        return projectEntityMapper.deleteProjectById(projectId);
    }

    /**
     * 查询项目列表
     *
     * @param projectEntity
     * @return
     */
    public List<ProjectEntity> queryProjectList(ProjectEntity projectEntity) {
        List<ProjectEntity> projects = projectEntityMapper.queryProjectList(null);
        if (CollectionUtils.isNotEmpty(projects)) {
            for (ProjectEntity project : projects) {
                project.setQuestions(questionnaireInfoService.queryProjectQustionnaire(project.getId()));
            }
        }
        return projects;
    }

    /**
     * 查询全部项目的名字接口
     *
     * @return
     */
    public List<Map<String, Object>> queryAllProjectName() {
        return null;
    }

    public ProjectEntity getProjectByName(String projectName) {
        return projectEntityMapper.getProjectByName(projectName);
    }

    public boolean hasPublishedQuestionnaire(String projectId) {
        List<QuestionnaireInfo> questionnaireInfos = questionnaireInfoService.queryProjectQustionnaire(projectId).stream()
                .filter(i -> i.getStatus() == 1).collect(Collectors.toList());
        return CollectionUtils.isNotEmpty(questionnaireInfos);
    }

    public ProjectEntity selectProjectById(String projectId) {
        return projectEntityMapper.selectByPrimaryKey(projectId);
    }
}
