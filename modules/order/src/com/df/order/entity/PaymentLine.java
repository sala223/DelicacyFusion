package com.df.order.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PaymentLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, name = "LINE_NUMBER")
    private long lineNumber;

    @Column(nullable = false, name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "TOTAL_PRICE")
    private float totalPrice;

    @Column(name = "QUANTITY")
    private int quantity;

    public long getLineNumber() {
	return lineNumber;
    }

    public void setLineNumber(long lineNumber) {
	this.lineNumber = lineNumber;
    }

    public String getItemCode() {
	return itemCode;
    }

    public void setItemCode(String itemCode) {
	this.itemCode = itemCode;
    }

    public float getTotalPrice() {
	return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
	this.totalPrice = totalPrice;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

}
