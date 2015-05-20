package com.chinalbs.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.chinalbs.entity.EnterpriseToConductor;

public class TestEnterpriseToConductorService {

	private EnterpriseToConductorService eToService;
	
	@Test
	public void testSave() {
		EnterpriseToConductor eToConductor = getEnterpriseToConductor();
		eToService.save(eToConductor);
		EnterpriseToConductor actual = eToService.find(eToConductor.getId());
		Assert.assertEquals(eToConductor, actual);
		eToService.delete(actual.getId());
		EnterpriseToConductor notExist = eToService.find(eToConductor.getId());
		Assert.assertNotNull(notExist);
	}

	private EnterpriseToConductor getEnterpriseToConductor() {
		EnterpriseToConductor eToConductor = new EnterpriseToConductor();
		eToConductor.setConductorId(1L);
		eToConductor.setCreateTime(new Date());
		eToConductor.setEnterpriseId(2L);
		return eToConductor;
	}
	
	

}
