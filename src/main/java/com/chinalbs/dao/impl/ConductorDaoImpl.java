package com.chinalbs.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.chinalbs.dao.ConductorDao;
import com.chinalbs.entity.Conductor;
import com.chinalbs.entity.Device;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

@Component
@Scope("singleton")
public class ConductorDaoImpl extends BaseDaoImpl<Conductor, Long> implements ConductorDao {

	@Override
	public Conductor findBySn(String sn) throws Exception {
		List<Conductor> conductors = entityManager.createQuery(SELECT_BY_SN).setParameter("sn", sn).getResultList();
		if (conductors == null || conductors.size()== 0) {
			return new Conductor();
		}
		else {
			return conductors.get(0);
		}
	}

	@Override
	public Page<Conductor> findByUserId(Long getfId, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Conductor> criteriaQuery = criteriaBuilder.createQuery(Conductor.class);
	    Root<Conductor> root = criteriaQuery.from(Conductor.class);
	    criteriaQuery.select(root);
	    Predicate restrictions = criteriaBuilder.conjunction();
	    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userId"), getfId));
	    criteriaQuery.where(restrictions);
	    return super.findPage(criteriaQuery, pageable);
	}
	
}
