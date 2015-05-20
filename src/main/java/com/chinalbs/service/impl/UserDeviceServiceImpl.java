package com.chinalbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.UserDeviceDao;
import com.chinalbs.entity.UserDevice;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.UserDeviceService;

@Service("userDeviceServiceImpl")
public class UserDeviceServiceImpl extends BaseServiceImpl<UserDevice, Long> implements UserDeviceService {
  
  @Resource(name="userDeviceDaoImpl")
  private UserDeviceDao userDeviceDao;
  
  public List<UserDevice> findListBySn(String fSn) {
    return userDeviceDao.findListBySn(fSn);
  }
}
