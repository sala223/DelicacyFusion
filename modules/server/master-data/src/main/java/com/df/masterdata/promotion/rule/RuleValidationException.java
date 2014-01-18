package com.df.masterdata.promotion.rule;

import com.df.core.common.exception.BusinessException;

public class RuleValidationException extends BusinessException {

	private static final long serialVersionUID = 1L;

	private static final String REALM = "ruleValidation";

	private static final int ERROR_CODE = 99999999;

	public RuleValidationException(String messageFormat, Object... args) {
		super(null, REALM, ERROR_CODE, messageFormat, args);
	}
}
