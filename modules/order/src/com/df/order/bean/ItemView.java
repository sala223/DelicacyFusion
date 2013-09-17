package com.df.order.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.df.masterdata.entity.Item;
import com.df.masterdata.entity.ItemType;
import com.df.masterdata.entity.ItemUnit;
import com.df.masterdata.entity.PictureRef;

public class ItemView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String promotionDetails;

    private BigDecimal discountPrice;

    private BigDecimal discount;

    @JsonIgnore
    private Item item;

    ItemView() {

    }

    public ItemView(Item item) {
	this.item = item;
    }

    public String getPromotionDetails() {
	return promotionDetails;
    }

    public void setPromotionDetails(String promotionDetails) {
	this.promotionDetails = promotionDetails;
    }

    public BigDecimal getDiscountPrice() {
	return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
	this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscount() {
	return discount;
    }

    public void setDiscount(BigDecimal discount) {
	this.discount = discount;
    }

    public float getPrice() {
	return item.getPrice();
    }

    @JsonProperty
    public String getName() {
	return item.getName();
    }

    @JsonProperty
    public String getCode() {
	return item.getCode();
    }

    @JsonProperty
    public List<String> getCategories() {
	return this.item.getCategories();
    }

    @JsonProperty
    public ItemType getType() {
	return this.item.getType();
    }

    @JsonProperty
    public Set<PictureRef> getPictureSet() {
	return this.item.getPictureSet();
    }

    @JsonProperty
    public String getDescription() {
	return this.item.getDescription();
    }

    @JsonProperty
    public String getCurrency() {
	return this.item.getCurrency();
    }

    @JsonProperty
    public ItemUnit getItemUnit() {
	return this.item.getItemUnit();
    }

}
