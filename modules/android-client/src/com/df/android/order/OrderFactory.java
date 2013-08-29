package com.df.android.order;

import com.df.android.entity.Table;

public class OrderFactory {
	private static Order currentOrder = null;
	
	public static Order createOnsiteOrder(String id, Table table, int headCount) {
		Order order = new OnsiteOrder(id);
		order.setTable(table);
		order.setHeadCount(headCount);
		
		currentOrder = order;
		
		return order;
	}
	
	public static Order currentOrder() {
		return currentOrder;
	}
	
	public static void clearCurrentOrder() {
		currentOrder = null;
	}

}
