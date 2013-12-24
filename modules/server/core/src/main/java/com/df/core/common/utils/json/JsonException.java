package com.df.core.common.utils.json;

import com.df.core.common.exception.BaseException;

public class JsonException extends BaseException {

	private static final long serialVersionUID = 1L;

	public JsonException(Throwable cause) {
		super(cause);
	}

	public JsonException(String format, Object... args) {
		super(format, args);
	}

	public JsonException(Throwable cause, String format, Object... args) {
		super(cause, format, args);
	}
}
