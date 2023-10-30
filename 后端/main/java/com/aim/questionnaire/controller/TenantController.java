package  com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.entity.Tenant;
import com.aim.questionnaire.entity.UserEntity;
import com.aim.questionnaire.entity.UserOnlineBo;
import com.aim.questionnaire.service.TenantService;
import com.aim.questionnaire.service.TokenService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
@RequestMapping("/tenant")
public class TenantController {
  @Autowired
  private TenantService tenantService;

  /**
   * 查询用户列表（模糊搜索）
   *
   * @param map
   * @return
   */
  @RequestMapping(value = "/query", method = RequestMethod.POST, headers = "Accept=application/json")
  public HttpResponseEntity queryUserList(@RequestBody JSONObject map) {
    PageInfo page = map.getObject("page", PageInfo.class);
    PageInfo pageInfo = PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(() -> tenantService.queryTenants(map.getObject("queryParam", Map.class)));
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
    httpResponseEntity.setData(pageInfo);
    httpResponseEntity.setMessage(Constans.STATUS_MESSAGE);
    return httpResponseEntity;
  }

  /**
   * 创建用户的基本信息
   *
   * @param map
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.POST, headers = "Accept=application/json")
  public HttpResponseEntity addUserInfo(@RequestBody Tenant tenant) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    try {
      tenantService.getBaseMapper().insert(tenant);
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
  public HttpResponseEntity modifyUserInfo(@RequestBody Tenant tenant) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    tenantService.getBaseMapper().updateById(tenant);
    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
    httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
    return httpResponseEntity;
  }

  @RequestMapping(value = "/status", method = RequestMethod.POST, headers = "Accept=application/json")
  public HttpResponseEntity update(@RequestBody Map<String, Object> map) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    if (map.containsKey("loginEnable")) {
      Tenant tenant = tenantService.getById(((Integer) map.getOrDefault("id", "")));
      if (tenant != null) {
        tenant.setLoginEnable(Integer.valueOf((String) map.get("loginEnable")));
        tenantService.getBaseMapper().updateById(tenant);;
      }
    }
    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
    httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
    return httpResponseEntity;
  }

  @Autowired
  private TokenService tokenService;

  @RequestMapping(value = "/buyItem", method = RequestMethod.POST, headers = "Accept=application/json")
  public HttpResponseEntity buyItem(@RequestBody Map<String, Object> map) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    UserOnlineBo userOnlineBo = tokenService.getUserInfo();
    Tenant tenant = tenantService.getOne(new QueryWrapper<Tenant>().eq("username",userOnlineBo.getUsername()));
    if (tenant == null) {
      return HttpResponseEntity.getErrorReponse("租户不存在");
    }
    String item = ((String) map.get("item"));
    if(StringUtils.isBlank(item)){
      return HttpResponseEntity.getErrorReponse("请选择购买项目");
    }
    Integer num = ((Integer) map.get("num"));
    if (num == null) {
      return HttpResponseEntity.getErrorReponse("请输入购买数量");
    }
    if(StringUtils.equals("group",item)){
      tenant.setGroupNum(tenant.getGroupNum()+num);
    }else if(StringUtils.equals("anwser",item)){
      tenant.setAnswerNum(tenant.getAnswerNum()+num);
    }else if(StringUtils.equals("questions",item)){
      tenant.setQuestionnaireNum(tenant.getQuestionnaireNum()+num);
    }
    tenantService.updateById(tenant);
    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
    httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
    return httpResponseEntity;
  }


}
