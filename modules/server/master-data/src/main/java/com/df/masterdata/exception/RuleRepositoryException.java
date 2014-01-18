package com.df.masterdata.exception;

public class RuleRepositoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RuleRepositoryException(Throwable cause) {
		super(cause);
	}

	public RuleRepositoryException(Throwable cause, String messageFormat, Object... args) {
		super(String.format(messageFormat, args), cause);
	}

}
