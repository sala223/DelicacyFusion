package com.df.order.promotion;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;

public interface PromotionRepository {

    Promotion getBestPromotionForItem(Item item);

    Promotion getBestPromotionForOrder(Order order);
}
