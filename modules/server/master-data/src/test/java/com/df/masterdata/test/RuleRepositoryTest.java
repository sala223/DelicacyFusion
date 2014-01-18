package com.df.masterdata.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.df.masterdata.promotion.rule.descriptor.ConfigurableRuleRepository;

public class RuleRepositoryTest {

	@Test
	public void testInitConfigurableRuleRepository() {
		ConfigurableRuleRepository ruleRepository = new ConfigurableRuleRepository();
		ruleRepository.afterPropertiesSet();
		TestCase.assertEquals(2,ruleRepository.getRules().length);
	}
}
