package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.chinalbs.dao.EnterpriseUserExtendDao;
import com.chinalbs.entity.EnterpriseUserExtend;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.EnterpriseUserExtendService;

@Service("enterpriseUserExtendServiceImpl")
public class EnterpriseUserExtendServiceImpl extends BaseServiceImpl<EnterpriseUserExtend, Long>
    implements EnterpriseUserExtendService {
  
    @Resource(name = "enterpriseUserExtendDaoImpl")
    public void setBaseDao(EnterpriseUserExtendDao enterpriseUserExtendDao) {
        super.setBaseDao(enterpriseUserExtendDao);
    }
}
