package com.df.order.promotion;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.entity.Promotion;
import com.df.order.promotion.rule.Rule;
import com.df.order.promotion.rule.RuleDescriptor;
import com.df.order.promotion.rule.RuleNotFoundException;
import com.df.order.promotion.rule.RuleRepository;

public class DefaultPromotionExecutor extends AbstractPromotionExecutor {

    @Autowired
    private RuleRepository ruleRepository;

    public DefaultPromotionExecutor(RuleRepository ruleRepository) {
	this.ruleRepository = ruleRepository;
    }

    public void setRuleRepository(RuleRepository ruleRepository) {
	this.ruleRepository = ruleRepository;
    }

    @Override
    protected Rule<?> lookupRule(Promotion promotion) {
	RuleDescriptor desc = ruleRepository.getRuleByQualifier(promotion.getRule().getQualifier());
	if (desc == null) {
	    throw new RuleNotFoundException(promotion.getRule().getQualifier());
	}
	return ruleRepository.getRuleInstanceByQualifier(promotion.getRule().getQualifier());
    }
}
