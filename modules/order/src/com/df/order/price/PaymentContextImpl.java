package com.df.order.price;

import java.util.Currency;

import org.springframework.security.core.userdetails.User;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.Promotion;
import com.df.order.entity.Order;

public class PaymentContextImpl implements PaymentContext {

    private Order order;

    private Item item;

    private Currency payCurrency;

    private User user;

    private Promotion orderAppliedPromotion;

    private Promotion itemAppliedPromotion;

    @Override
    public void setOrder(Order order) {
	this.order = order;
    }

    @Override
    public void setItem(Item item) {
	this.item = item;
    }

    @Override
    public void setPayCurrency(Currency currency) {
	this.payCurrency = currency;
    }

    @Override
    public Promotion getOrderAppliedPromotion() {
	return orderAppliedPromotion;
    }

    @Override
    public Promotion getItemAppliedPromotion() {
	return itemAppliedPromotion;
    }

    Order getOrder() {
	return order;
    }

    Item getItem() {
	return item;
    }

    Currency getPayCurrency() {
	return payCurrency;
    }

    void setOrderAppliedPromotion(Promotion orderAppliedPromotion) {
	this.orderAppliedPromotion = orderAppliedPromotion;
    }

    public void setItemAppliedPromotion(Promotion itemAppliedPromotion) {
	this.itemAppliedPromotion = itemAppliedPromotion;
    }

    @Override
    public void setUser(User user) {
	this.user = user;
    }

    User getUser() {
	return user;
    }

}
