package com.df.order.exception;

public class TableIsOccupiedException extends OrderException {

	private static final long serialVersionUID = 1L;

	private String occupiedTable;

	public TableIsOccupiedException(String occupiedTable) {
		super(120000, "Table %s is occupied or is not a valid table.", occupiedTable);
		this.occupiedTable = occupiedTable;
	}

	public String getOccupiedTable() {
		return occupiedTable;
	}
}
