package com.df.client.android.http.rs.resource.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.Item;
import com.df.client.rs.model.PictureRef;
import com.df.client.rs.resource.ItemResource;

public class ItemResourceImpl extends RestTemplateResource implements ItemResource {

	@Override
	public Item[] getItems() {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/item";
		return getRestTemplate().getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode());
	}

	@Override
	public Item[] getFoods() {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/food";
		return getRestTemplate().getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode());
	}

	@Override
	public Item[] getFoodsByCategory(String categoryCode) {
		ResourceContext rc = this.getResourceContext();
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/{categoryCode}/food";
		return getRestTemplate().getForObject(url, Item[].class, rc.getTenantCode(), rc.getStoreCode(), categoryCode);
	}

	public InputStream getItemImage(Item item, String imageId) {
		ResourceContext rc = this.getResourceContext();
		PictureRef pic = item.getImageByImageId(imageId);
		if (pic == null) {
			return null;
		}
		String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/item/{itemCode}/image/{iamgeId}";
		byte[] imageData = getRestTemplate().getForObject(url, byte[].class, rc.getTenantCode(), rc.getStoreCode(),
		        item.getCode(), imageId);
		return new ByteArrayInputStream(imageData);
	}
}
