package com.df.order.promotion;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;

public interface PromotionExecutor {

    void executeRule(Item item, Promotion promotion);

    void executeRule(Order order, Promotion promotion);
}
