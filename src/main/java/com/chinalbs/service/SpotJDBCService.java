package com.chinalbs.service;

import java.util.List;

import com.chinalbs.entity.Spot;

public interface SpotJDBCService {
  public List<Spot> findSpotByDeviceSn(String sn);
}
