package com.df.android.order;

import com.df.android.entity.Item;
import com.df.android.entity.Table;

public class OrderLine {
	private Item item;
	private float price = 0.0f;
	private int quantity = 1;
	private String comments;
	private Table table; 
	
	public OrderLine(Item item) {
		this(item, item.getPrice());
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
		if(price <= 0) 
			return item.getPrice();
		
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
		return getPrice()*quantity;
	}
	
	public void setTable(Table table) {
		this.table = table;
	}
	public Table getTable() {
		return table;
	}
	
	public int compare(OrderLine another) {
		int ret = table.getId().compareToIgnoreCase(another.getTable().getId());
		
		return ret;
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && table.equals(((OrderLine)o).getTable()) && item.equals(((OrderLine)o).getItem());
	}
	
	public String toString() {
		String ret = "Line{";
		
		ret += "item:" + item + ",";
		ret += "price:" + getPrice() + ",";
		ret += "quantity:" + quantity + ",";
		
		return ret;
	}
}