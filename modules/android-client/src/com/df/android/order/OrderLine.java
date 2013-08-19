package com.df.android.order;

import com.df.android.entity.Item;

public class OrderLine {
	private Item item;
	private float price;
	private int quantity = 1;
	private String comments;
	
	public OrderLine(Item item) {
		this(item, item.getActualPrice());
	}
	
	public OrderLine(Item item, float price) {
		this(item, price, null);
	}
	
	public OrderLine(Item item, float price, String comments) {
		this.item = item;
		this.price = price;
		this.comments = comments;
	}

	public Item getItem() {
		return item;
	}

	public float getPrice() {
		return price;
	}

	public String getComments() {
		return comments;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public float getTotal() {
		return price*quantity;
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && item.equals(((OrderLine)o).getItem());
	}
	
	public String toString() {
		String ret = "Line{";
		
		ret += "item:" + item + ",";
		ret += "price:" + price + ",";
		ret += "quantity:" + quantity + ",";
		
		return ret;
	}
}