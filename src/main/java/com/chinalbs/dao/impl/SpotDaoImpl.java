package com.chinalbs.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.SpotDao;
import com.chinalbs.entity.Spot;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
import com.chinalbs.unionprimary.SpotPK;

@Repository("spotDaoImpl")
public class SpotDaoImpl extends BaseDaoImpl<Spot,SpotPK> implements SpotDao{

  public List<Spot> findSpotBySn(String deviceSn) {
    String jpql="select spots from Spot spots where spots.fDeviceSn=:fDeviceSn";
    
    return entityManager.createQuery(jpql).setFlushMode(FlushModeType.COMMIT).setParameter("fDeviceSn",deviceSn).getResultList();
  }

}
