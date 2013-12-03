package com.df.core.rs;

import com.df.core.common.context.TenantContext;
import com.df.core.common.context.TenantContextHolder;

public abstract class TenantLevelResource {

	protected void injectTenantContext(String tenantCode) {
		TenantContext context = new TenantContext(tenantCode);
		TenantContextHolder.setTenant(context);
	}
}
