package  com.aim.questionnaire.service;

import com.aim.questionnaire.common.utils.EasyExcelUtil;
import com.aim.questionnaire.common.utils.MD5Utils;
import com.aim.questionnaire.common.utils.UUIDUtil;
import  com.aim.questionnaire.entity.Respondent;
import com.aim.questionnaire.dao.RespondentMapper;
import com.aim.questionnaire.entity.Tenant;
import com.aim.questionnaire.entity.UserEntity;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * <p>
 *   服务类
 * </p>
 *
 * @author 作者
 * @since 2022-11-09
 */

@Service
public class RespondentService extends ServiceImpl<RespondentMapper,Respondent>{

  @Autowired
  RespondentMapper respondentMapper;

  public List<Respondent> queryRespondents(Map queryParam) {
    return respondentMapper.queryRespondents(queryParam);
  }

  @Autowired
  TokenService tokenService;
  public void batchUpload(File file) {
    Map<String, List<Map<String, Object>>> datas = EasyExcelUtil.getMultiSheetsDataList(file);
    // 第一个sheet是学生，第二个是老师
    int index = 0;
    for (List<Map<String, Object>> value : datas.values()) {
      if (value == null || value.size() == 0) {
        continue;
      }
      for (Map<String, Object> stringObjectMap : value) {
        Respondent userEntity = new Respondent();
        userEntity.setUsername((String) stringObjectMap.get("账号"));
        userEntity.setRealname((String) stringObjectMap.get("账号"));
        userEntity.setSex((String) stringObjectMap.get("性别"));
        userEntity.setTelephone((String) stringObjectMap.get("手机号"));
        userEntity.setPassword(MD5Utils.getMD5Code("11111111"));
        userEntity.setRoleName("user");
        userEntity.setParentId(tokenService.getUserInfo().getUsername());
        respondentMapper.insert(userEntity);
      }


    }
  }
}
