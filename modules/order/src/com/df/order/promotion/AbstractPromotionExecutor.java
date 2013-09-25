package com.df.order.promotion;

import java.math.BigDecimal;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;
import com.df.order.promotion.rule.ItemRule;
import com.df.order.promotion.rule.OrderRule;
import com.df.order.promotion.rule.Rule;

public abstract class AbstractPromotionExecutor implements PromotionExecutor {

    protected abstract Rule<?> lookupRule(Promotion promotion);

    @Override
    public BigDecimal executeRule(Item item, Promotion promotion) {
	Rule<?> rule = lookupRule(promotion);
	if (rule != null && rule instanceof ItemRule) {
	    ItemRule itemRule = (ItemRule) rule;
	    return itemRule.execute(item, promotion.getRule().getParameters());
	} else {
	    return new BigDecimal(item.getPrice());
	}
    }

    @Override
    public BigDecimal executeRule(Order order, Promotion promotion) {
	Rule<?> rule = lookupRule(promotion);
	if (rule != null && rule instanceof OrderRule) {
	    OrderRule orderRule = (OrderRule) rule;
	    return orderRule.execute(order, promotion.getRule().getParameters());
	} else {
	    return order.getTotalPayment();
	}
    }

}
