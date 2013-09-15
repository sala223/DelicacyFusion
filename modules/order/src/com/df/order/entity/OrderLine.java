package com.df.order.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.df.masterdata.entity.PriceUnit;

@Embeddable
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, name = "LINE_NUMBER")
    private int lineNumber;

    @Column(nullable = false, name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "ROOM_CODE")
    private String roomCode;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "DISCOUNT")
    private float discount;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "PRICE_UNIT")
    private PriceUnit priceUnit;

    @Column(name = "TOTAL_PAYMENT")
    private float totalPayment;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "PROMOTION_DETAILS")
    private String promotionDetails;

    public String getItemCode() {
	return itemCode;
    }

    public void setItemCode(String itemCode) {
	this.itemCode = itemCode;
    }

    public String getRoomCode() {
	return roomCode;
    }

    public void setRoomCode(String roomCode) {
	this.roomCode = roomCode;
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

    public int getLineNumber() {
	return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
	this.lineNumber = lineNumber;
    }

    public String getPromotionDetails() {
	return promotionDetails;
    }

    public void setPromotionDetails(String promotionDetails) {
	this.promotionDetails = promotionDetails;
    }

    public float getDiscount() {
	return discount;
    }

    public void setDiscount(float discount) {
	this.discount = discount;
    }

}
