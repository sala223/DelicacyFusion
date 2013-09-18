package com.df.order.promotion.rule;

import java.math.BigDecimal;
import java.util.Map;

import com.df.masterdata.entity.RuleParameter;

public interface Rule<T> {

    BigDecimal execute(T entity, Map<String, RuleParameter> parmaters);
}
