package com.df.masterdata.promotion.rule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.RuleParameter;
import com.df.masterdata.promotion.rule.ItemRule;
import com.df.masterdata.promotion.rule.Rule;
import com.df.masterdata.promotion.rule.RuleParameterException;

public class ItemDiscountRule implements ItemRule {

	private float discount;

	private int scale = Rule.DEFAULT_SCALE;

	public static final String DISCOUNT_PAR = "DISCOUNT";

	@Override
	public BigDecimal execute(Object entity, Map<String, RuleParameter> parmaters) {
		if (entity instanceof Item) {
			Item item = (Item) entity;
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

			BigDecimal p = new BigDecimal(discount).multiply(new BigDecimal(0.1)).multiply(
			        new BigDecimal(item.getPrice()));
			return p.setScale(scale, RoundingMode.HALF_EVEN);
		} else {
			throw new IllegalArgumentException("entity must be type of " + Item.class.getName());
		}
	}
}
