package com.df.core.common.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int errorCode;

    private String realm;

    public BusinessException(Throwable cause) {
	super(cause);
    }

    public BusinessException(Throwable cause, int errorCode, String realm) {
	this(cause);
	this.errorCode = errorCode;
	this.realm = realm;
    }

    public BusinessException(Throwable throwable, String messageFormat, Object... args) {
	super(String.format(messageFormat, args), throwable);
    }

    public BusinessException(Throwable throwable, int errorCode, String realm, String messageFormat, Object... args) {
	super(String.format(messageFormat, args), throwable);
	this.errorCode = errorCode;
	this.realm = realm;
    }

    public int getErrorCode() {
	return errorCode;
    }

    public void setErrorCode(int errorCode) {
	this.errorCode = errorCode;
    }

    public String getRealm() {
	return realm;
    }

    public void setRealm(String realm) {
	this.realm = realm;
    }
}
