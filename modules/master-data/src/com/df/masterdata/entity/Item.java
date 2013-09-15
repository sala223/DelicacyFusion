package com.df.masterdata.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

@Entity
public class Item extends MasterData {

    @OneToOne
    @JsonIgnore
    private ItemTemplate itemTemplate;

    @Column(name = "PRICE")
    private Float price;

    @Column(name = "STORE_CODE")
    private String storeCode;

    Item() {
    }

    public Item(ItemTemplate itemTemplate, String storeCode) {
	this.itemTemplate = itemTemplate;
	this.storeCode = storeCode;
    }

    public ItemTemplate getItemTemplate() {
	return itemTemplate;
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

    @JsonProperty
    public String getName() {
	return itemTemplate.getName();
    }

    @JsonProperty
    public String getCode() {
	return itemTemplate.getCode();
    }

    @JsonProperty
    public List<String> getCategories() {
	return this.itemTemplate.getCategories();
    }

    @JsonProperty
    public ItemType getType() {
	return this.itemTemplate.getType();
    }

    @JsonProperty
    public Set<PictureRef> getPictureSet() {
	return this.itemTemplate.getPictureSet();
    }

    @JsonProperty
    public String getDescription() {
	return this.itemTemplate.getDescription();
    }

    @JsonProperty
    public PriceUnit getPriceUnit() {
	return this.itemTemplate.getPriceUnit();
    }

    @JsonProperty
    public ItemUnit getItemUnit() {
	return this.itemTemplate.getItemUnit();
    }

    @Override
    public boolean isEnabled() {
	if (this.itemTemplate.isEnabled()) {
	    return super.isEnabled();
	}
	return false;
    }

}
