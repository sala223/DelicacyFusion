package com.df.order.service.contract;

import java.util.List;

import com.df.order.entity.Order;

public interface OrderService {

	void createOrder(String storeCode, long userId, Order order);

	Order getOrderByTable(String storeCode, String tableCode);

	Order getOrderById(String storeCode, long orderId);

	List<Order> getOrdersWithServiceCard(String storeCode);

	List<Order> getOrdersWithServiceCard(String storeCode, long userId);
}
