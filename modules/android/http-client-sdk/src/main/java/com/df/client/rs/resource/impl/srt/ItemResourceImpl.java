package com.df.client.rs.resource.impl.srt;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.Item;
import com.df.client.rs.resource.AbstractResource;
import com.df.client.rs.resource.ItemResource;

public class ItemResourceImpl extends AbstractResource implements ItemResource {

    @Override
    public Item[] getItems() {
	RestTemplate restTemplate = new RestTemplate();
	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/{storeCode}/item";
	return restTemplate.getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode());
    }

    @Override
    public Item[] getFoods() {
	RestTemplate restTemplate = new RestTemplate();
	restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/{storeCode}/food";
	return restTemplate.getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode());
    }

    @Override
    public Item[] getItemsByCategory(String categoryCode) {
	return null;
    }

}
