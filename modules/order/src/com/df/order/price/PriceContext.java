package com.df.order.price;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;

public interface PriceContext {

    void setOrder(Order order);

    void setItem(Item item);

    Promotion getOrderAppliedPromotion();

    Promotion getItemAppliedPromotion();

}
