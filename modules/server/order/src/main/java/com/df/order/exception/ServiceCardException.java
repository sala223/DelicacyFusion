package com.df.order.exception;

import com.df.core.common.exception.BusinessException;

public class ServiceCardException extends BusinessException {
	private static final long serialVersionUID = 1L;

	private static final String REALM = "ServiceCard";

	public static final int INVALID_SERVICE_CARD_ID = 100000;

	public ServiceCardException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public ServiceCardException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public ServiceCardException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

}
