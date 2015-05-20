package com.chinalbs.dao;

import com.chinalbs.entity.Conductor;
import com.chinalbs.framework.dao.BaseDao;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

public interface ConductorDao extends BaseDao<Conductor, Long> {

	 final String SELECT_BY_SN = "from Conductor c where c.sn =:sn";
	Conductor findBySn(String sn) throws Exception;
	Page<Conductor> findByUserId(Long getfId, Pageable pageable);

}
