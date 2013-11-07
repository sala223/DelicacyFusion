package com.df.order.service.contract;

import java.util.List;

import com.df.order.entity.Order;

public interface OrderService {

    void createOrder(long userId, Order order);

    List<Order> getLatestOrders(long userId, int number);
}
