package com.df.client.rs.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ServiceCard implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private List<String> tables;

	private String storeCode;

	private Long orderId;

	private Date createdTime;

	public long getId() {
		return id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public List<String> getTables() {
		return tables;
	}

	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
}
