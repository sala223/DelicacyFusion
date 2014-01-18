package com.df.masterdata.promotion.rule;

import com.df.masterdata.promotion.rule.descriptor.RuleDescriptor;

public interface RuleRepository {

	public RuleDescriptor getRuleByQualifier(String qualifier);

	public Rule getRuleInstanceByQualifier(String qualifier);
	
	public RuleDescriptor[] getRules();

}
