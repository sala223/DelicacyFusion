package com.df.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.df.core.persist.eclipselink.MultiTenantSupport;
import com.df.masterdata.entity.PriceUnit;

@Entity
public class OrderLine extends MultiTenantSupport {

    @ManyToOne(optional = false)
    private Order order;

    @Column(nullable = false)
    private long lineId;

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

    public Order getOrder() {
	return order;
    }

    public void setOrder(Order order) {
	this.order = order;
    }

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

    public long getLineId() {
	return lineId;
    }

    public void setLineId(long lineId) {
	this.lineId = lineId;
    }

    public String getPromotionDetails() {
	return promotionDetails;
    }

    public void setPromotionDetails(String promotionDetails) {
	this.promotionDetails = promotionDetails;
    }
}
