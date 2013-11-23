package com.df.order.exception;

import com.df.core.common.exception.BusinessException;

public class PaymentException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "Payment";

	public static final int PAYMENT_NOT_FOUND = 100001;

	public static final int PAYMENT_NOT_CREATED = 100002;

	public static final int BILL_EXCEED_ORDER_TOTAL = 100003;

	public PaymentException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public PaymentException(Throwable cause, int errorCode, String messageFormat, Object... args) {
		super(cause, REALM, errorCode, messageFormat, args);
	}

	public PaymentException(int errorCode, String messageFormat, Object... args) {
		super(null, REALM, errorCode, messageFormat, args);
	}

	public static PaymentException paymentNotFound(String message) {
		return new PaymentException(PAYMENT_NOT_FOUND, message);
	}

	public static PaymentException paymentNotCreated(long order) {
		return new PaymentException(PAYMENT_NOT_CREATED, "Payment is not created for order " + order);
	}

	public static PaymentException billExceedsOrderTotal() {
		String msg = "Bill exceeds order total amount.";
		return new PaymentException(BILL_EXCEED_ORDER_TOTAL, msg);
	}
}
