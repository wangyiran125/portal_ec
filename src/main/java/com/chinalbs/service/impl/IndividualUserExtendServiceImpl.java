package com.chinalbs.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.chinalbs.dao.IndividualUserExtendDao;
import com.chinalbs.entity.IndividualUserExtend;
import com.chinalbs.framework.service.impl.BaseServiceImpl;
import com.chinalbs.service.IndividualUserExtendService;

@Service("individualUserExtendServiceImpl")
public class IndividualUserExtendServiceImpl extends BaseServiceImpl<IndividualUserExtend, Long>
    implements IndividualUserExtendService {

  @Resource(name = "individualUserExtendDaoImpl")
  public void setBaseDao(IndividualUserExtendDao individualUserExtendDao) {
    super.setBaseDao(individualUserExtendDao);
  }
}
