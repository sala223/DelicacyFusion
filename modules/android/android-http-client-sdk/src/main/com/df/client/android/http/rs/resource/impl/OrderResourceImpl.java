package com.df.client.android.http.rs.resource.impl;

import org.springframework.web.client.RestTemplate;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.Order;
import com.df.client.rs.resource.OrderResource;

public class OrderResourceImpl extends RestTemplateResource implements OrderResource {

    @Override
    public Order createOrder(Order order) {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/store/{storeCode}/order";
	RestTemplate rt = this.getRestTemplate();
	return rt.postForObject(url, order, Order.class, rc.getTenantCode(), rc.getStoreCode());
    }

}
