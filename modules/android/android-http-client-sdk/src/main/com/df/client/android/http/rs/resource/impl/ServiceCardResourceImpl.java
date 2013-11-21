package com.df.client.android.http.rs.resource.impl;

import java.util.List;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.ServiceCard;
import com.df.client.rs.resource.ServiceCardResource;

public class ServiceCardResourceImpl extends RestTemplateResource implements ServiceCardResource {

	@Override
	public ServiceCard[] getTableOccupation() {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/table/occupation";
		return getRestTemplate().getForObject(url, ServiceCard[].class, rc.getTenantCode(), rc.getStoreCode());
	}

	@Override
	public String[] getAvaliableTables() {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/table/avaliable";
		return getRestTemplate().getForObject(url, String[].class, rc.getTenantCode(), rc.getStoreCode());
	}

	@Override
	public ServiceCard acquireTables(List<String> tables) {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/table/acquire";
		return getRestTemplate().postForObject(url, tables, ServiceCard.class, rc.getTenantCode(), rc.getStoreCode());
	}

	@Override
	public void releaseTables(long serviceCardId) {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/table/{serviceCardId}";
		getRestTemplate().delete(url, rc.getTenantCode(), rc.getStoreCode(), serviceCardId);
	}

}
