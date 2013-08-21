package com.df.android.entity;



public class Table {
	private String id;
	private int capacity;
	private static Table currentTable;
	
	public static Table getCurrentTable() {
		return currentTable;
	}

	public Table(String id, int capacity) {
		this.id = id;
		this.capacity = capacity;
		currentTable = this;
	}
	
	public String getId() {
		return id;
	}

	public boolean equals(Table another) {
		return id.equals(another.getId());
	}

	public int getCapacity() {
		return capacity;
	}

}
