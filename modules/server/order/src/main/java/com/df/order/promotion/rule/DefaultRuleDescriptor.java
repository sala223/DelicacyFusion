package com.df.order.promotion.rule;

public class DefaultRuleDescriptor implements RuleDescriptor {

    private String ruleQualifier;

    private Class<? extends Rule<?>> ruleClass;

    public DefaultRuleDescriptor(String ruleQualifier, Class<? extends Rule<?>> ruleClass) {
	this.ruleQualifier = ruleQualifier;
	this.ruleClass = ruleClass;
    }

    @Override
    public String getRuleQualifier() {
	return ruleQualifier;
    }

    @Override
    public Class<? extends Rule<?>> getRuleClass() {
	return ruleClass;
    }

}
