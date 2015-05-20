package com.chinalbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.SpotDao;
import com.chinalbs.entity.Spot;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.SpotService;
import com.chinalbs.unionprimary.SpotPK;

@Service("spotServiceImpl")
public class SpotServiceImpl extends BaseServiceImpl<Spot, SpotPK> implements SpotService {
  @Resource(name = "spotDaoImpl")
  SpotDao spotDao;

  @Resource(name = "spotDaoImpl")
  public void setBaseDao(SpotDao spotDao) {
    super.setBaseDao(spotDao);
  }

  @Override
  public List<Spot> findSpotBySn(String deviceSn) {
    return spotDao.findSpotBySn(deviceSn);
  }



}
