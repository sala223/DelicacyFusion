package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@IdClass(StoreObjectId.class)
public abstract class StoreAwareMasterData extends MasterData {

	@Column(nullable = false, name = "STORE_CODE", length = 64)
	@Id
	private String storeCode;

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
}
