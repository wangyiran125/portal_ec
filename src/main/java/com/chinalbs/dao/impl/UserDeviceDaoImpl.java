package com.chinalbs.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.UserDeviceDao;
import com.chinalbs.entity.UserDevice;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

@Service("userDeviceDaoImpl")
public class UserDeviceDaoImpl extends BaseDaoImpl<UserDevice, Long> implements
    UserDeviceDao{

  public List<UserDevice> findListBySn(String fSn) {
    String jpql="select userDevice from UserDevice userDevice where userDevice.fDeviceSn=:fSn";
    return entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("fSn",fSn).getResultList();
    
  }

}
