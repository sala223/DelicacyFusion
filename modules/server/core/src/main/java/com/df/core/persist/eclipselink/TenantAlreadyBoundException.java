package com.df.core.persist.eclipselink;

public class TenantAlreadyBoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public TenantAlreadyBoundException(Throwable cause) {
	super(cause);
    }

    public TenantAlreadyBoundException(String format, Object... args) {
	this((Throwable) null, String.format(format, args));
    }

    public TenantAlreadyBoundException(Throwable cause, String format, Object... args) {
	super(String.format(format, args), cause);
    }
}
