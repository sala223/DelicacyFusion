package com.df.masterdata.promotion.rule;

import com.df.masterdata.entity.RuleDefinition;
import com.df.masterdata.entity.RuleParameter;
import com.df.masterdata.promotion.rule.descriptor.RuleDescriptor;

public class DefaultRuleValidator implements RuleValidator {

	private RuleRepository ruleRepository;

	public void setRuleRepository(RuleRepository ruleRepository) {
		this.ruleRepository = ruleRepository;
	}

	@Override
	public void validate(RuleDefinition definition) {
		RuleDescriptor ruleDescriptor = ruleRepository.getRuleByQualifier(definition.getQualifier());
		if (ruleDescriptor == null) {
			throw new RuleNotFoundException(definition.getQualifier());
		}
		String[] parameterNames = ruleDescriptor.getParameters().keySet().toArray(new String[0]);
		for (String parameter : parameterNames) {
			RuleParameter rp = definition.getParameter(parameter);
			if (rp == null) {
				throw new RuleValidationException("Invalid parameter %s", parameter);
			}
		}
	}
}
