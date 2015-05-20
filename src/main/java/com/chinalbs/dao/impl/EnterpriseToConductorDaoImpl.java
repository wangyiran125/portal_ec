package com.chinalbs.dao.impl;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.EnterpriseToConductorDao;
import com.chinalbs.entity.EnterpriseToConductor;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
@Service("enterpriseToConductorDaoImpl")
public class EnterpriseToConductorDaoImpl extends
		BaseDaoImpl<EnterpriseToConductor, Long> implements
		EnterpriseToConductorDao {

}
