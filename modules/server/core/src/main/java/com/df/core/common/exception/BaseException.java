package com.df.core.common.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String format, Object... args) {
		super(String.format(format, args));
	}

	public BaseException(Throwable cause, String format, Object... args) {
		super(String.format(format, args), cause);
	}
}
