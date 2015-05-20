package com.chinalbs.service;

import java.util.List;

import com.chinalbs.entity.Beacon;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.framework.service.BaseService;

public interface BeaconService extends BaseService<Beacon, String> {

	Page<Beacon> findIndoorBeaconWithServiceId(Long userId,
			Pageable pageable);

	Beacon findByMac(String mac);

	List<Beacon> findByMacVague(String mac);
}
