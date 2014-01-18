package com.df.masterdata.promotion.rule.descriptor;

public interface RuleParameterDescriptor {

	String getName();

	String getDescription();

	RuleParameterType getType();

	String getDefaultValue();

	boolean isMandatory();
	
}
