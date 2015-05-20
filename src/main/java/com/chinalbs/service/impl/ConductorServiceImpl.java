package com.chinalbs.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinalbs.dao.ConductorDao;
import com.chinalbs.entity.Conductor;
import com.chinalbs.entity.Device;
import com.chinalbs.entity.EnterpriseToConductor;
import com.chinalbs.entity.User;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.ConductorService;
import com.chinalbs.service.EnterpriseToConductorService;

@Service("conductorServiceImpl")
public class ConductorServiceImpl extends BaseServiceImpl<Conductor, Long>
		implements ConductorService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource(name = "conductorDaoImpl")
	private ConductorDao conductorDao;

	@Resource(name = "conductorDaoImpl")
	public void setBaseDao(ConductorDao conductorDao) {
		super.setBaseDao(conductorDao);
	}

	public void save(Conductor entity) {
		conductorDao.persist(entity);
	}

	public Conductor find(Long id) {
		return conductorDao.find(id);
	}

	public void delete(Conductor entity) {
		conductorDao.remove(entity);
	}

	public Conductor findBySn(String sn) throws Exception {
		conductorDao.flush();
		Conductor conductor = conductorDao.findBySn(sn);
		return conductor;
	}

	public boolean existBySn(String sn) {
		Conductor conductor;
		try {
			conductor = findBySn(sn);
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
		if (conductor.getSn().equals("")) {
			return false;
		}else {
			return true;
		}
	}

	@Override
	public Page<Conductor> findByUserId(Long getfId, Pageable pageable) {
		return conductorDao.findByUserId(getfId,pageable);
		
	}


	@Override
	public void saveWithUserId(Conductor conductor, Long userId) {
		conductor.setUserId(userId);
		save(conductor);
	}

}
