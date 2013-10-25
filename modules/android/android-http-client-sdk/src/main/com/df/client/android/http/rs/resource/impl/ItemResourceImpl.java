package com.df.client.android.http.rs.resource.impl;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.Item;
import com.df.client.rs.resource.AbstractResource;
import com.df.client.rs.resource.ItemResource;

public class ItemResourceImpl extends AbstractResource implements ItemResource {

    private RestTemplate restTemplate;

    public ItemResourceImpl() {
	restTemplate = new RestTemplate();
	restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public Item[] getItems() {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/{storeCode}/item";
	return restTemplate.getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode());
    }

    @Override
    public Item[] getFoods() {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/{storeCode}/food";
	return restTemplate.getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode());
    }

    @Override
    public Item[] getItemsByCategory(String categoryCode) {
	return null;
    }

}
