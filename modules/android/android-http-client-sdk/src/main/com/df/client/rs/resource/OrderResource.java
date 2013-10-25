package com.df.client.rs.resource;

import com.df.client.rs.model.Order;

public interface OrderResource extends Resource {

    public Order createOrder(Order order);

}
