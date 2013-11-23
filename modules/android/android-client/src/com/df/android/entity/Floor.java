package com.df.android.entity;

import java.util.ArrayList;
import java.util.List;

import com.df.client.rs.model.DiningTable;

public class Floor {
	private int floor = 1;
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	private List<DiningTable> tables = new ArrayList<DiningTable>();
	
	public void addTable(DiningTable table) {
		tables.add(table);
	}
	
	public List<DiningTable> getTables() {
		return tables;
	}
}
