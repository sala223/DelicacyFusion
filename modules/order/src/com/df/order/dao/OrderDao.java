package com.df.order.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.dao.ItemDao;
import com.df.order.entity.Order;
import com.df.order.entity.OrderLine;

public class OrderDao extends TransactionDataAccessFoundation {

    @Autowired
    private ItemDao itemDao;

    public void setItemDao(ItemDao itemDao) {
	this.itemDao = itemDao;
    }

    public void createOrder(Order order) {
	List<OrderLine> lines = order.getLines();
	String storeCode = order.getStoreCode();
	ArrayList<String> items = new ArrayList<String>();
	for (OrderLine line : lines) {
	    String item = line.getItemCode();
	    items.add(item);
	}
	itemDao.listAvaliableItems(storeCode,items);
	this.insert(order);
    }

    public void updateOrderStatus() {

    }
}
