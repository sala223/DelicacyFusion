package com.df.order.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeliveryLine implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false, name = "LINE_NUMBER")
    private long lineNumber;

    @Column(nullable = false, name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "ROOM_CODE")
    private String roomCode;

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

    public int getQuantity() {
	return quantity;
    }

    public void setQuantity(int quantity) {
	this.quantity = quantity;
    }

    public String getRoomCode() {
	return roomCode;
    }

    public void setRoomCode(String roomCode) {
	this.roomCode = roomCode;
    }
}
