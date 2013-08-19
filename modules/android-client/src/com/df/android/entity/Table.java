package com.df.android.entity;



public class Table {
	private String id;
	private static Table currentTable;
	
	public static Table getCurrentTable() {
		return currentTable;
	}

	public Table(String id) {
		this.id = id;
		currentTable = this;
	}
	
	public String getId() {
		return id;
	}

	public boolean equals(Table another) {
		return id.equals(another.getId());
	}
	
}
