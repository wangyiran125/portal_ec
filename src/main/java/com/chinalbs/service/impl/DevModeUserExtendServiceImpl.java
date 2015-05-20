package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.DevModeUserExtendDao;
import com.chinalbs.entity.DevModeUserExtend;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.DevModeUserExtendService;

/**
 * Service - 开发者扩展信息
 * 
 */
@Service("devModeUserExtendServiceImpl")
public class DevModeUserExtendServiceImpl extends BaseServiceImpl<DevModeUserExtend, Long>
    implements DevModeUserExtendService {

  @Resource(name = "devModeUserExtendDaoImpl")
  public void setBaseDao(DevModeUserExtendDao devModeUserExtendDao) {
    super.setBaseDao(devModeUserExtendDao);
  }
}
