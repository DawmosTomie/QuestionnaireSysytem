package  com.aim.questionnaire.service;

import  com.aim.questionnaire.entity.Tenant;
import com.aim.questionnaire.dao.TenantMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class TenantService extends ServiceImpl<TenantMapper,Tenant>{

  @Autowired
  TenantMapper tenantMapper;

  public List<Tenant> queryTenants(Map queryParam) {
    return tenantMapper.queryTenants(queryParam);
  }
}
