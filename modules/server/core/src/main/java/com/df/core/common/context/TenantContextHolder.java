package com.df.core.common.context;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;

public abstract class TenantContextHolder {

	private static ThreadLocal<TenantContext> tenants;

	private static ThreadLocal<TenantContext> inheritableTenants;

	static {
		tenants = new NamedThreadLocal<TenantContext>("Tenant Context");
		inheritableTenants = new NamedInheritableThreadLocal<TenantContext>("Tenant Context");
	}

	public static void reset() {
		tenants.remove();
		inheritableTenants.remove();
	}

	public static TenantContext getTenant() {
		TenantContext tenantId = tenants.get();
		if (tenantId == null) {
			tenantId = inheritableTenants.get();
		}
		return tenantId;
	}

	public static void setTenant(TenantContext tenantContext) {
		setTenant(tenantContext, false);
	}

	public static void setTenant(TenantContext tenantContext, boolean inheritable) {
		if (tenantContext == null) {
			reset();
		} else {
			if (inheritable) {
				inheritableTenants.set(tenantContext);
				tenants.remove();
			} else {
				tenants.set(tenantContext);
				inheritableTenants.remove();
			}
		}
	}
}
