package com.df.order.service.contract;

import java.util.List;

import com.df.order.entity.Order;
import com.df.order.entity.TransactionStatus;

public interface OrderService {

	void createOrder(String storeCode, long userId, Order order);

	Order getOrderByTable(String storeCode, String tableCode);

	Order getOrderById(String storeCode, long orderId);

	void updateOrderStatus(String storeCode, Order order, TransactionStatus status);

	List<Order> getOrdersWithServiceCard(String storeCode);

	List<Order> getOrdersWithServiceCard(String storeCode, long userId);
}
