package com.df.client.rs.resource.test;

import android.test.suitebuilder.annotation.MediumTest;

import com.df.client.rs.model.Order;
import com.df.client.rs.resource.OrderResource;
import com.google.gson.Gson;

public class OrderResourceTest extends AbstractResourceTest {

    @MediumTest
    public void testCreateOrder() {
	OrderResource oResource = this.getClient().getResource(OrderResource.class);
	Order order = new Order();
	order.setCurrency("RMB");
//	order.addOrderLine(new OrderLine("A0001"));
	order = oResource.createOrder(order);
	Gson gson = new Gson();
	this.logInfo(gson.toJson(order));
    }
}
