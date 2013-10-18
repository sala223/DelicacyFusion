package com.df.order.promotion.rule;

import java.math.BigDecimal;
import java.util.Map;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.RuleParameter;

public class TastePriceRule implements ItemRule {

    private float tastePrice;

    private static final String TASTE_PRICE_PAR = "TASTE_PRICE";

    @Override
    public BigDecimal execute(Item entity, Map<String, RuleParameter> parmaters) {
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

	return new BigDecimal(tastePrice);
    }

}
