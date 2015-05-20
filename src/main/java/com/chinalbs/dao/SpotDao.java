package com.chinalbs.dao;

import java.util.List;

import com.chinalbs.entity.Spot;
import com.chinalbs.framework.dao.BaseDao;
import com.chinalbs.unionprimary.SpotPK;

public interface SpotDao extends BaseDao<Spot, SpotPK> {
  public List<Spot> findSpotBySn(String deviceSn);
}
