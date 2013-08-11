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
	
	public Order(String id) {
		this.id = id;
		
		for(int i = 0; i < MenuItem.dishTypes.length; i ++)
			items[i] = new ArrayList<MenuItemOrder>();
		
		setCurrentOrder(this);
	}
	
	public String getId() {
		return id;
	}
	
	public void add(MenuItemOrder item) {
		if(item == null) 
			return; 
		
    	Log.d(getClass().getName(), "Adding item " + item.getItem().getName());
    	
    	boolean exists = false;
		for(int i = 0; i < MenuItem.dishTypes.length; i ++) {
			if(MenuItem.dishTypes[i] == item.getItem().getType()) {
				for(MenuItemOrder mio : items[i]) {
					if(item.equals(mio)) {
						mio.setCopies(mio.getCopies()+1);
						exists = true;
					}
				}

				if(!exists)
					items[i].add(item);
				
				onMenuItemAdded(item);
			}
		}
	}

	public void remove(MenuItemOrder item) {
		if(item == null) 
			return; 
		
    	Log.d(getClass().getName(), "Removing item " + item.getItem().getName());
    	
    	boolean exists = false;
		for(int i = 0; i < MenuItem.dishTypes.length; i ++) {
			if(MenuItem.dishTypes[i] == item.getItem().getType()) {
				for(MenuItemOrder mio : items[i]) {
					if(item.equals(mio)) {
						if(mio.getCopies() > 1)
							mio.setCopies(mio.getCopies()-1);
						else
							exists = true;
					}
				}

				if(exists)
					items[i].remove(item);
				
				onMenuItemRemoved(item);
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
	
	private void onMenuItemAdded(MenuItemOrder item) {
		for(OrderChangeListener listener : changeListeners) {
			listener.onMenuItemAdded(item.getItem());
		}
	}
	
	private void onMenuItemRemoved(MenuItemOrder item) {
		for(OrderChangeListener listener : changeListeners) {
			listener.onMenuItemRemoved(item.getItem());
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
    	
    	public boolean equals(MenuItemOrder o) {
    		if(o == null)
    			return false;
    		
    		return item.equals(o.getItem());
    	}
    }
}
