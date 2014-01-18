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
import com.df.idm.entity.RoleId;
import com.df.idm.entity.User;
import com.df.idm.service.contract.UserManagementService;

@Transactional
public class TenantServiceTest extends CRMJPABaseTest {

	@Autowired
	private TenantService tenantService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserManagementService userManagementService;

	@Test
	public void newTenant() {
		User user = new User("testUser1@test.com", "123456");
		user = userManagementService.createUser(user);
		entityManager.flush();
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan Limit");
		tenant.setAddress("testAddress");
		tenant = tenantService.createTenant(tenant, user.getId());
		entityManager.flush();
		user = userManagementService.getUserById(user.getId());
		TestCase.assertEquals(user.isTenantUser(), true);
		TestCase.assertEquals(tenant.getOwner(), user.getId());
		TestCase.assertEquals(user.getRoles().get(0).getName(), RoleId.TENANT_ADMIN_NAME);
	}

	@Test(expected = TenantException.class)
	public void newTenantWithSameName() {
		User user = new User("testUser2@test.com", "123456");
		user = userManagementService.createUser(user);
		entityManager.flush();
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan Limit");
		tenant.setAddress("PuDong Area");
		tenantService.createTenant(tenant, user.getId());
		tenant = new Tenant();
		tenant.setCode("TestCode2");
		tenant.setName("WangXiangYuan Limit");
		tenant.setAddress("PuDong Area");
		tenantService.createTenant(tenant, 1);
	}

	@Test(expected = TenantException.class)
	public void newTenantWithSameCode() {
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan Limit");
		tenant.setAddress("PuDong Area");
		tenantService.createTenant(tenant, 1);
		tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setName("WangXiangYuan2 Limit");
		tenant.setAddress("PuDong Area");
		tenantService.createTenant(tenant, 1);
	}

	@Test
	public void getTenants() {
		User user = new User("testUser3@test.com", "123456");
		user = userManagementService.createUser(user);
		entityManager.flush();
		int previous = tenantService.getTenants(0, Integer.MAX_VALUE).size();
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setAddress("PuDong Area");
		tenant.setName("WangXiangYuan Limit");
		tenantService.createTenant(tenant, user.getId());
		user = new User("testUser4@test.com", "123456");
		user = userManagementService.createUser(user);
		entityManager.flush();
		Tenant tenant2 = new Tenant();
		tenant2.setCode("TestCode2");
		tenant2.setAddress("PuDong Area");
		tenant2.setName("WangXiangYuan2 Limit");
		tenantService.createTenant(tenant2, user.getId());
		entityManager.flush();
		TestCase.assertTrue(tenantService.getTenants(0, 100).size() == 2 + previous);
	}

	@Test
	public void removeTenants() {
		User user = new User("testUser3@test.com", "123456");
		user = userManagementService.createUser(user);
		entityManager.flush();
		int previous = tenantService.getTenants(0, Integer.MAX_VALUE).size();
		Tenant tenant = new Tenant();
		tenant.setCode("TestCode");
		tenant.setAddress("Pudong Area");
		tenant.setName("WangXiangYuan Limit");
		tenantService.createTenant(tenant, user.getId());
		tenantService.deleteTenant(tenant.getCode());
		TestCase.assertTrue(tenantService.getTenants(0, Integer.MAX_VALUE).size() == previous);
	}
}
