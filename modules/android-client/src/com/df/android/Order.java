package com.df.android;

import java.util.ArrayList;
import java.util.List;

public class Order {
    static class MenuItemOrder {
    	private MenuItem item;
    	public MenuItem getItem() {
			return item;
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
	
	private String id;
	private List<MenuItemOrder> items[] = new ArrayList[MenuItem.dishTypes.length];
	private Order next;
	
	private static Order currentOrder;
	
	public Order() {
		for(int i = 0; i < MenuItem.dishTypes.length; i ++)
			items[i] = new ArrayList<MenuItemOrder>();
		
		setCurrentOrder(this);
	}
	
	public void add(MenuItem item) {
		if(item == null) 
			return;
		
		for(int i = 0; i < MenuItem.dishTypes.length; i ++)
			if(MenuItem.dishTypes[i] == item.getType())
				items[i].add(new MenuItemOrder(item));
	}
	
	public int getCount() {
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
}
