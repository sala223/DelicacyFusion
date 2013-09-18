package com.df.order.promotion.rule;

import com.df.core.common.exception.BusinessException;

public class RuleParameterException extends BusinessException {

    private static final long serialVersionUID = 1L;

    private static final String REALM = "RuleParameter";

    private String parameter;

    private String ruleClass;

    private static final int PARAMETER_NOT_DEFINED = 100000;

    public RuleParameterException(int errorCode, String ruleClass, String par, String msgFormat, Object... args) {
	super(null, REALM, errorCode, msgFormat, args);
	this.parameter = par;
	this.ruleClass = ruleClass;
    }

    public String getParameter() {
	return parameter;
    }

    public String getRuleClass() {
	return ruleClass;
    }

    public static RuleParameterException parameterIsNotDefined(String ruleClass, String name) {
	String msg = String.format("Parameter % is not defined for rule %s", name, ruleClass);
	throw new RuleParameterException(PARAMETER_NOT_DEFINED, ruleClass, name, msg);
    }

    public static RuleParameterException parameterValueTypeError(String ruleClass, String name, String value) {
	String msg = String.format("Incorrect type of Value %s for parameter %s in rule %s", value, name, ruleClass);
	throw new RuleParameterException(PARAMETER_NOT_DEFINED, ruleClass, name, msg);
    }
}
