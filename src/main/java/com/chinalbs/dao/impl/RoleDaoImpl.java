package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.RoleDao;
import com.chinalbs.entity.Role;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

/**
 * Dao - 角色
 * 
 */
@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

}