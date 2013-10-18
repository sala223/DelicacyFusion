package com.df.android.menu;

import java.util.ArrayList;
import java.util.List;

import com.df.android.entity.Item;

public class Menu {
	private List<Item> items = new ArrayList<Item>();
	
	public void addItem(Item item) {
		items.add(item);
	}
	
	public List<Item> getItems() { 
		return items;
	}
}
