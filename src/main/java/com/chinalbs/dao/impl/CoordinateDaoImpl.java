package com.chinalbs.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.CoordinateDao;
import com.chinalbs.entity.Coordinate;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

@Repository("coordinateDaoImpl")
public class CoordinateDaoImpl extends BaseDaoImpl<Coordinate, Long> implements
		CoordinateDao {
	public Page<Coordinate> findIndoorCoordinateWithServiceId(Long userId,
			Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Coordinate> criteriaQuery = criteriaBuilder
				.createQuery(Coordinate.class);
		Root<Coordinate> root = criteriaQuery.from(Coordinate.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		restrictions = criteriaBuilder.and(restrictions,
				criteriaBuilder.equal(root.get("fServiceId"), userId));
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);

	}

	public Coordinate findById(String id) {
		String jpql = "select coordinate from Coordinate coordinate where lower(coordinate.id)= lower(:id)";
		try {
			return entityManager.createQuery(jpql, Coordinate.class)
					.setFlushMode(FlushModeType.COMMIT).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public List<Coordinate> findByLocationVague(String location, String name) {
		try {
			String jpql = "select coordinate from Coordinate coordinate where lower(coordinate.fLocation) like lower(:location) and lower(coordinate.fName) like lower(:name) ";
			return entityManager.createQuery(jpql, Coordinate.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("location", "%" + location + "%")
					.setParameter("name", "%" + name + "%").getResultList();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Coordinate> findIndoorCoordinateWithServiceId(Long userId) {
		try {
			String jpql = "select coordinate from Coordinate coordinate where lower(coordinate.fServiceId)= lower(:userId)  ";
			return entityManager.createQuery(jpql, Coordinate.class)
					.setFlushMode(FlushModeType.COMMIT)
					.setParameter("userId", userId).getResultList();

		} catch (NoResultException e) {
			return null;
		}
	}

}
