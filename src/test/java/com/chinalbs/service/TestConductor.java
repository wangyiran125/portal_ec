package com.chinalbs.service;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.chinalbs.entity.Conductor;
import com.chinalbs.entity.EnterpriseToConductor;
import com.chinalbs.entity.User;
import com.chinalbs.framework.paging.Page;
import com.chinalbs.framework.paging.Pageable;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@Component
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TestConductor {

	@Resource(name = "conductorServiceImpl")
	private ConductorService cService;

	@Resource(name = "enterpriseToConductorServiceImpl")
	private EnterpriseToConductorService eToConductorService;

	@Test
	public void testSave() {
		Conductor willedSaved = getConductor();
		cService.save(willedSaved);
		Conductor saved = cService.find(willedSaved.getId());
		Assert.assertEquals(willedSaved, saved);
		cService.delete(saved);
		Conductor notExisted = cService.find(saved.getId());
		Assert.assertNull(notExisted);
	}

	private Conductor getConductor() {
		Conductor conductor = new Conductor();
		conductor.setName("手持机1");
		conductor.setSn("asssaaas");
		conductor.setUserId(2321L);
		return conductor;
	}

	@Test(expected = Exception.class)
	public void testFindBySnExpectedExpection() throws Exception {
		Conductor conductor = getConductor();
		cService.save(conductor);
		Conductor conductor2 = getConductor();
		cService.save(conductor2);
		Conductor actual = cService.findBySn(conductor.getSn());
		Assert.assertEquals(conductor, actual);
		cService.delete(actual);
		Conductor notExisted = cService.find(actual.getId());
		Assert.assertNull(notExisted);
	}

	@Test
	public void testExist() {
		boolean notExist1 = cService.existBySn("asssaaas");
		Conductor conductor = getConductor();
		cService.save(conductor);
		boolean isExist = cService.existBySn(conductor.getSn());
		Assert.assertTrue(isExist);
		cService.delete(conductor); 
		Conductor notExist2 = cService.find(conductor.getId());
		Assert.assertNull(notExist2);
		boolean notExist = cService.existBySn(conductor.getSn());
		Assert.assertFalse(notExist);
	}

//	@Test
//	public void testSaveConductorAndEnterpriseToConductor() {
//		User user = getUser();
//		Conductor conductor = getConductor();
//		cService.saveConductorAndEnterprise(user, conductor);
//		Conductor actual = cService.find(conductor.getId());
//		Assert.assertEquals(conductor, actual);
//		EnterpriseToConductor enterpriseToConductor = eToConductorService
//				.findByEnterpriseId(user.getId());
//		testEnterpriseToConductor(user, conductor, enterpriseToConductor);
//		cService.de
//		//TOOD DELETE
//	}

	@Test
	public void testSaveEnterprise() {
		EnterpriseToConductor eToConductor = getEnterpriseToConductor();
		eToConductorService.save(eToConductor);
		EnterpriseToConductor actual = eToConductorService.find(eToConductor
				.getId());
		Assert.assertEquals(eToConductor, actual);
	}

	private EnterpriseToConductor getEnterpriseToConductor() {
		EnterpriseToConductor enterpriseToConductor = new EnterpriseToConductor();
		enterpriseToConductor.setConductorId(12L);
		enterpriseToConductor.setCreateTime(new Date());
		enterpriseToConductor.setEnterpriseId(14L);
		return enterpriseToConductor;
	}

	@Test
	public void testGetConductorPage() {

		Conductor conductor = getConductor("sn1","name1");
		cService.save(conductor);
		Conductor conductor2 = getConductor("sn2", "name2");
		cService.save(conductor2);
		User usUser = getUser();
		Pageable pageable = getPageable();
		Page<Conductor> conductors = cService.findByUserId(usUser.getId(),
				pageable);
		assertPages(conductors);
		cService.delete(conductor);
		cService.delete(conductor2);
		Conductor notExistConductor = cService.find(conductor.getId());
		Assert.assertNull(notExistConductor);
		Conductor notExistConductor2 = cService.find(conductor2.getId());
		Assert.assertNull(notExistConductor2);
		
	}
	
	@Test
	public void testSaveConductorWithUser(){
		Conductor conductor = getConductorNoUserId();
		User user = getUser();
		cService.saveWithUserId(conductor,user.getId());
		Conductor actual = cService.find(conductor.getId());
		assertEqualsConductor(actual,conductor,user);		
	}

	private void assertEqualsConductor(Conductor actual, Conductor conductor,
			User user) {
		conductor.setUserId(user.getId());
		Assert.assertEquals(conductor, actual);
		
	}

	private Conductor getConductorNoUserId() {
		Conductor conductor = new Conductor();
		conductor.setName("手持机1");
		conductor.setSn("asssaaas");
		return conductor;
	}

	private Conductor getConductor(String sn,String name) {
		Conductor conductor = new Conductor();
		conductor.setName(name);
		conductor.setSn(sn);
		return conductor;
	}

	private void assertPages(Page<Conductor> conductors) {
		
	}

	private Pageable getPageable() {
		Pageable pageable = new Pageable();
		pageable.setPageNumber(1);
		pageable.setPageSize(1);
		return pageable;
	}

	private User getUser() {
		User user = new User();
		user.setId(321L);
		return user;
	}

	private void testEnterpriseToConductor(User user, Conductor conductor,
			EnterpriseToConductor enterpriseToConductor) {
		Assert.assertNotNull(enterpriseToConductor);
		Assert.assertEquals(user.getId().longValue(),
				enterpriseToConductor.getEnterpriseId());
		Assert.assertEquals(conductor.getId(),
				enterpriseToConductor.getConductorId());
	}

}
