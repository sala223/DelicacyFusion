package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

@Entity
@Table(name = "DINING_TABLE")
@JsonIgnoreProperties({ "id", "isEnabled" })
@Indexes({ @Index(name = "IDX_DT_BAR_CODE", unique = true, columnNames = { "BAR_CODE" }),
        @Index(name = "IDX_DT_CODE", unique = false, columnNames = { "STORE_CODE", "CODE" }) })
public class DiningTable extends StoreAwareMasterData {

	@Column(nullable = false, length = 128, name = "BAR_CODE")
	private String barCode;

	@Column(name = "CAPACITY")
	private int capacity;

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
