package com.df.order.promotion;

import java.math.BigDecimal;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;

public interface PromotionExecutor {

    BigDecimal executeRule(Item item, Promotion promotion);

    BigDecimal executeRule(Order order, Promotion promotion);
}
