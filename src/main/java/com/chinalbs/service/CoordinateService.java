package com.chinalbs.service;

import java.util.List;

import com.chinalbs.entity.Coordinate;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.framework.service.BaseService;

public interface CoordinateService extends BaseService<Coordinate, Long> {

	Page<Coordinate> findIndoorCoordinateWithServiceId(Long userId,
			Pageable pageable);

	Coordinate findById(String id);

	List<Coordinate> findByLocationVague(String location,String name);
	
	List<Coordinate> findIndoorCoordinateWithServiceId(Long userId);
	
}
