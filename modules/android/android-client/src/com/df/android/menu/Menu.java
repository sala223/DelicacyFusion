package com.df.android.menu;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.df.android.entity.Item;

public class Menu {
	private Map<String, Item> items = new HashMap<String, Item>();
	
	public void addItem(Item item) {
		items.put(item.getCode(), item);
	}
	
	public Collection<Item> getItems() { 
		return items.values();
	}
	
	public Item getItemByCode(String itemCode) {
		return items.get(itemCode);
	}
}
