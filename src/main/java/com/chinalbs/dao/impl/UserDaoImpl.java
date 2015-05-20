package com.chinalbs.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;



import org.springframework.stereotype.Repository;

import com.chinalbs.dao.UserDao;
import com.chinalbs.entity.User;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

/**
 * Dao -
 * 
 */
@Repository("userDaoImpl")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao {

	public boolean userNameExists(String userName) {
		if (userName == null) {
			return false;
		}
		String jpql = "select count(*) from User user where lower(user.userName) = lower(:userName)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("userName", userName).getSingleResult();
		return count > 0;
	}
	
	public boolean emailExists(String email) {
      if (email == null) {
          return false;
      }
      String jpql = "select count(*) from User user where lower(user.email) = lower(:email)";
      Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("email", email).getSingleResult();
      return count > 0;
  }
	
	public boolean mobileExists(String mobile) {
      if (mobile == null) {
          return false;
      }
      String jpql = "select count(*) from User user where user.mobile = :mobile";
      Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("mobile", mobile).getSingleResult();
      return count > 0;
  }

	public User findByUserName(String userName) {
		if (userName == null) {
			return null;
		}
		try {
			String jpql = "select user from User user where lower(user.userName) = lower(:userName)";
			return entityManager.createQuery(jpql, User.class).setFlushMode(FlushModeType.COMMIT).setParameter("userName", userName).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
  public List<User> findByName (String name)
  {
    if (name == null)
    {
      return null;
    }
    try
    {
      String jpql = "select user from User user where lower(user.name) like lower(:name)";
      return entityManager.createQuery (jpql, User.class).setFlushMode (FlushModeType.COMMIT).setParameter ("name", "%"+name+"%")
          .getResultList ();

    }
    catch (NoResultException e)
    {
      return null;
    }
  }

  public User findByNameAccurate (String name)
  {
    String jpql = "select user from User user where lower(user.name)= lower(:name)";
    try
    {
      return entityManager.createQuery (jpql, User.class).setFlushMode (FlushModeType.COMMIT).setParameter ("name", name)
          .getSingleResult ();
    }
    catch (NoResultException e)
    {
      return null;
    }

  }

  public Page<User> findPage(Pageable pageable, User user) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.select(root);
		Predicate restrictions = criteriaBuilder.conjunction();
		if (user != null) {
			restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("userName"), user.getUserName()));
		}
		criteriaQuery.where(restrictions);
		return super.findPage(criteriaQuery, pageable);
	}

	
}