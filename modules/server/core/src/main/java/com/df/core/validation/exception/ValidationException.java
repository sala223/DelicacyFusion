package com.df.core.validation.exception;

import java.io.StringWriter;

import javax.validation.ConstraintViolation;

public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ConstraintViolation<?>[] violations;

	protected static final String CR = System.getProperty("line.separator");

	public ValidationException(ConstraintViolation<?>[] violations) {
		this.violations = violations;
	}

	public ConstraintViolation<?>[] getViolations() {
		return violations;
	}

	@Override
	public String getMessage() {
		StringWriter writer = new StringWriter(100);
		writer.write(CR);
		if (violations != null && violations.length > 0) {
			writer.write("Root Bean Class:" + violations[0].getRootBeanClass());
		}
		if (violations != null) {
			for (ConstraintViolation<?> violation : violations) {
				writer.write(CR);
				writer.write(violation.getPropertyPath() + ":" + violation.getMessage());
			}
		}
		return writer.toString();
	}
}
