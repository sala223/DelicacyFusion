package com.df.management.configuration;

import com.df.core.common.exception.BusinessException;

public class ConfigurationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "configuration";

	public static final int CONFIGURABLE_MARSHALL_ERROR = 10000;

	public ConfigurationException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public ConfigurationException(Throwable throwable, int errorCode, String messageFormat, Object... args) {
		super(throwable, REALM, errorCode, messageFormat, args);
	}

}
