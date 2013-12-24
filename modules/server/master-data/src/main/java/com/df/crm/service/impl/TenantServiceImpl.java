package com.df.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.df.crm.dao.TenantDao;
import com.df.crm.entity.Tenant;
import com.df.crm.exception.TenantException;
import com.df.crm.service.contract.TenantService;

@Transactional
public class TenantServiceImpl implements TenantService {

	@Autowired
	private TenantDao tenantDao;
	
	@Override
	public Tenant createTenant(Tenant tenant) {
		return tenantDao.newTenant(tenant);
	}

	@Override
	public List<Tenant> getTenants(int firstResult, int maxResult) {
		return tenantDao.getTenants(firstResult, maxResult, false);
	}

	@Override
	public void deleteTenant(String tenantCode) {
		boolean isSuccess = tenantDao.disableTenant(tenantCode);
		if (!isSuccess) {
			throw TenantException.tenantWithCodeNotFound(tenantCode);
		}
	}

	@Override
	public Tenant findTenantByCode(String tenantCode) {
		return tenantDao.findTenantByCode(tenantCode);
	}

	@Override
	public Tenant updateTenant(Tenant tenant) {
		Tenant found = tenantDao.findTenantByCode(tenant.getCode());
		if (found == null || !found.isEnabled()) {
			throw TenantException.tenantWithCodeNotFound(tenant.getCode());
		}
		if (!tenant.getName().equals(found.getName())) {
			if (tenantDao.findTenantByName(tenant.getName()) != null) {
				throw TenantException.tenantWithNameAlreadyExist(tenant.getName());
			}
		}
		found.setName(tenant.getName());
		found.setDescription(tenant.getDescription());
		found.setAddress(tenant.getAddress());
		tenantDao.update(found);
		return found;
	}

	@Override
	public Tenant getTenantByCode(String tenantCode) {
		Tenant found = tenantDao.findTenantByCode(tenantCode);
		if (found == null) {
			throw TenantException.tenantWithCodeNotFound(tenantCode);
		}
		return found;
	}

}
