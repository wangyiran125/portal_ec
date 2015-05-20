package com.chinalbs.service;

import com.chinalbs.entity.BackendModeUserExtend;
import com.chinalbs.framework.service.BaseService;

public interface BackendModeService extends BaseService<BackendModeUserExtend,Long>{
  public Boolean siteDomainExist(String siteDomain);
   /**
     * 获取配置网站的跟用户
     *
     * @return 若不存在则返回null
   */
   BackendModeUserExtend getRootModeUserExtendByDomain(String companyDomain);
   
   /**
    * 网站名重复性验证
    * @param siteName
    * @return
    */
   Boolean siteNameExist(String siteName);

}
