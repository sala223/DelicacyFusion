package com.df.masterdata.exception;

public class CategoryResourceBundleException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CategoryResourceBundleException(Throwable cause) {
	super(cause);
    }

    public CategoryResourceBundleException(Throwable throwable, String messageFormat, Object... args) {
	super(String.format(messageFormat, args), throwable);
    }

}
