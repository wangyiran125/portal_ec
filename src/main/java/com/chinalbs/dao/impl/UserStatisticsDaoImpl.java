package com.chinalbs.dao.impl;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.UserStatisticsDao;
import com.chinalbs.entity.UserStatistics;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

@Repository("userStatisticsDaoImpl")
public class UserStatisticsDaoImpl extends BaseDaoImpl<UserStatistics, Long> implements UserStatisticsDao {

}
