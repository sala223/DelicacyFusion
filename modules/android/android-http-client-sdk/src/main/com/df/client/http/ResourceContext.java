package com.df.client.http;

import java.io.Serializable;

public class ResourceContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userCode;

    private String tenantCode;

    private String storeCode;

    private String targetUrl;

    public String getUserCode() {
	return userCode;
    }

    public void setUserCode(String userCode) {
	this.userCode = userCode;
    }

    public String getTenantCode() {
	return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
	this.tenantCode = tenantCode;
    }

    public String getStoreCode() {
	return storeCode;
    }

    public void setStoreCode(String storeCode) {
	this.storeCode = storeCode;
    }

    public String getTargetUrl() {
	return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
	this.targetUrl = targetUrl;
    }

}
