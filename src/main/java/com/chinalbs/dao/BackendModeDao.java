package com.chinalbs.dao;

import com.chinalbs.entity.BackendModeUserExtend;
import com.chinalbs.framework.dao.BaseDao;

import java.util.List;

public interface BackendModeDao extends BaseDao<BackendModeUserExtend,Long>{

  public Boolean siteDomainExist(String siteDomain);

    /**
     * 根据网站域名查询
     * @param domain
     * @return
     */
    List<BackendModeUserExtend> getRootModeUserExtendByDomain(String domain);
    
    
    Boolean siteNameExist(String siteName);

}
