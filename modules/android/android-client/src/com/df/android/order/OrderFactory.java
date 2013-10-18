package com.df.android.order;

import java.util.ArrayList;
import java.util.List;

import com.df.android.entity.Table;

public class OrderFactory {
	private static Order currentOrder = null;
	private static List<OrderCreateListener> createListeners = new ArrayList<OrderCreateListener>();
	
	public static void registerOrderCreateListener(OrderCreateListener listener) {
		createListeners.add(listener);
	}
	
	public static Order createOnsiteOrder(String id, Table table, int headCount) {
		Order order = new OnsiteOrder(id);
		order.setTable(table);
		order.setHeadCount(headCount);
		
		currentOrder = order;
		
		for(OrderCreateListener listener : createListeners) 
			listener.onOrderCreated(order);
		
		return order;
	}
	
	public static Order currentOrder() {
		return currentOrder;
	}
	
	public static void clearCurrentOrder() {
		currentOrder = null;
	}

}
