package com.df.order.exception;

import com.df.core.common.exception.BusinessException;

public class OrderException extends BusinessException {
	private static final long serialVersionUID = 1L;

	private static final String REALM = "Order";

	public static final int EMPTY_LINES = 100000;

	public static final int NO_DINING_TIME_FOR_PRESERVED_ORDER = 100001;

	public static final int STORE_IS_NOT_SPECIFIED = 100002;

	public static final int ORDER_NOT_FOUND = 100003;

	public static final int ORDER_IS_CLOSED = 100004;

	public OrderException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public OrderException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public OrderException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static OrderException emptyLinesException() {
		return new OrderException(EMPTY_LINES, "Order must have a line at least.");
	}

	public static OrderException noDiningTimeForPreservedOrder() {
		return new OrderException(NO_DINING_TIME_FOR_PRESERVED_ORDER, "Preserved order must have dining time.");
	}

	public static OrderException orderNotFound(String message) {
		return new OrderException(ORDER_NOT_FOUND, message);
	}

	public static OrderException orderIsClosed(String message) {
		return new OrderException(ORDER_IS_CLOSED, message);
	}
}
