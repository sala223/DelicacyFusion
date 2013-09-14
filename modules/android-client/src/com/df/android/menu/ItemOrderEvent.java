package com.df.android.menu;

import com.df.android.entity.Item;


public class ItemOrderEvent {
	private Item item; 
	
	public Item getItem() {
		return item;
	}

	public ItemOrderEvent(Item item) {
		this.item = item;
	}
	
}
