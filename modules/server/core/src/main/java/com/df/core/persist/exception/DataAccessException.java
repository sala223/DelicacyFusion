package com.df.core.persist.exception;

public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataAccessException(Throwable cause) {
	super(cause);
    }

    public DataAccessException(String format, Object... args) {
	this(null, format, args);
    }

    public DataAccessException(Throwable cause, String messageFormat, Object... args) {
	super(String.format(messageFormat, args), cause);
    }
}
