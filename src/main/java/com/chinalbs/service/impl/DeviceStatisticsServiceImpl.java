package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.DeviceStatisticsDao;
import com.chinalbs.entity.DeviceStatistics;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.DeviceStatisticsService;

@Service("deviceStatisticsServiceImpl")
public class DeviceStatisticsServiceImpl extends BaseServiceImpl<DeviceStatistics, Long> implements DeviceStatisticsService {
  
  @Resource(name = "deviceStatisticsDaoImpl")
  public void setBaseDao(DeviceStatisticsDao deviceStatisticsDao) {
    super.setBaseDao(deviceStatisticsDao);
  }
}
