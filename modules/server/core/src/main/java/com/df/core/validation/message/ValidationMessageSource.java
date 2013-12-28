package com.df.core.validation.message;

import org.springframework.context.support.MessageSourceAccessor;

class ValidationMessageSource extends MultipleResourceMessageSource {

	private static final String VALIDATION_DEFAULT_BASE_NAME = "classpath:messages/ValidationMessages";

	public ValidationMessageSource() {
		this(new String[] { VALIDATION_DEFAULT_BASE_NAME });
	}

	public ValidationMessageSource(String[] baseNames) {
		this.setUseCodeAsDefaultMessage(true);
		this.setAlwaysUseMessageFormat(false);
		this.setCacheSeconds(-1);
		this.setBasenames(baseNames);
	}

	public MessageSourceAccessor getAccessor() {
		return new MessageSourceAccessor(this);
	}
}
