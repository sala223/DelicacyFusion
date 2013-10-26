package com.df.order.exception;

import com.df.core.common.exception.BusinessException;

public class OrderException extends BusinessException {
    private static final long serialVersionUID = 1L;

    private static final String REALM = "Order";

    public static final int EMPTY_LINES = 100000;

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
}
