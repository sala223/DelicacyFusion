package com.df.order.promotion.rule;

public class RuleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String qualifier;

    public RuleNotFoundException(String ruleQualifier, String messageFormat, Object... args) {
	super(String.format(messageFormat, args), null);
	this.qualifier = ruleQualifier;
    }

    public RuleNotFoundException(String ruleQualifier) {
	this(ruleQualifier, "Cannot find rule qualifier %s", ruleQualifier);
    }

    public String getRuleQualifier() {
	return qualifier;
    }

}
