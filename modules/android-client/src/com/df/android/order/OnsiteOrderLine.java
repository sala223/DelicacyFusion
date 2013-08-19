package com.df.android.order;

import com.df.android.entity.Item;
import com.df.android.entity.Table;

public class OnsiteOrderLine extends OrderLine {
	private Table table; 
	
	public OnsiteOrderLine(Item item, Table table) {
		super(item);
		this.table = table;
	}
	
	public OnsiteOrderLine(Item item, float price, Table table) {
		super(item, price);
		this.table = table;
	}
	
	public Table getTable() {
		return table;
	}
}
