package com.chinalbs.service;

import java.util.List;

import com.chinalbs.entity.UserDevice;
import com.chinalbs.framework.service.BaseService;

public interface UserDeviceService extends BaseService<UserDevice, Long>{
  
   List<UserDevice> findListBySn(String fSn) ;
}
