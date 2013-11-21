package com.df.client.android.http.rs.resource.impl;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.CategoryNavigationTabs;
import com.df.client.rs.resource.ConfigurationResource;

public class ConfigurationResourceImpl extends RestTemplateResource implements ConfigurationResource {

	@Override
	public CategoryNavigationTabs getCategoryNavigationTabs() {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/configuration/cnv";
		return getRestTemplate().getForObject(url, CategoryNavigationTabs.class, rc.getTenantCode(), rc.getStoreCode());
	}

}
