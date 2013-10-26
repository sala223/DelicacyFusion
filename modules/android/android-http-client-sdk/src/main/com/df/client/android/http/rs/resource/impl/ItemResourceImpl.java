package com.df.client.android.http.rs.resource.impl;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.Item;
import com.df.client.rs.resource.ItemResource;

public class ItemResourceImpl extends RestTemplateResource implements ItemResource {

    @Override
    public Item[] getItems() {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/{storeCode}/item";
	return getRestTemplate().getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode());
    }

    @Override
    public Item[] getFoods() {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/{storeCode}/food";
	return getRestTemplate().getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode());
    }

    @Override
    public Item[] getFoodsByCategory(String categoryCode) {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/{storeCode}/{categoryCode}/food";
	return getRestTemplate().getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode(), categoryCode);
    }
}
