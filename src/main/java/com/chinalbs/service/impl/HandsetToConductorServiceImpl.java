package com.chinalbs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinalbs.dao.HandsetToConductorDao;
import com.chinalbs.entity.HandSetToConductor;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.HandsetToConductorService;

@Service("handsetToConductorServiceImpl")
public class HandsetToConductorServiceImpl extends BaseServiceImpl <HandSetToConductor, Long> implements
        HandsetToConductorService {

    private Logger                logger = LoggerFactory.getLogger (getClass ());
    @Resource(name = "handsetToConductorDaoImpl")
    private HandsetToConductorDao handsetToConductorDao;

    @Resource(name = "handsetToConductorDaoImpl")
    public void setBaseDao (HandsetToConductorDao htConductorDao) {

        super.setBaseDao (htConductorDao);
    }


    @Override
    public List <HandSetToConductor> findByConductorSn (String sn) {

        List <HandSetToConductor> handSetToConductors = handsetToConductorDao.findByConductorSn(sn);
        return handSetToConductors;
    }



    @Override
    public void deleteById (long id) {

        handsetToConductorDao.deleteById(id);
    }

}
