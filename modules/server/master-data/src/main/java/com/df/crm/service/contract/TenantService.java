package com.df.crm.service.contract;

import java.util.List;

import com.df.crm.entity.Tenant;

public interface TenantService {

	Tenant createTenant(Tenant tenant, long ownerId);

	Tenant getTenantByCode(String tenantCode);

	Tenant updateTenant(Tenant tenant);

	Tenant findTenantByCode(String tenantCode);

	void deleteTenant(String tenantCode);

	List<Tenant> getTenants(int firstResult, int maxResult);
}
