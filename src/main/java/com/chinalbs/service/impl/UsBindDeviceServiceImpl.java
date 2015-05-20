package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.UsBindDeviceDao;
import com.chinalbs.entity.UsBindDevice;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.UsBindDeviceService;

@Service("usBindDeviceServiceImpl")
public class UsBindDeviceServiceImpl extends BaseServiceImpl<UsBindDevice, Long> implements UsBindDeviceService {
  
  @Resource(name = "usBindDeviceDaoImpl")
  public void setBaseDao(UsBindDeviceDao usBindDeviceDao) {
    super.setBaseDao(usBindDeviceDao);
  }
}
