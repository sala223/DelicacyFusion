package com.df.client.android.http.rs.resource.impl;

import org.springframework.web.client.RestTemplate;

import android.util.Log;

import com.df.client.http.ResourceContext;
import com.df.client.rs.model.Order;
import com.df.client.rs.resource.OrderResource;

public class OrderResourceImpl extends RestTemplateResource implements OrderResource {

    @Override
    public Order createOrder(Order order) {
	ResourceContext rc = this.getResourceContext();
	String url = rc.getTargetUrl() + "/tenant/{tenantCode}/{storeCode}/order";
	RestTemplate rt = this.getRestTemplate();
	Log.d(getClass().getName(), "Post createorder to " + url + ", order: " + order);
	Order ro = rt.postForObject(url, order, Order.class, rc.getTenantCode(), rc.getStoreCode());
	Log.d(getClass().getName(), "Result: " + ro);
	
	return ro;
    }

}
