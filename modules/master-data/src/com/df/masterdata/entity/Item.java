package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Item extends StoreMasterData {

    @OneToOne
    private ItemTemplate itemTemplate;

    @Column
    private Float price;

    @Column
    private String promotionRule;

    public float getPrice() {
	if (this.price == null) {
	    return itemTemplate.getPrice();
	}
	return price;
    }

    public void setPrice(Float price) {
	this.price = price;
    }

    public String getPromotionRule() {
	return promotionRule;
    }

    public void setPromotionRule(String promotionRule) {
	this.promotionRule = promotionRule;
    }

    public ItemTemplate getItemTemplate() {
	return itemTemplate;
    }

    public void setItemTemplate(ItemTemplate itemTemplate) {
	this.itemTemplate = itemTemplate;
    }

}
