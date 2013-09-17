package com.df.order.service.contract;

import com.df.order.entity.Order;

public interface OrderServiceInf {

    void createOrder(long userId, Order order);
}
