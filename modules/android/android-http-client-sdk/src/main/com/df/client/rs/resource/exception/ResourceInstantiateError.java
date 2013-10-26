package com.df.client.rs.resource.exception;

public class ResourceInstantiateError extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceInstantiateError(Throwable cause) {
	super(cause);
    }

    public ResourceInstantiateError(Throwable throwable, String messageFormat, Object... args) {
	super(String.format(messageFormat, args), throwable);
    }

}
