package com.df.masterdata.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

@Entity
public class Item extends StoreAwareMasterData {

    @OneToOne
    @JsonIgnore
    private ItemTemplate itemTemplate;

    @Column(name = "PRICE", scale=2)
    private Float price;
    
    Item() {
    }

    public Item(ItemTemplate itemTemplate, String storeCode) {
	this.itemTemplate = itemTemplate;
	this.setStoreCode(storeCode);
	this.setCode(itemTemplate.getCode()); 
    }

    public ItemTemplate getItemTemplate() {
	return itemTemplate;
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
    public String getCurrency() {
	return this.itemTemplate.getCurrency();
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
