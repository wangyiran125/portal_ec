package com.chinalbs.dao;

import java.util.List;

import com.chinalbs.entity.Beacon;
import com.chinalbs.framework.dao.BaseDao;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

public interface BeaconDao extends BaseDao<Beacon, String> {

	Page<Beacon> findIndoorBeaconWithServiceId(Long userId, Pageable pageable);

	Beacon findByMac(String mac);

	List<Beacon> findByMacVague(String mac);
}
