package com.df.order.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.entity.Item;
import com.df.masterdata.service.contract.ItemServiceInf;
import com.df.order.dao.OrderDao;
import com.df.order.entity.Order;
import com.df.order.entity.OrderLine;
import com.df.order.exception.InvalidItemException;
import com.df.order.exception.OrderException;
import com.df.order.service.contract.OrderServiceInf;

public class OrderServiceImpl implements OrderServiceInf {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ItemServiceInf itemService;

    public void setOrderDao(OrderDao orderDao) {
	this.orderDao = orderDao;
    }

    public void setItemService(ItemServiceInf itemService) {
	this.itemService = itemService;
    }

    @Override
    public void createOrder(long userId, Order order) {
	List<OrderLine> lines = order.getLines();
	if (lines == null) {
	    throw OrderException.emptyLinesException();
	}
	String storeCode = order.getStoreCode();
	ArrayList<String> itemCodes = new ArrayList<String>();
	for (OrderLine line : lines) {
	    String item = line.getItemCode();
	    itemCodes.add(item);
	}
	List<Item> items = itemService.listAvaliableItems(storeCode, itemCodes);
	for (String itemCode : itemCodes) {
	    if (!items.contains(itemCode)) {
		throw new InvalidItemException(itemCode);
	    }
	}
	order.setOwnerId(userId);
	orderDao.insert(order);
    }

    @Override
    public List<Order> getLatestOrders(long userId, int number) {
	return orderDao.getLatestOrders(userId, number);
    }

}
