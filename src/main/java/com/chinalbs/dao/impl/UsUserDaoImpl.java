package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.UsUserDao;
import com.chinalbs.entity.UsUser;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

@Repository("usUserDaoImpl")
public class UsUserDaoImpl extends BaseDaoImpl<UsUser, Long> implements UsUserDao {

}
