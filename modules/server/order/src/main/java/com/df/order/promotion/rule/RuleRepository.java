package com.df.order.promotion.rule;

public interface RuleRepository {

    public RuleDescriptor getRuleByQualifier(String qualifier);

    public Rule<?> getRuleInstanceByQualifier(String qualifier);

}
