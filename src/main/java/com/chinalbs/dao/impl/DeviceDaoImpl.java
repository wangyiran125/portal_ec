package com.chinalbs.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.springframework.stereotype.Repository;

import com.chinalbs.common.log.LogUtil;
import com.chinalbs.dao.DeviceDao;
import com.chinalbs.entity.Device;
import com.chinalbs.entity.Spot;
import com.chinalbs.entity.User;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

@Repository("deviceDaoImpl")
public class DeviceDaoImpl extends BaseDaoImpl<Device, String> implements DeviceDao {
  public Page<Device> findDeviceWithServiceId (Long userId,Pageable pageable){
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Device> criteriaQuery = criteriaBuilder.createQuery(Device.class);
    Root<Device> root = criteriaQuery.from(Device.class);
    criteriaQuery.select(root);
    Predicate restrictions = criteriaBuilder.conjunction();
    restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("fServiceId"), userId));
    criteriaQuery.where(restrictions);
    return super.findPage(criteriaQuery, pageable);
    
  }

  @Override
  public boolean checkDeviceIsBinding(String sn) {
    
    if (sn == null) {
      return false;
    }
    String jpql = "select count(*) from UserDevice userDevice where lower(userDevice.fDeviceSn) = lower(:sn)";
    Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("sn", sn).getSingleResult();
    return count > 0;
  }

  @Override
  public List<Spot> selectUploadSpotsWithSn(String sn) {
   
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Spot> criteriaQuery = criteriaBuilder.createQuery(Spot.class);
    Root<Spot> root = criteriaQuery.from(Spot.class);
    criteriaQuery.select(root);
    Predicate restrictions = criteriaBuilder.conjunction();
    if (sn != null) {
        restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get("fDeviceSn"), sn));
    }

    criteriaQuery.where(restrictions);
    return entityManager.createQuery(criteriaQuery).setFlushMode(FlushModeType.COMMIT).getResultList();
    
  }

  public List<Device> findBySnVague (String fSn)
  {
    if (fSn == null)
    {
      return null;
    }
    try
    {
      String jpql = "select device from Device device where lower(device.fSn) like lower(:fSn)";
      return entityManager.createQuery (jpql, Device.class).setFlushMode (FlushModeType.COMMIT).setParameter ("fSn", "%"+fSn+"%")
          .getResultList ();

    }
    catch (NoResultException e)
    {
      return null;
    }
  }
  
  public Device findBySn (String fSn)
  {
    String jpql = "select device from Device device where lower(device.fSn)= lower(:fSn)";
    try
    {
      return entityManager.createQuery (jpql, Device.class).setFlushMode (FlushModeType.COMMIT).setParameter ("fSn", fSn)
          .getSingleResult ();
    }
    catch (NoResultException e)
    {
      return null;
    }

  }
  
}
