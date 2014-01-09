package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.eclipse.persistence.annotations.Index;
import org.eclipse.persistence.annotations.Indexes;

@Entity
@Table(name = "DINING_TABLE")
@JsonIgnoreProperties({ "isEnabled" })
@Indexes({ @Index(name = "IDX_DT_BAR_CODE", unique = true, columnNames = { "BAR_CODE" }),
        @Index(name = "IDX_DT_CODE", unique = false, columnNames = { "STORE_CODE", "CODE" }) })
public class DiningTable extends StoreAwareMasterData {

	@Size(message = "{diningTable.barCode.Size}", max = 128)
	@Column(nullable = true, length = 128, name = "BAR_CODE")
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
