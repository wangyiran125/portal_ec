package com.chinalbs.dao;

import java.util.List;

import com.chinalbs.entity.UserDevice;
import com.chinalbs.framework.dao.BaseDao;

public interface UserDeviceDao extends BaseDao<UserDevice, Long>{
  
  List<UserDevice> findListBySn(String fSn);
  
}
