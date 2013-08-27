package com.df.order.promotion;

import com.df.masterdata.entity.Item;
import com.df.order.entity.Order;

public interface PromotionExecutor {

    void setPromotionRepository(PromotionRepository repository);

    void runPromotionForItem(Item item);

    void runPromotionForOrder(Order order);
}
