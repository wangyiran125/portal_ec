package com.chinalbs.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.BeaconDao;
import com.chinalbs.entity.Beacon;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

@Repository("beaconDaoImpl")
public class BeaconDaoImpl extends BaseDaoImpl<Beacon, String> implements
		BeaconDao {
	public Page<Beacon> findIndoorBeaconWithServiceId(Long userId,
			Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Beacon> criteriaQuery = criteriaBuilder
				.createQuery(Beacon.class);
		Root<Beacon> root = criteriaQuery.from(Beacon.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("fServiceId"), userId));
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);

	}

	public List<Beacon> findByMacVague(String mac) {
		if (mac == null) {
			return null;
		}
		try {
			String jpql = "select beacon from Beacon beacon where lower(beacon.fMac) like lower(:mac)";
			return entityManager.createQuery(jpql, Beacon.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("mac", "%" + mac + "%").getResultList();

		} catch (NoResultException e) {
			return null;
		}
	}

	public Beacon findByMac(String mac) {
		String jpql = "select beacon from Beacon beacon where lower(beacon.fMac)= lower(:mac)";
		try {
			return entityManager.createQuery(jpql, Beacon.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("mac", mac).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
