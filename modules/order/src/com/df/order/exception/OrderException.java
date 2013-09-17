package com.df.order.exception;

import com.df.core.common.exception.BusinessException;

public class OrderException extends BusinessException {
    private static final long serialVersionUID = 1L;

    private static final String REALM = "Order";

    public OrderException(Throwable cause, int errorCode) {
	super(cause, REALM, errorCode);
    }

    public OrderException(Throwable cause, int errorCode, String messageFormat, Object... args) {
	super(cause, REALM, errorCode, messageFormat, args);
    }

    public OrderException(int errorCode, String messageFormat, Object... args) {
	super(null, REALM, errorCode, messageFormat, args);
    }
}
