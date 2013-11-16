package com.df.order.promotion.rule;

import java.math.BigDecimal;
import java.util.Map;

import com.df.masterdata.entity.RuleParameter;

public interface Rule<T> { 

    public static int DEFAULT_SCALE = 2;

    BigDecimal execute(T entity, Map<String, RuleParameter> parmaters);

}
