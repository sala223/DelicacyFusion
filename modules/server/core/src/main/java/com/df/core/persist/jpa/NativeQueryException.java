package com.df.core.persist.jpa;

public class NativeQueryException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static String NATIVE_SQL_PAR_VALUE_NOT_SET_MSG = "%s contains positional parameter in index %d, but the value is not set";

    private static String NATIVE_SQL_PAR_VALUE_TYPE_NOT_MATCH_MSG = "%s contains positional Array or List parameter in index %d, but the value is not Array or List type";

    public NativeQueryException(Throwable cause) {
	super(cause);
    }

    public NativeQueryException(String message, Object... args) {
	this(null, message, args);
    }

    public NativeQueryException(Throwable cause, String messageFormat, Object... args) {
	super(String.format(messageFormat, args), cause);
    }

    public static NativeQueryException NativeSQLParValueNotSetException(String sql, int index) {
	return new NativeQueryException(NATIVE_SQL_PAR_VALUE_NOT_SET_MSG, sql, index);
    }

    public static NativeQueryException NativeSQLExpandingParTypeUnmatchException(String sql, int index) {
	return new NativeQueryException(NATIVE_SQL_PAR_VALUE_TYPE_NOT_MATCH_MSG, sql, index);
    }
}
