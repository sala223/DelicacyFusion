package com.df.masterdata.promotion.rule;

import java.math.BigDecimal;
import java.util.Map;

import com.df.masterdata.entity.RuleParameter;

public interface Rule {

	public static int DEFAULT_SCALE = 2;

	BigDecimal execute(Object entity, Map<String, RuleParameter> parmaters);

}
