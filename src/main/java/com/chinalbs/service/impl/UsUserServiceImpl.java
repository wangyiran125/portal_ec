package com.chinalbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinalbs.dao.UsUserDao;
import com.chinalbs.entity.UsUser;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.UsUserService;

@Service("usUserServiceImpl")
public class UsUserServiceImpl extends BaseServiceImpl<UsUser, Long> implements UsUserService {
  
  @Resource(name = "usUserDaoImpl")
  public void setBaseDao(UsUserDao usUserDao) {
    super.setBaseDao(usUserDao);
  }
}
