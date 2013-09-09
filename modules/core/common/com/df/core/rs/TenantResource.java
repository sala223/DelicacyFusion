package com.df.core.rs;

import com.df.core.common.context.TenantContext;
import com.df.core.common.context.TenantContextHolder;

public abstract class TenantResource {

    protected void injectTenantContext(String tenantId) {
	TenantContext context = new TenantContext(tenantId);
	TenantContextHolder.setTenant(context);
    }
}
