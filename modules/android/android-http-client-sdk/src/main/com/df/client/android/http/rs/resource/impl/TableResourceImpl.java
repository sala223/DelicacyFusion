package com.df.client.android.http.rs.resource.impl;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.DiningTable;
import com.df.client.rs.resource.TableResource;

public class TableResourceImpl extends RestTemplateResource implements TableResource {

	@Override
	public DiningTable[] getTables() {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/table";
		return getRestTemplate().getForObject(url, DiningTable[].class, rc.getTenantCode(), rc.getStoreCode());
	}
}
