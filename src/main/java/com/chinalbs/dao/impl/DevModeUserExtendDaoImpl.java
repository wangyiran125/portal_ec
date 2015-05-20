package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.DevModeUserExtendDao;
import com.chinalbs.entity.DevModeUserExtend;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

/**
 * Dao - 开发者扩展信息
 * 
 */
@Repository("devModeUserExtendDaoImpl")
public class DevModeUserExtendDaoImpl extends BaseDaoImpl<DevModeUserExtend, Long> implements
    DevModeUserExtendDao {

}
