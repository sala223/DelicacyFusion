package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Item extends MasterData {

    @OneToOne
    private ItemTemplate itemTemplate;

    @Column
    private Float price;

    @Column
    private String promotionRule;

    @Column
    private long storeId;

    Item() {
    }

    public Item(ItemTemplate itemTemplate) {
	this.itemTemplate = itemTemplate;
    }

    public long getStoreId() {
	return storeId;
    }

    public void setStoreId(long storeId) {
	this.storeId = storeId;
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
