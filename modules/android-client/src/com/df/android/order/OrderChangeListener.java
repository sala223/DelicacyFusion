package com.df.android.order;


public interface OrderChangeListener {
	void onLineAdded(OrderLine line);
	
	void onLineRemoved(OrderLine line);
}
