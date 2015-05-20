package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.DeviceStatisticsDao;
import com.chinalbs.entity.DeviceStatistics;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

@Repository("deviceStatisticsDaoImpl")
public class DeviceStatisticsDaoImpl extends BaseDaoImpl<DeviceStatistics, Long> implements DeviceStatisticsDao {

}
