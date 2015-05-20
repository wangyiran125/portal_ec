package com.chinalbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinalbs.dao.CoordinateDao;
import com.chinalbs.entity.Coordinate;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.CoordinateService;

@Service("coordinateServiceImpl")
public class CoordinateServiceImpl extends BaseServiceImpl<Coordinate, Long>
		implements CoordinateService {

	@Resource(name = "coordinateDaoImpl")
	private CoordinateDao coordinateDao;

	@Resource(name = "coordinateDaoImpl")
	public void setBaseDao(CoordinateDao coordinateDao) {
		super.setBaseDao(coordinateDao);
	}

	@Override
	public Page<Coordinate> findIndoorCoordinateWithServiceId(Long userId,
			Pageable pageable) {
		Page<Coordinate> pages = coordinateDao
				.findIndoorCoordinateWithServiceId(userId, pageable);

		return pages;
	}

	@Override
	public Coordinate findById(String id) {
		return coordinateDao.findById(id);
	}

	@Override
	public List<Coordinate> findByLocationVague(String location, String name) {
		return coordinateDao.findByLocationVague(location, name);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void save(Coordinate role) {
		super.save(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Coordinate update(Coordinate role) {
		return super.update(role);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public Coordinate update(Coordinate role, String... ignoreProperties) {
		return super.update(role, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Coordinate coordinate) {
		super.delete(coordinate);
	}

	@Override
	public List<Coordinate> findIndoorCoordinateWithServiceId(Long userId) {
		return coordinateDao.findIndoorCoordinateWithServiceId(userId);
	}

}
