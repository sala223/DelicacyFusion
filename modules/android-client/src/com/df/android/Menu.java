package com.df.android;

import java.util.ArrayList;
import java.util.List;

public class Menu {
	private List<MenuItem> items = new ArrayList<MenuItem>();
	
	public void addItem(MenuItem item) {
		items.add(item);
	}
	
	public List<MenuItem> getItems() { 
		return items;
	}
}
