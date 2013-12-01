package com.df.crm.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.crm.entity.Tenant;
import com.df.crm.exception.TenantException;
import com.df.crm.service.contract.TenantService;

@Transactional
public class TenantServiceTest extends CRMJPABaseTest {

	@Autowired
	private TenantService tenantService;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void newTenant() {
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan Limit");
		tenantService.createTenant(tenant);
	}

	@Test(expected = TenantException.class)
	public void newTenantWithSameName() {
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan Limit");
		tenantService.createTenant(tenant);
		tenant = new Tenant();
		tenant.setCode("TestCode2");
		tenant.setName("WangXiangYuan Limit");
		tenantService.createTenant(tenant);
	}
	
	@Test(expected = TenantException.class)
	public void newTenantWithSameCode() {
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan Limit");
		tenantService.createTenant(tenant);
		tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan2 Limit");
		tenantService.createTenant(tenant);
	}

	@Test
	public void getTenants() {
		int previous = tenantService.getTenants(0, Integer.MAX_VALUE).size();
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan Limit");
		tenantService.createTenant(tenant);
		Tenant tenant2 = new Tenant();
		tenant2.setCode("TestCode2");
		tenant2.setName("WangXiangYuan2 Limit");
		tenantService.createTenant(tenant2);
		entityManager.flush();
		TestCase.assertTrue(tenantService.getTenants(0, 100).size() == 2 + previous);
	}

	@Test
	public void removeTenants() {
		int previous = tenantService.getTenants(0, Integer.MAX_VALUE).size();
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan Limit");
		tenantService.createTenant(tenant);
		tenantService.deleteTenant(tenant.getCode());
		TestCase.assertTrue(tenantService.getTenants(0, Integer.MAX_VALUE).size() == previous);
	}
}
