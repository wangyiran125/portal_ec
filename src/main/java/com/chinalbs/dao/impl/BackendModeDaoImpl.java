package com.chinalbs.dao.impl;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.BackendModeDao;
import com.chinalbs.entity.BackendModeUserExtend;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;

import java.util.List;

@Repository("backendModeDaoImpl")
public class BackendModeDaoImpl extends BaseDaoImpl<BackendModeUserExtend, Long> implements
    BackendModeDao {

  @Override
  public Boolean siteDomainExist(String siteDomain) {

    if (siteDomain == null) {
      return false;
  }
  String jpql = "select count(*) from BackendModeUserExtend backend where lower(backend.siteDomain) = lower(:siteDomain)";
  Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("siteDomain", siteDomain).getSingleResult();
  return count > 0;
  }
    @Override
  public List<BackendModeUserExtend> getRootModeUserExtendByDomain(String domain) {
    List<BackendModeUserExtend> backendModeUserExtendList = null;
    if (domain == null) {
      return null;
    }
    try {
      String jpql =
          "select backend from BackendModeUserExtend backend where lower(backend.siteDomain) = lower(:siteDomain)";
      backendModeUserExtendList =
          entityManager.createQuery(jpql, BackendModeUserExtend.class)
              .setFlushMode(FlushModeType.COMMIT).setParameter("siteDomain", domain)
              .getResultList();
      if (backendModeUserExtendList == null || backendModeUserExtendList.size() == 0) {
        jpql =
            "select backend from BackendModeUserExtend backend where lower(backend.defaultSiteDomain) = lower(:defaultSiteDomain)";
        backendModeUserExtendList =
            entityManager.createQuery(jpql, BackendModeUserExtend.class)
                .setFlushMode(FlushModeType.COMMIT).setParameter("defaultSiteDomain", domain)
                .getResultList();
      }
      return backendModeUserExtendList;
    } catch (NoResultException e) {
      return null;
    }
  }
    @Override
    public Boolean siteNameExist(String siteName) {
      if (siteName == null) {
        return false;
    }
    String jpql = "select count(*) from BackendModeUserExtend backend where lower(backend.siteName) = lower(:siteName)";
    Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("siteName", siteName).getSingleResult();
    return count > 0;
    }
}
