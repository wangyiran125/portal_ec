package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.CustomizeFunDao;
import com.chinalbs.entity.CustomizeFun;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.CustomizeFunService;

@Service("customizeFunServiceImpl")
public class CustomizeFunServiceImpl extends BaseServiceImpl<CustomizeFun, Long> implements CustomizeFunService{

  @Resource(name = "customizeFunDaoImpl")
  public void setBaseDao(CustomizeFunDao customizeFunDao) {
    super.setBaseDao(customizeFunDao);
  }
}
