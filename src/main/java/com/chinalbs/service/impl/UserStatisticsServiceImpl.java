package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.UserStatisticsDao;
import com.chinalbs.entity.UserStatistics;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.UserStatisticsService;

@Service("userStatisticsServiceImpl")
public class UserStatisticsServiceImpl extends BaseServiceImpl<UserStatistics, Long> implements UserStatisticsService {
  
  @Resource(name = "userStatisticsDaoImpl")
  public void setBaseDao(UserStatisticsDao userStatisticsDao) {
    super.setBaseDao(userStatisticsDao);
  }
}
