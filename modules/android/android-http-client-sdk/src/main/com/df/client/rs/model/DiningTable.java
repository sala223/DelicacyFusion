package com.df.client.rs.model;

import java.io.Serializable;

public class DiningTable implements Serializable {

    private static final long serialVersionUID = 1L;

    private String number;

    private String barCode;

    private int capacity;

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getBarCode() {
	return barCode;
    }

    public void setBarCode(String barCode) {
	this.barCode = barCode;
    }

    public int getCapacity() {
	return capacity;
    }

    public void setCapacity(int capacity) {
	this.capacity = capacity;
    }

}
