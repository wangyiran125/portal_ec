package com.chinalbs.dao.impl;

import java.util.List;

import javax.persistence.FlushModeType;

import org.springframework.stereotype.Repository;

import com.chinalbs.dao.HandsetToConductorDao;
import com.chinalbs.entity.HandSetToConductor;
import com.chinalbs.framework.dao.impl.BaseDaoImpl;
@Repository("handsetToConductorDaoImpl")
public class HandsetToConductorDaoImpl extends BaseDaoImpl <HandSetToConductor, Long> implements HandsetToConductorDao {


    @Override
    public List <HandSetToConductor> findByConductorSn (String sn) {

        String jpql = "select htcs from HandSetToConductor htcs where htcs.conductorId=:conductorId";

        return entityManager.createQuery (jpql).setFlushMode (FlushModeType.COMMIT).setParameter ("conductorId", sn)
                .getResultList ();
    }

    @Override
    public void deleteById (long id) {
        String jpql = "delete from HandSetToConductor where id=:id";

         entityManager.createQuery (jpql).setFlushMode (FlushModeType.COMMIT).setParameter ("id", id)
                .executeUpdate ();
        
    }

}
