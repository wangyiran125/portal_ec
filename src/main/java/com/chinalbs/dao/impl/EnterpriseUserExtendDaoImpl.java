package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.EnterpriseUserExtendDao;
import com.chinalbs.entity.EnterpriseUserExtend;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

@Repository("enterpriseUserExtendDaoImpl")
public class EnterpriseUserExtendDaoImpl extends BaseDaoImpl<EnterpriseUserExtend,Long> implements EnterpriseUserExtendDao{

}
