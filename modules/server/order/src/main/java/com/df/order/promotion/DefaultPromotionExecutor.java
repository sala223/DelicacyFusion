package com.df.order.promotion;

import org.springframework.beans.factory.annotation.Autowired;

import com.df.masterdata.entity.Promotion;
import com.df.masterdata.promotion.rule.Rule;
import com.df.masterdata.promotion.rule.RuleNotFoundException;
import com.df.masterdata.promotion.rule.RuleRepository;
import com.df.masterdata.promotion.rule.descriptor.RuleDescriptor;

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
	protected Rule lookupRule(Promotion promotion) {
		RuleDescriptor desc = ruleRepository.getRuleByQualifier(promotion.getRule().getQualifier());
		if (desc == null) {
			throw new RuleNotFoundException(promotion.getRule().getQualifier());
		}
		return (Rule) ruleRepository.getRuleInstanceByQualifier(promotion.getRule().getQualifier());
	}
}
