package com.df.order.price;

import java.util.Currency;

import com.df.idm.entity.User;
import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;

public interface PaymentContext {

    void setUser(User user);

    void setOrder(Order order);

    void setItem(Item item);

    void setPayCurrency(Currency currency);

    Promotion getOrderAppliedPromotion();

    Promotion getItemAppliedPromotion();

}
