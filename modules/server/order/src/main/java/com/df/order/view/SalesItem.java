package com.df.order.view;

import java.io.Serializable;

import com.df.masterdata.entity.Item;

public class SalesItem extends Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private float currentPrice;

    private String promotionDetails;

    public SalesItem(Item item) {
	super(item.getItemTemplate(), item.getStoreCode());
    }

    public String getPromotionDetails() {
	return promotionDetails;
    }

    public void setPromotionDetails(String promotionDetails) {
	this.promotionDetails = promotionDetails;
    }

    public float getCurrentPrice() {
	return this.currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
	this.currentPrice = currentPrice;
    }
}
