package com.df.masterdata.promotion.rule.descriptor;

import java.util.HashMap;
import java.util.Map;

import com.df.masterdata.promotion.rule.Rule;
import com.df.masterdata.promotion.rule.RuleInitialzeException;
import com.df.masterdata.promotion.rule.RuleNotFoundException;
import com.df.masterdata.promotion.rule.RuleRepository;

public class DefaultRuleRepository implements RuleRepository {

	private Map<String, RuleDescriptor> ruleTypes = new HashMap<String, RuleDescriptor>();

	public void registerRule(RuleDescriptor rule) {
		ruleTypes.put(rule.getRuleQualifier(), rule);
	}

	@Override
	public RuleDescriptor getRuleByQualifier(String qualifier) {
		return ruleTypes.get(qualifier);
	}

	public Rule getRuleInstanceByQualifier(String qualifier) {
		RuleDescriptor desc = ruleTypes.get(qualifier);
		if (desc == null) {
			throw new RuleNotFoundException(qualifier);
		}
		if (desc instanceof JavaRuleDescriptor) {
			Class<? extends Rule> ruleClass = ((JavaRuleDescriptor) desc).getRuleClass();
			try {
				return ruleClass.newInstance();
			} catch (InstantiationException ex) {
				throw new RuleInitialzeException(ex, qualifier);
			} catch (IllegalAccessException ex) {
				throw new RuleInitialzeException(ex, qualifier);
			}
		} else {
			String messsage = "Doesn't support to create rule instance for non java type rule";
			throw new RuleInitialzeException(null, desc.getRuleQualifier(), messsage);
		}
	}

	@Override
	public RuleDescriptor[] getRules() {
		return ruleTypes.values().toArray(new RuleDescriptor[0]);
	}
}
