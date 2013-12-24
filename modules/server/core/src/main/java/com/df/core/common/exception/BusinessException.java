package com.df.core.common.exception;

public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	private int errorCode;

	private String realm;

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(Throwable throwable, String messageFormat, Object... args) {
		super(throwable, messageFormat, args);
	}

	public BusinessException(Throwable cause, String realm, int errorCode) {
		this(cause);
		this.errorCode = errorCode;
		this.realm = realm;
	}

	public BusinessException(Throwable throwable, String realm, int errorCode, String messageFormat, Object... args) {
		super(throwable, messageFormat, args);
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
