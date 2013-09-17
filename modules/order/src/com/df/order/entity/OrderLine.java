package com.df.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, name = "LINE_NUMBER")
    private int lineNumber;

    @Column(nullable = false, name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "ROOM_CODE")
    private String roomCode;

    @Column(name = "PRICE", scale = 2)
    private BigDecimal price;

    @Column(name = "DISCOUNT", scale = 2)
    private BigDecimal discount;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "TOTAL_PAYMENT", scale = 2)
    private BigDecimal totalPayment;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "PROMOTION_ID")
    private long promotionId;
    
    @Column(name = "PROMOTION_NAME")
    private String promotionName;

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

    public BigDecimal getPrice() {
	return price;
    }

    public void setPrice(BigDecimal price) {
	this.price = price;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    public BigDecimal getTotalPayment() {
	return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
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

    public long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(long promotionId) {
        this.promotionId = promotionId;
    }

    public BigDecimal getDiscount() {
	return discount;
    }

    public void setDiscount(BigDecimal discount) {
	this.discount = discount;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }
    
    
}
