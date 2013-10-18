package com.df.order.promotion.rule;

import java.util.HashMap;
import java.util.Map;

public class DefaultRuleRepository implements RuleRepository {

    private Map<String, RuleDescriptor> ruleTypes = new HashMap<String, RuleDescriptor>();
    {
	ruleTypes.put("itemDiscount", new DefaultRuleDescriptor("itemDiscount", ItemDiscountRule.class));
	ruleTypes.put("tastePrice", new DefaultRuleDescriptor("tastePrice", TastePriceRule.class));
    }

    @Override
    public RuleDescriptor getRuleByQualifier(String qualifier) {
	return ruleTypes.get(qualifier);
    }

    @Override
    public Rule<?> getRuleInstanceByQualifier(String qualifier) {
	RuleDescriptor desc = ruleTypes.get(ruleTypes);
	if (desc == null) {
	    throw new RuleNotFoundException(qualifier);
	}
	Class<? extends Rule<?>> ruleClass = desc.getRuleClass();
	try {
	    return ruleClass.newInstance();
	} catch (InstantiationException ex) {
	    throw new RuleInitialzeException(ex, qualifier);
	} catch (IllegalAccessException ex) {
	    throw new RuleInitialzeException(ex, qualifier);
	}
    }

}
