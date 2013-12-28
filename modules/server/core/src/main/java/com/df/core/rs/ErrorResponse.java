package com.df.core.rs;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private String realm;

	private int errorCode;

	private String error;

	private ErrorFormat errorFormat = ErrorFormat.TEXT;

	public static enum ErrorFormat {
		TEXT, JSON
	}

	public ErrorResponse(String realm, int errorCode, String error) {
		this.realm = realm;
		this.errorCode = errorCode;
		this.error = error;
	}

	public String getRealm() {
		return this.realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ErrorFormat getErrorFormat() {
		return errorFormat;
	}

	public void setErrorFormat(ErrorFormat errorFormat) {
		this.errorFormat = errorFormat;
	}
}
