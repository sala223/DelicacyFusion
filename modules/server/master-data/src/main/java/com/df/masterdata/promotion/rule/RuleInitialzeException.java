package com.df.masterdata.promotion.rule;

public class RuleInitialzeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String qualifier;

	public RuleInitialzeException(Throwable ex, String ruleQualifier, String messageFormat, Object... args) {
		super(String.format(messageFormat, args), ex);
		this.qualifier = ruleQualifier;
	}

	public RuleInitialzeException(Throwable ex, String ruleQualifier) {
		this(ex, ruleQualifier, "Cannot initialize rule instance of qualifier %s", ruleQualifier);
	}

	public String getRuleQualifier() {
		return qualifier;
	}

}
