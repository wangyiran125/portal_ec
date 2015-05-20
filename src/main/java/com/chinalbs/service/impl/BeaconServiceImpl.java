package com.chinalbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinalbs.dao.BeaconDao;
import com.chinalbs.entity.Beacon;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.BeaconService;

@Service("beaconServiceImpl")
public class BeaconServiceImpl extends BaseServiceImpl<Beacon, String>
		implements BeaconService {

	@Resource(name = "beaconDaoImpl")
	private BeaconDao beaconDao;

	@Resource(name = "beaconDaoImpl")
	public void setBaseDao(BeaconDao beaconDao) {
		super.setBaseDao(beaconDao);
	}

	@Override
	public Page<Beacon> findIndoorBeaconWithServiceId(Long userId,
			Pageable pageable) {
		Page<Beacon> pages = beaconDao.findIndoorBeaconWithServiceId(
				userId, pageable);

		return pages;
	}

	@Override
	public Beacon findByMac(String mac) {
		return beaconDao.findByMac(mac);
	}

	@Override
	public List<Beacon> findByMacVague(String mac) {
		return beaconDao.findByMacVague(mac);
	}
	
	
	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void save(Beacon role) {
		super.save(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Beacon update(Beacon role) {
		return super.update(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Beacon update(Beacon role, String... ignoreProperties) {
		return super.update(role, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(String id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(String... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Beacon coordinate) {
		super.delete(coordinate);
	}

}
