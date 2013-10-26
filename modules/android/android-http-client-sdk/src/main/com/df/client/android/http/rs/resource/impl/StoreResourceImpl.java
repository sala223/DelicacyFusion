package com.df.client.android.http.rs.resource.impl;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.Store;
import com.df.client.rs.resource.StoreResource;

public class StoreResourceImpl extends RestTemplateResource implements StoreResource {

    @Override
    public Store[] getStores() {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store";
	return this.getRestTemplate().getForObject(url, Store[].class, rc.getTenantCode());
    }

}
