package com.df.core.common.context;

public class TenantContext implements TenantAware {

	private String tenantCode;

	public TenantContext(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public void setTenantId(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	@Override
	public String getTenantCode() {
		return tenantCode;
	}

}
