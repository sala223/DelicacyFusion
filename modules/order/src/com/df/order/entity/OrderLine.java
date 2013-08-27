package com.df.order.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.df.masterdata.entity.PriceUnit;

@Embeddable
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private long lineNumber;

    @Column(nullable = false)
    private long itemId;

    @Column
    private long roomId;

    @Column
    private float price;

    @Column
    private int quantity;

    @Column
    private PriceUnit priceUnit;

    @Column
    private float totalPayment;

    @Column
    private String comment;

    @Column
    private String promotionDetails;

    public long getItemId() {
	return itemId;
    }

    public void setItemId(long itemId) {
	this.itemId = itemId;
    }

    public long getRoomId() {
	return roomId;
    }

    public void setRoomId(long roomId) {
	this.roomId = roomId;
    }

    public float getPrice() {
	return price;
    }

    public void setPrice(float price) {
	this.price = price;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    public PriceUnit getPriceUnit() {
	return priceUnit;
    }

    public void setPriceUnit(PriceUnit priceUnit) {
	this.priceUnit = priceUnit;
    }

    public float getTotalPayment() {
	return totalPayment;
    }

    public void setTotalPayment(float totalPayment) {
	this.totalPayment = totalPayment;
    }

    public String getComment() {
	return comment;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public long getLineNumber() {
	return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
	this.lineNumber = lineNumber;
    }

    public String getPromotionDetails() {
	return promotionDetails;
    }

    public void setPromotionDetails(String promotionDetails) {
	this.promotionDetails = promotionDetails;
    }
}
