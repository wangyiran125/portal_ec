package com.chinalbs.dao;

import java.util.List;

import com.chinalbs.entity.Coordinate;
import com.chinalbs.framework.dao.BaseDao;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

public interface CoordinateDao extends BaseDao<Coordinate, Long> {

	Page<Coordinate> findIndoorCoordinateWithServiceId(Long userId,
			Pageable pageable);

	public Coordinate findById(String id);

	public List<Coordinate> findByLocationVague(String location, String name);

	List<Coordinate> findIndoorCoordinateWithServiceId(Long userId);
}
