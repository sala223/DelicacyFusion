package com.df.client.rs.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderLine implements Serializable {

    private static final long serialVersionUID = 1L;

    private int lineNumber;

//    private String itemCode;
    private Item item;

    private String roomCode;

    private String tableNumber;

    private float price;

    private float promotionPrice;

    private BigDecimal discount;

    private int quantity;

    private BigDecimal totalPayment;

    private BigDecimal promotionTotalPayment;

    private String comment;

    private Long promotionId;

    private String promotionName;

    OrderLine() {
    }

    public OrderLine(Item item) {
	this(item, 1);
    }
    
    public OrderLine(Item item, int quantity) {
	this.item = item;
	this.quantity = quantity;
    }

    public Item getItem() {
	return item;
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

    public BigDecimal getTotalPayment() {
	return totalPayment;
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

    public String getPromotionName() {
	return promotionName;
    }

    public void setPromotionName(String promotionName) {
	this.promotionName = promotionName;
    }

    public float getPromotionPrice() {
	return promotionPrice;
    }

    public void setPromotionPrice(float promotionPrice) {
	this.promotionPrice = promotionPrice;
    }

    public String getTableNumber() {
	return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
	this.tableNumber = tableNumber;
    }

    public BigDecimal getPromotionTotalPayment() {
	return promotionTotalPayment;
    }

    public boolean hasPromotion() {
	return this.promotionId != null && promotionId.longValue() != 1l;
    }

    public void calcuatePayment() {
	this.totalPayment = new BigDecimal(this.price).multiply(new BigDecimal(quantity));
	if (this.hasPromotion()) {
	    this.promotionTotalPayment = new BigDecimal(this.promotionPrice).multiply(new BigDecimal(quantity));
	    this.discount = this.totalPayment.subtract(this.promotionTotalPayment);
	}
    }
    
	@Override
	public boolean equals(Object o) {
		return o != null && tableNumber.equals(((OrderLine)o).getTableNumber()) && item.equals(((OrderLine)o).getItem());
	}
}
