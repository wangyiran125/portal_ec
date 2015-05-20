package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.UsBindDeviceDao;
import com.chinalbs.entity.UsBindDevice;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

@Repository("usBindDeviceDaoImpl")
public class UsBindDeviceDaoImpl extends BaseDaoImpl<UsBindDevice, Long> implements UsBindDeviceDao {

}
