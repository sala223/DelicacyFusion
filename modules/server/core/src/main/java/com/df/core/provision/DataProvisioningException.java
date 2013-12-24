package com.df.core.provision;

public class DataProvisioningException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataProvisioningException(Throwable cause) {
		super(cause);
	}

	public DataProvisioningException(String format, Object... args) {
		super(String.format(format, args));
	}

	public DataProvisioningException(Throwable cause, String format, Object... args) {
		super(String.format(format, args), cause);
	}
}
