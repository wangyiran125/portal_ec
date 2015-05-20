package com.chinalbs.service;

import com.chinalbs.entity.Conductor;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.framework.service.BaseService;

public interface ConductorService extends BaseService<Conductor, Long> {

	boolean existBySn(String sn);

	Conductor findBySn(String sn) throws Exception;

	Page<Conductor> findByUserId(Long getfId, Pageable pageable);

	void saveWithUserId(Conductor conductor, Long id);

}
