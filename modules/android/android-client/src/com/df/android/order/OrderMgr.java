package com.df.android.order;

import java.util.ArrayList;
import java.util.List;

public class OrderMgr {
	private static Order currentOrder = null;
	private static List<OrderCreateListener> createListeners = new ArrayList<OrderCreateListener>();
	private static List<OrderChangeListener> changeListeners = new ArrayList<OrderChangeListener>();

	public static void registerOrderCreateListener(OrderCreateListener listener) {
		createListeners.add(listener);
	}
	
	public static void registerChangeListener(OrderChangeListener listener) {
		changeListeners.add(listener);
	}

	public static Order createOrder() {
		Order order = new Order();
		
		currentOrder = order;
		
		for(OrderCreateListener listener : createListeners) 
			listener.onOrderCreated(order);
		
		return order;
	}
	
	public static void addOrderLine(Order order, OrderLine line) { 
		List<OrderLine> lines = order.getLines();
		int index = lines.indexOf(line);
		if(index >= 0) { 
			OrderLine l = lines.get(index);
			if(l != null)
				l.setQuantity(l.getQuantity() + 1);
		}
		else
			lines.add(line);
		
		onLineAdded(line);
	}

	public static void removeOrderLine(Order order, OrderLine line) {
		List<OrderLine> lines = order.getLines();
		int index = lines.indexOf(line);
		if(index < 0)
			return;
		
		OrderLine l = lines.get(index);
		if(l == null || l.getQuantity() <= 1)
			lines.remove(line);
		else
			l.setQuantity(l.getQuantity() - 1);
		
		onLineRemoved(line);
	}
	
	private static void onLineAdded(OrderLine line) {
		for (OrderChangeListener listener : changeListeners) {
			listener.onLineAdded(line);
		}
	}

	private static void onLineRemoved(OrderLine line) {
		for (OrderChangeListener listener : changeListeners) {
			listener.onLineRemoved(line);
		}
	}
	
	public static Order currentOrder() {
		return currentOrder;
	}
	
	public static void clearCurrentOrder() {
		currentOrder = null;
	}

}
