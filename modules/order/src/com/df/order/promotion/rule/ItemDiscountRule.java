package com.df.order.promotion.rule;

import java.math.BigDecimal;
import java.util.Map;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.RuleParameter;

public class ItemDiscountRule implements ItemRule {

    private float discount;

    private static final String DISCOUNT_PAR = "DISCOUNT";

    @Override
    public BigDecimal execute(Item item, Map<String, RuleParameter> parmaters) {
	RuleParameter parameter = parmaters.get(DISCOUNT_PAR);
	if (parameter == null) {
	    throw RuleParameterException.parameterIsNotDefined(this.getClass().getName(), DISCOUNT_PAR);
	}
	try {
	    discount = Float.parseFloat(parameter.getValue());
	} catch (NumberFormatException ex) {
	    String ruleClass = ItemDiscountRule.class.getName();
	    throw RuleParameterException.parameterValueTypeError(ruleClass, DISCOUNT_PAR, parameter.getValue());
	}

	return new BigDecimal(discount).multiply(new BigDecimal(0.1)).multiply(new BigDecimal(item.getPrice()));
    }
}
