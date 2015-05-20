package com.chinalbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.DeviceDao;
import com.chinalbs.entity.Device;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.DeviceService;

@Service("deviceServiceImpl")
public class DeviceServiceImpl extends BaseServiceImpl<Device, String> implements DeviceService {
  
  @Resource(name="deviceDaoImpl")
  private DeviceDao deviceDao;
  
  @Resource(name = "deviceDaoImpl")
  public void setBaseDao(DeviceDao deviceDao) {
    super.setBaseDao(deviceDao);
  }
  

  @Override
  public Page<Device> findDeviceWithServiceId(Long userId, Pageable pageable) {
    
   Page<Device> pages = deviceDao.findDeviceWithServiceId(userId, pageable); 
    
 //   Page<Device> pages = deviceDao.findPage(pageable); 
    
    return pages;
  }

  /**
   * 检查设备是否绑定
   * @param sn
   * @return true绑定 false 没有绑定
   */
  public boolean checkDeviceIsBinding(String sn){
    return deviceDao.checkDeviceIsBinding(sn);
  }

  
  public Device findBySn (String fSn){
    return deviceDao.findBySn(fSn);
  };
  
  public  List<Device> findBySnVague (String fSn){
    return deviceDao.findBySnVague(fSn);
  }
  
}
