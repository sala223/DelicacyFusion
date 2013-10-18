package com.df.core.common.context;

public class TenantContext implements TenantAware {

    private String tenantId;

    public TenantContext(String tenantId) {
	this.tenantId = tenantId;
    }

    public void setTenantId(String tenantId) {
	this.tenantId = tenantId;
    }

    @Override
    public String getTenantId() {
	return tenantId;
    }

}
