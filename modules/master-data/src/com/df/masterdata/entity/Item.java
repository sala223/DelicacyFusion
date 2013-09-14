package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Item extends MasterData {

    @OneToOne
    private ItemTemplate itemTemplate;

    @Column(name = "PRICE")
    private Float price;

    @Column(name = "PROMOTION_RULE")
    private String promotionRule;

    @Column(name = "STORE_CODE")
    private String storeCode;

    Item() {
    }

    public Item(ItemTemplate itemTemplate, String storeCode) {
	this.itemTemplate = itemTemplate;
	this.storeCode = storeCode;
    }

    public String getStoreCode() {
	return storeCode;
    }

    public void setStoreCode(String storeCode) {
	this.storeCode = storeCode;
    }

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
