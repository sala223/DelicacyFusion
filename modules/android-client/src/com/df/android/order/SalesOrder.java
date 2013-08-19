package com.df.android.order;

public class SalesOrder extends Order {
	public SalesOrder(String id, OrderType type) {
		super(id);
		this.type = type;
	}
}
