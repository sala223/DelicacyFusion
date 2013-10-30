package com.df.order.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemType;
import com.df.masterdata.entity.ItemUnit;
import com.df.masterdata.entity.PictureRef;

public class ItemDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private float currentPrice;

    private List<String> pictureLink;

    private Item item;

    public ItemDetails(Item item) {
	this.item = item;
    }

    public float getPrimePrice() {
	return item.getPrice();
    }

    public float getCurrentPrice() {
	return this.currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
	this.currentPrice = currentPrice;
    }

    public String getName() {
	return item.getName();
    }

    @JsonProperty
    public String getCode() {
	return item.getCode();
    }

    @JsonProperty
    public List<String> getCategories() {
	return item.getCategories();
    }

    @JsonProperty
    public ItemType getType() {
	return item.getType();
    }

    @JsonProperty
    public List<String> getPictureLinks() {
	Set<PictureRef> picutureSet = item.getPictureSet();
	List<String> link = new ArrayList<String>();
	if (picutureSet != null) {

	}
	return link;
    }

    @JsonProperty
    public String getDescription() {
	return item.getDescription();
    }

    @JsonProperty
    public String getCurrency() {
	return item.getCurrency();
    }

    @JsonProperty
    public ItemUnit getItemUnit() {
	return item.getItemUnit();
    }
}
