package com.df.masterdata.promotion.rule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import com.df.masterdata.entity.RuleParameter;
import com.df.masterdata.promotion.rule.ItemRule;
import com.df.masterdata.promotion.rule.Rule;
import com.df.masterdata.promotion.rule.RuleParameterException;

public class TastePriceRule implements ItemRule {

	private float tastePrice;

	private int scale = Rule.DEFAULT_SCALE;

	public static final String TASTE_PRICE_PAR = "TASTE_PRICE";

	@Override
	public BigDecimal execute(Object entity, Map<String, RuleParameter> parmaters) {
		RuleParameter parameter = parmaters.get(TASTE_PRICE_PAR);
		if (parameter == null) {
			throw RuleParameterException.parameterIsNotDefined(this.getClass().getName(), TASTE_PRICE_PAR);
		}
		try {
			tastePrice = Float.parseFloat(parameter.getValue());
		} catch (NumberFormatException ex) {
			String ruleClass = ItemDiscountRule.class.getName();
			throw RuleParameterException.parameterValueTypeError(ruleClass, TASTE_PRICE_PAR, parameter.getValue());
		}

		return new BigDecimal(tastePrice).setScale(scale, RoundingMode.HALF_UP);
	}

}
