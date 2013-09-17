package com.df.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PaymentLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, name = "LINE_NUMBER")
    private long lineNumber;

    @Column(nullable = false, name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "TOTAL", scale = 2)
    private BigDecimal total;

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

    public BigDecimal getTotal() {
	return total;
    }

    public void setTotal(BigDecimal total) {
	this.total = total;
    }

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

}
