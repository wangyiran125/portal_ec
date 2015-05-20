package com.chinalbs.service;

import java.util.List;

import com.chinalbs.entity.Spot;
import com.chinalbs.framework.service.BaseService;
import com.chinalbs.unionprimary.SpotPK;


public interface SpotService extends BaseService<Spot, SpotPK>{
  public List<Spot> findSpotBySn(String deviceSn);
}
