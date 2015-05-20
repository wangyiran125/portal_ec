package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.CustomizeFunDao;
import com.chinalbs.entity.CustomizeFun;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

@Repository("customizeFunDaoImpl")
public class CustomizeFunDaoImpl extends BaseDaoImpl<CustomizeFun,Long> implements CustomizeFunDao{

}
