package com.df.management.configuration;

import com.df.core.common.exception.BusinessException;

public class ConfigurationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "configuration";

	public static final int CONFIGURABLE_NO_DEFAULT_CONSTRUCTOR = 10000;

	public static final int CONFIGURABLE_MARSHALL_ERROR = 10001;

	public static final int CONFIGURABLE_UNMARSHALL_ERROR = 10002;

	public static final int CONFIGURABLE_NOT_FOUND = 10003;

	public static final int CONFIGURABLE_INITIALIZATION_ERROR = 10004;

	public ConfigurationException(Throwable cause, int errorCode) {
		super(cause, REALM, errorCode);
	}

	public ConfigurationException(Throwable throwable, int errorCode, String messageFormat, Object... args) {
		super(throwable, REALM, errorCode, messageFormat, args);
	}

}
