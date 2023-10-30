package com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.entity.Group;
import com.aim.questionnaire.entity.Tenant;
import com.aim.questionnaire.service.GroupService;
import com.aim.questionnaire.service.TokenService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-11-09
 */
@RestController
@RequestMapping("/group")
public class GroupController {
  @Autowired
  GroupService groupService;

  @RequestMapping(value = "/query", method = RequestMethod.POST, headers = "Accept=application/json")
  public HttpResponseEntity queryUserList(@RequestBody JSONObject map) {
    PageInfo page = map.getObject("page", PageInfo.class);
    PageInfo pageInfo = PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(() -> groupService.queryGroups(map.getObject("queryParam", Map.class),tokenService.getUserInfo().getUsername()));
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
    httpResponseEntity.setData(pageInfo);
    httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
    return httpResponseEntity;
  }


  @Autowired
  private TokenService tokenService;
  @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
  public HttpResponseEntity addUserInfo(@RequestBody Group group) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    try {
      group.setUserId(tokenService.getUserInfo().getUsername());
      groupService.getBaseMapper().insert(group);
      httpResponseEntity.setCode(Constans.SUCCESS_CODE);
      httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
    } catch (Exception e) {
      httpResponseEntity.setCode(Constans.EXIST_CODE);
      httpResponseEntity.setMessage(Constans.EXIST_MESSAGE);
    }
    return httpResponseEntity;
  }

  /**
   * 编辑用户的基本信息
   *
   * @param map
   * @return
   */
  @RequestMapping(value = "/update", method = RequestMethod.POST, headers = "Accept=application/json")
  public HttpResponseEntity modifyUserInfo(@RequestBody Group tenant) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    groupService.getBaseMapper().updateById(tenant);
    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
    httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
    return httpResponseEntity;
  }

}
