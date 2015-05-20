package com.chinalbs.service;

import com.chinalbs.entity.EnterpriseToConductor;
import com.chinalbs.framework.service.BaseService;

public interface EnterpriseToConductorService extends BaseService<EnterpriseToConductor, Long> {

	EnterpriseToConductor findByEnterpriseId(Long getfId);

}
