package com.chinalbs.dao;

import java.util.List;

import com.chinalbs.entity.HandSetToConductor;
import com.chinalbs.framework.dao.BaseDao;

public interface HandsetToConductorDao extends BaseDao <HandSetToConductor, Long> {

    List <HandSetToConductor> findByConductorSn (String sn);

    void deleteById (long id);

}
