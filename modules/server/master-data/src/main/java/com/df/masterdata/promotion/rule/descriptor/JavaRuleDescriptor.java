package com.df.masterdata.promotion.rule.descriptor;

import com.df.masterdata.promotion.rule.Rule;

public interface JavaRuleDescriptor extends RuleDescriptor {

	String getRuleQualifier();

	Class<? extends Rule> getRuleClass();
}
