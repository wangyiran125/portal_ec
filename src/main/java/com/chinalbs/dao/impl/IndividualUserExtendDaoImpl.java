package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.IndividualUserExtendDao;
import com.chinalbs.entity.IndividualUserExtend;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

@Repository("individualUserExtendDaoImpl")
public class IndividualUserExtendDaoImpl extends BaseDaoImpl<IndividualUserExtend,Long> implements IndividualUserExtendDao{

}
