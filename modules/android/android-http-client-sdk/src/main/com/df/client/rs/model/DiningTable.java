package com.df.client.rs.model;

import java.io.Serializable;

public class DiningTable implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code;

	private String barCode;

	private int capacity;

	private ServiceCard serviceCard;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ServiceCard getServiceCard() {
		return serviceCard;
	}

	public void setServiceCard(ServiceCard serviceCard) {
		this.serviceCard = serviceCard;
	}
}
