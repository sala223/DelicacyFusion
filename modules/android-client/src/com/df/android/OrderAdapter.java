package com.df.android;

import android.content.Context;
import android.graphics.Color;
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
		return order.getDistinctCount();
	}

	@Override
	public Object getItem(int index) {
		return order.getItem(index);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		Order.MenuItemOrder item = (Order.MenuItemOrder)getItem(position);
		
        if(convertView != null)
        	convertView = null;
 
        View view = new TextView(cxt);
        view.setBackgroundColor(Color.WHITE);
        ((TextView)view).setText(item.getItem().getName() + "(" + item.getItem().getType() + ")" + item.getCopies());
        
        return view;
    }

	@Override
	public long getItemId(int index) {
		return index;
	}

}

