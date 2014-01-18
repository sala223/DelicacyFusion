package com.df.masterdata.promotion.rule.descriptor;

import java.util.Map;

public interface RuleDescriptor {

	String getRuleQualifier();
	
	String getDescription();

	RuleType getType();

	Map<String, RuleParameterDescriptor> getParameters();

	public static enum RuleType {
		ITEM, ORDER, ALL
	}
}
