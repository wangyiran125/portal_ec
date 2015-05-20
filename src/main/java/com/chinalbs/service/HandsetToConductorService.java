package com.chinalbs.service;

import java.util.List;

import com.chinalbs.entity.HandSetToConductor;
import com.chinalbs.framework.service.BaseService;

public interface HandsetToConductorService extends BaseService <HandSetToConductor, Long> {

    List <HandSetToConductor> findByConductorSn (String sn);

    void deleteById (long id);

}
