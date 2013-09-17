package com.df.masterdata.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class StoreAwareMasterData extends MasterData {

    @Column(nullable = false, name = "STORE_CODE", length = 64)
    private String storeCode;

    public String getStoreCode() {
	return storeCode;
    }

    public void setStoreCode(String storeCode) {
	this.storeCode = storeCode;
    }

}
