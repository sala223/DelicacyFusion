package com.df.order.promotion.rule;

public interface RuleDescriptor {

    String getRuleQualifier();

    Class<? extends Rule<?>> getRuleClass();
}
