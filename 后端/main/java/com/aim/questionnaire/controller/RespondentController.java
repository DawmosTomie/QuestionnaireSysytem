package  com.aim.questionnaire.controller;

import com.aim.questionnaire.beans.HttpResponseEntity;
import com.aim.questionnaire.common.Constans;
import com.aim.questionnaire.entity.Respondent;
import com.aim.questionnaire.service.RespondentService;
import com.aim.questionnaire.service.TenantService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-11-09
 */
@RestController
@RequestMapping("/respondent")
public class RespondentController {

  @Autowired
  private RespondentService respondentService;

  /**
   * 查询用户列表（模糊搜索）
   *
   * @param map
   * @return
   */
  @RequestMapping(value = "/query", method = RequestMethod.POST, headers = "Accept=application/json")
  public HttpResponseEntity queryUserList(@RequestBody JSONObject map) {
    PageInfo page = map.getObject("page", PageInfo.class);
    PageInfo pageInfo = PageHelper.startPage(page.getPageNum(), page.getPageSize()).doSelectPageInfo(() -> respondentService.queryRespondents(map.getObject("queryParam", Map.class)));
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
  public HttpResponseEntity addUserInfo(@RequestBody Respondent respondent) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    try {
      respondentService.getBaseMapper().insert(respondent);
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
  public HttpResponseEntity modifyUserInfo(@RequestBody Respondent respondent) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    respondentService.getBaseMapper().updateById(respondent);
    httpResponseEntity.setCode(Constans.SUCCESS_CODE);
    httpResponseEntity.setMessage(Constans.ADD_MESSAGE);
    return httpResponseEntity;
  }


  @RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
  public HttpResponseEntity batchUpload(@RequestParam MultipartFile file) {
    HttpResponseEntity httpResponseEntity = new HttpResponseEntity();
    if (httpResponseEntity == null) {
      httpResponseEntity.setCode(Constans.EXIST_CODE);
      httpResponseEntity.setMessage("没有文件上传");
      return httpResponseEntity;
    }
    String s2 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    File curFile = new File(s2 + "/" + file.getOriginalFilename());
    try {
      file.transferTo(curFile);
    } catch (IOException e) {
      httpResponseEntity.setCode(Constans.EXIST_CODE);
      httpResponseEntity.setMessage("文件上传失败");
      return httpResponseEntity;
    }
    respondentService.batchUpload(curFile);
    httpResponseEntity.setCode("666");
    return httpResponseEntity;
  }
}
