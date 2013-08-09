package com.df.android;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter {
	Context cxt;
	Order order;
	
	public OrderAdapter(Context cxt) {
		this.cxt = cxt;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Override
	public int getCount() {
		return order.getCount();
	}

	@Override
	public Object getItem(int index) {
		return order.getItem(index);
	}

	@Override
	public View getView(int index, View arg1, ViewGroup arg2) {
		Order.MenuItemOrder item = (Order.MenuItemOrder)getItem(index);
		
		TextView tv = new TextView(cxt);
		tv.setText(item.getItem().getName());
		
		return tv;
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

}

