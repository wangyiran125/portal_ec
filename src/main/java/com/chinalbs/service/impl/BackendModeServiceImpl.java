package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.BackendModeDao;
import com.chinalbs.entity.BackendModeUserExtend;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.BackendModeService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("backendModeServiceImpl")
public class BackendModeServiceImpl extends BaseServiceImpl<BackendModeUserExtend, Long> implements
        BackendModeService {
    @Resource(name="backendModeDaoImpl")
    private BackendModeDao backendModeDao;
    @Resource(name = "backendModeDaoImpl")
    public void setBaseDao(BackendModeDao backendModeDao) {
        super.setBaseDao(backendModeDao);
    }

    @Override
    public Boolean siteDomainExist(String siteDomain) {

        return backendModeDao.siteDomainExist(siteDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public BackendModeUserExtend getRootModeUserExtendByDomain(String companyDomain) {
        List<BackendModeUserExtend> backendModeUserExtendList =  backendModeDao.getRootModeUserExtendByDomain(companyDomain);
        if(backendModeUserExtendList!=null&&backendModeUserExtendList.size()>0){
            backendModeUserExtendList.get(0).getUser().getEnterpriseUserExtend();
            backendModeUserExtendList.get(0).getUserCustomizeFuns();
            return backendModeUserExtendList.get(0);
        }
        return null;
    }

    @Override
    public Boolean siteNameExist(String siteName) {
       return backendModeDao.siteNameExist(siteName);
    }
}
