package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.EnterpriseToConductorDao;
import com.chinalbs.entity.EnterpriseToConductor;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.EnterpriseToConductorService;

@Service("enterpriseToConductorServiceImpl")
public class EnterpriseToConductorServiceImpl extends
		BaseServiceImpl<EnterpriseToConductor, Long> implements
		EnterpriseToConductorService {

	@Resource(name="enterpriseToConductorDaoImpl")
	private EnterpriseToConductorDao enterpriseToConductorDaoImpl;

	@Resource(name="enterpriseToConductorDaoImpl")
	public void setBaseDao(EnterpriseToConductorDao enToConductorDao) {
		super.setBaseDao(enToConductorDao);
	};
	
	@Override
	public EnterpriseToConductor findByEnterpriseId(Long getfId) {
		// TODO Auto-generated method stub
		return null;
	}
}
