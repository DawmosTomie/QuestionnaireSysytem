package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.entity.ProjectEntity;
import com.aim.questionnaire.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wln on 2018\8\6 0006.
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    private final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @Autowired
    private ProjectService projectService;


    /**
     * 查询全部项目的信息
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/queryProjectList", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectList(@RequestBody(required = false) ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        ;
        httpResponseEntity.setCode("666");
        httpResponseEntity.setData(projectService.queryProjectList(null));
        return httpResponseEntity;
    }

    /**
     * 根据id删除项目
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/deleteProjectById", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity deleteProjectById(@RequestBody ProjectEntity projectId) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (!projectService.hasPublishedQuestionnaire(projectId.getId())) {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            projectService.deleteProjectById(projectId.getId());
        } else {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("有正在进行中的问卷，无法删除项目");
        }
        return httpResponseEntity;
    }

    /**
     * 添加项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/addProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity addProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        try {
            if (StringUtils.isNotBlank(projectEntity.getProjectName())) {
                ProjectEntity project = projectService.getProjectByName(projectEntity.getProjectName());
                if (project == null) {
                    projectService.addProjectInfo(projectEntity);
                    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
                } else {
                    httpResponseEntity.setCode(Constans.EXIST_MESSAGE);
                    httpResponseEntity.setMessage("已存在同名项目，请修改项目名称");
                }
            } else {
                httpResponseEntity.setCode(Constans.EXIST_MESSAGE);
                httpResponseEntity.setMessage("项目名称不能为空");
            }
        } catch (Exception e) {
            logger.info("addProjectInfo :", e);
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
        }
        return httpResponseEntity;
    }

    /**
     * 根据项目id修改项目
     *
     * @param projectEntity
     * @return
     */
    @RequestMapping(value = "/modifyProjectInfo", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity modifyProjectInfo(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
        if (!projectService.hasPublishedQuestionnaire(projectEntity.getId())) {
            httpResponseEntity.setCode(Constans.SUCCESS_CODE);
            projectService.modifyProjectInfo(projectEntity);
        } else {
            httpResponseEntity.setCode(Constans.EXIST_CODE);
            httpResponseEntity.setMessage("有正在进行中的问卷，无法删除项目");
        }
        return httpResponseEntity;
    }


    /**
     * 查询全部项目的名字接口
     *
     * @return
     */
    @RequestMapping(value = "/queryProjectName", method = RequestMethod.POST, headers = "Accept=application/json")
    public HttpResponseEntity queryProjectName(@RequestBody ProjectEntity projectEntity) {
        HttpResponseEntity httpResponseEntity = new HttpResponseEntity();

                ProjectEntity project = projectService.getProjectByName(projectEntity.getProjectName());
                httpResponseEntity.setCode("666");
            httpResponseEntity.setData(project);
        return httpResponseEntity;}


}
