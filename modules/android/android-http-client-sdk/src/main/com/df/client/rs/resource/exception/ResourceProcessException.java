package com.df.client.rs.resource.exception;

import java.util.Locale;

public class ResourceProcessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String realm;

    private int errorCode;

    public ResourceProcessException(String realm, int errorCode, String message) {
	super(message);
	this.realm = realm;
	this.errorCode = errorCode;
    }

    public String getRealm() {
	return realm;
    }

    public int getErrorCode() {
	return errorCode;
    }

    @Override
    public String getMessage() {
	StringBuffer msg = new StringBuffer();
	msg.append("{realm=%s;");
	msg.append("errorCode=" + errorCode + ";");
	msg.append("message=%s}");
	return String.format(Locale.getDefault(), msg.toString(), getRealm(), super.getMessage());
    }

}
