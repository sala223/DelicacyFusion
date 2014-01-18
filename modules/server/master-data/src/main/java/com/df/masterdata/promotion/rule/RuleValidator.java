package com.df.masterdata.promotion.rule;

import com.df.masterdata.entity.RuleDefinition;

public interface RuleValidator {

	void validate(RuleDefinition ruleDefinition) throws RuleValidationException;

}
