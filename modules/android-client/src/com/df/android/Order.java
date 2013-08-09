package com.df.android;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Order {
	private String id;
	private List<MenuItemOrder> items[] = new ArrayList[MenuItem.dishTypes.length];
	private Order next;
	
	private static Order currentOrder;
	private List<OrderChangeListener> changeListeners = new ArrayList<OrderChangeListener>();
	
	public Order() {
		for(int i = 0; i < MenuItem.dishTypes.length; i ++)
			items[i] = new ArrayList<MenuItemOrder>();
		
		setCurrentOrder(this);
	}
	
	public void add(MenuItem item) {
		if(item == null) 
			return; 
		
    	Log.d(getClass().getName(), "Adding item " + item.getName());
    	
    	boolean exists = false;
		for(int i = 0; i < MenuItem.dishTypes.length; i ++) {
			if(MenuItem.dishTypes[i] == item.getType()) {
				for(MenuItemOrder mio : items[i]) {
					if(item.equals(mio.getItem())) {
						mio.setCopies(mio.getCopies()+1);
						exists = true;
					}
				}

				if(!exists)
					items[i].add(new MenuItemOrder(item));
				
				onMenuItemAdded(item);
			}
		}
	}
	
	public int getCount() {
		int count = 0;
		for(int i = 0; i < MenuItem.dishTypes.length; i ++)
			for(MenuItemOrder item : items[i])
				count += item.getCopies();
		
		return count;
	}
	
	public int getDistinctCount() {
		int count = 0;
		for(int i = 0; i < MenuItem.dishTypes.length; i ++)
			count += items[i].size();
		
		return count;
	}
	
	public MenuItemOrder getItem(int index) {
		int pos = index;
		for(int i = 0; i < MenuItem.dishTypes.length; i ++) {
			if(pos >= items[i].size())
				pos -= items[i].size();
			else
				return items[i].get(pos);
		}
		
		return null;
	}
	
	public static void setCurrentOrder(Order order) {
		currentOrder = order;
	}
	
	public static Order currentOrder() {
		return currentOrder;
	}
	
	public void registerChangeListener(OrderChangeListener listener) {
		changeListeners.add(listener);
	}
	
	private void onMenuItemAdded(MenuItem item) {
    	Log.d(getClass().getName(), "Item " + item.getName() + " added");
		for(OrderChangeListener listener : changeListeners) {
			listener.onMenuItemAdded(item);
		}
	}
	
	private void onMenuItemRemoved(MenuItem item) {
		for(OrderChangeListener listener : changeListeners) {
			listener.onMenuItemRemoved(item);
		}
	}
	
    static class MenuItemOrder {
    	private MenuItem item;
    	public MenuItem getItem() {
			return item;
		}

    	public void setCopies(int copies) {
    		this.copies = copies;
    	}
    	
		public int getCopies() {
			return copies;
		}

		public String getComments() {
			return comments;
		}

		private int copies = 1;
    	private String comments = "";
    	
    	public MenuItemOrder(MenuItem item) {
    		this.item = item;
    	}
    }
}
