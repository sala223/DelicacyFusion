package com.df.order.exception;

public class InvalidItemException extends OrderException {

	private static final long serialVersionUID = 1L;

	public static final int INVALID_ITEM = 110000;

	private String itemCode;

	public InvalidItemException(String itemCode, String messageFormat, Object[] args) {
		super(INVALID_ITEM, messageFormat, args);
		this.itemCode = itemCode;
	}

	public InvalidItemException(String itemCode) {
		super(INVALID_ITEM, "Invalid item %s, it doesn't exist or is disabled", itemCode);
		this.itemCode = itemCode;
	}

	public String getItemCode() {
		return itemCode;
	}
}
